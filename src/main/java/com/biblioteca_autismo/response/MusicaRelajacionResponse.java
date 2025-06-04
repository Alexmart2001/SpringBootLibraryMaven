package com.biblioteca_autismo.response;

import com.biblioteca_autismo.model.MusicaRelajacion;
import lombok.Data;

import java.util.List;

@Data
public class MusicaRelajacionResponse {

    private List<MusicaRelajacion> relajacion;
}
