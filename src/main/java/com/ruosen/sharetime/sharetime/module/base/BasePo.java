package com.ruosen.sharetime.sharetime.module.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.module.base
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-01-27 14:33
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePo implements Serializable {

    private static final long serialVersionUID = 5113115686619242497L;

    private long id;
    private String remark1;
    private String remark2;
    private String remark3;
    private String remark4;
    private String remark5;
    private Date createDate;
    private String createBy;
    private Date updateDate;
    private String updateBy;
    private String isDelete;
    private String enable;

}
