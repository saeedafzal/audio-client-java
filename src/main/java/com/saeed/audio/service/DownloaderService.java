package com.saeed.audio.service;

import com.saeed.audio.repository.YtDlpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DownloaderService {

    private final List<YtDlpRepository> ytDlpRepositoryList;

    public boolean checkForYtDlp() {
        return ytDlpRepositoryList.stream().anyMatch(YtDlpRepository::canRun);
    }

    public void downloadYtDlp() {
        // TODO: Seems unnecessary?
        boolean result = ytDlpRepositoryList.stream()
            .filter(YtDlpRepository::canDownload)
            .findFirst()
            .map(YtDlpRepository::download)
            .orElse(false);
        log.info("Download success: {}", result);
        if (!result) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Download failed");
        }
    }
}
