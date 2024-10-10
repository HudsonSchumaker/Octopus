package br.com.schumaker.force.app.view;

import java.time.LocalDateTime;

public record TicketDTO(String ticketId, String firstName, String lastName, String email, String country,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {}
