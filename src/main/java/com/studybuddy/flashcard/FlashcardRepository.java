package com.studybuddy.flashcard;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    @Query(value = "SELECT * FROM flashcard ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Flashcard> findRandom();

    @Query(value = "SELECT * FROM flashcard WHERE topic = :topic ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Flashcard> findRandomByTopic(String topic);
}
