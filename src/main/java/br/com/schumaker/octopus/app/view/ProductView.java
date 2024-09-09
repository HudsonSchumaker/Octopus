package br.com.schumaker.octopus.app.view;

import java.math.BigInteger;

/**
 * The ProductView class.
 * @param id
 * @param name
 * @param description
 * @param price
 */
public record ProductView(BigInteger id, String name, String description, double price) {}
