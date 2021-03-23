package com.project.Backend.Repositories;

import com.project.Backend.Entities.LoginSession;
import com.project.Backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing sessions
 */
@Repository
public interface SessionRepository extends JpaRepository<LoginSession, Integer> {

    @Query("SELECT t FROM LoginSession t where t.token = :token")
    LoginSession findByToken(@Param("token") String token);

    @Query("SELECT t FROM LoginSession t where t.user = :user")
    LoginSession findByUserId(@Param ("user") User user);

}
