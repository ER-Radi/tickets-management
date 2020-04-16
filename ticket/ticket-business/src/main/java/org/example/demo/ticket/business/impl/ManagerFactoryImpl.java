package org.example.demo.ticket.business.impl;

import org.example.demo.ticket.business.contract.ManagerFactory;
import org.example.demo.ticket.business.contract.manager.ProjetManager;
import org.example.demo.ticket.business.contract.manager.TicketManager;
import org.example.demo.ticket.business.impl.manager.ProjetManagerImpl;
import org.example.demo.ticket.business.impl.manager.TicketManagerImpl;

public class ManagerFactoryImpl implements ManagerFactory {

    private ProjetManager projetManager;
    private TicketManager ticketManager;

    @Override
    public ProjetManager getProjetManager() {
        return new ProjetManagerImpl();
    }

    @Override
    public void setProjetManager(ProjetManager pProjetManager) {
        projetManager = pProjetManager;
    }

    @Override
    public TicketManager getTicketManager() {
        return new TicketManagerImpl();
    }

    @Override
    public void setTicketManager(TicketManager tTicketManager) {
        ticketManager = tTicketManager;
    }

}