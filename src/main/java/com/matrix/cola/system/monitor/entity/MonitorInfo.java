package com.matrix.cola.system.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统监控
 *
 * @author : cui_feng
 * @since : 2022-06-14 12:31
 */
@Data
public class MonitorInfo implements Serializable {

    private JvmInfo jvm = new JvmInfo();
    private SystemInfo sys = new SystemInfo();
    private CpuInfo cpu = new CpuInfo();
    private MemoryInfo memory = new MemoryInfo();
    private SwapInfo swap = new SwapInfo();
    private DiskInfo disk = new DiskInfo();
    private String name = "";
    private String time = "";

}
