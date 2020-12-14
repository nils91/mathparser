FROM tomcat:9.0.40-jdk8-openjdk-buster
MAINTAINER nils.91@googlemail.com
#add .war-file
ADD MathParserDev.war /usr/local/tomcat/webapps/
#expose tomcat port
EXPOSE 8080
CMD ["catalina.sh", "run"]
