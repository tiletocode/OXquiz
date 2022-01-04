package com.kh.quiz.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizForm {
    private Integer id;
    @NotBlank
    private String question;
    private Boolean answer;
    @NotBlank
    private String author;
    private Boolean newQuiz;    //등록or변경 판정용
}
