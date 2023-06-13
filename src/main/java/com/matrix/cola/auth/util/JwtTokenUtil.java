package com.matrix.cola.auth.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.matrix.cola.common.utils.SecurityConst;
import com.matrix.cola.system.user.entity.UserEntity;

import java.util.HashMap;

/**
 * 创建和验证Jwt
 *
 * @author : cui_feng
 * @since : 2022-04-21 10:30
 */
public class JwtTokenUtil {

    /**
     * 多长时间超时，单位是分钟
     */
    private static final Integer EXPIRATION = 3 * 60;

    /**
     * 生成jwtToken
     * @param userPO 用户
     * @param expMinute 超时时间
     * @return JWT字符串
     */
    private static String createToken(UserEntity userPO, Integer expMinute) {

        if (ObjectUtil.isNull(userPO)) return null;
        if (ObjectUtil.isNull(expMinute) || expMinute <= 0) {
            expMinute = EXPIRATION;
        }

        // 计算过期时间
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.MINUTE, expMinute);

        HashMap<String,Object> payload = new HashMap<>();
        payload.put(JWTPayload.ISSUED_AT, now.getTime()); //签发时间
        payload.put(JWTPayload.EXPIRES_AT, expTime.getTime()); //过期时间
        payload.put(JWTPayload.NOT_BEFORE, now.getTime()); //生效时间

        payload.put("id",userPO.getId() == null ? "" : userPO.getId().toString());
        payload.put("name",StrUtil.emptyToDefault(userPO.getName(),""));
        payload.put("loginName",StrUtil.emptyToDefault(userPO.getLoginName(),""));
        payload.put("groupId",userPO.getGroupId());

        return JWTUtil.createToken(payload, SecurityConst.JWT_KEY.getBytes());
    }

    /**
     * 创建Token
     * @param userPO 用户
     * @return JWT字符串
     */
    public static String createToken(UserEntity userPO){
        return createToken(userPO,EXPIRATION);
    }
}
