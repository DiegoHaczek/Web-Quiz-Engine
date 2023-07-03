package engine.Service;

import engine.Dto.QuizDto;
import engine.Dto.SolvedQuizDto;
import engine.Entity.Quiz;
import engine.Dto.Response;
import engine.Entity.SolvedQuiz;
import engine.Entity.User;
import engine.Repository.QuizRepository;
import engine.Repository.SolvedQuizRepository;
import engine.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    public QuizService(QuizRepository quizRepository,
                       UserRepository userRepository,
                       SolvedQuizRepository solvedQuizRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.solvedQuizRepository = solvedQuizRepository;
    }

    public ResponseEntity<Quiz> getQuiz(int id) {
        return ResponseEntity.ok(quizRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found"))
        );
    }

    public ResponseEntity<Response> checkAnswer(int id, List<Integer> answer) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found"));

        if (answer.equals(quiz.getAnswer())) {
            SolvedQuiz solved = new SolvedQuiz(getCurrentUser(),quiz);
            solvedQuizRepository.save(solved);
            return ResponseEntity.ok(new Response(true,"Congratulations, you're right!"));
        }

        return ResponseEntity.ok(new Response(false,"Wrong answer! Please, try again."));
    }

    public ResponseEntity<Quiz> createQuiz(QuizDto dto) {
        Quiz quiz = new Quiz(dto,getCurrentUser());
        userRepository.save(getCurrentUser());
        quizRepository.save(quiz);
        return ResponseEntity.ok(quiz);
    }

    public ResponseEntity<Page<Quiz>> getAllQuizzes(int page) {
        Pageable pageable = PageRequest.of(page,10);
        return ResponseEntity.ok(quizRepository.findAll(pageable));
    }

    public ResponseEntity<Page<SolvedQuizDto>> getAllSolvedQuizzes(int page) {
        User user = getCurrentUser();
        Pageable pageable = PageRequest.of(page,10,Sort.Direction.DESC,
                "completed_At");

        Page<SolvedQuizDto> response = solvedQuizRepository
                .findByUser_id(user.getId(), pageable)
                .map(SolvedQuizDto::new);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteQuiz(int id) {
        quizRepository.findById(id).ifPresentOrElse(this::checkIfIsQuizOwner,
                this::throwNotFoundException);
        
        quizRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void checkIfIsQuizOwner(Quiz quiz) {
        if (quiz.getUserId() != getCurrentUser().getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "");
        }
    }

    private void throwNotFoundException(){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"");
    }

    private static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
