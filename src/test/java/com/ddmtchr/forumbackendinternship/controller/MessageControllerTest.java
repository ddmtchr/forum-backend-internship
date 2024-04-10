package com.ddmtchr.forumbackendinternship.controller;

import com.ddmtchr.forumbackendinternship.database.entities.Message;
import com.ddmtchr.forumbackendinternship.database.entities.Topic;
import com.ddmtchr.forumbackendinternship.payload.MessageDTO;
import com.ddmtchr.forumbackendinternship.payload.MessageUpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@WithMockUser(roles = "USER")
public class MessageControllerTest extends AbstractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    void addMessage_ReturnsTopic() throws Exception {
        MessageDTO messageDTO = new MessageDTO("Message 42", "Vasya");
        String inputJson = mapToJson(messageDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/topic/89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6/message")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        Topic topic = mapFromJson(content, Topic.class);
        assertEquals("89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6", topic.getId());
        assertEquals("Message 42", topic.getMessages().get(topic.getMessages().size() - 1).getText());
    }

    @Test
    @Transactional
    void addMessage_WrongId_ReturnsNotFound() throws Exception {
        MessageDTO messageDTO = new MessageDTO("Message 42", "Vasya");
        String inputJson = mapToJson(messageDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/topic/aaa/message")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        assertEquals("Topic is not found", content);
    }

    @Test
    @Transactional
    void updateMessage_ReturnsList() throws Exception {
        MessageUpdateDTO messageDTO = new MessageUpdateDTO("9ded9493-64cb-4b4a-ac57-0d0732282d42", "Message 42", "Vasya");
        String inputJson = mapToJson(messageDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/topic/89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6/message")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        Topic[] topicList = mapFromJson(content, Topic[].class);
        assertTrue(topicList.length > 0);
        Topic topic = Arrays.stream(topicList).filter(t -> t.getId().equals("89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6")).findFirst().orElse(null);
        assertNotNull(topic);
        Message message = topic.getMessages().stream().filter(m -> m.getId().equals("9ded9493-64cb-4b4a-ac57-0d0732282d42")).findFirst().orElse(null);
        assertNotNull(message);
        assertEquals("Message 42", message.getText());
        assertEquals("Vasya", message.getAuthor());
    }

    @Test
    @Transactional
    void updateMessage_WrongTopicId_ReturnsNotFound() throws Exception {
        MessageUpdateDTO messageDTO = new MessageUpdateDTO("9ded9493-64cb-4b4a-ac57-0d0732282d42", "Message 42", "Vasya");
        String inputJson = mapToJson(messageDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/topic/aaa/message")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        assertEquals("Topic is not found", content);
    }

    @Test
    @Transactional
    void updateMessage_WrongMessageId_ReturnsNotFound() throws Exception {
        MessageUpdateDTO messageDTO = new MessageUpdateDTO("aaa", "Message 42", "Vasya");
        String inputJson = mapToJson(messageDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/topic/89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6/message")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        assertEquals("Message is not found", content);
    }

    @Test
    @Transactional
    void updateMessage_NoSuchMessageInTopic_ReturnsNotFound() throws Exception {
        MessageUpdateDTO messageDTO = new MessageUpdateDTO("9ded9493-64cb-4b4a-ac57-0d0732282d42", "Message 42", "Vasya");
        String inputJson = mapToJson(messageDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/topic/16e08225-d756-4b29-b754-a5417a1a8c7f/message")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        assertEquals("Message is not found", content);
    }

    @Test
    @Transactional
    void deleteMessage_ReturnsNoContent() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/message/9ded9493-64cb-4b4a-ac57-0d0732282d42")).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    @Transactional
    void deleteMessage_WrongId_ReturnsNotFound() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/message/aaa")).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }
}
