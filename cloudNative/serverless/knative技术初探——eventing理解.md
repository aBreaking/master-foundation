knative技术初探——Eventing理解



# 概述

Knative Eventing提供了事件路由的工具，可以将生产者（Producer）生成的事件（event）路由到指定的事件接收器（sink）。能够让开发者采用基于事件架构（[event-driven architecture](https://en.wikipedia.org/wiki/Event-driven_architecture) ）来构建应用。

>  事件的路由有点类似生产者-消费者模式。生产者产生事件，根据knative eventing的机制，支持对事件进行过滤，再发送到指定的消费者

>基于事件架构（event-driven architecture）这个是构建serverless应用的基础，也就是应用的代码（函数）通过事件来进行触发。



## 事件源Event Source

>An event source is a Kubernetes custom resource (CR), created by a developer or cluster administrator, that acts as a link between an event producer and an event *sink*. A sink can be a k8s service, including Knative Services, a Channel, or a Broker that receives events from an event source.
>
>事件源是kubernetes自定义资源（CR），由开发人员或管理人员创建，它的作用是连接事件生产者到事件接收者（sink）。事件接收者（sink）可以是knative service、channel、broker。

所以，基本上可以认为Event Source就是事件生产者（event producer），生产事件，然后发送到事件接收者（sink）那里去。



Event source是kubernetes自定义资源（CR），通过Source对象实例化CR来创建的。Source对象定义了实例化CR所需要的参数。

所有的Sources都是事件源`sources`的范畴。



包括以下Sources：

* [APIServerSource](https://knative.dev/docs/eventing/sources/apiserversource/)：kubernetes Api server自身的事件，可以直接带入到Knative中。比如kubernetes资源发生更新、删除时，APIServerSource 都会触发一个新事件；
* [ContainerSource](https://knative.dev/docs/eventing/custom-event-source/containersource/)：ContainerSource 可以实例化一些用来生成事件容器镜像，直到ContainerSource 被删除。 比如轮询一个FTP服务来判断是否有了新文件，或者自行设置一个定时器来生成事件。ContainerSource 会让这些事件在指定的pod上保持运行。 所以大部分情况我们可以使用ContainerSource 在自定义事件源。



可以使用如下命令，来查询有哪些`sources`：

```shell
kubectl get sources
```



## 事件接收者Sink

事件接收者Sink用来指定source产生的事件发送到哪里去。Sink可以是knative service、channel、broker。

Sink有两种类型对象：`Addressable`、`Callable`

* Addressable objects 有个`status.address.url`属性，用来定义事件接收者的地址，它接收和确认通过HTTP传送的事件。作为一种特殊情况，核心的 Kubernetes Service Object 也实现了 Addressable 接口。 

* Callable objects 能够接收通过Http传送的事件，并且能够对该事件做一些改造，然后在HTTP response里返回0个或者1个新的事件，返回的事件还可以发送到别的Sink再进一步处理。



## 事件通道Channel

通道Channel也是Kubernetes自定义资源（custom resources），它定义了一个单一的事件转发和持久层。

Channel提供了一种事件传递机制，可以通过订阅将接收到的事件发送到多个目的地或接收器（sink）。

![image-20211210163904404](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211210163904404.png)



## Broker和Trigger

Brokers也是Kubernetes自定义资源，用于定义用于收集 CloudEvent 池的事件网格（event mesh）。

Broker是事件的入口，Trigger则是分发事件。

![image-20211210171401793](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211210171401793.png)

事件进入Broker后，可以使用Trigger将其转发给订阅者。Trigger允许按属性过滤事件，以便可以将具有特定属性的事件发送给特定的订阅者。 



[Brokers](https://knative.dev/docs/eventing/broker/) and [Triggers](https://knative.dev/docs/eventing/broker/triggers/) 提供了一种叫做**"event mesh"**模型。事件生成者将事件发送到Broker，然后再通过Trigger 统一将这些事件分发给消费者（consumer）。

这种方式有以下好处：

* 消费者可以注册特定类型的事件，而无需直接与事件生产者协商。
*  底层平台可以使用指定的过滤条件优化事件路由。 



![image-20211116151452805](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211116151452805.png)

> 个人一开始对`Sink`的概念理解了好久，个人认为改图蓝色部分的`Sink`我认为还是叫做`Consumer`比较好一点。
>
> `Sink`字面意思叫做事件接收者，理论上来说`Broker`、`Channel`，都是Sink，即都是事件的接收者。
>
> 这个图上的`Sink`给人一种事件最终的接收者的意思，可以想象`生产者—消费者`模型，事件从`Source`里来，最终要到`Consumer`里去，所以该图的`Sink`可以看作事件最后的消费者（consumer）。
>
> 此外，Sink还有除了接收者的字面意思外，还有再对事件再次处理的功能，即Sink接收到事件，做了一层转换，再发送到下一个Sink里去，貌似这里也能说通。



# 自定义Eventing

getting-start之前，你得需要先安装[Knative-serving]([Knative1.0版本初探（一）—serving、istio的安装与函数部署 (abreaking.com)](https://blog.abreaking.com/article/156))以及[Knative-eventing]([knative1.0版本初探（二）—eventing安装与使用 (abreaking.com)](https://blog.abreaking.com/article/157))。

## 创建一个临时命令空间

创建一个学习用的命令空间`event-example`，把后续所有的例子全部都放在这个命令空间里。

```
kubectl create namespace event-example
```



## 创建broker

broker的作用就是：允许你将事件路由到不同的事件接收者，或者说事件消费者（consumer）。

创建一个名为`default`的broker。先写好`broker-default.yaml`文件，内容如下:

```yaml
apiVersion: eventing.knative.dev/v1
kind: Broker
metadata:
 name: default
 namespace: event-example
```

然后执行以下命令：

```shell
kubectl apply -f broker-default.yaml
```

此时，broker就创建好了，通过如下命令来确认：

```shell
$ kubectl -n event-example get broker
NAME      URL                                                                              AGE   READY   REASON
default   http://broker-ingress.knative-eventing.svc.cluster.local/event-example/default   36m   True    
```



`你会看到有个URL，后面我们就可以通过`HTTP`请求来作为事件源，手动调用该URL，来模拟事件的触发`



## 创建consumer

事件消费者（event consumers）可以认为就是knative的service，它肯定是一个程序（或者叫做函数），程序逻辑也就是一个输入输出过程。

这里我们创建两个consumer：`hello-display`、`goodbye-display`。用于接收不同类型的事件。

`hello-display.yaml`内容如下：

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: hello-display
  namespace: event-example
spec:
  replicas: 1
  selector:
    matchLabels: &labels
      app: hello-display
  template:
    metadata:
      labels: *labels
    spec:
      containers:
        - name: event-display
          image: gcr.io/knative-releases/knative.dev/eventing/cmd/event_display

---

kind: Service
apiVersion: v1
metadata:
  name: hello-display
  namespace: event-example
spec:
  selector:
    app: hello-display
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
```



`goodbye-display.yaml`内容如下：

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: goodbye-display
  namespace: event-example
spec:
  replicas: 1
  selector:
    matchLabels: &labels
      app: goodbye-display
  template:
    metadata:
      labels: *labels
    spec:
      containers:
        - name: event-display
          image: gcr.io/knative-releases/knative.dev/eventing/cmd/event_display

---

kind: Service
apiVersion: v1
metadata:
  name: goodbye-display
  namespace: event-example
spec:
  selector:
    app: goodbye-display
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
```



再分别执行`kubectl apply`命令：

```shell
$ kubectl apply -f hello-display.yaml
$ kubectl apply -f goodbye-display.yaml
```



再确认下consumer是不是创建成功了：

```shell
$ kubectl get pod -n event-example
NAME                               READY   STATUS    RESTARTS      AGE
goodbye-display-587698fdf9-wcxk2   1/1     Running   0             43m
hello-display-759947d684-rrlr8     1/1     Running   0             48m

```



>如果状态不是Running，那多半是镜像拉取的问题。或者参考[Debugging Guide](https://knative.dev/docs/eventing/troubleshooting/) 里的解决方式。



## 创建trigger

此时consumer创建了好了，那么自然而然就想到一个问题：从broker过来的事件（event），哪些应该到hello-display里？又哪些应该到goodbye-display里呢？

触发器（trigger）的作用就是提供一个过滤器（filter）的机制，根据云事件（Cloud Event）的上下文属性来选择相应的事件。

所以我们需要为每个consumer来创建trigger。

`hello-display`的触发器`hello-trigger.yaml`，内容如下：

```yaml
apiVersion: eventing.knative.dev/v1
kind: Trigger
metadata:
  name: hello-display
  namespace: event-example
spec:
  broker: default
  filter:
    attributes:
      type: greeting
  subscriber:
    ref:
     apiVersion: v1
     kind: Service
     name: hello-display
```

> `type: greeting`，也就是说需要属性 type为greeting的事件



`goodbye-display`的触发器`goodbye-trigger.yaml`，内容如下：

```yaml
apiVersion: eventing.knative.dev/v1
kind: Trigger
metadata:
  name: goodbye-display
  namespace: event-example
spec:
  broker: default
  filter:
    attributes:
      source: sendoff
  subscriber:
    ref:
     apiVersion: v1
     kind: Service
     name: goodbye-display
```

> `source: sendoff`，也就是说需要属性source为sendoff的事件



再分别执行`kubectl apply`命令：

```shell
$ kubectl apply -f hello-trigger.yaml
$ kubectl apply -f goodbye-trigger.yaml
```



此时可以通过如下命令来验证创建的trigger：

```shell
$ kubectl -n event-example get triggers
NAME                   READY   REASON   BROKER    SUBSCRIBER_URI                                                                 AGE
goodbye-display        True             default   http://goodbye-display.event-example.svc.cluster.local/                        9s
hello-display          True             default   http://hello-display.event-example.svc.cluster.local/                          16s
```



## 开始生成事件

万事俱备，现在就可以开始上手来手动生成事件。还记得之前创建的broker嘛？有个URL，我们直接可以通过HTTP请求的方式来制造事件。

但是有个注意项：broker只能在knative-eventing集群内部访问。

所以我们需要在集群内部创建一个pod，用这个pod来执行`curl`命令，用它作为事件的产生者（producer）。

创建该pod，`producer-curl.yaml`内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  labels:
    run: curl
  name: curl
  namespace: event-example
spec:
  containers:
    # This could be any image that we can SSH into and has curl.
  - image: radial/busyboxplus:curl
    imagePullPolicy: IfNotPresent
    name: curl
    resources: {}
    stdin: true
    terminationMessagePath: /dev/termination-log
    terminationMessagePolicy: File
    tty: true
```



然后：

```
kubectl apply -f producer-curl.yaml
```

再确认该pod是否正常，pod名为`curl`

```shell
$ kubectl -n event-example  get pod curl
NAME   READY   STATUS    RESTARTS      AGE
curl   1/1     Running   2 (53m ago)   59m

```



通过如下命令ssh进入该pod容器：

```
kubectl -n event-example attach curl -it
```



此时，就可以在该pod容器里来手动发起HTTP请求了。

* 发起`type: greeting`的请求：

```shell
curl -v "http://broker-ingress.knative-eventing.svc.cluster.local/event-example/default" \
  -X POST \
  -H "Ce-Id: say-hello" \
  -H "Ce-Specversion: 1.0" \
  -H "Ce-Type: greeting" \
  -H "Ce-Source: not-sendoff" \
  -H "Content-Type: application/json" \
  -d '{"msg":"Hello Knative!"}'
```



* 发起`source: sendoff` 的请求

```shell
curl -v "http://broker-ingress.knative-eventing.svc.cluster.local/event-example/default" \
  -X POST \
  -H "Ce-Id: say-goodbye" \
  -H "Ce-Specversion: 1.0" \
  -H "Ce-Type: not-greeting" \
  -H "Ce-Source: sendoff" \
  -H "Content-Type: application/json" \
  -d '{"msg":"Goodbye Knative!"}'
```



* 发起既是`type: grerting`，又是`source: sendoff`的请求

```shell
curl -v "http://broker-ingress.knative-eventing.svc.cluster.local/event-example/default" \
  -X POST \
  -H "Ce-Id: say-hello-goodbye" \
  -H "Ce-Specversion: 1.0" \
  -H "Ce-Type: greeting" \
  -H "Ce-Source: sendoff" \
  -H "Content-Type: application/json" \
  -d '{"msg":"Hello Knative! Goodbye Knative!"}'
```



每次curl的请求，都有`202 Accepted`的响应。如下：

```shell
< HTTP/1.1 202 Accepted
< Content-Length: 0
< Date: Mon, 12 Aug 2019 19:48:18 GMT
```



## 确认事件被接收

生成了事件，并且收到了正常了返回信息，此时可以验证一下consumer是不是正常消费了。



先用`exit`命令退出curl容器。



通过如下命令，来查看`hello-display`的日志：

```shell
$ kubectl -n event-example logs -l app=hello-display --tail=100
☁  cloudevents.Event
Context Attributes,
  specversion: 1.0
  type: greeting
  source: not-sendoff
  id: say-hello
  datacontenttype: application/json
Extensions,
  knativearrivaltime: 2021-12-02T02:25:17.267322666Z
Data,
  {
    "msg": "Hello Knative!"
  }
☁  cloudevents.Event
Context Attributes,
  specversion: 1.0
  type: greeting
  source: sendoff
  id: say-hello-goodbye
  datacontenttype: application/json
Extensions,
  knativearrivaltime: 2021-12-02T02:26:47.890642045Z
Data,
  {
    "msg": "Hello Knative! Goodbye Knative!"
  }

```



如法炮制，看下`goodbye-display`的日志：

```shell

$ kubectl -n event-example logs -l app=goodbye-display --tail=100
☁  cloudevents.Event
Context Attributes,
  specversion: 1.0
  type: not-greeting
  source: sendoff
  id: say-goodbye
  datacontenttype: application/json
Extensions,
  knativearrivaltime: 2021-12-02T02:26:04.310859112Z
Data,
  {
    "msg": "Goodbye Knative!"
  }
☁  cloudevents.Event
Context Attributes,
  specversion: 1.0
  type: greeting
  source: sendoff
  id: say-hello-goodbye
  datacontenttype: application/json
Extensions,
  knativearrivaltime: 2021-12-02T02:26:47.890642045Z
Data,
  {
    "msg": "Hello Knative! Goodbye Knative!"
  }

```



可以确认，跟我们发送的信息内容一致。





# 参考资料

[Eventing getting started - Knative](https://knative.dev/docs/eventing/getting-started/)

[https://skyao.io/learning-knative/eventing](https://skyao.io/learning-knative/eventing)

