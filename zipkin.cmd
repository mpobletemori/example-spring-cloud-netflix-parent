@echo off
set RABBIT_ADDRESSES      =localhost:5672
set STORAGE_TYPE          =mysql
set MYSQL_HOST            =localhost
set MYSQL_TCP_PORT        =3306
set MYSQL_USER            =zipkin 
set MYSQL_PASS            =zipkin
set MYSQL_MAX_CONNECTIONS =10
set MYSQL_USE_SSL         =false
java -jar ./zipkin-server-2.23.4-exec.jar --server.port=7000