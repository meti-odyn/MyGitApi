package org.example.mygitapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.mygitapi.dto.GitRepositoryDto;
import org.example.mygitapi.service.GitHubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${apiPrefix}/users")
public class MyGitController {

    private final GitHubService gitHubService;

    @GetMapping("/{userLogin}/repositories")
    public ResponseEntity<?> getUserRepositories(@PathVariable String userLogin) {
        try {
            List<GitRepositoryDto> repositories = gitHubService.getRepositories(userLogin);
            return ResponseEntity.ok(repositories);
        } catch (RestClientException e) {
            return ResponseEntity.status(NOT_FOUND).body(Map.of(
                    "status", NOT_FOUND.value(),
                    "message", "User not found!"
            ));
        }
    }
}
