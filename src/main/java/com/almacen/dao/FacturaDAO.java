package com.almacen.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.almacen.model.Factura;
import com.almacen.model.Pedido;

/*
 * Esta clase es el Data Access Object (DAO) de Factura
 */
/**
 * @version: 17/04/2021/A
 * @author SebasCoGo
 */

public class FacturaDAO {

	private int contadorId = 1;


	/**
	 * Este método se encarga de generar una factura, cuando un pedido se guarda o se modifica
	 * @param Pedido y Booleano que indica si se es un nuevo pedido o es la modificación de uno existente
	 * @return Factura generada
	*/
	public Factura generarFacturaNueva(Pedido pedido, Boolean isCreacion) {
		Factura factura = new Factura();
		Double valorImpuestoIva = 0.0;
		Double valorDomicilio = 0.0;
		if(isCreacion) {
			factura.setFechaCreacion(pedido.getFechaCreacion());
			factura.setFechaPago(calcularFechaPagoFactura(pedido.getFechaCreacion(), 1));
		}else {
			factura.setFechaCreacion(pedido.getFechaModificacion());
			factura.setFechaPago(calcularFechaPagoFactura(pedido.getFechaModificacion(), 1));
		}
		factura.setId(""+contadorId);
		factura.setPedido(pedido);
		
		if( pedido.getTotal() > 70000 && pedido.getTotal() <= 100000) {
			valorImpuestoIva = pedido.getTotal()*0.19;
			valorDomicilio = 5000.0;
			factura.setObservacion("El valor de su pedido supera los 70.000 pesos y es menor o igual a "
					+ "100.000 pesos por lo que se le cobra el impuesto iva del 19% y el valor del domicilio ($5.000 pesos) ");
		}else if(pedido.getTotal() > 100000) {
			valorImpuestoIva = pedido.getTotal()*0.19;
			factura.setObservacion("El valor de su pedido supera los 100.000 pesos por lo que se le cobra el impuesto iva del 19%"
					+ " y el valor del domicilio es de 0 pesos ");
		}else {
			factura.setObservacion("El valor de su pedido es menor o igual a 70.000 pesos por lo que no se le cobra impuesto IVA "
					+ "ni domicilio");
		}
		factura.setValorDomicilio(valorDomicilio);
		factura.setValorImpuestoIva(valorImpuestoIva);
		factura.setValorProductos(pedido.getTotal());
		factura.setValorTotal(pedido.getTotal()+valorDomicilio+valorImpuestoIva);
		contadorId ++;
		return factura;
		
	}
	
	
	/**
	 * Este método se encarga de generar una factura, cuando un pedido se elimina un pedido con mas de 12 horas de creación y se debe cobrar el 10% del total del pedido
	 * @param Pedido a eliminar y fecha de la solicitud de la eliminacíon
	 * @return Factura generada
	*/
	public Factura generarFacturaPorCancelarPedido(Pedido pedido, String fechaEliminacion) {
		Factura factura = new Factura();
		factura.setId(""+contadorId);
		factura.setFechaCreacion(fechaEliminacion);
		factura.setPedido(pedido);
		factura.setValorDomicilio(0.0);
		factura.setValorImpuestoIva(0.0);
		factura.setValorProductos(0.0);
		factura.setFechaPago(calcularFechaPagoFactura(fechaEliminacion, 1));
		factura.setValorTotal(pedido.getTotal()*.1);
		factura.setObservacion("---- PEDIDO ELIMINADO ---- Por eliminar el pedido despues de 12 horas de su creación debe pagar un 10% del valor del mismo");
		contadorId ++;
		return factura;
	}
	
	
	/**
	 * Este método se encarga de generar una fecha de pago de la factura (Un mes despues, de la creación, modificacón o eliminacón (si aplica))
	 * @param Fecha de creación, modificación o eliminación.
	 * @return String con la fecha de pago
	*/
	private String calcularFechaPagoFactura(String fechaCreacion, int numMeses) {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Calendar calFechaCreacion = Calendar.getInstance();
		try {
			calFechaCreacion.setTime(formatter.parse(fechaCreacion));
		} catch (ParseException e) {
			System.err.println("Ocurrio un error convirtiendo String a Calendar: ");
			e.printStackTrace();
		}
		Calendar calFechaPago = calFechaCreacion;
		calFechaPago.add(Calendar.MONTH, numMeses);
		return formatter.format(calFechaPago.getTime());
	}

}
