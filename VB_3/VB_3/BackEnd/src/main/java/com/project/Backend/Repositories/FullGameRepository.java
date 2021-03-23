package com.project.Backend.Repositories;

import com.project.Backend.Entities.Game;
import com.project.Backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Game table
 */
@Repository
public interface FullGameRepository extends JpaRepository<Game, Integer> {

    Game findById(int id);

    @Query("SELECT t FROM Game t where t.status = :status")
    List<Game> findAllOpen(@Param("status") String status);

    @Query ("select t.game from GamePlayers t where t.player = :user order by t.game.date desc")
    List<Game> findGamebyUserDesc(@Param("user") User user);
}
