package com.almacen.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.almacen.dao.PedidoDAO;
import com.almacen.model.Factura;
import com.almacen.model.Pedido;

/*
 * Esta clase es el RestController con sus correspondientes endpoints
 */
/**
 * @version: 17/04/2021/A
 * @author SebasCoGo
 */

@RestController
@RequestMapping("almacen")
public class AlmacenService {
	
	private PedidoDAO pedidoDAO = new PedidoDAO();
	HttpHeaders headers = new HttpHeaders();
	
	/**
	 * Endpoint para guradar los pedidos 
	 * Este método se encarga de recibir el pedidio y enviarlo a DAO para guardarlo
	 * @param Pedido que solicita el cliente
	 * @return Respuesta con un header default y el body con la factura generada
	*/
	@PostMapping("/pedido/guardar")
	public ResponseEntity<Factura> guardarPedido(@RequestBody Pedido pedido){
		return ResponseEntity.ok()
                .headers(headers)
                .body(pedidoDAO.guardarPedido(pedido));
	}
	
	
	/**
	 * Endpoint para editar los pedidos 
	 * Este método se encarga de recibir el pedidio editado y enviarlo a DAO, validar y actulaizar si cumple con las condiciones requeridas
	 * @param Pedido que el cliente quiere editar
	 * @return Respuesta con un header default y el body con la factura generada (si fue actulizado)
	*/
	@PutMapping("/pedido/actualizar")
	public ResponseEntity<Factura> actualizarPedido(@RequestBody Pedido pedido) {
		return ResponseEntity.ok()
                .headers(headers)
                .body(pedidoDAO.actulaizarPedido(pedido));
	}
	
	
	/**
	 * Endpoint para eliminar los pedidos 
	 * Este método se encarga de recibir el pedidio a eliminar y enviarlo a DAO, validar y eliminar
	 * @param Id del pedido a eiliminar y fecha de sollicitud de la eliminación
	 * @return Respuesta con un header default y el body con la factura generada (si han pasado mas de 12 horas despues de la creación)
	*/
	@DeleteMapping("/pedido/eliminar")
	public ResponseEntity<Factura> eliminarPedido(@RequestBody HashMap<String, String> mapEiminar) {
		return ResponseEntity.ok()
                .headers(headers)
                .body(pedidoDAO.eliminarPedido(mapEiminar.get("id"), mapEiminar.get("fechaEliminacion")));
	}
	
	
	/**
	 * Endpoint para listar los pedidos
	 * Este método se encarga de llamar al DAO para traer la lista de pedidos
	 * @param 
	 * @return Lista de pedidos
	*/
	@GetMapping("/pedido/listar")
	public ArrayList<Pedido> listarPedidos(){
		return pedidoDAO.getPedidosArrayList();
	}
	
	
	/**
	 * Endpoint para listar las facturas
	 * Este método se encarga de llamar al DAO para traer la lista de facturas
	 * @param 
	 * @return Lista de facturas
	*/
	@GetMapping("/factura/listar")
	public ArrayList<Factura> listarFacturas(){
		return pedidoDAO.getFacturaArrayList();
	}

}
