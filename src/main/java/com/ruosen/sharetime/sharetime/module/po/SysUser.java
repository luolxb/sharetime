package com.ruosen.sharetime.sharetime.module.po;

import com.ruosen.sharetime.sharetime.module.base.BasePo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ruosen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends BasePo implements Serializable {

    private static final long serialVersionUID = -3214451631899677555L;

    private String openId;
    private String signature;
    private String avatarUrl;
    private String city;
    private String country;
    private String language;
    private String nickName;
    private String province;
    private Integer gender;

    private String phone;

}
