package com.matrix.cola.system.serialno.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.matrix.cola.common.entity.BaseEntity;
import lombok.Data;

/**
 * 表序列号实体类
 *
 * @author : cui_feng
 * @since : 2022-04-07 16:38
 */
@Data
@TableName("system_serial_no")
public class SerialNo extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String tableName;
    private String maxSerial;
    private String serialDate;
    private String groupId;
}
