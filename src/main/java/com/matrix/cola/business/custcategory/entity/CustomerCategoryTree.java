package com.matrix.cola.business.custcategory.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.matrix.cola.common.ColaConstant;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 客户分类实体类
 *
 * @author : cui_feng
 * @since : 2022-06-28 10:18
 */
@Data
public class CustomerCategoryTree extends CustomerCategoryEntity {

    /**
     * 子节点
     */
    private List<CustomerCategoryTree> children;


    public static List<CustomerCategoryTree> getCategoryTree(List<CustomerCategoryEntity> categoryList) {
        List<CustomerCategoryTree> customerCategoryTreeList = new ArrayList<>();
        if (ObjectUtils.isEmpty(categoryList)) {
            return customerCategoryTreeList;
        }

        List<CustomerCategoryEntity> hasTreeList = new ArrayList<>();

        customerCategoryTreeList = getCategoryTree(ColaConstant.TREE_ROOT_ID, categoryList, hasTreeList);

        GoOn:
        for (CustomerCategoryEntity category : categoryList) {
            for (CustomerCategoryEntity categoryTree : hasTreeList) {
                if (Objects.equals(categoryTree.getId(),category.getId())) {
                    continue GoOn;
                }
            }
            CustomerCategoryTree customerCategoryTree = getCategoryTree(category);
            customerCategoryTree.setChildren(getCategoryTree(category.getId(), categoryList, hasTreeList));
            customerCategoryTreeList.add(customerCategoryTree);
        }


        return customerCategoryTreeList;
    }

    private static List<CustomerCategoryTree> getCategoryTree(Long parentId, List<CustomerCategoryEntity> categoryList, List<CustomerCategoryEntity> hasTreeList) {
        List<CustomerCategoryTree> customerCategoryTreeList = new ArrayList<>();
        if (ObjectUtils.isEmpty(categoryList)) {
            return customerCategoryTreeList;
        }

        for (CustomerCategoryEntity category : categoryList) {
            if (Objects.equals(category.getParentId(),parentId)) {
                hasTreeList.add(category);
                CustomerCategoryTree customerCategoryTree = getCategoryTree(category);
                customerCategoryTree.setChildren(getCategoryTree(category.getId(), categoryList, hasTreeList));
                customerCategoryTreeList.add(customerCategoryTree);
            }
        }
        return customerCategoryTreeList;
    }

    private static CustomerCategoryTree getCategoryTree(CustomerCategoryEntity customerCategoryEntity) {
        CustomerCategoryTree customerCategoryTree = new CustomerCategoryTree();
        BeanUtil.copyProperties(customerCategoryEntity, customerCategoryTree);
        return customerCategoryTree;
    }
}
