FROM tomcat:9.0.40-jdk8-openjdk-buster
MAINTAINER nils.91@googlemail.com
#args for building
#master is the default branch
ARG branch=master
#update install cache and install git and maven
RUN apt-get update; apt-get -y install git maven
#set workdir to root
WORKDIR /
#clone mathparser and built
RUN mkdir /mathparser/
WORKDIR /mathparser/
RUN git clone https://github.com/nils91/mathparser.git . && git checkout ${branch} && git pull && mvn -Dmaven.test.skip=true clean install
#reset workdir
WORKDIR /usr/local/tomcat/
#copy war over to tomcat
RUN cp /mathparser/deploy-debug/target/MathParserDev.war /usr/local/tomcat/webapps/ && rm -rf /mathparser/
#expose tomcat port
EXPOSE 8080
CMD ["catalina.sh", "run"]
