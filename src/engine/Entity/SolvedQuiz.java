package engine.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SolvedQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", referencedColumnName = "ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Quiz quiz;

    private LocalDateTime completedAt;

    public int getQuizId() {
        return this.quiz.getId();
    }

    public SolvedQuiz(User user, Quiz quiz) {
        this.user = user;
        this.quiz = quiz;
        this.completedAt = LocalDateTime.now();
    }

    public SolvedQuiz() {
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

}
