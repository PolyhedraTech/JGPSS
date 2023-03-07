/**
 * Software end-user license agreement.
 *
 * The LICENSE.TXT containing the license is located in the JGPSS project.
 * License.txt can be downloaded here:
 * href="http://www-eio.upc.es/~Pau/index.php?q=node/28
 *
 * NOTICE TO THE USER: BY COPYING, INSTALLING OR USING THIS SOFTWARE OR PART OF
 * THIS SOFTWARE, YOU AGREE TO THE   TERMS AND CONDITIONS OF THE LICENSE AGREEMENT
 * AS IF IT WERE A WRITTEN AGREEMENT NEGOTIATED AND SIGNED BY YOU. THE LICENSE
 * AGREEMENT IS ENFORCEABLE AGAINST YOU AND ANY OTHER LEGAL PERSON ACTING ON YOUR
 * BEHALF.
 * IF, AFTER READING THE TERMS AND CONDITIONS HEREIN, YOU DO NOT AGREE TO THEM,
 * YOU MAY NOT INSTALL THIS SOFTWARE ON YOUR COMPUTER.
 * UPC IS THE OWNER OF ALL THE INTELLECTUAL PROPERTY OF THE SOFTWARE AND ONLY
 * AUTHORIZES YOU TO USE THE SOFTWARE IN ACCORDANCE WITH THE TERMS SET OUT IN
 * THE LICENSE AGREEMENT.
 */
package model.entities;

import model.Proces;
import model.blocks.Bloc;
import java.io.Serializable;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

/**
 * A class representing the XACT's.
 *
 * @author Pau Fonseca i Casas
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
public class Xact implements Serializable, Cloneable {

    static final long serialVersionUID = 42L;
    
    @Getter @Setter private Proces proces;
    @Getter @Setter private Bloc bloc;
    @Getter @Setter private Bloc scheduledBloc;
    @Getter @Setter private int ID;
    @Getter @Setter private float creatTime;
    @Getter @Setter private float moveTime;
    @Getter @Setter private float priority;
    @Getter private final HashMap<String, Object> transactionParameters;    
    // Delay indicator (Used in Simultaneous mode in a TRANSFER block)    
    @Getter @Setter private boolean delayed;    
    // Restore indicator (Used to indicate if the Xact must be restored to the FEC)
    private boolean restore;    
    // Used to set the nextBloc the Xact must be routed (Used for FUNAVAIL blocks)
    @Getter @Setter private Bloc blockRoute;
    // Used to grant ownership to the transaction on a facility unavailable period
    @Getter @Setter private boolean ownershipGranted;
    // Used to indentify the transaction current assembly set 
    @Getter @Setter private int assemblySet;
    // Used in Assemble block
    @Getter @Setter private int counter;
    
    /**
     * Constructor
     */
    public Xact() {
        transactionParameters = new HashMap<>();
        delayed = false;
        restore = false;
        blockRoute = null;
        ownershipGranted = false;
        counter = 0;
    }    
    
    /**
     * Return true if the Xact must be restored to the FEC, false otherwise
     * @return 
     */
    public boolean restoreToFEC() {
        return restore;
    }
    
    /**
     * Sets the Xact restore indicator to value
     * @param value
     */
    public void restore(boolean value) {
        this.restore = value;
    }   

    public Object getParameter(String paramName) {
        return transactionParameters.getOrDefault(paramName, null);
    }

    /**
     * Decrements the current counter and returns it
     * @return 
     */
    public int decCounter() {
        counter--;
        return counter;
    }
    
    /**
     * Allows to obtain a copy of the object
     * @return
     * @throws CloneNotSupportedException 
     */
    @Override
    public Xact clone() throws CloneNotSupportedException {
        return (Xact) super.clone();
    }

    public float getMoveTime() {
        return this.moveTime;
    }
}
