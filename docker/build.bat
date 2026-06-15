docker build ^
  --build-arg HTTP_PROXY=http://host.docker.internal:9567 ^
  --build-arg HTTPS_PROXY=http://host.docker.internal:9567 ^
  -t ssw-blog:latest .
docker tag ssw-blog:latest zhou827743509/ssw-blog:latest


docker login

docker push zhou827743509/ssw-blog:latest
