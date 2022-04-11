Serverless研究总结



# Preface

本文综合互联网上关于Serverless的资料以及个人的研究和理解，介绍了Serverless的定义、是什么、不是什么，与微服务的关系，FaaS和BaaS的概念，以及FaaS与PaaS、与存储过程的对比，并分别总结了Serverless应用的优点和缺点，以及Serverless的使用场景思考。

如有不足，敬请指教；互联网上的资料见文末参考资料。

# 概念

## Serverless定义

其实关于serverless的定义并没有一个统一的标准，以下是[CNCF白皮书]([wg-serverless/whitepapers/serverless-overview at master · cncf/wg-serverless (github.com)](https://github.com/cncf/wg-serverless/tree/master/whitepapers/serverless-overview))里对Serverless计算的定义：

> Serverless computing refers to the concept of building and running applications that do not require server management. It describes a finer-grained deployment model where applications, bundled as one or more functions, are uploaded to a platform and then executed, scaled, and billed in response to the exact demand needed at the moment.

Serverless计算是指构建和运行不需要服务器管理的应用程序的概念。它描述了一种更细粒度的部署模型，应用程序捆绑一个或多个函数，上传到平台，然后执行，缩放和计费，以响应当前所需的确切需求。



这里是[cloudflare](https://www.cloudflare.com/learning/serverless/what-is-serverless)对serverless计算的定义:

>Serverless computing is a method of providing backend services on an as-used basis. A serverless provider allows users to write and deploy code without the hassle of worrying about the underlying infrastructure. A company that gets backend services from a serverless vendor is charged based on their computation and do not have to reserve and pay for a fixed amount of bandwidth or number of servers, as the service is auto-scaling. Note that despite the name serverless, physical servers are still used but developers do not need to be aware of them.

Serverless计算是一种在使用时提供后端服务的方法。 Serverless提供程序允许用户编写和部署代码，而无需担心底层基础设施。 从Serverless供应商那里获得后端服务的公司根据他们的计算收费，并且由于服务是自动扩展的，因此不必预留和支付固定数量的带宽或服务器数量。 请注意，尽管名称为Serverless（无服务器），但仍使用物理服务器，但开发人员不需要了解它们。 



狭义上来说，你可以认为Serverless是一个部署平台。你只需要上传代码，然后部署到Serverless平台上，至于代码执行、弹性伸缩、计费等全部由平台去完成。

广义上来说，你可以认为Serverless是一种新的架构理念。正如同微服务架构：应用拆分成单体的微服务。我们可以用函数来构建应用，解耦计算和存储，事件驱动，为函数实际运行来计费。



不过，如果为了用Serverless而制定一个新的架构规范，这可能不太容易让人接受，微服务演进的终极目标是否就是Serverless目前尚未有结论（目前来看是这样的趋势）。当前探索阶段，个人认为，打造一套集开发、部署、运营一体的Serverless计算平台也许更符合实际。



那么，对Serverless计算平台而言，至少有两个角色：

* **Provider**：**供应商**，为外部或内部客户部署serverless平台。

- **Developer**：**开发人员**，为serverless平台编写代码并从中获益，serverless平台提供了这样的视角：没有服务器，而代码始终在运行。

运行serverless平台仍然需要服务器，**供应商**需要管理服务器（或虚拟机和容器）。即使在空闲时，**供应商**也会有一些运行平台的成本。自托管系统仍然可以被视为serverless：通常一个团队充当**供应商**，另一个团队充当**开发人员**。



## Serverless是什么

在伯克利关于Serverless论文中指出，Serverless与Serverful三个关键的区别：

1. **计算与存储解耦**：存储和计算是分开扩展的，并且是独立配置和定价的。通常，存储由单独的云提供服务，计算是无状态的。

2. **执行代码而不管理资源分配**：用户不请求资源，而是提供一段代码，云自动提供资源来执行该任务代码

3. **按资源使用比例支付，而不是按资源分配比例支付：**根据与执行相关的某个维度，比如执行时间来进行计费，而不是基础云平台的维度，比如分配的虚拟机的大小和数量

根据这三个指标，我们就可以判断应用是否是Serverless架构。



Serverless计算平台可以提供以下一到两种的服务：

1. **Backend-as-a-Service (BaaS)**，它是基于API的第三方服务（云端），可替代应用程序中的核心功能子集。因为这些API是作为可以自动扩展和透明操作的服务而提供的，比如数据库存储服务、认证服务等。
2. **Functions-as-a-Service (FaaS)**，提供事件驱动计算，将开发人员上传的函数代码运行在无状态的容器中，函数由事件触发，无需管理服务器或任何其他底层基础设施即可进行扩展。

关于FaaS、BaaS的关系，如下这张图可以直观的展示出Serverless整体的架构模型：

![](http://dockone.io/uploads/article/20211201/195262bb4e2bb751affb64b1325294b9.png)



可以用一句话来概括Serverless：**函数运行在FaaS平台上，使用了BaaS能力**。



## Serverless不是什么

* **Serverless不是说没有了服务器**

  使用Serverless计算并不意味着我们不再使用服务器来托管和运行代码，对开发人员而言，服务器被黑盒化了，开发人员只需要提供代码，至于代码怎么部署运行，开发人员就不需要关心了，只需要关心业务逻辑的实现。

* **Serverless不是说不需要运维人员**

  Serverless计算并不意味着不再需要运维工程师。相反，它指的是serverless计算的消费者不再需要花费时间和资源来进行服务器配置，维护，更新，扩展和容量规划。所有这些任务和功能都由serverless平台处理，并完全从开发人员和IT/运维团队中抽象出来。因此，开发人员专注于编写应用程序的业务逻辑。运维工程师能够将运维重点更多的放到关键业务任务上。

* **Serverless不完全就是FaaS**

  尽管大部分情况下我们讨论的Serverless都是FaaS，但是把Serverless就看作FaaS这太局限了。FaaS是实现Serverless的最佳形态，起码这几年来看是这样的。但是就目前Serverless的发展来看，有倾向于建设面向应用的Serverless服务的趋势，而不局限于面向函数计算的FaaS服务。

* **Serverless不等于PaaS**

  虽然对开发者而言，Serverless和PaaS都是提供了“平台”，但是PaaS所说的平台范畴大得多，所有与构建应用程序相关的开发工具、中间件、数据库管理以及操作系统等均可认为是PaaS。而Serverless范畴则小多了，对开发者也更透明，虽然也提供了各种后端BaaS，但在开发者的视角下，基本上可认为只是一个执行函数的平台。





## BaaS

相信大家对这张冰山图都不陌生，无需赘述：

![image-20220105145525970](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20220105145525970.png)



后端即服务(Backend-as-a-Service)是一种云服务模型，其中开发人员将 web 或移动应用程序的所有幕后工作外包出去，这样他们只需编写和维护前端。BaaS服务提供商可以提供多种服务器端的功能，例如用户认证、数据库管理、远程更新和推送通知(针对移动应用程序) ，以及云存储和托管。

如同一个导演，要拍摄一部电影，导演所要做的就是直接拍摄场景（前端）。而灯光、布景、衣柜、演员选角和制作等幕后活动这些就是BaaS了。

BaaS能够使开发者仅关注写好前端（frontend）应用代码，通过BaaS供应商提供的API或者SDK，集成开发者所需要的所有后端（backend）功能，而不需要在后端重复造轮子，开发者更不需要关心后端服务的管理、虚拟机以及容器来保持应用的运行。因此，开发者使用BaaS，他们可以更快地构建和启动移动应用程序和 web 应用程序(包括单页应用程序)。



### MBaaS

在移动应用端，还有一个术语，叫做：Mobile-Backend-as-a-Service (MBaaS)。它也是BaaS，只不过是专门为移动设备构建app的。虽然有些人认为 BaaS 和 MBaaS基本上是可以互换的术语，但 BaaS 服务并不一定要用于构建移动应用程序。



### 哪些后端属于BaaS

BaaS的提供商会提供许多服务器端（server-side）的能力，比如：

- Database management ： 数据库管理
- Cloud storage (for user-generated content)：云存储（针对用户生成的内容）
- User authentication：用户认证
- Push notifications：push的通知
- Remote updating：远程的修改
- Hosting：托管
- Other platform- or vendor-specific functionalities; for instance, Firebase offers Google search indexing：其他平台或供应商特定的功能; 例如，Firebase 提供谷歌搜索索引

### BaaS与Serverless

BaaS与Serverless有重叠的部分，因为两者都能让开发者只关注他们自己的应用代码而不需要考虑后端，大部分BaaS提供商也都会提供serverless计算能力。但是基于BaaS构建的应用和真正的serverless架构还有有显著的操作差异：

1. 应用的构建

   serverlessy应用的后端会被分解成函数（function），这些函数都是通过事件来响应，并且一个函数只完成一件操作。

   BaaS服务器端功能是按照供应商指定的标准来构建的，开发者在他们的前端应用程序代码里也不需要关注后台功能。

2. 代码的运行

   serverless的架构是基于事件驱动（event-driven）的架构，也就是说代码通过事件来响应，有事件到达时，就会触发代码运行， 反之代码是不会运行的；

   基于BaaS构建的应用通常是不会使用事件驱动的架构，意味着应用代码可能随时都在运行， 因此就需要更多的资源。

3. 应用的伸缩

   Serverless的架构一个最重要的特点就是自动伸缩，这也是与其他体系架构最大的区别，sreverless的应用可以根据使用量的增加而自动扩容，云供应商的基础设施根据需要启动每个函数的临时实例 。

   BaaS的应用通常不会以这种方式伸缩，除非BaaS供应商还提供了无服务器计算并且开发者将其构建到他们的应用程序。



### BaaS与PaaS

PaaS 通过云为开发人员构建他们的应用程序提供了一个平台。 与无服务器计算和 BaaS 一样，平台即服务 (PaaS) 消除了开发人员构建和管理应用程序后端的需要。 但是，PaaS 不包括预先构建的服务器端应用程序逻辑，例如推送通知和用户身份验证。 PaaS 为开发人员提供了更多的灵活性，而 BaaS 提供了更多的功能。

BaaS并非PaaS，它们的区别在于：PaaS需要参与应用的生命周期管理，BaaS则仅仅提供应用依赖的第三方服务。

典型的PaaS平台需要提供手段让开发者部署和配置应用，例如自动将应用部署到Tomcat容器中，并管理应用的生命周期。BaaS不包含这些内容，BaaS只以API的方式提供应用依赖的后端服务，例如数据库和对象存储。从功能上讲，BaaS可以看作PaaS的一个子集，即提供第三方依赖组件的部分。



### 后端BaaS化

FaaS 连接并访问传统数据库会增加额外的开销，我们可以采用数据编排的思想，将数据库操作转为 RESTful API。顺着这个思路，引出了后端应用的 BaaS 化，一句话总结，后端应用 BaaS 化就是将后端应用转换成 NoOps 的数据接口。

BaaS 化的核心其实就是把我们的后端应用封装成 RESTful API，然后对外提供服务，而为了后端应用更容易维护，我们需要将后端应用拆解成免运维（noOps）的微服务。



## FaaS

如果说微服务是以专注于单一责任与功能的小型功能块为基础，利用模组化的方式组合出复杂的大型应用程序，那么我们还可以进一步认为Serverless架构可以提供一种更加**代码碎片化**的软件架构范式，我们称之为Function as a Services（FaaS）。

FaaS是 Serverless 架构的其中一种形态，也是severless最佳解决方案。但是事实上 Serverless 架构非常庞大，FaaS 只是其中的一小部分，基于事件驱动的模型，从微服务（MicroService）这种专注于单一职责与功能的小型功能块演进而来。



因此，我们可以总结一下FaaS的关键点：

纯代码，在云上运行，无状态，由事件触发。



以下是[腾讯云函数(SCF)](https://cloud.tencent.com/document/product/583/9199)的总体流程，借此来理解FaaS，较为直观：

![image-20210917114042214](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20210917114042214.png)

1. 开发者编写代码；

2. 直接将源代码部署到腾讯云函数（SCF）平台上；
3. 云函数由事件触发，事件源可以是不同类型的云产品事件源，也可以是API/SDK调用的方式；
4. 触发后的云函数自动化弹性执行，即根据调用量弹性伸缩、自动扩缩容；
5. 根据调用量按需计费，然后生成账单。



### 对比微服务

可以认为，Serverless是另一种形式的微服务，它的细粒度更小。现在所说的微服务一般都是将应用拆分成了一个个单体的服务，而Serverless则是拆分成了更小的函数。

“函数”（Function）提供的是相比微服务更加细小的程序单元。例如，可以通过微服务代表为某个客户执行所有CRUD操作所需的代码，而FaaS中的“函数”可以代表客户所要执行的每个操作：创建、读取、更新，以及删除。

<img src="C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20220214103946880.png" alt="image-20220214103946880" style="zoom:200%;" />

这里从《华为Serverless核心技术与实践》一书中摘录的微服务与Serverless的对比：


![image-20211213145603882](http://dockone.io/uploads/article/20211201/53f0891559ed10613935696a692eec7f.png)





### 对比存储过程

如果你对数据库的存储过程有了解，那么理解FaaS就简单多了。在实际的业务场景中，我了解到某些业务逻辑用存储过程来实现，其实这些存储过程都可以视为FaaS（虽然有些局限）。

我们撰写的存储过程就是函数代码，然后交由数据库去执行，根据存储过程的API来进行调用。没有调用时，这些存储过程似乎也没有消耗任何数据库资源。



当然，存储过程有很多的问题，这些问题也是造成不推荐使用存储过程的原因：

1. 依赖数据库厂商，比如Oracle的存储过程只能在Oracle数据库执行；
2. 只能在数据库环境中执行，难以调试；
3. 纯代码片，难以管理，无法做版本控制。

那么对比下FaaS，看看FaaS是不是也有同样的问题：

第一条，直接排除，因为我们开发FaaS代码语言不限，理论上，任何语言、任何框架都可以用来开发函数代码；

第二条，因为FaaS是纯代码，在本地调试倒是没问题，但是如果需要调用BaaS，如何与生产环境一致以及端到端的调试可能是个问题；

第三条，如果FaaS平台上有成千上万的函数，版本控制倒是没有问题，但是函数管理也确实是需要解决的问题。



### 对比PaaS

我们知道，云计算的三种服务模式分别是：IaaS、PaaS、SaaS。

IaaS提供底层基础设施：服务器、存储等。SaaS就是托管在云计算中的应用。

PaaS作为SaaS的供应商，提供开发工具、中间件、数据库管理以及操作系统等构建程序所需的东西，使团队能够快速部署和扩展应用程序，而无需手动配置和管理容器和操作系统。

如果SaaS是需要造房子的客户，那么PaaS就是提供建房子所需的重型设备和电动工具的人，这些工具当然由PaaS的人负责维护。SaaS则为租用这些工具而付费。



以下对标了FaaS和PaaS部分内容的区别：

| 对比         | FaaS                                                     | PaaS                                                   |
| ------------ | -------------------------------------------------------- | ------------------------------------------------------ |
| 面向开发者   | 函数                                                     | 应用                                                   |
| 开发框架     | 不提供                                                   | 提供                                                   |
| 开发语言限制 | 基本上可认为无限制                                       | 有限制，根据开发框架而定                               |
| 调试难度     | 困难<br>如果需要用到后端BaaS，开发环境难以和生产环境一致 | 一般<br>平台一般会提供灰度/沙箱环境供调试              |
| 依赖         | 供应商的锁定<br>BaaS和函数事件的接口都得需要遵循平台标准 | 供应商的锁定<br>不同供应商的平台存在差异，换平台代价高 |
| 计费方式     | 按需计费，比如根据函数调用量                             | 根据工具来定价                                         |
| 运行时       | 只有事件驱动时，函数才会运行                             | 应用一直在运行                                         |
| 缩放         | 自动伸缩                                                 | 需要程序代码里控制，或者人为的扩缩容                   |
| 性能问题     | 存在冷启动的问题                                         | 存在瓶颈的问题                                         |



PaaS 和FaaS大部分是相似的，对于这两者，开发人员所需要担心的就是编写和上传代码，而厂商则处理所有后端进程。

只不过使用PaaS的开发者面向的是应用，而使用FaaS的开发者面向的是函数。

开发框架和语言限制上，必须遵循PaaS供应商的标准，一般是要求Java语言，Spring boot框架；而FaaS则不会有太多的限制，供应商一般会支持所有的高级语言。

不过，由于FaaS运行时更具动态性，与IaaS和PaaS相比，调试可能更具挑战性。

在计费上，FaaS要节约的多，也更精确，根据函数代码的使用来计费；而PaaS的费用就得需要看供应商对PaaS工具的定价了。

另外，FaaS和PaaS在运维方面最大的差异在于缩放能力。对于大部分PaaS平台，用户依然需要考虑缩放。但是对于FaaS应用，这种问题完全是透明的。就算将PaaS应用设置为自动缩放，依然无法在具体请求的层面上进行缩放，而FaaS应用在成本方面效益就高多了。



如果你还是无法区别Serverless和PaaS，AWS云架构战略副总裁Adrian Cockcroft曾经针对两者的界定给出了一个简单的方法：“如果你的PaaS能够有效地在20毫秒内启动实例并运行半秒,那么就可以称之为Serverless”。



## Serverless的优点

一言以概之：**简单交给用户，复杂留给平台**。

1. **零服务器运维**

   serverless通过消除维护服务器资源所涉及的开销，显着改变了运行软件应用程序的成本模型。使用者无需运维服务器、虚拟机、容器等资源，只需要关注业务的运维。

2. **灵活的可扩展性**

   serverless的FaaS或BaaS产品可以即时而精确地扩展，以处理每个单独的传入请求。对于开发人员来说，serverless平台没有“预先计划容量”的概念，也不需要配置“自动缩放”的触发器或规则。缩放可以在没有开发人员干预的情况下自动进行。完成请求处理后，serverless FaaS会自动收缩计算资源，因此不会有空闲容量。

3. **更"绿色"的计算**

   从消费者的角度来看，serverless产品的最大好处之一是空闲容量不会产生任何成本。例如，serverless计算服务不对空闲虚拟机或容器收费; 换句话说，当代码没有运行或者没有进行有意义的工作时，不收费。 对于数据库，数据库引擎容量空闲等待请求时无需收费。当然，这不包括其他成本，例如有状态存储成本或添加的功能/功能/特性集。

4. **简化开发模式**

   开发者只需了解输入输出处理（通常为JSON）及如何处理业务逻辑，减少开发者对开发语言或框架认知的负担。比如，即使开发者不理解Java并发编程，也能轻松实现高并发应用的支持，因为平台会自动根据请求数量扩展函数实例

5. **更低的成本**

   综上，只需要关注业务的运维，降低运维成本；为代码的运行而付费，降低使用成本；整个应用程序组件被商品化，降低开发成本。



## Serverless的缺点

1. **性能的问题**

   由于Serverless是按需计费，应用在空闲时不会存在实例，当事件触发时，实例从0到1的冷启动可能会有较大的延时。

   此外，函数是运行在容器中，从容器外到容器内、函数与函数之间的通信、网格代理等等，可能还会存在网络调用链路较长等问题。

2. **调试的问题**

   开发交给平台的一般都是应用或函数代码，我们很难在本地开发环模拟出一个Serverless平台出来，因此代码调试比较困难；

   此外，如果代码里使用BaaS服务，可能还比较难以验证调用的结果是否我们预期。

3. **无状态的问题**

   开发者无法控制Serverless实例是否还存在，函数执行完实例很可能就被删除了。如果需要临时存储一些数据，比如记录上次文件上传的地址，可能会面临丢失的风险。因此，即视如此简单的临时数据，开发者可能都不得不要使用类似Redis这样的BaaS服务。

4. **平台供应商锁定**

   一些FaaS事件/消息的接口，以及BaaS产品，不同供应商可能标准不一样，会存在平台供应商锁定的风险。



## 使用场景

考虑到Serverless的缺点，如果对实时响应没有太高的要求（秒级以内），可能可以考虑使用Serverless计算。

Serverless 比较适合以下场景：

- 异步的并发，组件可独立部署和扩展
- 应对突发或服务使用量不可预测（主要是为了节约成本，因为 Serverless 应用在不运行时不收费）
- 短暂、无状态的应用，对冷启动时间不敏感
- 需要快速开发迭代的业务（因为无需提前申请资源，因此可以加快业务上线速度）



一些典型的使用场景如下：

1. 通知类的场景

   比如如某个系统的监控，触发了告警，然后需要下发短信或邮件，告警被触发后的动作就可以考虑编写一个函数来实现。

2. 业务流程的场景

   摘录自CNCF白皮书：当与管理和协调function一起部署时，在业务流程中执行一系列步骤的微服务工作负载的编排是serverless计算的另一个好用例。执行特定业务逻辑的function（例如订单请求和批准，股票交易处理等）可以与有状态管理器一起安排和协调。来自客户端门户的事件请求可以由这样的协调function提供服务，并传递给适当的serverless function。

3. 数据流的处理

   比如日志采集器，采集到日志数据，以数据流的形式作为事件源，触发数据解析函数，函数对日志数据解析，做提取、转换、加载等逻辑操作，再存储到数据库中。

4. 聊天机器人

   摘录自CNCF白皮书：与人类交互不一定需要毫秒级别的响应时间，并且在许多方面，稍微延迟让回复人类的机器人对话感觉更自然。因此，等待从冷启动加载function的初始等待时间可能是可接受的。





# 参考资料

[敖小剑的博客—Serverless学习笔记 (skyao.io)，总结的比较好](https://skyao.io/learning-serverless/docs.html)

[从IaaS到FaaS—— 讲述Serverless架构的前世今生 | 亚马逊AWS官方博客 (amazon.com)](https://aws.amazon.com/cn/blogs/china/iaas-faas-serverless/)

[无服务器架构（Serverless Architectures） | serverless-zhcn (amio.github.io)](https://amio.github.io/serverless-zhcn/)

[对Serverless关键技术描述比较清楚的文章：《Serverless：微服务架构的终极模式》. DockOne.io](http://dockone.io/article/2434715)

[Mike Roberts关于无服务器体系结构的深入研究：《Serverless Architectures》](https://martinfowler.com/articles/serverless.html): 非常经典，中文翻译版本见 [Serverless架构综述](http://dockone.io/article/1460)或者[serverless-zhcn 的理解(amio.github.io)](https://amio.github.io/serverless-zhcn/)

[什么是无服务器计算_它可以提供怎样的后端服务以及使用无服务器架构的优势 | Cloudflare](https://www.cloudflare.com/zh-cn/learning/serverless/what-is-serverless/)

[加州大学伯克利关于Serverless的观点]()

[华为Serverless核心技术与实践]()

[What is BaaS? | Backend-as-a-Service vs. serverless | Cloudflare](https://www.cloudflare.com/zh-cn/learning/serverless/glossary/backend-as-a-service-baas/)