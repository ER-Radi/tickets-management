package org.example.demo.ticket.business.impl.manager;

import org.example.demo.ticket.business.contract.manager.ProjetManager;
import org.example.demo.ticket.model.bean.projet.Projet;
import org.example.demo.ticket.model.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager des beans du package Projet.
 *
 * @author lgu
 */
public class ProjetManagerImpl implements ProjetManager {

    @Override
    public Projet getProjet(Integer pId) throws NotFoundException {
        // Je n'ai pas encore codé la DAO
        // Je mets juste un code temporaire pour commencer le cours...
        if (pId < 1) {
            throw new NotFoundException("Projet non trouvé : ID=" + pId);
        }
        Projet vProjet = new Projet(pId);
        vProjet.setNom("Projet n°" + pId);
        return vProjet;
    }


    @Override
    public List<Projet> getListProjet() {
        // Je n'ai pas encore codé la DAO
        // Je mets juste un code temporaire pour commencer le cours...
        List<Projet> vList = new ArrayList<>();
        for (int vI = 0; vI < 9; vI++) {
            Projet vProjet = new Projet(vI);
            vProjet.setNom("Projet n°" + vI);
            vList.add(vProjet);
        }
        return vList;
    }
}
