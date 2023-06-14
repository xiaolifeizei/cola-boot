package com.matrix.cola.business.custcategory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matrix.cola.business.custcategory.entity.CustomerCategoryDetailEntity;
import org.apache.ibatis.annotations.Delete;

/**
 * 客户分类关联Mapper
 *
 * @author : cui_feng
 * @since : 2022-06-28 10:54
 */
public interface CustomerCategoryDetailMapper extends BaseMapper<CustomerCategoryDetailEntity> {

    @Delete("delete from customer_category_detail where customer_id=#{customerId}")
    int deleteCategoryDetailByCustomerId(Long customerId);
}
