# serverLess技术调研

# 摘要

## serverless

>  **广义的 Serverless 是指：构建和运行软件时不需要关心服务器的一种架构思想。** 虽然 Serverless 翻译过来是 “无服务器”，但这并不代表着应用运行不需要服务器，而是开发者不需要关心服务器。**而基于 Serverless 思想实现的软件架构就是 Serverless 架构。**
>
> from : [到底什么是 Serverless? - 知乎 (zhihu.com):https://zhuanlan.zhihu.com/p/404236938](https://zhuanlan.zhihu.com/p/404236938)

serverless的目标：基础设施越来越完善，不再关心系统管理，也不关心服务器运转、代码的部署和监控日志等，我们只关心怎样以最快的速度把代码变成Service。

>  Serverless 最基本的配套设施，包括底层资源的伸缩、编排，还有全栈的可观测性和服务治理。服务治理听着很重，SOA才需要这些，但今天有那么几个还是需要的：**服务注册发现、健康检查、配置、容错，最后还有流量**；如果不管理好流量，流量在对外API，也很难做。
>
> **可观测性**: 希望看到这样的关联图，从最前端的、直接服务于客户的API，到后面的Service，Service用了哪些中间件，这些中间件又运作在哪个资源上
>
> **健康检查**：快速故障定位和SLA的报告，容量分析，强调的是整体往下设计，说白了，就是干掉运维人员，开发人员就是运维人员。
>
> **流量管理**: ![image-20210915115353596](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20210915115353596.png)
>
> **服务注册发现**: 服务启动以后怎么注册，怎么被发现，支持灰度的多版本怎么做。一定要看服务视角，而不是资源视角，CMDB非常重要，但是一定是在服务，或者API视角
>
> ![image-20210915115512394](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20210915115512394.png)
>
> 
>
> from :[左耳朵耗子：Serverless 究竟是什么？ ](https://mp.weixin.qq.com/s?__biz=MzI2NDU4OTExOQ%3D%3D&chksm=eaa89ef9dddf17ef7c8d2efa066190eca5a6ec1bd7996308a41b5945093985ac628085020354&idx=1&mid=2247517353&scene=21&sn=d11d27ab406f7239e2cfbef7f163fc09#wechat_redirect)



整体解决方案参考：



整体架构图参考：

![image-20210915140047651](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20210915140047651.png)





![image-20210916180646936](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20210916180646936.png)

from : https://blog.csdn.net/yunqiinsight/article/details/114632379

 	

## FaaS:

FaaS拥有下面的特点：

1. FaaS里的应用逻辑单元都可以看作是一个函数，开发人员只关注如何实现这些逻辑，而不用提前考虑性能优化，让工作聚焦在这个函数里，而非应用整体。

2. FaaS是无状态的，天生满足云原生(Cloud Native App)应用该满足的12因子(12 Factors)中对状态的要求。无状态意味着本地内存、磁盘里的数据无法被后续的操作所使用。大部分的状态需要依赖于外部存储，比如数据库、网络存储等。

3. FaaS的函数应当可以快速启动执行，并拥有短暂的生命周期。函数在有限的时间里启动并处理任务，并在返回执行结果后终止。如果它执行时间超过了某个阈值，也应该被终止。

4. FaaS函数启动延时受很多因素的干扰。以AWS Lambda为例，如果采用了JS或Python实现了函数，它的启动时间一般不会超过10~100毫秒。但如果是实现在JVM上的函数，当遇到突发的大流量或者调用间隔过长的情况，启动时间会显著变长。

5. FaaS需要借助于API Gateway将请求的路由和对应的处理函数进行映射，并将响应结果代理返回给调用方。

**Fission**是一款基于Kubernetes的FaaS框架。通过Fission可以轻而易举地将函数发布成HTTP服务。它通过读取用户的源代码，抽象出容器镜像并执行。

平台整体设计：

FaaS平台的整体核心架构主要由网关、运行时容器、一站式运维发布平台、基础服务等组成：

![image-20210915143946721](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20210915143946721.png)

网关层主要负责接受函数调用请求，通过函数的唯一标识及函数的集群信息分发函数调用到对应集群的机器环境中执行。

函数容器层是整个系统的核心，主要通过函数执行引擎进行实例的调用执行，同时负责函数实例的生命周期管理，包括按需加载、代码预热、实例卸载回收等工作。

一站式发布运维平台（FaaS Platform）是面向开发者的主要操作平台，开发者在平台上进行函数编写、版本提交发布、回滚、监控运维等一系列工作。整个监控体系打通了集团的基础服务监控体系，,可以提供实时大盘，集群性能等基本监控指标的查询功能。

整个FaaS平台建立在集团中间件以及优酷内容分发依赖的各基础服务之上，通过良好的封装向开发者提供简洁的服务调用方式，同时函数本身的执行都是运行在互相隔离的环境中，通过统一的函数实例管理，进行函数的调度、执行监控、动态管理等。

整体技术栈服务端容器层主要是采用Java实现，结合中间件完成整个容器层的主要功能。

![image-20210915144941645](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20210915144941645.png)



from : https://baijiahao.baidu.com/s?id=1674248518601562853&wfr=spider&for=pc



FaaS 的入口是事件



# 开源项目

OpenFaaS：[https://github.com/openfaas/faas ](https://github.com/openfaas/faas )

# 参考资料

[到底什么是 Serverless? - 知乎 (zhihu.com):https://zhuanlan.zhihu.com/p/404236938](https://zhuanlan.zhihu.com/p/404236938)

[阿里云 Serverless Developer Meetup | 上海站-技术公开课-阿里云开发者社区 (aliyun.com):(https://developer.aliyun.com/live/246653](https://developer.aliyun.com/live/246653)

[Serverless + 低代码，让技术小白也能成为全栈工程师？_QcloudCommunity的博客-CSDN博客:https://blog.csdn.net/QcloudCommunity/article/details/118347455](https://blog.csdn.net/QcloudCommunity/article/details/118347455)

[左耳朵耗子：Serverless 究竟是什么？ ](https://mp.weixin.qq.com/s?__biz=MzI2NDU4OTExOQ%3D%3D&chksm=eaa89ef9dddf17ef7c8d2efa066190eca5a6ec1bd7996308a41b5945093985ac628085020354&idx=1&mid=2247517353&scene=21&sn=d11d27ab406f7239e2cfbef7f163fc09#wechat_redirect)

[苗立尧.《Faas，又一个未来？》:http://www.uml.org.cn/zjjs/202001023.asp](http://www.uml.org.cn/zjjs/202001023.asp)

[阿里技术.《如何落地一个FaaS平台》:https://baijiahao.baidu.com/s?id=1674248518601562853&wfr=spider&for=pc](https://baijiahao.baidu.com/s?id=1674248518601562853&wfr=spider&for=pc)

[函数计算 (aliyun.com):https://help.aliyun.com/product/50980.html](https://help.aliyun.com/product/50980.html)

[深入浅出 Serverless：优势、意义与应用 - 知乎 (zhihu.com):https://zhuanlan.zhihu.com/p/96656001](https://zhuanlan.zhihu.com/p/96656001)

[前言 · Knative 入门中文版 (servicemesher.com)](https://www.servicemesher.com/getting-started-with-knative/preface.html)