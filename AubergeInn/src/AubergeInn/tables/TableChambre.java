package AubergeInn.tables;

import AubergeInn.bdd.Connexion;
import AubergeInn.tuples.TupleChambre;

import java.math.BigDecimal;
import java.sql.*;


public class TableChambre
{

    private final Connexion cx;
    
    
    
    //========================================
    private final PreparedStatement stmtAjouterChambre;
    private final PreparedStatement stmtSupprimerChambre;
    private final PreparedStatement stmtChambreDansReservationAvecPeriode;
    private final PreparedStatement stmtAfficherChambreExistant;
    private PreparedStatement stmtChambreExistant;
  //========================================

    /**
     * Creation d'une instance. Precompilation d'enonces SQL.
     */
    public TableChambre(Connexion cx) throws SQLException
    {
    	
        stmtAjouterChambre = cx.getConnection().prepareStatement("insert into Chambre (IdChambre,NomChambre, TypeLit, PrixDeBase) " + "values (?,?,?,?)");
        stmtSupprimerChambre = cx.getConnection().prepareStatement("delete from Chambre where IdChambre = ?");
        stmtChambreDansReservationAvecPeriode = cx.getConnection().prepareStatement("SELECT * FROM Reservation WHERE IdChambre = ?  AND ("+ "DateDebut <= ?  or DateDebut"+ " >= ? ) AND (DateFin <= ?  or DateFin >= ? )");
        stmtAfficherChambreExistant = cx.getConnection().prepareStatement("select IdChambre,NomChambre, TypeLit, PrixDeBase from Chambre where IdChambre = ?");
        stmtChambreExistant = cx.getConnection().prepareStatement("select IdChambre,NomChambre,TypeLit,PrixDeBase from Chambre where IdChambre = ?");

        this.cx = cx;
        
    }

    /**
     * Retourner la connexion associee.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /*
    * Vérifie si un Chambre existe.
    */
   public boolean existe(int idChambre) throws SQLException
   {
	   stmtChambreExistant.setInt(1, idChambre);
       ResultSet rset = stmtChambreExistant.executeQuery();
       boolean livreExiste = rset.next();
       rset.close();
       return livreExiste;
   }

public void ajouterChambre(int idChambre, String nomChmabre, String typeLit, BigDecimal prixBase) throws SQLException {
	 /* Ajout du Client. */
	stmtAjouterChambre.setInt(1, idChambre);
	stmtAjouterChambre.setString(2, nomChmabre);
	stmtAjouterChambre.setString(3, typeLit);
	stmtAjouterChambre.setBigDecimal(4, prixBase);
	stmtAjouterChambre.executeUpdate();	
}
    
}
