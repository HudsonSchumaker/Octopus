package br.com.schumaker.force.app.model;

import br.com.schumaker.force.framework.ioc.annotations.db.Column;
import br.com.schumaker.force.framework.ioc.annotations.db.Pk;
import br.com.schumaker.force.framework.ioc.annotations.db.Table;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The Ticket class, it represents a Ticket on the DB.
 *
 * @see Pk
 * @see Table
 * @see Column
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Table("tickets")
public class Ticket {

    @Pk
    private BigInteger id;

    @Column("ticket_id")
    private UUID ticketId;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column
    private String email;

    @Column
    private String country;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    public Ticket() {}

    public Ticket(BigInteger id, UUID ticketId, String firstName, String lastName, String email, String country, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ticketId = ticketId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
