package com.matrix.cola.business.goods.controller;

import com.matrix.cola.business.goods.entity.GoodsEntity;
import com.matrix.cola.business.goods.service.GoodsService;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物资管理Controller
 *
 * @author : cui_feng
 * @since : 2022-06-21 10:26
 */
@RestController
@RequestMapping("/basics/goods")
@AllArgsConstructor
public class GoodsController {
    GoodsService goodsService;

    @PostMapping("/getCategoryGoodsPage")
    public Result getCategoryGoodsPage(@RequestBody Query<GoodsEntity> query) {
        return goodsService.getCategoryGoodsPage(query);
    }

    @PostMapping("/addGoods")
    public Result addGoods(@RequestBody GoodsEntity goodsPO) {
        return goodsService.insert(goodsPO);
    }

    @PostMapping("/updateGoods")
    public Result updateGoods(@RequestBody GoodsEntity goodsPO) {
        return goodsService.modify(goodsPO);
    }

    @PostMapping("/deleteGoods")
    public Result deleteGoods(@RequestBody GoodsEntity goodsPO) {
        return goodsService.remove(goodsPO);
    }
}
