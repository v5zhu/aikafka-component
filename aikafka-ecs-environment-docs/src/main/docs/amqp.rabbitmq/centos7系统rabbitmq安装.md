##一、安装
- 1.rabbitmq是有erlang编写，需要先安装erlang
```bash
wget http://www.rabbitmq.com/releases/erlang/erlang-18.1-1.el6.x86_64.rpm

rpm -ihv erlang-18.1-1.el6.x86_64.rpm
```

- 2.然后去官网下载最新版本：http://www.rabbitmq.com/download.html 
当前最新版是3.6.14
```bash
wget https://dl.bintray.com/rabbitmq/rabbitmq-server-rpm/rabbitmq-server-3.6.14-1.el7.noarch.rpm

rpm -ihv rabbitmq-server-3.6.14-1.el7.noarch.rpm
```
此时可能会报错：
warning: rabbitmq-server-3.6.14-1.el7.noarch.rpm: Header V4 RSA/SHA512 Signature, key ID 6026dfca: NOKEY
error: Failed dependencies:
	socat is needed by rabbitmq-server-3.6.14-1.el7.noarch
解决：
```bash
$ yum install -y socat
```
- 3.启动

```bash
service /sbin/rabbitmq-server start
``` 

##二、配置

- 1.创建用户，因为缺省的guest/guest用户只能在本地登录，所以先用命令行创建一个admin/admin123，并让他成为管理员。
```bash
$ /sbin/rabbitmqctl add_user admin admin123
Creating user "admin"
$ /sbin/rabbitmqctl set_user_tags admin administrator
Setting tags for user "admin" to [administrator]
```

- 2.然后，我们启用WEB管理。

```bash
/sbin/rabbitmq-plugins enable rabbitmq_management
```
现在可用使用浏览器访问管理台，就用刚才创建的admin登录即可，端口是15672。

##三、测试访问
1.若端口已开放直接访问ip:15672
2.若端口未开放请用nginx代理

```bash
#在nginx/conf目录创建文件
$ vi rabbit.conf
#添加如下内容:
server {
        listen       80;
        server_name  rabbit.aikafka.com;

        location / {
            proxy_pass http://localhost:15672/;
        }
}
#在nginx.conf http模块include进rabbit.conf
http{
    include rabbit.conf;
....
}
$ nginx -s reload
```
访问：http://rabbit.aikafka.com