CREATE DATABASE `share_time` CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `share_time`.`sys_user`
(
    `id`        INT(10) NOT NULL AUTO_INCREMENT,
    `u_num`     VARCHAR(32) COMMENT '用户编码',
    `user_name` VARCHAR(64) COMMENT '用户名称',
    `email`     VARCHAR(64) COMMENT '邮箱',
    `phone`     VARCHAR(13) COMMENT '电话号码',
    `wx_code`   VARCHAR(64) COMMENT '微信号',
    `password`  VARCHAR(16) COMMENT '密码MD5加密',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  CHARSET = utf8;


-- 公共字段抽离
alter table sys_user
    add remark1 varchar(500) null comment '备用字段';

alter table sys_user
    add remark2 varchar(500) null;

alter table sys_user
    add remark3 varchar(500) null;

alter table sys_user
    add remark4 varchar(500) null;

alter table sys_user
    add remark5 varchar(500) null;

alter table sys_user
    add create_date date not null comment '创建时间';

alter table sys_user
    add create_by varchar(64) null comment '创建人';

alter table sys_user
    add update_date dateTime null comment '修改时间';

alter table sys_user
    add update_by varchar(64) null comment '修改人';

alter table sys_user
    add is_delete varchar(2) default 'N' not null comment '是否删除 Y：删除 ；N：没有删除';

alter table sys_user
    add enable varchar(2) default 'Y' not null comment '是否启用 Y：是 ；N：否';


