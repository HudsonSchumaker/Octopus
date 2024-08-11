package br.com.schumaker.octopus.ioc;

public interface ManagedClass {

    String getFqn();
    Object getInstance();
}
