package org.example.mygitapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GitBranchDto {

    final String name;
    final String lastCommitSha;
}
