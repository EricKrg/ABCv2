FROM debian:jessie
RUN sudo apt-get update && apt-get install openjdk-8 \
    maven \
    git \
    nano
RUN git clone ...
RUN mvn install ...