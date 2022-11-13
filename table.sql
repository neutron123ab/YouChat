# 用户表
CREATE TABLE IF NOT EXISTS `user`
(
    `id`                    INT(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `username`              VARCHAR(32)  DEFAULT NULL COMMENT '用户名',
    `password`              VARCHAR(255) DEFAULT NULL COMMENT '加密后的密码',
    `enabled`               TINYINT(1)   DEFAULT NULL COMMENT '账户是否可用',
    `accountNonExpired`     TINYINT(1)   DEFAULT NULL COMMENT '账户是否没有过期',
    `accountNonLocked`      TINYINT(1)   DEFAULT NULL COMMENT '账户是否没有锁定',
    `credentialsNonExpired` TINYINT(1)   DEFAULT NULL COMMENT '凭证是否没有过期',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


# 角色表
CREATE TABLE IF NOT EXISTS `role`
(
    `id`     INT(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
    `name`   VARCHAR(32) DEFAULT NULL COMMENT '角色英文名',
    `nameZh` VARCHAR(32) DEFAULT NULL COMMENT '角色中文名',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 权限表
CREATE TABLE IF NOT EXISTS `permission`
(
    `id`     INT(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
    `name`   VARCHAR(32) DEFAULT NULL COMMENT '权限英文名',
    `nameZh` VARCHAR(32) DEFAULT NULL COMMENT '权限中文名',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 资源表
CREATE TABLE IF NOT EXISTS `resources`
(
    `id`   INT(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
    `name` VARCHAR(32) DEFAULT NULL COMMENT '权限中文名',
    `url`  varchar(32) DEFAULT NULL COMMENT '接口地址',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 用户-角色关联表
CREATE TABLE IF NOT EXISTS `user_role`
(
    `id`      INT(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
    `user_id` INT(11) DEFAULT NULL COMMENT '用户id',
    `role_id` INT(11) DEFAULT NULL COMMENT '角色id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 角色-权限关联表
CREATE TABLE IF NOT EXISTS `role_permission`
(
    `id`            INT(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
    `role_id`       INT(11) DEFAULT NULL COMMENT '角色id',
    `permission_id` INT(11) DEFAULT NULL COMMENT '权限id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 权限-资源关联表
CREATE TABLE IF NOT EXISTS `permission-resources`
(
    `id`            INT(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
    `permission_id` INT(11) DEFAULT NULL COMMENT '权限id',
    `resources_id`  INT(11) DEFAULT NULL COMMENT '资源id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 群组表
CREATE TABLE IF NOT EXISTS `group`
(
    `id`          INT(11)                               NOT NULL AUTO_INCREMENT COMMENT '群组id',
    `user_id`     INT(11)     DEFAULT 1                 NOT NULL COMMENT '群主id',
    `group_name`  VARCHAR(32) DEFAULT '新建群聊'         NOT NULL COMMENT '群聊名称',
    `create_time` TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `container`   INT(32)     DEFAULT 1000              NOT NULL COMMENT '群组容量',
    `num`         INT(32)     DEFAULT 0                 NOT NULL COMMENT '成员数量',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB,
  DEFAULT CHARSET = utf8;

# 用户好友关联表
CREATE TABLE IF NOT EXISTS `user_friends`
(
    `id`         INT(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
    `user_id`    INT(11) DEFAULT NULL COMMENT '用户id',
    `friends_id` INT(11) DEFAULT NULL COMMENT '好友id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB,
  DEFAULT CHARSET = utf8;

# 用户-群组关联表
CREATE TABLE IF NOT EXISTS `user_group`
(
    `id`       INT(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
    `user_id`  INT(11) DEFAULT NULL COMMENT '用户id',
    `group_id` INT(11) DEFAULT NULL COMMENT '群组id',
    primary key (`id`)
) ENGINE = InnoDB,
  DEFAULT CHARSET = utf8;


# 私聊记录表
CREATE TABLE IF NOT EXISTS `single_chat`
(
    `id`              INT(11)                                NOT NULL AUTO_INCREMENT COMMENT '表id',
    `user_friends_id` INT(11)                                NOT NULL COMMENT '用户好友关联表id',
    `send_time`       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '消息发送时间',
    `content`         VARCHAR(128) DEFAULT NULL COMMENT '消息内容',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB,
  DEFAULT CHARSET = utf8;

# 群聊记录表
CREATE TABLE IF NOT EXISTS `group_chat`
(
    `id`            INT(11)                                NOT NULL AUTO_INCREMENT COMMENT '表id',
    `user_group_id` INT(11)                                NOT NULL COMMENT '用户群组关联表id',
    `send_time`     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '消息发送时间',
    `content`       VARCHAR(128) DEFAULT NULL COMMENT '消息内容',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB,
  DEFAULT CHARSET = utf8;
