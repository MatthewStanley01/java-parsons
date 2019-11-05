package com.ten10.training.javaparsons.webapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ten10.training.javaparsons.webapp.views.Results;
import com.ten10.training.javaparsons.webapp.views.SubmittedSolution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ExerciseControllerTest {

    private static final String TRIVIAL_INPUT = "{\"input\": \"foo\"}";
    private static final String TRIVIAL_OUTPUT = "{}";


    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private MockMvc mvc;

    @Test
    void trivialInputShouldLoad() throws IOException {
        SubmittedSolution value = objectMapper.readValue(TRIVIAL_INPUT, SubmittedSolution.class);
        assertThat(value, is(notNullValue()));
        assertThat(value.getInput(), is("foo"));
    }

    @Test
    void serialiseResult() throws JsonProcessingException {
        Results results = new Results();
        results.storeCapturedOutput("Foo");
        String output = objectMapper.writeValueAsString(results);
        assertThat(output, is("{\"output\":\"Foo\",\"succesfulSolution\":false,\"compilerErrors\":[],\"compilerInfo\":[],\"runnerErrors\":[]}"));
    }

    @Test
    void getIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk());
    }

    @Test
    void submitExerciseSuccessfully() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/exercise/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TRIVIAL_INPUT);

        mvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().json(TRIVIAL_OUTPUT));

    }

    @Test
    void serialiseCompilerError() throws JsonProcessingException {
        Results results = new Results();
        results.storeCapturedOutput("Null");
        results.reportCompilerError(3, "incorrect Method");
        String output = objectMapper.writeValueAsString(results);
        assertThat(output, is("{\"output\":\"Null\",\"succesfulSolution\":false,\"compilerErrors\":[{\"lineNumber\":3,\"message\":\"incorrect Method\"}],\"compilerInfo\":[],\"runnerErrors\":[]}"));
    }

    private static final ExerciseController.ExerciseInformation exerciseInformation = new ExerciseController.ExerciseInformation("URL", "Title", "Description", "code", "code", 1);

    @Test
    void exerciseInformationGetURL () {
        assertEquals(exerciseInformation.getUrl(), "URL");
    }

    @Test
    void exerciseInformationGetTitle () {
        assertEquals(exerciseInformation.getTitle(), "Title");
    }

    @Test
    void exerciseInformationGetDescription () {
        assertEquals(exerciseInformation.getDescription(), "Description");
    }
}
