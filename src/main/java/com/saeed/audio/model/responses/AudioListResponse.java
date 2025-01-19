package com.saeed.audio.model.responses;

import java.util.List;

public record AudioListResponse(List<Audio> audioList) {

    public record Audio(String name, String path) {
    }
}
