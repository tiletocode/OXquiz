package com.kh.quiz;

import com.kh.quiz.entity.Quiz;
import com.kh.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class QuizApplication {

    public static void main(String[] args) {

        SpringApplication.run(QuizApplication.class, args).getBean(QuizApplication.class).execute();
    }

    @Autowired
    QuizRepository repository;

    private void execute() {
//        showOne();
//        updateQuiz();
        updateQuiz();
    }

    private void setup() {
        Quiz quiz1 = new Quiz(null, "Integer는 원시 자료형입니까?", false, "tiletocode");
        quiz1 = repository.save(quiz1);
        System.out.println("등록된 데이터: "+quiz1);

        Quiz quiz2 = new Quiz(null, "Javascript는 Java와 연관성이 있습니까?", false, "tiletocode");
        quiz2 = repository.save(quiz2);
        System.out.println("등록된 데이터: "+quiz2);

        Quiz quiz3 = new Quiz(null, "람다식은 Java 8버전부터 지원을 시작했습니까?", true, "tiletocode");
        quiz3 = repository.save(quiz3);
        System.out.println("등록된 데이터: "+quiz3);
    }
    private void showList() {
        System.out.println("***** 전체 검색 시작 *****");
        Iterable<Quiz> quizzes = repository.findAll();
        for (Quiz quiz : quizzes) {
            System.out.println(quiz);
        }
        System.out.println("***** 전체 검색 종료 *****");
    }

    private void showOne() {
        System.out.println("***** 1건 데이터 얻기 시작 *****");
        Optional<Quiz> quizOptional = repository.findById(1);   //Optional : null이 들어올수도 있는값

        if(quizOptional.isPresent()) {
            System.out.println(quizOptional.get());
        } else {
            System.out.println("해당 퀴즈가 없습니다.");
        }
        System.out.println("***** 1건 데이터 얻기 완료 *****");
    }

    private void updateQuiz() {
        System.out.println("***** 업데이트 처리 시작 *****");
        Quiz quiz1 = new Quiz(1, "Integer는 원시 자료형입니까?", false, "tiletocode");
        quiz1 = repository.save(quiz1);
        System.out.println("업데이트된 데이터: "+quiz1);
        System.out.println("***** 업데이트 처리 완료 *****");
    }
    
    private  void deleteQuiz() {
        System.out.println("***** 삭제 처리 시작 *****");
        repository.deleteById(6);
        System.out.println("***** 삭제 처리 완료 *****");
    }
}
