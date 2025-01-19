package com.saeed.audio.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class PathYtDlpRepository extends YtDlpRepository {

    @Override
    public boolean canRun() {
        return checkYtDlpVersion("yt-dlp");
    }

    @Override
    public boolean canDownload() {
        return false;
    }

    @Override
    public boolean download() {
        return false;
    }
}
