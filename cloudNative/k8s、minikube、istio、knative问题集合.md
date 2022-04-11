k8s、minikube、istio、knative问题集合

## istio安装失败，一些组件pending或者crashloopoff

istio的版本还是建议高版本，直接最新即可，官方给的建议是1.9.5版本；

其次，需要注意：istio对内存和cpu的要求比较严格，建议内存8G及以上，CPU8核及以上。

只要版本没问题，内存和CPU的配置达到要求，基本上istio安装没有问题。



## helloworld服务启动后，一直revisionMissing

这个问题有点无解，个人认为原因还是出在demo上，官方给的demo个人感觉还是有问题的，拉取下来居然好几百M。

后来我解决方式是自己制作demo，替换官方给的demo镜像，然后也出现过revisionMission的问题，多等了一会，然后就好了。



## istio-ingressgateway服务一直pending，EXTERNAL-IP为空

这个问题是出在minikube上。先看看你的istio-ingressgateway服务的type是不是LoadBalancer，如果是，还得需要minikube做一个操作：
开启一个新得窗口，运行命令：`minikube tunnel`。 然后就能解决。



#  ReconcileIngressFailed

 检查istio是否正常



minikube addons enable ingress

重新安装istio

see : https://kubernetes.feisky.xyz/extension/ingress/minikube-ingress

# The connection to the server 192.168.49.2:8443 was refused - did you specify the right host or port?

**问题描述**

minikube执行`kubectl version`出现如下错误：

```
oot@abreaking-PC:~/knative# kubectl version
Client Version: version.Info{Major:"1", Minor:"22", GitVersion:"v1.22.1", GitCommit:"632ed300f2c34f6d6d15ca4cef3d3c7073412212", GitTreeState:"clean", BuildDate:"2021-08-19T15:45:37Z", GoVersion:"go1.16.7", Compiler:"gc", Platform:"linux/amd64"}
The connection to the server 192.168.49.2:8443 was refused - did you specify the right host or port?

```

问题解决：一般是代理设置有误，重启就好了。



# sslip.io域名无法访问





[单机部署 - Kubernetes指南 (feisky.xyz)](https://kubernetes.feisky.xyz/setup/single)