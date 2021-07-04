FROM tomcat:8.5.31-jre8
RUN rm -rvf /usr/local/tomcat/webapps/ROOT
ADD  /rest/target/payment-rest-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8081
CMD ["catalina.sh", "run", "java","-Dspring.data.mongodb.uri=mongodb://mongodb:27017/Payment"]
