#CentOS 7安装gitlab-ee企业版

下面提供官方安装文档：
官方文档地址:https://about.gitlab.com/installation/#centos-7

- #####1.Install and configure the necessary dependencies

On CentOS, the commands below will also open HTTP and SSH access in the system firewall.

```bash
sudo yum install -y curl policycoreutils-python openssh-server
sudo systemctl enable sshd
sudo systemctl start sshd
sudo firewall-cmd --permanent --add-service=http
sudo systemctl reload firewalld
```
Next, install Postfix to send notification emails. If you want to use another solution to send emails please skip this step and configure an external SMTP server after GitLab has been installed.
```bash
sudo yum install postfix
sudo systemctl enable postfix
sudo systemctl start postfix
```

During Postfix installation a configuration screen may appear. Select 'Internet Site' and press enter. Use your server's external DNS for 'mail name' and press enter. If additional screens appear, continue to press enter to accept the defaults.

- #####2.Add the GitLab package repository and install the package

Add the GitLab package repository.

```bash
curl https://packages.gitlab.com/install/repositories/gitlab/gitlab-ee/script.rpm.sh | sudo bash
```
Next, install the GitLab package. Change `http://gitlab.example.com` to the URL at which you want to access your GitLab instance. Installation will automatically configure and start GitLab at that URL. HTTPS requires additional configuration after installation.

```bash
sudo EXTERNAL_URL="http://gitlab.example.com" yum install -y gitlab-ee
```
- #####3.Browse to the hostname and login

On your first visit, you'll be redirected to a password reset screen. Provide the password for the initial administrator account and you will be redirected back to the login screen. Use the default account's username root to login.

See our documentation for detailed instructions on installing and configuration.

Troubleshooting  Manual installation  CE or EE