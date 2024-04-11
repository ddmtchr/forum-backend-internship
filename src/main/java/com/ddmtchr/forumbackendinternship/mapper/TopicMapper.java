package com.ddmtchr.forumbackendinternship.mapper;

import com.ddmtchr.forumbackendinternship.database.entities.Topic;
import com.ddmtchr.forumbackendinternship.payload.TopicDTO;
import com.ddmtchr.forumbackendinternship.payload.TopicNoMessagesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;

@Mapper
public interface TopicMapper {
    TopicMapper instance = Mappers.getMapper(TopicMapper.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "created", expression = "java(java.time.ZonedDateTime.now().format(formatter))"),
            @Mapping(target = "messages", expression = "java(new java.util.ArrayList<>())")
    })
    Topic toTopic(TopicDTO topicDTO);

    TopicNoMessagesDTO toTopicMessageDTO(Topic topic);
}
