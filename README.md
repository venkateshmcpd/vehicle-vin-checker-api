# vehicle-vin-checker-api
API Client to check Vehicle VIN

clone repo to run locally
https://github.com/venkateshmcpd/vehicle-vin-checker-api.git

go to /resources folder
modify config file vin-checker-config.json to point to  MS SQL Server database

create new database in MS SQL Server
go to /sql-scripts folder
run script DDL.sql
run script DML.sql

build code and run jar with the following command
target folder ->
java -jar vehicle-vin-checker-api-0.0.1-SNAPSHOT-fat.jar -conf <config file path>
