package com.ddmtchr.forumbackendinternship.controller;

import com.ddmtchr.forumbackendinternship.database.entities.Message;
import com.ddmtchr.forumbackendinternship.database.entities.Topic;
import com.ddmtchr.forumbackendinternship.payload.MessageDTO;
import com.ddmtchr.forumbackendinternship.payload.TopicDTO;
import com.ddmtchr.forumbackendinternship.payload.TopicNoMessagesDTO;
import com.ddmtchr.forumbackendinternship.util.ResponsePage;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@WithMockUser(roles = "USER")
class TopicControllerTest extends AbstractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTopics_ReturnsList() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/topic")).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        Page<TopicNoMessagesDTO> topicList = mapFromJson(content, new TypeReference<ResponsePage<TopicNoMessagesDTO>>(){});
        assertTrue(topicList.getTotalElements() > 0);
    }

    @Test
    void getTopicsById_ReturnsMessagesList() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/topic/89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6")).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        Page<Message> messageList = mapFromJson(content,  new TypeReference<ResponsePage<Message>>(){});
        assertTrue(messageList.getTotalElements() > 0);
    }

    @Test
    void getTopicsById_WrongId_ReturnsNotFound() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/topic/aaa")).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        assertEquals("Topic is not found", content);
    }

    @Test
    void createTopic_ReturnsCreated() throws Exception {
        TopicDTO topicDTO = new TopicDTO("Topic 1000", new MessageDTO("Message 1000", "Vasya"));
        String inputJson = mapToJson(topicDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/topic")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        Topic topic = mapFromJson(content, Topic.class);
        assertEquals("Topic 1000", topic.getName());
        assertEquals("Message 1000", topic.getMessages().get(0).getText());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTopic_ReturnsOk() throws Exception {
        TopicNoMessagesDTO topicNoMessagesDTO = new TopicNoMessagesDTO("89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6", "Topic 1 New Name", null);
        String inputJson = mapToJson(topicNoMessagesDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/topic")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        Page<TopicNoMessagesDTO> topicList = mapFromJson(content, new TypeReference<ResponsePage<TopicNoMessagesDTO>>(){});
        assertTrue(topicList.getTotalElements() > 0);
        TopicNoMessagesDTO topic = topicList.getContent().stream().filter(t -> t.getId().equals("89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6")).findFirst().orElse(null);
        assertNotNull(topic);
        assertEquals("Topic 1 New Name", topic.getName());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTopic_WrongId_ReturnsNotFound() throws Exception {
        TopicNoMessagesDTO topicNoMessagesDTO = new TopicNoMessagesDTO("aaa", "Topic 1 New Name", null);
        String inputJson = mapToJson(topicNoMessagesDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/topic")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        assertEquals("Topic is not found", content);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteTopic_ReturnsNoContent() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/topic/16e08225-d756-4b29-b754-a5417a1a8c7f")).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteTopic_WrongId_ReturnsNotFound() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/topic/aaa")).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

}
