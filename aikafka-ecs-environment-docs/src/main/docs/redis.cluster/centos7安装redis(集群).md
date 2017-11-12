#Redis集群安装(redis+集群创建)

1. 下载redis包
```bash
$ wget http://download.redis.io/releases/redis-4.0.0.tar.gz
```
2. 解压
```bash
$ tar xzf redis-4.0.0.tar.gz
```
3. 编译
```bash
$ cd redis-4.0.0
$ make & make install
```
4. 编译完成后，在src目录下，有3个可执行文件redis-server、redis-benchmark、redis-cli、redis-trib.rb，redis-4.0.0目录有redis.conf，然后拷贝到一个目录下。
例如:/usr/local/redis
- redis-server：启动redis节点
- redis-cli redis：客户端，用于连接访问redis
- redis-trib.rb：用于创建集群
- redis.conf ：redis节点配置文件
```bash
$ mkdir -p /usr/local/redis
$ cp redis-server /usr/local/redis
$ cp redis-benchmark /usr/local/redis
$ cp redis-cli /usr/local/redis
$ cp redis-trib.rb /usr/local/redis
$ cp redis.conf /usr/local/redis
$ cd /usr/local/redis
```
5. 创建3个master节点3个slave配置
首先创建conf目录，再在conf下创建7000,7001,7002,8000,8001,8002三个子目录，分别将redis.conf拷贝到7000,7001,7002,8000,8001,8002子目录中
创建好后的目录视图：
```bash
$ mkdir -p conf/7000
$ mkdir -p conf/7001
$ mkdir -p conf/7002
$ mkdir -p conf/8000
$ mkdir -p conf/8001
$ mkdir -p conf/8002

drwxr-xr-x 8 root root    4096 Nov 12 14:08 conf
-rwxr-xr-x 1 root root 2450968 Nov 12 14:04 redis-benchmark
-rwxr-xr-x 1 root root 2605312 Nov 12 14:04 redis-cli
-rw-r--r-- 1 root root   57764 Nov 12 14:05 redis.conf
-rwxr-xr-x 1 root root 5739488 Nov 12 14:04 redis-server
-rwxr-xr-x 1 root root   60843 Nov 12 14:05 redis-trib.rb
[root@aikafka redis]# cd conf/
[root@aikafka conf]# ll
total 24
drwxr-xr-x 2 root root 4096 Nov 12 14:08 7000
drwxr-xr-x 2 root root 4096 Nov 12 14:08 7001
drwxr-xr-x 2 root root 4096 Nov 12 14:08 7002
drwxr-xr-x 2 root root 4096 Nov 12 14:08 8000
drwxr-xr-x 2 root root 4096 Nov 12 14:08 8001
drwxr-xr-x 2 root root 4096 Nov 12 14:08 8002
```
6. 按照如下说明修改每个节点目录下的redis.conf文件，每个节点根据不同的端口来修改配置
```bash
vi conf/7000/redis.conf  
```
```
#意味着允许所有主机连接
bind 0.0.0.0  

#每个Redis实例的端口必须是唯一的
port 7000   

#支持集群 
cluster-enabled yes 

#nodes-7000.conf这个文件不用我们去编辑
cluster-config-file nodes-7000.conf  

#这个文件也不需要编辑
pidfile /var/run/redis_7000.pid   
cluster-node-timeout 5000 

#指定是否在每次更新操作后进行日志记录，
appendonly yes 
 
#以守护进程方式运行
daemonize yes  

#redis-server启动时会在当前目录生成或读取dump.rdb
dir /usr/local/redis 
```
7. 安装前置，安装ruby和rubygems需要安装如下依赖包，若已安装，系统会自动忽略。（系统已安装ruby，无需安装）
```bash
$ yum -y install gcc openssl-devel libyaml-devel libffi-devel readline-devel zlib-devel gdbm-devel ncurses-devel gcc-c++ automake autoconf 
```
8. 下载ruby-2.2.1.tar.gz （系统已安装ruby，无需安装）
```bash
$ wget https://cache.ruby-lang.org/pub/ruby/2.2/ruby-2.2.1.tar.gz
```
9. 解压安装ruby（系统已安装ruby，无需安装）
```bash
$ tar -zxvf ruby-2.2.1.tar.gz
$ cd ruby-2.2.1  
$ ./configure -prefix=/usr/local/ruby    
$ make  
$ make install    
$ cp ruby /usr/local/bin
```
10. 下载rubygems-2.6.12.tgz 
```bash
$ wget https://rubygems.org/rubygems/rubygems-2.6.12.tgz
```
11. 解压并安装rubygems
```bash
$ tar -xvzf rubygems-2.6.12.tgz  
$ cd rubygems-2.6.12  
$ ruby setup.rb  
$ cp bin/gem /usr/local/bin
```
12. 下载redis-3.3.3.gem（这个是用于创建redis集群时用到）
```bash
$ wget https://rubygems.org/downloads/redis-3.3.3.gem
```
13. 在redis-3.3.3.gem当前目录执行如下命令
```bash
$ gem install -l ./redis-3.3.3.gem
```
  到此，redis集群环境已经搭建好了，接下来需要配置redis集群
14. 将3个redis节点分别运行起来
![Alt text](./集群.png)
15. 创建集群
![Alt text](./集群创建.png)

注：外网创建的集群外网可以访问，内网也能访问（内网可访问外网ip），但内网IP创建的集群外网不能访问(仅供参考)

附创建集群脚本：
```bash
$ ./redis-trib.rb  create --replicas 1  47.96.28.236:7000 47.96.28.236:7001 47.96.28.236:7002 47.96.28.236:8000 47.96.28.236:8001 47.96.28.236:8002
```