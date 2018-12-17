package br.com.acme.order.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.acme.order.model.OrderAcme;
import br.com.acme.order.model.OrderItemAcme;
import br.com.acme.order.repository.OrderAcmeRepository;
import br.com.acme.order.repository.OrderItemAcmeRepository;

@RestController
public class OrderAcmeResource {

	@Autowired
	private OrderAcmeRepository orderRepository;

	@Autowired
	private OrderItemAcmeRepository orderItemAcmeRepository;

	@RequestMapping(value = "/order/findAll", method = RequestMethod.GET)
	public ResponseEntity<List<OrderAcme>> findAll() {

		List<OrderAcme> listaOrder = this.orderRepository.findAll();

		if (listaOrder == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<OrderAcme>>(listaOrder, HttpStatus.OK);

	}

	@RequestMapping(value = "/order/findById", method = RequestMethod.POST)
	public ResponseEntity<OrderAcme> findById(@RequestParam("id") Integer id) {

		Optional<OrderAcme> order = this.orderRepository.findById(id);

		if (order == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<OrderAcme>(order.get(), HttpStatus.OK);

	}

	@RequestMapping(value = "/order/findByStatus", method = RequestMethod.POST)
	public ResponseEntity<List<OrderAcme>> findByStatus(@RequestParam("status") String status) {

		List<OrderAcme> listaOrder = this.orderRepository.findByStatus(status);

		if (listaOrder == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<OrderAcme>>(listaOrder, HttpStatus.OK);

	}

	@RequestMapping(value = "/order/insertOrder", method = RequestMethod.POST)
	public ResponseEntity<String> insertOrder(@RequestParam("address") String address,
			@RequestParam("data") String data, @RequestParam("status") String status,
			@RequestParam("itens") Integer[] itens) {

		List<OrderItemAcme> listaOrderItemAcme = new ArrayList<>();
		OrderAcme orderAcme = new OrderAcme();
		
		for (Integer item : itens) {
			Optional<OrderItemAcme> optionalOrderItemAcme = this.orderItemAcmeRepository.findById(item);
			listaOrderItemAcme.add(optionalOrderItemAcme.get());
		}
		
		if (listaOrderItemAcme.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			
			Date inputDate = null;
			
			try {
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				inputDate = dateFormat.parse(data);
				
				orderAcme.setAddress(address);
				orderAcme.setData(inputDate);
				orderAcme.setStatus(status);
				orderAcme.getOrderItemAcme().addAll(listaOrderItemAcme);
				
				this.orderRepository.save(orderAcme);
				
			} catch (ParseException e) {
				e.printStackTrace();
				return new ResponseEntity<String>("Error!", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("Insert order success!", HttpStatus.OK);
	}

}
