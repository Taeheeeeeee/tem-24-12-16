package com.ll.tem.domain.post.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {
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
        return """
                <h1>글쓰기 완료</h1>
                
                <div>
                    <h2>%s</h2>
                    <p>%s</p>
                </div>
                """.formatted(title, content);
    }
}