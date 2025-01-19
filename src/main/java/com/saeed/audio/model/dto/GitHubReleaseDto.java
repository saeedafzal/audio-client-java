package com.saeed.audio.model.dto;

import java.util.List;
import java.util.Locale;

public record GitHubReleaseDto(List<Asset> assets) {

    public record Asset(String name, String browser_download_url) {
    }

    // TODO: Support other OS
    public Asset getAssetFromOS() {
        final String os = System.getProperty("os.name", "unknown").toLowerCase(Locale.ENGLISH);
        final String search = os.contains("nux") ? "linux" : null;
        return search != null ? assets.stream()
            .filter(a -> a.name.contains(search))
            .findFirst()
            .orElse(null) : null;
    }
}
