package net.stawrul.services.exceptions;

/**
 * Wyjątek sygnalizujący niepoprawnosc zamowien wg kryteriow prowadzacego.
 *
 * Wystąpienie wyjątku z hierarchii RuntimeException w warstwie biznesowej
 * powoduje wycofanie transakcji (rollback).
 */
public class IllegalOrderExeception extends RuntimeException {
}