package org.example.mygitapi.githubclient;

import lombok.RequiredArgsConstructor;
import org.example.mygitapi.data.GitBranch;
import org.example.mygitapi.data.GitRepository;
import org.example.mygitapi.dto.GitBranchDto;
import org.example.mygitapi.dto.GitRepositoryDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GitHubClient {

    @Value("${gitHubApi}")
    private String gitHubApi;
    private final RestTemplate restTemplate;

    public List<GitRepositoryDto> getRepositoriesForUser(String userLogin) throws RestClientException {
        String url = gitHubApi + "/users/" + userLogin + "/repos";
        GitRepository[] repositories = restTemplate.getForObject(url, GitRepository[].class);
        if (repositories == null) return List.of();

        return Arrays.stream(repositories).filter(it -> !it.isFork())
                .map(it -> new GitRepositoryDto(
                        it.getName(),
                        it.getOwner().getLogin(),
                        getBranchesForRepo(it.getOwner().getLogin(), it.getName())
                )).toList();
    }

    private List<GitBranchDto> getBranchesForRepo(String owner, String repoName) {
        String url = gitHubApi + "/repos/" + owner + "/" + repoName + "/branches";

        try {
            GitBranch[] branches = restTemplate.getForObject(url, GitBranch[].class);
            if (branches == null) return List.of();

            return Arrays.stream(branches).map( it -> new GitBranchDto(
                    it.getName(),
                    it.getCommit().getSha()
            )).toList();

        } catch (RestClientException e) {
            return List.of();
        }
    }

}
