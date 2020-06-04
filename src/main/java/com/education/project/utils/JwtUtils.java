package com.education.project.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.education.project.exception.LsException;
import com.education.project.user.entity.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 */
public class JwtUtils {

    private static final String SUBJECT = "jwt_token";

    private static final String SERVICE = "jwt_server";


    private static final String APP_SECRET = "xzjy_app";

    private static User user = new User();

    public static String genJsonWebToken(User user) {
        if (user == null || user.getStudentId() == null || StringUtils.isBlank(user.getName())) {
            throw new NullPointerException("--设置token失败");
        }
        Algorithm algorithm = Algorithm.HMAC256(APP_SECRET);
        //头部信息
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        Date nowDate = new Date();
        // Date expireDate = getAfterDate(nowDate, 0, 0, 0, 2, 0, 0);//2小过期
        /*设置头部信息 Header*/
        return JWT.create().withHeader(map)
                .withClaim("studentId", user.getStudentId())
                .withClaim("name", user.getName())
                .withClaim("gradeId", user.getGradeId())
                .withIssuer(SERVICE)//签名是有谁生成 例如 服务器
                .withSubject(SUBJECT)//签名的主题
                .withIssuedAt(nowDate) //生成签名的时间
                /*签名 Signature */
                .sign(algorithm);
    }

    private static DecodedJWT checkJWT(final String token) {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(APP_SECRET)).withIssuer(SERVICE).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            throw new LsException("token 授权失败");
        }
        return jwt;
    }

    public static void setJwtUser(HttpServletRequest request, String token) {
        DecodedJWT decodedJWT = checkJWT(token);
        String studentId = decodedJWT.getClaim("studentId").asString();
        String name = decodedJWT.getClaim("name").asString();
        Integer gradeId = decodedJWT.getClaim("gradeId").asInt();
        request.setAttribute("studentId", studentId);
        request.setAttribute("name", name);
        request.setAttribute("gradeId", gradeId);
    }

}