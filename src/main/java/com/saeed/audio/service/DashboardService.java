package com.saeed.audio.service;

import com.saeed.audio.model.responses.AudioListResponse.Audio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

@Service
@Slf4j
public class DashboardService {

    private final String homeDirectory = System.getProperty("user.home");

    public List<Audio> filenamesFromMusicDirectory() {
        final String directory = homeDirectory + "/Music";

        try (Stream<Path> stream = Files.list(Paths.get(directory))) {
            return stream
                .filter(file -> !Files.isDirectory(file))
                .filter(this::isMp3)
                .map(this::audioFromPath)
                .toList();
        } catch (IOException e) {
            log.error("Error getting filenames: dir={} error={}", directory, e.getMessage(), e);
            return emptyList();
        }
    }

    private boolean isMp3(Path path) {
        return path.getFileName().toString().lastIndexOf(".mp3") != -1;
    }

    private Audio audioFromPath(Path path) {
        return new Audio(
            path.getFileName().toString(),
            path.toAbsolutePath().toString()
        );
    }
}
