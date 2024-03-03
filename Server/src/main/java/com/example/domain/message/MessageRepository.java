package com.example.domain.message;

import com.example.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByReceiver(User user);
    List<Message> findAllBySender(User user);

    @Query("select distinct m.receiver from Message m where m.sender = ?1")
    List<Object[]> findReceivers(User sender);

    @Query("select distinct m.sender from Message m where m.receiver = ?1")
    List<Object[]> findSenders(User receiver);

}