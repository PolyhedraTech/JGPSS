package utils;

/**
 * Software end-user license agreement.
 *
 * The LICENSE.TXT containing the license is located in the JGPSS project.
 * License.txt can be downloaded here:
 * href="http://www-eio.upc.es/~Pau/index.php?q=node/28
 *
 * NOTICE TO THE USER: BY COPYING, INSTALLING OR USING THIS SOFTWARE OR PART OF
 * THIS SOFTWARE, YOU AGREE TO THE TERMS AND CONDITIONS OF THE LICENSE AGREEMENT
 * AS IF IT WERE A WRITTEN AGREEMENT NEGOTIATED AND SIGNED BY YOU. THE LICENSE
 * AGREEMENT IS ENFORCEABLE AGAINST YOU AND ANY OTHER LEGAL PERSON ACTING ON
 * YOUR BEHALF. IF, AFTER READING THE TERMS AND CONDITIONS HEREIN, YOU DO NOT
 * AGREE TO THEM, YOU MAY NOT INSTALL THIS SOFTWARE ON YOUR COMPUTER. UPC IS THE
 * OWNER OF ALL THE INTELLECTUAL PROPERTY OF THE SOFTWARE AND ONLY AUTHORIZES
 * YOU TO USE THE SOFTWARE IN ACCORDANCE WITH THE TERMS SET OUT IN THE LICENSE
 * AGREEMENT.
 */
import java.io.Serializable;
import java.util.HashMap;
import model.blocks.Bloc;
import model.Model;
import model.reports.Report;
import model.entities.rng.Uniform;
import persistence.DDiscManager;
import model.entities.rng.RNG;

/**
 * A class representing the gloval variables of the model.
 *
 * @author Pau Fonseca i Casas
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
public class VarGlobals implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The title of the current model.
     */
    public static String titol;
    /**
     * The description of the current model.
     */
    public static String Descripcio;
    /**
     * Contains the URL of the website that host the JGPSS project.
     */
    public static String url;
    /**
     * Needed to know if we are continuing in the edition or execution of the
     * model.
     */
    public static boolean continuar;
    /**
     * Varialbe that contains the last selected block.
     */
    public static Bloc bloc;
    
    /**
     * Current block id being modified or created
     */
    public static int blocId;
    /**
     * The variable containing the model.
     */
    public static Model model;
    /**
     * url to save the file.
     */
    public static String urlGuardar;
    /**
     * To know if we are opening a model or not.
     */
    public static boolean Abrir;
    /**
     * To know if the model has been saved.
     */
    public static boolean esGuardat;
    /**
     * To know if the model has been modified.
     */
    public static boolean esModificat;
    /**
     * Name of the selected STORAGE.
     */
    public static String nomStorageSeleccionat;
    
    public static String nameFunctionSelected;
    
    public static String nameSaveValueSelected;
    
    public static String nameSeizeSelected;

    public static int valueSelectedStorage;

    /**
     * Disc manegament variables.
     */
    public static DDiscManager dg = new DDiscManager(model);
    /**
     * Transaction counter.
     */
    public static int TC;

    /**
     * To know the current assembly set id
     */
    public static int currentAssemblySet = 0;

    /**
     * Current GNA
     */
    public static HashMap<String, RNG> gna = new HashMap<>();

    /**
     * GNA Types
     */
    public static enum GNAType {
        UNIFORM
    }
    
    /**
     * Selected report type
     */
    public static Report selectedReport;   
    
    /**
     * Creates a new instance of VarGlobals.
     */
    public VarGlobals() {
    }

    /**
     * To clean the global variables.
     */
    public static void limpiarGlobales() {
        //posar les variables netes
        titol = null;
        //strBloc= null;
        Descripcio = null;
        url = null;
        bloc = null;
        model = null;
        urlGuardar = null;
        esGuardat = false;
        Abrir = false;
        currentAssemblySet = 1;
        //idNumBloc =0;
    }

    public static RNG getGNA(String gnaType) {
        switch (gnaType.toUpperCase()) {
            case "UNIFORM":
                return new Uniform();           
            default:
                return new Uniform();
        }
    }
}
