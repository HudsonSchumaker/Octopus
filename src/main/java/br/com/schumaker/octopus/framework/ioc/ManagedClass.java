package br.com.schumaker.octopus.framework.ioc;

public interface ManagedClass {

    String getFqn();
    Object getInstance();
}
