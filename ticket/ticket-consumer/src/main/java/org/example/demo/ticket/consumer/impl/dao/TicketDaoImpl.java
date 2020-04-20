package org.example.demo.ticket.consumer.impl.dao;

import org.example.demo.ticket.consumer.impl.rowmapper.ticket.TicketStatutRM;
import org.example.demo.ticket.model.bean.ticket.TicketStatut;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.example.demo.ticket.consumer.contract.dao.TicketDao;
import org.example.demo.ticket.model.recherche.ticket.RechercheTicket;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.inject.Named;
import java.sql.Types;
import java.util.List;

@Named
public class TicketDaoImpl extends AbstractDaoImpl implements TicketDao {

    @Override
    public int getCountTicket(RechercheTicket pRechercheTicket) {
        // il faut ajouter les tests sur la nullité de pRechercheTicket,
        // les critères non renseignés... Voir Bloc 3 au-dessous
        /*
        String vSQL
                = "SELECT COUNT(*) FROM ticket"
                + " WHERE auteur_id = ?"
                + "   AND projet_id = ?";

        JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());
        int vNbrTickets = vJdbcTemplate.queryForObject(
                vSQL, Integer.class,
                pRechercheTicket.getAuteurId(),
                pRechercheTicket.getProjetId());
         */

        /*
        String vSQL
                = "SELECT COUNT(*) FROM ticket"
                + " WHERE auteur_id = :auteur_id"
                + "   AND projet_id = :projet_id";

        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

        MapSqlParameterSource vParams = new MapSqlParameterSource();
        vParams.addValue("auteur_id", pRechercheTicket.getAuteurId());
        vParams.addValue("projet_id", pRechercheTicket.getProjetId());

        int vNbrTicket = vJdbcTemplate.queryForObject(vSQL, vParams, Integer.class);
         */


        MapSqlParameterSource vParams = new MapSqlParameterSource();

        StringBuilder vSQL = new StringBuilder("SELECT COUNT(*) FROM ticket WHERE 1=1");

        if (pRechercheTicket != null) {
            if (pRechercheTicket.getAuteurId() != null) {
                vSQL.append(" AND auteur_id = :auteur_id");
                vParams.addValue("auteur_id", pRechercheTicket.getAuteurId());
            }
            if (pRechercheTicket.getProjetId() != null) {
                vSQL.append(" AND projet_id = :projet_id");
                vParams.addValue("projet_id", pRechercheTicket.getProjetId());
            }
        }

        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
        int vNbrTicket = vJdbcTemplate.queryForObject(vSQL.toString(), vParams, Integer.class);

        return vNbrTicket;
    }


    @Override
    public List<TicketStatut> getListStatut() {
        String vSQL = "SELECT * FROM public.statut";

        JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

        RowMapper<TicketStatut> vRowMapper = new TicketStatutRM();

        List<TicketStatut> vListStatut = vJdbcTemplate.query(vSQL, vRowMapper);
        return vListStatut;
    }


    @Override
    public void updateTicketStatut(TicketStatut pTicketStatut) {
        String vSQL = "UPDATE statut SET libelle = :libelle WHERE id = :id";

        //1ère méthode
        /*
        MapSqlParameterSource vParams = new MapSqlParameterSource();
        vParams.addValue("id", pTicketStatut.getId(), Types.INTEGER);
        vParams.addValue("libelle", pTicketStatut.getLibelle(), Types.VARCHAR);
         */

        // 2ème méthode
        //SqlParameterSource vParams = new BeanPropertySqlParameterSource(pTicketStatut);
        BeanPropertySqlParameterSource vParams = new BeanPropertySqlParameterSource(pTicketStatut);
        vParams.registerSqlType("id", Types.INTEGER);
        vParams.registerSqlType("libelle", Types.VARCHAR);

        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

        int vNbrLigneMaj = vJdbcTemplate.update(vSQL, vParams);
        System.out.println("Nombre de lignes changées: " + vNbrLigneMaj);
    }


    @Override
    public void insertTicketStatut(TicketStatut pTicketStatut) {
        String vSQL = "INSERT INTO statut (id, libelle) VALUES (:id, :libelle)";
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

        BeanPropertySqlParameterSource vParams = new BeanPropertySqlParameterSource(pTicketStatut);
        vParams.registerSqlType("id", Types.INTEGER);
        vParams.registerSqlType("libelle", Types.VARCHAR);

        try {
            vJdbcTemplate.update(vSQL, vParams);
        } catch (DuplicateKeyException vEx) {
            //LOGGER.error("Le TicketStatut existe déjà ! id=" + pTicketStatut.getId(), vEx);
            // ...
            System.out.println("Le TicketStatut existe déjà ! id=" + pTicketStatut.getId());
        }
    }
}
