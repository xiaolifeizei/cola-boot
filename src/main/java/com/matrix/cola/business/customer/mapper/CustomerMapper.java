package com.matrix.cola.business.customer.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.matrix.cola.business.customer.entity.CustomerEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 客户服务Mapper
 *
 * @author cui_feng
 * @since : 2022-04-20 14:18
 */
@Repository
public interface CustomerMapper extends BaseMapper<CustomerEntity> {

    @Select("select distinct c.* from customer c " +
            "inner join customer_category_detail ccd on c.id=ccd.customer_id " +
            "inner join customer_category cc on cc.id=ccd.category_id ${ew.customSqlSegment}")
    IPage<CustomerEntity> getCustomerList(Page<CustomerEntity> page, @Param("ew") Wrapper<CustomerEntity> queryWrapper);
}
