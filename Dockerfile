# syntax=docker/dockerfile:1


FROM node:18-alpine AS my-blog-build
ARG HTTP_PROXY
ARG HTTPS_PROXY

ENV HTTP_PROXY=$HTTP_PROXY
ENV HTTPS_PROXY=$HTTPS_PROXY
ENV http_proxy=$HTTP_PROXY
ENV https_proxy=$HTTPS_PROXY

RUN npm config set proxy $HTTP_PROXY \
 && npm config set https-proxy $HTTPS_PROXY \
 && npm config set registry https://registry.npmmirror.com \
 && npm config set replace-registry-host always \
 && npm config set maxsockets 3 \
 && npm config set fetch-retries 10 \
 && npm config set fetch-retry-mintimeout 20000 \
 && npm config set fetch-retry-maxtimeout 120000 \
 && npm config set fetch-timeout 600000

WORKDIR /build/my_blog
COPY my_blog/package*.json ./
RUN npm ci --include=dev --legacy-peer-deps --no-audit --no-fund --registry=https://registry.npmmirror.com
COPY my_blog/ ./
RUN npx vue-cli-service build

FROM node:18-alpine AS blog-admin-build
ARG HTTP_PROXY
ARG HTTPS_PROXY

ENV HTTP_PROXY=$HTTP_PROXY
ENV HTTPS_PROXY=$HTTPS_PROXY
ENV http_proxy=$HTTP_PROXY
ENV https_proxy=$HTTPS_PROXY

RUN npm config set proxy $HTTP_PROXY \
 && npm config set https-proxy $HTTPS_PROXY \
 && npm config set registry https://registry.npmmirror.com \
 && npm config set replace-registry-host always \
 && npm config set maxsockets 3 \
 && npm config set fetch-retries 10 \
 && npm config set fetch-retry-mintimeout 20000 \
 && npm config set fetch-retry-maxtimeout 120000 \
 && npm config set fetch-timeout 600000

WORKDIR /build/blog_admin

COPY blog_admin/package*.json ./
RUN npm ci --include=dev --legacy-peer-deps --no-audit --no-fund --registry=https://registry.npmmirror.com
COPY blog_admin/ ./
RUN VUE_APP_PUBLIC_PATH=/admin/ npx vue-cli-service build

FROM maven:3.8.8-eclipse-temurin-8 AS backend-build
WORKDIR /build/blog
COPY blog/ ./
RUN mvn -q -DskipTests package

FROM eclipse-temurin:8-jre-jammy

RUN apt-get update \
    && apt-get install -y --no-install-recommends nginx ca-certificates \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=my-blog-build /build/my_blog/dist/ /usr/share/nginx/html/
COPY --from=blog-admin-build /build/blog_admin/dist/ /usr/share/nginx/html/admin/
COPY --from=backend-build /build/blog/target/*.war /app/blog.war
COPY docker/nginx.conf /etc/nginx/nginx.conf
COPY docker/start.sh /app/start.sh

RUN sed -i 's/\r$//' /app/start.sh \
    && chmod +x /app/start.sh

ENV SPRING_PROFILES_ACTIVE=pro
ENV JAVA_OPTS=""

EXPOSE 80

CMD ["/app/start.sh"]
