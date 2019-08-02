create table vehicle(vehicleId int(11) primary key auto_increment not null,
vehicleName varchar(50) not null,
vehicleNumber varchar(50) not null,
vehicleType varchar(50) not null);

create table consumer(consumerId int(11) auto_increment  primary key not null, 
name varchar(50) not null,
email varchar(50) not null, 
phoneNumber varchar(15), 
password varchar(100)  not null,
registrationDate datetime default current_timestamp,
lastUpdateDate datetime on update current_timestamp,
vehicleId int(11),
foreign key (vehicleId) references vehicle(vehicleId));

create table consumeraddress(addressId int(11) primary key auto_increment not null, 
addressLine varchar(255) not null, 
locality varchar(50) not null, 
city varchar(50) not null, 
state varchar(50) not null,
pincode int(8) not null, 
consumerId int(11) not null,
foreign key (consumerId) references consumer(consumerId) );

create table carwasher(carwasherId int(11) auto_increment  primary key not null, 
name varchar(50) not null, 
email varchar(50) not null, 
phoneNumber varchar(15)not null, 
password varchar(100)  not null,
registrationDate datetime default current_timestamp,
lastUpdateDate datetime on update current_timestamp );

create table carwasheraddress(addressId int(11) primary key auto_increment not null,
addressLine varchar(255) not null, 
locality varchar(50) not null, 
city varchar(50) not null, 
state varchar(50) not null,
pincode int(8) not null,
carwasherId int(11) not null,
foreign key (carwasherId) references carwasher(carwasherId) );


create table orders(orderId int(11) auto_increment primary key not null,
orderDate datetime default current_timestamp,
orderCompletedDate datetime on update current_timestamp,
orderAmount double not null,
orderStatus varchar(50) not null,
consumerId int(11) not null,
carwasherId int(11) not null,
foreign key (carwasherId) references carwasher(carwasherId),
foreign key (consumerId) references consumer(consumerId));


drop table orders;
drop table consumeraddress;
drop table consumer;
drop table vehicle;
drop table carwasheraddress;
drop table carwasher;
 
 