package com.ddmtchr.forumbackendinternship.controller;

import com.ddmtchr.forumbackendinternship.database.entities.Message;
import com.ddmtchr.forumbackendinternship.database.entities.Topic;
import com.ddmtchr.forumbackendinternship.payload.MessageDTO;
import com.ddmtchr.forumbackendinternship.payload.MessageUpdateDTO;
import com.ddmtchr.forumbackendinternship.service.MessageService;
import com.ddmtchr.forumbackendinternship.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MessageController {
    private final TopicService topicService;
    private final MessageService messageService;

    @PostMapping("/topic/{topicId}/message")
    public ResponseEntity<?> addMessage(@PathVariable String topicId, @RequestBody @Valid MessageDTO messageDTO) {
        Topic topic = messageService.addMessage(topicId, messageDTO);
        if (topic == null) {
            return new ResponseEntity<>("Topic is not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(topic, HttpStatus.CREATED);
    }

    @PutMapping("/topic/{topicId}/message")
    public ResponseEntity<?> updateMessage(@PathVariable String topicId, @RequestBody @Valid MessageUpdateDTO messageUpdateDTO) {
        if (!topicService.existsById(topicId)) { //todo only user's messages
            return new ResponseEntity<>("Topic is not found", HttpStatus.NOT_FOUND);
        }
        Message message = messageService.updateMessage(topicId, messageUpdateDTO);
        if (message == null) {
            return new ResponseEntity<>("Message is not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(topicService.findAllTopics());
    }

    @DeleteMapping("/message/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable String id) {
        if (!messageService.existsById(id)) { //todo only user's messages
            return new ResponseEntity<>("Message is not found", HttpStatus.NOT_FOUND);
        }
        messageService.deleteMessage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
