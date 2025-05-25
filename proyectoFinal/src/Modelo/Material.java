/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;

public class Material implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String titulo;
    private String autor;
    private long ano;
    private boolean esta;
    
    public Material(String titulo, String autor, long ano) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
    }
    
    
    
    // Getters y Setters
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
    
    
    @Override
    public String toString() {
        return "Titulo: " + titulo + ", Autor: " + autor + " " + ", Año: " + ano;
    }
}

