package com.kh.quiz.service;

import com.kh.quiz.entity.Quiz;
import com.kh.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    QuizRepository repository;

    @Override
    public Iterable<Quiz> selectAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Quiz> selectOneById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Quiz> selectOneRandomQuiz() {
        Integer randomId = repository.getRandomId();
        if(randomId == null) {
            return Optional.empty();
        }
        return repository.findById(randomId);
    }

    @Override
    public Boolean checkQuiz(Integer id, Boolean myAnswer) {
        boolean check = false;
        Optional<Quiz> optQuiz = repository.findById(id);
        if(optQuiz.isPresent()) {
            Quiz quiz = optQuiz.get();
            if(quiz.getAnswer().equals(myAnswer))
                check = true;
        }
        return check;
    }

    @Override
    public void insertQuiz(Quiz quiz) {
        repository.save(quiz);
    }

    @Override
    public void updateQuiz(Quiz quiz) {
        repository.save(quiz);
    }

    @Override
    public void deleteQuizById(Integer id) {
        repository.deleteById(id);
    }
}
