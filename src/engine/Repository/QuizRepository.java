package engine.Repository;

import engine.Entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer>,
        PagingAndSortingRepository<Quiz,Integer> {

    Page<Quiz> findAll(Pageable pageable);
}
