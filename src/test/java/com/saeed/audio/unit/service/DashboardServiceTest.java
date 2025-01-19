package com.saeed.audio.unit.service;

import com.saeed.audio.model.responses.AudioListResponse.Audio;
import com.saeed.audio.service.DashboardService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.createDirectory;
import static java.nio.file.Files.createFile;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @TempDir
    private Path tempDir;

    private DashboardService dashboardService;

    @BeforeEach
    void setup() {
        System.setProperty("user.home", tempDir.toString());
        dashboardService = new DashboardService();
    }

    @Test
    void testIfMusicDirectoryDoesNotExistReturnsEmptyList() {
        List<Audio> audio = dashboardService.filenamesFromMusicDirectory();
        assertThat(audio).isEmpty();
    }

    @Test
    @SneakyThrows
    void testWithMusicDirectoryReturnsAudioList() {
        Path musicDirectory = tempDir.resolve("Music");
        createDirectory(musicDirectory);
        final Path audio1 = musicDirectory.resolve("audio-1.mp3");
        final Path audio3 = musicDirectory.resolve("audio-3.mp3");
        createFile(audio1);
        createFile(musicDirectory.resolve("audio-2.ogg"));
        createFile(audio3);

        List<Audio> actual = dashboardService.filenamesFromMusicDirectory();
        assertThat(actual).isEqualTo(List.of(
            new Audio("audio-1.mp3", audio1.toString()),
            new Audio("audio-3.mp3", audio3.toString())));
    }
}
