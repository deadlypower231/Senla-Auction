FROM tomcat:8.5.31-jre8
RUN rm -rvf /usr/local/tomcat/webapps/ROOT
ADD  /rest/target/trading-rest-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run", "java","-Dspring.data.mongodb.uri=mongodb://mongodb:27017/Auction", "-Djava.security.egd=file:/dev/./urandom"]
