package net.stawrul;

import net.stawrul.model.Book;
import net.stawrul.model.Cd;
import net.stawrul.model.Film;
import net.stawrul.model.Order;
import net.stawrul.services.OrdersService;
import net.stawrul.services.exceptions.IllegalOrderExeception;
import net.stawrul.services.exceptions.NotFoundProductException;
import net.stawrul.services.exceptions.OutOfStockException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class OrdersServiceTest {

    @Mock
    EntityManager em;

    @Test(expected = OutOfStockException.class)
    public void whenOrderedBookNotAvailable_placeOrderThrowsOutOfStockEx() {
        //Arrange
        Order order = new Order();
        Book book = new Book();
        book.setAmount(0);
        order.getOrderedBooks().add(book);

        Mockito.when(em.find(Book.class, book.getId())).thenReturn(book);

        OrdersService ordersService = new OrdersService(em);

        //Act
        ordersService.placeOrder(order);
        //Assert - exception expected
    }

    @Test(expected = OutOfStockException.class)
    public void whenOrderedFilmNotAvailable_placeOrderThrowsOutOfStockEx() {
        //Arrange
        Order order = new Order();
        Film film = new Film();
        film.setAmount(0);
        order.getOrderedFilms().add(film);

        Mockito.when(em.find(Film.class, film.getId())).thenReturn(film);

        OrdersService ordersService = new OrdersService(em);

        //Act
        ordersService.placeOrder(order);
        //Assert - exception expected
    }

    @Test(expected = OutOfStockException.class)
    public void whenOrderedCdNotAvailable_placeOrderThrowsOutOfStockEx() {
        //Arrange
        Order order = new Order();
        Cd cd = new Cd();
        cd.setAmount(0);
        order.getOrderedCds().add(cd);

        Mockito.when(em.find(Cd.class, cd.getId())).thenReturn(cd);

        OrdersService ordersService = new OrdersService(em);

        //Act
        ordersService.placeOrder(order);
        //Assert - exception expected
    }

    @Test(expected = IllegalOrderExeception.class)
    public void whenOrderedCdAndFilm_placeOrderThrowsIllegalOrderEx(){
        //Arrange
        Order order = new Order();
        Cd cd = new Cd();
        cd.setAmount(1);
        Film film = new Film();
        film.setAmount(1);
        order.getOrderedCds().add(cd);
        order.getOrderedFilms().add(film);

        Mockito.when(em.find(Cd.class,cd.getId())).thenReturn(cd);
        Mockito.when(em.find(Film.class,film.getId())).thenReturn(film);

        OrdersService ordersService = new OrdersService(em);
        //Act
        ordersService.placeOrder(order);
        //Assert - exception expected
    }

    @Test(expected = IllegalOrderExeception.class)
    public void whenOrdered2TheSameCds_placeOrderThrowsIllegalOrderEx(){
        //Arrange
        Order order = new Order();
        Cd cd = new Cd();
        cd.setAmount(2);
        order.getOrderedCds().add(cd);
        order.getOrderedCds().add(cd);
        Mockito.when(em.find(Cd.class,cd.getId())).thenReturn(cd);
        OrdersService ordersService = new OrdersService(em);
        //Act
        ordersService.placeOrder(order);
        //Assert - exception expected
    }

    @Test(expected = IllegalOrderExeception.class)
    public void whenOrdered2TheSameFilms_placeOrderThrowsIllegalOrderEx(){
        //Arrange
        Order order = new Order();
        Film film = new Film();
        film.setAmount(2);
        order.getOrderedFilms().add(film);
        order.getOrderedFilms().add(film);
        Mockito.when(em.find(Film.class,film.getId())).thenReturn(film);
        OrdersService ordersService = new OrdersService(em);
        //Act
        ordersService.placeOrder(order);
        //Assert - exception expected
    }

    @Test(expected = IllegalOrderExeception.class)
    public void whenOrdered2TheSameBooks_placeOrderThrowsIllegalOrderEx(){
        //Arrange
        Order order = new Order();
        Book book = new Book();
        book.setAmount(2);
        order.getOrderedBooks().add(book);
        order.getOrderedBooks().add(book);
        Mockito.when(em.find(Book.class,book.getId())).thenReturn(book);
        OrdersService ordersService = new OrdersService(em);
        //Act
        ordersService.placeOrder(order);
        //Assert - exception expected
    }

    @Test(expected = NotFoundProductException.class)
    public void whenOrderedBookIsNotEvenSaved_placeOrderThrowsNotFoundProductEx()
    {
        //Arrange
        Order order = new Order();
        Book book = new Book();
        order.getOrderedBooks().add(book);
        OrdersService ordersService = new OrdersService(em);
        //Act
        ordersService.placeOrder(order);
        //Assert - exception expected
    }
    @Test(expected = NotFoundProductException.class)
    public void whenOrderedFilmIsNotEvenSaved_placeOrderThrowsNotFoundProductEx()
    {
        //Arrange
        Order order = new Order();
        Film film = new Film();
        order.getOrderedFilms().add(film);
        OrdersService ordersService = new OrdersService(em);
        //Act
        ordersService.placeOrder(order);
        //Assert - exception expected
    }
    @Test(expected = NotFoundProductException.class)
    public void whenOrderedCdIsNotEvenSaved_placeOrderThrowsNotFoundProductEx()
    {
        //Arrange
        Order order = new Order();
        Cd cd = new Cd();
        order.getOrderedCds().add(cd);
        OrdersService ordersService = new OrdersService(em);
        //Act
        ordersService.placeOrder(order);
        //Assert - exception expected
    }
    @Test
    public void whenOrderedBookAvailable_placeOrderDecreasesAmountByOne() {
        //Arrange
        Order order = new Order();
        Book book = new Book();
        book.setAmount(1);
        order.getOrderedBooks().add(book);

        Mockito.when(em.find(Book.class, book.getId())).thenReturn(book);

        OrdersService ordersService = new OrdersService(em);

        //Act
        ordersService.placeOrder(order);

        //Assert
        //dostępna liczba książek zmniejszyła się:
        assertEquals(0, (int)book.getAmount());
        //nastąpiło dokładnie jedno wywołanie em.persist(order) w celu zapisania zamówienia:
        Mockito.verify(em, times(1)).persist(order);
    }

    @Test
    public void whenOrderedFilmAvailable_placeOrderDecreasesAmountByOne() {
        //Arrange
        Order order = new Order();
        Film film = new Film();
        film.setAmount(1);
        order.getOrderedFilms().add(film);

        Mockito.when(em.find(Film.class, film.getId())).thenReturn(film);

        OrdersService ordersService = new OrdersService(em);

        //Act
        ordersService.placeOrder(order);

        //Assert
        //dostępna liczba książek zmniejszyła się:
        assertEquals(0, (int)film.getAmount());
        //nastąpiło dokładnie jedno wywołanie em.persist(order) w celu zapisania zamówienia:
        Mockito.verify(em, times(1)).persist(order);
    }

    @Test
    public void whenOrderedCdAvailable_placeOrderDecreasesAmountByOne() {
        //Arrange
        Order order = new Order();
        Cd cd = new Cd();
        cd.setAmount(1);
        order.getOrderedCds().add(cd);

        Mockito.when(em.find(Cd.class, cd.getId())).thenReturn(cd);

        OrdersService ordersService = new OrdersService(em);

        //Act
        ordersService.placeOrder(order);

        //Assert
        //dostępna liczba książek zmniejszyła się:
        assertEquals(0, (int)cd.getAmount());
        //nastąpiło dokładnie jedno wywołanie em.persist(order) w celu zapisania zamówienia:
        Mockito.verify(em, times(1)).persist(order);
    }


    @Test
    public void whenGivenLowercaseString_toUpperReturnsUppercase() {

        //Arrange
        String lower = "abcdef";

        //Act
        String result = lower.toUpperCase();

        //Assert
        assertEquals("ABCDEF", result);
    }
}
