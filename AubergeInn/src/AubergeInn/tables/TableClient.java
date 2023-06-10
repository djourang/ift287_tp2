package AubergeInn.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import AubergeInn.bdd.Connexion;


public class TableClient {
	



	// ======================================================>ajouter
	private PreparedStatement stmtAjouterClient;
	private PreparedStatement stmtSupprimerClient;
	private PreparedStatement stmtClientExistant;

	// ======================================================>ajouter

	private final Connexion cx;

	/**
	 * Creation d'une instance.
	 * Des enonces SQL pour chaque requete sont precompiler
	 * @throws SQLException 
	 */
	public TableClient(Connexion cx) throws SQLException {
		this.cx = cx;
        stmtAjouterClient = cx.getConnection().prepareStatement("insert into Client (IdClient, PrenomClient, NomClient, AgeClient) " + "values (?,?,?,?)");
        stmtSupprimerClient = cx.getConnection().prepareStatement("delete from Client where IdClient = ?");
        stmtClientExistant = cx.getConnection().prepareStatement("select IdClient,PrenomClient,NomClient,AgeClient from Client where IdClient = ?");

	}

	




	/**
	 * Retourner la connexion associÃ©e.
	 */
	public Connexion getConnexion() {
		return cx;
	}

	// ======================================================================================================================>Methode
	// ajouterClient <IdClient> <Prenom> <Nom> <Age>
	public void ajouterClient(int idClient, String prenom, String nom, int age) throws SQLException {
		 /* Ajout du Client. */
		stmtAjouterClient.setInt(1, idClient);
		stmtAjouterClient.setString(2, prenom);
		stmtAjouterClient.setString(3, nom);
		stmtAjouterClient.setInt(4, age);
		stmtAjouterClient.executeUpdate();		
	}
	
	

	/**
     * Vérifie si un client existe deja dans table client.
     */
    public boolean existe(int idClient) throws SQLException
    {
    	stmtClientExistant.setInt(1, idClient);
        ResultSet rset = stmtClientExistant.executeQuery();
        boolean livreExiste = rset.next();
        rset.close();
        return livreExiste;
    }

	public void supprimerClient(int idClient) throws SQLException {
		 stmtSupprimerClient.setInt(1, idClient);
		 cx.commit();
	}


}
