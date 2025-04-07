package org.example.mygitapi.service;

import lombok.RequiredArgsConstructor;
import org.example.mygitapi.dto.GitRepositoryDto;
import org.example.mygitapi.githubclient.GitHubClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubServiceImpl implements GitHubService {

    private final GitHubClient gitHubClient;
    @Override
    public List<GitRepositoryDto> getRepositories(String userLogin) throws RestClientException {
        return gitHubClient.getRepositoriesForUser(userLogin);
    }
}
