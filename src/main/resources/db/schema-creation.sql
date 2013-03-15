CREATE USER 'cabwala'@'%' IDENTIFIED BY 'secret';

DROP SCHEMA IF EXISTS `cab_o_meter`;
CREATE SCHEMA `cab_o_meter`;
GRANT ALL PRIVILEGES ON cab_o_meter.* TO 'cabwala'@'%';
FLUSH PRIVILEGES;


DROP SCHEMA IF EXISTS `test_cab_o_meter`;
CREATE SCHEMA `test_cab_o_meter`;
GRANT ALL PRIVILEGES ON test_cab_o_meter.* TO 'cabwala'@'%';
FLUSH PRIVILEGES;