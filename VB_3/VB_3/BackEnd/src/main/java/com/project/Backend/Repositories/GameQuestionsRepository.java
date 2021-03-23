package com.project.Backend.Repositories;

import com.project.Backend.Entities.GameQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing what questions are related to what game
 */
@Repository
public interface GameQuestionsRepository extends JpaRepository<GameQuestions, Integer> {
}
