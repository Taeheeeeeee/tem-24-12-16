package com.ll.tem.domain.post.post.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
@Validated
public class PostController {

    private String getFormHtml(String errorMessage, String title, String content) {
        return """
                <div>%s</div>
                <form method="POST">
                    <input type="text" name="title" placeholder="제목" value="%s">
                    <textarea name="content" placeholder="내용">%s</textarea>
                    <button type="submit">글쓰기</button>
                </form>
                """.formatted(errorMessage, title, content);
    }

    @GetMapping("/write")
    @ResponseBody
    public String showWrite() {
        return getFormHtml("", "", "");
    }

    @PostMapping("/write")
    @ResponseBody
    public String write(
            @RequestParam("title")
            @NotBlank(message = "제목을 입력해주세요")
            @Length(min = 5, message = "제목을 5자 이상 입력해주세요")
            String title
            , @RequestParam("content")
            @NotBlank(message = "내용을 입력해주세요")
            @Length(min = 10, message = "내용을 10자 이상 입력해주세요")
            String content
    ) {
        return """
                <h1>글쓰기 완료</h1>
                
                <div>
                    <h2>%s</h2>
                    <p>%s</p>
                </div>
                """.formatted(title, content);
    }
}