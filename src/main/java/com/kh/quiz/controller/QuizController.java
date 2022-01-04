package com.kh.quiz.controller;

import com.kh.quiz.entity.Quiz;
import com.kh.quiz.form.QuizForm;
import com.kh.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService service;

    @ModelAttribute
    public QuizForm setUpForm() {
        QuizForm form = new QuizForm();
        form.setAnswer(true);   //라디오버튼 기본값 설정
        return form;
    }

    @RequestMapping("/")
    public String main() { return "index";}

    @GetMapping     //주소미지정시 RequestMapping의 경로(상위경로)를 따름
    public String showList(QuizForm quizForm, Model model) {
        quizForm.setNewQuiz(true);
        Iterable<Quiz> list = service.selectAll();

        model.addAttribute("list", list);
        model.addAttribute("title", "등록 입력양식");
        return "crud";
    }

    @PostMapping("/insert")
    public String insert(@Validated QuizForm quizForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        Quiz quiz = new Quiz();
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());

        if(!bindingResult.hasErrors()) {
            service.insertQuiz(quiz);
            redirectAttributes.addFlashAttribute("complete", "등록이 완료되었습니다.");
            return "redirect:/quiz";
        } else {
            return showList(quizForm, model);
        }
    }

    @GetMapping("/{id}")
    public String showUpdate(QuizForm quizForm, @PathVariable Integer id, Model model) {
        Optional<Quiz> quizOptional = service.selectOneById(id);
        Optional<QuizForm> quizFormOptional = quizOptional.map(t -> makeQuizForm(t));

        if(quizFormOptional.isPresent())
            quizForm = quizFormOptional.get();

        makeUpdateModel(quizForm, model);
        return "crud";
    }

    private void makeUpdateModel(QuizForm quizForm, Model model) {
        model.addAttribute("id", quizForm.getId());
        quizForm.setNewQuiz(false);
        model.addAttribute("quizForm", quizForm);
        model.addAttribute("title", "변경 입력양식");
    }

    @PostMapping("/update")
    public String update(@Validated QuizForm quizForm, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Quiz quiz = makeQuiz(quizForm);
        if(!result.hasErrors()) {
            service.updateQuiz(quiz);
            redirectAttributes.addFlashAttribute("complete", "변경이 완료되었습니다.");
        }
        makeUpdateModel(quizForm, model);
        return "crud";
    }

    private Quiz makeQuiz(QuizForm quizForm) {
        Quiz quiz = new Quiz();
        quiz.setId(quizForm.getId());
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());

        return quiz;
    }

    private QuizForm makeQuizForm(Quiz quiz) {
        QuizForm form = new QuizForm();
        form.setId(quiz.getId());
        form.setQuestion(quiz.getQuestion());
        form.setAnswer(quiz.getAnswer());
        form.setAuthor(quiz.getAuthor());
        form.setNewQuiz(false);

        return form;
    }


    public String delete(@RequestParam("id") String id, Model model, RedirectAttributes redirectAttributes) {
        service.deleteQuizById(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("delcomplete", "삭제완료.");
        return "redirect:/quiz";
    }


}
