package com.studybuddy.flashcard;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class FlashcardDtos {

    private FlashcardDtos() {
    }

    public record CreateFlashcardRequest(
            @NotBlank @Size(max = 1000) String question,
            @NotBlank @Size(max = 2000) String answer,
            @Size(max = 255) String topic) {
    }

    public record FlashcardResponse(Long id, String question, String answer, String topic) {
        static FlashcardResponse from(Flashcard card) {
            return new FlashcardResponse(card.getId(), card.getQuestion(), card.getAnswer(), card.getTopic());
        }
    }

    /** A quiz question deliberately omits the answer. */
    public record QuizQuestion(Long id, String question, String topic) {
        static QuizQuestion from(Flashcard card) {
            return new QuizQuestion(card.getId(), card.getQuestion(), card.getTopic());
        }
    }
}
