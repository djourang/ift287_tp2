package AubergeInn.tables;

import AubergeInn.bdd.Connexion;
import AubergeInn.tuples.TupleReservation;

import java.sql.*;

/**
 * Permet d'effectuer les accès à la table reservation.
 * 

 */

public class TableReservations
{

	
    public PreparedStatement stmtClientAvecReservation;
    public PreparedStatement stmtChambreDansReservation;
    public PreparedStatement stmtReserver;

    //public PreparedStatement stmtChambreDansReservationAvecPeriode;

	
	
    private final Connexion cx;

    /**
     * Creation d'une instance.
     */
    public TableReservations(Connexion cx) throws SQLException
    {
        this.cx = cx;
        // initialisation des transaction concernant le Reservation
        stmtChambreDansReservation = cx.getConnection().prepareStatement("select * from Reservation where IdChambre = ?");
        stmtClientAvecReservation = cx.getConnection().prepareStatement("select IdClient from Reservation where IdClient = ?");
        stmtReserver = cx.getConnection().prepareStatement("insert into Reservation (IdClient, IdChambre, DateDebut, DateFin) "+ "values (?,?,?,?)");
    }

    
    
    
    
    
    
    /**
     * Retourner la connexion associee.
     */
    public Connexion getConnexion()
    {
        return cx;
    }





    // verifier si client a au moins une reservation en cours

	public boolean clienRsrvEnCr(int idClient) throws SQLException {
		stmtClientAvecReservation.setInt(1, idClient);
        ResultSet rset = stmtClientAvecReservation.executeQuery();
        boolean livreExiste = rset.next();
        rset.close();
        return livreExiste;
	}
			
}
