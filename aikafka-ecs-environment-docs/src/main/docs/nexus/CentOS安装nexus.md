maven从远程仓库下载jar偶尔会有一些jar会没有，可通过其他渠道下载后手动上传至nexus私服，本地开发maven settings文件中配置使用私服，需要从其他仓库下载可将其他仓库配置进私服的public仓库组
1.下载
```bash
$ wget https://sonatype-download.global.ssl.fastly.net/nexus/3/nexus-3.6.0-02-unix.tar.gz
```
2.解压
```bash
tar -zxvf nexus-3.6.0-02-unix.tar.gz
```
3.修改nexus访问端口
```bash
$ vi nexus-3.6.0-02/etc/nexus-default.properties 
application-port=18081
```
4.启动
```bash
$ ./nexus-3.6.0-02/bin/nexus start
```
5.访问http://47.96.28.236:18081/nexus/
若不能访问，登录阿里云开放端口18081或者使用nginx代理
6.登录
- 默认账号admin
- 默认密码admin123
7.配置nexus环境变量，便于直接执行nexus启动和停止
```bash
$ vi /etc/profile
#添加如下
export NEXUS_HOME=/home/env-package/nexus/nexus-2.11.2-03/
export PATH=$NEXUS_HOME/bin:$PATH
```
8.让环境变量立即生效
```bash
$ source /etc/profile
```
9.目前只能使用ip:18081/nexus访问，但阿里云默认不开放其他端口，因此使用nginx代理下
```bash
$ vi /usr/local/nginx/conf/nginx.conf
#添加如下：
location /nexus {
   proxy_pass http://localhost:18081/nexus;
}
```
10.nginx重新加载
```bash
$ nginx -s reload
```
11.访问域名www.aikafka.com/nexus即可通过外网访问无需开放其他端口