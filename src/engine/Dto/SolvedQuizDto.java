package engine.Dto;

import engine.Entity.SolvedQuiz;

import java.time.LocalDateTime;

public record SolvedQuizDto (int id, LocalDateTime completedAt){
    public SolvedQuizDto(SolvedQuiz solved){
        this(solved.getQuizId(), solved.getCompletedAt());
    }
}
