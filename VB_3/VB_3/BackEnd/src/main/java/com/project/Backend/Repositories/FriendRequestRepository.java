package com.project.Backend.Repositories;

import com.project.Backend.Entities.FriendRequest;
import com.project.Backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing friend requests
 */
@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest,Integer> {

    @Query ("select distinct t from FriendRequest t where t.sender = :sender and t.receiver = :receiver")
    FriendRequest findRequest(@Param("sender") User sender, @Param("receiver") User receiver);

    @Query ("select t.sender from FriendRequest t where t.receiver = :receiver")
    List<User> findByReceiver(@Param("receiver") User receiver);
}
