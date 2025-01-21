package com.saeed.audio.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Repository
@Slf4j
public class YtDlpRepository {

    private static final String DIRECTORY = System.getProperty("user.home") + "/Music";
    private static final List<String> CAN_RUN = List.of("yt-dlp", "--version");
    private static final List<String> DOWNLOAD = List.of("yt-dlp", "-x", "--audio-format", "mp3", "-P", DIRECTORY);

    public boolean canRun() {
        return runCommand(CAN_RUN);
    }

    public boolean downloadMp3(String url) {
        return runCommand(Stream.concat(DOWNLOAD.stream(), Stream.of(url)).toList());
    }

    private boolean runCommand(List<String> commands) {
        try {
            log.info("Trying command: {}", commands);
            ProcessBuilder p = new ProcessBuilder(commands);
            p.redirectErrorStream(true);
            Process process = p.start();
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            log.warn("Failed to run yt-dlp: {}", e.getMessage());
            return false;
        }
    }
}
