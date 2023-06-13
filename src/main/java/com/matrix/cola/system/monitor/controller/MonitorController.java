package com.matrix.cola.system.monitor.controller;

import cn.hutool.core.util.StrUtil;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.cache.CacheProxy;
import com.matrix.cola.common.service.ColaCacheName;
import com.matrix.cola.system.monitor.entity.MonitorInfo;
import com.matrix.cola.system.monitor.service.SystemMonitorService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 系统监控Controller
 *
 * @author : cui_feng
 * @since : 2022-06-13 10:44
 */
@RestController
@RequestMapping("/system/monitor")
@AllArgsConstructor
public class MonitorController {

    CacheProxy cacheProxy;

    SystemMonitorService monitorService;

    @PostMapping("/getSystemMonitor")
    @PreAuthorize("hasAuthority('administrator')")
    public Result getSystemMonitor() {
        try {
            return Result.ok().put("monitor", monitorService.getSystemMonitor());
        } catch (Exception ignored) {
            return Result.ok().put("monitor", new MonitorInfo());
        }
    }
}
