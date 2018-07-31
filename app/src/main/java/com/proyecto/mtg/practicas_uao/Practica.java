package com.proyecto.mtg.practicas_uao;

import android.media.Image;
import android.util.Log;

import java.util.Date;

/**
 * Created by mateo on 5/19/2018.
 */

public class Practica {

    private String codigo;
    private String nom_empres;
    private String fecha_inicio;
    private String fecha_fin;
    private String requisitos;
    private String programas;
    private String fotografia;
    private String sede;



    public Practica(String codigo, String nom_empres, String fecha_inicio, String fecha_fin, String requisitos, String programas, String fotografia, String sede) {
        this.codigo = codigo;
        this.nom_empres = nom_empres;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.requisitos = requisitos;
        this.programas = programas;
        this.fotografia = fotografia;
        this.sede = sede;
    }

    public Practica() {
    }

    public String getcodigo() {
        return codigo;
    }

    public void setcodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getnom_empres() {
        return nom_empres;
    }

    public void setnom_empres(String nom_empres) {
        this.nom_empres = nom_empres;
    }

    public String getfecha_inicio() {
        return fecha_inicio;
    }

    public void setfecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getfecha_fin() {
        return fecha_fin;
    }

    public void setfecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getProgramas() {
        return programas;
    }

    public String getSede() {
        return sede;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public void setSede(String sede) {

        this.sede = sede;
    }

    @Override
    public String toString() {

        String msg = "Empresa:\t" + nom_empres + "\n" +
                "Programa:\t" + programas + "\n" +
                "Fecha de inicio:\t" + fecha_inicio + "\n" +
                "Fecha de fin:\t" + fecha_fin + "\n" +
                "Requisitos:\t" + requisitos;

        return msg;
    }


}
