package org.example.demo.ticket.consumer.contract.dao;

import org.example.demo.ticket.model.bean.ticket.Ticket;
import org.example.demo.ticket.model.bean.ticket.TicketStatut;
import org.example.demo.ticket.model.exception.TechnicalException;
import org.example.demo.ticket.model.recherche.ticket.RechercheTicket;

import java.util.List;

public interface TicketDao {

    public int getCountTicket(RechercheTicket pRechercheTicket);

    public List<TicketStatut> getListStatut();

    public void updateTicketStatut(TicketStatut pTicketStatut);

    public void insertTicketStatut(TicketStatut pTicketStatut);

    public void updateTicket(Ticket pTicket) throws TechnicalException;

}
