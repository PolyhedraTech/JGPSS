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
package persistence;

/**
 * Autor: Francisco G�mez Gonz�lez Cluster: 2 Grupo: 2.2 Versi�n: 1.0
 * Fecha: 04-11-05
 */
import exceptions.MalformedFunctionDistributionException;
import exceptions.UnrecognizedModelException;
import model.Proces;
import model.entities.Storage;
import model.blocks.*;
import utils.VarGlobals;
import utils.Constants;
import java.io.*;
import java.util.ArrayList;
import lombok.NoArgsConstructor;
import model.*;
import model.entities.AmperVariable;
import model.entities.Function;
import model.entities.SaveValue;
import model.entities.rng.RNG;

/**
 * Clase que escribe y lee de disco objetos serializables.
 */
@NoArgsConstructor
public class DiscManager {

    private Model GPSSmodel;

    public DiscManager(Model model) {
        GPSSmodel = model;
    }

    /*
   * Explicaci�n de guardaObjeto
   * PRE:-
   * POST: El objeto o se ha guardado en disco con el nombre nomFichero
   * @param o Objeto el cual queremos guardar en disco
   * @param nomFichero Nombre con el cual queremos guardar el objeto
   * @exception FileNotFoundException No se ha podido crear el fichero
   * @exception IOException No se ha podido realizar la entrada/salida
     */
    public void guardaObjeto(Object o, String nomFichero) throws IOException, FileNotFoundException {
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(new FileOutputStream(nomFichero));
        oos.writeObject(o);
        oos.close();
    }

    /*
   * Explicaci�n de la recuperaObjeto
   * PRE:-
   * POST: Retorna el objeto recuperado con el nombre nomFichero
   * @param nomFichero Nombre del objeto que queremos recuperar de disco
   * @exception FileNotFoundException No se ha podido crear el fichero
   * @exception IOException No se ha podido realizar la entrada/salida
   * @exception ClassNotFoundException El fichero no contiene una clase valida
     */
    public Object recuperaObjeto(String nomFichero) throws IOException, ClassNotFoundException {
        ObjectInputStream ois;
        ois = new ObjectInputStream(new FileInputStream(nomFichero));
        return ois.readObject();
    }

    public void guardarTxt(String url) throws IOException {
        FileWriter guardx;
        guardx = new FileWriter(url);
        guardx.write(generarTxt());
        guardx.close();
    }

    private String generarTxt() throws IOException {

        Model model = VarGlobals.model;
        StringBuffer textModel;
        textModel = new StringBuffer();

        writeTitle(model, textModel);
        writeStorages(model, textModel);
        writeSaveValues(model, textModel);
        writeAmperVariables(model, textModel);
        writeFunctions(model, textModel);
        writeEndEntities(textModel);
        writeProcess(model, textModel);

        return textModel.toString();
    }

    private String escriurebloc(Bloc b) throws IOException {

        StringBuilder bufBloc = new StringBuilder();
        String gnaName;
        int id = b.getId();
        int espacios;
        bufBloc.append(b.getLabel());
        espacios = Constants.espaisLabel.length() - b.getLabel().length();
        for (int i = 0; i < espacios; i++) {
            bufBloc.append(Constants.espacio);
        }
        switch (id) {
            case Constants.idGenerate:
                Generate g = (Generate) b;
                bufBloc.append(Constants.Generate);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Generate.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                //datos
                //A

                gnaName = g.getGna().name();
                bufBloc.append(gnaName);
                bufBloc.append(Constants.coma);
                bufBloc.append(g.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(g.getB());
                bufBloc.append(Constants.coma);
                bufBloc.append(Float.toString(g.getC()));
                bufBloc.append(Constants.coma);
                bufBloc.append(Float.toString(g.getD()));
                bufBloc.append(Constants.coma);
                bufBloc.append(Float.toString(g.getE()));
                bufBloc.append(Constants.coma);
                bufBloc.append(Integer.toString(g.getF()));

                break;
            case Constants.idFunavail:
                Funavail fun = (Funavail) b;
                bufBloc.append(Constants.Funavail);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Funavail.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                //datos
                //A
                bufBloc.append(fun.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(fun.getB());
                bufBloc.append(Constants.coma);
                bufBloc.append(fun.getC());
                bufBloc.append(Constants.coma);
                bufBloc.append(fun.getD());
                bufBloc.append(Constants.coma);
                bufBloc.append(fun.getE());
                bufBloc.append(Constants.coma);
                bufBloc.append(fun.getF());

                break;
            case Constants.idTerminate:

                Terminate t = (Terminate) b;
                bufBloc.append(Constants.Terminate);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Terminate.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                //datos
                //A
                bufBloc.append(Integer.toString(t.getA()));
                break;

            case Constants.idAdvanced:
                Advance a = (Advance) b;
                bufBloc.append(Constants.Advanced);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Advanced.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                //datos
                //A

                bufBloc.append(a.getGna().name());
                bufBloc.append(Constants.coma);
                bufBloc.append(a.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(a.getB());
                break;
            case Constants.idAssign:
                Assign as = (Assign) b;
                bufBloc.append(Constants.Assign);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Assign.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                //datos
                //A
                bufBloc.append(as.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(Float.toString(as.getB()));
                break;
            case Constants.idDepart:
                Depart d = (Depart) b;
                bufBloc.append(Constants.Depart);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Depart.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                //datos
                //A
                bufBloc.append(d.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(Integer.toString(d.getB()));
                break;

            case Constants.idQueue:
                Queue q = (Queue) b;
                bufBloc.append(Constants.Queue);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Queue.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                //datos
                //A
                bufBloc.append(q.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(Integer.toString(q.getB()));
                break;
            case Constants.idEnter:
                Enter e = (Enter) b;
                bufBloc.append(Constants.Enter);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Enter.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                //datos
                //A
                bufBloc.append(e.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(Integer.toString(e.getB()));
                break;
            case Constants.idLeave:
                Leave l = (Leave) b;
                bufBloc.append(Constants.Leave);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Leave.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                //datos
                //A
                bufBloc.append(l.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(Integer.toString(l.getB()));
                break;
            case Constants.idRelease:
                Release r = (Release) b;
                bufBloc.append(Constants.Release);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Release.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(r.getA());
                break;
            case Constants.idSeize:
                Seize s = (Seize) b;
                bufBloc.append(Constants.Seize);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Seize.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(s.getA());
                break;
            //
            case Constants.idTest:
                Test test = (Test) b;
                bufBloc.append(Constants.Test);
                bufBloc.append(Constants.espacio);
                bufBloc.append(test.getX());
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Test.length() - Constants.espacio.length() - test.getX().length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }

                bufBloc.append(test.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(test.getB());
                bufBloc.append(Constants.coma);
                bufBloc.append(test.getC());

                break;
            case Constants.idTransfer:
                Transfer transf = (Transfer) b;
                bufBloc.append(Constants.Transfer);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Transfer.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(transf.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(transf.getB());
                bufBloc.append(Constants.coma);
                bufBloc.append(transf.getC());
                bufBloc.append(Constants.coma);
                bufBloc.append(transf.getD());
                break;
            case Constants.idLogic:
                Logic log = (Logic) b;
                bufBloc.append(Constants.Logic);
                bufBloc.append(Constants.espacio);
                bufBloc.append(log.getX());
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Logic.length() - Constants.espacio.length() - log.getX().length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }

                bufBloc.append(log.getA());
                break;
            case Constants.idGate:
                Gate gate = (Gate) b;
                bufBloc.append(Constants.Gate);
                bufBloc.append(Constants.espacio);
                bufBloc.append(gate.getX());
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Gate.length() - Constants.espacio.length() - gate.getX().length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(gate.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(gate.getB());
                break;
            case Constants.idSavevavg:
                Savevalue sav = (Savevalue) b;
                bufBloc.append(Constants.Savevavg);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Savevavg.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(sav.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(sav.getB());
                break;
            case Constants.idLoop:
                Loop loop = (Loop) b;
                bufBloc.append(Constants.Loop);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Loop.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(loop.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(loop.getB());
                break;

            case Constants.idSplit:
                Split split = (Split) b;
                bufBloc.append(Constants.Split);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Split.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(split.getA());
                bufBloc.append(Constants.coma);
                bufBloc.append(split.getB());
                bufBloc.append(Constants.coma);
                bufBloc.append(split.getC());
                break;
            case Constants.idSavail:
                Savail savail = (Savail) b;
                bufBloc.append(Constants.Savail);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Savail.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(savail.getA());
                break;
            case Constants.idSunavail:
                Sunavail sunavail = (Sunavail) b;
                bufBloc.append(Constants.Sunavail);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Sunavail.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(sunavail.getA());
                break;
            case Constants.idAssemble:
                Assemble ass = (Assemble) b;
                bufBloc.append(Constants.Assemble);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Assemble.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(ass.getA());
                break;

            case Constants.idGather:
                Gather gather = (Gather) b;
                bufBloc.append(Constants.Gather);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Gather.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(gather.getA());

                break;

            case Constants.idMatch:
                Match match = (Match) b;
                bufBloc.append(Constants.Match);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Match.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(match.getA());

                break;
            case Constants.idPriority:
                Priority pri = (Priority) b;
                bufBloc.append(Constants.Priority);
                //espacios hasta llegas a 16
                espacios = Constants.numCaractersBloc - Constants.Priority.length();
                for (int i = 0; i < espacios; i++) {
                    bufBloc.append(Constants.espacio);
                }
                bufBloc.append(pri.getA());

                break;
            case Constants.idBuffer:
                //Buffer buffer = (Buffer)b;
                bufBloc.append(Constants.Buffer);

                break;
            //   
            default:
                break;
        }
        //si existeix el comentari
        if (!(b.getComentari().equals("")) && (b.getComentari() != null)) {
            bufBloc.append(Constants.cincoEspacios);
            bufBloc.append(Constants.puntoycoma);
            bufBloc.append(b.getComentari());
        }
        return bufBloc.toString();
    }

    public void recuperarTxt(BufferedReader entrada) throws UnrecognizedModelException, IOException, MalformedFunctionDistributionException {
        txtToModel(entrada);
    }

    private void txtToModel(BufferedReader entrada) throws UnrecognizedModelException, IOException, MalformedFunctionDistributionException {
        Model m = new Model();
        setGPSSmodel(m);
        ArrayList<Proces> p = new ArrayList<>();
        ArrayList<Storage> storages = new ArrayList<>();
        ArrayList<SaveValue> saveValues = new ArrayList<>();
        ArrayList<AmperVariable<?>> amperVariables = new ArrayList<>();
        ArrayList<Bloc> blocs = new ArrayList<>();
        ArrayList<Function> functions = new ArrayList<>();
        String nomModel = "";
        String descModel = "";
        String nomProces = "";
        Proces pro;
        Storage stor;
        int linea, cont, xini, yini, xfin, yfin;
        cont = 1;
        xini = 0;
        yini = xfin = xini;
        yfin = Constants.y;
        linea = 1;
        String s;

        // parse header information
        while (linea <= 4) {
            s = entrada.readLine();
            switch (linea) {
                case 1:
                case 4:
                    if (!s.equals(Constants.asterisco)) {
                        throw new UnrecognizedModelException();
                    }
                    break;
                case 2:
                    if (!s.substring(0, 1).equals(Constants.asterisco)) {
                        throw new UnrecognizedModelException();
                    }
                    nomModel = s.substring(2);
                    break;
                case 3:
                    if (!s.substring(0, 1).equals(Constants.asterisco)) {
                        throw new UnrecognizedModelException();
                    }
                    descModel = s.substring(2);
                    break;
            }
            linea++;
        }

        // parse entities
        while (!(s = entrada.readLine()).contains("* end-entities")) {

            if (!s.equals("")) {

                String vEntity[] = s.split(Constants.espacio);
                String entityName = vEntity[0];
                String entityType = vEntity[1];
                String entityValue = vEntity[2];

                switch (entityType) {

                    case Constants.FUNCTION:

                        String A = vEntity[2];
                        String B = vEntity[3];
                        String dist = vEntity[4];
                        Function function = new Function(entityName, A, B, dist);
                        functions.add(function);
                        break;

                    case Constants.INTEGER:
                        Integer iValue = new Integer(entityValue);
                        AmperVariable<Integer> avi = new AmperVariable<>(entityName, iValue);
                        amperVariables.add(avi);
                        break;

                    case Constants.REAL:
                        Float fValue = new Float(entityValue);
                        AmperVariable<Float> avf = new AmperVariable<>(entityName, fValue);
                        amperVariables.add(avf);
                        break;

                    case Constants.STRING:
                        AmperVariable<String> avs = new AmperVariable<>(entityName, entityValue);
                        amperVariables.add(avs);
                        break;

                    case Constants.STORAGE:
                        storages.add(new Storage(entityName, new Integer(vEntity[2])));
                        break;
                    case Constants.SAVEVALUE:
                        saveValues.add(new SaveValue(entityName, new Float(vEntity[2])));
                        break;
                    default:
                        throw new UnrecognizedModelException();                        
                }
            }
        }

        // parse process
        pro = new Proces();
        while ((s = entrada.readLine()) != null) {
            if (s.equals("")) {
                pro = new Proces(nomProces, getGPSSmodel());
                blocs = new ArrayList<>();
                pro.setPosx(xini);
                pro.setPosy(yini);
                pro.setFinposx(xfin);
                pro.setFinposy(yfin);
                pro.setBlocs(blocs);
                p.add(pro);
                cont = 1;
                xini = xini + Constants.x;
                xfin = xini;
                yini = 0;
                yfin = Constants.y;

            } else {

                if ((cont == 1) && !s.equals(Constants.asterisco)) {
                    throw new UnrecognizedModelException();
                } else if ((cont == 2)) {
                    nomProces = s.substring(2);
                    pro.setDescpro(nomProces);

                } else if ((cont > 3)) {
                    Bloc bloc = crearBloc(s, pro, getGPSSmodel());
                    blocs.add(bloc);
                    bloc.setPos(pro.lastPos());
                    yfin = yfin + Constants.y;
                }
                cont++;
            }
        }
        m.setName(nomModel);
        m.setDescription(descModel);
        m.setProces(p);
        m.setStorages(storages);
        m.setSaveValues(saveValues);
        m.setFunctions(functions);
        m.setAmperVariables(amperVariables);
        VarGlobals.model = m;

    }

    private Bloc crearBloc(String s, Proces proces, Model model) throws IOException {

        
        String[] part = s.split("( )*");
        
        
        
        String partBloc;
        String vComentari[] = s.split(Constants.puntoycoma);
        s = vComentari[0];
        String strComentari = "";
        if (vComentari.length > 1) {
            strComentari = vComentari[1];
        }
        
        String strLabel;
        strLabel = (String) s.subSequence(0, Constants.espaisLabel.length());
        strLabel = strLabel.trim();

        partBloc = s.substring(Constants.espaisLabel.length());
        String cadenas[] = partBloc.split(Constants.espacio);
        String nomBloc = cadenas[0];
        String strValors = cadenas[cadenas.length - 1];
        String valors[] = strValors.split(Constants.coma);
        Bloc b = null;
        switch (nomBloc) {
            case Constants.Advanced: {

                RNG gna = VarGlobals.getGNA(valors[0]);
                Advance a = new Advance(
                        strComentari, strLabel,
                        valors[1],
                        valors[2],
                        gna);
                b = a;
                break;
            }
            case Constants.Assign: {
                Assign a = new Assign(
                        strComentari, strLabel, valors[0],
                        Float.valueOf(valors[1]));
                b = a;
                break;
            }
            case Constants.Depart:
                Depart d = new Depart(
                        strComentari, strLabel,
                        valors[0],
                        Integer.parseInt(valors[1]));
                b = d;
                break;
            case Constants.Enter:
                Enter e = new Enter(
                        strComentari, strLabel,
                        valors[0],
                        Integer.parseInt(valors[1]));
                b = e;
                break;
            case Constants.Generate:
                Generate g = new Generate(
                        strComentari, strLabel,
                        valors[1],
                        valors[2],
                        Float.valueOf(valors[3]),
                        Float.valueOf(valors[4]),
                        Float.valueOf(valors[5]),
                        Integer.parseInt(valors[6]),
                        VarGlobals.getGNA(valors[0]));
                b = g;
                break;
            case Constants.Leave:
                Leave l = new Leave(
                        strComentari, strLabel,
                        valors[0],
                        Integer.parseInt(valors[1]));
                b = l;
                break;
            case Constants.Queue:
                Queue q = new Queue(
                        strComentari, strLabel,
                        valors[0],
                        Integer.parseInt(valors[1]));
                b = q;
                break;
            case Constants.Release:
                Release r = new Release(
                        strComentari, strLabel,
                        valors[0]);
                b = r;
                break;
            case Constants.Seize:
                Seize se = new Seize(
                        strComentari, strLabel,
                        valors[0]);
                b = se;
                break;
            case Constants.Terminate:
                Terminate t = new Terminate(
                        strComentari, strLabel,
                        Integer.parseInt(valors[0]));
                b = t;
                break;
            case Constants.Test:
                Test test;
                if (valors.length == 3) {
                    test = new Test(
                            strComentari, strLabel, cadenas[1],
                            valors[0], valors[1], valors[2]);
                    b = test;
                } else if (valors.length == 0) {
                    test = new Test(
                            strComentari, strLabel, cadenas[1],
                            "", "", "");
                    b = test;
                }
                if (valors.length == 1) {
                    test = new Test(
                            strComentari, strLabel, cadenas[1],
                            valors[0], "", "");
                    b = test;
                }
                if (valors.length == 2) {
                    test = new Test(
                            strComentari, strLabel, cadenas[1],
                            valors[0], valors[1], "");
                    b = test;
                }
                break;
            case Constants.Transfer:
                Transfer transf = new Transfer(
                        strComentari, strLabel,
                        valors[0], valors[1], valors[2], valors[3]);
                b = transf;
                break;
            case Constants.Logic:
                Logic logic = new Logic(
                        strComentari, strLabel, cadenas[1], valors[0]);
                b = logic;
                break;
            case Constants.Gate:
                Gate gate = new Gate(
                        strComentari, strLabel, cadenas[1], valors[0], valors[1]);
                b = gate;
                break;
            case Constants.Savevavg:
                Savevalue sav = new Savevalue(
                        strComentari, strLabel, valors[0], valors[1]);
                b = sav;
                break;
            case Constants.Loop:
                Loop loop;
                switch (valors.length) {
                    case 2:
                        loop = new Loop(
                                strComentari, strLabel, valors[0], valors[1]);
                        b = loop;
                        break;
                    case 1:
                        loop = new Loop(
                                strComentari, strLabel, valors[0], "");
                        b = loop;
                        break;
                    case 0:
                        loop = new Loop(
                                strComentari, strLabel, "", "");
                        b = loop;
                        break;
                    default:
                        break;
                }
                break;
            case Constants.Split:
                Split split;
                if (valors.length == 3) {
                    split = new Split(
                            strComentari, strLabel, Integer.parseInt(valors[0]), valors[1], valors[2]);
                    b = split;
                } else if (valors.length == 2) {
                    split = new Split(
                            strComentari, strLabel, Integer.parseInt(valors[0]), valors[1], "");
                    b = split;
                } else if (valors.length == 1) {
                    split = new Split(
                            strComentari, strLabel, Integer.parseInt(valors[0]), "", "");
                    b = split;
                } else if (valors.length == 2) {
                    split = new Split(
                            strComentari, strLabel, Integer.parseInt("0"), "", "");
                    b = split;
                }
                break;
            case Constants.Funavail:
                Funavail fun = new Funavail(
                        strComentari, strLabel,
                        valors[0],
                        valors[1],
                        valors[2],
                        valors[3],
                        valors[4],
                        valors[5]);
                b = fun;
                break;
            case Constants.Savail:
                Savail savail = new Savail(
                        strComentari, strLabel, valors[0]);
                b = savail;
                break;
            case Constants.Sunavail:
                Sunavail sunavail = new Sunavail(
                        strComentari, strLabel, valors[0]);
                b = sunavail;
                break;
            case Constants.Assemble:
                Assemble ass = new Assemble(
                        strComentari, strLabel, Integer.parseInt(valors[0]));
                b = ass;
                break;
            case Constants.Gather:
                Gather gather = new Gather(
                        strComentari, strLabel, Integer.parseInt(valors[0]));
                b = gather;
                break;
            case Constants.Match:
                Match match = new Match(
                        strComentari, strLabel, valors[0]);
                b = match;
                break;
            case Constants.Buffer:
                Buffer buffer = new Buffer(
                        strComentari, strLabel);
                b = buffer;
                break;
            case Constants.Priority:
                Priority pri = new Priority(
                        strComentari, strLabel, Integer.parseInt(valors[0]));
                b = pri;
                break;
        }
        b.setModel(model);
        b.setProces(proces);
        return b;
    }

    /**
     * @return the GPSSmodel
     */
    public Model getGPSSmodel() {
        return GPSSmodel;
    }

    /**
     * @param GPSSmodel the GPSSmodel to set
     */
    public void setGPSSmodel(Model GPSSmodel) {
        this.GPSSmodel = GPSSmodel;
    }

    private void writeTitle(Model model, StringBuffer textModel) {
        textModel.append(Constants.asterisco);
        textModel.append(Constants.saltoLinea);
        textModel.append(Constants.asterisco);
        textModel.append(Constants.espacio);
        textModel.append(model.getName());
        textModel.append(Constants.saltoLinea);
        textModel.append(Constants.asterisco);
        textModel.append(Constants.espacio);
        textModel.append(model.getDescription());
        textModel.append(Constants.saltoLinea);
        textModel.append(Constants.asterisco);
        textModel.append(Constants.saltoLinea);
        textModel.append(Constants.saltoLinea);
    }

    private void writeStorages(Model model, StringBuffer textModel) {

        if (model.getStorages().isEmpty()) {
            return;
        }
        model.getStorages().forEach(st -> {
            textModel.append(st.getNom());
            textModel.append(Constants.espacio);
            textModel.append(Constants.STORAGE);
            textModel.append(Constants.espacio);
            textModel.append(st.getValor());
            textModel.append(Constants.saltoLinea);
        });
    }

    private void writeProcess(Model model, StringBuffer textModel) throws IOException {

        //escrire els processos
        ArrayList<Proces> procesos = model.getProces();
        Proces p;
        ArrayList<Bloc> blocs;
        Bloc b;

        for (int i = 0; i < procesos.size(); i++) {
            p = procesos.get(i);
            //descripcio y nom del proces
            textModel.append(Constants.saltoLinea);
            textModel.append(Constants.saltoLinea);
            textModel.append(Constants.asterisco);
            textModel.append(Constants.saltoLinea);
            textModel.append(Constants.asterisco);
            textModel.append(Constants.espacio);
            textModel.append(p.getDescpro());
            textModel.append(Constants.saltoLinea);
            textModel.append(Constants.asterisco);

            //blocs
            blocs = p.getBlocs();
            for (int j = 0; j < blocs.size(); j++) {
                // textModel.append(Constants.saltoLinea);
                textModel.append(Constants.saltoLinea);
                b = blocs.get(j);
                textModel.append(escriurebloc(b));

            }
        }
    }

    private void writeSaveValues(Model model, StringBuffer textModel) {

        if (model.getSaveValues().isEmpty()) {
            return;
        }

        ArrayList<SaveValue> saveValues = model.getSaveValues();

        saveValues.forEach(sv -> {
            textModel.append(sv.getName());
            textModel.append(Constants.espacio);
            textModel.append(Constants.SAVEVALUE);
            textModel.append(Constants.espacio);
            textModel.append(sv.getValue());
            textModel.append(Constants.saltoLinea);
        });
    }

    private void writeAmperVariables(Model model, StringBuffer textModel) {

        if (model.getAmperVariables().isEmpty()) {
            return;
        }

        model.getAmperVariables().forEach(av -> {

            String amperVariableType = "";

            if (av.getValue() instanceof Float) {
                amperVariableType = Constants.REAL;
            } else if (av.getValue() instanceof Integer) {
                amperVariableType = Constants.INTEGER;
            } else if (av.getValue() instanceof String) {
                amperVariableType = Constants.STRING;
            }

            textModel.append(av.getName());
            textModel.append(Constants.espacio);
            textModel.append(amperVariableType);
            textModel.append(Constants.espacio);
            textModel.append(av.getValue());
            textModel.append(Constants.saltoLinea);

        });
    }

    private void writeFunctions(Model model, StringBuffer textModel) {

        if (model.getFunctions().isEmpty()) {
            return;
        }

        model.getFunctions().forEach(f -> {

            textModel.append(f.getName());
            textModel.append(Constants.espacio);
            textModel.append(Constants.FUNCTION);
            textModel.append(Constants.espacio);
            textModel.append(f.getA());
            textModel.append(Constants.espacio);
            textModel.append(f.getB());
            textModel.append(Constants.espacio);
            textModel.append(f.getDistribution());
            textModel.append(Constants.saltoLinea);

        });
    }

    private void writeEndEntities(StringBuffer textModel) {
        textModel.append(Constants.asterisco + " end-entities");
    }

    private void parseHeaderInformation(BufferedReader entrada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
