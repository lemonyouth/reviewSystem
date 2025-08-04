# 使用官方 OpenJDK 作为基础镜像
FROM eclipse-temurin:24-jdk

## 创建应用目录并设置权限（Alpine 需要）
#RUN mkdir -p /app && chown -R 1000:1000 /app
WORKDIR /app

# 将构建好的 jar 包复制进容器中
COPY target/review-system-0.0.1-SNAPSHOT.jar app.jar


# 暴露端口（Spring Boot 默认端口）
EXPOSE 8080
USER 1000
# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
