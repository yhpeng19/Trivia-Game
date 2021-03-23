package com.project.Backend.Repositories;

import com.project.Backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing app users info
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query ("select t from User t where t.id = :id")
    User findById(@Param("id") int id);
   // User save(User user);
    //List<User> findAll();
    @Query ("SELECT t FROM User t where t.email = :email")
    User findByEmail(@Param("email") String email);

    @Query ("select t from User t where t.username LIKE %:username% AND t.role = 'player'")
    List<User> findByUsername(@Param ("username") String username);

    @Query ("select count(t) from User t ")
    int numUsers();

    @Query ("select t from User t where t.username = :username")
    User findByUsernameUnique(@Param ("username") String username);

    @Query ("select t.friends from User t where t.id = :id")
    List <User> findFriendsById(@Param ("id") int id);

    @Query ("select t from User t order by t.userScore desc")
    List <User> findLeaders();

}
