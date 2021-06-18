FROM ubuntu

WORKDIR /root

COPY  'openjdk-15+36_linux-x64_bin.tar.gz' .
COPY  target/database-0.0.1-SNAPSHOT.jar .
COPY  src/main/python .
COPY  requirements.txt .
COPY  start.sh .

RUN tar -xvf 'openjdk-15+36_linux-x64_bin.tar.gz' && apt-get update && apt-get install python3 && pip3 install -r requirements.txt

CMD['sh start.sh']



