package pit;

import java.io.Serializable;

/* 
 * A Trade carries a commodity card from one Player to another.
 */
public class Trade implements Serializable {
    // The Player originating the Trade
    public int sourcePlayer;
    
    // The commodity being traded
    public String tradeCard;    
}
