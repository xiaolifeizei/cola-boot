package com.matrix.cola.auth.util;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrix.cola.common.Result;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 返回格式统一处理
 *
 * @author : cui_feng
 * @since : 2022-04-21 16:02
 */
public class ResponseUtil {

    public static void out (HttpServletResponse response, Result result) {

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.HTTP_OK);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            objectMapper.writeValue(response.getWriter(),result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
