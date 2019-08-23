CREATE DATABASE `test` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
CREATE TABLE `test`.`users`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `test`.`users`(`username`, `password`) VALUES ('zty', '123456');

CREATE TABLE `test`.`user_roles`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `test`.`user_roles`(`username`, `role_name`) VALUES ('zty', 'admin');
INSERT INTO `test`.`user_roles`(`username`, `role_name`) VALUES ('zty', 'user');
INSERT INTO `test`.`user_roles`(`username`, `role_name`) VALUES ('zzz', 'user');

CREATE TABLE `test`.`roles_permissions`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  `permission` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `test`.`roles_permissions`(`role_name`, `permission`) VALUES ('admin', 'user:delete');
INSERT INTO `test`.`roles_permissions`(`role_name`, `permission`) VALUES ('admin', 'user:put');
INSERT INTO `test`.`roles_permissions`(`role_name`, `permission`) VALUES ('admin', 'user:post');
INSERT INTO `test`.`roles_permissions`(`role_name`, `permission`) VALUES ('admin', 'user:get');
INSERT INTO `test`.`roles_permissions`(`role_name`, `permission`) VALUES ('user', 'user:get');

CREATE TABLE `test`.`test_user`  (
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`user_name`)
);
INSERT INTO `test`.`test_user`(`user_name`, `password`) VALUES ('ttt', '123456');

CREATE TABLE `test`.`test_user_role`  (
  `user_name` varchar(255) NOT NULL,
  `role_name` varchar(255) NOT NULL
);
INSERT INTO `test`.`test_user_role`(`user_name`, `role_name`) VALUES ('ttt', 'user');

CREATE TABLE `test`.`test_roles_permissions`  (
  `role_name` varchar(255) NOT NULL,
  `permission` varchar(255) NOT NULL
);
INSERT INTO `test`.`test_roles_permissions`(`role_name`, `permission`) VALUES ('user', 'user:get');
