@echo off
set HTTP_PROXY=http://127.0.0.1:9567
set HTTPS_PROXY=http://127.0.0.1:9567
@REM docker builder prune -f
@REM docker image prune -f
docker build --build-arg HTTP_PROXY=http://host.docker.internal:9567 --build-arg HTTPS_PROXY=http://host.docker.internal:9567 -t ssw-blog:latest .
docker tag ssw-blog:latest zhou827743509/ssw-blog:latest

docker login

docker push zhou827743509/ssw-blog:latest
