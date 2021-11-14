package com.company;

import api.GrafoTDA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ExtraerCaminos {
    private String path;
    private GrafoTDA grafo;
    private BufferedReader br = null;
    private String line;
    private final String SEPARATOR = ";";


    public ExtraerCaminos(String path, GrafoTDA grafo){
        this.path = path;
        this.grafo = grafo;
    }

    public void cargarCaminos () throws IOException {
        try {

            br = new BufferedReader(new FileReader(path));

            br.readLine();
            line = br.readLine();
            while(null!=line) {
                String [] fields = line.split(SEPARATOR);
                grafo.agregarCamino(Float.parseFloat(fields[3]), Float.parseFloat(fields[2]), fields[0], fields[1]);
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (null!=br) {
                br.close();
            }
        }

    }
}
