package com.matrix.cola.system.errorlog.controller;

import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.system.errorlog.entity.ErrorLogEntity;
import com.matrix.cola.system.errorlog.service.ErrorLogService;
import com.matrix.cola.system.errorlog.service.ErrorLogWrapperService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 错误日志controller
 *
 * @author : cui_feng
 * @since : 2022-06-10 12:45
 */
@RequestMapping("/system/errorlog")
@RestController
@AllArgsConstructor
public class ErrorLogController {

    ErrorLogWrapperService errorLogWrapper;

    ErrorLogService errorLogService;

    @PostMapping("/getErrorLogPage")
    @PreAuthorize("hasAuthority('administrator')")
    public Result getErrorLogPage(@RequestBody Query<ErrorLogEntity> query) {
        return Result.page(errorLogWrapper.getWrapperPage(query));
    }

    @PostMapping("/clearErrorLog")
    @PreAuthorize("hasAuthority('administrator')")
    public Result clearErrorLog() {
        return errorLogService.clearErrorLog();
    }
}
