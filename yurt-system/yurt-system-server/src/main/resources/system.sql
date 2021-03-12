-- menu
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu(
    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL  COMMENT '菜单Id',
    parent_id     INT UNSIGNED    DEFAULT 0          NOT NULL  COMMENT '上级菜单Id',
    name          VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '菜单名称',
    icon          VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '菜单图标',
    url           VARCHAR(256)    DEFAULT ''         NOT NULL  COMMENT '链接',
    order_num     SMALLINT UNSIGNED    DEFAULT 0     NOT NULL  COMMENT '顺序号',
    type          VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '菜单类型',
    is_enabled    TINYINT(1)      DEFAULT 1          NOT NULL  COMMENT '是否启用',
    auth_code     VARCHAR(64)     DEFAULT ''         NOT NULL  COMMENT '权限码',
    is_deleted    TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否删除',
    gmt_created   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  COMMENT '创建时间',
    gmt_modified  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gmt_deleted   DATETIME DEFAULT '2000-01-01'      NOT NULL  COMMENT '删除时间'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci
    COMMENT ='菜单表';
CREATE INDEX idx_name ON sys_menu(name);

INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (1, 0, '系统管理', 'el-icon-setting', '', 3, 'menu', true, 'sys');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (2, 1, '字典管理', '', '/dictionary/index', 9, 'menu', true, 'sys.dictionary');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (3, 1, '菜单管理', '', '/menu/index', 3, 'menu', true, 'sys.menu');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (4, 1, '参数管理', 'fa-wrench', '/param/index', 10, 'menu', true, 'sys.param');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (5, 1, '用户管理', 'el-icon-user', '/plugin/user', 1, 'menu', true, 'view_user');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (6, 1, '组织管理', 'fa-sitemap', '/plugin/org', 6, 'menu', true, 'view_org');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (7, 1, '角色管理', 'fa-lock', '/role/index', 4, 'menu', true, 'sys.role');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (8, 1, '权限管理', '', '/authority/index', 15, 'menu', true, 'sys.authority');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (12, 0, '组织机构', 'el-icon-user', '', 6, 'menu', true, '');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (13, 3, '新增菜单', '', '', 1, 'button', true, 'sys.menu.view');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (14, 0, '点外卖', 'el-icon-eleme', '', 3, 'menu', true, 'ele');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (15, 0, '点外卖', 'el-icon-eleme', '/element', 10, 'menu', true, 'ele');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (16, 15, '饿了吗', '', '/element', 2, 'menu', true, 'wai.element');
INSERT INTO sys_menu (id, parent_id, name, icon, url, order_num, type, is_enabled, auth_code) VALUES (17, 0, 'wailian', 'el-icon-paperclip', '/test', 7, 'menu', true, '');

-- dictionary
DROP TABLE IF EXISTS sys_dictionary;
CREATE TABLE sys_dictionary(
    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL  COMMENT '字典Id',
    parent_id     INT UNSIGNED    DEFAULT 0          NOT NULL  COMMENT '上级字典Id',
    dict_key      VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '字典key',
    dict_value    VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '字典值',
    order_num     SMALLINT UNSIGNED    DEFAULT 0     NOT NULL  COMMENT '顺序号',
    remark        VARCHAR(256)    DEFAULT ''         NOT NULL  COMMENT '备注',
    is_deleted    TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否删除',
    gmt_created   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  COMMENT '创建时间',
    gmt_modified  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gmt_deleted   DATETIME DEFAULT '2000-01-01'      NOT NULL  COMMENT '删除时间'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci
    COMMENT = '字典表';

CREATE INDEX uk_key_pid ON sys_dictionary (dict_key, parent_id);

INSERT INTO sys_dictionary (id, parent_id, dict_key, dict_value, order_num, remark) VALUES (1, 0, 'menu_type', '菜单类型', 1, '菜单类型');
INSERT INTO sys_dictionary (id, parent_id, dict_key, dict_value, order_num, remark) VALUES (2, 1, 'menu_type_top', '顶部导航菜单', 1, '顶部导航菜单');
INSERT INTO sys_dictionary (id, parent_id, dict_key, dict_value, order_num, remark) VALUES (3, 1, 'menu_type_left', '左侧菜单', 2, '左侧菜单');
INSERT INTO sys_dictionary (id, parent_id, dict_key, dict_value, order_num, remark) VALUES (4, 0, 'gender', '性别', 2, '');
INSERT INTO sys_dictionary (id, parent_id, dict_key, dict_value, order_num, remark) VALUES (5, 4, 'male', '男', 1, '');
INSERT INTO sys_dictionary (id, parent_id, dict_key, dict_value, order_num, remark) VALUES (6, 4, 'female', '女', 4, '');

-- dept
DROP TABLE IF EXISTS sys_dept;
CREATE TABLE sys_dept (
    id      INT UNSIGNED PRIMARY KEY  AUTO_INCREMENT NOT NULL  COMMENT '部门id',
    parent_id     INT UNSIGNED    DEFAULT 0          NOT NULL  COMMENT '父部门Id',
    name          VARCHAR(256)    DEFAULT ''         NOT NULL  COMMENT '部门名称',
    order_num     SMALLINT UNSIGNED    DEFAULT 0     NOT NULL  COMMENT '顺序号',
    level_index   VARCHAR(32)     DEFAULT ''         NOT NULL  COMMENT '特殊编号, 用于查询',
    remark        VARCHAR(512)    DEFAULT ''         NOT NULL  COMMENT '备注',
    is_enabled    TINYINT(1)      DEFAULT 1          NOT NULL  COMMENT '是否启用',
    is_deleted    TINYINT(1)      DEFAULT 0          NOT NULL  COMMENT '是否删除',
    gmt_created   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  COMMENT '创建时间',
    gmt_modified  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gmt_deleted   DATETIME DEFAULT '2000-01-01'      NOT NULL  COMMENT '删除时间'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci
    COMMENT = '机构表';

CREATE INDEX idx_name ON sys_dept (name);
CREATE INDEX idx_level ON sys_dept (level_index);


-- param
DROP TABLE IF EXISTS sys_param;
CREATE TABLE sys_param(
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
    COLLATE = utf8_general_ci
    COMMENT = '参数表';

CREATE UNIQUE INDEX uk_code ON sys_param (code);

-- user
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user(
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
    COLLATE = utf8_general_ci
    COMMENT = '用户表';

CREATE UNIQUE INDEX uk_name
    ON sys_user (username);
CREATE INDEX uk_code_num
    ON sys_user (code, job_num, mobile_num, email, id_card_num);

-- security
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_authority;
DROP TABLE IF EXISTS sys_role_authority;
CREATE TABLE sys_authority(
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
    COLLATE = utf8_general_ci
    COMMENT ='权限码';

CREATE UNIQUE INDEX uk_code ON sys_authority (code);

CREATE TABLE sys_role (
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
    COLLATE = utf8_general_ci
    COMMENT ='角色';

CREATE UNIQUE INDEX uk_code ON sys_role (code);


CREATE TABLE sys_role_authority (
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
    ON sys_role_authority (role_id, authority_id);

CREATE TABLE sys_user_role (
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
    ON sys_user_role (user_id, role_id);


DELETE FROM sys_authority;
INSERT INTO sys_authority (code, name, function, module)
VALUES ('VIEW_DICTIONARY', '查看字典', 'dictionary_manager', 'system');
INSERT INTO sys_authority (code, name, function, module)
VALUES ('ADD_DICTIONARY', '新增字典', 'dictionary_manager', 'system');
INSERT INTO sys_authority (code, name, function, module)
VALUES ('MODIFY_DICTIONARY', '修改字典', 'dictionary_manager', 'system');
INSERT INTO sys_authority (code, name, function, module)
VALUES ('REMOVE_DICTIONARY', '删除字典', 'dictionary_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('VIEW_PARAM', '查看参数', 'param_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('ADD_PARAM', '新增参数', 'param_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('MODIFY_PARAM', '修改参数', 'param_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('REMOVE_PARAM', '删除参数', 'param_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('VIEW_MENU', '查看菜单', 'menu_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('ADD_MENU', '新增菜单', 'menu_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('MODIFY_MENU', '修改菜单', 'menu_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('REMOVE_MENU', '删除菜单', 'menu_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('VIEW_USER', '查看菜单', 'user_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('ADD_USER', '新增菜单', 'user_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('MODIFY_USER', '修改菜单', 'user_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('REMOVE_USER', '删除菜单', 'user_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('VIEW_ORG', '查看组织', 'org_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('ADD_ORG', '新增组织', 'org_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('MODIFY_ORG', '修改组织', 'org_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('REMOVE_ORG', '删除组织', 'org_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('VIEW_ROLE', '查看角色', 'role_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('ADD_ROLE', '新增角色', 'role_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('MODIFY_ROLE', '修改角色', 'role_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('REMOVE_ROLE', '删除角色', 'role_manager', 'system');
INSERT INTO sys_authority (code, name, function, module) VALUES ('VIEW_AUTHORITY', '查看权限', 'role_manager', 'system');

DELETE FROM sys_user;
INSERT INTO sys_user (org_id, code, job_num, username, mobile_num, avatar, password, gender)
VALUES (0, '001', '', 'liulu', '', '15267548275', '$2a$10$4d.sZRSu0mNup8TKQtamM.K4CBf9ZIwC7gMuN4B5MROP9iykCnLRC', TRUE);






