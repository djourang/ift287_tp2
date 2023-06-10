package AubergeInn.tuples;

import java.math.BigDecimal;



/**
 * Permet de representer un tuple Chambe de l'auberge.
 */
public class TupleChambre
{
    private int idMembre;
    private String nom;
    private long telephone;
    private int limitePret;
    private int nbPret;
    
    
    
    //=============================================================================>
    private int idChambre;
    private String nomChambre;
    private String typeLit;
    private BigDecimal  PrixBase;
    private boolean idChambreNull;
    //=============================================================================>
    
    
    
    
    public TupleChambre()
    {
    }
    
    public TupleChambre(int idChambre, String nomChambre, long telephone, int limitePret, int nbPret,/*ajout*/String typeLit,BigDecimal PrixBase/*ajout*/)
    {
        this.setIdMembre(idChambre);
        this.setNom(nomChambre);
        this.setTelephone(telephone);
        this.setLimitePret(limitePret);
        this.setNbPret(nbPret);
        
        
        //=============================================================================>
        this.idChambre=idChambre;
        this.nomChambre=nomChambre;
        this.typeLit=typeLit;
        this.PrixBase=PrixBase;
        //=============================================================================>
        
    }
    
    
  
    
    
    public int getIdMembre()
    {
        return idMembre;
    }

    public void setIdMembre(int idMembre)
    {
        this.idMembre = idMembre;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public long getTelephone()
    {
        return telephone;
    }

    public void setTelephone(long telephone)
    {
        this.telephone = telephone;
    }

    public int getLimitePret()
    {
        return limitePret;
    }

    public void setLimitePret(int limitePret)
    {
        this.limitePret = limitePret;
    }

    public int getNbPret()
    {
        return nbPret;
    }

    public void setNbPret(int nbPret)
    {
        this.nbPret = nbPret;
    }
}
