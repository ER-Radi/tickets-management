package org.example.demo.ticket.business.contract.manager;

import org.example.demo.ticket.model.bean.ticket.Commentaire;
import org.example.demo.ticket.model.bean.ticket.HistoriqueStatut;
import org.example.demo.ticket.model.bean.ticket.Ticket;
import org.example.demo.ticket.model.bean.ticket.TicketStatut;
import org.example.demo.ticket.model.bean.utilisateur.Utilisateur;
import org.example.demo.ticket.model.exception.NotFoundException;
import org.example.demo.ticket.model.recherche.ticket.RechercheTicket;

import java.util.List;

public interface TicketManager {

    public void changerStatut(Ticket pTicket, TicketStatut pNewStatut,
                              Utilisateur pUtilisateur, Commentaire pCommentaire);

    public HistoriqueStatut changerStatut2(Ticket pTicket, TicketStatut pNewStatut,
                                          Utilisateur pUtilisateur, Commentaire pCommentaire);

    public void changerStatut3(Ticket pTicket, TicketStatut pNewStatut,
                              Utilisateur pUtilisateur, Commentaire pCommentaire);

    /**
     * Cherche et renvoie le {@link Ticket} numéro {@code pNumero}
     *
     * @param pNumero le numéro du Ticket
     * @return Le {@link Ticket}
     * @throws NotFoundException Si le Ticket n'est pas trouvé
     */
    Ticket getTicket(Long pNumero) throws NotFoundException;

    /**
     * Renvoie la liste des {@link Ticket} correspondants aux critères de recherche.
     *
     * @param pRechercheTicket -
     * @return List
     */
    List<Ticket> getListTicket(RechercheTicket pRechercheTicket);

    /**
     * Renvoie le nombre de {@link Ticket} correspondants aux critères de recherche.
     *
     * @param pRechercheTicket -
     * @return int
     */
    int getCountTicket(RechercheTicket pRechercheTicket);
}
