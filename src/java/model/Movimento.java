/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author PhelipeRocha
 */
public class Movimento implements Serializable {
    private int id;
    private String categoria;
    private String nome;
    private double valor;
    private String data;
    private boolean frequencia;

    public Movimento(int id, String categoria, String nome, double valor, String data, boolean frequencia) {
        this.id = id;
        this.categoria = categoria;
        this.nome = nome;
        this.valor = valor;
        this.data = data;
        this.frequencia = frequencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isFrequencia() {
        return frequencia;
    }

    public void setFrequencia(boolean frequencia) {
        this.frequencia = frequencia;
    }
    
    
            
}
