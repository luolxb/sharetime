package com.ruosen.sharetime.sharetime.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruosen.sharetime.sharetime.dao.SysUserMapper;
import com.ruosen.sharetime.sharetime.exception.CustomException;
import com.ruosen.sharetime.sharetime.module.base.ResponseData;
import com.ruosen.sharetime.sharetime.module.enums.ResultInfoEnum;
import com.ruosen.sharetime.sharetime.module.po.SysUser;
import com.ruosen.sharetime.sharetime.module.vo.SmsValidRq;
import com.ruosen.sharetime.sharetime.module.vo.SysUserReq;
import com.ruosen.sharetime.sharetime.module.vo.SysUserVo;
import com.ruosen.sharetime.sharetime.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.service.impl
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-01-27 14:56
 **/
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    public final static String PHONE_CODE_SESSION = "phone-code-session:";
    /**
     * 设置超时时间-可自行调整
     */
    final static String DEFAULTCONNECTTIMEOUT = "sun.net.client.defaultConnectTimeout";
    final static String DEFAULTREADTIMEOUT = "sun.net.client.defaultReadTimeout";
    final static String TIMEOUT = "10000";
    /**
     * 初始化ascClient需要的几个参数
     * 短信API产品名称（短信产品名固定，无需修改）
     * 短信API产品域名（接口地址固定，无需修改）
     * 你的accessKeyId,填你自己的 上文配置所得  自行配置
     * 你的accessKeySecret,填你自己的 上文配置所得 自行配置
     */
    final static String PRODUCT = "Dysmsapi";


    /**
     * 手机验证部分配置
     */
    final static String DOMAIN = "dysmsapi.aliyuncs.com";
    /**
     * 替换成你的AK (产品密)
     */
    final static String ACCESSKEYID = "LTAI4FhapXksnEErwtkwSpeN";
    final static String ACCESSKEYSECRET = "jxanlpURiZpgRde2hnbKcxg6CKgzlS";
    /**
     * 必填:短信签名-可在短信控制台中找到
     * 阿里云配置你自己的短信签名填入
     */
    final static String SIGNNAME = "ruosen";
    /**
     * 必填:短信模板-可在短信控制台中找到
     * 阿里云配置你自己的短信模板填入
     */
    final static String TEMPLATECODE = "SMS_177551900";
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取验证码
     *
     * @return
     */
    private static String vcode() {
        String code = String.valueOf((int) (Math.random() * 999999));
        log.info("vcode ==> {}", code);

        return code;
    }

    @Override
    public SysUserVo registerOrLogin(SysUserReq sysUserReq) {
        SysUser sysUser = new SysUser();
        SysUser sUser = new SysUser();
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUserReq, sysUser);
        if (StringUtils.isNotBlank(sysUserReq.getOpenId())) {
            // 微信登录
            SysUser user = this.getOne(new QueryWrapper<SysUser>().eq("open_id", sysUser.getOpenId()));
            if (null == user) {
                sysUser.setCreateDate(new Date());
                sysUser.setCreateBy(sysUserReq.getNickName());
                this.baseMapper.insert(sysUser);
            } else {
                sUser.setOpenId(sysUserReq.getOpenId());
                sUser.setUpdateBy(sysUserReq.getNickName());
                sUser.setUpdateDate(new Date());
                this.baseMapper.update(sUser, new QueryWrapper<SysUser>().eq("open_id", sUser.getOpenId()));
            }
            BeanUtils.copyProperties(user == null ? sysUser : user, sysUserVo);
        }
        return sysUserVo;

    }

    @Override
    public SysUserVo updateUserInfo(SysUserReq sysUserReq) {
        SysUser sysUser = new SysUser();
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUserReq, sysUser);
        if (StringUtils.isNotBlank(sysUserReq.getOpenId())) {
            // 微信登录
            SysUser user = this.getOne(new QueryWrapper<SysUser>().eq("open_id", sysUser.getOpenId()));
            if (null == user) {
                sysUser.setCreateDate(new Date());
                sysUser.setCreateBy(sysUserReq.getNickName());
                this.baseMapper.insert(sysUser);
            } else {
                Integer count = this.baseMapper.selectCount(new QueryWrapper<SysUser>().eq("phone", sysUser.getPhone()));

                if (count != null && count > 1) {
                    throw new CustomException(ResultInfoEnum.PHONE_IS_USEING);
                }
                sysUser.setUpdateBy(sysUserReq.getNickName());
                sysUser.setUpdateDate(new Date());
                this.baseMapper.update(sysUser, new QueryWrapper<SysUser>().eq("open_id", sysUser.getOpenId()));
            }
            BeanUtils.copyProperties(user == null ? sysUser : user, sysUserVo);
        }
//        else {
//            SysUser user = this.getOne(new QueryWrapper<SysUser>().eq("phone", sysUser.getPhone()));
//            if (user != null) {
//                sysUser.setUpdateBy(sysUserReq.getPhone());
//                sysUser.setUpdateDate(new Date());
//                this.baseMapper.update(sysUser, new QueryWrapper<SysUser>().eq("phone", sysUser.getPhone()));
//            } else {
//                sysUser.setCreateDate(new Date());
//                sysUser.setCreateBy(sysUserReq.getPhone());
//                this.baseMapper.insert(sysUser);
//            }
//            BeanUtils.copyProperties(user == null ? sysUser : user, sysUserVo);
//        }
        return sysUserVo;

    }

    @Override
    public List<SysUserVo> userList() {

        List<SysUserVo> sysUserVoList = (List<SysUserVo>) redisTemplate.opsForValue().get("userList");
        if (null == sysUserVoList) {
            List<SysUser> sysUserList = this.baseMapper.selectList(new QueryWrapper<>());
            List<SysUserVo> sysUserVos = new ArrayList<>();
            sysUserList.forEach(sysUser -> {
                SysUserVo sysUserVo = new SysUserVo();
                BeanUtils.copyProperties(sysUser, sysUserVo);
                sysUserVos.add(sysUserVo);
            });
            redisTemplate.opsForValue().set("userList", new ResponseData<>().ok(sysUserVos));
            return sysUserVos;
        }

        return sysUserVoList;
    }

    @Override
    public String sendPhoneCode(String mobile) {
        String code = vcode();
        if (StringUtils.isBlank(mobile)) {
            throw new CustomException(ResultInfoEnum.CODE_IS_NULL);
        }

        /**
         * 短信验证---阿里大于工具
         */
        // 设置超时时间-可自行调整
        System.setProperty(DEFAULTCONNECTTIMEOUT, TIMEOUT);
        System.setProperty(DEFAULTREADTIMEOUT, TIMEOUT);
        // 初始化ascClient需要的几个参数
        final String product = PRODUCT;
        final String domain = DOMAIN;
        // 替换成你的AK
        final String accessKeyId = ACCESSKEYID;
        final String accessKeySecret = ACCESSKEYSECRET;
        // 初始化ascClient,暂时不支持多region
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,
                    domain);
        } catch (Exception e1) {
            log.error("DefaultProfile error", e1);
        }

        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(mobile);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(SIGNNAME);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(TEMPLATECODE);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{ \"code\":\"" + code + "\"}");
        // 可选-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");
        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(UUID.randomUUID().toString().replace("-", ""));
        // 请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            if (StringUtils.isNotBlank(sendSmsResponse.getCode())
                    && StringUtils.equals("OK", sendSmsResponse.getCode())) {
                // 请求成功
                log.info("获取验证码成功 ==> {}", sendSmsResponse.getMessage());
            } else {
                //如果验证码出错，会输出错误码告诉你具体原因
                log.error("获取验证码失败 ==>{}", sendSmsResponse.getMessage());
            }
        } catch (Exception e) {
            log.error("由于系统维护 无法使用", e);
            throw new CustomException(ResultInfoEnum.ERROR_MSG);
        }

        redisTemplate.opsForValue().set(PHONE_CODE_SESSION + mobile, code, 60 * 5, TimeUnit.SECONDS);

        return code;
    }

    @Override
    public SysUserVo valid(SmsValidRq smsValidRq) {

        SysUserVo sysUserVo = new SysUserVo();
        SysUser sysUser = new SysUser();
        String code = (String) redisTemplate.opsForValue().get(PHONE_CODE_SESSION + smsValidRq.getMobile());
        // 验证码已经过期
        if (StringUtils.isBlank(code)) {
            throw new CustomException(ResultInfoEnum.CODE_IS_OVERDUE);
        }
        // 如果输入验证不等于缓存中验证
        if (!StringUtils.equals(code, smsValidRq.getValidCode())) {
            throw new CustomException(ResultInfoEnum.CODE_IS_ERROR);
        }

        SysUser user = this.baseMapper.selectOne(new QueryWrapper<SysUser>().eq("phone", smsValidRq.getMobile()));
        if (null == user) {
            sysUser.setOpenId(UUID.randomUUID().toString().replaceAll("-", ""));
            sysUser.setPhone(smsValidRq.getMobile());
            sysUser.setNickName(smsValidRq.getMobile());
            sysUser.setCreateBy(smsValidRq.getMobile());
            sysUser.setCreateDate(new Date());
            this.baseMapper.insert(sysUser);
        } else {
            user.setUpdateBy(smsValidRq.getMobile());
            user.setUpdateDate(new Date());
            this.baseMapper.update(user, new QueryWrapper<SysUser>().eq("phone", smsValidRq.getMobile()));
        }
        BeanUtils.copyProperties(user == null ? sysUser : user, sysUserVo);

        return sysUserVo;
    }


}
