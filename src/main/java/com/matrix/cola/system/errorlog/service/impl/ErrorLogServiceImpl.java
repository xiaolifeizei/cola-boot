package com.matrix.cola.system.errorlog.service.impl;

import com.matrix.cola.common.Result;
import com.matrix.cola.common.service.AbstractColaEntityService;
import com.matrix.cola.system.errorlog.entity.ErrorLogEntity;
import com.matrix.cola.system.errorlog.mapper.ErrorLogMapper;
import com.matrix.cola.system.errorlog.service.ErrorLogService;
import org.springframework.stereotype.Service;

/**
 * 系统错误接口实现类
 *
 * @author : cui_feng
 * @since : 2022-06-10 12:44
 */
@Service
public class ErrorLogServiceImpl extends AbstractColaEntityService<ErrorLogEntity, ErrorLogMapper> implements ErrorLogService {

    @Override
    public Result clearErrorLog() {
        if (getMapper().clearErrorLog() > 0) {
            return Result.ok();
        }
        return Result.err("清空失败");
    }
}
