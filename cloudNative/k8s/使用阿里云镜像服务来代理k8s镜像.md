使用阿里云镜像服务来代理k8s镜像

# Preface

由于为文明上网的原因，国内根本拉取不了谷歌k8s仓库的镜像；

比如用docker拉取如下地址的镜像是不得行的：

```shell
[root@k8s-vm03 ~]``# docker pull k8s.gcr.io/coredns:1.6.5
Error response from daemon: Get https:``//k8s``.gcr.io``/v2/``: net``/http``: request canceled ``while` `waiting ``for` `connection (Client.Timeout exceeded ``while` `awaiting headers)
```



一种解决方式可以直接阿里云代理，即替换`k8s.gcr.io`的前缀为`registry.aliyuncs.com/google_containers/`，如：

```shell
[root@k8s-vm03 ~]``# docker pull registry.aliyuncs.com/google_containers/coredns:1.6.5
1.6.5: Pulling from google_containers``/coredns
c6568d217a00: Pull complete
fc6a9081f665: Pull complete
Digest: sha256:608ac7ccba5ce41c6941fca13bc67059c1eef927fd968b554b790e21cc92543c
Status: Downloaded newer image ``for` `registry.aliyuncs.com``/google_containers/coredns``:1.6.5
registry.aliyuncs.com``/google_containers/coredns``:1.6.5
```

然后重命名tag、删除之前的镜像即可。

参考：[无法访问k8s.gcr.io下载镜像问题解决办法 - 高宏宇 - 博客园 (cnblogs.com)](https://www.cnblogs.com/gaohongyu/p/14275873.html)

但是这种方式可能拉取不了最新的k8s镜像。



接下来介绍另一种方式，自己制作k8s镜像，通过阿里云镜像服务来代理。

你需要准备：

1. 一个阿里云帐户
2. 一个github帐户

理论上来说，通过这种方式，可以拉取任何互联网上的容器镜像。


# 创建github镜像仓库

1. 创建仓库

需要创建一个github仓库，来存放你的镜像文件。比如可以参考我的的仓库：[aBreaking/kubernetes-images: kubernetes镜像 (github.com)](https://github.com/aBreaking/kubernetes-images)

2. 值作Dockerfile文件

比如我要拉取如下的镜像：

```
gcr.io/knative-releases/knative.dev/serving/cmd/webhook@sha256:xxxxxxxxx3303f72f13e36e7b0d0576ac2d68fb377f4faa7c8c1
```

那么我需要创建一个镜像仓库文件夹`webhook`，里面放一个`Dockerfile`文件。如下：

![image-20211103093818404](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103093818404.png)

Dockerfile文件内容就是要拉取的镜像地址：

```
FROM gcr.io/knative-releases/knative.dev/serving/cmd/webhook@sha256:xxxxxxxxx3303f72f13e36e7b0d0576ac2d68fb377f4faa7c8c1
```



# 阿里云容器镜像服务

1. 进入容器镜像服务，创建个人实例

   位置：控制台->产品与服务->**容器镜像服务**，进入容器镜像服务页面（没找到的话直接搜索`容器镜像服务`关键字）。

   在实例列表中**创建个人实例**。

   ![image-20211103094433140](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103094433140.png)

   

2. 创建命令空间

   进入个人实例后，选择 仓库管理->**命令空间**，创建命令空间。注意：个人版最多只能创建3个。如：
   ![image-20211103094637701](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103094637701.png)

3. 创建镜像仓库

   选择仓库管理 -> 镜像仓库，来**创建镜像仓库**，注意仓库类型要选择**公开**。

   ![image-20211103094940309](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103094940309.png)

   

   点击下一步后，选择Github代码源，然后选择上面创建的github镜像仓库，勾选**海外机器构建**，然后点击创建即可。注：如果阿里云没有绑定github帐户，这一步会先提示你先去绑定账号，绑定完成之后回来继续操作即可。

   ![image-20211103095252920](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103095252920.png)

   

4. 构建镜像

   镜像仓库创建完后，直接点击进入该镜像仓库。然后选择**构建**。

   此时，我们开始**添加规则**。选择分支和前面你创建的github仓库Dockerfile目录路径。

   注意：Dockerfile目录，就是前面github里的镜像仓库文件夹的路径，镜像版本可以随意命令，一般建议跟你要拉取的镜像版本一致。

   ![image-20211103100603978](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103100603978.png)

   

5. 立即构建

   构建规则添加完毕后，点击立即构建，那么就可以看到构建状态，状态为成功，那么该镜像版本就可以直接使用了。

   ![image-20211103100638370](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103100638370.png)

# 使用镜像

在刚才镜像仓库的页面上，**基本信息**里就有操作指南，你可以直接按照指南来进行docker 拉取镜像的操作。

![image-20211103100322024](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103100322024.png)



接下来介绍：就是拿到了官方文档上的crd文件，如何替换掉里面不可用镜像地址。

比如我现在正部署knative-serving，在官方文档上拿到了yaml文件，发现里面需要拉取7个镜像。

1. 首先先在github上把这7个镜像文件创建好。

   ![image-20211103103511494](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103103511494.png)

2. 使用阿里云镜像服务服务将镜像制作好：

   ![image-20211103103605041](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103103605041.png)

3. 在本地将阿里云的镜像拉取下来

   ![image-20211103103821695](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103103821695.png)

4. 替换yaml文件中的镜像地址为本地`docker images`里面的镜像

   即将原来`gcr.io`开头的那些镜像地址全部替换本地的镜像。

   ![image-20211103104137688](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103104137688.png)

5. kubectl开始部署

   yaml文件里内容替换完毕后，此时直接使用`kubectl apply -f xxxx.yaml`命令，看到状态都是`running`，部署就算成功了，如下：

   ![image-20211103104415755](C:\Users\MI\AppData\Roaming\Typora\typora-user-images\image-20211103104415755.png)

# 参考资料

[无法访问k8s.gcr.io下载镜像问题解决办法 - 高宏宇 - 博客园 (cnblogs.com)](https://www.cnblogs.com/gaohongyu/p/14275873.html)

[k8s.gcr.io、gcr.io仓库的镜像下载 - 粽先生 - 博客园 (cnblogs.com)](https://www.cnblogs.com/straycats/p/14405513.html?spm=a2c6h.12873639.0.0.1ab27770PhT6Vd)