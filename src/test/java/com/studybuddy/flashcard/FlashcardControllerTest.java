package com.studybuddy.flashcard;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class FlashcardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FlashcardRepository repository;

    @BeforeEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void quizReturns404WhenNoCards() throws Exception {
        mvc.perform(get("/api/quiz")).andExpect(status().isNotFound());
    }

    @Test
    void addCardThenGetQuizQuestionWithoutAnswer() throws Exception {
        mvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"question": "What does JPA stand for?", "answer": "Jakarta Persistence API", "topic": "java"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.answer").value("Jakarta Persistence API"));

        mvc.perform(get("/api/quiz").param("topic", "java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value("What does JPA stand for?"))
                .andExpect(jsonPath("$.answer").doesNotExist());
    }

    @Test
    void addCardRejectsBlankQuestion() throws Exception {
        mvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"question\": \"\", \"answer\": \"x\"}"))
                .andExpect(status().isBadRequest());
    }
}
