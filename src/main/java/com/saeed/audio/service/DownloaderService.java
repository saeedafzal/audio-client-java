package com.saeed.audio.service;

import com.saeed.audio.repository.YtDlpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DownloaderService {

    private final YtDlpRepository ytDlpRepository;

    public boolean checkForYtDlp() {
        return ytDlpRepository.canRun();
    }

    public void downloadAudioFromUrl(String url) {
        boolean result = false;
        if (ytDlpRepository.canRun()) {
            result = ytDlpRepository.downloadMp3(url);
        }

        log.info("Downloading url successful: {}", result);
        if (!result) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Download failed");
        }
    }
}
