package com.biblioteca_autismo.controller;

import com.biblioteca_autismo.model.Videos;
import com.biblioteca_autismo.response.VideosResponseRest;
import com.biblioteca_autismo.service.IVideosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class VideosController {

    @Autowired
    private IVideosService service;

    @PostMapping("/Videos")
    public ResponseEntity<VideosResponseRest> AddNewVideo(@RequestBody Videos videos) {
        ResponseEntity<VideosResponseRest> response = service.InsertarVideo(videos);
        return response;
    }

    @GetMapping("/Videos")
    public ResponseEntity<VideosResponseRest> getAllVideos() {
        ResponseEntity<VideosResponseRest> response = service.ObtenerVideos();
        return response;
    }

    @DeleteMapping("/Videos/{id}")
    public ResponseEntity<VideosResponseRest> DeleteVideo(@PathVariable Long id) {
        ResponseEntity<VideosResponseRest> response = service.EliminarVideos(id);
        return response;
    }
}
