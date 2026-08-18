package com.sangeeth.cab;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldGiveInformationAboutEmployee() throws Exception {
        MockHttpSession session = TestSessions.signIn(mvc, "PC0014", "password");

        mvc.perform(get("/api/user").session(session).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeId").value("PC0014"))
                .andExpect(jsonPath("$.firstName").value("Mannix"))
                .andExpect(jsonPath("$.lastName").value("Buckley"))
                .andExpect(jsonPath("$.role").value("Employee"))
                .andExpect(jsonPath("$.costCentre").value("12345"))
                .andExpect(jsonPath("$.address.city").value("Chennai"));
    }

    @Test
    void shouldRejectUnknownCredentials() throws Exception {
        mvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"employeeId\":\"NOPE\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized());
    }
}
