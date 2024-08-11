package br.com.schumaker.octopus.app;

import br.com.schumaker.octopus.framework.annotations.Service;

@Service
public class ProductService {
    public void save() {
        System.out.println("Product saved!");
    }
}
