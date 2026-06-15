#!/bin/sh
set -e

java $JAVA_OPTS -jar /app/blog.war &

exec nginx -g "daemon off;"
