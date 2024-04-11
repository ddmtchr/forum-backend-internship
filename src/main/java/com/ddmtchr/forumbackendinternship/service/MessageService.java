package com.ddmtchr.forumbackendinternship.service;

import com.ddmtchr.forumbackendinternship.database.entities.Message;
import com.ddmtchr.forumbackendinternship.database.entities.Topic;
import com.ddmtchr.forumbackendinternship.database.repository.MessageRepository;
import com.ddmtchr.forumbackendinternship.database.repository.TopicRepository;
import com.ddmtchr.forumbackendinternship.mapper.MessageMapper;
import com.ddmtchr.forumbackendinternship.payload.MessageDTO;
import com.ddmtchr.forumbackendinternship.payload.MessageUpdateDTO;
import com.ddmtchr.forumbackendinternship.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;
    private final JwtUtils jwtUtils;

    public Boolean existsById(String id) {
        return messageRepository.existsById(id);
    }

    public Page<Message> findAllByTopicId(int page, int size, String id) {
        Pageable p = PageRequest.of(page, size);
        return messageRepository.findAllByTopicId(id, p);
    }

    @Transactional
    public Topic addMessage(String topicId, MessageDTO messageDTO) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic == null) {
            return null;
        }
        Message message = MessageMapper.instance.toMessage(messageDTO);
        topic.addMessage(message);
        topicRepository.save(topic);
        return topic;
    }

    @Transactional
    public Message updateMessage(String topicId, MessageUpdateDTO messageUpdateDTO) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic != null) {
            List<Message> messages = messageRepository.findAllByTopicId(topicId);
            Message message = messages.stream().filter(m -> m.getId().equals(messageUpdateDTO.getId())).findFirst().orElse(null);
            if (message != null) {
                UserDetails user = jwtUtils.getCurrentUser();
                if (user.getUsername().equals(message.getAuthor()) || jwtUtils.hasRole(user, "ADMIN")) {
                    message.setText(messageUpdateDTO.getText());
                    return messageRepository.save(message);
                }
                throw new AccessDeniedException("No permission");
            }
            return null;
        }
        return null;
    }

    @Transactional
    public void deleteMessage(String id) {
        Topic topic = topicRepository.findTopicByMessageId(id);
        Message message = messageRepository.findById(id).orElse(null);
        String author = message == null ? null : message.getAuthor();
        UserDetails user = jwtUtils.getCurrentUser();
        if (user.getUsername().equals(author) || jwtUtils.hasRole(user, "ADMIN")) {
            topic.removeMessage(message);
            topicRepository.save(topic);
            if (topic.getMessages().isEmpty()) topicRepository.delete(topic); // could be done with db trigger
        } else throw new AccessDeniedException("No permission");
    }
}
