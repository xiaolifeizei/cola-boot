package com.matrix.cola.system.datascope.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matrix.cola.system.datascope.entity.DataScopeEntity;
import org.apache.ibatis.annotations.Delete;

/**
 * 数据权限Mapper
 *
 * @author : cui_feng
 * @since : 2022-06-06 11:28
 */
public interface DataScopeMapper extends BaseMapper<DataScopeEntity> {

    @Delete("delete from system_data_scope where id=#{id}")
    int deleteDataScope(Long id);
}
