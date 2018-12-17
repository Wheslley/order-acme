package br.com.acme.order.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.acme.order.model.OrderItemAcme;
import br.com.acme.order.repository.OrderItemAcmeRepository;

@RestController
public class OrderItemAcmeResource {
	
	@Autowired
	private OrderItemAcmeRepository orderItemAcmeRepository;

	@RequestMapping(value = "/orderItem/findAll", method = RequestMethod.GET)
	public ResponseEntity<List<OrderItemAcme>> findAll() {

		List<OrderItemAcme> listaOrderItem = this.orderItemAcmeRepository.findAll();

		if (listaOrderItem == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<OrderItemAcme>>(listaOrderItem, HttpStatus.OK);

	}
	
}
