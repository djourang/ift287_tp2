package AubergeInn.tuples;

import java.sql.*;


/**
 * Permet de representer un tuple Client de l'auberge.
 */
public class TupleClient
{
    private int idLivre;
    private String titre;
    private String auteur;
    private Date dateAcquisition;
    private int idMembre;
    private boolean idMembreNull;
    private Date datePret;
   
    //=============================================================================>
    private int idClient;
    private String prenomClient;
    private String nomClient;
    private int ageClient;
    private boolean idClientNull;
    //=============================================================================>
    

    public TupleClient()
    {
    
    }



    public TupleClient(int idClient, String prenomClient, String nomClient, int ageClient) {
        //=============================================================================>
        this.idClient=idClient;
        this.prenomClient=prenomClient;
        this.nomClient=nomClient;
        this.ageClient=ageClient;
        //=============================================================================>

	}

	public int getIdClient()
    {
        return idLivre;
    }

    public void setIdClient(int idLivre)
    {
        this.idLivre = idLivre;
    }



    public String getprenomClient()
    {
        return titre;
    }

    public void setprenomClient(String titre)
    {
        this.titre = titre;
    }





    public String getnomClient()
    {
        return auteur;
    }


    public void setnomClient(String auteur)
    {
        this.auteur = auteur;
    }



    public int getageClient()
    {
        return idMembre;
    }

    public void setageClient(int idMembre)
    {
        this.idMembre = idMembre;
    }

    public boolean isIdClientNull()
    {
        return idMembreNull;
    }

    public void setIdClientNull(boolean idMembreNull)
    {
        this.idMembreNull = idMembreNull;
    }

    
}
