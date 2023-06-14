package com.matrix.cola.business.goodscategory.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.matrix.cola.business.goodscategory.entity.GoodsCategoryDetailEntity;
import com.matrix.cola.business.goodscategory.mapper.CategoryGoodsMapper;
import com.matrix.cola.business.goodscategory.service.GoodsCategoryDetailService;
import com.matrix.cola.business.goodscategory.service.GoodsCategoryService;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.service.AbstractEntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 物资分类与物资关联关系接口实现类
 *
 * @author : cui_feng
 * @since : 2022-06-24 10:14
 */
@Service
@AllArgsConstructor
public class GoodsCategoryDetailServiceImpl extends AbstractEntityService<GoodsCategoryDetailEntity, CategoryGoodsMapper> implements GoodsCategoryDetailService {

    GoodsCategoryService goodsCategoryService;

    @Override
    public Result beforeAdd(GoodsCategoryDetailEntity po) {

        if (ObjectUtil.isNull(po.getGoodsId())) {
            return Result.err("添加失败，物资不能为空");
        }
        if (ObjectUtil.isNull(po.getCategoryId())) {
            return Result.err("添加失败，物资分类不能为空");
        }
        if (ObjectUtil.isNull(goodsCategoryService.getOne(po.getCategoryId()))) {
            return Result.err("添加失败，物资分类不存在");
        }

        LambdaQueryWrapper<GoodsCategoryDetailEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCategoryDetailEntity::getCategoryId,po.getCategoryId())
                .eq(GoodsCategoryDetailEntity::getGoodsId, po.getGoodsId());
        if (ObjectUtil.isNotNull(getOne(queryWrapper))) {
            return Result.err("添加失败，添加的数据已经存在");
        }

        return super.beforeAdd(po);
    }

    @Override
    public Result getCategoryByGoodsId(Long goodsId) {
        if (ObjectUtil.isNull(goodsId)) {
            return Result.list(Collections.emptyList());
        }
        LambdaQueryWrapper<GoodsCategoryDetailEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCategoryDetailEntity::getGoodsId,goodsId);
        return Result.list(getList(queryWrapper));
    }

    @Override
    public Result deleteCategoryGoodsDetailByGoodsId(Long goodsId) {
        if (getMapper().deleteCategoryDetailByGoodsId(goodsId) > 0) {
            return Result.ok();
        }
        return Result.err("删除物资分类关联关系失败");
    }
}
