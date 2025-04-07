package org.example.mygitapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GitBranch {

    private String name;
    private Commit commit;

    @AllArgsConstructor
    @Getter
    public class Commit {
        private String sha;
    }
}
