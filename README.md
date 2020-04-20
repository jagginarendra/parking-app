# parking-app
Multi-Level Parking App

AWS hosted URL - 3.136.11.80:9080


Mysql Sql scripts-

create database parking_system;
create table floors(floor_id int4 primary key auto_increment, description VARCHAR(55));
create table spots(spot_id int primary key auto_increment, floor_id int4 , level VARCHAR(55), company_id int4);
create table spot_occupancy(occupancy_id int primary key auto_increment,spot_id int4, company_id int4, status VARchar(1), count int4);
create table customer(customer_id int4 primary key auto_increment, first_name varchar(55), last_name varchar(55), address varchar(100), phone varchar(10), customer_type varchar(30));
create table vehicle (vehicle_id int primary key auto_increment, registration_number varchar(15), vehicle_type varchar(15),color varchar(30), customer_id int4);
create table park (park_id int primary key auto_increment, vehicle_id int, start_time datetime , end_time datetime, spot_id int ,parking_lot_id int,amount int);


CI with Jenkins Pipeline With docker

build pipeline-url --> http://3.19.66.87:8080/job/Deploy_Parking_App/
Few Words about pipeline
- For every commit generates artifacts and place to target host
- Builds image with Dockerfile. Image name - parking-app-image
- Generate docker container parking-app-image and starts on 9080






