package com.project.Backend.Repositories;


import com.project.Backend.Entities.ProposedQuestion;
import com.project.Backend.Entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing questions proposed by players
 */
@Repository
public interface ProposedQuestionRepository extends JpaRepository<ProposedQuestion, Integer> {
    ProposedQuestion save(ProposedQuestion q);

    @Query("SELECT count(t) FROM ProposedQuestion t")
    int numPending();

    ProposedQuestion findById(int id);
}
