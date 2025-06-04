package com.biblioteca_autismo.service;

import com.biblioteca_autismo.model.Videos;
import com.biblioteca_autismo.response.VideosResponseRest;
import org.springframework.http.ResponseEntity;

public interface IVideosService {

    public ResponseEntity<VideosResponseRest> InsertarVideo (Videos videos);

    public ResponseEntity<VideosResponseRest> ObtenerVideos();

    public ResponseEntity<VideosResponseRest> EliminarVideos (Long id);
}
