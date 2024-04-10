package com.ddmtchr.forumbackendinternship.controller;

import com.ddmtchr.forumbackendinternship.database.entities.Topic;
import com.ddmtchr.forumbackendinternship.payload.MessageDTO;
import com.ddmtchr.forumbackendinternship.payload.TopicDTO;
import com.ddmtchr.forumbackendinternship.payload.TopicUpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

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
        Topic[] topicList = mapFromJson(content, Topic[].class);
        assertTrue(topicList.length > 0);
    }

    @Test
    void getTopicsById_ReturnsTopic() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/topic/89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6")).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        Topic topic = mapFromJson(content, Topic.class);
        assertEquals("89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6", topic.getId());
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
    void updateTopic_ReturnsOk() throws Exception {
        TopicUpdateDTO topicUpdateDTO = new TopicUpdateDTO("89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6", "Topic 1 New Name");
        String inputJson = mapToJson(topicUpdateDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/topic")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        Topic[] topicList = mapFromJson(content, Topic[].class);
        Topic topic = Arrays.stream(topicList).filter(t -> t.getId().equals("89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6")).findFirst().orElse(null);
        assertNotNull(topic);
        assertEquals("Topic 1 New Name", topic.getName());
    }

    @Test
    void updateTopic_WrongId_ReturnsNotFound() throws Exception {
        TopicUpdateDTO topicUpdateDTO = new TopicUpdateDTO("aaa", "Topic 1 New Name");
        String inputJson = mapToJson(topicUpdateDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/topic")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        assertEquals("Topic is not found", content);
    }

}
