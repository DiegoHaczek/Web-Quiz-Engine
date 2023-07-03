package engine.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import engine.Dto.QuizDto;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "QUIZ")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "TEXT")
    private String text;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<String> options;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> answer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User user;


    public Quiz(QuizDto dto,User user){
        this.title = dto.title();
        this.text = dto.text();
        this.options = dto.options();
        this.answer = dto.answer();
        this.user = user;
    }

    public Quiz() {
    }

    @JsonIgnore
    public int getUserId(){
        return this.user.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }
}
