package com.matrix.cola.business.customer.controller;

import com.matrix.cola.business.customer.entity.CustomerEntity;
import com.matrix.cola.business.customer.service.CustomerService;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户管理controller
 *
 * @author : cui_feng
 * @since : 2022-06-28 11:59
 */
@RestController
@RequestMapping("/basics/customer")
@AllArgsConstructor
public class CustomerController {

    CustomerService customerService;

    @PostMapping("/getCategoryCustomerPage")
    public Result getCategoryGoodsPage(@RequestBody Query<CustomerEntity> query) {
        return customerService.getCategoryCustomerPage(query);
    }

    @PostMapping("/addCustomer")
    public Result addCustomer(@RequestBody CustomerEntity customerPO) {
        return customerService.insert(customerPO);
    }

    @PostMapping("/updateCustomer")
    public Result updateCustomer(@RequestBody CustomerEntity customerPO) {
        return customerService.modify(customerPO);
    }

    @PostMapping("/deleteCustomer")
    public Result deleteCustomer(@RequestBody CustomerEntity customerPO) {
        return customerService.remove(customerPO);
    }
}
