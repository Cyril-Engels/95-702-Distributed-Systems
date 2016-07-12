package pit;
import java.io.Serializable;

/*
 * A Marker, as used in the snapshot algorithm.
 */
public class Marker implements Serializable {
    // source is the Player number from which the Marker is sent
    int source;

    
    public Marker(int source) {
        this.source = source;
    }
}
