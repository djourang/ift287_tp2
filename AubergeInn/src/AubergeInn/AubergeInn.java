// Travail fait par :
//   NomEquipier1 - Matricule
//   NomEquipier2 - Matricule

package AubergeInn;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import AubergeInn.gestionnaires.GestionnaireAuberge;

/**
 * Fichier de base pour le TP2 du cours IFT287
 */



public class AubergeInn {
	
	
	

	
	
    private final GestionnaireAuberge gestionAuberge;



    
    // client
    public  PreparedStatement stmtAjouterClient;
    public PreparedStatement stmtSupprimerClient;
    public PreparedStatement stmtClientExistant;

    // Chambre
    public PreparedStatement stmtAjouterChambre;
    public PreparedStatement stmtSupprimerChambre;
    public PreparedStatement stmtAfficherChambresLibres;
    public PreparedStatement stmtChambreExistant;


    // Commodite
    public PreparedStatement stmtAjouterCommodite;
    public PreparedStatement stmtEnleverCommodite;
    public PreparedStatement stmtAfficherCommoditeExistent;



    public PreparedStatement stmtAfficherClientAReservEncour;
    public PreparedStatement stmtChambreDansReservationAvecPeriode;

    public PreparedStatement stmtChambreDansReservation;

    
    public PreparedStatement stmtInclureCommodite;
    public PreparedStatement stmtChambreDansAcomodement;
    public PreparedStatement stmtGetdateFinLocationChambre;

    public PreparedStatement stmtReserver;

    
    
    
   AubergeInn(String serveurType, String bd, String useId, String motDePass) throws IFT287Exception, SQLException {
    	
	   gestionAuberge=new GestionnaireAuberge(this,serveurType, bd, useId, motDePass);
	   
		
 }
    
    
    
    
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
    	
    	
    	
        if (args.length < 4) {
            System.out.println(
                    "Usage: java AubergeInn.AubergeInn " + "<serveur> <bd> <user> <password> [<fichier-transactions>]");
            return;
        }

    	AubergeInn instanceAuberge  = null;

        try {

            instanceAuberge = new AubergeInn(args[0], args[1], args[2], args[3]);
            
            BufferedReader reader = ouvrirFichier(args);
            String transaction = lireTransaction(reader);
            while (!finTransaction(transaction)) {
            	instanceAuberge.executerTransaction(transaction);
                transaction = lireTransaction(reader);
            }
        } finally {
            if (instanceAuberge != null)
            	instanceAuberge.fermer();
        }
    }

    private void fermer() throws SQLException {
    	gestionAuberge.fermer();
		
	}




	/**
     * Decodage et traitement d une transaction
     */
    public void executerTransaction(String transaction) throws Exception, IFT287Exception {
    	
    	
        try {
        	
            System.out.print(transaction); // transation = ligne complet //
            // Decoupage de la transaction en mots
            StringTokenizer tokenizer = new StringTokenizer(transaction, " ");
            if (tokenizer.hasMoreTokens()) {
                String command = tokenizer.nextToken(); // commande = mot courant de transaction//
                // Vous n avez pas a traiter la
                // commande "quitter".
                if (command.startsWith("--")) {
                    System.out.println(" : Commaintaire");
                } else {
                    switch (command) {

                    case "ajouterClient":// ajouterClient <IdClient> <Prenom> <Nom> <Age>
                    	gestionAuberge.getGestionClient().ajouterClient(readInt(tokenizer), readString(tokenizer), readString(tokenizer),readInt(tokenizer));
                        break;
                    /*
                     * supprimerClient <idClient> Cette commande supprime un client s il n a pas de
                     * reservation encours.
                     */
                    case "supprimerClient":
                    	gestionAuberge.getGestionClient().suprimerClient(readInt(tokenizer));
                        break;
                    /*
                     * ajouterChambre <IdChambre> <NomChambre> <TypeLit> <PrixDeBase> Cette commande
                     * ajoute une nouvelle chambre au systeme
                     */
                    case "ajouterChambre":
                    	gestionAuberge.getGestionChambre().ajouterChambre(readInt(tokenizer), readString(tokenizer), readString(tokenizer),
                    			new BigDecimal(readString(tokenizer)) );
                        break;
                    /*
                     * supprimerChambre <IdChambre> Cette commande supprime une chambre si elle
                     * n est pas reservee et n a pas de reservation future.
                     */
                    case "supprimerChambre":
                    	gestionAuberge.getGestionChambre().supprimerChambre(readInt(tokenizer));
                        break;
                    /*
                     * ajouterCommodite <IdCommodite> <Description> <SurplusPrix> Cette commande
                     * ajoute un nouveau service offert par l entreprise.
                     */
                    case "ajouterCommodite":
                        ajouterCommodite(readInt(tokenizer), readString(tokenizer), readInt(tokenizer));
                        break;
                    /*
                     * inclureCommodite <IdChambre> <IdCommodite> Cette commande ajoute une
                     * Acommodement a une chambre.
                     */
                    case "inclureCommodite":
                        inclureCommodite(readInt(tokenizer), readInt(tokenizer));
                        break;
                    /*
                     * enleverCommodite <IdChambre> <IdCommodite> Cette commande enleve un
                     * Acommodement a une chambre.
                     */
                    case "enleverCommodite":
                        enleverCommodite(readInt(tokenizer), readInt(tokenizer));
                        break;
                    /*
                     * reserver <IdClient> <IdChambre> <DateDebut> <DateFin> Cette commande enleve
                     * un Acommodement a une chambre.
                     */
                    case "reserver":
                        reserver(readInt(tokenizer), readInt(tokenizer), readDate(tokenizer), readDate(tokenizer));
                        break;
                    default:
                        System.out.println(" : Transaction non reconnue");
                        break;
                    }
                    System.out.println("");
                }

            }
        } catch (Exception e) {
            System.out.println(" " + e.toString());
            // Ce rollback est ici seulement pour vous aider et eviter des problemes lors de
            // la correction
            // automatique. En theorie, il ne sert  a rien et ne devrait pas apparaitre ici
            // dans un programme
            // fini et fonctionnel sans bogues.
            gestionAuberge.getConnexion().rollback();
        }
    }

    // ======================================================================================================================>Methode
	// ajouterClient <IdClient> <Prenom> <Nom> <Age>

//	public void ajouterClient(int IdClient, String Prenom, String Nom, int Age) throws Exception {
//		TableClient tableClient = new TableClient(cx);
//		tableClient.ajouterClient( new TupleClient(IdClient,Prenom,Nom,Age));
//	}

    
    
    
    public  void ajouterClient(int IdClient, String Prenom, String Nom, int Age) throws Exception {
        try {
            // Verifie si le Client existe deja
        	stmtClientExistant.setInt(1, IdClient);
            ResultSet rsetClient = stmtClientExistant.executeQuery();
            
            if (rsetClient.next()) {
                rsetClient.close();
                throw new IFT287Exception("Client existe deja: " + IdClient);
            }
            rsetClient.close();

            // Ajout du Client dans la table des Client
            stmtAjouterClient.setInt(1, IdClient);
            stmtAjouterClient.setString(2, Prenom);
            stmtAjouterClient.setString(3, Nom);
            stmtAjouterClient.setInt(4, Age);
            stmtAjouterClient.executeUpdate();
            
        	//Persiste la transaction courante
            gestionAuberge.getConnexion().commit();
        } catch (Exception e) {
        		
        	gestionAuberge.getConnexion().rollback();
            throw e;
        }
    }

    // supprimerClient <idClient>
    public void suprimerClient(int IdClient) throws Exception {
        try {
            // verifier si client a au moins une reservation en cours
            // on ne la supprime pas si vrais
            stmtAfficherClientAReservEncour.setInt(1, IdClient);
            ResultSet rsetClient = stmtAfficherClientAReservEncour.executeQuery();
            if (rsetClient.next()) {
                rsetClient.close();
                throw new IFT287Exception("Ce Client  a au moins une reservation en cours: " + IdClient);
            }
            rsetClient.close();
            // Suppression du Client.
            stmtSupprimerClient.setInt(1, IdClient);

            if (stmtSupprimerClient.executeUpdate() == 0)
                throw new IFT287Exception("Client " + IdClient + " inexistant");
            // Commit
            gestionAuberge.getConnexion().commit();
        } catch (Exception e) {
            // System.out.println(e);
        	gestionAuberge.getConnexion().rollback();
            throw e;
        }
    }

    // ajouterChambre <IdChambre> <NomChambre> <TypeLit> <PrixDeBase>
    private  void ajouterChambre(int IdChambre, String NomChambre, String TypeLit, int PrixDeBase)
            throws Exception {
        try {
            // Verifie si le Chambre existe deja
            stmtChambreExistant.setInt(1, IdChambre);
            ResultSet rsetClient = stmtChambreExistant.executeQuery();
            if (rsetClient.next()) {
                rsetClient.close();
                throw new IFT287Exception("Chambre existe deja: " + IdChambre);
            }
            rsetClient.close();

            // Ajout du Client dans la table des Client
            stmtAjouterChambre.setInt(1, IdChambre);
            stmtAjouterChambre.setString(2, NomChambre);
            stmtAjouterChambre.setString(3, TypeLit);
            stmtAjouterChambre.setInt(4, PrixDeBase);
            stmtAjouterChambre.executeUpdate();
            // Commit
            gestionAuberge.getConnexion().commit();
        } catch (Exception e) {
            // System.out.println(e);
            gestionAuberge.getConnexion().rollback();
            throw e;
        }
    }

    // supprimerChambre <IdChambre>
    private  void supprimerChambre(int IdChambre) throws Exception {
        try {
            // verifier si Chambre est reserver
            // on ne la supprime pas si vrais
            stmtChambreDansReservation.setInt(1, IdChambre);
            ResultSet rsetClient = stmtChambreDansReservation.executeQuery();
            if (rsetClient.next()) {
                rsetClient.close();
                throw new IFT287Exception("Ce Chambre  est reserver par un client: " + IdChambre);
            }
            rsetClient.close();
            // Suppression du Chambre.
            stmtSupprimerChambre.setInt(1, IdChambre);
            
            if (stmtSupprimerChambre.executeUpdate() == 0)
                throw new IFT287Exception("Chambre " + IdChambre + " inexistant");
            // Commit
            gestionAuberge.getConnexion().commit();
        } catch (Exception e) {
            // System.out.println(e);
            gestionAuberge.getConnexion().rollback();
            throw e;
        }
    }

    // ajouterCommodite <IdCommodite> <Description> <SurplusPrix>
    private  void ajouterCommodite(int IdCommodite, String Description, int SurplusPrix) throws Exception {
        try {
            // Verifie si le Chambre existe deja
            stmtAfficherCommoditeExistent.setInt(1, IdCommodite);
            ResultSet rsetClient = stmtAfficherCommoditeExistent.executeQuery();
            if (rsetClient.next()) {
                rsetClient.close();
                throw new IFT287Exception("Commodite existe deja: " + IdCommodite);
            }
            rsetClient.close();

            // Ajout du Client dans la table des Client
            stmtAjouterCommodite.setInt(1, IdCommodite);
            stmtAjouterCommodite.setString(2, Description);
            stmtAjouterCommodite.setInt(3, SurplusPrix);
            stmtAjouterCommodite.executeUpdate();
            // Commit
            gestionAuberge.getConnexion().commit();
        } catch (Exception e) {
            // System.out.println(e);
            gestionAuberge.getConnexion().rollback();
            throw e;
        }
    }

    // inclureCommodite <IdChambre> <IdCommodite>
    private  void inclureCommodite(int IdChambre, int IdCommodite) throws Exception {
        try {
            // Verifie si le Chambre exist avant de l accomoder
            stmtChambreExistant.setInt(1, IdChambre);
            ResultSet rsetClient = stmtChambreExistant.executeQuery();
            if (!rsetClient.next()) {
                rsetClient.close();
                throw new IFT287Exception("Acommodement impossible car Chambre inexistant: " + IdChambre);
            }
            rsetClient.close();
            // Verifie si le Chambre a deja ce accomodement avant de l accomoder
            stmtChambreDansAcomodement.setInt(1, IdChambre);
            stmtChambreDansAcomodement.setInt(2, IdCommodite);
            ResultSet rsetClient1 = stmtChambreDansAcomodement.executeQuery();
            if (rsetClient1.next()) {
                rsetClient1.close();
                throw new IFT287Exception("la chambre inclu deja ce Accommodement: " + IdChambre);
            }
            rsetClient1.close();
            // Acommodement de la Chambre Specifier a l acommodement specifier
            stmtInclureCommodite.setInt(1, IdChambre);
            stmtInclureCommodite.setInt(2, IdCommodite);
            stmtInclureCommodite.executeUpdate();
            // Commit
            gestionAuberge.getConnexion().commit();
        } catch (Exception e) {
            // System.out.println(e);
            gestionAuberge.getConnexion().rollback();
            throw e;
        }
    }

    // inclureCommodite <IdChambre> <IdCommodite>
    private  void enleverCommodite(int IdChambre, int IdCommodite) throws Exception {
        try {
            // Verifie si le Chambre exist avant d enlever l Accommodement
            stmtChambreExistant.setInt(1, IdChambre);
            ResultSet rsetClient = stmtChambreExistant.executeQuery();
            if (!rsetClient.next()) {
                rsetClient.close();
                throw new IFT287Exception("enlever Accommodement impossible car Chambre inexistant: " + IdChambre);
            }
            rsetClient.close();
            // Verifie si le Chambre a deja ce accomodement avant de voiloir enlever
            // l Accommodement
            stmtChambreDansAcomodement.setInt(1, IdChambre);
            stmtChambreDansAcomodement.setInt(2, IdCommodite);
            ResultSet rsetClient1 = stmtChambreDansAcomodement.executeQuery();
            if (!rsetClient1.next()) {
                rsetClient1.close();
                throw new IFT287Exception("la chambre n a pas cet Accommodement: " + IdChambre);
            }
            rsetClient1.close();
            // Acommodement de la Chambre Specifier a l acommodement specifier
            stmtEnleverCommodite.setInt(1, IdChambre);
            stmtEnleverCommodite.setInt(2, IdCommodite);
            stmtEnleverCommodite.executeUpdate();
            // Commit
            gestionAuberge.getConnexion().commit();
        } catch (Exception e) {
            // System.out.println(e);
            gestionAuberge.getConnexion().rollback();
            throw e;
        }
    }

    // reserver <IdClient> <IdChambre> <DateDebut> <DateFin>
    private  void reserver(int IdClient, int IdChambre, Date DateDebut, Date DateFin) throws Exception {
        try {
            
            // Verifie si le Chambre exist avant de reserver
            stmtChambreExistant.setInt(1, IdChambre);
            ResultSet rsetClient = stmtChambreExistant.executeQuery();
            if (!rsetClient.next()) {
                rsetClient.close();
                throw new IFT287Exception("Acommodement impossible car Chambre inexistant: " + IdChambre);
            }
            // verifier si Chambre est reserver pour la periode desiree
            // on ne la reserve pas si vrais
            
            stmtChambreDansReservationAvecPeriode.setInt(1, IdChambre);
            stmtChambreDansReservationAvecPeriode.setDate(2, DateDebut);
            stmtChambreDansReservationAvecPeriode.setDate(3, DateFin);
            stmtChambreDansReservationAvecPeriode.setDate(4, DateDebut);
            stmtChambreDansReservationAvecPeriode.setDate(5, DateFin);
            ResultSet rsetClient1 = stmtChambreDansReservationAvecPeriode.executeQuery();
            if (rsetClient1.next()) {
                rsetClient1.close();
                throw new IFT287Exception("Ce Chambre  est deja reserver par un client pour cette periode ou une partie de cette periode: " + IdChambre);
            }
            
            // Acommodement de la Chambre Specifier a l acommodement specifier
            stmtReserver.setInt(1, IdClient);
            stmtReserver.setInt(2, IdChambre);
            stmtReserver.setDate(3, DateDebut);
            stmtReserver.setDate(4, DateFin);
            stmtReserver.executeUpdate();
            // Commit
            gestionAuberge.getConnexion().commit();
        } catch (Exception e) {
            System.out.println(e);
            gestionAuberge.getConnexion().rollback();
            throw e;
        }
    }
    // ======================================================================================================================>Methode
    
    
    // ****************************************************************
    // * Les methodes suivantes n ont pas besoin d etre modifiees *
    // ****************************************************************

    /**
     * Ouvre le fichier de transaction, ou lit  a partir de System.in
     */
    public static BufferedReader ouvrirFichier(String[] args) throws FileNotFoundException {
        if (args.length < 5)
            // Lecture au clavier
            return new BufferedReader(new InputStreamReader(System.in));
        else
            // Lecture dans le fichier passe en parametre
            return new BufferedReader(new InputStreamReader(new FileInputStream(args[4])));
    }

    /**
     * Lecture d une transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    /**
     * Verifie si la fin du traitement des transactions est atteinte.
     */
    static boolean finTransaction(String transaction) {
        // fin de fichier atteinte
        return (transaction == null || transaction.equals("quitter"));
    }

    /** Lecture d une chaine de caracteres de la transaction entree a l ecran */
    static String readString(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreElements())
            return tokenizer.nextToken();
        else
            throw new Exception("Autre parametre attendu");
    }

    /**
     * Lecture d un int java de la transaction entree a l ecran
     */
    static int readInt(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            try {
                return Integer.valueOf(token).intValue();
            } catch (NumberFormatException e) {
                throw new Exception("Nombre attendu a la place de \"" + token + "\"");
            }
        } else
            throw new Exception("Autre parametre attendu");
    }

    static Date readDate(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            try {
                return Date.valueOf(token);
            } catch (IllegalArgumentException e) {
                throw new Exception("Date dans un format invalide - \"" + token + "\"");
            }
        } else
            throw new Exception("Autre parametre attendu");
    }

    
    
    

    
    
    
    private  void initialiseStatements() throws SQLException

    {
        
        // initialisation des transaction concernant le Client
        stmtAjouterClient = gestionAuberge.getConnexion().getConnection().prepareStatement("insert into Client (IdClient, PrenomClient, NomClient, AgeClient) " + "values (?,?,?,?)");
        stmtSupprimerClient = gestionAuberge.getConnexion().getConnection().prepareStatement("delete from Client where IdClient = ?");
        stmtClientExistant = gestionAuberge.getConnexion().getConnection().prepareStatement("select * from Client where IdClient = ?");

        
        // initialisation des transaction concernant le Chambre
        stmtAjouterChambre = gestionAuberge.getConnexion().getConnection().prepareStatement("insert into Chambre (IdChambre,NomChambre, TypeLit, PrixDeBase) " + "values (?,?,?,?)");
        stmtSupprimerChambre = gestionAuberge.getConnexion().getConnection().prepareStatement("delete from Chambre where IdChambre = ?");
        stmtChambreDansReservationAvecPeriode = gestionAuberge.getConnexion().getConnection().prepareStatement("SELECT * FROM Reservation WHERE IdChambre = ?  AND ("+ "DateDebut <= ?  or DateDebut"+ " >= ? ) AND (DateFin <= ?  or DateFin >= ? )");
        stmtChambreExistant = gestionAuberge.getConnexion().getConnection().prepareStatement("select * from Chambre where IdChambre = ?");

        
        
        // initialisation des transaction concernant le Commodite
        stmtAjouterCommodite = gestionAuberge.getConnexion().getConnection().prepareStatement("insert into Commodite (IdCommodite, Description,SurplusPrix) " + "values (?,?,?)");
        stmtInclureCommodite = gestionAuberge.getConnexion().getConnection().prepareStatement("insert into Accommoder (IdChambre,IdCommodite) " + "values (?,?)");
        stmtEnleverCommodite = gestionAuberge.getConnexion().getConnection().prepareStatement("delete from Accommoder where IdChambre = ? AND IdCommodite=?");
        stmtAfficherCommoditeExistent = gestionAuberge.getConnexion().getConnection().prepareStatement("select * from Commodite where IdCommodite = ?");

        
        

        // initialisation des transaction concernant le Reservation
        stmtChambreDansReservation = gestionAuberge.getConnexion().getConnection().prepareStatement("select * from Reservation where IdChambre = ?");
        stmtAfficherClientAReservEncour = gestionAuberge.getConnexion().getConnection().prepareStatement("select * from Reservation where IdClient = ?");
        stmtReserver = gestionAuberge.getConnexion().getConnection().prepareStatement("insert into Reservation (IdClient, IdChambre, DateDebut, DateFin) "+ "values (?,?,?,?)");



        // initialisation des transaction concernant le Accommodement
        stmtChambreDansAcomodement = gestionAuberge.getConnexion().getConnection().prepareStatement("select * from Accommoder where IdChambre=? AND IdCommodite=?");


    }

    
    

}
