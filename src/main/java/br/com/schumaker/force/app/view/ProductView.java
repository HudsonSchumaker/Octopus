package br.com.schumaker.force.app.view;

import java.math.BigInteger;

/**
 * The ProductView class.
 *
 * @param id
 * @param name
 * @param description
 * @param price
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public record ProductView(BigInteger id, String name, String description, double price) {}
