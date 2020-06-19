package com.education.project.utils;

import com.alibaba.excel.EasyExcel;
import com.education.project.base.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
public class EasyexcelUtil {


    public static void write(HttpServletResponse response, Class head, String fileName, String sheetName, List<?> data) throws IOException {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), head).autoCloseStream(Boolean.FALSE)
                    .sheet(sheetName).doWrite(data);
        }catch (IOException e ){
            errorWrite(response,e);
        }
    }


    /**
     * 导出错误
     *
     * @param response
     * @param e
     * @throws IOException
     */
    private static void errorWrite(HttpServletResponse response, Exception e) throws IOException {
        // 重置response
        response.reset();
        log.error(e.getMessage(), e);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().println(HttpResult.fail("导出失败"));
    }


}
