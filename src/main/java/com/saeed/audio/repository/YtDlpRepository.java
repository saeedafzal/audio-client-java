package com.saeed.audio.repository;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public abstract class YtDlpRepository {

    public abstract boolean canRun();
    public abstract boolean canDownload();
    public abstract boolean download();

    protected boolean checkYtDlpVersion(String binaryPath) {
        try {
            log.info("Trying binary from path: {}", binaryPath);
            ProcessBuilder p = new ProcessBuilder(binaryPath, "--version");
            p.redirectErrorStream(true);
            Process process = p.start();
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            log.warn("Failed to run yt-dlp: {}", e.getMessage());
            return false;
        }
    }
}
