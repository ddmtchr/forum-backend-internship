package com.ddmtchr.forumbackendinternship.service;

import com.ddmtchr.forumbackendinternship.database.entities.Topic;
import com.ddmtchr.forumbackendinternship.database.repository.TopicRepository;
import com.ddmtchr.forumbackendinternship.mapper.MessageMapper;
import com.ddmtchr.forumbackendinternship.mapper.TopicMapper;
import com.ddmtchr.forumbackendinternship.payload.TopicDTO;
import com.ddmtchr.forumbackendinternship.payload.TopicNoMessagesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;

    public Boolean existsById(String id) {
        return topicRepository.existsById(id);
    }

    public List<TopicNoMessagesDTO> findAllTopics() {
        List<Topic> topics = (List<Topic>) topicRepository.findAll();
        return topics.stream().map(TopicMapper.instance::toTopicMessageDTO).toList();
    }

    public Optional<Topic> findTopicById(String id) {
        return topicRepository.findById(id);
    }

    public Topic createTopic(TopicDTO topicDTO) {
        Topic topic = TopicMapper.instance.toTopic(topicDTO);
        topic.addMessage(MessageMapper.instance.toMessage(topicDTO.getMessage()));
        return topicRepository.save(topic);
    }

    public Topic updateTopic(TopicNoMessagesDTO topicNoMessagesDTO) {
        Topic topic = topicRepository.findById(topicNoMessagesDTO.getId()).orElse(null);
        if (topic == null) {
            return null;
        }
        topic.setName(topicNoMessagesDTO.getName());
        return topicRepository.save(topic);
    }

    public void deleteTopic(String id) {
        topicRepository.deleteById(id);
    }

}
