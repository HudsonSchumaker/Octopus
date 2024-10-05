package br.com.schumaker.force.app.view.mapper;

import br.com.schumaker.force.app.model.Ticket;
import br.com.schumaker.force.app.view.TicketForm;
import br.com.schumaker.force.framework.ioc.annotations.bean.Component;
import br.com.schumaker.force.framework.model.ModelViewMapper;

import java.time.LocalDateTime;

@Component
public class TicketForm2Ticket implements ModelViewMapper<TicketForm, Ticket> {

        @Override
        public Ticket from(TicketForm source) {
            return new Ticket(
                    source.id(),
                    source.firstName(),
                    source.lastName(),
                    source.email(),
                    source.countryCode(),
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
        }
}
