package AubergeInn.gestionnaires;

import java.math.BigDecimal;

import AubergeInn.IFT287Exception;
import AubergeInn.bdd.Connexion;
import AubergeInn.tables.TableChambre;
import AubergeInn.tables.TableReservations;

public class GestionChambre {

    private final TableChambre chambre;
    private final TableReservations reservation;
    private final Connexion cx;
	
	
	
	public GestionChambre(TableChambre chambre, TableReservations reservation) throws IFT287Exception
    {
        this.cx = chambre.getConnexion();
        if (chambre.getConnexion() != reservation.getConnexion())
            throw new IFT287Exception("Les instances de Chambre et de reservation n'utilisent pas la même connexion au serveur");
        this.chambre = chambre;
        this.reservation = reservation;
    }

	
	
	 /**
     * Ajout d'un nouveau chambre dans la base de données. S'il existe déjà, une
     * exception est levée.
	 * @throws Exception 
     */

	public void ajouterChambre(int idChambre, String nomChmabre, String typeLit, BigDecimal readInt2) throws Exception {
		
		 try
	        {
	            // Vérifie si le chambre existe déja
	            if (chambre.existe(idChambre))
	                throw new IFT287Exception("Chambre existe déjà: " + idChambre);

	            // Ajout du livre dans la table des livres
	            chambre.ajouterChambre(idChambre,nomChmabre, typeLit, readInt2);
	            
	            // Commit
	            cx.commit();
	        }
	        catch (Exception e)
	        {
	            cx.rollback();
	            throw e;
	        }
	}


}
