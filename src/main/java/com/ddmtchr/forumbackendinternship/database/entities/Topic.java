package com.ddmtchr.forumbackendinternship.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String created;
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "topic",
            orphanRemoval = true
    )
    private List<Message> messages;

    public void addMessage(Message message) {
        messages.add(message);
        message.setTopic(this);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
        message.setTopic(null);
    }
}
