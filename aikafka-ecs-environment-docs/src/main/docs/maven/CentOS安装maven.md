1.下载
```bash
$ wget http://mirrors.shuosc.org/apache/maven/maven-3/3.5.2/binaries/apache-maven-3.5.2-bin.tar.gz
```
2.解压
```bash
$ tar -zxvf apache-maven-3.5.2-bin.tar.gz
```
3.修改环境变量
```bash
$ vi /etc/profile
```
添加如下内容：
export MAVEN_HOME=/home/env-package/maven/apache-maven-3.5.2
export PATH=$MAVEN_HOME/bin:$PATH

4.让环境变量立即生效
```bash
source /etc/profile
```
5.测试安装结果
```bash
$ mvn -v
Maven home: /home/env-package/maven/apache-maven-3.5.2
Java version: 9.0.1, vendor: Oracle Corporation
Java home: /usr/local/jdk9
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "3.10.0-514.26.2.el7.x86_64", arch: "amd64", family: "unix"
```
