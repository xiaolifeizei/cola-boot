package com.matrix.cola.common.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.matrix.cola.common.cache.CacheProxy;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * 自动配置
 *
 * @author : cui_feng
 * @since : 2022-06-13 15:21
 */
@Configuration
public class AutoConfiguration {


    @Bean
    public CacheProxy cacheProxy() {
        return new CacheProxy();
    }

    /**
     * 配置Json解析时区和日期时间格式
     * @return 自定义JacksonObjectMapperBuilder
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {

        final String dateFormat = "yyyy-MM-dd";
        final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(dateTimeFormat);

        return builder -> {
            builder.dateFormat(format);
            builder.simpleDateFormat(dateTimeFormat);
            builder.timeZone(TimeZone.getDefault());
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }
}
