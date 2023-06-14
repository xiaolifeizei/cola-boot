package com.matrix.cola.business.goodspec.controller;

import com.matrix.cola.business.goodspec.entity.SpecEntity;
import com.matrix.cola.business.goodspec.service.SpecService;
import com.matrix.cola.business.goodspec.service.SpecWrapperService;
import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 规格管理Controller
 *
 * @author : cui_feng
 * @since : 2022-06-21 12:26
 */
@RestController
@RequestMapping("/basics/goods/spec")
@AllArgsConstructor
public class SpecController {

    SpecWrapperService specWrapper;

    SpecService specService;

    @PostMapping("/getSpecPage")
    public Result getSpecPage(@RequestBody Query<SpecEntity> query) {
        return Result.page(specWrapper.getWrapperPage(query));
    }

    @PostMapping("/addSpec")
    public Result addSpec(@RequestBody SpecEntity specPO) {
        return specService.insert(specPO);
    }

    @PostMapping("/updateSpec")
    public Result updateSpec(@RequestBody SpecEntity specPO) {
        return specService.modify(specPO);
    }

    @PostMapping("/deleteSpec")
    public Result deleteSpec(@RequestBody SpecEntity specPO) {
        return specService.remove(specPO);
    }
}
