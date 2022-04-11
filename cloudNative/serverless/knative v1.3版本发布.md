Serverless开源技术研究周报@20220406



## knative v1.3版本发布

3月28号，[Knative v1.3](https://knative.dev/blog/releases/announcing-knative-v1-3-release/)版本发布。从knativev1.0版本以后，knative社区基本上保持一个月一个版本发布的节奏。本次发布的版本并没太多亮点，以修复bug为主。

不过我注意到knative的官网首页有了较大的变化，官方对knative的定义貌似进行重定义。



以下是[Knative v1.0](https://knative.dev/v1.0-docs/)首页上knative的定义：

> Enterprise-grade Serverless on your own terms.
> Kubernetes-based platform to deploy and manage modern serverless workloads.
>
> 企业级的无服务器。
>
> 基于Kubernetes的平台，用于部署和管理现代化serverless的工作负载。



以下是[Knative v1.3](https://knative.dev/docs/)首页上对knative的定义：

>Knative is an Open-Source Enterprise-level solution to build Serverless and Event Driven Applications。
>
>Serverless Containers in Kubernetes environments.
>
>Knative是一个开源的企业级构建Serverless和事件驱动应用的解决方案。
>
>Serverless容器在kubernetes的环境中。

前者强调的**工作负载**，后者强调的是**解决方案**。可能官方也意识到Knative不仅仅只是serverless运行时的底座，后续可以演进成为serverless整个生态技术圈的解决方案。



此外呢，knative也正式被接受成为CNCF的孵化项目（incubating project），见：[Knative accepted as a CNCF incubating project - Knative](https://knative.dev/blog/steering/cncf/)。



## Knative-serving

在本地搭建了k8s的学习环境[minikube](https://minikube.sigs.k8s.io/docs/start/)，并部署了[Knative-serving](http://eip.teamshub.com/t/5595781)和[Knative-eventing](http://eip.teamshub.com/t/5607910)这两大组件，跟着Knative的官方文档将弹性伸缩、流量管理的使用在本地跑通了。

关于弹性伸缩本地还有点问题：

根据knative的算法，设置了每个pod支持的并发数：`autoscaling.knative.dev/target: "10"`。启动50个并发，按理应该会自动扩容5个pod。但在我本地测试发现，只有提前设置了最小pod数：`autoscaling.knative.dev/minScale: "1"`，算法才会正确，如果没有设置最小pod数，就会莫名多出好几个pod。

也就是说只有从1-N的伸缩才起作用，从0-N的伸缩就不知道是哪里的问题。

所以我一直怀疑可能是我本地minikube性能方面的原因，因为每次从从0-1启动一个pod，总是需要较长时间。





## 基于Knative自定义控制器开发

上周理解了k8s的operator模式，简单的说：operator=crd+controller。由于k8s本身就是用go语言开发的，所以operator的开发go语言这块生态也较好。比如knative官方就提供了一个Controller开发的模板：[knative-sandbox/sample-controller](https://github.com/knative-sandbox/sample-controller)。

go语言这块我还处于学习阶段，跟java还是有较大的差异。理解了下knative-controller的代码的目录结构，有一些地方还是有点迷惑，比如Reconciler的编码，还有就是相关API，我怎么知道该实现哪些接口，该使用哪些API？

不过代码的细节我没有过于深究，而是在本地学习环境，将该模板工程运行起来了，并能成功地在本地进行调试，见：[kubernetes学习环境搭建及开发调试](http://eip.teamshub.com/t/5797712)。

本周计划做一个事情：就是理解基于knative自定义controller开发。因为controller的开发还是很有必要的，一个原因是以后能够阅读knative的源码；另一个原因是再基于knative可以进行二次的开发，比如knative网关入口这一块（目前默认实现是istio）。还有一个原因，后续fortress基于容器化的的升级迭代，这块的思想我觉得也可以借助。



## Knative func

Knative面向的是serverless应用。在函数这块，knative也推出了一个工具[func](https://github.com/knative-sandbox/kn-plugin-func)。不过当前还处于沙箱(sanbox)环境阶段。

>func is a Client Library and CLI enabling the development and deployment of Functions.
>
>func 是一个客户端库和 CLI，支持函数的开发和部署。

跟一般的faas框架都差不多，主要亮点是与Springboot整合的较好。可能是一开始knative的开源者也有Pivotal（spring团队公司）。

上周也简单研究了下spring native，之前云原生阶段之前，Spring广为诟病的是项目过于臃肿，根本无法满足像serverless这种从0-1秒级启动的场景。所以spring也意识到了这点，因此推出了spring native。现在一个hello world版的spring native项目工程大小仅8M。确实减负了许多。

Spring也推出了函数这一块的功能，[Spring Cloud Function](https://spring.io/projects/spring-cloud-function)，几行代码就可以构建一个[demo函数](https://github.com/salaboy/func-templates/blob/main/springboot/uppercase/src/main/java/uppercase/UpperCaseFunction.java)：

```java
import org.springframework.cloud.function.cloudevent.CloudEventMessageBuilder;
import org.springframework.cloud.function.web.util.HeaderUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.UUID;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.*;

@Component("UppercaseRequestedEvent")
public class UpperCaseFunction implements Function<Message<Input>, Message<Output>> {
  
  @Override
  public Message<Output> apply(Message<Input> inputMessage) {
      HttpHeaders httpHeaders = HeaderUtils.fromMessage(inputMessage.getHeaders());
      Input input = inputMessage.getPayload();
      Output output = new Output();
      output.setInput(input.getInput());
      output.setOperation(httpHeaders.getFirst(SUBJECT));
      output.setOutput(input.getInput() != null ? input.getInput().toUpperCase() : "NO DATA");
      return CloudEventMessageBuilder.withData(output)
        .setType("UpperCasedEvent").setId(UUID.randomUUID().toString())
        .setSubject("Convert to UpperCase")
        .setSource(URI.create("http://example.com/uppercase")).build();
  }
}
```



后面spring函数这块可以与产品同事商量是否有必要投入精力研究一下，可以考虑整合到JCF里。

