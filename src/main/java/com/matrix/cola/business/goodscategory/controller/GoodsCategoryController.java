package com.matrix.cola.business.goodscategory.controller;

import com.matrix.cola.business.goodscategory.entity.GoodsCategoryEntity;
import com.matrix.cola.business.goodscategory.service.GoodsCategoryDetailService;
import com.matrix.cola.business.goodscategory.service.GoodsCategoryService;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物资分类Controller
 *
 * @author : cui_feng
 * @since : 2022-06-24 10:47
 */
@RestController
@RequestMapping("/basics/goods/category")
@AllArgsConstructor
public class GoodsCategoryController {

    GoodsCategoryService goodsCategoryService;

    GoodsCategoryDetailService goodsCategoryDetailService;

    @PostMapping("/getCategoryTree")
    public Result getCategoryTree(@RequestBody Query<GoodsCategoryEntity> query) {
        return goodsCategoryService.getCategoryTree(query);
    }

    @PostMapping("/getCategoryByGoodsId")
    public Result getCategoryByGoodsId(Long goodsId) {
        return goodsCategoryDetailService.getCategoryByGoodsId(goodsId);
    }

    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody GoodsCategoryEntity goodsCategoryPO) {
        return goodsCategoryService.insert(goodsCategoryPO);
    }

    @PostMapping("/updateCategory")
    public Result updateCategory(@RequestBody GoodsCategoryEntity goodsCategoryPO) {
        return goodsCategoryService.modify(goodsCategoryPO);
    }

    @PostMapping("/deleteCategory")
    public Result deleteCategory(@RequestBody GoodsCategoryEntity goodsCategoryPO) {
        return goodsCategoryService.remove(goodsCategoryPO);
    }
}
