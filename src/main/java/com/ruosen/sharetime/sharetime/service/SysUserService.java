package com.ruosen.sharetime.sharetime.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruosen.sharetime.sharetime.module.po.SysUser;
import com.ruosen.sharetime.sharetime.module.vo.SmsValidRq;
import com.ruosen.sharetime.sharetime.module.vo.SysUserReq;
import com.ruosen.sharetime.sharetime.module.vo.SysUserVo;

import java.util.List;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.service
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-01-27 14:55
 **/
public interface SysUserService extends IService<SysUser> {

    SysUserVo registerOrLogin(SysUserReq sysUserReq);

    SysUserVo updateUserInfo(SysUserReq sysUserReq);

    List<SysUserVo> userList();


    String sendPhoneCode(String mobile);

    SysUserVo valid(SmsValidRq smsValidRq);
}
