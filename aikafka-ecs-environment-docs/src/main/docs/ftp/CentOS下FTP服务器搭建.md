#CentOS 7下FTP服务器的安装配置。

##假设我们有以下要求
| 路径	| 权限备注| 
| ------------- |:-------------:|
| /ftp/open     | 所有用户均可访问	只读 |
| /ftp/private  | 仅允许Alice、Jack、Tom三个人访问,Alice、Jack只允许下载，Tom可以上传|

##一、安装FTP

#####1.使用yum安装
```bash
 $ yum -y install ftp vsftpd
```
#####2.查看安装是否成功
```bash
$ rpm -qa|grep vsftpd
$ ftp-0.17-67.el7.x86_64
$ vsftpd-3.0.2-22.el7.x86_64
```
#####3.创建匿名用户主目录
```bash
$ mkdir -p /ftp/open
```
#####4.创建几个测试文件到该目录
```bash
$ echo hello > /ftp/open/open.txt
$ echo world > /ftp/open/test.txt
$ touch /ftp/open/empty.txt
```
#####5.查看vsftpd安装的配置文件所在路径
```bash
$ rpm -qc vsftpd
/etc/logrotate.d/vsftpd
/etc/pam.d/vsftpd
/etc/vsftpd/ftpusers
/etc/vsftpd/user_list
/etc/vsftpd/vsftpd.conf
```
#####6.备份原配置文件
```bash
$ cd /etc/vsftpd/
$ cp vsftpd.conf vsftpd.conf.origin
```
#####7.创建用户账号密码文件，上面账号，下面密码
```bash
$ vi /etc/vsftpd/vftpuser.txt
alice
alice@pass
jack
jack@pass
tom
tom@pass
```
#####8.根据账号密码文件创建密码DB文件,此处应保证db_load已经安装，若未安装请先安装
```bash
$ rpm -qf /usr/bin/db_load
libdb-utils-5.3.21-19.el7.x86_64

$ db_load -T -t hash -f /etc/vsftpd/vftpuser.txt \
/etc/vsftpd/vftpuser.db
```
#####9.查看密码DB文件
```bash
$ file /etc/vsftpd/vftpuser.db
/etc/vsftpd/vftpuser.db: Berkeley DB (Hash, version 9, native byte-order)
```
 #####10. 创建vftpd的guest账号
```bash
$ useradd -d /ftp/private -s /sbin/nologin vftpuser

$ vi/etc/pam.d/vsftpd
#将auth和account所有配置都注释，添加下面两行
auth    required pam_userdb.so db=/etc/vsftpd/vftpuser
account required pam_userdb.so db=/etc/vsftpd/vftpuser
```
 #####11.在配置文件/etc/vsftpd/vsftpd.conf末尾加上
```bash
anon_root=/ftp/open
guest_username=vftpuser
chroot_local_user=YES
allow_writeable_chroot=YES
use_localtime=YES
listen_port=21
idle_session_timeout=300
guest_enable=YES
guest_username=vftpuser
user_config_dir=/etc/vsftpd/
data_connection_timeout=1
virtual_use_local_privs=YES
pasv_min_port=10060
pasv_max_port=10090
accept_timeout=5
connect_timeout=1
```
#####12.设置自动启动
```bash
$ systemctl enable vsftpd
```
#####13.启动服务
```bash
$ systemctl start vsftpd
```
#####14.查看运行状态
```bash
$ systemctl status vsftpd
● vsftpd.service - Vsftpd ftp daemon
   Loaded: loaded (/usr/lib/systemd/system/vsftpd.service; enabled; vendor preset: disabled)
   Active: active (running) since Sat 2017-11-11 10:56:58 CST; 1h 27min ago
  Process: 2915 ExecStart=/usr/sbin/vsftpd /etc/vsftpd/vsftpd.conf (code=exited, status=0/SUCCESS)
 Main PID: 2916 (vsftpd)
   CGroup: /system.slice/vsftpd.service
           └─2916 /usr/sbin/vsftpd /etc/vsftpd/vsftpd.conf

Nov 11 10:56:58 aikafka systemd[1]: Starting Vsftpd ftp daemon...
Nov 11 10:56:58 aikafka systemd[1]: Started Vsftpd ftp daemon.
```
##二、测试

#####1.测试匿名账户
```bash 
$ ftp localhost
Trying ::1...
Connected to localhost (::1).
220 (vsFTPd 3.0.2)
Name(localhost:root): `anonymous`
331 Please specify the password.
Password:#此处不输入直接回车
230 Login successful.
Remote system type is UNIX.
Using binary mode to transfer files.

ftp> ls
229 Entering Extended Passive Mode (|||61057|).
150 Here comes the directory listing.
-rw-r--r--  1 0        0              9 Aug 11 11:45 open.txt
226 Directory send OK.

ftp> lcd/tmp
Local directory now /tmp

ftp> get open.txt
local: open.txt remote: open.txt
229 Entering Extended Passive Mode (|||64276|).
150 Opening BINARY mode data connection foropen.txt (9 bytes).
226 Transfer complete.
9 bytes received in 0.000895 secs(10.06 Kbytes/sec)

ftp> bye
221 Goodbye.
#文件open.txt成功下载到/tmp/目录
```
#####2.测试本地账户
```bash
# ftp localhost
Trying ::1...
Connected to localhost (::1).
220 (vsFTPd 3.0.2)
Name(localhost:root): `alice`
331 Please specify the password.
Password:#此处输入密码alice@pass
230 Login successful.
Remote system type is UNIX.
Using binary mode to transfer files.

ftp> ls
229 Entering Extended Passive Mode (|||21750|).
150 Here comes the directory listing.
226 Directory send OK.

ftp> !ls /tmp
filetest1.txt    open.txt    systemd-private-9xPN7y 

ftp> lcd /tmp
Local directory now /tmp

ftp> put filetest1.txt
local: filetest1.txt remote: filetest1.txt
229 Entering Extended Passive Mode (|||65399|).
150 Ok to send data.
226 Transfer complete.
9 bytes sent in 5.9e-05 secs (152.54 Kbytes/sec)

ftp> bye
221 Goodbye.
```
#####3.测试xftp客户端工具连接
问题：无论是匿名用户还是指定用户都无法连接
解决：xftp使用主动模式连接，选项>使用被动模式（P）√取消掉即可

#####4.使用IE客户端访问ftp://47.96.28.236/
问题：不能连接
解决：IE>工具（Alt+X）>Internet选项（D）>高级>使用被动FTP（用户防火墙和DSL调制解调器的兼容）√取消掉即可

#####5.谷歌浏览器访问ftp://47.96.28.236/
问题：不能访问
解决：暂时还没找到解决方案，请有方案的多多指教！