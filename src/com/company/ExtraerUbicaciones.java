package com.company;

import TDA.Grafo;
import api.GrafoTDA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ExtraerUbicaciones {
    private String path;
    private GrafoTDA grafo;
    private BufferedReader br = null;
    private String line;
    private final String SEPARATOR = ";";


    public ExtraerUbicaciones(String path, GrafoTDA grafo){
        this.path = path;
        this.grafo = grafo;
    }

    public void cargarUbicaciones () throws IOException {
        try {
            br = new BufferedReader(new FileReader(path));
            br.readLine();
            line = br.readLine();
            //Agrega centro distribucion
            String [] central = line.split(SEPARATOR);
            grafo.agregarVertice(central[0], central[1], 0, 0);
            line = br.readLine();

            while(null!=line) {
                String [] fields = fields = line.split(SEPARATOR);
                grafo.agregarVertice(fields[0], fields[1], Integer.parseInt(fields[4])*60, Integer.parseInt(fields[5])*60);
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
