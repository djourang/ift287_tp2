package AubergeInn.tables;

import AubergeInn.bdd.Connexion;
import AubergeInn.tuples.TupleClient;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TableCommodite {
	
	private final PreparedStatement stmtExiste;
	private final PreparedStatement stmtInsert;
	private final PreparedStatement stmtUpdate;
	private final PreparedStatement stmtDelete;
	private final PreparedStatement stmtListePret;


	// ======================================================>ajouter
	private static PreparedStatement stmtAjouterClient;
	private static PreparedStatement stmtSupprimerClient;
	private static PreparedStatement stmtAfficherClientExistant;

	// ======================================================>ajouter

	private final Connexion cx;

	/**
	 * Creation d'une instance.
	 * Des enonces SQL pour chaque requete sont precompiler
	 */
	public TableCommodite(Connexion cx) throws SQLException {
		this.cx = cx;

		// ======================================================>ajouter
		stmtAjouterClient = cx.getConnection().prepareStatement(
				"insert into Client (IdClient, PrenomClient, NomClient, AgeClient) " + "values (?,?,?,?)");
		stmtSupprimerClient = cx.getConnection().prepareStatement("delete from Client where IdClient = ?");
		stmtAfficherClientExistant = cx.getConnection().prepareStatement("select * from Client where IdClient = ?");
		// ======================================================>ajouter

		stmtExiste = cx.getConnection().prepareStatement(
				"select idlivre, titre, auteur, dateAcquisition, idMembre, datePret from livre where idlivre = ?");
		stmtInsert = cx.getConnection()
				.prepareStatement("insert into livre (idLivre, titre, auteur, dateAcquisition, idMembre, datePret) "
						+ "values (?,?,?,?,null,null)");
		stmtUpdate = cx.getConnection()
				.prepareStatement("update livre set idMembre = ?, datePret = ? " + "where idLivre = ?");
		stmtDelete = cx.getConnection().prepareStatement("delete from livre where idlivre = ?");
		stmtListePret = cx.getConnection()
				.prepareStatement("select idLivre, titre, auteur, dateAcquisition, idMembre, datePret "
						+ "from livre where idmembre = ?");
	}

	/**
	 * Retourner la connexion associée.
	 */
	public Connexion getConnexion() {
		return cx;
	}

	/**
	 * Vérifie si un livre existe.
	 */
	public boolean existe(int idLivre) throws SQLException {
		stmtExiste.setInt(1, idLivre);
		ResultSet rset = stmtExiste.executeQuery();
		boolean livreExiste = rset.next();
		rset.close();
		return livreExiste;
	}


	/**
	 * Ajout d'un nouveau livre dans la base de données.
	 */
	public void acquerir(int idLivre, String titre, String auteur, String dateAcquisition) throws SQLException {
		/* Ajout du livre. */
		stmtInsert.setInt(1, idLivre);
		stmtInsert.setString(2, titre);
		stmtInsert.setString(3, auteur);
		stmtInsert.setDate(4, Date.valueOf(dateAcquisition));
		stmtInsert.executeUpdate();
	}

	/**
	 * Enregistrement de l'emprunteur d'un livre.
	 */
	public int preter(int idLivre, int idMembre, String datePret) throws SQLException {
		/* Enregistrement du pret. */
		stmtUpdate.setInt(1, idMembre);
		stmtUpdate.setDate(2, Date.valueOf(datePret));
		stmtUpdate.setInt(3, idLivre);
		return stmtUpdate.executeUpdate();
	}


	/**
	 * Suppression d'un livre.
	 */
	public int vendre(int idLivre) throws SQLException {
		/* Suppression du livre. */
		stmtDelete.setInt(1, idLivre);
		return stmtDelete.executeUpdate();
	}

	



}
