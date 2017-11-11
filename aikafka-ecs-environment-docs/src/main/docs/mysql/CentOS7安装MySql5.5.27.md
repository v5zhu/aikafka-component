阿里云ECS CentOS 7系统安装mysql 5.5.27
说明：
(1).此安装中的路径仅供参考
(2).连接工具用的xshell，文件传输工具用的xftp

1.安装cmake 
```bash
$ yum install -y cmake
```
- 命令详解：cmake正是makefile的工具，它的目的正是为了产生可移植的makefile，并简化自己动手写makefile时的巨大工作量。

2.安装make
```bash
$ yum install -y make
```
命令详解：Make工具最主要也是最基本的功能就是通过makefile文件来描述源程序之间的相互关系并自动维护编译工作。而makefile 文件需要按照某种语法进行编写，文件中需要说明如何编译各个源文件并连接生成可执行文件，并要求定义源文件之间的依赖关系。

3.创建目录/usr/local/mysql以及/usr/local/mysql/data两个目录
- mysql用于安装mysql-5.5.27
- data用于存放数据库
```bash
$ mkdir -p /usr/local/mysql
$ mkdir -p /usr/local/mysql/data
```
4.下载mysql压缩包mysql-5.5.27.tar.gz
方式一：通过网络连接用wget命令直接下载到local目录；

方式二：事先下载好压缩包，通过xftp传输到local目录；

- 此处通过wget下载
```bash
$ wget https://dev.mysql.com/get/Downloads/MySQL-5.6/mysql-5.6.38-linux-glibc2.12-x86_64.tar.gz
```
5.解压
```bash
$ tar -zxvf mysql-5.6.38-linux-glibc2.12-x86_64.tar.gz
```
命令详解：
- -z：用gzip进行解压
- -x：从指定文件中读入不想包含的文件的列表
- -v：显示处理文件列表
- -f：在每个磁盘结尾使用脚本F

6.进入目录mysql-5.6.38-linux-glibc2.12-x86_64
```bash
$ cd mysql-5.6.38-linux-glibc2.12-x86_64
```
7.安装前置环境
```bash
$ yum -y install ncurses-devel
```
8.使用cmake命令，命令内容可直接复制不做修改(前提：前面说的mysql和data路径和我的一致)
命令内容：
```bash
$ cmake \
-DCMAKE_INSTALL_PREFIX=/usr/local/mysql \
-DMYSQL_UNIX_ADDR=/usr/local/mysql/data/mysql.sock \
-DDEFAULT_CHARSET=utf8 \
-DDEFAULT_COLLATION=utf8_general_ci \
-DWITH_EXTRA_CHARSETS:STRING=utf8,gbk \
-DWITH_MYISAM_STORAGE_ENGINE=1 \
-DWITH_INNOBASE_STORAGE_ENGINE=1 \
-DWITH_MEMORY_STORAGE_ENGINE=1 \
-DWITH_READLINE=1 \
-DENABLED_LOCAL_INFILE=1 \
-DMYSQL_DATADIR=/usr/local/mysql/data/ \
-DMYSQL_USER=mysql -DMYSQL_TCP_PORT=3306
```
注：\不可省去

可能报错：
CMake Error at /usr/share/cmake/Modules/CMakeCXXInformation.cmake:17 (GET_FILENAME_COMPONENT):

解决：
```bash
yum install -y gcc-c++
rm -f CMakeCache.txt
```
重新cmake

9.使用make
```bash
$ make
```

10.使用make install
```bash
$ make install
```

11.将my.cnf复制并重命名到/usr/local/mysql/support-files/my-dedium.cnf
```bash
$ cp /usr/local/mysql/support-files/my-medium.cnf /etc/my.cnf
$ cd /usr/local/mysql
```

12.chmod 755 scripts/mysql_install_db  改变文件目录权限
```bash
$ chmod 755 scripts/mysql_install_db
```
权限说明：chmod abc file
其中a,b,c各为一个数字，分别表示User、Group、及Other的权限。
r=4，w=2，x=1
　　若要rwx属性则4+2+1=7；
　　若要rw-属性则4+2=6；
　　若要r-x属性则4+1=7。
　　范例：
　　chmod a=rwx file 和 chmod 777 file 效果相同
　　chmod ug=rwx,o=x file 和 chmod 771 file 效果相同
　　若用chmod 4755 filename可使此程式具有root的权限
755权限：属主有读、写、执行权限；而属组用户和其他用户只有读、执行权限。


13.初始化数据库(同理，若目录和我的一致，命令可以不做修改)
```bash
$ groupadd Mysql
$ useradd -g Mysql mysql -d /usr/local/mysql -s /sbin/nologin
$ chgrp Mysql -R mysql
$ chown mysql -R mysql
```
命令内容：
```bash
$ scripts/mysql_install_db  \
--user=mysql  \
--basedir=/usr/local/mysql \
--datadir=/usr/local/mysql/data/

Installing MySQL system tables...
OK
Filling help tables...
OK

To start mysqld at boot time you have to copy
support-files/mysql.server to the right place for your system

PLEASE REMEMBER TO SET A PASSWORD FOR THE MySQL root USER !
To do so, start the server, then issue the following commands:

/usr/local/mysql/bin/mysqladmin -u root password 'new-password'
/usr/local/mysql/bin/mysqladmin -u root -h aikafka password 'new-password'

Alternatively you can run:
/usr/local/mysql/bin/mysql_secure_installation

which will also give you the option of removing the test
databases and anonymous user created by default.  This is
strongly recommended for production servers.

See the manual for more instructions.

You can start the MySQL daemon with:
cd /usr/local/mysql ; /usr/local/mysql/bin/mysqld_safe &

You can test the MySQL daemon with mysql-test-run.pl
cd /usr/local/mysql/mysql-test ; perl mysql-test-run.pl

Please report any problems with the /usr/local/mysql/scripts/mysqlbug script!
```


至此，mysql已经安装成功，可以使用mysql命令测试一下，后续还有一些设置

14.拷贝启动文件以开机启动
```bash
$ cp /usr/local/mysql/support-files/mysql.server /etc/init.d/mysql 
```

15.改变/etc/init.d/mysql的权限
```bash
$ chmod 755 /etc/init.d/mysql
```

16.设置开机启动
```bash
$ chkconfig mysql on
```

17.将mysql的bin目录添加至环境变量中
```bash
$ echo 'export PATH=/usr/local/mysql/bin:$PATH' >> /etc/profile 
```

18.使环境变量生效
```bash
$ source /etc/profile
```

19.启动mysql，若不能启动，reboot重启服务器
```bash
$ service mysql start
Starting MySQL..                                           [  OK  ]
```

20.查看3306端口状态
```bash
$ netstat -nat|grep 3306
```

21.登陆mysql,设置新密码
- (1)初次登陆,然后需要设置密码
```bash
$ mysql
``` 
- (2)非初次登陆命令：
```bash
$ mysql -u root -p
```
然后输入密码，即可登陆

如果提示：ERROR 1044 (42000): Access denied for user ''@'localhost' to database 'mysql'。
是因为mysql数据库的user表里，存在用户名为空的账户即匿名账户，导致登录的时候是虽然用的是root，但实际是匿名登录的，通过错误提示里的''@'localhost'可以看出来

22.设置新密码
```bash
mysql> use mysql;
mysql> update user set password= password ("123456");
```
23.现在使用navicat还不能远程连接数据库，因此需要开启允许远程连接数据库
```bash
mysql> select `Host`,`User` from user;
```
24.设置Host为%，允许远程连接
```bash
update user set `Host`='%' where `User` = 'root' limit 1;
```
25.最后，重启mysql服务
```bash
/etc/init.d/mysql restart
```
测试连接：输入ip地址、账号、密码即可登录
此处可能还连接不上，因为最新的阿里云ECS只开放了常用的几个端口，3306并未开放，需要登录阿里云控制台安全规则中添加规则开放3306端口,
开放后即可用客户端工具连接mysql

26.最后此数据库仅支持管理员登录，即root用户，先创建其他用户

测试连接


至此，Linux下的mysql安装完整过程到此结束，分享给大家！我也是新手，难免有纰漏，请大家见谅，有什么好的建议可以回复哦！
