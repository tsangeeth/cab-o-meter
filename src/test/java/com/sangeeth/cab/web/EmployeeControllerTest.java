package com.sangeeth.cab;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldSearchForEmployeeBasedOnHisLastName() throws Exception {
        MockHttpSession session = TestSessions.signIn(mvc, "PC0013", "password");

        mvc.perform(get("/api/employees").param("name", "Howell").session(session).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].employeeId").value(hasItem("PC0282")));
    }

    @Test
    void shouldSearchAcrossNameFields() throws Exception {
        MockHttpSession session = TestSessions.signIn(mvc, "PC0013", "password");

        mvc.perform(get("/api/employees").param("name", "John").session(session).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].employeeId").value(hasItem("PC0958")));
    }
}
