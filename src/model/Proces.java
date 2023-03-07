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
package model;

/*
 * Proces.java
 *
 * Created on 28 de diciembre de 2006, 12:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.util.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Model;
import model.blocks.Bloc;

/**
 * A class representing the process to be followed by the entities.
 *
 * @author Pau Fonseca i Casas
 * @autor M� Dolores
 * @author Ezequiel Andujar Montes
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
@Data
@SuppressWarnings("FieldMayBeFinal")
@NoArgsConstructor
public class Proces implements Serializable {

    private final static long serialVersionUID = 1L;
    private ArrayList<Bloc> blocs;
    private Model GPSSmodel;
    private String descpro;
    private int posx;
    private int posy;
    private int finposx;
    private int finposy;

    /**
     * Creates a new instance of Proces
     *
     * @param descripcio description of the process
     * @param model the model of the process.
     */
    public Proces(String descripcio, Model model) {

        this.descpro = descripcio;
        this.GPSSmodel = model;
        this.blocs = new ArrayList<>();
    }

    /**
     * To set the array containing the blocks of the process.
     *
     * @param blocs the new array.
     */
    public void setBlocs(ArrayList<Bloc> blocs) {

        for (int i = 0; i < blocs.size(); i++) {
            blocs.get(i).setProces(this);
            blocs.get(i).setModel(GPSSmodel);
            blocs.get(i).setPos(i);
        }
        this.blocs = blocs;
    }

    /**
     * Returns the bloc with an specific label
     *
     * @param label
     * @return
     */
    public Bloc findBloc(String label) {

        return blocs.stream()
                .filter(b -> b.getLabel().equals(label))
                .findFirst()
                .orElse(null);
    }
    
    public int lastPos() {
        return !blocs.isEmpty() ? blocs.size() - 1 : 0;
    }
}
