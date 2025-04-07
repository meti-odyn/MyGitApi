# MyGitAPI

MyGitAPI is a Spring Boot application that integrates with the GitHub API to fetch repositories and their details
for a given user. The application is designed to provide users with an endpoint to retrieve the repositories of a 
GitHub user, along with information such as repository names, owner logins, branches, and commit details.

## Features

- Fetch repositories of a user from GitHub.
- Provide details about each repository including branch names and latest commit SHAs.
- Handles cases where the user does not exist on GitHub.

## Requirements

- Java 21 or higher
- Spring Boot 3.4.4
- Gradle for building the project
