package AubergeInn.tables;

import AubergeInn.IFT287Exception;
import AubergeInn.bdd.Connexion;
import AubergeInn.tuples.TupleClient;

import java.sql.*;

public class TableAccommoder {
	


	// ======================================================>ajouter

	private final PreparedStatement stmtChambreDansAcomodement;
	private final PreparedStatement chambre_EST_Accomoder;
    private static PreparedStatement stmtInclureCommodite;


	// ======================================================>ajouter

	private final Connexion cx;

	/**
	 * Creation d'une instance.
	 * Des enonces SQL pour chaque transaction sont precompiler
	 */
	public TableAccommoder(Connexion cx) throws SQLException {
		
		this.cx = cx;
		// ======================================================>ajouter
        stmtChambreDansAcomodement = cx.getConnection().prepareStatement("select IdChambre,IdCommodite,DateDebut,DateFin from Accommoder where IdChambre=? AND IdCommodite=?");
        chambre_EST_Accomoder = cx.getConnection().prepareStatement("select IdClient,PrenomClient,NomClient,AgeClient from Client where IdClient = ?");
        stmtInclureCommodite = cx.getConnection().prepareStatement("insert into Accommoder (IdChambre,IdCommodite) " + "values (?,?)");

        // ======================================================>ajouter

	}

	/**
	 * Retourner la connexion associee.
	 */
	public Connexion getConnexion() {
		return cx;
	}

	

	
	//======================================================

	/**
	 * Verifie si un chambre EST accomoder.
	 */
	public boolean estAccomoder(int idLivre) throws SQLException {
		
		chambre_EST_Accomoder.setInt(1, idLivre);
		ResultSet rset = chambre_EST_Accomoder.executeQuery();
		boolean livreExiste = rset.next();
		rset.close();
		return livreExiste;
	}

	
	
	/**
	 * Lecture d'une chambre accomoder.
	 */
	public TupleClient getLivre(int idChambre) throws SQLException {
		
		chambre_EST_Accomoder.setInt(1, idChambre);
		
		ResultSet rset = chambre_EST_Accomoder.executeQuery();
		
		if (rset.next()) {
			
			TupleClient tupleClient = new TupleClient();
			tupleClient.setIdClient(idChambre);
			tupleClient.setprenomClient(rset.getString(2));
			tupleClient.setnomClient(rset.getString(3));
			//tupleClient.setDateAcquisition(rset.getDate(4));
			tupleClient.setageClient(rset.getInt(5));
			tupleClient.setIdClientNull(rset.wasNull());
			//tupleClient.setDatePret(rset.getDate(6));
			
			rset.close();
			return tupleClient;
		} else
			return null;
	}
	
	
	
	
	
	 // inclureCommodite <IdChambre> <IdCommodite>
//    private static void inclureCommodite(int IdChambre, int IdCommodite) throws Exception {
//        try {
//            // Verifie si le Chambre exist avant de l accomoder
//        	
//        	
//        	
//        	
//            stmtAfficherChambreExistant.setInt(1, IdChambre);
//            ResultSet rsetClient = stmtAfficherChambreExistant.executeQuery();
//            if (!rsetClient.next()) {
//                rsetClient.close();
//                throw new IFT287Exception("Acommodement impossible car Chambre inexistant: " + IdChambre);
//            }
//            rsetClient.close();
//            // Verifie si le Chambre a deja ce accomodement avant de l accomoder
//            stmtChambreDansAcomodement.setInt(1, IdChambre);
//            stmtChambreDansAcomodement.setInt(2, IdCommodite);
//            ResultSet rsetClient1 = stmtChambreDansAcomodement.executeQuery();
//            if (rsetClient1.next()) {
//                rsetClient1.close();
//                throw new IFT287Exception("la chambre inclu deja ce Accommodement: " + IdChambre);
//            }
//            rsetClient1.close();
//            // Acommodement de la Chambre Specifier a l acommodement specifier
//            stmtInclureCommodite.setInt(1, IdChambre);
//            stmtInclureCommodite.setInt(2, IdCommodite);
//            stmtInclureCommodite.executeUpdate();
//            // Commit
//            cx.commit();
//        } catch (Exception e) {
//            // System.out.println(e);
//            cx.rollback();
//            throw e;
//        }
//    }
	//======================================================






}
