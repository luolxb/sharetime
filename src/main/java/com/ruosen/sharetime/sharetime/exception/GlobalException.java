package com.ruosen.sharetime.sharetime.exception;

import com.ruosen.sharetime.sharetime.module.base.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *   捕获全局异常
 *  * @projectName ruosen-star
 *  * @title     GlobalException   
 *  * @package    com.ruosen.star.ruosenstar.exception  
 *  * @author Administrator     
 *  * @date   2019/9/28 0028 星期六
 *  * @version V1.0.0
 *  
 */
@Slf4j
@ControllerAdvice
public class GlobalException {


    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseData errorHandler(Exception ex) {
        log.error("errorHandler :{}", ex);
        return new ResponseData().error("未知错误");
    }

    /**
     * 拦截捕捉自定义异常 CustomException.class
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = CustomException.class)
    public ResponseData customException(CustomException ex) {
        ResponseData data = new ResponseData();
        data.setCode(ex.getErrorCode());
        data.setMsg(ex.getErrorMessage());

        log.error("customException :{}", ex);
        return data;
    }

}
