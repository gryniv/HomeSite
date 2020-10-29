#!/bin/bash
echo mysql create DATABASE (make sure what docker-compose is running)
echo Please enter password for Root user (default: root):
sudo mysql -h127.0.0.1 -P 3306 -uroot -p <<MYSQL_SCRIPT
CREATE DATABASE IF NOT EXISTS Site_DB;
CREATE USER IF NOT EXISTS 'siteUser'@'%' IDENTIFIED BY '8ky@FEN7MQiVPcjb8qtW';
GRANT ALL PRIVILEGES ON Site_DB.* TO 'siteUser'@'%';
FLUSH PRIVILEGES;
MYSQL_SCRIPT