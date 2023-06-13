package com.matrix.cola.system.notice.service.impl;

import com.matrix.cola.common.service.AbstractColaEntityWrapperService;
import com.matrix.cola.system.notice.entity.NoticeEntity;
import com.matrix.cola.system.notice.entity.NoticeEntityWrapper;
import com.matrix.cola.system.notice.service.NoticeService;
import com.matrix.cola.system.notice.service.NoticeWrapperService;
import org.springframework.stereotype.Service;

/**
 * 通知公告包装类接口实现类
 *
 * @author : cui_feng
 * @since : 2022-06-29 11:05
 */
@Service
public class NoticeWrapperServiceImpl extends AbstractColaEntityWrapperService<NoticeEntity, NoticeEntityWrapper, NoticeService> implements NoticeWrapperService {

}
