package com.saeed.audio.controllers;

import com.saeed.audio.service.DownloaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void downloadYtDlp() {
        log.info("Downloading yt-dlp...");
        downloaderService.downloadYtDlp();
    }
}
