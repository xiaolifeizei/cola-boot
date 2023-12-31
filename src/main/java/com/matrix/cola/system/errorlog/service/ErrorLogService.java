package com.matrix.cola.system.errorlog.service;

import com.matrix.cola.common.Result;
import com.matrix.cola.common.service.BaseColaEntityService;
import com.matrix.cola.system.errorlog.entity.ErrorLogEntity;

/**
 * 系统错误实体类服务接口
 *
 * @author : cui_feng
 * @since : 2022-06-10 12:42
 */
public interface ErrorLogService extends BaseColaEntityService<ErrorLogEntity> {

    /**
     * 清空错误日志，物理删除
     * @return
     */
    Result clearErrorLog();
}
