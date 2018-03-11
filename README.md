Build the software as follows:

    mvn clean package

Run it as follows, changing the port to 9090:

    java -Dserver.port=9090 -jar target/mia-service-0.1.0.jar

Connect to the web client at: http:://<hostname>:9090/.  