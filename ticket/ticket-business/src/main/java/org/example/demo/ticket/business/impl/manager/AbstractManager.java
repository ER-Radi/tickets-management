package org.example.demo.ticket.business.impl.manager;

import org.example.demo.ticket.consumer.contract.DaoFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Inject;
import javax.inject.Named;

public abstract class AbstractManager {

    private DaoFactory daoFactory;

    @Inject
    @Named("txManagerTicket")
    private PlatformTransactionManager platformTransactionManager;

    protected DaoFactory getDaoFactory() {
        return daoFactory;
    }

    public void setDaoFactory(DaoFactory pDaoFactory) {
        this.daoFactory = pDaoFactory;
    }

    public PlatformTransactionManager getPlatformTransactionManager() {
        return platformTransactionManager;
    }
}
