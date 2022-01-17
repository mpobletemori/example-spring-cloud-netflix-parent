@echo off
set RABBIT_ADDRESSES=localhost:5672
java -jar ./zipkin-server-2.23.4-exec.jar --server.port=7000