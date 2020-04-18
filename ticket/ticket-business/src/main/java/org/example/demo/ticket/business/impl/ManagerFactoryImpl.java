package org.example.demo.ticket.business.impl;

import org.example.demo.ticket.business.contract.ManagerFactory;
import org.example.demo.ticket.business.contract.manager.ProjetManager;
import org.example.demo.ticket.business.contract.manager.TicketManager;
import org.example.demo.ticket.business.impl.manager.ProjetManagerImpl;
import org.example.demo.ticket.business.impl.manager.TicketManagerImpl;

import javax.inject.Inject;
import javax.inject.Named;

/*
    @ManagedBean (JSR-250), @Named (JSR-330) ou @Component (Spring)
    => La classe se signale en tant que bean injectable
 */
@Named("managerFactory")
public class ManagerFactoryImpl implements ManagerFactory {

    /*
        @Inject (JSR-330) ou @Autowired (Spring)
     */
    @Inject
    private ProjetManager projetManager;

    @Inject
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