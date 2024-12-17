package com.ll.tem.domain.post.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
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
        return """
                <form method="POST">
                    <input type="text" name="title" placeholder="제목">
                    <textarea name="content" placeholder="내용"></textarea>
                    <button type="submit">글쓰기</button>
                </form>
                """;
    }

    @PostMapping("/write")
    @ResponseBody
    public String write(
            @RequestParam("title") String title,
            @RequestParam("content") String content
    ) {
        if (title.length() < 5) {
            return getFormHtml("제목을 5자 이상 입력해주세요", title, content);
        }

        if (title == null || title.isBlank()) {
            return getFormHtml("제목을 입력해주세요", title, content);
        }

        if (content.length() < 10) {
            return getFormHtml("내용을 10자 이상 입력해주세요", title, content);
        }

        if (content == null || content.isBlank()) {
            return getFormHtml("내용을 입력하세요", title, content);
        }

        return """
                <h1>글쓰기 완료</h1>
                
                <div>
                    <h2>%s</h2>
                    <p>%s</p>
                </div>
                """.formatted(title, content);
    }
}