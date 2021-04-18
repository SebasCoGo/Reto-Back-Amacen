package com.almacen.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.almacen.model.Factura;
import com.almacen.model.Pedido;
import com.almacen.model.Producto;

/*
 * Esta clase es el Data Access Object (DAO) de Pedido
 */
/**
 * @version: 17/04/2021/A
 * @author SebasCoGo
 */

public class PedidoDAO {

	FacturaDAO facturaDAO = new FacturaDAO();
	private int contadorId = 1;

	//ArrayList Que simulan la base de datos
	private ArrayList<Pedido> pedidosArrayList = new ArrayList<Pedido>();
	private ArrayList<Factura> facturaArrayList = new ArrayList<Factura>();
	
	
	/**
	 * Este método se encarga de guardar el pedido en el ArrayList que simulan la BD
	 * @param Pedido que solicita el cliente
	 * @return Factura generada
	*/
	public Factura guardarPedido(Pedido pedido) {
		Factura factura = new Factura();
		pedido.setId("" + contadorId);
		pedido.setTotal(calcularValorPedido(pedido));
		pedidosArrayList.add(pedido);
		factura = facturaDAO.generarFacturaNueva(pedido, true);
		guardarFactura(factura);
		contadorId++;
		return factura;
	}

	/**
	 * Este método se encarga de Actualizar el pedido (si cumple con las condiciones) en el ArrayList que simulan la BD
	 * @param Pedido que solicita editar el cliente
	 * @return Factura generada
	*/
	public Factura actulaizarPedido(Pedido pedidoModificado) {
		int index = -1;
		Factura factura = new Factura();
		Pedido pedidoAnterior = new Pedido();
		pedidoModificado.setTotal(calcularValorPedido(pedidoModificado));
		for (int i = 0; i < pedidosArrayList.size(); ++i) {
			if (pedidosArrayList.get(i).getId().equals(pedidoModificado.getId())) {
				index = i;
				pedidoAnterior = pedidosArrayList.get(i);
			}

		}
		if (index != -1
				&& validarTiempoTranscurrido(pedidoModificado.getFechaModificacion(), pedidoAnterior.getFechaCreacion(),
						5)
				&& validarValorTotalPedidoModificadoMayorIgualPedidoAnterior(pedidoModificado, pedidoAnterior)) {
			pedidosArrayList.set(index, pedidoModificado);
			factura = facturaDAO.generarFacturaNueva(pedidoModificado, false);
			factura.setObservacion("---- PEDIDO MODIFICADO ---- " + factura.getObservacion());
			guardarFactura(factura);

		} else {
			if (!validarTiempoTranscurrido(pedidoModificado.getFechaModificacion(), pedidoAnterior.getFechaCreacion(),
					5)) {
				factura.setObservacion("Despues de 5 horas de la creacón el pedido no puede ser modificado");
			} else if (!validarValorTotalPedidoModificadoMayorIgualPedidoAnterior(pedidoModificado, pedidoAnterior)) {
				factura.setObservacion(
						"Para modificar el pedido los productos selecionados deben costar igual o mas que los anteriores");
			} else {
				// Pedido no existe
				factura.setObservacion("ID de pedido no existe");
			}

		}
		return factura;

	}

	/**
	 * Este método se encarga de Eliminar el pedido en el ArrayList que simula la BD
	 * @param Id del pedido a eiliminar y fecha de sollicitud de la eliminación
	 * @return Factura generada (si han pasado mas de 12 horas despues de la creación)
	*/
	public Factura eliminarPedido(String id, String fechaEliminacion) {
		Pedido pedidoEliminar = new Pedido();
		for (Pedido pedido : pedidosArrayList) {
			if (pedido.getId().equals(id))
				pedidoEliminar = pedido;
		}
		
		Factura factura = new Factura();
		if (validarTiempoTranscurrido(fechaEliminacion, pedidoEliminar.getFechaCreacion(), 12)) {
			pedidosArrayList.remove(pedidoEliminar);
			factura.setObservacion("---- PEDIDO ELIMINADO ----");
		} else {
			factura = facturaDAO.generarFacturaPorCancelarPedido(pedidoEliminar, fechaEliminacion);
			guardarFactura(factura);
		}

		pedidosArrayList.remove(pedidoEliminar);
		return factura;

	}

	/**
	 * Este método se encarga de calcular el valor total del pedido sumando el valor de todos los productos del pedido
	 * @param Pedido solicitado
	 * @return Valor total del pedido
	*/
	private Double calcularValorPedido(Pedido pedido) {
		Double totaPedido = 0.0;
		for (Producto producto : pedido.getProductosArrayList()) {
			totaPedido += producto.getValor() * producto.getCantidad();
		}

		return totaPedido;
	}

	/**
	 * Este método se encarga de validar cuento tiempo ha transcurido despues de la creacion del pedido (para solicitudes de editar o eliminar pedido)
	 * @param fecha de solicitud de modificaciónn o eliminación del pedido, fecha en que fue creado el pedido y nuemro de horas permitidas para poder modificar o eliminar
	 * @return Booleano que indica si cumple con el requisito de fecha(horas para este caso)
	*/
	private Boolean validarTiempoTranscurrido(String fechaModificacion, String fechaCreacion, int horasPermitidas) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calFechaModificacion = Calendar.getInstance();
		Calendar calFechaCreacion = Calendar.getInstance();
		try {
			calFechaModificacion.setTime(formatter.parse(fechaModificacion));
			calFechaCreacion.setTime(formatter.parse(fechaCreacion));
		} catch (ParseException e) {
			System.err.println("Ocurrio un error convirtiendo String a Calendar: ");
			e.printStackTrace();
		}
		Calendar calFechaPermitida = calFechaCreacion;
		calFechaPermitida.add(Calendar.HOUR, +horasPermitidas);
		if (calFechaModificacion.after(calFechaPermitida)) {
			// No Pasa filtro
			return false;

		} else {
			// Pasa filtro
			return true;
		}

	}
	

	/**
	 * Este método se encarga de validar que en una modificación de pedido los productos selecionados deben costar igual o mas que los anteriores
	 * @param pedido modificado y pedido anterior (pedido que se quiere modificar)
	 * @return Booleano que indica si cumple con el requisito del valor total maoy o igual
	*/
	private Boolean validarValorTotalPedidoModificadoMayorIgualPedidoAnterior(Pedido pedidoModificado,
			Pedido pedidoAterior) {
		if (pedidoModificado.getTotal() >= pedidoAterior.getTotal()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Este método se encarga de guardar la Fuctura en el ArrayList que simulan la BD
	 * @param Factura a guardar
	 * @return void
	*/
	private void guardarFactura(Factura fcatura) {
		facturaArrayList.add(fcatura);
	}

	public ArrayList<Pedido> getPedidosArrayList() {
		return pedidosArrayList;
	}

	public ArrayList<Factura> getFacturaArrayList() {
		return facturaArrayList;
	}

}
