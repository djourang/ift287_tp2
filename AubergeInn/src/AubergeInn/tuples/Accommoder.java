package AubergeInn.tuples;

import java.sql.Date;

/**
 * Permet de representer un tuple d'une chambre Accommoder de l'auberge.
 */

public class Accommoder {


    private int idChambre;
    private int idCommodite;
    private Date dateFin;
    private Date  dateDebut;

    public Accommoder() {}

    public Accommoder(int idChambre,int idCommodite, Date dateFin,Date dateDebut) {
        this.idChambre=idChambre;
        this.idCommodite=idCommodite;
        this.dateDebut=dateDebut;
        this.dateFin=dateFin;
    }

  
}
