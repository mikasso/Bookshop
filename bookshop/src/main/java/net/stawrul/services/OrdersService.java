package net.stawrul.services;

import net.stawrul.model.Order;
import net.stawrul.model.Product;
import net.stawrul.services.exceptions.IllegalOrderExeception;
import net.stawrul.services.exceptions.NotFoundProductException;
import net.stawrul.services.exceptions.OutOfStockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;

@Service
public class OrdersService extends EntityService<Order> {

    public OrdersService(EntityManager em) {
        super(em, Order.class, Order::getId);
    }

    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }


    @Transactional
    public void placeOrder(Order order) {
        if(!order.getOrderedCds().isEmpty() && !order.getOrderedFilms().isEmpty())
            throw new IllegalOrderExeception();
        TreeSet<UUID> IDs = new TreeSet<UUID>();


            List<Product> products = new ArrayList<Product>();
            products.addAll(order.getOrderedBooks());
            products.addAll(order.getOrderedCds());
            products.addAll(order.getOrderedFilms());

        for (Product productStub :products) {
            Class< ? extends Product > c = productStub.getClass();
            Product product = em.find(c, productStub.getId());
            if(product == null)
                throw new NotFoundProductException();
            if (IDs.add(productStub.getId()) == false)       //sprawdzenie czy element sie powtarza
                throw new IllegalOrderExeception();          //to znaczy nie da sie go dodac do drzewa zawierajacego unikalne wartosci

            if (product.getAmount() < 1) {

                throw new OutOfStockException();
            } else {
                int newAmount = product.getAmount() - 1;
                product.setAmount(newAmount);
            }
        }

         save(order);
    }




}
