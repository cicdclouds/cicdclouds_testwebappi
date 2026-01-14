java -Dapp.host=localhost -Dapp.port=9090 -Dapp.context=copilot -jar target/functionalapi-tests.jar
java -Dapp.host=172.31.x.x -Dapp.port=8080 -Dapp.context=copilot -jar target/functionalapi-tests.jar
java -Dapp.host=10.x.x.x -Dapp.port=8080 -Dapp.context=copilot -jar target/functionalapi-tests.jar
java -Dapp.host=<PVT_IP> -Dapp.port=8080 -Dapp.context="" -jar target/functionalapi-tests.jar
java -Dapp.host=<IP> -Dapp.port=<PORT> -Dapp.context=<APP_NAME> -jar target/functionalapi-tests.jar
