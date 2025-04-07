package org.example.mygitapi.service;

import org.example.mygitapi.dto.GitRepositoryDto;

import java.util.List;

public interface GitHubService {

    public List<GitRepositoryDto> getRepositories (String userLogin);
}
