package org.example.mygitapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class GitRepository {

    private String name;
    private boolean fork;
    private Owner owner;

    @Getter
    @AllArgsConstructor
    public  class Owner {
        private String login;
    }
}
