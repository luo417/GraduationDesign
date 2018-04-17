f ### 1.重新加载Nginx配置文件
 ``` 
 sudo ./nginx -s reload
```

### 2.配置Nginx数据转发
```
location /login {
    fastcgi_pass 地址:port;
    include fastcgi.conf;
}

地址：本机ip/127.0.0.1/localhost

```
### 3.启动spawn_fcgi
```
//编译fastcgi程序
gcc echo.c -lfcgi

//启动spawn_fcgi
spawn-fcgi -a 地址 -p port -f ./fastcgi程序
```

### 4.编译cgi程序
```
//将libfcgi库所在路径加入到/etc/ld.so.conf
/usr/local/lib
sudo ldconfig -v

gcc cgi.c -o app -lfcgi
```

### 5.启动spawn-fcgi
```
//查看端口号对应进程
netstat -apn | grep 8888

//-n 前台显示
spawn-fcgi -a 127.0.0.1 -p 8888 -f ./app -n
```

### 6.git相关命令
```
//回退代码
git log
git reset --hard 55ee69a682b094b7e3ab32dc2e3746282d986109
git push -f

git revert e7c8599d29b61579ef31789309b4e691d6d3a83f
```