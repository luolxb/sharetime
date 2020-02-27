package com.ruosen.sharetime.sharetime.module.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 *  验证码实体
 *  * @projectName ruosen-star
 *  * @title     SmsValidRq   
 *  * @package    com.ruosen.star.ruosenstar.module.vo  
 *  * @author Administrator     
 *  * @date   2019/11/23 0023 星期六
 *  * @version V1.0.0
 *  
 */
@Data
public class SmsValidRq {

    @NotEmpty(message = "手机号码不能为空")
    private String mobile;

    @NotEmpty(message = "验证码不能为空")
    private String validCode;
}
