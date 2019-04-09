FROM alpine:latest
RUN  apk update && apk add openjdk8 \
    maven \
    git \
    nano \
    python3
RUN git clone https://github.com/EricKrg/ABCv2.git && cd ABCv2 && ls && pip3 install -r requirements.txt
RUN cd ABCv2 && mvn install && mvn package
EXPOSE 9000
RUN cd ABCv2  && mkdir logs && echo "This might take a bit..." && java -jar target/abc2.0-1.0-SNAPSHOT-jar-with-dependencies.jar
RUN cd ABCv2 && python3 vis.py;
CMD echo $'    _    ____   ____       ____ \n\
              / \  | __ ) / ___|_   _|___ \ \n\
             / _ \ |  _ \| |   \ \ / / __) | \n\
            / ___ \| |_) | |___ \ V / / __/ \n\
           /_/   \_\____/ \____| \_/ |_____| \n\
            \n\
           by eric.krueger@uni-jena.de'
CMD cd ABCv2 && python3 myHttp.py


