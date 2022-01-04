package com.kh.quiz;

import com.kh.quiz.entity.Quiz;
import com.kh.quiz.repository.QuizRepository;
import com.kh.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class QuizApplication {

    public static void main(String[] args) {

        SpringApplication.run(QuizApplication.class, args);
    }

    @Autowired
    QuizService service;

    /*************
    로직 테스트부분
    *************/

    private void execute() {
        //setup();
        //showList();
        //showOne();
        //updateQuiz();
        //deleteQuiz();
        runQuiz();
    }

    private void setup() {
        System.out.println("***** 등록 시작 *****");
        Quiz quiz1 = new Quiz(null, "Integer는 원시 자료형입니까?", false, "tiletocode");
        Quiz quiz2 = new Quiz(null, "Javascript는 Java와 연관성이 있습니까?", false, "tiletocode");
        Quiz quiz3 = new Quiz(null, "람다식은 Java 8버전부터 지원을 시작했습니까?", true, "tiletocode");
        List<Quiz> quizList = new ArrayList<>();
        Collections.addAll(quizList, quiz1, quiz2, quiz3);

        for(Quiz quiz : quizList)
            service.insertQuiz(quiz);
        System.out.println("***** 등록 종료 *****");
    }
    private void showList() {
        System.out.println("***** 전체 검색 시작 *****");
        Iterable<Quiz> quizzes = service.selectAll();
        for (Quiz quiz : quizzes) {
            System.out.println(quiz);
        }
        System.out.println("***** 전체 검색 종료 *****");
    }

    private void showOne() {
        System.out.println("***** 1건 데이터 얻기 시작 *****");
        Optional<Quiz> quizOptional = service.selectOneById(1);   //Optional : null이 들어올수도 있는값

        if(quizOptional.isPresent()) {
            System.out.println(quizOptional.get());
        } else {
            System.out.println("해당 퀴즈가 없습니다.");
        }
        System.out.println("***** 1건 데이터 얻기 완료 *****");
    }

    private void updateQuiz() {
        System.out.println("***** 업데이트 처리 시작 *****");
        Quiz quiz1 = new Quiz(1, "int는 원시 자료형입니까?", true, "tiletocode");
        service.updateQuiz(quiz1);
        System.out.println("업데이트된 데이터: "+quiz1);
        System.out.println("***** 업데이트 처리 완료 *****");
    }
    
    private  void deleteQuiz() {
        System.out.println("***** 삭제 처리 시작 *****");
        service.deleteQuizById(2);
        System.out.println("***** 삭제 처리 완료 *****");
    }

    private void runQuiz() {
        System.out.println("***** 퀴즈 1건 얻기 시작 *****");
        Optional<Quiz> quizOptional = service.selectOneRandomQuiz();

        if(quizOptional.isPresent())
            System.out.println(quizOptional.get());
        else
            System.out.println("해당하는 퀴즈가 없습니다.");
        System.out.println("***** 퀴즈 1건 얻기 완료 *****");

        Boolean myAnswer = false;
        Integer id = quizOptional.get().getId();
        if(service.checkQuiz(id, myAnswer))
            System.out.println("정답!");
        else
            System.out.println("오답!");
    }
}
