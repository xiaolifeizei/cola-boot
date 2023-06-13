package com.matrix.cola.system.datalog.controller;

import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.system.datalog.entity.DataLogEntity;
import com.matrix.cola.system.datalog.service.DataLogService;
import com.matrix.cola.system.datalog.service.DataLogWrapperService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志Controller
 *
 * @author : cui_feng
 * @since : 2022-07-01 10:54
 */
@RestController
@RequestMapping("/system/dataLog")
@AllArgsConstructor
public class DataLogController {

    DataLogWrapperService dataLogWrapperService;

    DataLogService dataLogService;

    @PostMapping("/getDataLogPage")
    public Result getDataLogPage(@RequestBody Query<DataLogEntity> query) {
        return Result.page(dataLogWrapperService.getWrapperPage(query));
    }

    @PostMapping("/deleteDataLog")
    @PreAuthorize("hasAuthority('administrator')")
    public Result deleteDataLog(@RequestBody DataLogEntity dataLogEntity) {
        return dataLogService.deleteDataLog(dataLogEntity);
    }

    @PostMapping("/clearDataLog")
    @PreAuthorize("hasAuthority('administrator')")
    public Result clearDataLog() {
        return dataLogService.clearDataLog();
    }
}
