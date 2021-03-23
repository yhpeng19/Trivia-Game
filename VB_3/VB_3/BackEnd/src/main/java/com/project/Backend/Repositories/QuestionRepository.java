package com.project.Backend.Repositories;

import com.project.Backend.Entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Questions table
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    void deleteById(int id);

   Question findById(int id);

   @Query("SELECT t FROM Question t where t.category = :category")
   List<Question> findByCategory(@Param("category") String category);


    Question save(Question q);

}
