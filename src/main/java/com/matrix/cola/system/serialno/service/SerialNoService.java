package com.matrix.cola.system.serialno.service;

import com.matrix.cola.common.service.BaseService;

/**
 * 序列号服务接口
 *
 * @author cui_feng
 * @since : 2022-04-20 14:18
 */
public interface SerialNoService extends BaseService {

    /**
     * 生成序列号，格式为：前缀+yyyyMMdd+序号前缀+序号，每天更新
     *
     * @param tableName     表名
     * @param prefix        前缀
     * @param serialPrefix 序号前缀
     * @param length        序列号长度，最少4位
     * @param groupId       机构
     * @return 序号
     */
    String getSerialNo(String tableName,String prefix,String serialPrefix,int length, String groupId);

    /**
     * 生成序列号，格式为： yyyyMMdd+序号，每天更新
     * @param tableName 表名
     * @param length    长度
     * @param groupId   机构id
     * @return 序号
     */
    String getSerialNo(String tableName,int length,String groupId);

    /**
     * 生成序列号，格式为： yyyyMMdd+4位序号，每天更新
     * @param tableName 表名
     * @param groupId   机构id
     * @return 序号
     */
    String getSerialNo(String tableName,String groupId);
}
