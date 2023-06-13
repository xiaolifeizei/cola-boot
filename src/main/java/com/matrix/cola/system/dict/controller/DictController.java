package com.matrix.cola.system.dict.controller;

import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.system.dict.entity.DictEntity;
import com.matrix.cola.system.dict.service.DictService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典管理Controller
 *
 * @author : cui_feng
 * @since : 2022-05-18 12:57
 */
@RestController
@RequestMapping("/system/dict")
@AllArgsConstructor
public class DictController {

    DictService dictService;

    @PostMapping("/getDictList")
    public Result getDictList(@RequestBody Query<DictEntity> query) {
        return Result.list(dictService.getList(query));
    }

    @PostMapping("/getDictTree")
    public Result getDictTree(@RequestBody Query<DictEntity> query) {
        return dictService.getDictTree(query);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @PostMapping("/deleteDict")
    public Result deleteDict(@RequestBody DictEntity dictPO) {
        return dictService.deleteDict(dictPO);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @PostMapping("/updateDict")
    public Result updateDict(@RequestBody DictEntity dictPO) {
        return dictService.updateDict(dictPO);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @PostMapping("/addDict")
    public Result addDict(@RequestBody DictEntity dictPO) {
        return dictService.insert(dictPO);
    }
}
