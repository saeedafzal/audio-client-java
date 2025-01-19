package com.saeed.audio.repository;

import com.saeed.audio.model.dto.GitHubReleaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
public class GitHubRepository {

    private static final String API_URL = "https://api.github.com/repos/yt-dlp/yt-dlp/releases/latest";
    private final RestTemplate restTemplate;

    public GitHubReleaseDto getGithubReleases() {
        return restTemplate
            .getForEntity(API_URL, GitHubReleaseDto.class)
            .getBody();
    }
}
