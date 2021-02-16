package net.stawrul.controllers;

import net.stawrul.model.Book;
import net.stawrul.model.Product;
import net.stawrul.services.BooksService;
import net.stawrul.services.CdsService;
import net.stawrul.services.FilmsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequestMapping("/products")
public class ProductsController {


    final BooksService  booksService;
    final CdsService cdsService;
    final FilmsService filmsService;

    public ProductsController(BooksService  booksService, CdsService cdsService, FilmsService filmsService) {
        this.cdsService = cdsService;
        this.booksService = booksService;
        this.filmsService = filmsService;
    }

    @GetMapping
    public List<Product> listProducts() {
        List<Product> L = new ArrayList<Product>();
        L.addAll(booksService.findAll());
        L.addAll(filmsService.findAll());
        L.addAll(cdsService.findAll());
        return L;
    }

    @GetMapping("/price_less_than/{price}")
    public List<Product> listProductsPrice(@PathVariable int price) {
        List<Product> L = new ArrayList<Product>();
        L.addAll(booksService.findAll());
        L.addAll(filmsService.findAll());
        L.addAll(cdsService.findAll());

        for(Product p : L){
            if(p.getPrice() > price){
                L.remove(p);
            }
        }
        return L;
    }
        
    @GetMapping("/name/{name}")
    public List<Product> productsByName(String name){
        List<Product> L = listProducts();
        for(Product p : L)
        {
            String productName = p.getTitle();
            if(name.contains(productName))
            {
                listProducts().add(p);
            }
        }
        return listProducts();
    }
}