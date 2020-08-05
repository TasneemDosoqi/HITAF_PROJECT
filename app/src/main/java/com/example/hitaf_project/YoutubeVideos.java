package com.example.hitaf_project;

public class YoutubeVideos {

    String videoUrl;

    // Class to retrieve videos from YouTube, includes constructors and getter
    public YoutubeVideos() {
    }

    public YoutubeVideos(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
