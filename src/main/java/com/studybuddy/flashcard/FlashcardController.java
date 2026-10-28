package com.studybuddy.flashcard;

import java.net.URI;

import com.studybuddy.flashcard.FlashcardDtos.CreateFlashcardRequest;
import com.studybuddy.flashcard.FlashcardDtos.FlashcardResponse;
import com.studybuddy.flashcard.FlashcardDtos.QuizQuestion;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FlashcardController {

    private final FlashcardRepository repository;

    public FlashcardController(FlashcardRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/cards")
    public ResponseEntity<FlashcardResponse> addCard(@Valid @RequestBody CreateFlashcardRequest request) {
        Flashcard saved = repository.save(new Flashcard(request.question(), request.answer(), request.topic()));
        return ResponseEntity.created(URI.create("/api/cards/" + saved.getId()))
                .body(FlashcardResponse.from(saved));
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<FlashcardResponse> getCard(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id).map(FlashcardResponse::from));
    }

    /** Returns a random card's question (without the answer); 404 if no cards exist. */
    @GetMapping("/quiz")
    public ResponseEntity<QuizQuestion> getQuizQuestion(@RequestParam(required = false) String topic) {
        var card = topic == null ? repository.findRandom() : repository.findRandomByTopic(topic);
        return ResponseEntity.of(card.map(QuizQuestion::from));
    }
}
