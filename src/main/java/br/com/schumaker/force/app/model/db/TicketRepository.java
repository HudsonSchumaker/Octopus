package br.com.schumaker.force.app.model.db;

import br.com.schumaker.force.app.model.Ticket;
import br.com.schumaker.force.framework.ioc.annotations.db.Repository;
import br.com.schumaker.force.framework.jdbc.SqlCrud;

import java.util.UUID;

@Repository
public interface TicketRepository extends SqlCrud<UUID, Ticket> {}
