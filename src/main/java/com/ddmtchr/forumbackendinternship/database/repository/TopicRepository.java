package com.ddmtchr.forumbackendinternship.database.repository;

import com.ddmtchr.forumbackendinternship.database.entities.Topic;
import com.ddmtchr.forumbackendinternship.payload.TopicNoMessagesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends PagingAndSortingRepository<Topic, String>, CrudRepository<Topic, String> {
    @Query("SELECT t FROM Topic t WHERE t.id = (SELECT m.topic.id FROM Message m WHERE m.id = :messageId)")
    Topic findTopicByMessageId(@Param("messageId") String messageId);

    @NonNull
    @Query("SELECT new com.ddmtchr.forumbackendinternship.payload.TopicNoMessagesDTO(t.id, t.name, t.created) FROM Topic t")
    Page<TopicNoMessagesDTO> findAllProjected(@NonNull Pageable pageable);
}
