package com.ddmtchr.forumbackendinternship.database.repository;

import com.ddmtchr.forumbackendinternship.database.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, String>, CrudRepository<Message, String> {
    List<Message> findAllByTopicId(String topicId);
    Page<Message> findAllByTopicId(String topicId, Pageable pageable);
}
