- 1.下载mongodb安装包
```
wget https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-rhel70-3.2.4.tgz
```
- 2.解压
```
tar -zxvf mongodb-linux-x86_64-rhel70-3.2.4.tgz
```
- 3.创建mongodb安装目录
```
mkdir -p /usr/local/mongodb
 ```
- 4.将解压的mongodb文件夹整体复制到上面安装目录
```BASH
mv mongodb-linux-x86_64-rhel70-3.2.4 mongodb
mv  mongodb /usr/local/
```
- 5.在mongodb目录创建logs目录用于存放日志
```bash
cd /usr/local/mongodb
mkdir logs
```
- 6.在mongodb目录创建db目录用于存放数据
```bash
mkdir db
```
- 7.创建mongodb/bin/mongodb.conf文件并写入如下内容：
```text
# idae - MongoDB config start - 2017-11-10

# 设置数据文件的存放目录
dbpath = /usr/local/mongodb/db

# 设置日志文件的存放目录及其日志文件名
logpath = /usr/local/mongodb/logs/mongodb.log

# 设置端口号（默认的端口号是 27017）
port = 27017

# 设置为以守护进程的方式运行，即在后台运行
fork = true

# nohttpinterface = true
nohttpinterface = true
# idae - MongoDB config end - 2017-11-10
```
```text
参数解释: 
--dbpath 数据库路径(数据文件)
--logpath 日志文件路径
--master 指定为主机器
--slave 指定为从机器
--source 指定主机器的IP地址
--pologSize 指定日志文件大小不超过64M.因为resync是非常操作量大且耗时，最好通过设置一个足够大的oplogSize来避免resync(默认的 oplog大小是空闲磁盘大小的5%)。
--logappend 日志文件末尾添加，即使用追加的方式写日志
--journal 启用日志
--port 启用端口号
--fork 在后台运行
--only 指定只复制哪一个数据库
--slavedelay 指从复制检测的时间间隔
--auth 是否需要验证权限登录(用户名和密码)
--syncdelay 数据写入硬盘的时间（秒），0是不等待，直接写入
--notablescan 不允许表扫描
--maxConns 最大的并发连接数，默认2000  
--pidfilepath 指定进程文件，不指定则不产生进程文件
--bind_ip 绑定IP，绑定后只能绑定的IP访问服务
```
- 8.启动 mongodb 服务
```bash
./bin/mongod --config bin/mongodb.conf 
```
- 9.查看端口27017是否已启动
```bash
[root@aikafka mongodb]# netstat -lanp | grep 27017
tcp        0      0 0.0.0.0:27017           0.0.0.0:*               LISTEN      12804/./bin/mongod  
unix  2      [ ACC ]     STREAM     LISTENING     645556   12804/./bin/mongod   /tmp/mongodb-27017.sock
```
- 10.将 mongodb 服务加入到自启动文件中
```bash
vi /etc/rc.local 
/usr/local/mongodb/bin/mongod --config /usr/local/mongodb/bin/mongodb.conf
```
