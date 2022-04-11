使用ko在Kubernetes上构建和部署go应用



最近在研究knative，面临一个问题：knative应用开发完毕，总是需要部署上kubernetes上，过程总是有点繁琐。然后发现了google开源的一个轻量级k8s自动构建的神器：ko，特此研究了一下。

# 概述

> ko 是一个简单、快速的 Go 应用程序容器镜像构建器。
>
> 它非常适合您的映像包含单个 Go 应用程序而对 OS 基础映像没有任何/许多依赖项的用例（例如，没有 cgo，没有 OS 包依赖项）。
>
> ko 通过在本地机器上有效地执行 go build 来构建图像，因此不需要安装 docker。 这可以使其非常适合轻量级 CI/CD 用例。
>
> ko 还包括对简单 YAML 模板的支持，这使其成为 Kubernetes 应用程序的强大工具（见下文）。

综上，ko有点类似CI/CD的功能，仅提供对go应用语言的支持，不过，在我们学习环境上，使用该工具，可以极大简化我们的应用开发后的构建镜像、部署到k8s等操作。

标准的流程总是这样：

1. 应用开发（go语言的应用）完毕；
2. 手写Dockerfile文件
3. docker build 、docker push
4. 手写k8s的yaml文件
5. kubectl apply 

![image-20220121162546173](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20220121162546173.png)

所以这个流程还是有点点繁琐。

为此，google开源了ko工具，基本上一键搞定上述从2到5的步骤，如下：

![image-20220121171528173](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20220121171528173.png)





# 安装

前提：安装了go。

很简单，可以直接从[release](https://github.com/google/ko/releases)最新的包拉下来。解压即用。

或者直接使用go install方式：

```
go install github.com/google/ko@latest
```



# 使用

ko提供了多种功能，比如`ko publish`将代码打成镜像并上传指定仓库；`ko apply`直接将代码构建、部署到k8s里。这里主要讨论的也就是这两个命令，其他的使用请见[官方文档](https://github.com/google/ko)。

在使用之前，需要先设置ko的环境变量。

## ko的环境变量

默认情况下，Ko 依赖的是 Docker 的镜像仓库的认证（ Docker config 文件），即 `~/.docker/config.json`

```
{
        "auths": {
                "https://index.docker.io/v1/": {
                        "auth": "***"
                }
        },
        "HttpHeaders": {
                "User-Agent": "Docker-Client/18.09.1 (linux)"
        }
}

```



如果使用的minikube，使用如下命令，设置ko以使用minikube docker实例

```
eval $(minikube docker-env)
```



设置镜像仓库地址，ko会把制作好的镜像push该仓库。

```
export KO_DOCKER_REPO=docker.io/abreaking
```





## ko publish：构建镜像

这里没有写helloword，我直接应用了knative-eventing文档中自定义事件源的[demo](https://github.com/aBreaking/knative-eventing-source-demo)。当然你也可以用[官方的demo](https://github.com/google/ko/tree/main/test)

我的代码地址：[https://github.com/aBreaking/knative-eventing-source-demo](https://github.com/aBreaking/knative-eventing-source-demo)



该代码的工程主要目录结构如下：

```shell
knative-eventing-source-demo
├── cmd 
│   ├── controller 
│   │   └── main.go # controller 的启动入口文件
│   ├── schema
│   │   └── main.go # 生成 CRD 资源的 工具
│   └── webhook
│       └── main.go # webhook 的入口文件
├── config # 部署文件，可以使用ko apply命令一键部署
│   ├── 300-addressableservice.yaml
│   ├── 300-simpledeployment.yaml
├── .ko.yaml # 覆盖ko的默认容器镜像，否则ko会使用k8s.io的容器
├── ...
```

如上，我需要部署三个3个镜像。这里以controller为例：

执行ko publish命令制作controller的镜像

```shell
# 先cd应用的工程目录下
ko publish ./cmd/controller
```

控制台打印内容如下：

```
2022/01/21 16:06:10 Using base ubuntu for knative.dev/sample-source/cmd/controller
2022/01/21 16:06:15 Building knative.dev/sample-source/cmd/controller for linux/amd64
2022/01/21 16:06:21 Publishing docker.io/abreaking/controller-e5cee97180fd591b81b7b71194e1d4a7:latest
2022/01/21 16:06:26 mounted blob: sha256:ea362f368469f909a95f9a6e54ebe0121ce0a8e3c30583dd9c5fb35b14544dec
2022/01/21 16:06:28 pushed blob: sha256:250c06f7c38e52dc77e5c7586c3e40280dc7ff9bb9007c396e06d96736cf8542
2022/01/21 16:07:05 pushed blob: sha256:d8f7edf025b5220be93824577d882f64102329bb91c52053728037eae519ee84
2022/01/21 16:07:08 pushed blob: sha256:b457239dcad2d513e17b1941f507853171f7a03c0b99ab483424a02f244c5e3e
2022/01/21 16:07:09 docker.io/abreaking/controller-e5cee97180fd591b81b7b71194e1d4a7:latest: digest: sha256:d3c9e06ab3d1238fe7aed80690e1c061b306bdc41dd60bf2f52220bde1c4d635 size: 960
2022/01/21 16:07:09 Published docker.io/abreaking/controller-e5cee97180fd591b81b7b71194e1d4a7@sha256:d3c9e06ab3d1238fe7aed80690e1c061b306bdc41dd60bf2f52220bde1c4d635
docker.io/abreaking/controller-e5cee97180fd591b81b7b71194e1d4a7@sha256:d3c9e06ab3d1238fe7aed80690e1c061b306bdc41dd60bf2f52220bde1c4d635

```

此时可以在dockerhub上看到自动生成的镜像已经推送成功。

![image-20220121171245820](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20220121171245820.png)





## ko apply：部署到k8s 

在工程的config目录下，有一些yaml文件，用于应用的构建部署到k8s上。

比如我们看下500-controller.yaml的主要内容：

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: sample-source-controller
  namespace: knative-samples
  ...
spec:
  ...
  containers:
  - name: controller
  	image: ko://knative.dev/sample-source/cmd/controller
```

我们可以使用`ko apply`命令一键部署config下面所有的yaml

```shell
# 先cd到工程目录
ko apply -f ./config
```

或者指定的yaml：

```shell
ko apply -f ./config/500-controller.yaml
```



此时，我们再去对应namespace（yaml里指定的）去查看pod状态：

```shell
$ kubectl -n knative-samples get pods
NAME                                                              READY   STATUS    RESTARTS   AGE
sample-source-controller-6b69b7f6d6-rfcwp                         2/2     Running   0          75m
```

是不是一键方便了许多。



你会注意到，在yaml文件里指定的镜像地址`image: ko://knative.dev/sample-source/cmd/controller`，这个镜像的格式也就是：`ko://go mod下的路径`，它会被自动替换成ko构建好的镜像。





# 一些问题

###  出现Get "https://gcr.io/v2/": dial tcp 142.251.8.82:443: connect: connection refused的错误
首先需要确认你是否有配置了镜像仓库地址：`KO_DOCKER_REPO`。见上面ko的环境变量。

如果没有指定一个基本的image，ko默认会去使用[gcr.io/distroless/static:nonroot](http://gcr.io/distroless/static:nonroot)，所以我们需要创建一个`.ko.yaml`文件（注意前面也有一个点），来覆盖默认的镜像。

.ko.yaml文件内容如下：

```yaml
defaultBaseImage: docker.io/ubuntu
# 也可以跟上版本号，docker.io/ubuntu:<version>
```



然后再执行操作就可以解决了。



# 参考资料

[ko作者的原文：《ko: fast Kubernetes microservice development in Go》 | by Matthew Moore | Medium](https://medium.com/@mattmoor/ko-fast-kubernetes-microservice-development-in-go-f94a934a7240)

[ko开源项目地址: Build and deploy Go applications on Kubernetes (github.com)](https://github.com/google/ko)