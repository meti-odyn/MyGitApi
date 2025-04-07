package org.example.mygitapi;

import org.example.mygitapi.dto.GitBranchDto;
import org.example.mygitapi.dto.GitRepositoryDto;
import org.example.mygitapi.service.GitHubService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MyGitControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Value("${apiPrefix}")
    private String apiPrefix;

    @Test
    @DisplayName("Integration: should return real GitHub data")
    void shouldReturnGitHubRepositories() throws Exception {
        String userLogin = "octocat";

        mockMvc.perform(get(apiPrefix + "/users/" + userLogin + "/repositories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].repositoryName").exists())
                .andExpect(jsonPath("$[0].ownerLogin").value(userLogin))
                .andExpect(jsonPath("$[0].branches").isArray());
    }

    @Test
    @DisplayName("Integration: should return 404 when user does not exist")
    void shouldReturnNotFound() throws Exception {
        String userLogin = "non-existent-user-asdghqw123";

        mockMvc.perform(get(apiPrefix + "/users/" + userLogin + "/repositories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found!"));
    }

}
