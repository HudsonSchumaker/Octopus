package br.com.schumaker.octopus.app.view;

import br.com.schumaker.octopus.framework.annotations.validations.Max;
import br.com.schumaker.octopus.framework.annotations.validations.NotBlank;
import br.com.schumaker.octopus.framework.annotations.validations.NotEmpty;

public record ProductDTO(@NotEmpty String name, @NotBlank String description, @Max(22.5) double price) {}
