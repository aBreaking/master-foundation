knative安装



# 安装istio

有一个非常不错网页，可以让你当场体验istio的安装以及入门：[Get Started with Istio and Kubernetes | Istio | Katacoda](https://www.katacoda.com/courses/istio/deploy-istio-on-kubernetes)



国内根本访问不了git.io以及k8s.io这样的网址，所以通过如下curl的方式根本就下不下来istio：

```shell
# 这样不得性，国内网不通。
curl -L https://git.io/getLatestIstio | ISTIO_VERSION=1.0.0 sh -
```



接下来介绍如果手动在本地安装istio。

1. 首先去github上把istio最新版本下载下来（我此时的版本是istio-1.11.4），下载地址：

2. 然后把下载的安装包放在主机上，解压；

3. 设置环境变量

   ```shell
   export PATH="$PATH:/root/istio-1.11.4/bin"
   ```

4. 此时可以使用`istioctl`命令，在学习、实验环境可以选择 demo 这个 profile 进行安装

   ```
   istioctl install --set profile=demo -y
   ```

5. 此时可以用如下kubectl命令，查看istio相应的namespace和pod是否已经创建

   ```
   $ kubectl get ns |grep istio
   $ kubectl get pods -n istio-system
   $ kubectl get crd |grep istio
   ```

6. 如果istio的namespace都为running，那么表示安装成功；

   ```shell
   root@abreaking-PC:~# kubectl get pods -n istio-system
   NAME                                    READY   STATUS    RESTARTS   AGE
   istio-egressgateway-756d4db566-tq85j    1/1     Running   0          26m
   istio-ingressgateway-8577c57fb6-zkkkr   1/1     Running   0          26m
   istiod-5847c59c69-mxtw6                 1/1     Running   0          31m
   
   ```

   ```shell
   root@abreaking-PC:~# kubectl get crd |grep istio
   authorizationpolicies.security.istio.io               2021-10-26T02:07:57Z
   destinationrules.networking.istio.io                  2021-10-26T02:07:58Z
   envoyfilters.networking.istio.io                      2021-10-26T02:07:58Z
   gateways.networking.istio.io                          2021-10-26T02:07:58Z
   istiooperators.install.istio.io                       2021-10-26T02:07:58Z
   peerauthentications.security.istio.io                 2021-10-26T02:07:58Z
   requestauthentications.security.istio.io              2021-10-26T02:07:58Z
   serviceentries.networking.istio.io                    2021-10-26T02:07:58Z
   sidecars.networking.istio.io                          2021-10-26T02:07:58Z
   telemetries.telemetry.istio.io                        2021-10-26T02:07:58Z
   virtualservices.networking.istio.io                   2021-10-26T02:07:58Z
   workloadentries.networking.istio.io                   2021-10-26T02:07:59Z
   workloadgroups.networking.istio.io                    2021-10-26T02:07:59Z
   
   ```

   ```
   root@abreaking-PC:~# kubectl api-resources |grep istio
   istiooperators                    iop,io          install.istio.io/v1alpha1                   true         IstioOperator
   destinationrules                  dr              networking.istio.io/v1beta1                 true         DestinationRule
   envoyfilters                                      networking.istio.io/v1alpha3                true         EnvoyFilter
   gateways                          gw              networking.istio.io/v1beta1                 true         Gateway
   serviceentries                    se              networking.istio.io/v1beta1                 true         ServiceEntry
   sidecars                                          networking.istio.io/v1beta1                 true         Sidecar
   virtualservices                   vs              networking.istio.io/v1beta1                 true         VirtualService
   workloadentries                   we              networking.istio.io/v1beta1                 true         WorkloadEntry
   workloadgroups                    wg              networking.istio.io/v1alpha3                true         WorkloadGroup
   authorizationpolicies                             security.istio.io/v1beta1                   true         AuthorizationPolicy
   peerauthentications               pa              security.istio.io/v1beta1                   true         PeerAuthentication
   requestauthentications            ra              security.istio.io/v1beta1                   true         RequestAuthentication
   telemetries                       telemetry       telemetry.istio.io/v1alpha1                 true         Telemetry
   
   ```

   

# 安装knative

同样的，文明上网的原因，官网上给的knative的crd也根本安装不了，因为knative需要依赖其他组件，而这些组件都在k8s的镜像仓库。

knative的安装可能比较麻烦；

knative主要由三个组件构成：serving、eventing还有build。目前build已经独立出来了，成为了Tekton；所以在本地环境安装knative只需要安装serving和eventing即可。



由于官网上的安装不得行，那么只有另辟蹊径了。



好在已经有前人把knative的阿里云镜像给做出来了，该镜像地址：https://github.com/knative-sample/knative-release。

## knative-serving安装

1. 把knative-serving的crd文件下载下来；

   ```shell
   wget https://github.com/knative-sample/knative-release/blob/b38cd719e7eb43bb0e8df33fbe844937d65b8e03/serving/serving.yaml
   ```

   注：该文件其实还有点问题，webhood的env配置有误，所以建议你还是直接使用附件serving.yaml;

2. 部署crd文件

   ```shell
   $ kubectl apply -f serving.yaml
   clusterrole.rbac.authorization.k8s.io/knative-build-admin created
   serviceaccount/build-controller created
   clusterrolebinding.rbac.authorization.k8s.io/build-controller-admin created
   customresourcedefinition.apiextensions.k8s.io/builds.build.knative.dev created
   customresourcedefinition.apiextensions.k8s.io/buildtemplates.build.knative.dev created
   ...
   ...
   ```

   

3. 查看Knative-serving的状态

   ```
   $ kubectl get pods -n knative-serving
   E                                     READY   STATUS             RESTARTS       AGE
   activator-5dd4b57449-8dg6q               1/2     ImagePullBackOff   0              4h11m
   activator-7ccbc7c9f5-2m6zf               2/2     Running            0              7h1m
   autoscaler-5579b7ddd8-d9wkh              2/2     Running            1 (4h8m ago)   4h11m
   autoscaler-hpa-597d86c87f-db6vj          1/1     Running            0              4h11m
   controller-7f477c7c55-pvbhx              1/1     Running            0              7h9m
   domain-mapping-5557d5d995-7nrwt          0/1     ImagePullBackOff   0              31h
   domainmapping-webhook-646496fddc-bpgkb   0/1     ImagePullBackOff   0              31h
   networking-istio-5b794fd9f-m6dhn         1/1     Running            0              4h11m
   webhook-6f45fff4b6-mssw7                 1/1     Running            0              35m
   
   ```

   需要注意看webhook容器是否是"running"，我执行到这一步遇到了webhook的坑，详见后面的“一些问题”。

4. 到这一步其实knative已经就可以用了，那么接下来可以继续使用官网的demo即可。

   

## knative-eventing安装

安装步骤同上，只是替换掉

1. 把knative-eventing的crd文件下载下来；

```shell
wget https://github.com/knative-sample/knative-release/blob/b38cd719e7eb43bb0e8df33fbe844937d65b8e03/eventing/eventing.yaml
```

TODO





# 一些问题

## knative-serving的webhook组件容器启动失败的问题

执行`kubectl apply`后发现webhook容器没有启动成功：





**问题原因**：从[这个地址]([knative-release/serving.yaml at b38cd719e7eb43bb0e8df33fbe844937d65b8e03 · knative-sample/knative-release (github.com)](https://github.com/knative-sample/knative-release/blob/b38cd719e7eb43bb0e8df33fbe844937d65b8e03/serving/serving.yaml))上拉取的serving.yaml里webhook的env有点问题，对比官网的yaml，如下：

![image-20211026181645891](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211026181645891.png)



解决方式：把上述图片中官网的env配置复制到你的serving.yaml中对应位置即可。



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

# 



# 参考资料

knative官网安装文档：https://github.com/knative/serving/；https://github.com/knative/eventing

[国内在Minikube上搭建Knative及示例演示 – 滴滴云博客 (didiyun.com)](https://blog.didiyun.com/index.php/2018/11/23/minikube-knative/#2.2 Knative 部署)

[docs/README.md at main · knative/docs (github.com)](https://github.com/knative/docs/blob/main/docs/serving/samples/hello-world/helloworld-go/README.md)

https://knative.club/01deploy/knative-install