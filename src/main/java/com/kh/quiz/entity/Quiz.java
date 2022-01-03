package com.kh.quiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    private Integer id;
    private String question;
    private Boolean answer;
    private String author;
}
