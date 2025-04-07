package org.example.mygitapi;

import org.example.mygitapi.controller.MyGitController;
import org.example.mygitapi.dto.GitBranchDto;
import org.example.mygitapi.dto.GitRepositoryDto;
import org.example.mygitapi.service.GitHubService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MyGitController.class)
public class MyGitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GitHubService gitHubService;

    @Value("${apiPrefix}")
    private String myApi;

    @Test
    @DisplayName("Should return user repositories.")
    void shouldReturnUserRepositories() throws Exception {

        // Given:
        String branchName = "test-branch-name";
        String lastCommitSha = "test-last-commit-sha";
        String repositoryName = "test-repository-name";
        String userLogin = "test-user-login";

        List<GitBranchDto> branches = List.of(new GitBranchDto(branchName, lastCommitSha));
        List<GitRepositoryDto> repos = List.of(
                new GitRepositoryDto(repositoryName, userLogin, branches)
        );

        given(gitHubService.getRepositories(userLogin)).willReturn(repos);

        mockMvc.perform(get(myApi + "/users/" + userLogin + "/repositories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].repositoryName").value(repositoryName))
                .andExpect(jsonPath("$[0].ownerLogin").value(userLogin))
                .andExpect(jsonPath("$[0].branches[0].name").value(branchName))
                .andExpect(jsonPath("$[0].branches[0].lastCommitSha").value(lastCommitSha));
    }

    @Test
    @DisplayName("Should return not found when user does not exist.")
    void shouldReturnNotFoundWhenUserNotExists() throws Exception {

        String userLogin = "no-existing-user-login";

        doThrow(new RestClientException("exceptionMessage"))
                .when(gitHubService).getRepositories(userLogin);

        mockMvc.perform(get(myApi + "/users/" + userLogin + "/repositories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("User not found!"));
    }
}
