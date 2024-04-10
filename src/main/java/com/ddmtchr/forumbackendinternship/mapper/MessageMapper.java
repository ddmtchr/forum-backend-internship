package com.ddmtchr.forumbackendinternship.mapper;

import com.ddmtchr.forumbackendinternship.database.entities.Message;
import com.ddmtchr.forumbackendinternship.payload.MessageDTO;
import com.ddmtchr.forumbackendinternship.payload.MessageUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;

@Mapper
public interface MessageMapper {
    MessageMapper instance = Mappers.getMapper(MessageMapper.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "created", expression = "java(java.time.ZonedDateTime.now().format(formatter))")
    })
    Message toMessage(MessageDTO messageDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "created", ignore = true)
    })
    Message toMessage(MessageUpdateDTO messageUpdateDTO);
}
