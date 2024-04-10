package com.ddmtchr.forumbackendinternship.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String text;
    private String author;
    private String created;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", created='" + created + '\'' +
                ", topicId=" + (topic == null ? null : topic.getId()) +
                '}';
    }
}
