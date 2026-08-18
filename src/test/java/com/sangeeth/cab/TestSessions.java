package com.sangeeth.cab;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

final class TestSessions {

    private TestSessions() {
    }

    static MockHttpSession signIn(MockMvc mvc, String employeeId, String password) throws Exception {
        MockHttpSession session = new MockHttpSession();
        mvc.perform(post("/api/login")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"employeeId\":\"%s\",\"password\":\"%s\"}".formatted(employeeId, password)))
                .andExpect(result -> {
                    if (result.getResponse().getStatus() != 200) {
                        throw new AssertionError("Login failed: " + result.getResponse().getContentAsString());
                    }
                });
        return session;
    }
}
