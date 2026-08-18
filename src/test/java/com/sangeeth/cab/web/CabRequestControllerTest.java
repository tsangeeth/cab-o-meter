package com.sangeeth.cab;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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
class CabRequestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldCreateNewCabRequest() throws Exception {
        MockHttpSession session = TestSessions.signIn(mvc, "PC0014", "password");
        LocalDate start = LocalDate.now().plusDays(3);
        LocalDate end = start.plusDays(14);
        String body = """
                {
                  "startDate": "%s",
                  "endDate": "%s",
                  "loginTime": "09:00:00",
                  "logoutTime": "18:30:00",
                  "reoccurDays": ["Monday", "Tuesday", "Wednesday", "Thursday"],
                  "reason": "Office commute"
                }
                """.formatted(start, end);

        mvc.perform(post("/api/cab-requests")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value("PC0014"))
                .andExpect(jsonPath("$.status").value("pending"))
                .andExpect(jsonPath("$.reason").value("Office commute"));
    }
}
