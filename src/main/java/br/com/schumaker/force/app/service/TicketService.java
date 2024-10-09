package br.com.schumaker.force.app.service;

import br.com.schumaker.force.app.exception.TicketException;
import br.com.schumaker.force.app.model.Ticket;
import br.com.schumaker.force.app.model.db.TicketRepository;
import br.com.schumaker.force.framework.ioc.annotations.bean.Service;
import br.com.schumaker.force.framework.ioc.reflection.validation.EmailValidator;
import br.com.schumaker.force.framework.model.PatchHelper;
import br.com.schumaker.force.framework.web.view.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository repository;

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public Page<Ticket> list() {
        return repository.findAll();
    }

    public Ticket create(Ticket newTicket) {
        var id = repository.save(newTicket);
        if (id.isEmpty()) {
            throw new TicketException("Error creating ticket.");
        } else {
            return repository.findById(id.get()).orElseThrow();
        }
    }

    public Optional<Ticket> getByTicketId(UUID ticketId) {
        return repository.findByTicketId(ticketId.toString());
    }

    public List<Ticket> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<Ticket> getByCountry(String country) {
        return repository.findByCountry(country);
    }

    public Ticket update(UUID ticketId, Ticket updatedTicket) {
        var existingTicket = this.getByTicketId(ticketId).orElse(null);
        if (existingTicket == null) {
            throw new TicketException("Ticket not found, " + ticketId);
        }

        existingTicket.setFirstName(updatedTicket.getFirstName());
        existingTicket.setLastName(updatedTicket.getLastName());
        existingTicket.setEmail(updatedTicket.getEmail());
        existingTicket.setCountry(updatedTicket.getCountry());
        existingTicket.setUpdatedAt(LocalDateTime.now());

        repository.update(existingTicket);
        return this.getByTicketId(ticketId).orElseThrow();
    }

    public Ticket patch(UUID ticketId, Map<String, Object> patch) {
        var existingTicket = this.getByTicketId(ticketId).orElse(null);
        if (existingTicket == null) {
            throw new TicketException("Ticket not found, " + ticketId);
        }

        patch.remove("id");
        patch.remove("ticketId");
        patch.remove("createdAt");
        patch.remove("updatedAt");

        if (patch.get("email") != null) {
            if (!EmailValidator.isValidEmail((String) patch.get("email"))) {
                throw new TicketException("Invalid email: " +  patch.get("email") + " ticketId: " + ticketId);
            }
        }

        PatchHelper.applyPatch(existingTicket, patch);
        existingTicket.setUpdatedAt(LocalDateTime.now());

        repository.update(existingTicket);
        return this.getByTicketId(ticketId).orElseThrow();
    }

    public void delete(UUID ticketId) {
        var existingTicket = this.getByTicketId(ticketId).orElse(null);
        if (existingTicket == null) {
            throw new TicketException("Ticket not found, " + ticketId);
        }
        repository.delete(existingTicket);
    }
}
