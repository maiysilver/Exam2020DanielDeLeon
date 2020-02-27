package com.example.examdanieldeleonmairena;

import java.io.Serializable;

public class TodoListt implements Serializable {

    private Integer id;
    private String texto;
    private String fecha_ini;
    private String fecha_fin;
    private Integer checkbox;

    public TodoListt(Integer id, String tex, String fecha_in, String fecha_fi, Integer check) {
        this.id = id;
        this.texto = tex;
        this.fecha_ini = fecha_in;
        this.fecha_fin = fecha_fi;
        this.checkbox = check;
    }
    public TodoListt(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFecha_ini() {
        return fecha_ini;
    }

    public void setFecha_ini(String fecha_ini) {
        this.fecha_ini = fecha_ini;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Integer getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(Integer checkbox) {
        this.checkbox = checkbox;
    }
}
