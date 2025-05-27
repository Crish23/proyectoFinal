/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;

public class Material implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String titulo;
    private String autor;
    private long ano;
    private boolean esta;
    
    public Material(String id, String titulo, String autor, long ano, boolean esta) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.esta = esta;
    }
    
    
    
    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public long getAno() {
        return ano;
    }
    
    public void setAno(long ano) {
        this.ano = ano;
    }
    
    public boolean getEsta(){
        return esta;
    }

    public void setEsta(boolean esta){
        this.esta = esta;
    }
    
    @Override
    public String toString() {
        return "id" + id + ", Titulo: " + titulo + ", Autor: " + autor + " " + ", Año: " + ano;
    }
}

