package com.education.project.interceoter;

import com.education.project.base.HttpResult;
import com.education.project.user.entity.User;
import com.education.project.utils.CookieUtil;
import com.education.project.utils.JwtUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 监听拦截器
 */
public class InterCenter implements HandlerInterceptor {
    private Gson gson = new Gson();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        String token = CookieUtil.getValue(request, "token");
        if (token == null) {
            //校验token
            token = request.getHeader("token");
            if (token == null) {
                //尝试去参数里面获取看看
                token = request.getParameter("token");
            }
        }
        if (StringUtils.isNotBlank(token)) {
            JwtUtils.setJwtUser(request, token);
            return true;
        }
        if (request.getRequestURI().equals("/admin")) {
            response.sendRedirect("/login");
            return false;
        }
        sendJsonMessage(response, HttpResult.fail("请先登录"));
        return false;
    }

    public void sendJsonMessage(HttpServletResponse response, Object obj) {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.print(gson.toJson(obj));
            writer.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}



