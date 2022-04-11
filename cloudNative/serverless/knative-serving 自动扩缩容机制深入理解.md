knative-serving 自动扩缩容机制深入理解



# 关于自动伸缩

安装Knative-serving后，knative提供了开箱即用的快速、基于请求的自动扩缩容功能，这是通过使用 Knative Pod Autoscaler (KPA)提供的。

例如，如果一个应用程序没有接收到任何流量，并且将伸缩性调整为零，那么 Knative Serving 将应用程序缩放为零复制。如果禁用缩放到零，则应用程序将缩放到为集群上的应用程序指定的最小副本数量。如果应用程序的流量增加，副本将扩展以满足需求。

创建了knative 服务后，我们可以直接查看`podautoscaler`资源。

```shell
$ k get podautoscaler
NAME          DESIREDSCALE   ACTUALSCALE   READY   REASON
hello-world   3              3             True 
```



# 自动伸缩类型

knative支持两种扩缩容类型：

1. **KPA**（ Knative Pod Autoscale）：这个是Knative自带的自动扩缩容的实现，支持从0-1，1-n的扩缩容，并支持根据并发量来进行缩放，但是不支持基于CPU的进行缩放；
2. **HPA**（Kubernetes' Horizontal Pod Autoscaler ）：Kubenetes独立的组件，需要单独安装，只支持1-N的扩缩容，不支持0-1的伸缩，并支持基于CPU来进行缩放。



# KPA的原理

Knative Serving 为每个 POD 注入 QUEUE 代理容器 (queue-proxy)，该容器负责向 Autoscaler 报告用户容器并发指标。Autoscaler 接收到这些指标之后，会根据并发请求数及相应的算法，调整 Deployment 的 POD 数量，从而实现自动扩缩容。

![image-20220330161947946](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20220330161947946.png)


##  Queue Proxy

队列代理是一个sidecar容器，与每个用户容器中的用户容器一起部署。发送到应用程序实例的每个请求都首先通过队列代理，因此其名称为“代理”。

队列代理的主要目的是测量并限制用户应用程序的并发性。如果修订将并发限制定义为5，则队列代理可确保一次到达应用程序实例的请求不超过5个。如果发送给它的请求更多，它将在本地将它们排队，因此是其名称中的“队列”。队列代理还测量传入的请求负载，并在单独的端口上报告平均并发和每秒请求数。

## Autoscaler

Autoscaler是一个独立的Pod，包含三个主要组件：

1. **PodAutoscaler reconciler**
2. **Collector**
3. **Decider**

PodAutoscaler协调程序可确保正确获取对PodAutoscalers的任何更改（请参阅API部分），并将其反映在Decider，Collector或两者中。

Collector负责从应用程序实例上的队列代理收集度量。为此，它会刮擦其内部指标端点并对其求和，以得到代表整个系统的指标。为了实现可伸缩性，仅会抓取所有应用程序实例的一个样本，并将接收到的指标外推到整个集群。

Decider获得所有可用指标，并决定应将应用程序部署扩展到多少个Pod。基本上，要做的事情就是`want = concurrencyInSystem/targetConcurrencyPerInstance`。

除此之外，它还会针对修订版的最大缩放比例和最小实例数和最大实例数设置值进行调整。它还计算当前部署中还剩下多少突发容量，从而确定是否可以从数据路径中删除Activator。

## Activator

Activator是全局共享的部署，具有很高的可伸缩性。其主要目的是缓冲请求并向autoscaler报告指标。

Activator主要涉及从零到零的规模扩展以及容量感知负载平衡。当修订版本缩放到零实例时，Activator将被放置到数据路径中，而不是修订版本的实例中。如果请求将达到此修订版，则Activator将缓冲这些请求，并使用指标戳autoscaler并保留请求，直到出现应用程序实例。在这种情况下，Activator会立即将其缓冲的请求转发到新实例，同时小心避免使应用程序的现有实例过载。Activator在这里有效地充当负载平衡器。当它们可用时，它将负载分配到所有Pod上，并且不会在并发设置方面使它们过载。在系统认为合适的情况下，将Activator放置在数据路径上或从数据路径上取下，以使其充当如上所述的负载平衡器。如果当前部署具有足够的空间以使其不太容易过载，则将Activator从数据路径中删除，以将网络开销降至最低。

与队列代理不同，激活器通过Websocket连接主动将指标发送到autoscaler，以最大程度地减小从零开始的延迟。



# 指标

其实关于伸缩，无非就是两个问题，第一个是参照的指标是什么？CPU？内存？RPS？另外一个问题是伸缩的策略，也就是伸缩的数目。

knative提供了许多autoscaler可配置的指标，比如缩放上限、自动扩缩容类型、多少并发就扩容等等，这些指标的配置方式有两种：

1. 一种是我们直接在configmap里进行全局配置，该configmap在knative-serving的namespace下，查看config-autoscaler：

   ```yaml
   $ k -n knative-serving edit configmap config-autoscaler
   ```

   内容：

   ```yaml
   data:
     _example: |
     
       container-concurrency-target-percentage: "70"
       
       # 单个service pod支持的并发数量，默认100
       container-concurrency-target-default: "100"
       
       # 当将每秒请求数（RPS）用作修订的缩放指标，以及 修订版指定了无限制的RPS，autoscaler 将尝试去维护。即使指定了无限制的RPS，autoscaler将基于此目标RPS水平缩放应用程序。该值必须大于1.0。
       requests-per-second-target-default: "200"
       
       # 指定并发中突发的请求大小。如果当前服务的备用容量小于设定值，Autoscaler将通过引入请求器路径中的Activator来尝试保护系统免于排队。如果此设置为 0，则只当修订版缩放为0时，Activator位于请求路径中。如果此设置 > 0，并且container-concurrency-target-percentage为100％或1.0，则Activator将始终位于请求路径中。-1 表示无限的目标爆发容量，Activator将始终在请求路径中。其他负值无效。
       target-burst-capacity: "200"
       
       # 当在Stable模式运行中，Autoscaler在稳定窗口期下平均并发数下的操作
       stable-window: "60s"
       
       #  当观察到紧急窗口期间的平均并发达到目标并发的紧急阈值百分比，自动缩放进入紧急模式。
       # 在紧急模式下运行时，autoscaler在紧急情况窗口上按平均并发缩放稳定窗口的紧急窗口百分比。
       panic-window-percentage: "10.0"
       
       # 容器并发目标要达到的百分比，此时在紧急情况窗口内进入紧急状态。
       panic-threshold-percentage: "200.0"
       
       # 扩容时的最大扩展速率，即autoscaler的扩容Pod速率。
       max-scale-up-rate: "1000.0"
       
       #  缩容时的最大比例，即autoscaler的缩容Pod速率。
       max-scale-down-rate: "2.0"
       
       # 是否支持缩放到零，kpa默认是开启的
       enable-scale-to-zero: "true"
       
       # 表示在缩为0之前，inactive revison保留的运行时间
       scale-to-zero-grace-period: "30s"
       ....
   ...
   ```

   

2. 另一种是为每个revision进行配置（后面演示用这种方式）

   创建一个revision的yaml内容如下：

   ```yaml
   kind: Service
   metadata:
     name: hello
   spec:
     template:
       metadata:
         name: hello-world
         annotations:
           # 表示自动扩缩容使用kpa类型 ，也是Knative默认类型
           autoscaling.knative.dev/class: "kpa.autoscaling.knative.dev" 
           # 表示平均每个pod支持10个并发
           autoscaling.knative.dev/target: "10" 
           # 表示Knative service最小pods数为1
           autoscaling.knative.dev/minScale: "1"
           # 最大pods数为5
           autoscaling.knative.dev/maxScale: "5"
       spec:
         containers:
           - image: abreaking/helloworld-java
             imagePullPolicy: Never
             ports:
               - containerPort: 8080
             env:
               - name: TARGET
                 value: "World"
   ```

创建service 成功后，我们直接查看`podautoscaler`资源信息：

```shell
$ k get podautoscaler
NAME          DESIREDSCALE   ACTUALSCALE   READY   REASON
hello-world   1              1             True  
```

或者我们直接对`hello-world`的`podautoscaler`进行修改：

```
k edit podautoscaler hello-world
```

内容如下：

```yaml
apiVersion: autoscaling.internal.knative.dev/v1alpha1
kind: PodAutoscaler
metadata:
  annotations:
    autoscaling.knative.dev/class: kpa.autoscaling.knative.dev
    autoscaling.knative.dev/maxScale: "5"
    autoscaling.knative.dev/metric: concurrency
    autoscaling.knative.dev/minScale: "1"
    autoscaling.knative.dev/target: "10"
    serving.knative.dev/creator: minikube-user
....
```



> 还有一种operator方式进行指标的配置，暂时没有研究。



# 示例


如上，我们创建了一个Knative service，并设置了扩缩容边界，最小pod数1，最大为5，每个Pod支持10个并发。 现在来进行测试。

这里使用轻量级压测工具`hey`来进行测试

>hey的安装，执行以下命令即可：
>
>```
>go get -u github.com/rakyll/hey
>```



查看部署的knative service：

```shell
$ k get ksvc
NAME    URL                                           LATESTCREATED   LATESTREADY   READY   REASON
hello   http://hello.default.10.105.32.166.sslip.io   hello-world     hello-world   True 
```



 压测5秒，30个并发

```shell
$ hey -z 5s -c 30 http://hello.default.10.105.32.166.sslip.io
```

此时再查看pods数：

```shell
$ k get pods
NAME                                     READY   STATUS    RESTARTS   AGE
hello-world-deployment-c67d79887-ggzpt   2/2     Running   0          13m
hello-world-deployment-c67d79887-nzdn7   2/2     Running   0          50s
hello-world-deployment-c67d79887-qjxk7   2/2     Running   0          47s

```

30个并发/10（之前配置的并发数） = 3个pod，与预期一致。



过一段时间（30s）后，再查看pods数，发现只有一个了：

```shell
$ k get pods
NAME                                     READY   STATUS    RESTARTS   AGE
hello-world-deployment-c67d79887-ggzpt   2/2     Running   0          13m
```

这与我们设置的`minScale`一致。



提供并发，比如100个并发，

```
$ hey -z 5s -c 100 http://hello.default.10.105.32.166.sslip.io
```

再查看pods，发现只有5个pods，与我们设置的设置的`maxScale`一致。



# 算法

Autoscaler 基于每个 POD 的平均请求数（并发数）进行扩所容处理。默认并发数为 100。

**POD 数=并发请求总数/容器并发数**。

上述我们服务中并发数设置了 10，这时候如果加载了 30 个并发请求的服务，Autoscaler 就会创建了 3个 POD （30 个并发请求/10=POD）。

Autoscaler 实现了两种操作模式的缩放算法：Stable（稳定模式）和 Panic（恐慌模式）。



在stable模式下，Autoscaler 调整 Deployment 的大小，以实现每个 POD 所需的平均并发数。 POD 的并发数是根据 60 秒窗口内接收所有数据请求的平均数来计算得出。

此外，Autoscaler 还会计算 60 秒窗口内的平均并发数，即系统需要 1 分钟stable模式在所需的并发级别。但是，Autoscaler 也会计算 6 秒的panic窗口，如果该窗口达到目标并发的 2 倍，则会进入panic模式。在panic模式下，Autoscaler 在更短、更敏感的紧急窗口上工作。一旦紧急情况持续 60 秒后，Autoscaler 将返回初始的 60 秒稳定窗口。

```
                                                       |
                                  Panic Target--->  +--| 20
                                                    |  |
                                                    | <------Panic Window
                                                    |  |
       Stable Target--->  +-------------------------|--| 10   CONCURRENCY
                          |                         |  |
                          |                      <-----------Stable Window
                          |                         |  |
--------------------------+-------------------------+--+ 0
120                       60                           0
                     TIME
```



# 原理

**稳定模式下的扩缩**

![img](https://segmentfault.com/img/remote/1460000022665335)

在稳定状态下，autoascaler会不断抓取当前活动的修订包，以不断调整修订的规模。当请求流入系统时，被刮擦的值将发生变化，并且自动缩放器将指示修订版的部署遵循给定的缩放比例。

SKS通过私有服务跟踪部署规模的变化。它将相应地更新公共服务。

**scale 到 0**

![img](https://segmentfault.com/img/remote/1460000022665337)

一旦系统中不再有任何请求，修订版本就会缩放为零。从autoscaler到修订版容器的所有刮擦都返回0并发性，并且activator报告的并发性相同（1）。

在实际删除修订的最后一个pod之前，系统应确保activator在路径中并且可路由。首先决定将比例缩放为零的autoscaler会指示SKS使用代理模式，因此所有流量都将定向到activator（4.1）。现在将检查SKS的公共服务，直到确保它返回activator的响应为止。在这种情况下，如果已经过去了宽限期（可通过_`scale-to-zero-grace-period`_进行配置），则修订的最后一个pod将被删除，并且修订已成功缩放为零（5）。

**从 0 扩容**

![img](https://segmentfault.com/img/remote/1460000022665338)

如果修订版本缩放为零，并且系统中有一个试图达到该修订版本的请求，则系统需要将其扩展。当SKS处于代理模式时，请求将到达activator（1），activator将对其进行计数并将其报告给autoscaler（2.1）。然后，activator将缓冲请求，并监视SKS的专用服务以查看端点的出现（2.2）。

Aujtoscaler从activator获取度量，并立即运行自动缩放循环（3）。该过程将确定至少需要一个pod（4），autoscaler将指示修订的部署扩展到N> 0个副本（5.1）。它还将SKS置于“服务”模式，一旦流量上升（5.2），流量就会直接流到修订版的Pod。

activator最终会看到端点出现并开始对其进行探测。一旦探测成功通过，相应的地址将被认为是健康的，并用于路由我们缓冲的请求以及在此期间到达的所有其他请求（8.2）。

该revision已成功从零开始缩放。





# 参考资料

[跟我一起学Knative(4)--Serving 自动扩缩容 - SegmentFault:https://segmentfault.com/a/1190000022665332](https://segmentfault.com/a/1190000022665332?utm_source=sf-similar-article)

[knative官方文档-autoscaling: https://knative.dev/docs/serving/autoscaling/](https://knative.dev/docs/serving/autoscaling/)

[基于流量请求数实现服务自动扩缩容 (aliyun.com)](https://help.aliyun.com/document_detail/186137.html)

[从HPA到KPA：Knative自动扩缩容深度分析 - Ethfoo's Blog](http://ethfoo.xyz/posts/serverless/knative之自动扩缩容/)