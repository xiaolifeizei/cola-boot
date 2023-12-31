package com.matrix.cola.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.matrix.cola.common.entity.BaseColaEntity;
import lombok.Data;

/**
 * 用户实体类
 *
 * @author : cui_feng
 * @since : 2022-04-20 13:02
 */
@Data
@TableName("system_user")
public class UserEntity extends BaseColaEntity {

    /**
     * 姓名
     */
    private String name;

    /**
     * 登陆名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号
    **/
    private String ids;

    /**
     * 禁用：0=未禁用，1=已禁用
     */
    private Integer noUse;

    /**
     * 备注
     */
    private String remark;
}
