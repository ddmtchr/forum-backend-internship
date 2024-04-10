package com.ddmtchr.forumbackendinternship.payload;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopicDTO {
    @NotNull
    private String name;
    @NotNull
    private MessageDTO message;
}
