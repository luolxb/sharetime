package com.ruosen.sharetime.sharetime.module.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  返回信息枚举
 *  * @projectName ruosen-star
 *  * @title     resultInfoEnum   
 *  * @package    com.ruosen.star.ruosenstar.module.Enums  
 *  * @author Administrator     
 *  * @date   2019/9/28 0028 星期六
 *  * @version V1.0.0
 *  
 */
@AllArgsConstructor
public enum ResultInfoEnum {

    ERROR_MSG(-1, "哎呀~~~，出现异常"),
    SUCCESS_MSG(0, "成功！"),
    USER_IS_NOT_EXIST(1, "用户不存在"),
    CODE_IS_NULL(2, "验证码为空"),
    CODE_IS_OVERDUE(3, "验证码已过期"),
    CODE_IS_ERROR(4, "验证码错误"),
    PHONE_IS_USEING(5, "手机号码被占用"),
    UPLOAD_FILE_ERROR(6, "上传文件失败");

    @Setter
    @Getter
    private Integer code;

    @Setter
    @Getter
    private String msg;


}
