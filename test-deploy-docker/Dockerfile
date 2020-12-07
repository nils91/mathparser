FROM tomcat:9.0.40-jdk8-openjdk-buster
MAINTAINER nils.91@googlemail.com
#args for building
#master is the default branch
ARG branch=master
#update
RUN apt-get update
RUN apt-get -y upgrade
#install git and maven
RUN apt-get -y install git
RUN apt-get -y install maven
#set workdir to root
WORKDIR /
#clone mathparser
RUN git clone https://github.com/nils91/mathparser.git
WORKDIR /mathparser/
RUN git checkout ${branch}
RUN git pull
#build
RUN mvn clean install
#reset workdir
WORKDIR /usr/local/tomcat/
#copy war over to tomcat
RUN cp /mathparser/deploy-debug/target/MathParserDev.war /usr/local/tomcat/webapps/
#expose tomcat port
EXPOSE 8080
CMD ["catalina.sh", "run"]
