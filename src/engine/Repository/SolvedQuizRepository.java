package engine.Repository;

import engine.Entity.SolvedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SolvedQuizRepository extends PagingAndSortingRepository<SolvedQuiz,Integer> {

   // Page<SolvedQuiz> findByUserId(int userId, Pageable pageable);
    @Query(value = "SELECT * FROM solved_quiz WHERE user_id = :userId",
            countQuery = "SELECT COUNT(*) FROM solved_quiz WHERE user_id = :userId",
            nativeQuery = true)
    Page<SolvedQuiz> findByUser_id(@Param("userId") int userId, Pageable pageable);
}
