# Preface 

社区版的kubernetes自己去安装实在是太恼火了，好在有替代方案----minikube。可以在minikube上体验kubernetes的相关功能。所以我们直接在本地实验环境来安装minikube，来入门学习kubernetes相关的知识；



# 安装前准备

推荐在linux主机上安装，我本地用的是 deepin/ubuntu。

安装minikube的主机必要配置：

- 2 CPUs or more
- 2GB of free memory
- 20GB of free disk space
- Internet connection
- Container or virtual machine manager, such as: [Docker](https://minikube.sigs.k8s.io/docs/drivers/docker/), [Hyperkit](https://minikube.sigs.k8s.io/docs/drivers/hyperkit/), [Hyper-V](https://minikube.sigs.k8s.io/docs/drivers/hyperv/), [KVM](https://minikube.sigs.k8s.io/docs/drivers/kvm2/), [Parallels](https://minikube.sigs.k8s.io/docs/drivers/parallels/), [Podman](https://minikube.sigs.k8s.io/docs/drivers/podman/), [VirtualBox](https://minikube.sigs.k8s.io/docs/drivers/virtualbox/), or [VMware](https://minikube.sigs.k8s.io/docs/drivers/vmware/)



Container 容器我本地安装是docker；

# docker的安装

from : [Docker_apt安装 - 简书 (jianshu.com)](https://www.jianshu.com/p/9b1b514e66fc)

1. 如果之前有安装过docker，可以先卸载：

   ```
   apt-get remove docker docker-engine docker.io
   ```

   

2. 更新apt安装包索引

   ```
   apt-get update
   ```

   

3. 安装软件包以允许apt通过HTTPS

   ```
   sudo apt-get install apt-transport-https  ca-certificates curl software-properties-common
   ```

   

4. 添加Docker官方的GPG密钥

   ```
   curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
   ```

   

5. 安装稳定版仓库

   ```
   sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
   ```

   

6. 安装docker

   ```
   apt-get install docker.io 
   ```

7. 启动docker服务

   ```
   systemctl start docker
   ```



此时可以通过`docker version`命令来验证docker是否安装成功。

![image-20211021174158972](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211021174158972.png)



注：由于国内访问docker镜像库很是缓慢，所以建议配置阿里云的代理，通过修改daemon配置文件/etc/docker/daemon.json来使用加速器：

```shell
$ cd /etc/docker
# 在daemon.json文件末尾追加如下配置：
$ sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://dhx52mku.mirror.aliyuncs.com"]
}
EOF

# 重启docker
$ sudo systemctl daemon-reload
$ sudo systemctl restart docker
```



# 安装minikube

## 安装步骤

minikube的官网：[minikube start | minikube (k8s.io)](https://minikube.sigs.k8s.io/docs/start/)

官网上的安装minikube网速实在太慢了，推荐使用阿里云的镜像来进行安装minikube，更多信息见：[Minikube - Kubernetes本地实验环境-阿里云开发者社区 (aliyun.com)](https://developer.aliyun.com/article/221687)

1. 直接安装minikube

   ```
   curl -Lo minikube https://kubernetes.oss-cn-hangzhou.aliyuncs.com/minikube/releases/v1.23.1/minikube-linux-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/
   ```

2. 启动minikube

   ```shell
   # 默认会使用docker容器。 
   minikube start
   ```

3. 让minikube下载kubectl客户端命令工具

   ```shell
   minikube kubectl -- get po -A
   ```



此时就可以使用kubectl命令来进行各种kubernetes的操作了。



## 开启控制台

执行以下命令开启控制台

```
minikube dashboard
```

![image-20211021175445795](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211021175445795.png)

直接点击上面的url，即可看到控制台页面：

![image-20211021180206426](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211021180206426.png)



## 直接使用

此时的`minikube kubectl --`就相当于k8s里的`kubectl`命令，当然我们实际不会这样使用，我们可以minikube的命令给alias一下：

```bash
$ alias kubectl="minikube kubectl --"
```

此时再直接运行`kubectl`命令：

```bash
$ kubectl
kubectl controls the Kubernetes cluster manager.

 Find more information at: https://kubernetes.io/docs/reference/kubectl/overview/

Basic Commands (Beginner):
  create        Create a resource from a file or from stdin
  expose        Take a replication controller, service, deployment or pod and expose it as a new
Kubernetes service
  run           在集群中运行一个指定的镜像
  set           为 objects 设置一个指定的特征

Basic Commands (Intermediate):
  explain       Get documentation for a resource
  get           显示一个或更多 resources
  edit          在服务器上编辑一个资源
  delete        Delete resources by file names, stdin, resources and names, or by resources and
label selector
...
```



好了，就可以愉快的k8s玩耍了



# 一些常见问题

* 执行`minikube start`出现 **The "docker" driver should not be used with root privileges** 的报错.

如果是本地测试环境，根本就不需要考虑那么多，直接执行以下命令，强制使用docker：

```shell
minikube start --force --driver=docker
# 或者使用阿里云镜像启动
minikube start --force --driver=docker --image-mirror-country cn --iso-url=https://kubernetes.oss-cn-hangzhou.aliyuncs.com/minikube/iso/minikube-v1.5.0.iso --registry-mirror=https://xxxxxx.mirror.aliyuncs.com 
```

* 执行`istioctl install`命令时一直出现如下问题：

```
Processing resources for Istiod. Waiting for Deployment/service-mesh-system/istiod                                
✘ Istiod encountered an error: failed to wait for resource: resources not ready after 5m0s: timed out waiting for the condition
```

此时使用`kubectl get pods -n istio-system`命令发现`istio-egressgateway`和`istio-ingressgateway`都处于`ContainerCreateing`状态。

我当时的解决方式：等了很久（10分钟左右），自动就好了。原因暂时未知，google说可能是node的问题。



# 附录

## 使用阿里云代理k8s.gcr.io镜像仓库

1. 国内根本访问不了k8s的镜像库：`k8s.gsc.io`。比如下载k8s.gcr.io``/coredns``:1.6.5镜像，在国内默认是下载失败的！


```
[root@k8s-vm03 ~]``# docker pull k8s.gcr.io/coredns:1.6.5
Error response from daemon: Get https:``//k8s``.gcr.io``/v2/``: net``/http``: request canceled ``while` `waiting ``for` `connection (Client.Timeout exceeded ``while` `awaiting headers)
```


2. 这时候去指定国内的阿里云镜像代理仓库进行下载
```
[root@k8s-vm03 ~]``# docker pull registry.aliyuncs.com/google_containers/coredns:1.6.5
1.6.5: Pulling from google_containers``/coredns
c6568d217a00: Pull complete
fc6a9081f665: Pull complete
Digest: sha256:608ac7ccba5ce41c6941fca13bc67059c1eef927fd968b554b790e21cc92543c
Status: Downloaded newer image ``for` `registry.aliyuncs.com``/google_containers/coredns``:1.6.5
registry.aliyuncs.com``/google_containers/coredns``:1.6.5
```

 

3. 然后打tag，并删除之前从代理仓库下载的镜像
```
[root@k8s-vm03 ~]``# docker tag registry.aliyuncs.com/google_containers/coredns:1.6.5 k8s.gcr.io/coredns:1.6.5
```


```
[root@k8s-vm03 ~]``# docker images
REPOSITORY                    TAG         IMAGE ID      CREATED       SIZE
k8s.gcr.io``/coredns`                `1.6.5        70f311871ae1    5 months ago    41.6MB
registry.aliyuncs.com``/google_containers/coredns`  `1.6.5        70f311871ae1    5 months ago    41.6MB
```

 

```
[root@k8s-vm03 ~]``# docker rmi registry.aliyuncs.com/google_containers/coredns:1.6.5
Untagged: registry.aliyuncs.com``/google_containers/coredns``:1.6.5
Untagged: registry.aliyuncs.com``/google_containers/coredns``@sha256:608ac7ccba5ce41c6941fca13bc67059c1eef927fd968b554b790e21cc92543c
```

 

```
[root@k8s-vm03 ~]``# docker images
REPOSITORY                TAG         IMAGE ID      CREATED       SIZE
k8s.gcr.io``/coredns`            `1.6.5        70f311871ae1    5 months ago    41.6MB
```

 

```
最终发现我们想要的k8s.gcr.io``/coredns``:1.6.5镜像被成功下载下来了！
```

 

```
最后要记得：
确定imagePullPolicy镜像下载策略是IfNotPresent，即本地有镜像则使用本地镜像，不拉取！
或者将下载好的镜像放到harbor私有仓库里，然后将image下载地址指向harbor仓库地址。

以上总结三个步骤：
# docker pull registry.aliyuncs.com/google_containers/coredns:1.6.5
# docker tag registry.aliyuncs.com/google_containers/coredns:1.6.5 k8s.gcr.io/coredns:1.6.5
# docker rmi registry.aliyuncs.com/google_containers/coredns:1.6.5
```

# 参考资料

[Minikube - Kubernetes本地实验环境-阿里云开发者社区 (aliyun.com)](https://developer.aliyun.com/article/221687)

[minikube start | minikube (k8s.io)](https://minikube.sigs.k8s.io/docs/start/)

[安装Istio - 肖祥 - 博客园 (cnblogs.com)](https://www.cnblogs.com/xiao987334176/p/14236554.html)

