package com.almacen.model;

import java.util.ArrayList;

/*
 * Esta clase simula el Entity de Pedido
 */
/**
 * @version: 17/04/2021/A
 * @author SebasCoGo
 */

public class Pedido {
	private String id;
	private Cliente cliente ;
	private String fechaCreacion;
	private String fechaModificacion;
	private Double total;
	private ArrayList<Producto> productosArrayList = new ArrayList<Producto>();
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public ArrayList<Producto> getProductosArrayList() {
		return productosArrayList;
	}
	public void setProductosArrayList(ArrayList<Producto> productosArrayList) {
		this.productosArrayList = productosArrayList;
	}
	
	@Override 
	public String toString() {
		return "Id: " + this.id + this.cliente.getNombre();
		
	}
	

}
