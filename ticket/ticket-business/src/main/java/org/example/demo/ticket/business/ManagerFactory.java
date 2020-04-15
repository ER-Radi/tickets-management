package org.example.demo.ticket.business;

import org.example.demo.ticket.business.manager.ProjetManager;
import org.example.demo.ticket.business.manager.TicketManager;

public class ManagerFactory {

    private ProjetManager projetManager;
    private TicketManager ticketManager;

    public ProjetManager getProjetManager() {
        return new ProjetManager();
    }

    public void setProjetManager(ProjetManager pProjetManager) {
        projetManager = pProjetManager;
    }

    public TicketManager getTicketManager() {
        return new TicketManager();
    }

    public void setTicketManager(TicketManager tTicketManager) {
        ticketManager = tTicketManager;
    }

}
