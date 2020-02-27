package com.ruosen.sharetime.sharetime.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruosen.sharetime.sharetime.exception.CustomException;
import com.ruosen.sharetime.sharetime.module.base.ResponseData;
import com.ruosen.sharetime.sharetime.module.enums.ResultInfoEnum;
import com.ruosen.sharetime.sharetime.module.vo.SmsValidRq;
import com.ruosen.sharetime.sharetime.module.vo.SysUserReq;
import com.ruosen.sharetime.sharetime.module.vo.SysUserVo;
import com.ruosen.sharetime.sharetime.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.controller
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-01-27 14:59
 **/
@RestController
@Slf4j
@RequestMapping("/sysUser")
public class SysUserController {

    @Value("${uploadPice}")
    private String uploadPicePath;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/registerOrLogin")
    public ResponseData registerOrLogin(@RequestBody SysUserReq sysUserReq) {
        SysUserVo sysUserVo = sysUserService.registerOrLogin(sysUserReq);
        return new ResponseData<>().ok(sysUserVo);
    }

    @PostMapping("/updateUserInfo")
    public ResponseData updateUserInfo(@RequestBody SysUserReq sysUserReq) {
        SysUserVo sysUserVo = sysUserService.updateUserInfo(sysUserReq);
        return new ResponseData<>().ok(sysUserVo);
    }

    @PostMapping("/uploadPice")
    public ResponseData uploadPice(@RequestParam(value = "file", required = false) MultipartFile file) {
        String url = null;
        String type = null;
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            type = filename.substring(filename.lastIndexOf("."));
        }

        String newFileName = UUID.randomUUID().toString().replaceAll("-", "");
        newFileName = newFileName + type;

        try {
            File file1 = new File(uploadPicePath);
            boolean exists = file1.exists();
            if (!exists) {
                file1.mkdirs();
            }
            file.transferTo(new File(uploadPicePath + newFileName));
        } catch (IOException e) {
            log.error("e", e);
            throw new CustomException(ResultInfoEnum.UPLOAD_FILE_ERROR);
        }

        url = "/upload/" + newFileName;
        return new ResponseData().ok(url);
    }

    @GetMapping("/userList")
    public ResponseData userList() {
        List<SysUserVo> sysUserVos = sysUserService.userList();
        return new ResponseData().ok(sysUserVos);
    }

    /************************************验证码登录或注册****************************************************/

    @GetMapping("/getCode")
    public ResponseData getCode(@RequestParam String phone, HttpSession session) {
        if (StringUtils.isBlank(phone)) {
            return new ResponseData().error("用户手机号不能为空！");
        }

        String code = sysUserService.sendPhoneCode(phone);
        return new ResponseData().ok(code);
    }

    /**
     * 验证验证码
     *
     * @param smsValidRq
     * @param bindingResult
     * @return
     */
    @PostMapping("/valid")
    public ResponseData valid(@Valid @RequestBody SmsValidRq smsValidRq,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseData().error(bindingResult.getFieldError().getDefaultMessage());
        }
        SysUserVo userVo = sysUserService.valid(smsValidRq);

//        redisTemplate.delete(PHONE_CODE_SESSION + smsValidRq.getMobile());
        return new ResponseData().ok(userVo);
    }

    @GetMapping("/weixinLogin")
    public ResponseData weixinLogin(@RequestParam String code) {
        String appid = "wx009938ce6968fda8";
        String secret = "2033689428847d4c4d087790b8e4d0b4";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";


        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String str = responseEntity.getBody();

        log.info("openid==>{}", str);
        // 转成Json对象 获取openid
        JSONObject jsonObject = JSONObject.parseObject(str);

        // 我们需要的openid，在一个小程序中，openid是唯一的
        String openid = jsonObject.get("openid").toString();
        return new ResponseData().ok(openid);
    }


}
