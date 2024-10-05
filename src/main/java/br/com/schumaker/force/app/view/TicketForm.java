package br.com.schumaker.force.app.view;

import br.com.schumaker.force.framework.ioc.annotations.validations.Email;
import br.com.schumaker.force.framework.ioc.annotations.validations.NotEmpty;

import java.util.UUID;

public record TicketForm(UUID id, @NotEmpty String firstName, @NotEmpty String lastName, @Email String email, @NotEmpty String countryCode) {}
