package com.matrix.cola.system.serialno.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.matrix.cola.system.serialno.entity.SerialNo;
import com.matrix.cola.system.serialno.mapper.SerialNoMapper;
import com.matrix.cola.system.serialno.service.SerialNoService;
import com.matrix.cola.common.service.AbstractEntityService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 序列号服务接口实现类
 *
 * @author : cui_feng
 * @since : 2022-04-07 16:46
 */
@Service
public class SerialNoServiceImpl extends AbstractEntityService<SerialNo, SerialNoMapper> implements SerialNoService {

    @Override
    public synchronized String getSerialNo(String tableName, String prefix,String serialPrefix, int length, String groupId) {

        if (StrUtil.isEmpty(tableName)) {
            return null;
        }
        if (StrUtil.isEmpty(prefix)) {
            prefix = "";
        }
        if (StrUtil.isEmpty(serialPrefix)) {
            serialPrefix = "";
        }

        // 最小长度为5
        if (length<4) {
            length = 4;
        }
        // 表名为大写
        tableName = tableName.trim().toUpperCase();
        LambdaQueryWrapper<SerialNo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SerialNo::getTableName,tableName).eq(SerialNo::getGroupId,groupId);
        List<SerialNo> serialList = getList(queryWrapper);
        SerialNo serialNoPO = ObjectUtil.isEmpty(serialList) ? null : serialList.get(0);

        String todayNo = DateUtil.format(new Date(),"YYYYMMdd");
        // 查不到，初始化一个并返回
        if (serialNoPO == null) {
            String serialNo = fillZero("1",length);
            SerialNo sPO = new SerialNo();
            sPO.setTableName(tableName);
            sPO.setMaxSerial(serialNo);
            sPO.setSerialDate(todayNo);
            sPO.setGroupId(groupId);
            // 入库
            add(sPO);
            return prefix.trim() + todayNo + serialPrefix.trim() + serialNo;
        }

        // 取数据库中的序列号日期
        String serialDate = serialNoPO.getSerialDate();
        // 数据库中序号日期与当前日期不一致时重新初始化
        if (serialDate==null || !serialDate.equals(todayNo)) {
            String serialNo = fillZero("1",length);
            serialNoPO.setMaxSerial(serialNo);
            serialNoPO.setSerialDate(todayNo);
            // 入库
            update(serialNoPO);
            return prefix.trim() + todayNo.trim() + serialPrefix.trim() + serialNo;
        }

        // 取出序列号
        String maxvalue = serialNoPO.getMaxSerial();
        // 转成Long型
        long serialno = Long.parseLong(maxvalue);
        // 序列号加1
        serialno = serialno + 1;
        maxvalue = fillZero(Long.toString(serialno),length);
        serialNoPO.setMaxSerial(maxvalue);
        serialNoPO.setSerialDate(todayNo);
        // 入库
        update(serialNoPO);
        return prefix.trim() + todayNo + serialPrefix.trim() + maxvalue;
    }

    @Override
    public String getSerialNo(String tableName, int length, String groupid) {
        return getSerialNo(tableName,"","",length,groupid);
    }

    @Override
    public String getSerialNo(String tableName, String groupId) {
        return getSerialNo(tableName,4,groupId);
    }

    /**
     * 序号补零
     * @param num 被补零的数字
     * @param length 补零后的长度
     * @return 补零后的数字
     */
    private String fillZero(String num,int length) {
        if (length<4) {
            length = 4;
        }
        if (num.length()==length) {
            return num;
        }
        if (num.length()>length) {
            return num.substring(num.length()-length);
        }

        int count = length - num.length();

        StringBuilder numBuilder = new StringBuilder(num);
        for(int i = 0; i<count; i++) {
            numBuilder.insert(0, "0");
        }
        return numBuilder.toString();
    }

}
