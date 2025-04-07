package org.example.mygitapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GitRepositoryDto {

    final String repositoryName;
    final String ownerLogin;
    final List<GitBranchDto> branches;
}
