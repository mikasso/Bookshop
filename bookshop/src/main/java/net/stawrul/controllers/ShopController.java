package net.stawrul.controllers;

import javassist.NotFoundException;
import net.stawrul.model.Order;
import net.stawrul.services.OrdersService;
import net.stawrul.services.exceptions.IllegalOrderExeception;
import net.stawrul.services.exceptions.NotFoundProductException;
import net.stawrul.services.exceptions.OutOfStockException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;


@RestController
public class ShopController {

    final OrdersService ordersService;

    public ShopController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/orders")
    public List<Order> listOrders() {
        return ordersService.findAll();
    }


    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable UUID id) {
        Order order = ordersService.find(id);
        return isNull(order) ? ResponseEntity.notFound().build() : ResponseEntity.ok(order);
    }


    @PostMapping("/orders")
    public ResponseEntity<Void> addOrder(@RequestBody Order order, UriComponentsBuilder uriBuilder) {
        try {
            ordersService.placeOrder(order);
            URI location = uriBuilder.path("/orders/{id}").buildAndExpand(order.getId()).toUri();
            return ResponseEntity.created(location).build();

        } catch (OutOfStockException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
        catch (IllegalOrderExeception e){
            return ResponseEntity.badRequest().build();
        }
        catch (NotFoundProductException e)
        {
            return ResponseEntity.notFound().build();
        }
    }
}
