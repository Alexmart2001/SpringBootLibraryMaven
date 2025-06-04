package com.biblioteca_autismo.response;

import com.biblioteca_autismo.model.Videos;
import lombok.Data;

import java.util.List;

@Data
public class VideosResponse{

    private List<Videos> videos;
}
