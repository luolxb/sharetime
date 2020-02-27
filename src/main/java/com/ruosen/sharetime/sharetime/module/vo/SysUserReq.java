package com.ruosen.sharetime.sharetime.module.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.module.vo
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-01-27 15:15
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserReq implements Serializable {

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
