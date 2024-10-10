package br.com.schumaker.force.app.view.mapper;

import br.com.schumaker.force.app.model.Ticket;
import br.com.schumaker.force.app.view.TicketDTO;
import br.com.schumaker.force.framework.ioc.annotations.bean.Component;
import br.com.schumaker.force.framework.model.ModelViewMapper;
import br.com.schumaker.force.framework.web.view.Page;
import br.com.schumaker.force.framework.web.view.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Ticket2TicketDTO implements ModelViewMapper<Ticket, TicketDTO> {

    @Override
    public TicketDTO from(Ticket ticket) {
        return new TicketDTO(
                ticket.getTicketId().toString(),
                ticket.getFirstName(),
                ticket.getLastName(),
                ticket.getEmail(),
                ticket.getCountry(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
        );
    }

    public Page<TicketDTO> from(Page<Ticket> ticketPage) {
        List<TicketDTO> content = ticketPage.getContent().stream()
                .map(this::from)
                .collect(Collectors.toList());

        return new PageImpl<>(
                content,
                ticketPage.getPageNumber(),
                ticketPage.getPageSize(),
                ticketPage.getTotalElements()
        );
    }
}
