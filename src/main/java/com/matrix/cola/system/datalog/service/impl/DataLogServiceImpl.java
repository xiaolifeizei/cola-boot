package com.matrix.cola.system.datalog.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.matrix.cola.common.ColaConstant;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.BaseColaEntity;
import com.matrix.cola.common.service.AbstractColaEntityService;
import com.matrix.cola.system.datalog.entity.DataLogEntity;
import com.matrix.cola.system.datalog.mapper.DataLogMapper;
import com.matrix.cola.system.datalog.service.DataLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * 操作日志服务实现类
 *
 * @author : cui_feng
 * @since : 2022-04-08 10:09
 */
@Slf4j
@Service
public class DataLogServiceImpl extends AbstractColaEntityService<DataLogEntity, DataLogMapper> implements DataLogService {


    @Override
    public void addUpdateLog(String tableName, BaseColaEntity before, BaseColaEntity after) {
        try {
            DataLogEntity dataLogColaEntity = new DataLogEntity();
            dataLogColaEntity.setTableName(tableName);
            dataLogColaEntity.setBeforeData(JSONUtil.toJsonStr(before));
            dataLogColaEntity.setAfterData(JSONUtil.toJsonStr(after));
            dataLogColaEntity.setOperation("更新");
            dataLogColaEntity.setCreateTime(new Date());
            add(dataLogColaEntity);
        } catch (Exception e) {
            log.error("记录更新日志失败");
            log.error(e.getMessage());
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            log.error(stringWriter.toString());
        }

    }

    @Override
    public void addDeleteLog(String tableName, BaseColaEntity before) {
        try {
            DataLogEntity dataLogColaEntity = new DataLogEntity();
            dataLogColaEntity.setTableName(tableName);
            dataLogColaEntity.setBeforeData(JSONUtil.toJsonStr(before));
            dataLogColaEntity.setOperation("删除");
            dataLogColaEntity.setCreateTime(new Date());
            add(dataLogColaEntity);
        } catch (Exception e) {
            log.error("记录删除日志失败");
            log.error(e.getMessage());
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            log.error(stringWriter.toString());
        }

    }

    @Override
    public Result deleteDataLog(DataLogEntity dataLogEntity) {
        if (ObjectUtil.isNull(dataLogEntity) || ObjectUtil.isNull(dataLogEntity.getId())) {
            return Result.err("删除失败，id不能为空");
        }
        if (getMapper().deleteDataLog(dataLogEntity.getId()) == ColaConstant.YES) {
            return Result.ok();
        }
        return Result.err("删除失败");
    }

    @Override
    public Result clearDataLog() {
        getMapper().clearDataLog();
        return Result.ok();
    }
}
