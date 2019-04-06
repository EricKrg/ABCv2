FROM debian:jessie
RUN sudo apt-get update && apt-get install openjdk-8 \
    maven \
    git \
    nano \
    python3 \
    python3-pip

RUN git clone https://github.com/EricKrg/ABCv2.git && cd ABCv2 && ls && pip3 install -r requirements.txt
RUN mvn install && mvn package
