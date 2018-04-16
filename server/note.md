### 1.重新加载Nginx配置文件
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
spawn-fcgi -a 地址 -p port -f fastcgi程序
```