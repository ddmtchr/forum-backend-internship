package com.ddmtchr.forumbackendinternship.controller;

import com.ddmtchr.forumbackendinternship.database.entities.Topic;
import com.ddmtchr.forumbackendinternship.payload.TopicDTO;
import com.ddmtchr.forumbackendinternship.payload.TopicNoMessagesDTO;
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
public class TopicController {
    private final TopicService topicService;
    private final MessageService messageService;

    @GetMapping("/topic")
    public ResponseEntity<?> getAllTopics(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int size) {
        return ResponseEntity.ok(topicService.findAllTopics(page, size));
    }

    @GetMapping("/topic/{id}")
    public ResponseEntity<?> getTopicById(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
            @PathVariable String id) { //todo pagination
        Topic topic = topicService.findTopicById(id).orElse(null);
        if (topic == null) {
            return new ResponseEntity<>("Topic is not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(messageService.findAllByTopicId(page, size, id));
    }

    @PostMapping("/topic")
    public ResponseEntity<?> createTopic(@RequestBody @Valid TopicDTO topicDTO) {
        return new ResponseEntity<>(topicService.createTopic(topicDTO), HttpStatus.CREATED);
    }

    @PutMapping("/topic")
    public ResponseEntity<?> updateTopic(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int size,
            @RequestBody @Valid TopicNoMessagesDTO topicNoMessagesDTO) {
        if (topicService.updateTopic(topicNoMessagesDTO) == null) {
            return new ResponseEntity<>("Topic is not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(topicService.findAllTopics(page, size));
    }

    @DeleteMapping("/topic/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable String id) {
        if (!topicService.existsById(id)) {
            return new ResponseEntity<>("Topic is not found", HttpStatus.NOT_FOUND);
        }
        topicService.deleteTopic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
