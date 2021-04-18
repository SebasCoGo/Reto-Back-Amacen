package com.almacen.model;

/*
 * Esta clase simula el Entity de Factura
 */
/**
 * @version: 17/04/2021/A
 * @author SebasCoGo
 */

public class Factura {
	private String id;
	private Pedido pedido;
	private String observacion;
	private String fechaCreacion;
	private String fechaPago;
	private Double valorImpuestoIva;
	private Double valorProductos;
	private Double valorDomicilio;
	private Double valorTotal;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Pedido getPedido() {
		return pedido;
	}
	
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	public Double getValorProductos() {
		return valorProductos;
	}
	public void setValorProductos(Double valorProductos) {
		this.valorProductos = valorProductos;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Double getValorImpuestoIva() {
		return valorImpuestoIva;
	}
	public void setValorImpuestoIva(Double valorImpuestoIva) {
		this.valorImpuestoIva = valorImpuestoIva;
	}
	public Double getValorDomicilio() {
		return valorDomicilio;
	}
	public void setValorDomicilio(Double valorDomicilio) {
		this.valorDomicilio = valorDomicilio;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	

}
