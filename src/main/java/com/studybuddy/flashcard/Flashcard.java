package com.studybuddy.flashcard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String question;

    @Column(nullable = false, length = 2000)
    private String answer;

    private String topic;

    protected Flashcard() {
        // required by JPA
    }

    public Flashcard(String question, String answer, String topic) {
        this.question = question;
        this.answer = answer;
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getTopic() {
        return topic;
    }
}
