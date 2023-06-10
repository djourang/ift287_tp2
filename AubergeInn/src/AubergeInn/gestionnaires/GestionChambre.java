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
            throw new IFT287Exception("Les instances de Chambre et de reservation n'utilisent pas la m�me connexion au serveur");
        this.chambre = chambre;
        this.reservation = reservation;
    }

	
	
	 /**
     * Ajout d'un nouveau chambre dans la base de donn�es. S'il existe d�j�, une
     * exception est lev�e.
	 * @throws Exception 
     */

	public void ajouterChambre(int idChambre, String nomChmabre, String typeLit, BigDecimal readInt2) throws Exception {
		
		 try
	        {
	            // V�rifie si le chambre existe d�ja
	            if (chambre.existe(idChambre))
	                throw new IFT287Exception("Chambre existe d�j�: " + idChambre);

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
