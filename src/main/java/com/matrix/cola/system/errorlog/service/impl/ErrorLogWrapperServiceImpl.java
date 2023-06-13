package com.matrix.cola.system.errorlog.service.impl;

import com.matrix.cola.common.service.AbstractColaEntityWrapperService;
import com.matrix.cola.system.errorlog.entity.ErrorLogEntity;
import com.matrix.cola.system.errorlog.entity.ErrorLogEntityWrapper;
import com.matrix.cola.system.errorlog.service.ErrorLogService;
import com.matrix.cola.system.errorlog.service.ErrorLogWrapperService;
import org.springframework.stereotype.Service;

/**
 * 错误日志包装类
 *
 * @author : cui_feng
 * @since : 2022-06-10 13:20
 */
@Service
public class ErrorLogWrapperServiceImpl extends AbstractColaEntityWrapperService<ErrorLogEntity, ErrorLogEntityWrapper, ErrorLogService> implements ErrorLogWrapperService {
    @Override
    public ErrorLogEntityWrapper entityWrapper(ErrorLogEntity entity) {
        return new ErrorLogEntityWrapper();
    }
}
