package com.ruosen.sharetime.sharetime.exception;

import com.ruosen.sharetime.sharetime.module.enums.ResultInfoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *  自定义异常处理类
 *  * @projectName ruosen-star
 *  * @title     MyException   
 *  * @package    com.ruosen.star.ruosenstar.exception  
 *  * @author Administrator     
 *  * @date   2019/9/28 0028 星期六
 *  * @version V1.0.0
 *  
 */
@Data
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = -5992021692405200159L;

    private Integer errorCode;

    private String errorMessage;

    public CustomException(String errorMessage) {
        super(errorMessage);
    }

    public CustomException(ResultInfoEnum resultInfoEnum) {
        this(resultInfoEnum.getCode(), resultInfoEnum.getMsg());
    }
}
