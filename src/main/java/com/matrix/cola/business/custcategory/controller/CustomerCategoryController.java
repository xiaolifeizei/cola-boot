package com.matrix.cola.business.custcategory.controller;

import com.matrix.cola.business.custcategory.entity.CustomerCategoryEntity;
import com.matrix.cola.business.custcategory.service.CustomerCategoryDetailService;
import com.matrix.cola.business.custcategory.service.CustomerCategoryService;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户分类Controller
 *
 * @author : cui_feng
 * @since : 2022-06-28 11:59
 */
@RestController
@RequestMapping("/basics/customer/category")
@AllArgsConstructor
public class CustomerCategoryController {


    CustomerCategoryService customerCategoryService;

    CustomerCategoryDetailService customerCategoryDetailService;

    @PostMapping("/getCategoryTree")
    public Result getCategoryTree(@RequestBody Query<CustomerCategoryEntity> query) {
        return customerCategoryService.getCategoryTree(query);
    }

    @PostMapping("/getCategoryByCustomerId")
    public Result getCategoryByCustomerId(Long customerId) {
        return customerCategoryDetailService.getCategoryByCustomerId(customerId);
    }

    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody CustomerCategoryEntity customerCategoryPO) {
        return customerCategoryService.insert(customerCategoryPO);
    }

    @PostMapping("/updateCategory")
    public Result updateCategory(@RequestBody CustomerCategoryEntity customerCategoryPO) {
        return customerCategoryService.modify(customerCategoryPO);
    }

    @PostMapping("/deleteCategory")
    public Result deleteCategory(@RequestBody CustomerCategoryEntity customerCategoryPO) {
        return customerCategoryService.remove(customerCategoryPO);
    }
}
