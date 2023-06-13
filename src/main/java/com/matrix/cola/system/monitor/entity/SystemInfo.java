package com.matrix.cola.system.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统信息
 *
 * @author : cui_feng
 * @since : 2022-06-14 12:32
 */
@Data
public class SystemInfo implements Serializable {
    private String os = "";
    private String day = "";
    private String ip = "";
}
