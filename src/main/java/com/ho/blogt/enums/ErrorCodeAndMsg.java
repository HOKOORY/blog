package com.ho.blogt.enums;

public enum ErrorCodeAndMsg {
    User_INPUT_FAIL("-1", "用户名输入错误"),
    User_BAN("-1", "该用户被Ban了"),
    USER_NOT_LOGIN("-1", "请登录后再尝试！"),
    USER_PASSWORD_ERROR("-1", "密码输入错误"),
    UNKNOW_ERROR("-1", "未知错误"),
    USER_NAME_IN_DATABASE("-1", "该用户名已存在"),
    USER_SIGNUP_ERROR("-1", "注册失败，请联系管理员"),
    NETWORK_ERROR("99999", "网络错误，待会重试"),
    DATABASE_ERROR("88888", "数据库出错，请联系管理员"),
    NOT_IMAGE("10001", "该文件不是图片格式"),
    IMAGE_NULL("10002", "该图片为空"),
    UPLOAD_IMAGE_FAIL("10003", "上传图片失败"),
    USER_LIKED("10004", "已经点过赞啦，不要再点了，受不了！"),
    DATEFORMAT_ERROR("20001", "时间格式不一致"),
    PHOTO_IS_NOT_YOUR("30001", "该图片不是你的，你没有权限删除"),
    ARTICLE_NOT_EXIST("40001", "该文章不存在"),
    COMMENT_NOT_EXIST("40002", "该评论不存在"),
    ARTICLE_CAN_NOT_COMMENT("40003", "该文章不允许评论"),
    ;

    private String code;
    private String msg;

    ErrorCodeAndMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;

    }
}
