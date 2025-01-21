package com.saeed.audio.controllers;

import com.saeed.audio.service.DownloaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/downloader")
@RequiredArgsConstructor
@Slf4j
public class DownloaderController {

    private final DownloaderService downloaderService;

    @GetMapping("/check")
    public boolean checkForYtDlp() {
        log.info("Checking for yt-dlp...");
        return downloaderService.checkForYtDlp();
    }

    @PostMapping
    public void downloadAudioFromUrl(@RequestParam String url) {
        log.info("Downloading audio from URL: {}", url);
        downloaderService.downloadAudioFromUrl(url);
    }
}
