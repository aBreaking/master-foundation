knative-eventing 事件源机制深入理解



# 核心组件

knative-eventing的组件比较多，因此eventing比较复杂。



## 事件源 Event source

在一个eventing的deployment里，事件源就是事件的生产者，事件被发送到接收者sink或订阅者subscriber。



## Broker和Trigger

Broker和Trigger提供了一种叫做“事件网格（event mesh）”的模型，允许事件生产者传送事件到一个Broker里，然后Broker再使用Trigger把这些事件统一分发给消费者。

这样的分发有以下好处：

* 消费者可以注册指定的事件类型，而不需要与生产者有直接联系；
* 底层平台可以使用指定的过滤条件来优化事件路由。



## Channel 和 Subscription

Channel 和 Subscription提供了一种叫做“事件流水 （event pipe）”模型，它使用Subscription可以在Channels之间转换和路由事件。

此模型适用于需要转换来自一个系统的事件然后将其路由到另一个进程的事件管道。 



## 事件仓库 Event registry

Knative Eventing 定义了一个 EventType 对象，让消费者更容易发现他们可以从 Brokers 消费的事件类型。

registry由事件类型的集合组成。 存储在registry中的事件类型包含（所有）消费者创建触发器所需的信息，而无需求助于其他一些带外机制。 

 

## 云事件 CloudEvents

在开始使用knative-eventing之前，有必要先阐述下CloudEvents。

CloudEvents　最初由　CNCF Severless 工作组提出。CloudEvents是一种以通用方式描述事件数据的规范。CloudEvents旨在简化跨服务，平台及其他方面的事件声明和发送。

**CloudEvents 的核心是定义了一组关于系统间传输事件的元数据（被称为属性），以及这些元数据应该如何出现在该消息中**。

也就是说事件源发送一个云事件给消费者，那么数据的格式应该遵守一个规范，这个规范就是CloudEvents。

目前大部分语言都有cloudevent的sdk，比如java、go、python等等。



一个Json的CloudEvent样例如下：（以下的字段都是必须的）

```
{
    "specversion" : "1.0",
    "type" : "com.github.pull.create",
    "source" : "https://github.com/cloudevents/spec/pull",
    "subject" : "123",
    "id" : "A234-1234-1234",
    "time" : "2018-04-05T17:31:00Z",
    "comexampleextension1" : "value",
    "comexampleothervalue" : 5,
    "datacontenttype" : "text/xml",
    "data" : "hello world!"
}
```



## event_display

不管何种类型的事件源，在发送事件之前，肯定需要先考虑发送到那里去，这里我们使用knative官方提供的一个消费者event_display，它也是一个knative的serving。

它的作用很简单，就是接收云事件，然后把内容以Json格式打印出来。方便我们观察。







# 自定义事件源

官方文档中给的样例有点复杂，对初学者有点不够友好，这里使用[salaboy.《Continuous Delivery for Kubernetes》](https://livebook.manning.com/book/continuous-delivery-for-kubernetes/chapter-6/v-5/59)一书中的demo来进行阐述。

我们即将要做的事情如下图：

![image-20220301101840682](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20220301101840682.png)





# 安装Kafka

from： [Quickstarts (strimzi.io): https://strimzi.io/quickstarts/](https://strimzi.io/quickstarts/)





kubectl apply -f event-display.yaml

```
event-display-00001-deployment-5497f49c99-bmdg5   2/2     Running   0          79s
```





kubectl apply -f event-source2.yaml

```
kafkasource-kafka-source-57481be6-dc0a-4112-8198-97651801dtg2b7   1/1     Running   0          88s
```



kubectl logs kafkasource-kafka-source-57481be6-dc0a-4112-8198-97651801dtg2b7





发送消息

```
kubectl -n kafka run kafka-producer -ti --image=quay.io/strimzi/kafka:0.27.1-kafka-3.0.0 --rm=true --restart=Never -- bin/kafka-console-producer.sh --broker-list my-cluster-kafka-bootstrap:9092 --topic knative-demo-topic
```



接收消息

```
kubectl logs --selector='serving.knative.dev/service=event-display' -c user-container -f 
```





# PingSource

创建ns:

```
kubectl create ns event-ping
```



创建sink：

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: event-display
  namespace: event-ping
spec:
  replicas: 1
  selector:
    matchLabels: &labels
      app: event-display
  template:
    metadata:
      labels: *labels
    spec:
      containers:
        - name: event-display
        # 这里替换了官方的镜像
          image: abreaking/knative_event_display

---

kind: Service
apiVersion: v1
metadata:
  name: event-display
  namespace: event-ping
spec:
  selector:
    app: event-display
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080

```



确认：

```shell
$ kubectl get pods -n event-ping
NAME                             READY   STATUS    RESTARTS   AGE
event-display-5d78846b99-vzmn8   1/1     Running   0          12m

```









创建source

```
apiVersion: sources.knative.dev/v1
kind: PingSource
metadata:
  name: test-ping-source
  namespace: event-ping
spec:
  schedule: "*/1 * * * *"
  contentType: "application/json"
  data: '{"message": "Hello world for ping source!"}'
  sink:
    ref:
      apiVersion: v1
      kind: Service 
      name: event-display

```



确认：

```shell
$ kubectl get PingSource -n event-ping
NAME               SINK                                                SCHEDULE      AGE     READY   REASON
test-ping-source   http://event-display.event-ping.svc.cluster.local   */1 * * * *   8m13s   True  
```





查看日志

````
kubectl -n event-ping logs event-display-5d78846b99-vzmn8
或者：
kubectl -n event-ping logs -l app=event-display
````

输出日志：

```
☁  cloudevents.Event
Context Attributes,
  specversion: 1.0
  type: dev.knative.sources.ping
  source: /apis/v1/namespaces/event-ping/pingsources/test-ping-source
  id: 82b75735-a763-4aeb-b4e7-fc3554625ae3
  time: 2022-01-19T07:18:00.302467914Z
  datacontenttype: application/json
Data,
  {
    "message": "Hello world for ping source!"
  }

```







tips: 官方的文档有些地方已经过时了/



# 参考资料

[spec/primer.md at v1.0.1 · cloudevents/spec (github.com)](https://github.com/cloudevents/spec/blob/v1.0.1/primer.md)