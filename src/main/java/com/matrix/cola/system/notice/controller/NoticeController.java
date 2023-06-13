package com.matrix.cola.system.notice.controller;

import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.system.notice.entity.NoticeEntity;
import com.matrix.cola.system.notice.service.NoticeService;
import com.matrix.cola.system.notice.service.NoticeWrapperService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知公告Controller
 *
 * @author : cui_feng
 * @since : 2022-06-29 11:12
 */
@RestController
@RequestMapping("/system/notice")
@AllArgsConstructor
public class NoticeController {

    NoticeWrapperService noticeWrapper;

    NoticeService noticeService;

    @PostMapping("/getNoticePage")
    public Result getNoticePage(@RequestBody Query<NoticeEntity> query) {
        return Result.page(noticeWrapper.getWrapperPage(query));
    }

    @PostMapping("/addNotice")
    public Result addNotice(@RequestBody NoticeEntity noticePO) {
        return noticeService.insert(noticePO);
    }

    @PostMapping("/updateNotice")
    public Result updateNotice(@RequestBody NoticeEntity noticePO) {
        return noticeService.modify(noticePO);
    }

    @PostMapping("/deleteNotice")
    public Result deleteNotice(@RequestBody NoticeEntity noticePO) {
        noticeService.remove(noticePO.getId());
        return noticeService.remove(noticePO);
    }
}
