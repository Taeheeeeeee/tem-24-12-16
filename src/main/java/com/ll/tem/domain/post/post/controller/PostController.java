package com.ll.tem.domain.post.post.controller;

import com.ll.tem.domain.post.post.entity.Post;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostController {
    private List<Post> posts = new ArrayList<>() {{
        add(
                Post.builder()
                        .title("글1")
                        .content("글1")
                        .build()
        );
        add(
                Post.builder()
                        .title("글2")
                        .content("글2")
                        .build()
        );
        add(
                Post.builder()
                        .title("글3")
                        .content("글3")
                        .build()
        );
    }};

    private String getFormHtml(String errorMessage, String title, String content) {
        return """
                <div>%s</div>
                <form method="POST">
                    <input type="text" name="title" placeholder="제목" value="%s">
                    <br>
                    <textarea name="content" placeholder="내용">%s</textarea>
                    <br>
                    <button type="submit">글쓰기</button>
                </form>
                """.formatted(errorMessage, title, content);
    }

    @GetMapping
    @ResponseBody
    public String showList() {
        String ul = "<ul>" + posts
                .reversed()
                .stream()
                .map(post -> "<li>%s</li>".formatted(post.getTitle()))
                .collect(Collectors.joining()) + "</ul>";

        String body = """
                <h2>글 목록</h2>
                %s
                <a href="/posts/write">글쓰기</a>
                """.formatted(ul);
        return body;
    }

    private record PostWriteForm(
            @NotBlank(message = "01-제목을 입력해주세요.")
            @Length(min = 2, message = "02-제목을 2자 이상 입력해주세요.")
            String title,
            @NotBlank(message = "03-내용을 입력해주세요.")
            @Length(min = 2, message = "04-내용을 2자 이상 입력해주세요.")
            String content
    ){ }

    @GetMapping("/write")
    public String showWrite(PostWriteForm form) {
        return "domain/post/post/write";
    }

    @PostMapping("/write")
    public String write(
            @Valid PostWriteForm form
            , BindingResult bindingResult
            , Model model
    ) {
        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .sorted()
                    .map(message -> message.split("-", 2)[1])
                    .collect(Collectors.joining("<br>"));

            model.addAttribute("errorMessage", errorMessage);

            return "domain/post/post/write";
        }

        posts.add(Post.builder()
                .title(form.title)
                .content(form.content)
                .build());

        return "redirect:/posts";
    }
}