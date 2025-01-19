package com.saeed.audio.repository;

import com.saeed.audio.model.dto.GitHubReleaseDto;
import com.saeed.audio.model.dto.GitHubReleaseDto.Asset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CacheYtDlpRepository extends YtDlpRepository {

    private static final String CACHE_PATH = "/.cache/audio-client/yt-dlp";

    private final String homeDirectory = System.getProperty("user.home");
    private final GitHubRepository gitHubRepository;
    private final RestTemplate restTemplate;

    @Override
    public boolean canRun() {
        // TODO: Support windows which probably calls yt-dlp.exe
        FileSystemResource resource = new FileSystemResource(homeDirectory + CACHE_PATH);
        if (!resource.exists()) {
            return false;
        }
        return checkYtDlpVersion(resource.getPath());
    }

    @Override
    public boolean canDownload() {
        return true;
    }

    @Override
    public boolean download() {
        GitHubReleaseDto releases = gitHubRepository.getGithubReleases();
        Asset asset = releases.getAssetFromOS();
        log.info("Retrieved asset from Github: {}", asset);

        Optional.ofNullable(asset).ifPresent(a ->
            restTemplate.execute(a.browser_download_url(),
                HttpMethod.GET,
                req -> req.getHeaders().setAccept(singletonList(MediaType.APPLICATION_OCTET_STREAM)),
                res -> {
                    Path path = Paths.get(homeDirectory + CACHE_PATH);
                    Files.createDirectories(path.getParent());
                    File f = Files.createFile(path).toFile();
                    try (FileOutputStream outputStream = new FileOutputStream(f)) {
                        StreamUtils.copy(res.getBody(), outputStream);
                        boolean b = f.setExecutable(true);
                        log.info("Downloaded asset executable? {}", b);
                    }
                    return f;
                }));

        return canRun();
    }
}
