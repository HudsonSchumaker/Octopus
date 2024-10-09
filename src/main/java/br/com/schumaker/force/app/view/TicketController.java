package br.com.schumaker.force.app.view;

import br.com.schumaker.force.app.exception.TicketException;
import br.com.schumaker.force.app.model.Ticket;
import br.com.schumaker.force.app.service.TicketService;
import br.com.schumaker.force.app.view.mapper.Ticket2TicketDTO;
import br.com.schumaker.force.app.view.mapper.TicketForm2Ticket;
import br.com.schumaker.force.framework.ioc.annotations.controller.*;
import br.com.schumaker.force.framework.ioc.annotations.validations.Validate;
import br.com.schumaker.force.framework.web.view.Page;
import br.com.schumaker.force.framework.web.view.ResponseView;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *  The TicketController class.
 *  It is responsible for controlling the ticket operations.
 *
 * @see Controller
 * @see Get
 * @see Put
 * @see Post
 * @see Delete
 * @see Payload
 * @see Validate
 * @see PathVariable
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Controller("/ticket")
public class TicketController {

    private final TicketService service;
    private final TicketForm2Ticket ticketForm2Ticket;
    private final Ticket2TicketDTO ticket2TicketDTO;

    public TicketController(TicketService service, TicketForm2Ticket ticketForm2Ticket, Ticket2TicketDTO ticket2TicketDTO) {
        this.service = service;
        this.ticketForm2Ticket = ticketForm2Ticket;
        this.ticket2TicketDTO = ticket2TicketDTO;
    }

    @Get
    public ResponseView<Page<TicketDTO>> list() {
        var ticketPage = service.list();
        var ticketDTOPage = ticket2TicketDTO.from(ticketPage);

        return ResponseView.ok()
                .body(ticketDTOPage)
                .build();
    }

    @Post
    public ResponseView<TicketDTO> create(@Payload @Validate TicketForm ticketForm) {
        var ticket = ticketForm2Ticket.from(ticketForm);
        var ticketDTO = ticket2TicketDTO.from(service.create(ticket));

        return ResponseView.created()
                .body(ticketDTO)
                .build();
    }

    @Get("/{id}")
    public ResponseView<TicketDTO> getById(@PathVariable UUID id) {
        var ticket = service.getByTicketId(id).orElseThrow(() -> new TicketException("Ticket not found, " + id));
        var ticketDTO = ticket2TicketDTO.from(ticket);

        return ResponseView.ok()
                .body(ticketDTO)
                .build();
    }

    @Get("/search")
    public ResponseView<List<TicketDTO>> search(@QueryParam(value = "email", required = false) String email,
                                                @QueryParam(value = "ticketId", required = false) UUID ticketId) {
        List<Ticket> tickets;
        if (!email.isBlank()) {
            tickets = service.getByEmail(email);
            if (tickets.isEmpty()) {
                throw new TicketException("No tickets found for email: " + email);
            }
        } else if (ticketId != null) {
            Ticket ticket = service.getByTicketId(ticketId).orElseThrow(() -> new TicketException("Ticket not found for ticketId: " + ticketId));
            tickets = List.of(ticket);
        } else {
            throw new TicketException("Either email or ticketId must be provided.");
        }

        var ticketDTOs = tickets.stream().map(ticket2TicketDTO::from).toList();
        return ResponseView.ok().body(ticketDTOs).build();
    }

    @Put("/{id}")
    public ResponseView<TicketDTO> update(@PathVariable UUID id, @Payload @Validate TicketForm ticketForm) {
        var updatedTicket = ticketForm2Ticket.from(ticketForm);
        var ticketDTO = ticket2TicketDTO.from(service.update(id, updatedTicket));

        return ResponseView.accepted()
                .body(ticketDTO)
                .build();
    }

    @Patch("/{id}")
    public ResponseView<TicketDTO> patch(@PathVariable UUID id, @Payload Map<String, Object> patch) {
        var ticketDTO = ticket2TicketDTO.from(service.patch(id, patch));

        return ResponseView.accepted()
                .body(ticketDTO)
                .build();
    }

    @Delete("/{id}")
    public ResponseView<Void> delete(@PathVariable UUID id) {
        service.delete(id);

        return ResponseView.noContent()
                .build();
    }
}
