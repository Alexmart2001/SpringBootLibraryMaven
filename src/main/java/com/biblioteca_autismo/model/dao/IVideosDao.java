package com.biblioteca_autismo.model.dao;

import com.biblioteca_autismo.model.Videos;
import com.biblioteca_autismo.response.VideosResponseRest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

public interface IVideosDao extends CrudRepository<Videos,Long> {
}
