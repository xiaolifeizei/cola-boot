package com.matrix.cola.system.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Jvm信息
 *
 * @author : cui_feng
 * @since : 2022-06-14 12:34
 */
@Data
public class JvmInfo implements Serializable {

    private String maxMemory = "";
    private String freeMemory = "";
    private String totalMemory = "";
    private String usableMemory = "";
    private String javaVersion = "";
    private String jvmVersion = "";
    private Integer totalTread = 0;
    private String usedRate = "0";
    private String freeRate = "0";
}
