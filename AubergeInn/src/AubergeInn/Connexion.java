package AubergeInn;

import java.sql.*;

/**
 * Gestionnaire d une connexion avec une BD relationnelle via JDBC.<br><br>
 * 
 * Cette classe ouvre une connexion avec une BD via JDBC.<br>
 * La methode serveursSupportes() indique les serveurs supportes.<br>
 * <pre>
 * Pre-condition
 *   Le driver JDBC approprie doit être accessible.
 * 
 * Post-condition
 *   La connexion est ouverte en mode autocommit false et serialisable, 
 *   (s il est supporte par le serveur).
 * </pre>
 * <br>
 * IFT287 - Exploitation de BD relationnelles et OO 
 * 
 * @author Marc Frappier - Universite de Sherbrooke
 * @version Version 2.0 - 13 novembre 2004
 * 
 * 
 * @author Vincent Ducharme - Universite de Sherbrooke
 * @version Version 3.0 - 21 mai 2016
 */
public class Connexion
{
	private Connection conn;

	/**
	 * Ouverture d une connexion en mode autocommit false et serialisable (si
	 * supporte)
	 * 
	 * @param serveur Le type de serveur SQL a utiliser (Valeur : local, dinf).
	 * @param bd      Le nom de la base de donnees sur le serveur.
	 * @param user    Le nom d utilisateur a utiliser pour se connecter a la base de donnees.
	 * @param pass    Le mot de passe associe a l utilisateur.
	 */
	public Connexion(String serveur, String bd, String user, String pass)
			throws IFT287Exception, SQLException
	{
		Driver d;
		try
		{
			d = (Driver)Class.forName("org.postgresql.Driver").newInstance();
			DriverManager.registerDriver(d);
			
			if (serveur.equals("local"))
			{
				conn = DriverManager.getConnection("jdbc:postgresql:" + bd, user, pass);
			}
			else if (serveur.equals("dinf"))
			{
				conn = DriverManager.getConnection("jdbc:postgresql://bd-info2.dinf.usherbrooke.ca:5432/" + bd + "?ssl=true&sslmode=require", user, pass);
			}
			else
			{
				throw new IFT287Exception("Serveur inconnu");
			}

			// Mise en mode de commit manuel
			conn.setAutoCommit(false);

			// Mise en mode serialisable, si possible
			// (plus haut niveau d integrite pour l acces concurrent aux donnees)
			DatabaseMetaData dbmd = conn.getMetaData();
			if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE))
			{
				conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				System.out.println("Ouverture de la connexion en mode serialisable :\n"
						+ "Connecte sur la BD postgreSQL "
						+ bd + " avec l utilisateur " + user);
			}
			else
			{
				System.out.println("Ouverture de la connexion en mode read committed (default) :\n"
						+ "Connecte sur la BD postgreSQL "
						+ bd + " avec l utilisateur " + user);
			}
		}
		catch (SQLException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			throw new IFT287Exception("JDBC Driver non instancie");
		}
	}

	/**
	 * Fermeture d une connexion
	 */
	public void fermer() throws SQLException
	{
		conn.rollback();
		conn.close();
		System.out.println("Connexion fermee " + conn);
	}

	/**
	 * Commit
	 */
	public void commit() throws SQLException
	{
		conn.commit();
	}

	public void setIsolationReadCommited() throws SQLException
	{
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	}

	/**
	 * Rollback
	 */
	public void rollback() throws SQLException
	{
		conn.rollback();
	}

	/**
	 * Retourne la Connection JDBC
	 */
	public Connection getConnection()
	{
		return conn;
	}

	public void setAutoCommit(boolean m) throws SQLException
	{
		conn.setAutoCommit(false);
	}

	/**
	 * Retourne la liste des serveurs supportes par ce gestionnaire de
	 * connexions
	 */
	public static String serveursSupportes()
	{
		return "local : PostgreSQL installe localement\n"
			 + "dinf  : PostgreSQL installe sur les serveurs du departement\n";
	}
}
