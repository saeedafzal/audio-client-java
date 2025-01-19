package com.saeed.audio.controllers;

import com.saeed.audio.model.responses.AudioListResponse;
import com.saeed.audio.model.responses.AudioListResponse.Audio;
import com.saeed.audio.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/load/music")
    public AudioListResponse getAudioFromDirectory() {
        log.info("Getting audio list from music directory.");
        List<Audio> names = dashboardService.filenamesFromMusicDirectory();
        log.info("Returning {} filenames.", names.size());
        return new AudioListResponse(names);
    }

    @GetMapping("/load")
    public Resource getAudioFileFromPath(@RequestParam String path) {
        log.info("Getting audio file from path={}", path);
        return new FileSystemResource(path);
    }
}
