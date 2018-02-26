FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD mtk-eureka-service-@version@.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "java -Xms512m -Xmx2g -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
