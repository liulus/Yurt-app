-- dictionary
DROP TABLE IF EXISTS yurt_dictionary;
CREATE TABLE yurt_dictionary(
    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL  COMMENT '字典Id',
    parent_id     INT UNSIGNED    DEFAULT 0          NOT NULL  COMMENT '上级字典Id',
    dict_key      VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '字典key',
    dict_value    VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '字典值',
    order_num     SMALLINT UNSIGNED    DEFAULT 0     NOT NULL  COMMENT '顺序号',
    remark        VARCHAR(256)    DEFAULT ''         NOT NULL  COMMENT '备注',
    is_system     TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否系统级',
    is_deleted    TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否删除',
    gmt_created   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  COMMENT '创建时间',
    gmt_modified  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gmt_deleted   DATETIME DEFAULT '2000-01-01'      NOT NULL  COMMENT '删除时间'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci
    COMMENT = '字典表';

CREATE UNIQUE INDEX uk_pid_key ON yurt_dictionary (parent_id, dict_key);

-- param
DROP TABLE IF EXISTS yurt_param;
CREATE TABLE yurt_param(
    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL  COMMENT '参数Id',
    code          VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '参数编码',
    value         VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '参数值',
    remark        VARCHAR(256)    DEFAULT ''         NOT NULL  COMMENT '备注',
    is_system     TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否系统级',
    is_deleted    TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否删除',
    gmt_created   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  COMMENT '创建时间',
    gmt_modified  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gmt_deleted   DATETIME DEFAULT '2000-01-01'      NOT NULL  COMMENT '删除时间'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci
    COMMENT = '参数表';

CREATE UNIQUE INDEX uk_code ON yurt_param (code);

-- menu
DROP TABLE IF EXISTS yurt_menu;
CREATE TABLE yurt_menu(
    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL  COMMENT '菜单Id',
    parent_id     INT UNSIGNED    DEFAULT 0          NOT NULL  COMMENT '上级菜单Id',
    code          VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '菜单编码',
    name          VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '菜单名称',
    icon          VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '菜单图标',
    url           VARCHAR(256)    DEFAULT ''         NOT NULL  COMMENT '链接',
    order_num     SMALLINT UNSIGNED    DEFAULT 0     NOT NULL  COMMENT '顺序号',
    remark        VARCHAR(512)    DEFAULT ''         NOT NULL  COMMENT '备注',
    type          VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '菜单类型',
    module        VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '所属模块',
    is_enabled    TINYINT(1)      DEFAULT 1          NOT NULL  COMMENT '是否启用',
    auth_code     VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '权限码',
    is_deleted    TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否删除',
    gmt_created   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  COMMENT '创建时间',
    gmt_modified  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gmt_deleted   DATETIME DEFAULT '2000-01-01'      NOT NULL  COMMENT '删除时间'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci
    COMMENT ='菜单表';

CREATE INDEX uk_pid_code ON yurt_menu (parent_id, code);

DELETE FROM yurt_dictionary WHERE dict_key LIKE 'menu_type%';

INSERT INTO yurt_dictionary (parent_id, dict_key, dict_value, order_num, remark, is_system) VALUES (0, 'menu_type', '菜单类型', 1, '菜单类型', 1);
INSERT INTO yurt_dictionary (parent_id, dict_key, dict_value, order_num, remark, is_system) VALUES (last_insert_id(), 'menu_type_top', '顶部导航菜单', 1, '顶部导航菜单', 1);
INSERT INTO yurt_dictionary (parent_id, dict_key, dict_value, order_num, remark, is_system) VALUES (last_insert_id() - 1, 'menu_type_left', '左侧菜单', 2, '左侧菜单', 1);

-- user

DROP TABLE IF EXISTS yurt_organization;
DROP TABLE IF EXISTS yurt_user;

CREATE TABLE yurt_organization (
    id      INT UNSIGNED PRIMARY KEY  AUTO_INCREMENT NOT NULL  COMMENT '机构id',
    parent_id     INT UNSIGNED    DEFAULT 0          NOT NULL  COMMENT '父机构Id',
    code          VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '机构号',
    full_name     VARCHAR(256)    DEFAULT ''         NOT NULL  COMMENT '机构名',
    short_name    VARCHAR(128)    DEFAULT ''         NOT NULL  COMMENT '机构简称',
    type          VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '机构类型',
    level_index   VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '特殊编号, 用于查询',
    address       VARCHAR(512)    DEFAULT ''         NOT NULL  COMMENT '地址',
    remark        VARCHAR(512)    DEFAULT ''         NOT NULL  COMMENT '备注',
    is_deleted    TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否删除',
    gmt_created   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  COMMENT '创建时间',
    gmt_modified  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gmt_deleted   DATETIME DEFAULT '2000-01-01'      NOT NULL  COMMENT '删除时间'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci
    COMMENT = '机构表';

CREATE INDEX uk_code
    ON yurt_organization (code);
CREATE UNIQUE INDEX uk_name
    ON yurt_organization (full_name);
CREATE INDEX idx_pid_level
    ON yurt_organization (parent_id, level_index);


CREATE TABLE yurt_user(
    id     INT UNSIGNED PRIMARY KEY  AUTO_INCREMENT  NOT NULL  COMMENT '用户Id',
    org_id        INT UNSIGNED    DEFAULT 0          NOT NULL  COMMENT '机构Id',
    code          VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '用户编号',
    job_num       VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '工号',
    username      VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '用户名',
    nick_name     VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '昵称',
    real_name     VARCHAR(16)     DEFAULT ''         NOT NULL  COMMENT '真实姓名',
    mobile_num    VARCHAR(16)     DEFAULT ''         NOT NULL  COMMENT '手机号',
    avatar        VARCHAR(256)    DEFAULT ''         NOT NULL  COMMENT '头像',
    password      VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '密码',
    gender        TINYINT(1)      DEFAULT 1          NOT NULL  COMMENT '性别',
    email         VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '邮箱',
    id_card_num   VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '身份证号',
    telephone     VARCHAR(16)     DEFAULT ''         NOT NULL  COMMENT '电话',
    type          VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '用户类型',
    status        VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '用户状态',
    is_lock       TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否锁定',
    creator       VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '创建人',
    is_deleted    TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否删除',
    gmt_created   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  COMMENT '创建时间',
    gmt_modified  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gmt_deleted   DATETIME DEFAULT '2000-01-01'      NOT NULL  COMMENT '删除时间'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci
    COMMENT = '用户表';

CREATE UNIQUE INDEX uk_name
    ON yurt_user (username);
CREATE INDEX uk_code_num
    ON yurt_user (code, job_num, mobile_num, email, id_card_num);

-- security
DROP TABLE IF EXISTS yurt_role;
DROP TABLE IF EXISTS yurt_user_role;
DROP TABLE IF EXISTS yurt_authority;
DROP TABLE IF EXISTS yurt_role_authority;
CREATE TABLE yurt_authority(
    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL  COMMENT 'id',
    code          VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '权限码',
    name          VARCHAR(128)    DEFAULT ''         NOT NULL  COMMENT '权限码名称',
    function      VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '所属功能',
    module        VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '所属模块',
    remark        VARCHAR(512)    DEFAULT ''         NOT NULL  COMMENT '备注',
    is_deleted    TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否删除',
    gmt_created   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  COMMENT '创建时间',
    gmt_modified  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gmt_deleted   DATETIME DEFAULT '2000-01-01'      NOT NULL  COMMENT '删除时间'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci
    COMMENT ='权限码';

CREATE UNIQUE INDEX uk_code ON yurt_authority (code);

CREATE TABLE yurt_role (
    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL  COMMENT '角色Id',
    code          VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '角色编号',
    name          VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '角色名',
    remark        VARCHAR(512)    DEFAULT ''         NOT NULL  COMMENT '备注',
    is_deleted    TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否删除',
    gmt_created   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  COMMENT '创建时间',
    gmt_modified  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gmt_deleted   DATETIME DEFAULT '2000-01-01'      NOT NULL  COMMENT '删除时间'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci
    COMMENT ='角色';

CREATE UNIQUE INDEX uk_code ON yurt_role (code);


CREATE TABLE yurt_role_authority (
    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL  COMMENT 'id',
    role_id       INT UNSIGNED    DEFAULT 0          NOT NULL  COMMENT '角色Id',
    authority_id  INT UNSIGNED    DEFAULT 0          NOT NULL  COMMENT '权限码Id',
    remark        VARCHAR(512)    DEFAULT ''         NOT NULL  COMMENT '备注',
    is_deleted    TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否删除',
    gmt_created   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  COMMENT '创建时间',
    gmt_modified  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gmt_deleted   DATETIME DEFAULT '2000-01-01'      NOT NULL  COMMENT '删除时间'
)
    ENGINE = InnoDB
    COMMENT ='角色权限关联表';

CREATE UNIQUE INDEX uk_role_auth
    ON yurt_role_authority (role_id, authority_id);

CREATE TABLE yurt_user_role (
    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL  COMMENT 'id',
    user_id       INT UNSIGNED    DEFAULT 0          NOT NULL  COMMENT '用户Id',
    role_id       INT UNSIGNED    DEFAULT 0          NOT NULL  COMMENT '角色Id',
    remark        VARCHAR(512)    DEFAULT ''         NOT NULL  COMMENT '备注',
    is_deleted    TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否删除',
    gmt_created   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  COMMENT '创建时间',
    gmt_modified  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gmt_deleted   DATETIME DEFAULT '2000-01-01'      NOT NULL  COMMENT '删除时间'
)
    ENGINE = InnoDB
    COMMENT ='用户角色关联表';
CREATE UNIQUE INDEX uk_user_role
    ON yurt_user_role (user_id, role_id);

DELETE FROM yurt_menu WHERE code LIKE 'system%';

INSERT INTO yurt_menu (parent_id, code, name, icon, url, order_num, type, auth_code)
VALUES (0, 'system', '系统管理', 'fa-cog', '', 1, 'menu_type_left', '');
INSERT INTO yurt_menu (parent_id, code, name, icon, url, order_num, type, auth_code)
VALUES (last_insert_id(), 'system_dictionary', '字典管理', 'fa-book', '/plugin/dictionary', 3, 'menu_type_left',
        'view_dictionary');
INSERT INTO yurt_menu (parent_id, code, name, icon, url, order_num, type, auth_code)
VALUES (last_insert_id() - 1, 'system_menu', '菜单管理', 'fa-list-ul', '/plugin/menu', 1, 'menu_type_left', 'view_menu');
INSERT INTO yurt_menu (parent_id, code, name, icon, url, order_num, type, auth_code)
VALUES (last_insert_id() - 2, 'system_param', '参数管理', 'fa-wrench', '/plugin/param', 2, 'menu_type_left', 'view_param');
INSERT INTO yurt_menu (parent_id, code, name, icon, url, order_num, type, auth_code)
VALUES (last_insert_id() - 3, 'system_user', '用户管理', 'fa-user', '/plugin/user', 5, 'menu_type_left', 'view_user');
INSERT INTO yurt_menu (parent_id, code, name, icon, url, order_num, type, auth_code)
VALUES (last_insert_id() - 4, 'system_organization', '组织管理', 'fa-sitemap', '/plugin/org', 4, 'menu_type_left', 'view_org');
INSERT INTO yurt_menu (parent_id, code, name, icon, url, order_num, type, auth_code)
VALUES (last_insert_id() - 5, 'system_role', '角色管理', 'fa-lock', '/plugin/role', 6, 'menu_type_left', 'view_role');
INSERT INTO yurt_menu (parent_id, code, name, icon, url, order_num, type, auth_code)
VALUES (last_insert_id() - 6, 'system_authority', '权限管理', 'fa-key', '/plugin/authority', 7, 'menu_type_left',
        'view_authority');


DELETE FROM yurt_authority;
INSERT INTO yurt_authority (code, name, function, module)
VALUES ('VIEW_DICTIONARY', '查看字典', 'dictionary_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module)
VALUES ('ADD_DICTIONARY', '新增字典', 'dictionary_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module)
VALUES ('MODIFY_DICTIONARY', '修改字典', 'dictionary_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module)
VALUES ('REMOVE_DICTIONARY', '删除字典', 'dictionary_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('VIEW_PARAM', '查看参数', 'param_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('ADD_PARAM', '新增参数', 'param_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('MODIFY_PARAM', '修改参数', 'param_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('REMOVE_PARAM', '删除参数', 'param_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('VIEW_MENU', '查看菜单', 'menu_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('ADD_MENU', '新增菜单', 'menu_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('MODIFY_MENU', '修改菜单', 'menu_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('REMOVE_MENU', '删除菜单', 'menu_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('VIEW_USER', '查看菜单', 'user_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('ADD_USER', '新增菜单', 'user_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('MODIFY_USER', '修改菜单', 'user_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('REMOVE_USER', '删除菜单', 'user_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('VIEW_ORG', '查看组织', 'org_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('ADD_ORG', '新增组织', 'org_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('MODIFY_ORG', '修改组织', 'org_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('REMOVE_ORG', '删除组织', 'org_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('VIEW_ROLE', '查看角色', 'role_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('ADD_ROLE', '新增角色', 'role_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('MODIFY_ROLE', '修改角色', 'role_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('REMOVE_ROLE', '删除角色', 'role_manager', 'system');
INSERT INTO yurt_authority (code, name, function, module) VALUES ('VIEW_AUTHORITY', '查看权限', 'role_manager', 'system');

DELETE FROM yurt_user;
INSERT INTO yurt_user (org_id, code, job_num, username, mobile_num, avatar, password, gender)
VALUES (0, '001', '', 'liulu', '', '15267548275', '$2a$10$4d.sZRSu0mNup8TKQtamM.K4CBf9ZIwC7gMuN4B5MROP9iykCnLRC', TRUE);






