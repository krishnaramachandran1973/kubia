FROM amazoncorretto:11-alpine-jdk
RUN apk add --no-cache bash
ENV APPROOT=/app 
WORKDIR ${APPROOT} 
COPY target/kubia-1.0.jar ${APPROOT}
ENTRYPOINT [ "java" ]
CMD [ "-jar", "-Xms256m", "-Xmx256m", "kubia-1.0.jar"]