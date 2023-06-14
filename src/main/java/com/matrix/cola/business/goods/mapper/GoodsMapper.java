package com.matrix.cola.business.goods.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.matrix.cola.business.goods.entity.GoodsEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 物资实体类Mapper
 *
 * @author cui_feng
 * @since : 2022-04-20 14:18
 */
public interface GoodsMapper extends BaseMapper<GoodsEntity> {

    @Select("select distinct g.* from goods g " +
            "inner join goods_category_detail gcd on g.id=gcd.goods_id " +
            "inner join goods_category gc on gc.id=gcd.category_id ${ew.customSqlSegment}")
    IPage<GoodsEntity> getGoodsList(Page<GoodsEntity> page, @Param("ew") Wrapper<GoodsEntity> queryWrapper);
}
