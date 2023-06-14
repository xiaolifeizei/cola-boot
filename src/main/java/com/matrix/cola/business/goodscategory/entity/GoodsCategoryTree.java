package com.matrix.cola.business.goodscategory.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.matrix.cola.common.ColaConstant;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 物资分类树
 *
 * @author : cui_feng
 * @since : 2022-06-24 11:04
 */
@Data
public class GoodsCategoryTree extends GoodsCategoryEntity {

    /**
     * 子节点
     */
    public List<GoodsCategoryTree> children = new ArrayList<>();

    public static List<GoodsCategoryTree> getCategoryTree(List<GoodsCategoryEntity> categoryList) {
        List<GoodsCategoryTree> goodsCategoryTreeList = new ArrayList<>();
        if (ObjectUtils.isEmpty(categoryList)) {
            return goodsCategoryTreeList;
        }

        List<GoodsCategoryEntity> hasTreeList = new ArrayList<>();

        goodsCategoryTreeList = getCategoryTree(ColaConstant.TREE_ROOT_ID, categoryList, hasTreeList);

        GoOn:
        for (GoodsCategoryEntity category : categoryList) {
            for (GoodsCategoryEntity categoryTree : hasTreeList) {
                if (Objects.equals(categoryTree.getId(),category.getId())) {
                    continue GoOn;
                }
            }
            GoodsCategoryTree goodsCategoryTree = getCategoryTree(category);
            goodsCategoryTree.setChildren(getCategoryTree(category.getId(), categoryList, hasTreeList));
            goodsCategoryTreeList.add(goodsCategoryTree);
        }


        return goodsCategoryTreeList;
    }

    private static List<GoodsCategoryTree> getCategoryTree(Long parentId, List<GoodsCategoryEntity> categoryList, List<GoodsCategoryEntity> hasTreeList) {
        List<GoodsCategoryTree> goodsCategoryTreeList = new ArrayList<>();
        if (ObjectUtils.isEmpty(categoryList)) {
            return goodsCategoryTreeList;
        }

        for (GoodsCategoryEntity category : categoryList) {
            if (Objects.equals(category.getParentId(),parentId)) {
                hasTreeList.add(category);
                GoodsCategoryTree goodsCategoryTree = getCategoryTree(category);
                goodsCategoryTree.setChildren(getCategoryTree(category.getId(), categoryList, hasTreeList));
                goodsCategoryTreeList.add(goodsCategoryTree);
            }
        }
        return goodsCategoryTreeList;
    }

    private static GoodsCategoryTree getCategoryTree(GoodsCategoryEntity goodsCategoryEntity) {
        GoodsCategoryTree goodsCategoryTree = new GoodsCategoryTree();
        BeanUtil.copyProperties(goodsCategoryEntity, goodsCategoryTree);
        return goodsCategoryTree;
    }
}
