CNCF serverless 白皮书 V1.0（中英文对照）

The current PDF version of the whitepaper can be found [here](https://github.com/cncf/wg-serverless/blob/master/whitepapers/serverless-overview/cncf_serverless_whitepaper_v1.0.pdf).

This paper describes a new model of cloud native computing enabled by emerging "serverless" architectures and their supporting platforms. It defines what serverless computing is, highlights use cases and successful examples of serverless computing, and shows how serverless computing differs from (and interrelates with) other cloud application development models such as Infrastructure-as-a-Service (IaaS), Platform-as-a-Service (PaaS), and container orchestration or Containers-as-a-Service (CaaS).

本文描述了新兴的“无服务器”架构及其支持平台所支持的云计算的新模型。它定义了什么是无服务计算，重点介绍了无服务计算的用例和成功实例，并展示了无服务计算与其他云应用程序开发模型(如 Infrastructure-as-a-Service (IaaS)、 Platform-as-a-Service (PaaS)和容器编排或容器作为服务(Containers-as-a-Service，caa)之间的区别(以及相互联系)。

This paper includes a logical description of the mechanics of a generic serverless platform with an associated programming model and message format, but it does not prescribe a standard. It introduces several industry serverless platforms and their capabilities, but it does not recommend a particular implementation.

本文对一般无服务器平台的机制进行了逻辑描述，并给出了相关的编程模型和消息格式，但没有规定标准。它介绍了几个行业无服务器平台及其功能，但并不推荐特定的实现。

It closes with a set of recommendations for the CNCF on how to advance the adoption of serverless computing for cloud-native computing.

文章最后为 CNCF 提出了一系列建议，指出如何推动在本地云计算中采用无服务器计算。

# Serverless Computing 无服务器计算

## What is serverless computing? 什么是无服务器计算？

Serverless computing refers to the concept of building and running applications that do not require server management. It describes a finer-grained deployment model where applications, bundled as one or more functions, are uploaded to a platform and then executed, scaled, and billed in response to the exact demand needed at the moment.

无服务器计算是指构建和运行不需要服务器管理的应用程序的概念。它描述了一个更细粒度的部署模型，其中捆绑为一个或多个功能的应用程序被上传到一个平台，然后根据当时的确切需求执行、缩放和计费。

Serverless computing does not mean that we no longer use servers to host and run code; nor does it mean that operations engineers are no longer required. Rather, it refers to the idea that consumers of serverless computing no longer need to spend time and resources on server provisioning, maintenance, updates, scaling, and capacity planning. Instead, all of these tasks and capabilities are handled by a serverless platform and are completely abstracted away from the developers and IT/operations teams. As a result, developers focus on writing their applications’ business logic. Operations engineers are able to elevate their focus to more business critical tasks.

无服务器计算并不意味着我们不再使用服务器来托管和运行代码; 也不意味着不再需要操作工程师。相反，它指的是无服务器计算的消费者不再需要在服务器供应、维护、更新、扩展和容量规划上花费时间和资源。相反，所有这些任务和功能都由无服务器平台处理，并且完全从开发人员和 IT/操作团队中抽象出来。因此，开发人员专注于编写应用程序的业务逻辑。运营工程师能够将他们的注意力提升到更重要的业务任务上。

There are two primary serverless personas:

有两个主要的无服务器角色:

1. **Developer**: writes code for, and benefits from the serverless platform which provides them the point of view that there are no servers nor that their code is always running.

   开发人员: 为无服务器平台编写代码，并从中受益，因为无服务器平台为他们提供了这样一种观点: 没有服务器，他们的代码也总是在运行。

2. **Provider**: deploys the serverless platform for an external or internal customer.

   提供者: 为外部或内部客户部署无服务器平台。

Servers are still required to run a serverless platform. The **provider** will need to manage servers (or virtual machines or containers). The provider will have some cost for running the platform, even when idle. A self-hosted system can still be considered serverless: typically one team acts as the **provider** and another as the **developer**.

运行无服务器平台仍然需要服务器。提供者将需要管理服务器(或虚拟机或容器)。即使在空闲状态下，运行平台也需要一定的成本。自托管的系统仍然可以被认为是无服务器的: 通常一个团队充当提供者，另一个团队充当开发者。

A serverless computing platform may provide one or both of the following:

无服务器计算平台可以提供以下一种或两种:

1. Functions-as-a-Service (FaaS), which typically provides event-driven computing. Developers run and manage application code with functions that are triggered by events or HTTP requests. Developers deploy small units of code to the FaaS, which are executed as needed as discrete actions, scaling without the need to manage servers or any other underlying infrastructure.
2. 作为服务的函数(Functions-as-a-Service，FaaS) ，它通常提供事件驱动的计算。开发人员使用由事件或 HTTP 请求触发的函数运行和管理应用程序代码。开发人员将小量代码部署到 FaaS，这些代码可以根据需要作为离散操作执行，无需管理服务器或任何其他底层基础设施。
3. Backend-as-a-Service (BaaS), which are third-party API-based services that replace core subsets of functionality in an application. Because those APIs are provided as a service that auto-scales and operates transparently, this appears to the developer to be serverless.
4. 后端即服务(Backend-as-a-Service，BaaS) ，它是基于第三方 api 的服务，用于替换应用程序中的核心功能子集。因为这些 api 是作为自动扩展和透明操作的服务提供的，所以开发人员认为这是无服务器的。

Serverless products or platforms deliver the following benefits to developers:

无服务器产品或平台为开发者提供以下好处:

1. **Zero Server Ops**: Serverless dramatically changes the cost model of running software applications through eliminating the overhead involved in the maintenance of server resources.

   零服务器运维: Serverless 通过消除服务器资源维护所涉及的开销，极大地改变了运行软件应用程序的成本模型。

   - **No provisioning, updating, and managing server infrastructure.** Managing servers, virtual machines and containers is a significant overhead expense for companies when one includes headcount, tools, training, and time. Serverless vastly reduces this kind of expense.

     无需配置、更新和管理服务器基础设施。对于公司来说，管理服务器、虚拟机和容器是一笔巨大的管理费用，包括人员、工具、培训和时间。无服务器大大减少了这种费用。

   - **Flexible Scalability:** A serverless FaaS or BaaS product can instantly and precisely scale to handle each individual incoming request. For the developer, serverless platforms have no concept of "pre-planned capacity," nor do they require configuring “auto-scaling” triggers or rules. The scaling occurs automatically without intervention from the developer. Upon completion of the request processing, the serverless FaaS automatically scales down the compute resources so that there is never idle capacity.

     灵活的可伸缩性: 一个无服务器的 FaaS 或 BaaS 产品可以即时和精确地进行扩展，以处理每个传入的请求。对于开发人员，无服务器平台没有“预先计划的容量”概念，也不需要配置“自动伸缩”触发器或规则。扩展自动发生，不需要开发人员的干预。在请求处理完成之后，无服务器的 faa 会自动缩小计算资源，这样就不会有空闲容量。

2. **No Compute Cost When Idle**: One of the greatest benefits of serverless products from a consumer perspective is that there are no costs resulting from idle capacity. For example, serverless compute services do not charge for idle virtual machines or containers; in other words, there is no charge when code is not running or no meaningful work is being done. For databases, there is no charge for database engine capacity waiting idly for queries. Of course this does not include other costs such as stateful storage costs or added capabilities/functionality/feature set.

   空闲时不需要计算成本: 从消费者的角度来看，无服务器产品的最大好处之一是不需要因空闲容量而产生成本。例如，无服务器的计算服务不对空闲的虚拟机或容器收费; 换句话说，当代码不运行或没有完成有意义的工作时，不收费。对于数据库，对于等待查询的数据库引擎容量不收费。当然，这不包括其他成本，如有状态存储成本或添加的功能/功能/特性集。

## A short history of serverless technology 无服务器技术的简短历史

While the idea of an on-demand, or "pay as you go," model can be traced back to 2006 and a platform called Zimki, one of the first uses of the term serverless is from Iron.io in 2012 with their IronWorker product, a container-based distributed work-on-demand platform.

尽管按需付费(on-demand，或称“ pay as you go”)的概念可以追溯到2006年，还有一个名为 Zimki 的平台，但“无服务器”(serverless)这个术语的最早使用者之一是2012年 Iron.io 公司推出的 IronWorker 产品，这是一个基于容器的分布式按需工作平台。

There have since been more serverless implementations in both public and private cloud. First there were BaaS offerings, such as Parse in 2011 and Firebase in 2012 (acquired by Facebook and Google, respectively). In November 2014, [AWS Lambda](https://aws.amazon.com/lambda/) was launched, and early 2016 saw announcements for [IBM OpenWhisk on Bluemix](https://www.ibm.com/cloud-computing/bluemix/openwhisk) (now IBM Cloud Functions, with the core open source project governed as [Apache OpenWhisk](http://openwhisk.incubator.apache.org/)), [Google Cloud Functions](https://cloud.google.com/functions/), and [Microsoft Azure Functions](https://azure.microsoft.com/services/functions/). [Huawei Function Stage](http://www.huaweicloud.com/product/functionstage.html) launched in 2017. There are also numerous open source serverless frameworks. Each of the frameworks, both public and private, have unique sets of language runtimes and services for handling events and processing data.

此后，公共和私有云中出现了更多的无服务器实现。首先是 BaaS 产品，比如2011年的 Parse 和2012年的 Firebase (分别被 Facebook 和谷歌收购)。2014年11月，AWS Lambda 发布，2016年初，IBM OpenWhisk on Bluemix (现在的 IBM 云功能，其核心开源项目是 Apache OpenWhisk)、谷歌云功能和微软 Azure 功能发布。华为功能舞台于2017年推出。还有许多开源的无服务器框架。每个框架(包括公共框架和私有框架)都有独特的语言运行时和服务集，用于处理事件和数据。

These are just a few examples; for a more complete and up-to-date list see the [Serverless Landscape](https://docs.google.com/spreadsheets/d/10rSQ8rMhYDgf_ib3n6kfzwEuoE88qr0amUPRxKbwVCk/edit#gid=0) document. The [Detail View: Serverless Processing Model](https://github.com/cncf/wg-serverless/tree/master/whitepapers/serverless-overview#heading=h.vli9umq7mfhe) section contains more detail about the entire FaaS model.

这些只是一些例子; 有关更完整和最新的列表，请参阅无服务器景观文档。详细视图: 无服务器处理模型部分包含有关整个 FaaS 模型的更多细节。

## Serverless use cases 无服务器用例

While serverless computing is widely available, it is still relatively new. In general, a serverless approach should be considered a top choice when the workload is:

虽然无服务器计算广泛可用，但它仍然是相对较新的。一般来说，当工作负载为:

- Asynchronous, concurrent, easy to parallelize into independent units of work

  异步的、并发的、易于并行化为独立的工作单元

- Infrequent or has sporadic demand, with large, unpredictable variance in scaling requirements

  不经常或有零星的需求，与大的，不可预测的变化，在伸缩需求

- Stateless, ephemeral, without a major need for instantaneous cold start time

  无状态，短暂，不需要即时的冷启动时间

- Highly dynamic in terms of changing business requirements that drive a need for accelerated developer velocity

  高度动态的业务需求变化驱动了对加速开发人员速度的需求

For example, for common HTTP-based applications, there are clear upsides in terms of automated scale and a finer-grained cost model. That said, there can be some tradeoffs in using a serverless platform. For example, function startup after a period of inactivity may result in performance declines if the number of instances of the function drops down to zero. Therefore, choosing whether to adopt a serverless architecture requires a careful look at both the functional and nonfunctional aspects of the compute model.

例如，对于常见的基于 http 的应用程序，在自动化规模和细粒度成本模型方面有明显的好处。也就是说，使用无服务器平台可能会有一些折衷。例如，在一段时间的不活动之后，如果函数的数量下降到零，那么函数的启动可能会导致性能下降。因此，选择是否采用无服务器架构需要仔细考虑计算模型的功能和非功能方面。

Non-HTTP-centric and non-elastic scale workloads that weren’t good fits for an IaaS, PaaS, or CaaS solution can now take advantage of the on-demand nature and efficient cost model of a serverless architecture. Some of these workloads include:

非以 http 为中心和非弹性规模的工作负载不适合 IaaS、 PaaS 或 CaaS 解决方案，现在可以利用无服务器体系结构的按需特性和高效的成本模型。其中一些工作量包括:

- Executing logic in response to database changes (insert, update, trigger, delete)

  执行逻辑以响应数据库更改(插入、更新、触发器、删除)

- Performing analytics on IoT sensor input messages (such as MQTT messages)

  对物联网传感器输入消息(如 MQTT 消息)进行分析

- Handling stream processing (analyzing or modifying data in motion)

  处理流处理(在运动中分析或修改数据)

- Managing single time extract, transform, and load jobs that require a lot of processing for a short time (ETL)

  管理短时间内需要大量处理的单次提取、转换和加载作业(ETL)

- Providing cognitive computing via a chatbot interface (asynchronous, but correlated)

  通过聊天机器人(chatbot)接口提供认知计算(异步的，但是相关的)

- Scheduling tasks performed for a short time, such as cron or batch style invocations

  调度短时间内执行的任务，如 cron 或批处理样式的调用

- Serving machine learning and AI models (retrieving one or more data elements such as tables, NLP, or images and matching against a pre-learned data model to identify text, faces, anomalies, etc.)

  服务于机器学习和人工智能模型(检索一个或多个数据元素，如表格、 NLP 或图像，并根据预先学习的数据模型进行匹配，以识别文本、面孔、异常等)

- Continuous integration pipelines that provision resources for build jobs on-demand, instead of keeping a pool of build worker hosts waiting for jobs to be dispatched

  持续集成管道(Continuous integration pipeline)为按需构建作业提供资源，而不是保持一个构建工作主机池，等待作业被分派

This section describes existing and emerging workloads and use cases where serverless architectures excels. It also includes details on early results, patterns, and best practices distilled from early success stories.

本节描述无服务器架构的优势所在的现有和新出现的工作负载和用例。它还包括从早期成功故事中提炼出来的早期结果、模式和最佳实践的细节。

Each of these scenarios show how serverless architectures have addressed a technical problem where it would be inefficient or impossible with IaaS, PaaS, or CaaS. These examples:

每个场景都显示了无服务器架构是如何解决技术问题的，即 IaaS、 PaaS 或 caa 效率低下或不可能解决的问题。这些例子:

- Solved a brand new problem efficiently where an on-demand model wasn’t available

  有效地解决了一个全新的问题，在这个问题上没有按需模式

- Solved a traditional cloud problem much more efficiently (performance, cost)

  更有效地解决了传统的云问题(性能、成本)

- Showed a dimension of "largeness", whether in size of data processed or requests handled

  显示了“巨大”的维度，无论是处理的数据的大小还是处理的请求

- Showed resilience by scaling automatically (up and down) with a low error rate

  通过自动缩放(上下)显示了弹性，错误率很低

- Brought a solution to market much faster than previously possible (days to hours)

  将解决方案推向市场的速度比以前快得多(天到小时)

The workloads listed in this section can be run on a public cloud (hosted serverless platform), on premises, or at the edge.

本节中列出的工作负载可以在公共云(托管的无服务器平台)、前提或边缘上运行。

### Multimedia processing 多媒体处理

A common use case, and one of the earliest to crystallize, is the implementation of functions that execute some transformational process in response to a new file upload. For example, if an image is uploaded to an object storage service such as Amazon S3, that event triggers a function to create a thumbnail version of the image and store it back to another object storage bucket or Database-as-a-Service. This is an example of a fairly atomic, parallelizable compute task that runs infrequently and scales in response to demand.

一个常见的用例(也是最早具体化的用例之一)是执行某些转换过程以响应新文件上载的函数的实现。例如，如果将一个图像上传到一个对象存储服务(如 Amazon S3) ，该事件将触发一个函数，以创建该图像的缩略版，并将其存储回另一个对象存储桶或 Database-as-a-Service。这是一个相当原子化的、可并行化的计算任务的示例，该任务不经常运行，并根据需求进行扩展。

Examples include:

例子包括:

- [Santander](https://www.google.com/url?q=https://www.slideshare.net/DanielKrook/optimize-existing-banking-applications-and-build-new-ones-faster-with-ibm-cloud-functions&sa=D&ust=1515189586080000&usg=AFQjCNHFOCjEEqR4s6ZzkCO3Wy0t79wfOw) built a proof of concept using serverless functions to process mobile check deposits using optical character recognition. This type of workload is quite variable, and processing demand on payday—once every two weeks—can be several times larger than the most idle time of the pay period.

  桑坦德银行建立了一个概念验证，使用无服务器功能处理移动支票存款使用光学字符识别。这种类型的工作量变化很大，在发薪日处理需求(每两周一次)的工作量可能比发薪期最多的空闲时间多几倍。

- Categorizing a film automatically by [passing each video frame through an image recognition service](https://github.com/IBM-Bluemix/openwhisk-darkvisionapp) to extract actor, sentiment, and objects; or processing drone footage of a disaster area to estimate the extent of damage.

  通过图像识别服务将每个视频帧自动分类，以提取演员、情绪和物体; 或者处理灾区的无人机镜头以估计损失程度。

### Database changes or change data capture (CDC) 数据库更改或更改数据捕获(CDC)

In this scenario, a function is invoked when data is inserted, modified, or deleted from a database. In this case, it functions similarly to a traditional SQL trigger, almost like a side effect or action parallel to the main synchronous flow. The effect is to execute an asynchronous piece of logic that can modify something within that same database (such as logging to an audit table), or in turn invoke an external service (such as sending an email) or updating an additional database such as in the case of DB CDC (change data capture) use case. These use cases can vary in their frequency and need for atomicity and consistency due to business need and distribution of services that handle the changes.

在此场景中，当数据从数据库中插入、修改或删除时，将调用函数。在这种情况下，它的功能类似于传统的 SQL 触发器，几乎像是与主同步流并行的副作用或操作。其效果是执行一个异步逻辑片段，这个逻辑片段可以修改同一个数据库中的某些内容(比如将日志记录到审计表中) ，或者反过来调用一个外部服务(比如发送电子邮件) ，或者更新一个额外的数据库，比如 dbcdc (更改数据捕获)用例。由于业务需求和处理更改的服务的分布，这些用例的频率和对原子性和一致性的需求可能会有所不同。

Examples include:

例子包括:

- Auditing changes to a database, or ensuring that they meet a particular quality or analytics standard for acceptable changes.

  审核数据库的更改，或确保它们满足可接受更改的特定质量或分析标准。

- Automatically translating data to another language as or shortly after it’s entered.

  输入数据时或数据输入后不久自动将数据翻译成另一种语言。

### IoT sensor input messages 物联网传感器输入信息

With the explosion of autonomous devices connected to networks comes additional traffic that is both massive in volume and uses lighter-weight protocols than HTTP. Efficient cloud services must be able to quickly respond to messages and scale in response to their proliferation or sudden influx of messages. Serverless functions can efficiently manage and filter MQTT messages from IoT devices. They can both scale elastically and shield other services downstream from the load.

随着连接到网络上的自治设备的爆炸式增长，附加的流量不仅数量巨大，而且使用比 HTTP 更轻量级的协议。高效的云服务必须能够快速响应消息，并针对消息的激增或突然涌入进行扩展。无服务器功能可以有效地管理和过滤来自物联网设备的 MQTT 消息。它们既能弹性地伸缩，又能保护下游的其他服务不受负载影响。

Examples include:

例子包括:

- GreenQ’s sanitation use case (the Internet of Garbage) where the [truck pickup route was optimized](https://www.wired.com/2014/05/how-the-internet-of-garbage-cans-will-remake-our-future-cities/) based on the relative fullness of trash receptacles.

  GreenQ 的卫生用例(垃圾互联网) ，其中卡车拾取路线优化的基础上相对丰满的垃圾容器。

- Using serverless on an IoT device (like [AWS Greengrass](https://aws.amazon.com/greengrass/)) to collect local sensor data, normalize it, compare with triggers, and push events up to an aggregation unit/cloud.

  在物联网设备上使用无服务器(如 AWS Greengrass)收集本地传感器数据，将其规范化，与触发器进行比较，并将事件推送到聚合单元/云。

### Stream processing at scale 大规模的流处理

Another non-transactional, non-request/response type of workload is processing data within a potentially infinite stream of messages. Functions can be connected to a source of messages that must each be read and processed from an event stream. Given the high performance, highly elastic, and compute intensive processing workload, this can be an important fit for serverless. In many cases, stream processing requires comparing data against a set of context objects (in a NoSQL or in-mem DB) or aggregating and storing data from streams into a object or a database system.

另一种非事务性、非请求/响应类型的工作负载是在可能无限的消息流中处理数据。函数可以连接到必须从事件流中读取和处理每个函数的消息源。鉴于高性能、高弹性和计算密集型处理工作负载，这非常适合于无服务器。在许多情况下，流处理需要将数据与一组上下文对象(在 NoSQL 或 In-mem DB 中)进行比较，或者将流中的数据聚合并存储到对象或数据库系统中。

Examples include:

例子包括:

- Mike Roberts has a good [Java/AWS Kinesis example](https://martinfowler.com/articles/serverless.html) handling billions of messages efficiently.

  Mike Roberts 有一个很好的 Java/AWS Kinesis 示例，可以高效地处理数十亿条消息。

- SnapChat uses [serverless on Google AppEngine](https://www.recode.net/2017/3/1/14661126/snap-snapchat-ipo-spending-2-billion-google-cloud) to process messages.

  使用 Google AppEngine 上的无服务器来处理消息。

### Chat bots 聊天程序

Interacting with humans doesn’t necessarily require millisecond response time, and in many ways a bot that replies to humans may actually benefit from a slight delay to make the conversation feel more natural. Because of this, a degree of initial latency from waiting for the function to be loaded from a cold start may be acceptable. A bot may also need to be extremely scalable when added to a popular social network like Facebook, WhatsApp, or Slack, so pre-provisioning an always-on daemon in a PaaS or IaaS model in anticipation of sudden or peak demand may not be as efficient or cost-effective as a serverless approach.

与人类互动并不一定需要毫秒级的响应时间，而且在许多方面，一个回应人类的机器人实际上可能受益于轻微的延迟，从而使对话更加自然。因此，从冷启动开始等待加载函数所产生的一定程度的初始延迟是可以接受的。当添加到 Facebook、 WhatsApp 或 Slack 等流行的社交网络时，bot 可能还需要具有极强的可伸缩性，因此在 PaaS 或 IaaS 模型中预先提供一个始终在线的守护进程，以应对突然或高峰的需求，可能不如无服务器方法那么有效或具有成本效益。

Examples include:

例子包括:

- Support and sales bots that are plugged into large social media services such as Facebook or other high traffic sites.

  支持和销售机器人，插入大型社会媒体服务，如 Facebook 或其他高流量网站。

- Messaging app Wuu uses Google Cloud Functions to enable users to [create and share content that disappears](https://firebase.google.com/docs/functions/case-studies/wuu.pdf) in hours or seconds.

  即时通讯软件 Wuu 使用 Google Cloud Functions 来让用户创建和分享在几小时或几秒钟内就消失的内容。

- See also the HTTP REST APIs and web applications below.

  参见下面的 HTTP REST api 和 web 应用程序。

### Batch jobs or scheduled tasks 批处理作业或计划任务

Jobs that require intense parallel computation, IO, or network access for only a few minutes a day in an asynchronous manner can be a great fit for serverless. Jobs can consume the resources they need efficiently for the time they run in an elastic manner, and not incur resource costs for the rest of the day when they are not used.

需要高强度并行计算、 i/o 或网络访问的作业，每天只需以异步方式进行几分钟，非常适合无服务器的作业。作业可以以弹性的方式有效地消耗它们在运行期间所需的资源，而不会在不使用它们的剩余时间里产生资源成本。

Examples include:

例子包括:

- A scheduled task could be a backup job that runs every night.

  计划的任务可以是每晚运行的备份任务。

- Jobs that send many emails in parallel scale out function instances.

  以并行扩展功能实例发送许多电子邮件的作业。

### HTTP REST APIs and web applications 和 web 应用程序

Traditional request/response workloads are still quite a good fit for serverless whether the workload is a static web site or one that uses a programming language like JavaScript or Python to generate a response on demand. Even though they may incur a startup cost for the first user, there is precedent for that type of delay in other compute models, such as the initial compilation of a JavaServer Page into a servlet, or starting up a new JVM to handle additional load. The benefit is that individual REST calls (each of the 4 GET, POST, UPDATE, and DELETE endpoints in a microservice, for example) can scale independently and be billed separately, even if they share a common data backend.

无论工作负载是静态网站还是使用诸如 JavaScript 或 Python 之类的编程语言根据需要生成响应的网站，传统的请求/响应工作负载仍然非常适合无服务器。尽管第一个用户可能会产生启动成本，但在其他计算模型中也存在这种类型的延迟，比如将 JavaServer Page 初始编译成 servlet，或者启动一个新的 JVM 来处理额外的负载。这样做的好处是，单个 REST 调用(例如，微服务中的4个 GET、 POST、 UPDATE 和 DELETE 端点中的每一个)可以独立扩展，并且可以单独计费，即使它们共享一个公共的数据后端。

Examples include:

例子包括:

- [Australian census ported to a serverless architecture shows speed of development, cost improvements, and autoscaling](https://medium.com/serverless-stories/challenge-accepted-building-a-better-australian-census-site-with-serverless-architecture-c5d3ad836bfa).

  澳大利亚移植到无服务器架构的人口普查显示了开发速度，成本改进和自动扩展。

- ["How I cut my AWS bill by 90% by going serverless."](https://medium.freecodecamp.org/how-i-cut-my-aws-bill-by-90-35c937596f0c)

  “我是如何通过无服务的方式将 AWS 账单削减了90% 。”

- AutoDesk example: ["Costs a small fraction (~1%) of the traditional cloud approach."](https://www.infoq.com/news/2016/08/serverless-autodesk)

  AutoDesk 的例子: “成本只是传统云方法的一小部分(约1%)。”

- Online coding/education (exam, test, etc.) runs exercise code in an event-driven environment, and provides feedback to the user based on a comparison with expected results for that exercise. The serverless platform runs the answer-checking on demand and scale as needed, paying for only the time during which code is running.

  在线编码/教育(考试、测试等)在事件驱动的环境中运行练习代码，并根据与练习预期结果的比较向用户提供反馈。无服务器平台根据需要运行应答检查和规模检查，只支付代码运行期间的费用。

### Mobile backends 移动后端

Using serverless for mobile backend tasks is also attractive. It allows developers to build on the REST API backend workload above the BaaS APIs, so they can spend time optimizing a mobile app and less on scaling its backend. Examples include: optimizing graphics for a video game and not investing in servers when the game becomes a viral hit; or for consumer business applications that need quick iterations to find product/market fit, or when time-to-market is critical. Another example is in batching notifications to users or processing other asynchronous tasks for an offline-first experience.

对移动后端任务使用无服务器也很有吸引力。它允许开发人员在停止 API 后端工作负载之上构建 BaaS API，这样他们就可以花时间优化移动应用程序，而减少缩放后端的时间。例子包括: 优化视频游戏的图形，当游戏成为病毒式传播时不投资服务器; 或者对于需要快速迭代来寻找产品/市场匹配的消费者商业应用程序，或者当上市时间至关重要时。另一个例子是将通知批处理给用户，或者处理其他异步任务以获得脱机优先体验。

Examples include:

例子包括:

- Mobile apps that need a small amount of server-side logic; developers can focus their effort on native code development.

  需要少量服务器端逻辑的移动应用程序; 开发人员可以将精力集中在本地代码开发上。

- Mobile apps that use direct-from-mobile access to BaaS using configured security policy, such as Firebase Auth/Rules or Amazon Cognito, with event-triggered serverless compute.

  使用配置的安全策略(如 Firebase Auth/Rules 或 Amazon Cognito)从移动设备直接访问 baa 的移动应用程序，带有事件触发的无服务器计算。

- "Throwaway" or short-term use mobile applications, such as the scheduling app for a large conference, that has very little demand on the weekends before and after the conference, but needs to scale up and down greatly; surges post-keynote based on schedule viewing demands over the course of the event on Monday and Tuesday mornings, then back down at midnight those days.

  “一次性”或短期使用的移动应用程序，比如大型会议的日程安排应用程序，在会前和会后的周末几乎没有什么需求，但需要进行大幅度的扩展和缩减; 在会议期间的周一和周二上午，基于日程安排的主题演讲后观看需求激增，然后在那些日子的午夜回落。

### Business Logic 业务逻辑

The orchestration of microservice workloads that execute a series of steps in a business process is another good use case for serverless computing when deployed in conjunction with a management and coordination function. Functions that perform specific business logic such as order request and approval, stock trade processing, etc. can be scheduled and coordinated with a stateful manager. Event requests from client portals can be serviced by such a coordination function and delivered to appropriate serverless functions.

在业务流程中执行一系列步骤的微服务工作负载的编排是结合管理和协调功能部署的无服务计算的另一个很好的用例。执行特定业务逻辑(如订单请求和批准、库存交易处理等)的函数可以通过有状态管理器进行调度和协调。客户端门户的事件请求可以通过这种协调功能得到服务，并交付给适当的无服务器功能。

Examples include:

例子包括:

A trading desk that handles stock market transactions and processes trade orders and confirmations from a client. The orchestrator manages the trades using a graph of states. An initial state accepts a trade request from a client portal and delivers the request to a microservice function to parse the request and authenticate the client. Subsequent states steer the workflows based on a buy or sell transaction, validate fund balances, ticker, etc. and send a confirmation to the client. On receipt of a confirmation request event from the client, follow-on states invoke functions that manage execution of the trade, update the account, and notify the client of the completion of the transaction.

处理股票市场交易和处理客户交易指令和确认的交易部门。协调器使用状态图来管理交易。初始状态接受来自客户端门户的交易请求，并将请求传递给微服务函数，以解析请求并对客户端进行身份验证。随后的国家基于买卖交易引导工作流程，验证资金余额、股票报价器等，并向客户发送确认信息。在收到客户端的确认请求事件后，后续状态调用管理交易执行、更新账户并通知客户端交易已完成的功能。

### Continuous Integration Pipeline 持续集成流水线

A traditional CI pipeline includes a pool of build worker hosts waiting idle for jobs to be dispatched. Serverless is a good pattern to remove the need for pre-provisioned hosts and reduce costs. Build jobs are triggered by new code commit or PR merged. A function call is invoked to run the build and test case, executing only for the time needed, and not incurring costs while unused. This lowers costs and can reduce bottlenecks through autoscaling to meet demand.

传统的 CI 管道包括一个等待待分派作业的构建工作者主机池。无服务器是一种很好的模式，可以消除对预先准备好的主机的需求，并降低成本。构建作业由新的代码提交或 PR 合并触发。调用函数调用来运行构建和测试用例，只在所需的时间内执行，不会在未使用时产生成本。这样可以降低成本，并通过自动扩展来减少瓶颈，以满足需求。

Examples include:

例子包括:

- [Serverless CI - Hyper.sh integration for Buildbot](https://blog.hyper.sh/serverless-ci-hyper-docker-integration-for-buildbot.html)[面向 Buildbot 的无服务器 CI-Hyper.sh 集成](https://blog.hyper.sh/serverless-ci-hyper-docker-integration-for-buildbot.html)

## Serverless vs. Other Cloud Native Technologies 无服务器技术 vs 其他本地云技术

There are three primary development and deployment models that most application developers might consider when looking for a platform to host their cloud-native applications. Each model has its own set of differing implementations (whether an open source project, hosted platform, or on-premises product). These three models are commonly built upon container technology for its density, performance, isolation, and packaging features, but containerization is not a requirement.

大多数应用程序开发人员在寻找托管其本地云应用程序的平台时，可能会考虑三种主要的开发和部署模型。每个模型都有自己不同的实现集(无论是开放源码项目、托管平台还是本地产品)。这三种型号通常建立在容器技术的基础上，因为它的密度、性能、隔离性和包装特性，但是容器化不是必需的。

In order of increasing abstraction away from the actual infrastructure that is running their code, and toward a greater focus on only the business logic that’s developed, they are Container Orchestration (or Containers-as-a-Service), Platform-as-a-Service, and Serverless (Functions-as-a-Service). All of these approaches provide a way to deploy cloud-native application, but they prioritize different functional and non-functional aspects based on their intended developer audience and workload type. The following section lists some of the key characteristics of each.

为了增加对运行其代码的实际基础设施的抽象，并且更多地关注已经开发的业务逻辑，它们是容器编排(或容器作为服务)、平台作为服务(Platform-as-a-Service)和服务无服务(function-as-a-service)。所有这些方法都提供了部署云本地应用程序的方法，但是它们根据预期的开发人员受众和工作负载类型对不同的功能和非功能方面进行优先级排序。接下来的部分列出了每种方法的一些关键特征。

Keep in mind that no single approach is a silver bullet for all cloud-native development and deployment challenges. It’s important to match the needs of your particular workload against the benefits and drawbacks of each of these common cloud-native development technologies. It’s also important to consider that subcomponents of your application may be more suitable for one approach versus another, so a hybrid approach is possible.

请记住，对于所有本地云的开发和部署挑战，没有单一的方法是银弹。将特定工作负载的需求与这些常见的本地云开发技术的优缺点进行匹配非常重要。考虑应用程序的子组件可能更适合一种方法而不是另一种方法，这一点也很重要，因此可以采用混合方法。

## Container Orchestration 容器编排

**Containers-as-a-Service(CaaS)** - Maintain full control over infrastructure and get maximum portability. Examples: Kubernetes, Docker Swarm, Apache Mesos.

容器即服务(Containers-as-a-Service，caa)——保持对基础设施的完全控制并获得最大的可移植性。

Container orchestration platforms like Kubernetes, Swarm, and Mesos allow teams to build and deploy portable applications, with flexibility and control over configuration, which can run anywhere without the need to reconfigure and deploy for different environments.

像 Kubernetes、 Swarm 和 Mesos 这样的容器编排平台允许团队构建和部署可移植的应用程序，具有灵活性和对配置的控制，可以在任何地方运行，无需为不同的环境重新配置和部署。

Benefits include maximum control, flexibility, reusability and ease of bringing existing containerized apps into the cloud, all of which is possible because of the degree of freedom provided by a less-opinionated application deployment model.

好处包括最大限度的控制、灵活性、可重用性以及将现有的集装箱化应用程序引入云端的便利性，所有这些都是可能的，因为不那么固执己见的应用程序部署模型提供了一定程度的自由度。

Drawbacks of CaaS include significantly more added developer responsibility for the operating systems (including security patches), load balancing, capacity management, scaling, logging and monitoring.

Caa 的缺点包括显著增加了开发人员对操作系统(包括安全补丁)、负载平衡、容量管理、可伸缩性、日志记录和监视的责任。

### Target audience 服务对象

- Developers and operations teams who want control over how their application and all of its dependencies are packaged and versioned, ensuring portability and reuse across deployment platforms.

  开发人员和操作团队希望控制他们的应用程序及其所有依赖项如何打包和版本化，以确保跨部署平台的可移植性和重用性。

- Developers looking for high performance among a cohesive set of interdependent, independently scaled microservices.

  开发人员希望在一系列相互依赖、独立扩展的内聚型微服务中获得高性能。

- Organizations moving containers to the cloud, or deploying across private/public clouds, and who are experienced with end-to-end cluster deployments.

  组织将容器移动到云中，或者跨私有/公共云进行部署，以及具有端到端集群部署经验的组织。

### Developer/Operator experience 开发人员/操作员经验

- Create a Kubernetes cluster, Docker Swarm stack, or Mesos resource pool (done once).

  创建一个 Kubernetes 集群、 Docker Swarm stack 或 Mesos 资源池(完成一次)。

- Iterate and build container images locally.

  在本地迭代和生成容器映像。

- Push tagged application images to a registry.

  将标记过的应用程序图像推送到注册表。

- Deploy containers based on container images to cluster.

  根据容器映像将容器部署到集群。

- Test and observe application in production.

  测试和观察在生产中的应用。

### Benefits 好处

- The developer has the most control, and the responsibility that comes with that power, for what’s being deployed. With container orchestrators one can define the exact image versions to deploy, and in what configuration, along with policies that govern their runtime.

  开发人员拥有最大的控制权，以及随之而来的责任，对于正在部署的内容。使用容器编排器，可以定义要部署的确切映像版本、以何种配置以及控制其运行时的策略。

- Control over runtime environment (e.g. runtimes, versions, minimal OS, network configuration).

  控制运行执行期函式库(如运行时、版本、最小操作系统、网络配置)。

- Greater reusability and portability of container images outside the system.

  容器图像在系统外具有更高的可重用性和可移植性。

- Great fit for bringing containerized apps and systems to the cloud.

  非常适合将集装箱化的应用程序和系统带入云端。

### Drawbacks 缺点

- More responsibility for the filesystem image and execution environment, including security patches and distribution optimizations.

  对文件系统映像和执行环境负更多责任，包括安全补丁和分发优化。

- More responsibility for managing load balancing and scaling behavior.

  管理负载平衡和缩放行为的更多责任。

- Typically more responsibility for capacity management.

  通常对容量管理负有更多责任。

- Typically longer startup times, today.

  今天，通常启动时间更长。

- Typically less opinionated about application structure, so there are fewer guide rails.

  对应用程序结构的看法通常较少，因此导轨较少。

- Typically more responsibility for build and deployment mechanisms.

  通常对构建和部署机制负有更多的责任。

- Typically more responsibility for integration of monitoring, logging, and other common services.

  通常，更多的职责是集成监视、日志记录和其他公共服务。

## Platform-as-a-Service 平台即服务

**Platform-as-a-Service (PaaS)** - Focus on the application and let the platform handle the rest.

平台即服务(Platform-as-a-Service，PaaS)——专注于应用程序，让平台处理其余部分。

Examples: Cloud Foundry, OpenShift, Deis, Heroku

例如: Cloud Foundry，OpenShift，Deis，Heroku

Platform-as-a-Service implementations enable teams to deploy and scale applications using a broad set of runtimes, binding to a catalog of data, AI, IoT, and security services through injection of configuration information into the application, without having to manually configure and manage a container and OS. It is a great fit for existing web apps that have a stable programming model.

平台即服务(Platform-as-a-Service)实现使团队能够使用广泛的运行时集来部署和扩展应用程序，通过向应用程序中注入配置信息绑定到数据、 AI、物联网和安全服务的目录，而无需手动配置和管理容器和操作系统。它非常适合有稳定编程模型的现有网络应用。

Benefits include easier management and deployment of applications, auto-scaling and pre-configured services for the most common application needs.

它的好处包括更容易地管理和部署应用程序、针对最常见的应用程序需求进行自动伸缩和预配置服务。

Drawbacks include lack of OS control, granular container portability and load balancing and application optimization as well as potential vendor lock-in and needing to build and manage monitoring and logging capabilities on most PaaS platforms.

缺点包括缺乏操作系统控制、粒状容器可移植性、负载平衡和应用程序优化，以及潜在的供应商锁定，需要在大多数 PaaS 平台上构建和管理监控和日志记录能力。

### Target audience 服务对象

- Developers who want a deployment platform that enables them to focus on application source code and files (not packaging them) and without worrying about the OS.

  希望部署平台使他们能够专注于应用程序源代码和文件(而不是打包它们) ，而不用担心操作系统的开发人员。

- Developers who are creating more traditional HTTP-based services (apps and APIs) with routable hostnames by default. However, some PaaS platforms now support generic TCP routing as well.

  开发人员创建更多传统的基于 http 的服务(应用程序和 api) ，默认情况下使用可路由的主机名。然而，一些 PaaS 平台现在也支持通用的 TCP 路由。

- Organizations that are comfortable with a more established model of cloud computing (as compared to serverless) supported by comprehensive docs and many samples.

  那些对更为成熟的云计算模型(与无服务器模型相比)感到满意的组织，这些模型由综合性的文档和许多样本提供支持。

### Developer/Operator experience 开发人员/操作员经验

- Iterate on applications, build and test in local web development environment.

  在本地 web 开发环境中迭代应用程序、构建和测试。

- Push application to PaaS, where it is built and runs.

  将应用程序推送到 PaaS，并在其中构建和运行。

- Test and observe application in production.

  测试和观察在生产中的应用。

- Update configuration to ensure high availability and scale to match demand.

  更新配置，以确保高可用性和规模符合需求。

### Benefits 好处

- The developer’s frame of reference is on the application code, and the data services to which it connects. There’s less control over the actual runtime, but the developer avoids a build step and can also choose scaling and deployment options.

  开发人员的参考框架在于应用程序代码以及与之相连的数据服务。对实际运行时的控制较少，但开发人员可以避免构建步骤，还可以选择缩放和部署选项。

- No need to manage underlying OS.

  不需要管理底层操作系统。

- Buildpacks provide influence over the runtime, giving as much or as little control (sensible defaults) as desired.

  Buildpacks 提供对运行时的影响，根据需要提供尽可能多或尽可能少的控制(合理的默认值)。

- Great fit for many existing web apps with a stable programming model.

  非常适合现有的网络应用程序和稳定的编程模型。

### Drawbacks 缺点

- Loss of control over OS,  possibly at the mercy of buildpack versions.

  对操作系统失去控制，可能受到构建包版本的支配。

- More opinionated about application structure, tending towards [12-Factor](https://12factor.net/) microservices best practices at the potential cost of architecture flexibility.

  对应用程序结构更有主见，倾向于采用12因子的微服务最佳实践，但这可能会损害体系结构的灵活性。

- Potential platform lock-in.

  潜在的平台锁定。

## Serverless 

### Functions-as-a-Service (FaaS) 

Write logic as small pieces of code that respond to a variety of events.

将逻辑编写为响应各种事件的小段代码。

Examples: AWS Lambda, Azure Functions, IBM Cloud Functions based on Apache OpenWhisk, Google Cloud Functions, Huawei Function Stage and Function Graph, Kubeless, iron.io, funktion, fission, nuclio

例如: AWS Lambda，Azure Functions，基于 Apache OpenWhisk 的 IBM Cloud Functions，Google Cloud Functions，huawei Function Stage and Function Graph，Kubeless，iron.io，funktion，fission，nuclio

Serverless enables developers to focus on applications that consist of event-driven functions that respond to a variety of triggers and let the platform take care of the rest - such as trigger-to-function logic, information passing from one function to another function, auto-provisioning of container and run-time (when, where, and what), auto-scaling, identity management, etc.

无服务器使开发人员能够关注由事件驱动的函数组成的应用程序，这些函数响应各种触发器，并让平台处理其余的事项——例如触发到函数的逻辑、从一个函数传递到另一个函数的信息、容器的自动配置和运行时(何时、何地和什么)、自动伸缩、身份管理等。

The benefits include the lowest requirement for infrastructure management of any of the cloud native paradigms. There is no need to consider operating or file system, runtime or even container management. Serverless enjoys automated scaling, elastic load balancing and the most granular "pay-as-you-go" computing model.

它的好处包括对任何云本地范例的基础设施管理的最低要求。不需要考虑操作或文件系统、运行时甚至容器管理。Serverless 享受自动缩放、弹性负载平衡和最细粒度的“现收现付”计算模型。

Drawbacks include less comprehensive and stable documentation, samples, tools, and best practices; challenging debugging; potentially slower responses times; lack of standardization and ecosystem maturity and potential for platform lock-in.

缺点包括不够全面和稳定的文档、示例、工具和最佳实践; 具有挑战性的调试; 响应时间可能较慢; 缺乏标准化和生态系统的成熟度，以及平台锁定的潜力。

#### Target audience 服务对象

- Developers who want to focus more on business logic within individual functions that automatically scale in response to demand and closely tie transactions to cost.

  开发人员希望更多地关注单个功能中的业务逻辑，这些功能可以根据需求自动扩展，并将交易与成本紧密联系在一起。

- Developers who want to build applications more quickly and concern themselves less with operational aspects.

  希望更快地构建应用程序、更少关注操作方面的开发人员。

- Developers and teams creating event-driven applications, such as those that respond to database changes, IoT readings, human input etc.

  开发者和团队创建事件驱动的应用程序，比如那些响应数据库变化、物联网读数、人工输入等的应用程序。

- Organizations that are comfortable adopting cutting-edge technology in an area where standards and best practices have not yet been thoroughly established.

  在标准和最佳做法尚未完全建立的领域，那些乐于采用尖端技术的组织。

#### Developer/Operator experience 开发人员/操作员经验

- Iterate on function, build and test in local web development environment.

  在本地 web 开发环境中迭代函数、构建和测试。

- Upload individual functions to the serverless platform.

  将单个函数上传到无服务器平台。

- Declare the event triggers, the functions and its runtime, and event-to-function relationship.

  声明事件触发器、函数及其运行时以及事件到函数的关系。

- Test and observe application in production.

  测试和观察在生产中的应用。

- No need to update configuration to ensure high availability and scale to match demand.

  不需要更新配置来确保高可用性和规模匹配需求。

#### Benefits 好处

- The developer point of view has shifted farther away from operational concerns like managing the deployment of highly available functions and more toward the function logic itself.

  开发人员的观点已经远离了操作上的考虑，比如管理高可用性函数的部署，而更多地转向了函数逻辑本身。

- The developer gets automated scaling based on demand/workload.

  开发人员可以根据需求/工作负载进行自动扩展。

- Leverages a new "pay-as-you-go" cost model that charges only for the time code is actually running.

  利用一个新的“现收现付”成本模型，该模型仅对实际运行的时间代码收费。

- OS, runtime, and even container lifecycle is completely abstracted (serverless).

  操作系统、运行时甚至容器生命周期都是完全抽象的(无服务器)。

- Better fit for emerging event-driven and unpredictable workloads involving IoT, data, messages.

  更适合新兴的事件驱动和不可预测的涉及物联网，数据，消息的工作负载。

- Typically stateless, immutable and ephemeral deployments. Each function runs with a specified role and well-defined/limited access to resources.

  典型的无状态、不可变和短暂的部署。每个函数都具有指定的角色和对资源的定义良好/有限的访问。

- Middleware layers will get tuned/optimized, will improve application performance over time.

  中间件层将得到调整/优化，随着时间的推移将提高应用程序的性能。

- Strongly promotes the microservices model, as most serverless runtimes enforce limits on the size or execution time of each individual function.

  大力促进微服务模型，因为大多数无服务运行时都对每个函数的大小或执行时间实施限制。

- As easy to integrate third-party APIs as custom-built serverless APIs, both scaling with usage, with flexibility of being called from client or server.

  像定制构建的无服务器 api 一样容易集成第三方 api，两者都随使用而扩展，具有从客户机或服务器调用的灵活性。

#### Drawbacks 缺点

- An emerging computing model, rapid innovation with less comprehensive and stable documentation, samples, tools, and best practices.

  一个新兴的计算模型，快速的创新，缺乏全面和稳定的文档、示例、工具和最佳实践。

- Due to the more dynamic nature of the runtime, it may be more challenging to debug when compared to IaaS and PaaS.

  由于运行时更具动态性，与 IaaS 和 PaaS 相比，调试可能更具挑战性。

- Due to the on-demand structure, the "cold start" aspect on some serverless runtimes could be a performance issue if the runtime removes all instances of a function when idle.

  由于按需结构，如果运行时在空闲时删除一个函数的所有实例，那么某些无服务器运行时上的“冷启动”方面可能会出现性能问题。

- In more complex cases (e.g., functions triggering other functions), there can be more operational surface area for the same amount of logic.

  在更复杂的情况下(例如，函数触发其他函数) ，对于相同数量的逻辑，可以有更多的操作表面积。

- Lack of standardization and ecosystem maturity.

  缺乏标准化和生态系统的成熟度。

- Potential for platform lock-in due to the platform’s programming model, eventing/message interface and BaaS offerings.

  由于平台的编程模型、事件/消息接口和 BaaS 产品，平台锁定的潜力。

## Which Cloud Native Deployment Model Should You Use? 您应该使用哪种云本地部署模型？

In order to determine which model is best for your particular needs, a thorough evaluation of each approach (and several model implementations) should be made. This section will provide some suggestions for areas of consideration as there is no one-size-fits-all solution.

为了确定哪个模型最适合您的特定需求，应该对每种方法(和几个模型实现)进行彻底的评估。这一节将为考虑的领域提供一些建议，因为没有一个放之四海皆准的解决方案。

### Evaluate Features and Capabilities 评估特性和功能

Experiment with each approach. Find what works best for your needs from a functionality and development experience point of view. You’re trying to find the answers to questions such as:

尝试每一种方法。从功能和开发经验的角度，找到最适合您需要的工作。你试图找到这些问题的答案，比如:

- Does my application seem like a fit based on the workloads described in the earlier section where serverless proves its value? Do I anticipate a major benefit from serverless versus the alternatives that justifies a change?

  我的应用程序看起来是否适合前面部分描述的工作负载(serverless 证明了它的价值) ？我是否期待无服务器相对于替代服务的主要好处，从而证明改变是合理的？

- How much control do I really need over the runtime and the environment in which it runs? Do minor runtime version changes affect me? Can I override the defaults?

  我真正需要对运行时及其运行环境进行多少控制？较小的运行时版本变化会影响我吗？我可以覆盖默认值吗？

- Can I use the full set of features and libraries available in my language of choice? Can I install additional modules if needed? Do I have to patch or upgrade them myself?

  我可以使用自己选择的语言中的所有特性和库吗？如果需要，我可以安装额外的模块吗？我需要自己修补或升级它们吗？

- How much operational control do I need? Am I willing to give up over the lifecycle of the container or execution environment?

  我需要多少操作控制？我是否愿意放弃容器或执行环境的生命周期？

- What if I need to change the code of my service? How fast can I deploy it?

  如果我需要更改服务的代码怎么办? 我可以以多快的速度部署它？

- How do I secure my service? Do I have to manage that? Or can I offload to a service that can do it better?

  我如何确保服务的安全？我必须这么做吗？或者我可以把工作转移给一个能做得更好的服务吗？

### Evaluate and Measure Operational Aspects 评估和测量操作方面

Gather performance numbers such as time to recovery with PaaS and a Container Orchestrator as well as cold starts with a Serverless platform. Explore and quantify the impact of other important non-functional characteristics of your application on each platform, such as:

收集性能数据，例如使用 PaaS 和 Container Orchestrator 恢复的时间，以及使用无服务器平台进行 cold 启动的时间。探索和量化应用程序在每个平台上的其他重要非功能特性的影响，例如:

Resiliency:

复原力:

- How do I make my application resilient to a data-center failure?

  如何使应用程序对数据中心故障具有弹性？

- How do I ensure continuity of service while I deploy updates?

  在部署更新时如何确保服务的连续性？

- What if my service fails? Will the platform automatically recover? Will it be invisible to end-users?

  如果我的服务失败了怎么办? 平台会自动恢复吗? 最终用户会看不见吗？

Scalability:

可扩展性:

- Does the platform support auto-scaling in case I have a sudden change in demand?

  如果需求突然发生变化，平台是否支持自动调整？

- Is my application designed to take advantage of stateless scaling effectively?

  我的应用程序是否有效地利用了无状态扩展？

- Will my serverless platform overwhelm any other components such as a database? Can I manage or throttle back-pressure?

  我的无服务器平台是否会压垮其他任何组件，例如数据库? 我是否可以管理或减压？

Performance:

性能:

- How many function invocations per second per instance or per HTTP client?

  每个实例或每个 HTTP 客户机每秒调用多少个函数？

- How many servers or instances will be required for given workload?

  给定的工作负载需要多少台服务器或实例？

- What is the delay from invocation to response (in cold and warm start)?

  从调用到响应(在冷启动和热启动中)的延迟是多少？

- Is the latency between the microservices, vs co-located features within a single deployment, an issue?

  在一个部署中，微服务与共位特性之间的延迟是一个问题吗？

One of the potential outcomes of the CNCF Serverless Working Group could be a decision framework for when to choose a particular model, and how to test given a set of recommended tools. See the Conclusion section for more detail.

CNCF 无服务器工作组的一个潜在成果可以是一个决策框架，用于确定何时选择特定的模式，以及如何测试一组推荐的工具。更多细节请参见结论部分。

### Evaluate and Consider the Full Spectrum of Potential Costs 全谱潜在成本的评估与考虑

This covers both development costs and runtime resource costs.

这包括开发成本和运行时资源成本。

- Not everyone will have the luxury of starting their development activities from scratch. Therefore, the cost of migrating existing applications to one of the cloud native models needs to be carefully considered. While a lift-and-shift model to containers may seem the cheapest, it may not be the most cost effective in the long run. Likewise, the on-demand model of serverless is very attractive from a cost perspective, but the development effort needed to split a monolithic application into functions could be daunting.

  不是每个人都可以从头开始他们的开发活动。因此，需要仔细考虑将现有应用程序迁移到其中一个云本机模型的成本。虽然升降和移动模式的集装箱可能看起来最便宜，它可能不是最具成本效益的长期运行。同样，从成本的角度来看，无服务器的按需模型非常有吸引力，但是将整个应用程序拆分为多个功能所需的开发工作可能会令人望而却步。

- How much will integration with dependent services cost? Serverless compute may appear the most economical at first, but it may require more expensive third party service costs, or autoscale very quickly which may result in greater usage fees.

  与非独立服务的集成成本是多少？无服务器计算可能看起来最经济，但它可能需要更昂贵的第三方服务成本，或自动扩展非常快，可能导致更大的使用费。

- Which features/services do the platforms offer for free? Am I willing to buy into a vendor’s ecosystem at the potential cost of portability?

  这些平台免费提供哪些功能/服务？我是否愿意以可移植性的潜在成本为代价购买供应商的生态系统？

## Running an Application Based on Multiple Platforms 基于多平台的应用程序运行

When looking at the various cloud hosting technologies that are available it may not be obvious but there is no reason why a single solution needs to be used for all deployments. In fact, there is no reason why the same solution needs to be used within a single application. Once an application is split into multiple components, or microservices, you then have the freedom to deploy each one separately on completely different infrastructures, if that’s what’s best for your needs.

当看到各种可用的云托管技术时，可能不是很明显，但是没有理由需要为所有部署使用单一的解决方案。实际上，没有必要在单个应用程序中使用相同的解决方案。一旦一个应用程序被分割成多个组件或者微服务，你就可以自由地在完全不同的基础架构上分别部署每一个组件，如果这样做最适合你的需要的话。

Likewise, each microservice can also be developed with the best technology (i.e. language) for its particular purpose. The freedom that comes with "breaking up of the monolith" brings new challenges though, and the following sections highlight some of the aspects that should be considered when choosing a platform and developing your microservices.

同样，每个微型服务也可以用最好的技术(即语言)为其特定目的进行开发。随之而来的自由，“分裂的整体”带来了新的挑战，虽然，以下部分突出了一些方面，应该考虑在选择一个平台和开发你的微型服务。

### Split Components Across Deployment Targets 跨部署目标拆分组件

Think about matching the right technology to the right job, for example an IoT demo might use both a PaaS application to handle requests to a dashboard of connected devices and a set of serverless functions to handle MQTT message events from the devices themselves. Serverless isn't a magic bullet, but rather a new option to consider within your cloud native architecture.

考虑将正确的技术与正确的工作相匹配，例如物联网演示可能使用 PaaS 应用程序来处理连接设备的仪表板上的请求，以及一组无服务器的功能来处理来自设备本身的 MQTT 消息事件。无服务器不是一个魔术子弹，而是在您的云本地架构中考虑的一个新选项。

### Design for More Than One Deployment Target 多个部署目标的设计

Another design choice is to make your code as generic as possible, allow it to be tested locally, and rely on contextual information, such as environment variables, to influence how it runs in particular environments. For example, a set of plain old Java objects might be able to run within any of the three major environments, and exact behavior tailored based on available environment variables, class libraries, or bound services.

另一个设计选择是使代码尽可能通用，允许在本地进行测试，并依赖上下文信息(如环境变量)来影响它在特定环境中的运行方式。例如，一组普通的旧 Java 对象可以在三种主要环境中的任何一种环境中运行，并且可以根据可用的环境变量、类库或绑定服务来裁剪精确的行为。

### Continue to Use DevOps Pipelines for Any of the Approaches 继续使用 DevOps 管道实现任何方法

Most container orchestration platforms, PaaS implementations, and serverless frameworks can be driven by command line tools, and the same container image can potentially be built once and reused across each platform.

大多数容器编排平台、 PaaS 实现和无服务器框架都可以由命令行工具驱动，并且可以一次性构建相同的容器映像，并在每个平台上重用。

### Consider Abstractions to ease Portability Between Models 考虑抽象以简化模型之间的可移植性

There is a growing ecosystem of third party projects that bridge the gap for porting HTTP-based web applications that currently run on a PaaS or a CaaS to serverless platforms. These include several tools from Serverless, Inc. and the Zappa Framework.

越来越多的第三方项目为基于 http 的 web 应用程序的移植架起了桥梁，这些应用程序目前运行在 PaaS 或 caa 上，并且可以移植到无服务平台上。这些工具包括来自 Serverless 公司和 Zappa 框架的一些工具。

Serverless frameworks provide adaptors that enable applications written using popular web application frameworks such as Python WSGi and JAX-RS REST API to run on serverless platforms. These frameworks can also provide portability and abstraction of the difference between multiple serverless platforms.

无服务器框架提供适配器，使用流行的 web 应用程序框架(如 Python WSGi 和 JAX-RS REST API)编写的应用程序能够在无服务器平台上运行。这些框架还可以提供多个无服务器平台之间差异的可移植性和抽象性。

# Detail View: Serverless Processing Model 详细视图: 无服务器处理模型

This section summarizes the current function usage within serverless frameworks and draws a generalization of the serverless function requirements, lifecycle, invocation types and the required abstractions. We aim to define the serverless function specification so that the same function could be coded once and used in different serverless frameworks. This section does not define the exact function configuration and APIs.

本节总结了无服务器框架中当前的函数使用情况，并对无服务器函数需求、生命周期、调用类型和所需的抽象做了概括。我们的目标是定义无服务器的函数规范，以便同一个函数可以被编码一次，并在不同的无服务器框架中使用。本节不定义确切的函数配置和 api。

We can generalize a FaaS solution as having several key elements shown in the following diagram:

我们可以将一个 FaaS 解决方案归纳为如下图所示的几个关键元素:

[![FaaS](https://github.com/cncf/wg-serverless/raw/master/whitepapers/serverless-overview/image_0.png)](https://github.com/cncf/wg-serverless/blob/master/whitepapers/serverless-overview/image_0.png)

- **Event sources** - trigger or stream events into one or more function instances

  事件源-触发器或流事件到一个或多个函数实例中

- **Function instances** - a single function/microservice, that can be scaled with demand

  函数实例——单个函数/微服务，可以根据需求进行扩展

- **FaaS Controller**- deploy, control and monitor function instances and their sources

  FaaS Controller ——部署、控制和监视功能实例及其来源

- **Platform services** - general cluster or cloud services used by the FaaS solution (sometimes referred to as Backend-as-a-Service)

  平台服务—— FaaS 解决方案使用的通用集群或云服务(有时称为后端即服务)

Let’s start by looking at the lifecycle of a function in a serverless environment.

让我们首先看一下无服务器环境中函数的生命周期。

## Function LifeCycle 功能生命周期

The following sections describe the various aspects of a function’s lifecycle and how serverless frameworks/runtimes typically manage them.

以下部分描述了函数生命周期的各个方面，以及无服务器框架/运行时通常如何管理它们。

### Function Deployment Pipeline 功能展开管道

[![function deployment pipeline](https://github.com/cncf/wg-serverless/raw/master/whitepapers/serverless-overview/image_1.png)](https://github.com/cncf/wg-serverless/blob/master/whitepapers/serverless-overview/image_1.png)

The lifecycle of a function begins with writing code and providing specifications and metadata (see [Function Definition](https://github.com/cncf/wg-serverless/tree/master/whitepapers/serverless-overview#heading=h.1gf8i83) below), a "builder" entity will take the code and specification, compile, and turn it into an artifact (a code binary, package, or container image). Artifacts then get deployed on a cluster with a controller entity in charge of scaling the number of function instances based on the events traffic and/or load on the instances.

函数的生命周期始于编写代码并提供规范和元数据(参见下面的函数定义) ，“构建器”实体将获取代码和规范，进行编译，并将其转换为工件(代码二进制、包或容器映像)。然后在集群上部署工件，由一个控制器实体负责根据事件流量和/或实例上的负载来调整函数实例的数量。

### Function Operations 功能操作

Serverless frameworks may allow the following actions and methods to define and control function lifecycle:

无服务器框架可能允许以下动作和方法来定义和控制功能生命周期:

- Create - Creates a new function, including its spec and code

  创建-创建一个新的函数，包括它的规格和代码

- Publish - Creates a new version of a function that can be deployed on the cluster

  发布——创建可以部署在群集上的函数的新版本

- Update Alias/Label (of a version) - Updating a version alias

  更新别名/标签(版本)-更新别名版本

- Execute/Invoke - Invoke a specific version not through its event source

  执行/调用——不通过事件源调用特定版本

- Event Source association - Connect a specific version of a function with an event source

  事件源关联——将函数的特定版本与事件源连接起来

- Get - Returns the function metadata and spec

  Get-返回函数元数据和 spec

- Update - Modify the latest version of a function

  更新-修改函数的最新版本

- Delete - Deletes a function, could delete a specific version or the function with all its versions

  删除-删除一个函数，可以删除一个特定的版本或函数及其所有版本

- List - Show the list of functions and their metadata

  List-显示函数列表及其元数据

- Get Stats - Return statistics about the runtime usage of a function

  获取统计数据——返回关于函数运行时使用情况的统计数据

- Get Logs - Return the logs generated by a function

  获取日志-返回一个函数生成的日志

[![function operations](https://github.com/cncf/wg-serverless/raw/master/whitepapers/serverless-overview/image_2.png)](https://github.com/cncf/wg-serverless/blob/master/whitepapers/serverless-overview/image_2.png)

When creating a function, providing its metadata (as described later under function spec) as part of the function creation, it will be compiled and possibly published. A function may be started, disabled and enabled later on. Function deployments need to be able to support the following usecases:

当创建一个函数并提供其元数据(如后面在函数规范中所描述的)作为函数创建的一部分时，它将被编译并可能被发布。一个函数可以在稍后启动、禁用和启用。功能部署需要能够支持以下用途:

- Event streaming, in this use case there may always be events in queue however the processing may need to be paused/resumed through an explicit request

  事件流，在这个用例中，队列中可能总是有事件，但是处理可能需要通过显式请求暂停/恢复

- Warm startup - function that has minimal number of instances at any time, such that the "first" event received has a warm start since the function is already deployed and is ready to serve the event (as opposed to a cold start where the function gets deployed on the first invocation by an “incoming” event)

  在任何时候具有最小数量的 Warm startup-function，这样接收到的“第一个”事件有一个热启动，因为该函数已经部署完毕并准备为事件服务(而不是冷启动，在第一个“传入”事件调用时部署该函数)

A user can **Publish** a function, this will create a new version (copy of the "latest" version), the published version may be tagged/labeled or have aliases, see more below.

一个用户可以发布一个函数，这将创建一个新版本(“最新”版本的副本) ，发布的版本可能被标记/标记或有别名，见下面更多。

A user may want to **Execute/Invoke** a function directly (bypass the event source or API gateway) for debug and development processes. A user may specify invocation parameters such as desired version, Sync/Async operation, Verbosity level, etc.

用户可能希望为调试和开发流程直接执行/调用函数(绕过事件源或 API 网关)。用户可以指定调用参数，如期望的版本、同步/异步操作、冗长级别等。

Users may want to obtain function **Statistics** (e.g. number of invocations, average runtime, average delay, failures, retries, etc.), statistics can be the current metric values or a time-series of values (e.g. stored in Prometheus or cloud provider facility such as AWS Cloud Watch).

用户可能希望获得函数统计信息(例如调用次数、平均运行时间、平均延迟、故障、重试等) ，统计信息可以是当前的度量值，也可以是值的时间序列(例如存储在 Prometheus 或云供应商设施中，例如 AWS Cloud Watch)。

Users may want to retrive function **Log** data. This may be filtered by severity level and/or time range, and/or content. The Log data is per function, it include events such as function creation and deletion, explicit errors, warnings, or debug messages, and optionally the Stdout or Stderr of a function. It would be preferred to have one log entry per invocation or a way to associate log entries with a specific invocation (to allow simpler tracking of the function execution flow).

用户可能需要检索函数日志数据。这可能会根据严重级别和/或时间范围和/或内容进行筛选。日志数据是每个函数，它包括诸如函数创建和删除、显式错误、警告或调试消息等事件，还可以选择函数的 Stdout 或 Stderr。最好每次调用都有一个日志条目，或者将日志条目与特定调用关联起来(以便更简单地跟踪函数执行流)。

### Function Versioning and Aliases 函数的版本控制和别名

A Function may have multiple versions, providing the user the ability to run different level of codes such as beta/production, A/B testing etc. When using versioning, the function version is "latest" by default, the “latest” version can be updated and modified, potentially triggering a new build process on every such change.

一个函数可能有多个版本，为用户提供运行不同级别代码的能力，如 beta/production、 a/b 测试等。当使用版本控制时，函数版本默认是“最新”的，“最新”版本可以被更新和修改，每次更改都可能触发新的构建过程。

Once a user wants to freeze a version he will use a **Publish** operation that will create a new version with potential tags or aliases (e.g. "beta", “production”) to be used when configuring an event source, so an event or API call can be routed to a specific function version. Non-latest function versions are immutable (their code and all or some of the function spec) and cannot be changed once published; functions cannot be “un-published” instead they should be deleted.

一旦用户想要冻结一个版本，他会使用一个 Publish 操作来创建一个带有潜在标记或别名(例如“ beta”，“ production”)的新版本，以便在配置事件源时使用，这样一个事件或 API 调用就可以路由到一个特定的函数版本。非最新的函数版本是不可变的(它们的代码和全部或部分函数规范) ，一旦发布就不能更改; 函数不能“未发布”，而应该删除它们。

Note that most implementations today do not allow function branching/fork (updating an old version code) since it complicates the implementation and usage, but this may be desired in the future.

请注意，目前的大多数实现都不允许函数分支/fork (更新旧版本代码) ，因为它会使实现和使用复杂化，但是将来可能需要这样做。

When there are multiple versions of the same function, the user must specify the version of the function he would like to operate and how to divide the traffic of events between the different versions (e.g. a user can decide to route 90% of an event traffic to a stable version and 10% to a beta version a.k.a "canary update"). This can be either by specifying the exact version or by specifying the version alias. A version alias will typically reference to a specific function version.

当同一函数有多个版本时，用户必须指定要操作的函数的版本，以及如何在不同版本之间划分事件流量(例如，用户可以决定将90% 的事件流量路由到稳定版本，10% 路由到 beta 版本 a.k.a“ canary update”)。这可以通过指定确切的版本或指定版本别名来实现。版本别名通常会引用特定的函数版本。

When a user creates or updates a function, it may drive a new build and deployment depending on the nature of the change.

当用户创建或更新函数时，可能会根据更改的性质来驱动新的构建和部署。

### Event Source to Function Association 事件源到函数关联

Functions are invoked as a result of an event triggered by an event source. There is a n:m mapping between functions and event sources. Each event source could be used to invoke more than a single function, a function may be triggered by multiple event sources. Event source could be mapped to a specific version of a function or to an alias of the function, the latter provides a means for changing the function and deploys a new version without the need to change the event association. Event Source could also be defined to use different versions of the same function with the definition of how much traffic should be assigned to each.

作为由事件源触发的事件的结果调用函数。函数和事件源之间有一个 n: m 映射。每个事件源可以用来调用多个函数，一个函数可以由多个事件源触发。事件源可以映射到函数的特定版本或函数的别名，后者提供了更改函数的方法，并部署新版本，而无需更改事件关联。还可以定义事件源来使用同一函数的不同版本，并定义应该为每个版本分配多少流量。

After creating a function, or at a later point in time, one would need to associate the event source that should trigger the function invocation as a result of that event. This requires a set of actions and methods such as:

在创建一个函数之后，或者在稍后的时间点，需要关联应该触发该事件的函数调用的事件源。这需要一系列的动作和方法，例如:

- Create event source association

  创建事件源关联

- Update event source association

  更新事件源关联

- List event source associations

  列出事件源关联

#### Event Sources 活动资料来源

Different types of event sources includes:

不同类型的事件源包括:

- Event and messaging services, e.g.: RabbitMQ, MQTT, SES, SNS, Google Pub/Sub

  事件和消息服务，例如: RabbitMQ，MQTT，SES，SNS，Google Pub/Sub

- Storage services, e.g.: S3, DynamoDB, Kinesis, Cognito, Google Cloud Storage, Azure Blob, iguazio V3IO (object/stream/DB)

  存储服务，例如: S3，DynamoDB，Kinesis，Cognito，Google Cloud Storage，Azure Blob，iguazio V3IO (object/stream/DB)

- Endpoint services, e.g.: IoT, HTTP Gateway, mobile devices, Alexa, Google Cloud Endpoints

  端点服务，例如: 物联网，HTTP 网关，移动设备，Alexa，谷歌云端点

- Configuration repositories, e.g.: Git, CodeCommit

  配置仓库，例如: Git，CodeCommit

- User applications using language-specific SDKs

  使用特定于语言的 sdk 的用户应用程序

- Scheduled events - Enables invocation of functions on regular intervals.

  调度事件——允许定期调用函数。

While the data provided per event could vary between the different event sources, the event structure should be generic with the ability to encapsulate specific information with respect to the event source (details under [Event data and metadata](https://github.com/cncf/wg-serverless/tree/master/whitepapers/serverless-overview#event-data-and-metadata)).

虽然每个事件提供的数据在不同的事件源之间可能有所不同，但事件结构应该是通用的，能够封装与事件源有关的特定信息(事件数据和元数据下的详细信息)。

## Function Requirements 功能需求

The following list describes the set of common requirements that functions, and serverless runtimes, should meet based on the state of art as of today:

下面的列表描述了基于目前的最新技术，函数和无服务器运行时应该满足的一系列共同需求:

- Functions must be decoupled from the underlying implementation of the different event classes

  函数必须与不同事件类的底层实现解耦

- A Function may be invoked from multiple event sources

  可以从多个事件源调用函数

- No need for a different function per invocation method

  每个调用方法不需要不同的函数

- Event source may invoke multiple functions

  事件源可以调用多个函数

- Functions may require a mechanism for long-lasting bindings with underlying platform services, which may be cross function invocations. Functions could be short-lived but the bootstrap may be expensive if it needs to be done on every invocation, such as in the case of logging, connecting, mounting external data sources.

  函数可能需要一种机制，用于与底层平台服务进行长时间绑定，这可能是跨函数调用。函数的寿命可能很短，但是如果需要在每次调用(例如日志记录、连接、挂载外部数据源)时进行引导，那么引导程序的开销可能很大。

- Each function can be written in a different code language from other functions that are using within the same application

  每个函数都可以使用不同于同一应用程序中使用的其他函数的代码语言编写

- Function runtime should minimize the event serialization and deserialization overhead if possible (e.g. use native language structures or efficient encoding schemes)

  函数运行时应尽可能减少事件序列化和反序列化开销(例如使用本地语言结构或高效的编码模式)

Workflow related requirements:

与工作流程相关的要求:

- Functions may be invoked as part of a workflow, where the result of the function is a trigger of another function

  函数可以作为工作流的一部分被调用，其中函数的结果是另一个函数的触发器

- A function can be triggered by an event or an "and/or combination of events"

  函数可以由事件或“和/或事件组合”触发

- One event could trigger multiple functions executed in sequence or parallel

  一个事件可以触发按顺序或并行执行的多个函数

- "and/or combination of events" could trigger m functions running in sequence or parallel or branching

  “和/或事件的组合”可以触发 m 函数顺序运行、并行运行或分支运行

- In the middle of the workflow, different events or function results might be received, which would trigger branching to different functions

  在工作流的中间，可能会接收到不同的事件或函数结果，这将触发向不同函数的分支

- Part or all of a function’s result needs to be passed as input to another function

  一个函数的部分或全部结果需要作为输入传递给另一个函数

- Functions may require a mechanism for long-lasting bindings with underlying platform services, which may be cross function invocations or function could be short lived.

  函数可能需要一种机制，用于与底层平台服务进行长时间的绑定，这可能是跨函数调用，或者函数可能是短时间的。

## Function Invocation Types 函数调用类型

Functions can be invoked from different event sources depending on the different use-cases, such as:

根据不同的用例，可以从不同的事件源调用函数，例如:

1. **Synchronous Request (Req/Rep)**, e.g. HTTP Request, gRPC call

   同步请求(Req/Rep) ，例如 HTTP 请求、 gRPC 调用

   - Client issues a request and waits for an immediate response. This is a blocking call.
   - 客户端发出请求并等待即时响应。这是一个阻塞调用。

2. **Asynchronous Message Queue Request** (Pub/Sub), e.g. RabbitMQ, AWS SNS, MQTT, Email, Object (S3) change, scheduled events like CRON jobs

   异步消息队列请求(Pub/Sub) ，例如 RabbitMQ，AWS SNS，MQTT，Email，Object (S3) change，计划事件，例如 CRON 作业

   - Messages are published to an exchange and distributed to subscribers

     消息发布到交换机并分发给订阅者

   - No strict message ordering. Exactly once processing

     没有严格的消息排序，只处理一次

3. **Message/Record Streams**: e.g. Kafka, AWS Kinesis, AWS DynamoDB Streams, Database CDC

   信息/记录流: 例如 Kafka，AWS Kinesis，AWS DynamoDB Streams，Database CDC

   - An ordered set of messages/records (must be processed sequentially)

     一组有序的消息/记录(必须按顺序处理)

   - Usually a stream is sharded to multiple partitions/shards with a single worker (the shard consumer) per shard

     通常，一个流被分片到多个分区/碎片，每个碎片只有一个工作者(碎片使用者)

   - Stream can be produced from messages, database updates (journal), or files (e.g. CSV, Json, Parquet)

     流可以从消息、数据库更新(日志)或文件(例如 CSV、 Json、 Parquet)生成

   - Events can be pushed into the function runtime or pulled by the function runtime

     事件可以推送到函数运行时，也可以由函数运行时提取

4. **Batch Jobs** e.g. ETL jobs, distributed deep learning, HPC simulation

   批量作业，例如 ETL 作业，分布式深度学习，高性能计算模拟

   - Jobs are scheduled or submitted to a queue, and processed at run time using multiple function instances in parallel, each handling one or more portion of the working set (a task)

     作业被调度或提交到一个队列，并在运行时使用多个函数实例并行处理，每个实例处理工作集的一个或多个部分(任务)

   - The job is complete when all the parallel workers successfully completed all the computation tasks

     当所有的并行工作者都成功地完成了所有的计算任务时，工作就完成了

## [![function invocation types](https://github.com/cncf/wg-serverless/raw/master/whitepapers/serverless-overview/image_3.png)](https://github.com/cncf/wg-serverless/blob/master/whitepapers/serverless-overview/image_3.png)

## Function Code 功能代码

Function code and dependencies and/or binaries may reside in an external repository such as S3 object bucket or Git repository, or provided directly by the user. If the code is in an external repository the user will need to specify the path and credentials.

函数代码、依赖项和/或二进制文件可能驻留在外部存储库中，比如 s3对象桶或 Git 存储库，或者由用户直接提供。如果代码位于外部存储库中，则用户需要指定路径和凭据。

The serverless framework may also allow the user to watch the code repository for changes (e.g. using a web hook) and build the function image/binary automatically on every commit.

无服务器框架还允许用户观察代码存储库的变化(例如使用 web 钩子) ，并在每次提交时自动构建函数 image/binary。

A function may have dependencies on external libraries or binaries, those need to be provided by the user including a way to describe their build process (e.g. using a Dockerfile, Zip).

函数可能依赖于外部库或二进制文件，这些需要由用户提供，包括描述其构建过程的方法(例如使用 Dockerfile，Zip)。

Additionally, the function could be provided to the framework via some binary packaging, such as an OCI image.

此外，该函数可以通过一些二进制打包(如 OCI 映像)提供给框架。

## Function Definition 功能定义

Serverless function definitions may contain the following specifications and metadata, the function definition is version specific:

无服务器函数定义可能包含以下规范和元数据，函数定义是版本特定的:

- Unique ID

  唯一 ID

- Name

  姓名

- Description

  描述

- Labels (or tags)

  标签(或标签)

- Version ID (and/or Version Aliases)

  版本 ID (及/或版本别名)

- Version creation time

  版本创建时间

- Last Modified Time (of function definition)

  上次修改时间(函数定义)

- Function Handler

  函数处理程序

- Runtime language

  运行时语言

- Code + Dependencies or Code path and credentials

  代码 + 依赖项或代码路径和凭据

- Environment Variables

  环境变量

- Execution Role and Secret

  执行角色与秘密

- Resources (Required CPU, Memory)

  资源(所需 CPU、内存)

- Execution Timeout

  执行超时

- Log Failure (Dead Letter Queue)

  日志失败(死信队列)

- Network Policy / VPC

  网络政策/虚拟电脑

- Data Bindings

  数据绑定

### Metadata details 元数据细节

Function frameworks may include the following metadata for functions:

功能框架可能包括以下功能元数据:

- **Version**- each function version should have a unique identifier, in addition versions can be labeled using one or more aliases (e.g. "latest", “production”, “beta”). API gateways and event sources would route traffic/events to a specific function version.

  版本-每个功能版本应该有一个唯一标识符，此外，版本可以使用一个或多个别名(例如“最新”，“生产”，“测试版”)标记。API 网关和事件源将把流量/事件路由到特定的函数版本。

- **Environment Variables** - the user may specify Environment variables that will be provided to the function at runtime. Environment variables can also be derived from secrets and encrypted content, or derived from platform variables (e.g. like Kubernetes EnvVar definition). Environment variables enable developers to control function behavior and parameters without the need to modify code and/or rebuild the function allowing better developer experience and function reuse.

  环境变量-用户可以指定在运行时提供给函数的环境变量。环境变量也可以从机密和加密内容中派生，或者从平台变量中派生(例如 Kubernetes EnvVar 定义)。环境变量使开发人员能够控制函数行为和参数，而不需要修改代码和/或重新构建函数，从而获得更好的开发人员体验和函数重用。

- **Execution Role** - the function should run under a specific user or role identity that grants and audits its access to platform resources.

  执行角色-该功能应该在特定的用户或角色标识下运行，该标识授予和审核其对平台资源的访问权。

- **Resources**- define the required or maximum hardware resources such as Memory and CPU used by the function.

  资源——定义所需的或最大的硬件资源，例如函数使用的内存和 CPU。

- **Timeout**- specify the maximum time a function call can run until it is terminated by the platform.

  超时-指定函数调用在被平台终止之前可以运行的最长时间。

- **Failure Log (Dead Letter Queue)** - a path to a queue or stream that will store the list of failed function executions with appropriate details.

  故障日志(死信队列)-一个队列或流的路径，将存储失败的函数执行列表与适当的细节。

- **Network Policy**- the network domain and policy assigned to the function (for the function to communicate with external services/resources).

  网络策略——分配给该功能的网络域和策略(用于该功能与外部服务/资源通信)。

- **Execution Semantics** - specifies how the functions should be executed (e.g. at least once, at most once, exactly once per event).

  执行语义-指定函数应该如何执行(例如，至少一次，最多一次，每个事件正好一次)。

### Data Bindings 数据绑定

Some serverless frameworks allow a user to specify the input/output data resources used by the function, this enables developer simplicity, performance (data connections are preserved between executions, data can be pre-fetched, etc.), and better security (data resources credentials are part of the context not the code).

有些无服务器框架允许用户指定函数使用的输入/输出数据资源，这使得开发人员简单、性能(在执行之间保留数据连接，数据可以预先获取等)和更好的安全性(数据资源凭证是上下文的一部分，而不是代码)。

Bound data can be in the form of files, objects, records, messages etc., the function spec may include an array of data binding definitions, each specifying the data resource, its credentials and usage parameters. Data binding can refer to event data (e.g. the DB key is derived from the event "username" field), see more in: https://docs.microsoft.com/azure/azure-functions/functions-triggers-bindings.

绑定数据可以采用文件、对象、记录、消息等形式，函数规范可以包括一组数据绑定定义，每个定义指定数据资源、其凭据和使用参数。数据绑定可以引用事件数据(例如数据库密钥是从事件“用户名”字段派生出来的) ，更多信息参见: https://docs.microsoft.com/azure/azure-functions/functions-triggers-bindings。

## Function Input 功能输入

Function input includes event data and metadata, and may include a context object.

函数输入包括事件数据和元数据，并可能包括上下文对象。

### Event data and metadata 事件数据和元数据

Event details should be passed to the function handler, different events may have varying metadata, so it would be desirable for functions to be able to determine the type of event and easily parse the common and event specific metadata.

事件详细信息应该传递给函数处理程序，不同的事件可能有不同的元数据，所以函数最好能够确定事件的类型，并且能够轻松地分析通用的和特定于事件的元数据。

It can be desirable to decouple the event classes from the implementation, for example: a function processing a message stream would work the same regardless if the streaming storage is Kafka or Kinesis. In both cases, it will receive a message body and event metadata, the message may be routed between different frameworks.

将事件类与实现分离是可取的，例如: 无论流存储是 Kafka 还是 Kinesis，处理消息流的函数都将工作相同。在这两种情况下，它都将接收消息体和事件元数据，消息可以在不同的框架之间路由。

An event may include a single record (e.g. in Request/Response model), or accept multiple records or micro-batch (e.g. in Streaming modes).

一个事件可能包括一个记录(例如在请求/响应模型中) ，或者接受多个记录或微批处理(例如在流模式中)。

Examples for common event data and metadata used by FaaS solutions:

Faq 解决方案使用的通用事件数据和元数据示例:

- Event Class/Kind

  活动类别/种类

- Version

  版本

- Event ID

  事件 ID

- Event Source/Origin

  事件来源/来源

- Source Identity

  来源标识

- Content Type

  内容类型

- Message Body

  信息正文

- Timestamp

  时间戳

Examples for event/record specific metadata

事件/记录特定元数据的示例

- **HTTP:** Path, Method, Headers, Query Args

  路径，方法，头，查询参数

- **Message Queue:** Topic, Headers

  消息队列: 主题，报头

- **Record Stream:** table, key, op, modified-time, old fields, new fields

  记录流: 表，键，操作，修改时间，旧字段，新字段

Examples of event source structures:

事件源结构的例子:

- http://docs.aws.amazon.com/lambda/latest/dg/eventsources.html
- https://docs.microsoft.com/azure/azure-functions/functions-triggers-bindings
- https://cloud.google.com/functions/docs/concepts/events-triggers

Some implementations focus on JSON as a mechanism to deliver event information to the functions. This may add substantial serialization/deserialization overhead for higher speed functions (e.g. stream processing), or low-energy devices (IoT). It may be worth considering native language structures or additional serialization mechanisms as options in these cases.

一些实现将 JSON 作为向函数交付事件信息的机制。这可能会为高速函数(如流处理)或低能耗设备(IoT)增加大量的序列化/反序列化开销。在这些情况下，可以考虑使用本地语言结构或其他序列化机制作为选项。

### Function Context 功能上下文

When functions are called, frameworks may want to provide access to platform resources or general properties that span multiple function invocations, instead of placing all the static data in the event or forcing the function to initialize platform services on every call.

当函数被调用时，框架可能希望提供对跨多个函数调用的平台资源或通用属性的访问，而不是将所有静态数据放在事件中，或者强制函数在每次调用时初始化平台服务。

Context is delivered as a set of input properties, environment variables or global variables. Some implementations use a combination of all three.

上下文是作为一组输入属性、环境变量或全局变量交付的。有些实现使用三者的组合。

Examples for Context:

背景例子:

- Function Name, Version, ARN

  函数名，版本，ARN

- Memory Limit

  内存限制

- Request ID

  请求 ID

- Cloud Region

  云区

- Environment Variables

  环境变量

- Security keys/tokens

  保安密码匙/代币

- Runtime/Bin paths

  运行时/Bin 路径

- Log

  原木

- Data binding

  数据绑定

Some implementations initialize a log object (e.g. as global variables in AWS or part of the context in Azure), using the log object users can track function execution using integrated platform facilities. In addition to traditional logging, future implementations may abstract counter/monitoring and tracing activities as part of the platform context to further improve functions usability.

一些实现初始化日志对象(例如 AWS 中的全局变量或 Azure 中的部分上下文) ，使用日志对象用户可以使用集成平台工具跟踪函数执行。除了传统的日志记录之外，未来的实现可能会将对抗/监视和跟踪活动抽象为平台上下文的一部分，以进一步提高功能的可用性。

Data bindings are part of the function context, the platform initiates the connections to the external data resources based on user configuration, and those connections may be reused across multiple function invocations.

数据绑定是函数上下文的一部分，平台基于用户配置启动到外部数据资源的连接，这些连接可以跨多个函数调用重用。

## Function Output 函数输出

When a function exits it may:

当一个函数退出时，它可能:

- Return a value to the caller (e.g. in HTTP request/response example)

  向调用者返回一个值(例如在 HTTP 请求/响应示例中)

- Pass the result to the next execution phase in a workflow

  将结果传递到工作流中的下一个执行阶段

- Write the output to the log

  将输出写入日志

There should be a deterministic way to know if the function succeeded or failed through a returned error value or exit code.

通过返回的错误值或退出代码，应该有一种确定性的方法来知道函数是成功还是失败。

Function output may be structured (e.g. HTTP response object) or unstructured (e.g. some output string).

函数输出可以是结构化的(例如 HTTP 响应对象)或非结构化的(例如一些输出字符串)。

## Serverless Function Workflow 无服务器功能工作流

In the serverless domain, use cases fall into one of the following categories:

在无服务器域中，用例属于以下类别之一:

1. One event triggers one function

   一个事件触发一个函数

2. An and/or combination of events trigger one function

   事件的和/或组合触发一个函数

3. One event triggers multiple functions executed in sequence or in parallel

   一个事件触发按顺序或并行执行的多个函数

4. The result of the function could be a trigger of another function

   该函数的结果可能是另一个函数的触发器

5. N events (in and/or) triggers m functions, i.e. an event-function interleaved workflow, eg. event1 triggers function1, completion of function1 together with event2 and event 3 trigger function2, then different result of function2 triggers branching to function3 or function4.

   N 事件(in 和/或)触发 m 函数，即事件-函数交叉的工作流，如 event1触发器功能1，功能1与 event2和事件3触发器功能2一起完成，然后功能2触发器分支到功能3或功能4的不同结果。

A user needs a way to specify their serverless use case or workflow. For example, one use case could be "do face recognition on a photo when a photo is uploaded onto the cloud storage (photo storage event happens)." Another IoT use case could be “do motion analysis” when a motion detection event is received, then depending on the result of the analysis function, either “trigger the house alarm plus call to the police department” or just “send the motion image to the house owner.” Refer to the use cases section for more detailed information.

用户需要一种方法来指定他们的无服务器用例或工作流。例如，一个用例可以是“当照片上传到云存储(照片存储事件发生)时，对照片进行面部识别”另一个物联网用例可以是在接收到动作感应事件时进行动态分析，然后根据分析功能的结果，或者“触发房屋警报，再打电话给警察局”，或者只是“将动态图像发送给房主”有关更多详细信息，请参阅用例部分。

AWS provides "step function" primitives (state machine based primitives) for the user to specify its workflow, but Step Functions does not allow specification of what event/events will trigger what functions in the workflow. Please refer to https://aws.amazon.com/step-functions/.

AWS 为用户提供了“步骤函数”原语(基于状态机的原语)来指定其工作流，但是步骤函数不允许指定哪些事件/事件将触发工作流中的哪些功能。请参阅 https://aws.amazon.com/step-functions/。

The following graph is an example of a user’s workflow that involves events and functions. Using such a function graph, the user can easily specify the interaction between events and functions as well as how information can be passed between functions in the workflow.

下图是一个包含事件和函数的用户工作流示例。使用这样一个函数图，用户可以很容易地指定事件和函数之间的交互，以及如何在工作流中的函数之间传递信息。

[![image alt text](https://github.com/cncf/wg-serverless/raw/master/whitepapers/serverless-overview/image_4.png)](https://github.com/cncf/wg-serverless/blob/master/whitepapers/serverless-overview/image_4.png)

The Function Graph States include the following:

功能图状态包括以下内容:

Event State This state allows for waiting for events from event sources, and then triggering a function run or multiple functions run in sequence or in parallel or in branch.

事件状态这种状态允许等待来自事件源的事件，然后触发一个函数运行或多个函数按顺序运行、并行运行或分支运行。

Operation/Task State This state allows the run of one or more functions in sequence or in parallel without waiting for any event.

操作/任务状态这种状态允许按顺序或并行运行一个或多个函数，而无需等待任何事件。

Switch/Choice State This state permits transitions to multiple other states (eg. a previous function result triggers branching/transition to different next states).

开关/选择状态这种状态允许转换到多个其他状态(例如，前一个函数结果触发分支/转换到不同的下一个状态)。

End/Stop State This state terminates the workflow with Fail/Success.

结束/停止状态此状态用 Fail/Success 结束工作流。

Pass State This state injects event data in-between two states.

这个状态在两个状态之间注入事件数据。

Delay/Wait State This state causes the workflow execution to delay for a specified duration or until a specified time/date.

延迟/等待状态这种状态会导致工作流执行延迟一个指定的持续时间或者直到一个指定的时间/日期。

States and associated information need to be saved in some persistent storage for failure recovery. In some use cases, the user may want information from one state to be passed to the next state. This information could be part of the function execution result or part of input data associated with an event trigger. An information filter needs to be defined at each state to filter out the information that needs to be passed between states.

状态和相关信息需要保存在一些持久性存储中，以便进行故障恢复。在某些用例中，用户可能希望将来自一个状态的信息传递到下一个状态。此信息可以是函数执行结果的一部分，也可以是与事件触发器关联的输入数据的一部分。需要在每个状态定义一个信息过滤器，以过滤出需要在状态之间传递的信息。

In addition to providing a way to specify function interaction (often with a visual representation of the resulting workflow) and managing function orchestration, a workflow tool might provide visibility into the state of running "workflow instances" and a means of fixing issues leading to workflow instances that cannot progress to the next step. The flow of a workflow instance from one function to another can otherwise be difficult to monitor and manage.

除了提供一种指定功能交互的方法(通常以可视化的方式表示所产生的工作流)和管理功能编排，工作流工具还可以提供运行“工作流实例”状态的可见性，以及修复导致工作流实例无法进展到下一步的问题的方法。否则，工作流实例从一个函数到另一个函数的流可能很难监视和管理。

The workflow tool might also provide historic data from all prior executions of the workflow, documenting which branches were taken, which data was referenced for a decision to take a certain branch, and so on. This data can be used as an audit log (for example, in industries with strict regulatory requirements) or to analyze and improve performance of the multi-function workflow.

工作流工具还可以提供工作流所有先前执行的历史数据，记录采用了哪些分支，引用了哪些数据以决定采用某个分支，等等。这些数据可以用作审计日志(例如，在有严格监管要求的行业中) ，或用于分析和改进多功能工作流的性能。

Esentri, a software consultancy, [built a proof-of-concept with Fn Project and Zeebe to demonstrate how to orchestrate serverless functions with a horizontally-scalable workflow engine](https://www.esentri.com/zeebe-and-fn-project-integrated-a-proof-of-concept/).

软件咨询公司 esentro 与 Fn Project 和 Zeebe 一起构建了一个概念验证，以演示如何使用一个横向可伸缩的工作流引擎来编排无服务器功能。

# Conclusion 总结

Serverless architectures provide an exciting new deployment option for cloud native workloads. As we saw in the [Serverless Workloads section](https://github.com/cncf/wg-serverless/tree/master/whitepapers/serverless-overview#heading=h.rr6of6jc01cx) there are certain use cases where serverless technology provides major benefits over other cloud hosting technologies.

无服务器架构为云本地工作负载提供了一个令人兴奋的新部署选项。正如我们在无服务器工作负载部分中看到的，在某些用例中，无服务器技术比其他云托管技术提供了更多的好处。

However, serverless technology is not a perfect fit for all cases and careful consideration should be given to when it is appropriate. Short-lived, event-driven processing is driving early adoption and use cases for businesses that expect a high rate of change with unpredictable capacity and infrastructure needs are emerging. See the [Additional References](https://github.com/cncf/wg-serverless/tree/master/whitepapers/serverless-overview#heading=h.nkn4basctyj) section for more reading material and insights into serverless computing.

然而，无服务器技术并非完全适用于所有情况，应该仔细考虑何时适用。短期的、事件驱动的处理正在推动早期采用，并且正在出现预期具有不可预测的容量和基础设施需求的高变化率的企业的用例。更多阅读材料和对无服务器计算的见解，请参阅附加参考部分。

The CNCF Serverless Working Group, in partnership with [Redpoint Ventures](http://www.redpoint.com/), recently published a [Serverless Landscape](https://docs.google.com/spreadsheets/d/10rSQ8rMhYDgf_ib3n6kfzwEuoE88qr0amUPRxKbwVCk/edit#gid=0). It illustrates some of the major serverless projects, tooling and services that are available in the ecosystem. It is not intended to represent a comprehensive, fully inclusive serverless ecosystem, nor is it an endorsement, rather just an overview of the landscape. It is expected that owners of each will provide updates in an attempt to keep it up to date.

CNCF 无服务器工作组与 Redpoint Ventures 合作，最近发布了一个无服务器景观。它说明了生态系统中可用的一些主要的无服务器项目、工具和服务。它不是要代表一个全面的，完全包容的无服务器的生态系统，也不是一个认可，而只是一个景观的概述。预计每个业主将提供更新，以保持最新的努力。

# Next Steps for the CNCF CNCF 的下一步工作

With respect to what, if anything, the CNCF should consider doing in this space, the following suggestions are offered for the Technical Oversight Committee’s consideration:

关于国家气候变化委员会在这方面应考虑做些什么，现提出以下建议供技术监督委员会审议:

- **Encourage more serverless technology vendors and open source developers to join the CNCF** to share ideas and build upon each other’s innovation. For example, keep the open source projects listed in the [Serverless Landscape](https://docs.google.com/spreadsheets/d/10rSQ8rMhYDgf_ib3n6kfzwEuoE88qr0amUPRxKbwVCk/edit#gid=0) document updated and the matrix of capabilities maintained.

  鼓励更多的无服务器技术供应商和开放源码开发者加入 CNCF，分享想法，建立在彼此的创新之上。例如，更新无服务器景观文档中列出的开放源码项目，并维护功能矩阵。

- **Foster an open ecosystem by establishing interoperable APIs, ensuring interoperable implementations with vendor commitments and open source tools**. New interoperability and portability efforts similar to [CSI](https://github.com/cncf/wg-storage) and [CNI](https://github.com/cncf/wg-networking) with the help of both platform providers and third-party developer library creators. Some of these may merit their own CNCF working group, or may continue as an initiative of the Serverless WG. For example:

  通过建立可互操作的 api，确保实现与供应商承诺和开放源码工具的可互操作性，从而培育一个开放的生态系统。在平台提供商和第三方开发者库创建者的帮助下，与 CSI 和 CNI 类似的新的互操作性和可移植性努力。其中一些可能值得他们自己的 CNCF 工作组，或可能继续作为无服务器工作组的倡议。例如:

  - **Events:** define a common event format and API along with metadata. Some initial proposals can be found in the [Serverless WG github repo](https://github.com/cncf/wg-serverless/tree/master/proposals).

    事件: 定义通用事件格式和 API 以及元数据。在无服务器 WG github repo 中可以找到一些初始提案。

  - **Deployment**: leveraging the existing CNCF members that are also serverless providers, start a new working group to explore possible small steps that can be taken to harmonize on a common set of function definitions, metadata. For example:

    部署: 利用同时也是无服务提供者的现有 CNCF 成员，启动一个新的工作组，探讨可能采取的小步骤，以统一一套共同的功能定义、元数据。例如:

    - **Application definition manifests**, such as the [AWS SAM](https://github.com/awslabs/serverless-application-model) and the [OpenWhisk Packaging Specification](https://github.com/apache/incubator-openwhisk-wskdeploy/tree/master/specification#openwhisk-packaging-specification).
    - 应用程序定义清单，例如 AWS SAM 和 OpenWhisk 打包规范。

  - **Function WorkFlow** across different providers’ serverless platforms. There are many usage scenarios that go beyond a single event triggering a single function and would involve a workflow of multiple functions executed in sequence or in parallel and triggered by different combinations of events + return values of the function in the previous step of the workflow. If we can define a common set of constructs that the developers can use to define their use case workflow, then they will be able to create tools that can be used across different serverless platforms. These constructs specify the relationship/interaction between the events and functions, relationship/interaction between functions in the workflow as well as how to pass information from one function to the next step function, etc. Some examples are AWS Step Function Constructs and Huawei’s Function Graph/Workflow Constructs.

    跨不同提供者的无服务器平台运行工作流。有许多使用场景，超越单个事件触发单个功能，涉及一个多个功能的工作流程执行顺序或并行和触发的不同组合的事件 + 返回值的功能在前一步的工作流程。如果我们能够定义一组通用的结构，开发人员可以使用这些结构来定义他们的用例工作流，那么他们就能够创建跨不同无服务器平台使用的工具。这些构造指定事件和函数之间的关系/交互，工作流中函数之间的关系/交互，以及如何将信息从一个函数传递到下一个步骤函数，等等。一些例子是 AWS 步骤函数构造和华为的功能图/工作流构造。

- Foster an ecosystem of open source tools that accelerate developer adoption and velocity, exploring areas of concern, such as:

  培育一个开源工具的生态系统，加速开发人员的采用和速度，探索关注的领域，例如:

  - Instrumentation

    仪器仪表

  - Debugability

    可调试性

- Education: provide a set of design patterns, reference architectures, and common vocabulary for new users.

  教育背景: 为新用户提供一组设计模式、参考架构和通用词汇。

  - Glossary of terms: maintain glossary of terms (Appendix A) in a published form and ensure that Working Group documents use these terms consistently

    术语表: 以公布的形式维护术语表(附录 a) ，并确保工作组文件使用这些术语的一致性

  - Use cases: maintain list of use cases, grouped by common patterns, creating a shared higher-level vocabulary. Supporting the following goals:

    用例: 维护用例列表，按照通用模式分组，创建一个共享的高级词汇表:

    - For developers who are new to Serverless platforms: increase understanding of common use cases, identifying good entry points

      对于初次接触无服务器平台的开发人员: 增加对常见用例的理解，确定良好的入口点

    - For Serverless providers and library/framework authors, facilitate consideration of common needs

      对于无服务器提供者和库/框架作者，促进考虑共同需求

  - Sample applications and open source tools in the CNCF GitHub repo, with a preference for highlighting the interoperability aspects or linking to external resources for each provider.

    CNCF GitHub repo 中的示例应用程序和开放源码工具，优先突出互操作性方面或链接到每个提供者的外部资源。

- Provide guidance on how to evaluate functional and nonfunctional characteristics of serverless architectures relative to CaaS or PaaS. This could take the form of a decision tree or recommend a set of tools from within the CNCF project family.

  提供关于如何评估无服务器架构相对于 CaaS 或 PaaS 的功能和非功能特性的指导。这可以采取决策树的形式，或者从 CNCF 项目家族中推荐一组工具。

- Provide guidance on serverless security topics such as: secure serverless development guidelines, hardening serverless deployments, adequate security logging and monitoring as well as relevant tools & procedures (see [The Ten Most Critical Security Risks in Serverless Architectures](https://github.com/puresec/sas-top-10)).

  提供关于无服务器安全主题的指导，例如: 安全无服务器开发指南、加强无服务器部署、充分的安全日志记录和监控以及相关的工具和程序(见无服务器架构中十大最关键的安全风险)。

- Begin a process for CNCF outputs (for the suggested documents referenced above), such as from this Serverless Working Group and the Storage Working Groups, to live on as Markdown files in GitHub where they can be collaboratively maintained over time, which is particularly important given the speed of innovation in this space.

  开始一个 CNCF 输出(针对上面提到的建议文件)的进程，例如这个无服务器工作组和存储工作组的输出，作为 Markdown 文件存储在 GitHub 上，可以随着时间的推移进行协作维护，鉴于这一领域的创新速度，这一点特别重要。

# Appendix A: Glossary 附录 a: 词汇表

This section defines some of the terms used in this whitepaper.

本节定义了本白皮书中使用的一些术语。

**Backend-as-a-Service**

后端即服务

Applications often leverage services that are managed outside of the application itself - for example, a remote storage service. This allows for the application to focus on its key business logic. This collection of 3rd party services is sometimes referred to as Backend-as-a-Service (BaaS). While these may be used from traditional compute platforms or from Serverless, it is important to note that BaaS plays an important role in the Serverless architecture as it will often be the supporting infrastructure (e.g. provides state) to the stateless Functions themselves. BaaS platforms may also generate events that trigger Serverless compute.

应用程序通常利用在应用程序本身之外管理的服务——例如，远程存储服务。这允许应用程序将重点放在其关键业务逻辑上。这种第三方服务的集合有时称为后端服务(Backend-as-a-Service，BaaS)。虽然这些可以从传统的计算平台或 Serverless 使用，但需要注意的是，BaaS 在无服务器架构中扮演着重要角色，因为它通常是无状态函数本身的支持基础设施(例如提供状态)。BaaS 平台还可能生成触发无服务器计算的事件。

**Cold Start**

冷启动

"Cold start" refers to the starting of an instance of the function, typically with new code, from an undeployed state.

“冷启动”指的是从未部署的状态启动函数实例，通常使用新代码。

**Context**

背景

A Serverless platform typically provides a Context object as an input parameter when executing a Function, including Trigger metadata and other information about the environment or the circumstances around this specific Function invocation.

无服务器平台通常在执行函数时提供一个 Context 对象作为输入参数，包括触发器元数据和关于环境的其他信息或围绕这个特定函数调用的情况。

**Data Binding**

数据绑定

Function may require data bindings for long lasting connections to data such as backend storage (such as mount points/volumes/object store), databases, etc. The data binding may include secure information such as secrets that can not be preserved within the function itself. The data binding may be used across several Function invocations.

函数可能需要数据绑定才能与数据进行长时间的连接，如后端存储(例如挂载点/卷/对象存储)、数据库等。数据绑定可以包括安全信息，例如不能在函数本身中保存的秘密。数据绑定可以跨多个函数调用使用。

**Development Framework**

发展纲领

The environment in which Functions are developed. This can be local (e.g. on a laptop) or in a hosted environment.

开发函数的环境。可以是本地的(例如在笔记本电脑上) ，也可以是托管环境。

**Event**

比赛项目

The notification of something that happened.

通知发生了某事的通知。

**Event Association**

活动协会

Mapping between event sources and the the specific Functions that are meant to be executed as a result of the event

事件源和作为事件结果意味着要执行的特定函数之间的映射

**Event Data**

事件数据

Information pertaining to the Event that occurred. See [Event data and metadata](https://github.com/cncf/wg-serverless/tree/master/whitepapers/serverless-overview#event-data-and-metadata) for more information.

有关发生的事件的信息。有关详细信息，请参阅事件数据和元数据。

**Event Source**

事件来源

Functions could be invoked through one or more event source types such as HTTP gateways, message queues, streams, etc. or generated based on a change in the system, such as a database write, IoT sensor activation or period of inactivity.

函数可以通过一个或多个事件源类型调用，比如 HTTP 网关、消息队列、流等，或者根据系统的变化生成，比如数据库写入、物联网传感器激活或者一段时间的不活动。

**Function/Action**

功能/行动

The code that is executed as a result of a Trigger.

作为触发器的结果执行的代码。

**Function Graph/Workflow**

职能图/工作流程

A developer’s Serverless scenario usually involves definition of an Event, Function, Event-Function interaction and coordination between Functions. In some use cases, there are multiple Events and multiple Functions. A Function Graph/Workflow describes the Event-Function interaction and Function coordination. It provides a way for the user to specify what Events trigger what Functions, whether the Functions are executed in sequence or in parallel, transition between Functions, and how information is passed from one Function to the next Function in the workflow. Function Graphs can be viewed as a collection of workflow states and the transition between these states, with each state having its associated Events and Functions. An example of the Function Graph/Workflow is AWS’s step function.

开发人员的无服务器场景通常包括定义事件、函数、事件-函数交互和函数之间的协调。在某些用例中，有多个事件和多个函数。功能图/工作流描述了事件-功能交互和功能协调。它为用户提供了一种方法来指定什么事件触发什么函数，函数是按顺序执行还是并行执行，函数之间的转换，以及工作流程中信息如何从一个函数传递到下一个函数。功能图可以看作是工作流状态的集合和这些状态之间的转换，每个状态都有其相关的事件和功能。功能图/工作流的一个例子是 AWS 的步进函数。

**Function Parameters**

功能参数

When a Function is invoked, the Runtime Framework will typically provide metadata about this particular invocation as a set of parameters (see Context).

当调用函数时，Runtime Framework 通常会提供关于这个特定调用的元数据作为一组参数(请参见上下文)。

**Functions-as-a-Service**

「服务为本」

FaaS describes the core functionality of a platform to run functions provided by the end user on demand. It’s a core component of a serverless platform, which includes the additional quality-of-service features that manage functions on behalf of the user including autoscale and billing.

FaaS 描述了一个平台的核心功能，用于根据需要运行最终用户提供的功能。它是无服务平台的核心组件，其中包括代表用户管理功能的附加服务质量特性，包括自动扩展和计费。

**Invocation**

调用

The act of executing a Function. For example, as a result of an Event.

执行一个函数的行为。例如，作为一个事件的结果。

**Runtime Framework**

运行时框架

The runtime environment/platform in which Serverless workflows are executed, Triggers are mapped to Functions, Functions hosting container resource and language package/library are dynamically provisioned, and those Functions are executed. Sometimes Runtime Frameworks will have a fixed set of runtime languages in which the Functions can be written.

执行无服务器工作流的执行期函式库/平台，触发器映射到函数，托管容器资源的函数和语言包/库动态分配，这些函数被执行。有时运行时框架会有一组固定的运行时语言，可以用这些语言编写函数。

**Trigger**

触发器

A request to execute a Function. Often Triggers are the result of an incoming Event, such as an HTTP request, database change, or stream of messages.

执行函数的请求。触发器通常是传入事件(如 HTTP 请求、数据库更改或消息流)的结果。

**Warm Start**

温暖的开始

"Warm starts" refers to the starting of an instance of the function from a stopped (but deployed) state.

“ Warm starts”指的是从停止(但已部署)状态启动函数实例。

# Appendix B: Additional References 附录 b: 附加参考资料

The following references are provided for those looking for additional resources on serverless computing:

下面的参考资料提供给那些寻找额外资源的无服务器计算:

- [Serverless Architectures](https://martinfowler.com/articles/serverless.html) by Mike Roberts

  无服务器的建筑作者: Mike Roberts

- [Containers vs serverless - Navigating application deployment options](https://www.slideshare.net/DanielKrook/containers-vs-serverless-navigating-application-deployment-options) by Daniel Krook

  容器 vs 无服务器——应用程序部署选项导航作者: Daniel Krook

- [Serverless Computing: Current Trends and Open Problems](https://arxiv.org/abs/1706.03178) by Ioana Baldini, et al.

  无服务器计算: 当前趋势和开放的问题。

- [Serverless: Background, Challenges and Future](https://medium.com/@yaronhaviv/serverless-background-challenges-and-future-d0928df71758) by Yaron Haviv

  无服务者: 背景、挑战与未来》 by 亚伦 · 哈维夫

- [What is serverless good for?](https://medium.com/openwhisk/what-serverless-is-good-for-from-serverless-mobile-backends-to-data-streaming-cognitive-c0dd4aec90e9) by Andreas Nauerz

  没有服务器有什么用

- [Serverless Architecture: Five Design Patterns](https://thenewstack.io/serverless-architecture-five-design-patterns/) by Mark Boyd

  无服务器架构: 五种设计模式作者: Mark Boyd

- [How Two College Kids Built A Better Census](https://medium.com/serverless-stories/challenge-accepted-building-a-better-australian-census-site-with-serverless-architecture-c5d3ad836bfa) by Stefanie Monge

  两个大学生如何更好地进行人口普查

- [7 AWS Lambda Tips from the Trenches](https://read.acloud.guru/lambda-for-alexa-skills-7-tips-from-the-trenches-684c963e6ad1) by Mitchell Harris

  7《来自战壕的 AWS Lambda 技巧》作者: Mitchell Harris

- [Why The Future Of Software And Apps Is Serverless](http://readwrite.com/2012/10/15/why-the-future-of-software-and-apps-is-serverless/) by Ken Fromm

  为什么软件和应用的未来是无服务的

- [The Serverless Guide](https://serverless.github.io/guide/) authored by the community, curated by Serverless, Inc.

  无服务器指南由社区授权，由无服务器公司策划。

- [Microservice Orchestration for Serverless Computing](https://conferences.oreilly.com/oscon/oscon-tx/public/schedule/detail/61681) by Cathy Zhang, Louis Fourie

  无服务计算的微服务编配》 by 凯西 · 张，路易斯 · 富里

- [The Ten Most Critical Security Risks in Serverless Architectures](https://github.com/puresec/sas-top-10) by PureSec

  无服务器架构中的十大最关键的安全风险