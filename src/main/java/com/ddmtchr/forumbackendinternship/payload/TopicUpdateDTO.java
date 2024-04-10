package com.ddmtchr.forumbackendinternship.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopicUpdateDTO {
    @NotNull
    private String id;
    @NotNull
    private String topicName;
}
