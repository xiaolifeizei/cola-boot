package com.matrix.cola.system.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 交换区信息
 *
 * @author : cui_feng
 * @since : 2022-06-14 12:39
 */
@Data
public class SwapInfo implements Serializable {

    private String total = "";
    private String used = "";
    private String available = "";
    private String usageRate = "0";
}
