knative-eventing 自定义事件源



# create an event source overview

需要定义三个组件（component）：

**接收适配器（Receive adapter）** ： 指定 如何从生产者接收事件，接收器（sink）的 uri是什么，以及如何将事件转为标准的CloudEvent格式。

**kubernetets控制器（Kubernetes controller）**：用于管理事件源，以及协调底层的接收适配器（Receive adapter）。

**自定义的资源(CRD)**：配置。根据提供的配置，controller用来管理接收适配器（Receive adapter）。



基本原理：

![image-20220120151907653](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20220120151907653.png)



官方提供了一个模板库 [sample-source](https://github.com/knative-sandbox/sample-source)，包括了基本的事件源控制器（ event source controller），以及接收适配器（Receive adapter）。

我们可以直接在github上，根据这个模板库来生成自己的[demo](https://github.com/aBreaking/knative-eventing-source-demo)。

* 实现了云事件（CloudEvent）绑定的事件，cloudevent's的提供了go语言的[sdk](https://github.com/cloudevents/sdk-go) ，可以根据需要来配置接口。
* 控制器（controller）运行时（这是我们通过注入共享的）将特定于协议的配置合并到“通用控制器(generic controller)”CRD 中。 



然后我们来看看代码，建议把demo clone到本地，在你的编辑器里打开。

代码的主要目录结构如下：

```go
sample-source
|-cmd  
 |-controller
 |-receice_adapter
 |-webhook
|-config
|-pkg
```

接收适配器的代码文件

`cmd/receive_adapter/main.go`：将资源变量转换成底层的适配器结构，以便它们可以传递到Knative系统里；

`pkg/adapter/adapter.go`：将接收到的事件转为标准的云事件（CloudEvents）。



控制器（Controller）的代码文件

源码里实现了一个NewController，

`cmd/controller/main.go`：将事件源的 NewController 实现传递给共享的 main 方法。 

`pkg/reconciler/sample/controller.go`： NewController 实现。 



CRD 文件

`pkg/apis/samples/v1alpha1/samplesource_types.go`（注意路径，这里官方文档有误）: 就是解析yaml文件，我们需要在yaml文件里定义哪些变量，在该类里进行定义。



功能性(Reconciler)的文件

`pkg/reconciler/sample/samplesource.go`: 适配器的协调功能；

`pkg/apis/samples/v1alpha1/samplesource_types.go`：包含事件源的协调详细信息的状态信息：资源就绪、提供sink、已部署、提供的事件类型、kubernetes资源正确/



首先是创建controller：

前面说了，controller的作用是：1. 管理事件源；2. 控制事件协调器，根据CRD的配置。





创建controller





参考资料：

[为 Serverless 平台插上腾飞的翅膀--自定义 Faas 事件驱动事件源 - mdnice 墨滴](https://www.mdnice.com/writing/837d669b80d644968001389150e76953)