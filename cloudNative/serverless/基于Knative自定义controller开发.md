基于Knative自定义controller开发



# Controller概述

## operator=crd+controller

kubernetes，我们可以自定义资源（CustomResourceDefinition，简称CRD）。比如kubernetes上常见的pod、service、deployment等都是CRD，只不过这些都是kubernetes系统级自带的CRD资源。

为了可以封装、管理和部署我们自定义的Kubernetes 应用，kubernetes提供了operator方式。Operator 是使用自定义资源（CR）管理应用及其组件的自定义 Kubernetes 控制器（Controller）。

> 注：operator貌似并不是kubernetes推出来的，只不过后来kubernetes接受了operator，具体过程可以自行百度。

简单的说：operator=crd+controller

从operator引申出来的方式比较多，比如go开发经常使用的[Kubebuilder](https://book.kubebuilder.io/cronjob-tutorial/basic-project.html)、java开发可使用的[fabric8io](https://github.com/fabric8io/kubernetes-client)。这两种方式都值得研究，不过由于kubernetes本身就是go语言开发的，可能用Kubebuilder会比较多一点。

此外呢，使用Knative来开发controller貌似更简单一些。



创建一个完整的CRD，一半需要有两部分：

1. crd资源内容如何定义？——CustomResource
2. 资源有增删改查的操作如何进行控制？—— Controller

所以，第2点就是Controller需要做的事情。



knative提供了更简单的controller开发框架，那么本文将介绍如何通过knative来自定义CRD和开发Controller。





## 为什么要自定义Controller开发



控制器的工作是确保对于任何给定的对象，世界的实际状态（包括集群状态，以及潜在的外部状态，如 Kubelet 的运行容器或云提供商的负载均衡器）与对象中的期望状态相匹配。每个控制器专注于一个根 Kind，但可能会与其他 Kind 交互。

我们把这个过程称为 **reconciling**。



在 controller-runtime 中，为特定种类实现 reconciling 的逻辑被称为 [*Reconciler*](https://pkg.go.dev/sigs.k8s.io/controller-runtime/pkg/reconcile)。 Reconciler 接受一个对象的名称，并返回我们是否需要再次尝试（例如在错误或周期性控制器的情况下，如 HorizontalPodAutoscaler）。

# 开发

## controller模板

knative官方提供了一个Controller开发的模板：[knative-sandbox/sample-controller](https://github.com/knative-sandbox/sample-controller)，只需要`user this template`即可：

![image-20220331161130646](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20220331161130646.png)



然后填写你自己的仓库名，就可以生成了controller的样例[代码 knative-sample-controller](https://github.com/aBreaking/knative-sample-controller)了。

生成代码的主要目录结构如下：

```yaml
knative-sample-controller
├── cmd # 包含 controller 和webhook 的入口 main 函数,以及生成 crd  的 schema 工具
│   ├── controller 
│   │   └── main.go # controller 的启动入口文件
│   ├── schema
│   │   └── main.go # 生成 CRD 资源的 工具
│   └── webhook
│       └── main.go # webhook 的入口文件
├── config # controller 和webhook 的部署文件（deploy role clusterrole 等等，此处省略）
│   ├── 300-addressableservice.yaml
│   ├── 300-simpledeployment.yaml
├── example-addressable-service.yaml # CR 资源的示例yaml
├── example-simple-deployment.yaml # CR 资源的示例yaml
├── hack # 是 程序自动生成代码的脚本，其中的 update-codegen.sh 最常用
│   ├── update-codegen.sh # 生成 informer，clientset，injection，lister的工具
│   ├── update-deps.sh
│   ├── update-k8s-deps.sh
│   └── verify-codegen.sh
├── pkg 
│   ├── apis # CRD 定义的 types 文件
│   │   └── samples 
│   │       ├── register.go
│   │       └── v1alpha1 # 此处需编写 CRD 资源的types
│   ├── client # 执行 hack/update-codegen.sh 后自动生成的文件
│   │   ├── clientset
│   │   ├── informers
│   │   ├── injection
│   │   └── listers
│   └── reconciler # 此处是控制器的主要逻辑，示例中实现了两个控制器，每个控制器包含主控制器入口（controller.go） 和对应的 reconcile 逻辑
│       ├── addressableservice
│       │   ├── addressableservice.go
│       │   └── controller.go
│       └── simpledeployment
│           ├── controller.go
│           └── simpledeployment.go
```



从代码上看，knative-sample-controller提供了两个默认的controller实现：addressableservice和simpledeployment。这里呢，我们来仿照simpledeployment来定义crd和实现controller 的编码。



## CRD定义




update-codegen.sh

这里需要一个操作系统的问题，如果你是在windows上开发，在linux上执行该脚本可能出错，

这个问题是因为不同系统之前换行符的问题。

需要使用dos2unix命令将，脚本内容格式化。





参考资料：

[如何从零开始编写一个Kubernetes CRD · Service Mesh](https://www.servicemesher.com/blog/kubernetes-crd-quick-start/)

[Extend the Kubernetes API with CustomResourceDefinitions | Kubernetes](https://kubernetes.io/docs/tasks/extend-kubernetes/custom-resources/custom-resource-definitions/)

[OperatorHub.io | The registry for Kubernetes Operators](https://operatorhub.io/)