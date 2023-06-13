package com.matrix.cola.system.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Cpu信息
 *
 * @author : cui_feng
 * @since : 2022-06-14 12:35
 */
@Data
public class CpuInfo implements Serializable {

    private String name = "";
    private String packages = "";
    private String core = "";
    private Integer coreNumber = 0;
    private String logic = "";
    private String used = "0";
    private String idle = "";

}
