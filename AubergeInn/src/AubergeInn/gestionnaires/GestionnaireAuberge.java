package AubergeInn.gestionnaires;

import java.sql.SQLException;

import AubergeInn.AubergeInn;
import AubergeInn.IFT287Exception;
import AubergeInn.bdd.Connexion;
import AubergeInn.tables.TableChambre;
import AubergeInn.tables.TableClient;
import AubergeInn.tables.TableReservations;

public class GestionnaireAuberge {

	
	
    private final Connexion cx;
    
    private final TableChambre chambre;
    private final TableClient  client;
    private final TableReservations reservation;
    
    private GestionChambre gestionChambre;
    private GestionClient gestionClient;
    private GestionCommodite gestionCommodite;
    private GestionAccommoder gestionAccommoder;
    private GestionReservation gestionReservation;
    
    
    
    
    public GestionnaireAuberge(AubergeInn aubergeInn, String serveur, String bd, String user, String password)throws IFT287Exception, SQLException
    {
        // Allocation des objets pour le traitement des transactions
        cx = new Connexion(serveur, bd, user, password);
        chambre = new TableChambre(cx);
        client = new TableClient(cx);
        reservation = new TableReservations(cx);
        initStmts(aubergeInn,serveur,bd,user,password);
        
        setGestionChambre(new GestionChambre(chambre, reservation));
        setGestionClient(new GestionClient(client, reservation));
        setGestionAccommoder(new GestionAccommoder(chambre, client, reservation));
        setGestionReservation(new GestionReservation(chambre, client, reservation));
        setGestionCommodite(new GestionCommodite(chambre, client, reservation));
        
    }

    private void initStmts(AubergeInn aubergeInn, String serveur, String bd, String user, String password) throws SQLException {
    	
    	// initialisation des transaction concernant le Client
    	aubergeInn.stmtAjouterClient = cx.getConnection().prepareStatement("insert into Client (IdClient, PrenomClient, NomClient, AgeClient) " + "values (?,?,?,?)");
    	aubergeInn.stmtSupprimerClient = cx.getConnection().prepareStatement("delete from Client where IdClient = ?");
    	aubergeInn.stmtClientExistant = cx.getConnection().prepareStatement("select IdClient,PrenomClient,NomClient,AgeClient from Client where IdClient = ?");

        
        // initialisation des transaction concernant le Chambre
    	aubergeInn.stmtAjouterChambre = cx.getConnection().prepareStatement("insert into Chambre (IdChambre,NomChambre, TypeLit, PrixDeBase) " + "values (?,?,?,?)");
    	aubergeInn.stmtSupprimerChambre = cx.getConnection().prepareStatement("delete from Chambre where IdChambre = ?");
        aubergeInn.stmtChambreDansReservationAvecPeriode = cx.getConnection().prepareStatement("SELECT * FROM Reservation WHERE IdChambre = ?  AND ("+ "DateDebut <= ?  or DateDebut"+ " >= ? ) AND (DateFin <= ?  or DateFin >= ? )");
        aubergeInn.stmtChambreExistant = cx.getConnection().prepareStatement("select * from Chambre where IdChambre = ?");

        
        
        // initialisation des transaction concernant le Commodite
        aubergeInn.stmtAjouterCommodite = cx.getConnection().prepareStatement("insert into Commodite (IdCommodite, Description,SurplusPrix) " + "values (?,?,?)");
        aubergeInn.stmtInclureCommodite = cx.getConnection().prepareStatement("insert into Accommoder (IdChambre,IdCommodite) " + "values (?,?)");
        aubergeInn.stmtEnleverCommodite = cx.getConnection().prepareStatement("delete from Accommoder where IdChambre = ? AND IdCommodite=?");
        aubergeInn.stmtAfficherCommoditeExistent = cx.getConnection().prepareStatement("select * from Commodite where IdCommodite = ?");

        
        

        // initialisation des transaction concernant le Reservation
        aubergeInn.stmtChambreDansReservation = cx.getConnection().prepareStatement("select * from Reservation where IdChambre = ?");
        aubergeInn.stmtAfficherClientAReservEncour = cx.getConnection().prepareStatement("select * from Reservation where IdClient = ?");
        aubergeInn.stmtReserver = cx.getConnection().prepareStatement("insert into Reservation (IdClient, IdChambre, DateDebut, DateFin) "+ "values (?,?,?,?)");



        // initialisation des transaction concernant le Accommodement
        aubergeInn.stmtChambreDansAcomodement = cx.getConnection().prepareStatement("select * from Accommoder where IdChambre=? AND IdCommodite=?");	
		
	}

	public GestionChambre getGestionChambre ()
    {
        return gestionChambre;
    }

    private void setGestionChambre (GestionChambre gestionLivre)
    {
        this.gestionChambre = gestionLivre;
    }

    public GestionClient getGestionClient()
    {
        return gestionClient;
    }

    private void setGestionClient(GestionClient gestionClient)
    {
        this.gestionClient = gestionClient;
    }



    public GestionReservation getGestionReservation()
    {
        return gestionReservation;
    }

    private void setGestionReservation(GestionReservation gestionReservation)
    {
        this.gestionReservation = gestionReservation;
    }
    
    
    public GestionAccommoder getGestionAccommoder()
    {
        return gestionAccommoder;
    }

    private void setGestionAccommoder(GestionAccommoder gestionAccommoder)
    {
        this.gestionAccommoder = gestionAccommoder;
    }
    
    
    

    public GestionCommodite getGestionCommoditer()
    {
        return gestionCommodite;
    }

    private void setGestionCommodite(GestionCommodite gestionCommodite)
    {
        this.gestionCommodite = gestionCommodite;
    }

    
    
    
    
    
    
    
    
	public GestionChambre gestionLivre() {
		return getGestionChambre();
	}

    public Connexion getConnexion()
    {
        return cx;
    }

	public void fermer() throws SQLException {
		cx.fermer();		
	}
	
	
	

}
