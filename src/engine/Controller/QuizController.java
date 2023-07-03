package engine.Controller;

import engine.Dto.QuizDto;
import engine.Dto.SolveDto;
import engine.Dto.SolvedQuizDto;
import engine.Entity.Quiz;
import engine.Dto.Response;
import engine.Service.QuizService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("quizzes")
    public ResponseEntity<Page<Quiz>> getAllQuizzes(@RequestParam(name = "page") int page){
        return quizService.getAllQuizzes(page);
    }

    @GetMapping("quizzes/completed")
    public ResponseEntity<Page<SolvedQuizDto>> getAllSolvedQuizzes(@RequestParam(name = "page") int page){
        return quizService.getAllSolvedQuizzes(page);
    }

    @PostMapping("quizzes")
    public ResponseEntity<Quiz>createQuiz(@RequestBody @Valid QuizDto dto) {
        return quizService.createQuiz(dto);
    }

    @GetMapping("quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable int id){
        return quizService.getQuiz(id);
    }

    @DeleteMapping("quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable int id) {return quizService.deleteQuiz(id);}

    @PostMapping("quizzes/{id}/solve")
    public ResponseEntity<Response> checkAnswer(@PathVariable int id,
                                                @RequestBody SolveDto dto) {
        return quizService.checkAnswer(id, dto.answer());
    }
}
