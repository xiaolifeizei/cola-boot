package com.matrix.cola.system.datascope.controller;

import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.system.datascope.entity.DataScopeEntity;
import com.matrix.cola.system.datascope.service.DataScopeService;
import com.matrix.cola.system.datascope.service.DataScopeWrapperService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据权限Controller
 *
 * @author : cui_feng
 * @since : 2022-06-06 11:30
 */
@RestController
@RequestMapping("/system/datascope")
@AllArgsConstructor
public class DataScopeController {

    DataScopeService dataScopeService;

    DataScopeWrapperService dataScopeWrapper;

    @PostMapping("/getDataScopeList")
    @PreAuthorize("hasAuthority('administrator')")
    public Result getDataScopeList(@RequestBody Query<DataScopeEntity> query) {
        return Result.list(dataScopeWrapper.getWrapperList(query));
    }

    @PostMapping("/addDataScope")
    @PreAuthorize("hasAuthority('administrator')")
    public Result addDataScope(@RequestBody DataScopeEntity dataScopePO) {
        return dataScopeService.insert(dataScopePO);
    }

    @PostMapping("/updateDataScope")
    @PreAuthorize("hasAuthority('administrator')")
    public Result updateDataScope(@RequestBody DataScopeEntity dataScopePO) {
        return dataScopeService.modify(dataScopePO);
    }

    @PostMapping("/deleteDataScope")
    @PreAuthorize("hasAuthority('administrator')")
    public Result deleteDataScope(@RequestBody DataScopeEntity dataScopePO) {
        return dataScopeService.deleteDataScope(dataScopePO);
    }
}
