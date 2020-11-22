package com.github.liulus.yurt.system.model.dto;

import lombok.Data;

/**
 * User : liulu
 * Date : 17-10-3 下午3:51
 * version $Id: UserVo.java, v 0.1 Exp $
 */
@Data
public abstract class UserVo {

    private String code;
    private String username;
    private String jobNum;
    private String nickName;
    private String realName;
    private String idCardNum;
    private String avatar;
    private String email;
    private String mobileNum;
    private String telephone;
    private String password;
    private Boolean gender;


    @Data
    public static class List {
        private Long id;
        private String username;
        private String mobileNum;
        private Short gender;
        private Boolean lock;
    }

    @Data
    public static class Detail extends UserVo {
        private Long id;
        private String type;
        private Boolean lock;
    }

    @Data
    public static class Add extends UserVo {
    }

    @Data
    public static class Update extends UserVo {
        private Long id;
    }

    @Data
    public static class Register {
        private String mobileNum;
        private String password;
        private String confirmPassword;
        private String smsCaptcha;
    }


}
