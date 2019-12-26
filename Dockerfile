FROM openjdk:8
ENV JAR_NAME caffeine-0.0.1-SNAPSHOT.jar
COPY . /usr/src/app/build
WORKDIR /usr/src/app/build
RUN chmod +x gradlew
RUN ./gradlew -parallel build
RUN cp ./build/libs/$JAR_NAME /usr/src/app
WORKDIR /usr/src/app
RUN rm -rf build
EXPOSE 8080
CMD java -jar $JAR_NAME --spring.profiles.active=deploy