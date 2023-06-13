package com.matrix.cola.common.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author cui_feng
 * @since : 2022-04-20 14:18
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ColaLog {
    String value() default "";
}
