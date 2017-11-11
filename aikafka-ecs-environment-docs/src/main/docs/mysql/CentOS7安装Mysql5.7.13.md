###本教程是安装的mysql 5.7.20，和版本mysql 5.5.27有所区别

约定
- 文件下载目录：/home/env-package/mysql
- Mysql安装位置：/usr/local/mysql
- 数据库保存位置：/usr/local/mysql/data
- 日志保存位置：/usr/local/mysql/log
1.下载mysql
```bash
$ wget http://dev.mysql.com/get/Downloads/MySQL-5.7/mysql-5.7.17-linux-glibc2.5-x86_64.tar.gz
```
2.解压
```bash
tar -zxvf mysql-5.7.17-linux-glibc2.5-x86_64.tar.gz
```
3.修改文件夹名称
```bash
mv mysql-5.7.17-linux-glibc2.5-x86_64.tar.gz mysql
```
4.移动mysql到/usr/local/
```bash
mv mysql /usr/local/mysql
``` 
5.创建仓库目录
```bash
$ cd /usr/local/mysql
$ mkdir data
```
6.新建mysql用户、组及目录
```bash
$ groupadd Mysql
$ useradd -r -s /sbin/nologin -g Mysql mysql -d /usr/local/mysql #新建msyql用户禁止登录shell
```
7.改变目录属主
```bash
$ chgrp Mysql -R *
$ chown mysql -R *
```
8配置参数
```bash
$ bin/mysqld --initialize --user=mysql --basedir=/usr/local/mysql --datadir=/usr/local/mysql/data
```
此时会报错：
`bin/mysqld: error while loading shared libraries: libaio.so.1: cannot open shared object file: No such file or directory`
因为缺少缺少安装包libaio和libaio-devel，安装：
```bash
$ yum install -y libaio*
```
再次配置
```
$ bin/mysqld --initialize --user=mysql --basedir=/usr/local/mysql --datadir=/usr/local/mysql/data

2017-11-11T14:46:58.900215Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2017-11-11T14:47:00.955421Z 0 [Warning] InnoDB: New log files created, LSN=45790
2017-11-11T14:47:01.182881Z 0 [Warning] InnoDB: Creating foreign key constraint system tables.
2017-11-11T14:47:01.300360Z 0 [Warning] No existing UUID has been found, so we assume that this is the first time that this server has been started. Generating a new UUID: 2d06f50a-c6ef-11e7-adcc-00163e100c9c.
2017-11-11T14:47:01.302636Z 0 [Warning] Gtid table is not ready to be used. Table 'mysql.gtid_executed' cannot be opened.
2017-11-11T14:47:01.303129Z 1 [Note] A temporary password is generated for root@localhost: /+aEeV8o8laW
```
此处需要注意记录生成的临时密码，如上文：/+aEeV8o8laW

```bash
$ bin/mysql_ssl_rsa_setup --datadir=/usr/local/mysql/data

Generating a 2048 bit RSA private key
.............................+++
..+++
writing new private key to 'ca-key.pem'
-----
Generating a 2048 bit RSA private key
.........................+++
..............................+++
writing new private key to 'server-key.pem'
-----
Generating a 2048 bit RSA private key
.....+++
.................................................................................................................................+++
writing new private key to 'client-key.pem'
-----
```
9.修改系统配置文件
```bash
$ rm -f /etc/my.cnf
$ cp support-files/my-default.cnf /etc/my.cnf
$ cp support-files/mysql.server /etc/init.d/mysql
$ vi /etc/init.d/mysql
```
修改以下内容：
basedir=/usr/local/mysql
datadir=/usr/local/mysql/data
```bash
$ vi /etc/my.cnf
```
修改以下内容：
basedir=/usr/local/mysql
datadir=/usr/local/mysql/data
port=3306
# server_id = .....
socket=/usr/local/mysql/mysql.sock



9启动mysql
```bash
service mysql start
```
```bash
bin/mysqld_safe --user=mysql &
bin/mysql -uroot –p
```
--输入第6步生成的临时密码


mysql> set password=password('A123456');

mysql>grant all privileges on *.* to root@'%' identified by 'A123456';
mysql> flush privileges;

mysql> use mysql;
mysql> select host,user from user;

10添加系统路径
# vim /etc/profile
添加：
export PATH=/usr/local/mysql/bin:$PATH
如下：

# source /etc/profile
11配置mysql自动启动
# chmod 755 /etc/init.d/mysql
# chkconfig --add mysql
# chkconfig --level 345 mysql on
以上就是linux环境Mysql 5.7.13安装教程的第一套方案，希望对大家的学习有所帮助。