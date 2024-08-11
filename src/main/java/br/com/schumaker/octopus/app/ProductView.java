package br.com.schumaker.octopus.app;

import java.math.BigInteger;

public record ProductView(BigInteger id, String name, String description, double price) {}
