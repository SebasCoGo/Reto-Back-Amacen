package com.almacen.model;

/*
 * Esta clase simula el Entity de Pedido
 */
/**
 * @version: 17/04/2021/A
 * @author SebasCoGo
 */

public class Producto {
	private String id;
	private String nombre;
	private Double valor;
	private int cantidad;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	

}
