package com.biblioteca_autismo.response;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class ResponseRest {

    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();

    public void setMetadata(String tipo, String codigo, String date) {
        HashMap<String, String> mapa = new HashMap<String, String>();
        mapa.put("tipo", tipo);
        mapa.put("codigo", codigo);
        mapa.put("date", date);

        metadata.add(mapa);
    }
}
