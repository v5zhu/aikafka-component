#CentOS 7安装gitlab-ee企业版

下面提供官方安装文档：
官方文档地址:https://about.gitlab.com/installation/#centos-7
安装前：
```bash
[root@localhost ~]# free -m
              total        used        free      shared  buff/cache   available
Mem:           7823         136        7486           8         200        7491
Swap:          8191           0        8191

```
##一、安装
- #####1.安装必要的依赖项

系统开放http和ssh访问,可能有些系统没有安装防火墙使用的是其他安全组件，最后两行可以不执行
```bash
sudo yum install -y curl policycoreutils-python openssh-server
sudo systemctl enable sshd
sudo systemctl start sshd
sudo firewall-cmd --permanent --add-service=http
sudo systemctl reload firewalld
```
安装邮件服务器，用于发送邮件通知，如果有其他的解决方案，可以不用安装，跳过此步

```bash
sudo yum install postfix
sudo systemctl enable postfix
sudo systemctl start postfix
```

安装过程直接回车继续...

- #####2.下载Gitlab包

```bash
curl https://packages.gitlab.com/install/repositories/gitlab/gitlab-ee/script.rpm.sh | sudo bash
```
这一步可以设置`EXTERNAL_URL=http://gitlab.example.com`，用于安装启动后访问gitlab，这里如果使用https的话安装之后还需要做一些额外的配置
```bash
sudo EXTERNAL_URL="http://gitlab.example.com" yum install -y gitlab-ee
```
如果不设置`EXTERNAL_URL=http://gitlab.example.com`，可以直接安装，安装之后在编辑访问url，直接安装使用：
```bash
sudo yum install -y gitlab-ee
```
```bash
It looks like GitLab has not been configured yet; skipping the upgrade script.

       *.                  *.
      ***                 ***
     *****               *****
    .******             *******
    ********            ********
   ,,,,,,,,,***********,,,,,,,,,
  ,,,,,,,,,,,*********,,,,,,,,,,,
  .,,,,,,,,,,,*******,,,,,,,,,,,,
      ,,,,,,,,,*****,,,,,,,,,.
         ,,,,,,,****,,,,,,
            .,,,***,,,,
                ,*,.
  


     _______ __  __          __
    / ____(_) /_/ /   ____ _/ /_
   / / __/ / __/ /   / __ \`/ __ \
  / /_/ / / /_/ /___/ /_/ / /_/ /
  \____/_/\__/_____/\__,_/_.___/
  

Thank you for installing GitLab!
GitLab was unable to detect a valid hostname for your instance.
Please configure a URL for your GitLab instance by setting `external_url`
configuration in /etc/gitlab/gitlab.rb file.
Then, you can start your GitLab instance by running the following command:
  sudo gitlab-ctl reconfigure

For a comprehensive list of configuration options please see the Omnibus GitLab readme
https://gitlab.com/gitlab-org/omnibus-gitlab/blob/master/README.md

D: %posttrans(gitlab-ee-10.1.3-ee.0.el7.x86_64): waitpid(5767) rc 5767 status 0
D: closed   db index       /var/lib/rpm/Sha1header
D: closed   db index       /var/lib/rpm/Sigmd5
D: closed   db index       /var/lib/rpm/Installtid
D: closed   db index       /var/lib/rpm/Dirnames
D: closed   db index       /var/lib/rpm/Triggername
D: closed   db index       /var/lib/rpm/Obsoletename
D: closed   db index       /var/lib/rpm/Conflictname
D: closed   db index       /var/lib/rpm/Providename
D: closed   db index       /var/lib/rpm/Requirename
D: closed   db index       /var/lib/rpm/Group
D: closed   db index       /var/lib/rpm/Basenames
D: closed   db index       /var/lib/rpm/Name
D: closed   db index       /var/lib/rpm/Packages
D: closed   db environment /var/lib/rpm
[root@localhost gitlab]# 
```
安装完成以后修改文件`/etc/gitlab/gitlab.rb`,设置访问url
```text
## GitLab URL
##! URL on which GitLab will be reachable.
##! For more details on configuring external_url see:
##! https://docs.gitlab.com/omnibus/settings/configuration.html#configuring-the-external-url-for-gitlab
external_url 'http://10.10.10.10:80/gitlab'
```
##二、启动
```bash
[root@localhost gitlab]# gitlab-ctl reconfigure
....
Running handlers:
Running handlers complete
Chef Client finished, 365/538 resources updated in 03 minutes 43 seconds
gitlab Reconfigured!
[root@localhost gitlab]# free
              total        used        free      shared  buff/cache   available
Mem:        8011216     1937024     3476364       88720     2597828     5694128
Swap:       8388604           0     8388604
```
访问之后系统内存（gitlab比较耗内存啊）：
```bash
[root@localhost gitlab]# free
              total        used        free      shared  buff/cache   available
Mem:        8011216     3120148     2288708       88788     2602360     4510728
Swap:       8388604           0     8388604
```

##三、修改账号密码
- 注：在第一次访问的时候会提示输入新密码，此时该密码为最初的密码,可以在页面设置新密码或者通过下面的方式重置密码
Gitlab 修改root用户密码

使用root权限登录服务器并启动Ruby on Rails 控制台.

```bash
[root@localhost ~]# gitlab-rails console production
Loading production environment (Rails 4.2.8)
irb(main):001:0> 

```
回车后感觉没响应,耐心等待1分钟左右...


有多种方式可以重置密码
```bash
irb(main):001:0> user = User.where(id: 1).first
=> #<User id:1 @root>
irb(main):002:0>user.password = 'secret_pass'
irb(main):002:0>user.password_confirmation = 'secret_pass'
irb(main):002:0>user.save
```
或者
```bash
irb(main):001:0> user = User.find_by(email: 'admin@local.host')
=> #<User id:1 @root>
irb(main):002:0>user.password = 'secret_pass'
irb(main):002:0>user.password_confirmation = 'secret_pass'
irb(main):002:0>user.save
```
修改密码后可以尝试使用新密码登录

##4.TortoiseGit clone push 项目
1.在用户目录下使用cmd或者git bash创建一个目录.ssh
```bash
$ mkdir .ssh
```
2.使用ssh-keygen -C 邮箱 -t rsa生成公钥和私钥
此处邮箱为gitlab上用户的邮箱，输入命令后一路回车，使用默认配置
```bash
$ ssh-keygen -C 2810010108@qq.com -t rsa
Generating public/private rsa key pair.
Enter file in which to save the key (/c/Users/cnlm.me/.ssh/id_rsa):
Enter passphrase (empty for no passphrase):
Enter same passphrase again:
Your identification has been saved in /c/Users/cnlm.me/.ssh/id_rsa.
Your public key has been saved in /c/Users/cnlm.me/.ssh/id_rsa.pub.
The key fingerprint is:
SHA256:JXOINlt4OpM92GIDCAypY97ndQcELMcjNtDE7Im9rDY 2810010108@qq.com
The key's randomart image is:
+---[RSA 2048]----+
|=. .*.o..        |
|.o . O B o       |
|. . * @ O o      |
|o. . * @ *       |
|o.. . % S .      |
| . . = * o .     |
|    + . . .      |
|   E .           |
|  . .            |
+----[SHA256]-----+

cnlm.me@DESKTOP-RKGQDB9 MINGW64 ~/.ssh
```
此时，生成了id-rsa私钥和id-rsa.pub公钥，远程需要访问gitlab下载和提交，需要将此公钥配置到gitlab对应账号下
3.配置SSH Keys

- 登录http://ip/gitlab
- 点击右上角头像
- settings
- SSH Keys
- 在Key输入框输入id-rsa.pub公钥文件内容
- 保存

4.TortoiseGit生成ppk私钥
由于本地使用的是TortoiseGit，因此需要生成一个.ppk的私钥
- 在TortoiseGit安装目录bin下双击打开puttygen.exe
- 点击load，选中id-rsa私钥
- 点击Save private key，.ssh目录会生成一个.ppk的私钥

5.clone
- 鼠标右键，git clone ...
- URL输入git@ip:test/pms_dev.git
- Directory输入....\gitlab\pms_dev
- 勾选Load Putty Key，选择文件C:\Users\...\.ssh\ppk.ppk
- OK
成功将项目clone到了本地

6.push
- 修改其中一个文件
- 鼠标右键，Git Commit -> "master"...
- 提交到本地仓库后直接push到远程gitlab master
