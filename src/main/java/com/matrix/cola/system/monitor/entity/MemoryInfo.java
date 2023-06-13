package com.matrix.cola.system.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 内存信息
 *
 * @author : cui_feng
 * @since : 2022-06-14 12:37
 */
@Data
public class MemoryInfo implements Serializable {

    private String total = "";
    private String available = "";
    private String used = "";
    private String usageRate = "0";
}
