package utils;

import java.util.HashMap;

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
/**
 * A class representing the constants of the JGPSS system.
 *
 * @author Pau Fonseca i Casas
 * @author M.Dolores
 * @version 1
 * @see     <a href="http://www-eio.upc.es/~Pau/index.php?q=node/28">Project
 * website</a>
 * @serialData
 */
public class Constants {

    //Constatns del bloc Generate
    /**
     * String defining GENERATE.
     */
    public final static String Generate = "Generate";
    /**
     * GENERATE icon.
     */
    public final static String UrlGenerate = "/imagenes/generate.GIF";
    /**
     * GENERATE identifier.
     */
    public final static int idGenerate = 1;
    //constants del bloc Terminate
    /**
     * String defining TERMINATE.
     */
    public final static String Terminate = "Terminate";
    /**
     * TERMINATE icon.
     */
    public final static String UrlTerminate = "/imagenes/terminate.GIF";
    /**
     * TERMINATE identifier.
     */
    public final static int idTerminate = 2;
    //constants del bloc Advanced
    /**
     * String defining ADVANCE.
     */
    public final static String Advanced = "Advance";
    /**
     * ADVANCE icon.
     */
    public final static String UrlAdvanced = "/imagenes/advance.GIF";
    /**
     * ADVANCE identifier.
     */
    public final static int idAdvanced = 3;

    //constants del bloc Seize
    /**
     * String defining SEIZE.
     */
    public final static String Seize = "Seize";
    /**
     * SEIZE icon.
     */
    public final static String UrlSeize = "/imagenes/seize1.GIF";
    /**
     * SEIZE identifier.
     */
    public final static int idSeize = 4;

    //constants del bloc Release
    /**
     * String defining RELEASE.
     */
    public final static String Release = "Release";
    /**
     * RELEASE icon.
     */
    public final static String UrlRelease = "/imagenes/release.GIF";
    /**
     * RELEASE identifier.
     */
    public final static int idRelease = 5;

    //constants del bloc Enter
    /**
     * String defining ENTER.
     */
    public final static String Enter = "Enter";
    /**
     * ENTER icon.
     */
    public final static String UrlEnter = "/imagenes/enter.GIF";
    /**
     * ENTER identifier.
     */
    public final static int idEnter = 6;

    //constants del bloc LEAVE
    /**
     * String defining LEAVE.
     */
    public final static String Leave = "Leave";
    /**
     * LEAVE icon.
     */
    public final static String UrlLeave = "/imagenes/leave.GIF";
    /**
     * LEAVE identifier.
     */
    public final static int idLeave = 7;

    //constants del bloc QUEUE
    /**
     * String defining QUEUE.
     */
    public final static String Queue = "Queue";
    /**
     * QUEUE icon.
     */
    public final static String UrlQueue = "/imagenes/queue.GIF";
    /**
     * QUEUE identifier.
     */
    public final static int idQueue = 8;

    //constants del bloc DEPART.
    /**
     * String defining DEPART.
     */
    public final static String Depart = "Depart";
    /**
     * DEPART icon.
     */
    public final static String UrlDepart = "/imagenes/depart.GIF";
    /**
     * DEPART identifier.
     */
    public final static int idDepart = 9;

    //constatnts del bloc test
    /**
     * String defining TEST.
     */
    public final static String Test = "Test";
    /**
     * TEST icon.
     */
    public final static String UrlTest = "/imagenes/test.GIF";
    /**
     * TEST identifier.
     */
    public final static int idTest = 10;

    //constants del bloc Transfer
    /**
     * String defining TRANSFER.
     */
    public final static String Transfer = "Transfer";
    /**
     * TRANSFER icon.
     */
    public final static String UrlTransfer = "/imagenes/transfer2.GIF";
    /**
     * TRANSFER identifier.
     */
    public final static int idTransfer = 11;

    //constants del bloc Logic
    /**
     * String defining LOGIC
     */
    public final static String Logic = "Logic";
    /**
     * LOGIC icon.
     */
    public final static String UrlLogic = "/imagenes/logic2.GIF";
    /**
     * LOGIC identifier.
     */
    public final static int idLogic = 12;

    //constants del bloc Gate
    /**
     * String defining GATE.
     */
    public final static String Gate = "Gate";
    /**
     * GATE icon.
     */
    public final static String UrlGate = "/imagenes/gate.GIF";
    /**
     * GATE identifier.
     */
    public final static int idGate = 13;

    //constants del bloc SAVEVALUE
    /**
     * String defining SAVEVALUE.
     */
    public final static String Savevavg = "SavevAvg";
    /**
     * SAVEVALUE icon.
     */
    public final static String UrlSavevavg = "/imagenes/Savevavg.GIF";
    /**
     * SAVEVALUE identifier.
     */
    public final static int idSavevavg = 14;

    //constants del bloc Loop
    /**
     * String defining LOOP.
     */
    public final static String Loop = "Loop";
    /**
     * LOOP icon.
     */
    public final static String UrlLoop = "/imagenes/loop.GIF";
    /**
     * LOOP identifier.
     */
    public final static int idLoop = 15;

    //constants del bloc Split
    /**
     * String defining SPLIT.
     */
    public final static String Split = "Split";
    /**
     * SPLIT icon.
     */
    public final static String UrlSplit = "/imagenes/split.GIF";
    /**
     * SPLIT identiier.
     */
    public final static int idSplit = 16;

    //Funavail
    /**
     * String defining FUNAVAIL.
     */
    public final static String Funavail = "Funavail";
    /**
     * FUNAVAIL icon.
     */
    public final static String UrlFunavail = "/imagenes/funavail.GIF";
    /**
     * FUNAVAIL identifier.
     */
    public final static int idFunavail = 17;

    //Savail
    /**
     * String defining SAVAIL.
     */
    public final static String Savail = "Savail";
    /**
     * SAVAIL icon.
     */
    public final static String UrlSavail = "/imagenes/savail.GIF";
    /**
     * SAVAIL identifier.
     */
    public final static int idSavail = 18;

    //Assemble
    /**
     * String defining ASSEMBLE.
     */
    public final static String Assemble = "Assemble";
    /**
     * ASSEMBLE icon.
     */
    public final static String UrlAssemble = "/imagenes/assemble.GIF";
    /**
     * ASSEMBLE identifier.
     */
    public final static int idAssemble = 19;

    //Gather
    /**
     * String defining GATHER.
     */
    public final static String Gather = "Gather";
    /**
     * GATHER icon.
     */
    public final static String UrlGather = "/imagenes/gather.GIF";
    /**
     * GATHER identifier.
     */
    public final static int idGather = 20;

    //Match
    /**
     * String defining MATCH.
     */
    public final static String Match = "Match";
    /**
     * MATCH icon.
     */
    public final static String UrlMatch = "/imagenes/match.GIF";
    /**
     * MATCH identifier.
     */
    public final static int idMatch = 21;

    //Priority
    /**
     * String defining PRIORITY.
     */
    public final static String Priority = "Priority";
    /**
     * PRIORITY icon.
     */
    public final static String UrlPriority = "/imagenes/priority.GIF";
    /**
     * PRIORITY identifier.
     */
    public final static int idPriority = 22;

    //Buffer
    /**
     * String defining BUFFER.
     */
    public final static String Buffer = "Buffer";
    /**
     * BUFFER icon.
     */
    public final static String UrlBuffer = "/imagenes/buffer.GIF";
    /**
     * BUFFER identifier.
     */
    public final static int idBuffer = 23;

    //Sunavail
    /**
     * String defining SUNAVAIL.
     */
    public final static String Sunavail = "Sunavail";
    /**
     * SUNAVAIL icon.
     */
    public final static String UrlSunavail = "/imagenes/sunavail.GIF";
    /**
     * SUNAVAIL identifier.
     */
    public final static int idSunavail = 24;

    //Favail
    /**
     * String defining FAVAIL.
     */
    public final static String Favail = "Favail";
    /**
     * FAVAIL icon.
     */
    public final static String UrlFavail = "/imagenes/favail.GIF";
    /**
     * FAVAIL identifier.
     */
    public final static int idFavail = 25;

    //Assign
    /**
     * String defining ASSIGN.
     */
    public final static String Assign = "Assign";
    /**
     * ASSIGN icon.
     */
    public final static String UrlAssign = "/imagenes/assign.GIF";
    /**
     * ASSIGN identifier.
     */
    public final static int idAssign = 26;

    //The next texts are useful for internationalization purposes.
    /**
     * Entity Generation text.
     */
    public final static String PestanyaGenerate = "Entity Generation";
    /**
     * Servers text.
     */
    public final static String PestanyaServer = "Servers";
    /**
     * Syntax error text.
     */
    public final static String ErrorParam = "Syntax Error";
    /**
     * Syntax error in operand description text.
     */
    public final static String ErrorDescrip = "Syntax Error in operand description";
    /**
     * Synta error in model name text.
     */
    public final static String ErrorNomModel = "Syntax Error in Model Name";
    /**
     * Syntax error un model description text.
     */
    public final static String ErrorDescripModel = "Syntax Error in Model Description";
    /**
     * Proces name repeated text.
     */
    public final static String ErrorDescripRepetida = "Proces name repeated";
    /**
     * Syntax Error in storage text.
     */
    public final static String ErrorFaltaStorage = "Syntax Error in storage";
    /**
     * Syntax Error in Operand X text.
     */
    public final static String ErrorFaltaX = "Syntax Error in Operand X ";
    /**
     * Syntax Error in Operand A text.
     */
    public final static String ErrorFaltaA = "Syntax Error in Operand A";

    /**
     * Error opening file
     */
    
    public final static String ERROR_OPENING_FILE = "Error opening file";
    
    /**
     * * text.
     */
    public final static String asterisco = "*";
    //Segun en el sistema operativo donde estemos
    /**
     * carriage return text.
     */
    public final static String saltoLinea = System.getProperty("line.separator");
    /**
     * space text.
     */
    public final static String espacio = " ";
    /**
     * five spaces text.
     */
    public final static String cincoEspacios = "     ";
    /**
     * space reserved fo the label text.
     */
    public final static String espaisLabel = "        ";
    /**
     * comma text.
     */
    public final static String coma = ",";
    /**
     * semicolon text.
     */
    public final static String puntoycoma = ";";
    /**
     * spaces reserved for the blocs text.
     */
    public final static int numCaractersBloc = 16;

    /**
     * save text.
     */
    public final static String guardar = "Save";
    /**
     * no text.
     */
    public final static String salir = "No";
    /**
     * Open text.
     */
    public final static String abrir = "Open";
    /**
     * Cancel text.
     */
    public final static String cancel = "Cancel";
    /**
     * Delete text.
     */
    public final static String borrar = "Delete";
    /**
     * Overwrite text.
     */
    public final static String sobreescribir = "Overwrite";

    /**
     * the file text.
     */
    public final static String elarchivo = "the file: ";
    /**
     * the file exists text.
     */
    public final static String archivoexiste = " allready exists, what do you want to do?";

    /**
     * Exit of the application text.
     */
    public final static String SalirAplicacion = "Do you want to save the model before leave?";
    /**
     * Cauiton text.
     */
    public final static String caution = "caution";
    /**
     * Sure to leave text.
     */
    public final static String Seguro = "Do you want to leave?";
    /**
     * Yes text.
     */
    public final static String Si = "Yes";
    /**
     * No text.
     */
    public final static String No = "No";

    /**
     * To define the layout of the JGPSS system.
     */
    public final static int x = 160;
    /**
     * To define the layout of the JGPSS system.
     */
    public final static int y = 60;
    /**
     * To define the layout of the JGPSS system.
     */
    public final static int distanciaX = 60;

    /**
     * Delete block text.
     */
    public final static String BorrarBloc = "Do you want to delete this block?";
    /**
     * Delete process text.
     */
    public final static String BorrarProces = "Do you want to delete this proces?";

    //constants per a fer la pantallaDescripcioBloc segons si volem consultar o modifocar
    /**
     * 1 if we are modifying the block.
     */
    public final static int ModificarBloc = 1;
    /**
     * 2 if we are consulting the block.
     */
    public final static int ConsultarBloc = 2;

    /**
     * 1 if we are modifying the process.
     */
    public final static int ModificarProces = 1;
    /**
     * 2 if we are consulting the process.
     */
    public final static int ConsultarProces = 2;

    /**
     * Internat error text.
     */
    public final static String errorInterno = "there is the error: ";
    /**
     * Error generating the file.
     */
    public final static String errortxt = "Mistake on having generated the file";
    /**
     * Process need a name text.
     */
    public final static String errorvalornull = "you must put the name proces";
    /**
     * Error accesing a block text.
     */
    public final static String errorconsultarbloc = "consult/modify error";
    /**
     * Data error text.
     */
    public final static String errorDades = "Syntax error";
    /**
     * Error recovering a file text.
     */
    public final static String errorRecuperar = "Mistake on having recovered the file";

    public final static String malformedNumber = "Error malformed number";

    /**
     * .txt text.
     */
    public final static String extensio = ".txt";

    /**
     * A text.
     */
    public final static String A = "A";
    /**
     * B text.
     */
    public final static String B = "B";
    /**
     * C text.
     */
    public final static String C = "C";
    /**
     * D text.
     */
    public final static String D = "D";
    /**
     * E text.
     */
    public final static String E = "E";
    /**
     * F text.
     */
    public final static String F = "F";
    /**
     * G text.
     */
    public final static String G = "G";
    /**
     * X text.
     */
    public final static String X = "X";
    /**
     * Time between arribls text.
     */
    public final static String tempsEntreArribades = "temps entre arribades";

    /**
     * STORAGE text.
     */
    public final static String STORAGE = "STORAGE";

    /**
     * SAVEVALUE text.
     */
    public final static String SAVEVALUE = "SAVEVALUE";   
   
    
    /**
     * FUNCTION text.
     */
    public final static String FUNCTION = "FUNCTION";

    /**
     * MATRIX text.
     */
    public final static String MATRIX = "MATRIX";

    /**
     * Open icon.
     */
    public final static String ImagenAbrir = "/imagenes/Package.png";
    /**
     * Note icon.
     */
    public final static String ImagenNuevo = "/imagenes/Note.png";
    /**
     * Save icon.
     */
    public final static String ImagenGuardar = "/imagenes/save2.gif";
    /**
     * White image icon.
     */
    public final static String ImagenBlanco = "/imagenes/blanco.gif";
    /**
     * Main title text.
     */
    public final static String tituloPrincipal = "JGPSS, learning GPSS with Java.";
    /**
     * New model text.
     */
    public final static String tituloNewModel = "New Model";
    /**
     * New process text.
     */
    public final static String tituloNewProces = "New Proces";
    /**
     * New block text.
     */
    public final static String tituloNewBloc = "New Bloc";
    /**
     * Storages text.
     */
    public final static String tituloStorages = "Storages";
    
    /**
     * Function text 
     */
    
    public final static String tituloFunction = "Functions";

    /**
     * Seize text
     */
    public final static String seizeTitle = "Seizes";

    /**
     * Save Values text.
     */
    public final static String SaveValueTitle = "SaveValue";

    /**
     * Error text.
     */
    public final static String tituloError = "Error";

    /**
     * To define the layout of the JGPSS system.
     */
    public static int xScroll = 14;
    /**
     * To define the layout of the JGPSS system.
     */
    public static int yScroll = 12;
    /**
     * To define the layout of the JGPSS system.
     */
    public static int tabWidth = 181;
    /**
     * To define the layout of the JGPSS system.
     */
    public static int tabHeight = 439;
    /**
     * To define the layout of the JGPSS system.
     */
    public static int jPanelModelWidth = 705;
    /**
     * To define the layout of the JGPSS system.
     */
    public static int jPanelModelHeight = 613;

    /**
     * Txt report type
     */
    public static String txtReport = "txt";
    
    /**
     * Screen Report
     */
    public static String screenReport = "screen";

    /**
     * Pdf report type
     */
    public static String pdfReport = "pdf";

    /**
     *
     */
    public static String csvReport = "csv";

    public static HashMap<Integer, String> blocName;

    public static String simulationEnd = "Simulation end";

    /**
     * Amper variables title
     */
    public static String AmperVariableTitle = "Amper Variables";

    public final static String INTEGER = "INTEGER";
    public final static String REAL = "REAL";
    public final static String STRING = "STRING";
    
    

    /**
     * New instance of constants.
     */
    public Constants() {
        blocName = new HashMap<>();
        blocName.put(1, "Generate");
        blocName.put(2, "Terminate");
        blocName.put(3, "Advance");
        blocName.put(4, "Seize");
        blocName.put(5, "Release");
        blocName.put(6, "Enter");
        blocName.put(7, "Leave");
        blocName.put(8, "Queue");
        blocName.put(9, "Depart");
        blocName.put(10, "Test");
        blocName.put(11, "Transfer");
        blocName.put(12, "Logic");
        blocName.put(13, "Gate");
        blocName.put(14, "SaveValue");
        blocName.put(15, "Loop");
        blocName.put(16, "Split");
        blocName.put(17, "Funavail");
        blocName.put(18, "Savail");
        blocName.put(19, "Assemble");
        blocName.put(20, "Gather");
        blocName.put(21, "Match");
        blocName.put(22, "Priority");
        blocName.put(23, "Buffer");
        blocName.put(24, "Sunavail");
        blocName.put(25, "Favail");
        blocName.put(26, "Assign");
    }
};
