package br.com.schumaker.force.app.model.db;

import br.com.schumaker.force.app.model.Ticket;
import br.com.schumaker.force.framework.ioc.annotations.db.Repository;
import br.com.schumaker.force.framework.jdbc.SqlCrud;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends SqlCrud<BigInteger, Ticket> {
    Optional<Ticket> findByTicketId(String ticketId);
    List<Ticket> findByEmail(String email);
    List<Ticket> findByCountry(String country);
}
