package AubergeInn.gestionnaires;

import java.sql.ResultSet;

import AubergeInn.IFT287Exception;
import AubergeInn.bdd.Connexion;
import AubergeInn.tables.TableClient;
import AubergeInn.tables.TableReservations;

public class GestionClient {

    private final TableClient client;
    private final TableReservations reservation;
    private final Connexion cx;
    
    
    public GestionClient(TableClient client, TableReservations reservation) throws IFT287Exception
    {
        this.cx = client.getConnexion();
        if (client.getConnexion() != reservation.getConnexion())
            throw new IFT287Exception("Les instances de Chambre et de reservation n'utilisent pas la même connexion au serveur");
        this.client = client;
        this.reservation = reservation;
    }



    public void ajouterClient(int IdClient, String Prenom, String Nom, int Age) throws Exception {
        try
        {
            // Vérifie si le Client existe déja
            if (client.existe(IdClient))
                throw new IFT287Exception("Client existe déjà: " + IdClient);

            // Ajout du client dans la table des client
            client.ajouterClient( IdClient,  Prenom,  Nom,  Age);
            
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }       
    }
    
    
    
    public void suprimerClient(int IdClient) throws Exception {
        try {
        	// Verifie si le Client existe avant de vouloir le supprimer
            if (!client.existe(IdClient)) {
                throw new IFT287Exception("Client inexistant : " + IdClient);
            }
            
        	// verifier si client a au moins une reservation en cours
            // on ne la supprime pas si vrais
            
            else if (reservation.clienRsrvEnCr(IdClient)) {
                throw new IFT287Exception("supression non appliquer, car Ce Client  a au moins une reservation en cours: " + IdClient);
            }else {
            	
            // Suppression du Client.
            client.supprimerClient(IdClient);
            }
           
        } catch (Exception e) {
            // System.out.println(e);
        	cx.rollback();
            throw e;
        }
    }








}
