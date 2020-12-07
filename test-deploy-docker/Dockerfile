FROM tomcat:9.0.40-jdk8-openjdk-buster
MAINTAINER nils.91@googlemail.com
#update
RUN apt-get update
RUN apt-get -y upgrade
#install git and maven
RUN apt-get -y install git
RUN apt-get -y install maven
CMD ["catalina.sh", "run"]
