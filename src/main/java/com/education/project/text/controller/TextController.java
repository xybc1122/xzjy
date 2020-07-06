package com.education.project.text.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.education.project.base.HttpResult;
import com.education.project.base.StatusCode;
import com.education.project.text.entity.Text;
import com.education.project.text.service.ITextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/v1/api/text")
public class TextController {
    private final ITextService textService;

    @Autowired
    public TextController(ITextService textService) {
        this.textService = textService;
    }

    @GetMapping("getTextContent")
    public HttpResult<String> getTextContent() {
        List<Text> list = textService.list();
        Text text = list.get(0);
        return HttpResult.success(text.getTextContent(), "success");
    }


    @PostMapping("editTextContent")
    public HttpResult editTextContent(@RequestBody Text text) {
        UpdateWrapper<Text> wrapper = new UpdateWrapper<>();
        wrapper.set("text_content", text.getTextContent());
        if (textService.update(wrapper)) {
            return HttpResult.success();
        }
        return HttpResult.fail(StatusCode.FAILURE);
    }


}
