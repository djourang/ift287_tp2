package AubergeInn.tuples;

import java.math.BigDecimal;





/**
 * Permet de representer un tuple Commodite de l'auberge.
 */
public class TupleCommodite
{

    
    
    
    private int idCommodite;
    private String description;
    private BigDecimal  surplusPrix;
    
    
    
    
    public TupleCommodite()
    {
    	
    }
    
    public TupleCommodite(int idCommodite, String description, BigDecimal surplusPrix)
    {
        this.idCommodite=idCommodite;
        this.description=description;
        this.surplusPrix=surplusPrix;
        
    }
    
    
  
    
  
}
