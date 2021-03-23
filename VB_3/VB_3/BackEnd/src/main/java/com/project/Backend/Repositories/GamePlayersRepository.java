package com.project.Backend.Repositories;

import com.project.Backend.Entities.Game;
import com.project.Backend.Entities.GamePlayers;
import com.project.Backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing who played what game and got what score
 */
@Repository
public interface GamePlayersRepository extends JpaRepository<GamePlayers, Integer> {

    GamePlayers save(GamePlayers g);

    @Query ("select t from GamePlayers t where t.player = :user")
    List<GamePlayers> findAllByUser(@Param("user") User user);

    @Query ("select t from GamePlayers t where t.player = :user AND t.game = :game")
    GamePlayers findScoreByUserAndGame(@Param("user") User user, @Param("game") Game game);

    @Query ("select t from GamePlayers t where t.player <> :user AND t.game = :game")
    List<GamePlayers> findEnemyScoresByGame(@Param("user") User user, @Param("game") Game game);

}