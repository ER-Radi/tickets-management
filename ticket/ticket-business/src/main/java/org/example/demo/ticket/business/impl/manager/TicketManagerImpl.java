package org.example.demo.ticket.business.impl.manager;

import java.util.ArrayList;
import java.util.List;

import org.example.demo.ticket.business.contract.manager.TicketManager;
import org.example.demo.ticket.model.bean.projet.Projet;
import org.example.demo.ticket.model.bean.ticket.*;
import org.example.demo.ticket.model.bean.utilisateur.Utilisateur;
import org.example.demo.ticket.model.exception.NotFoundException;
import org.example.demo.ticket.model.exception.TechnicalException;
import org.example.demo.ticket.model.recherche.ticket.RechercheTicket;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * Manager des beans du package Ticket.
 *
 * @author lgu
 */
public class TicketManagerImpl extends AbstractManager implements TicketManager {


    // But: Elcapsuler le traitement de changerStatut dans une transaction
    // Méthode 1: Tout est automatisé par Spring
    @Override
    public void changerStatut(Ticket pTicket, TicketStatut pNewStatut,
                              Utilisateur pUtilisateur, Commentaire pCommentaire) {

        TransactionTemplate vTransactionTemplate = new TransactionTemplate(getPlatformTransactionManager());

        // l'ajout du type de la propagation (la propagation par défaut est: PROPAGATION_REQUIRED)
        vTransactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        vTransactionTemplate.setTimeout(30); // 30 secondes

        // exécuter le code dans un context transactionnel
        vTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus pTransactionStatus) {
                pTicket.setStatut(pNewStatut);
                getDaoFactory().getTicketDao().updateTicket(pTicket);    // TODO ...
                // TODO Ajout de la ligne d'historique + commentaire ...

                /*
                    Effectuer l'annulation manuellement
                 */
                /*
                TicketStatut vOldStatut = pTicket.getStatut();
                pTicket.setStatut(pNewStatut);

                try {
                    getDaoFactory().getTicketDao().updateTicket(pTicket);    // TODO ...
                    // TODO Ajout de la ligne d'historique + commentaire ...
                } catch (TechnicalException vEx) {
                    pTransactionStatus.setRollbackOnly();
                    pTicket.setStatut(vOldStatut);
                }
                */

            }
        });
    }

    @Override
    public HistoriqueStatut changerStatut2(Ticket pTicket, TicketStatut pNewStatut,
                                           Utilisateur pUtilisateur, Commentaire pCommentaire) {

        TransactionTemplate vTransactionTemplate = new TransactionTemplate(getPlatformTransactionManager());

        HistoriqueStatut vHistoriqueStatut = vTransactionTemplate.execute(
                new TransactionCallback<HistoriqueStatut>() {
                    @Override
                    public HistoriqueStatut doInTransaction(TransactionStatus transactionStatus) {
                        pTicket.setStatut(pNewStatut);
                        getDaoFactory().getTicketDao().updateTicket(pTicket);

                        HistoriqueStatut vHistoriqueStatut = new HistoriqueStatut();
                        // TODO Ajout de la ligne d'historique + commentaire ...
                        return vHistoriqueStatut;
                    }
                }
        );
        return vHistoriqueStatut;
    }


    // Méthode 2: Pas d'automatisation: c'est à vous de « demander » l'ouverture/validation/annulation
    // de la transaction, mais Spring qui s'occupe de faire le nécessaire.
    @Override
    public void changerStatut3(Ticket pTicket, TicketStatut pNewStatut,
                               Utilisateur pUtilisateur, Commentaire pCommentaire) {


        // l'ajout du type de la propagation (la propagation par défaut est: PROPAGATION_REQUIRED)
        // Avec le type PROPAGATION_REQUIRES_NEW, quand vous demandez l'ouverture d'une transaction,
        // une nouvelle transaction physique est toujours créée, peu importe si une transaction physique existe déjà ou pas.
        // Avec le type PROPAGATION_REQUIRED, si une transaction physique est déjà en cours, alors elle est utilisée.
        // Sinon, une nouvelle transaction est ouverte.
        DefaultTransactionDefinition vDefinition = new DefaultTransactionDefinition();
        vDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        vDefinition.setTimeout(30);  // 30 secondes


        TransactionStatus vTransactionStatus =
                getPlatformTransactionManager().getTransaction(vDefinition);
        try {
            // le traitement transactionnel ...
            pTicket.setStatut(pNewStatut);
            getDaoFactory().getTicketDao().updateTicket(pTicket);
            // TODO : Ajout de la ligne d'historique + commentaire ...

            TransactionStatus vTScommit = vTransactionStatus;
            vTransactionStatus = null;
            getPlatformTransactionManager().commit(vTScommit);
        } finally {
            if(vTransactionStatus != null) {
                getPlatformTransactionManager().rollback(vTransactionStatus);
            }
        }

    }



    @Override
    public Ticket getTicket(Long pNumero) throws NotFoundException {
        // Je n'ai pas encore codé la DAO
        // Je mets juste un code temporaire pour commencer le cours...
        if (pNumero < 1L) {
            throw new NotFoundException("Ticket non trouvé : numero=" + pNumero);
        }
        Evolution vEvolution = new Evolution(pNumero);
        vEvolution.setPriorite(10);
        return vEvolution;
    }


    @Override
    public List<Ticket> getListTicket(RechercheTicket pRechercheTicket) {
        // Je n'ai pas encore codé la DAO
        // Je mets juste un code temporaire pour commencer le cours...
        List<Ticket> vList = new ArrayList<>();
        if (pRechercheTicket.getProjetId() != null) {
            Projet vProjet = new Projet(pRechercheTicket.getProjetId());
            for (int vI = 0; vI < 4; vI++) {
                Ticket vTicket = new Bug((long) pRechercheTicket.getProjetId() * 10 + vI);
                vTicket.setProjet(vProjet);
                vList.add(vTicket);
            }
        } else {
            for (int vI = 0; vI < 9; vI++) {
                Ticket vTicket = new Evolution((long) vI);
                vList.add(vTicket);
            }
        }
        return vList;
    }


    @Override
    public int getCountTicket(RechercheTicket pRechercheTicket) {
        // Je n'ai pas encore codé la DAO
        // Je mets juste un code temporaire pour commencer le cours...
        return 42;
    }
}
