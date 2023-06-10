package AubergeInn.tuples;

import java.sql.*;

/**
 * Permet de représenter un tuple de la table membre.
 * 
 * <pre>
 * Marc Frappier
 * Université de Sherbrooke
 * Version 2.0 - 13 novembre 2004
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Vincent Ducharme
 * Université de Sherbrooke
 * Version 3.0 - 17 juin 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * </pre>
 */

public class TupleReservation
{
    private int idReservation;
    private int idLivre;
    private int idMembre;
    private Date dateReservation;
    
    public TupleReservation()
    {
    }
    
    public TupleReservation(int idReservation, int idLivre, int idMembre, Date dateReservation)
    {
        this.setIdReservation(idReservation);
        this.setIdLivre(idLivre);
        this.setIdMembre(idMembre);
        this.setDateReservation(dateReservation);
    }
    
    public int getIdReservation()
    {
        return idReservation;
    }

    public void setIdReservation(int idReservation)
    {
        this.idReservation = idReservation;
    }

    public int getIdLivre()
    {
        return idLivre;
    }

    public void setIdLivre(int idLivre)
    {
        this.idLivre = idLivre;
    }

    public int getIdMembre()
    {
        return idMembre;
    }

    public void setIdMembre(int idMembre)
    {
        this.idMembre = idMembre;
    }

    public Date getDateReservation()
    {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation)
    {
        this.dateReservation = dateReservation;
    }
}
