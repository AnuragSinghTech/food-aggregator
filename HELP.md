# Getting Started

### Reference Documentation
Assumption Taken: Vendor returns only distinct items (No two item names are same)

1. Download the package from GitHub and open terminal on your system.

2. Run ./mvnw spring-boot:run command in classpath, check the jar file has been created in Target folder.

3. DockerFile and Docker-compose file has been provided and jar can be deployed into container using docker-compose up command.

4. Hit the five endpoints to get the result for ex:
item: wheat
quantity: 2
Amount: 1500
http://localhost:8080/buy-item-qty-price/item/wheat/qty/2/price/1500

http://localhost:8080/show-summary
