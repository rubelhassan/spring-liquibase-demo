package com.example.springliquibasedemo.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TaskControllerTest {
    @Autowired
    MockMvc mockMvc;

    private final MediaType contentType = new MediaType("application", "hal+json");

    @Test
    void testFetchAllTasks() throws Exception {
        // see test context data in liquibase script
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._embedded.tasks", hasSize(2)))
                .andExpect(jsonPath("$._embedded.tasks[0].task.id", is(1)))
                .andExpect(jsonPath("$._embedded.tasks[1].task.id", is(2)));
    }

    @Test
    void testFetchTask() throws Exception {
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.task.id", is(1)))
                .andExpect(jsonPath("$.task.title", is("Liquibase Code Repository")));
    }
}
