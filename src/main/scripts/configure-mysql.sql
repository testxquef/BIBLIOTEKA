CREATE USER 'sfg_dev_user'@'localhost' IDENTIFIED BY 'danzal';
CREATE USER 'sfg_prod_user'@'localhost' IDENTIFIED BY 'danzal';
CREATE USER 'sfg_dev_user'@'%' IDENTIFIED BY 'danzal';
CREATE USER 'sfg_prod_user'@'%' IDENTIFIED BY 'danzal';


GRANT SELECT ON sfg_dev.* to '26753852_danzal'@'localhost';
GRANT INSERT ON sfg_dev.* to '26753852_danzal'@'localhost';
GRANT DELETE ON sfg_dev.* to '26753852_danzal'@'localhost';
GRANT UPDATE ON sfg_dev.* to '26753852_danzal'@'localhost';
GRANT SELECT ON sfg_prod.* to '26753852_danzal'@'localhost';
GRANT INSERT ON sfg_prod.* to '26753852_danzal'@'localhost';
GRANT DELETE ON sfg_prod.* to '26753852_danzal'@'localhost';
GRANT UPDATE ON sfg_prod.* to '26753852_danzal'@'localhost';
GRANT SELECT ON sfg_dev.* to '26753852_danzal'@'%';
GRANT INSERT ON sfg_dev.* to '26753852_danzal'@'%';
GRANT DELETE ON sfg_dev.* to '26753852_danzal'@'%';
GRANT UPDATE ON sfg_dev.* to '26753852_danzal'@'%';
GRANT SELECT ON sfg_prod.* to '26753852_danzal'@'%';
GRANT INSERT ON sfg_prod.* to '26753852_danzal'@'%';
GRANT DELETE ON sfg_prod.* to '26753852_danzal'@'%';
GRANT UPDATE ON sfg_prod.* to '26753852_danzal'@'%';