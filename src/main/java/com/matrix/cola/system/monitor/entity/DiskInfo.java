package com.matrix.cola.system.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 磁盘信息
 *
 * @author : cui_feng
 * @since : 2022-06-14 12:40
 */
@Data
public class DiskInfo implements Serializable {

    private String total = "";
    private String available = "";
    private String used = "";
    private String usageRate = "0";
}
