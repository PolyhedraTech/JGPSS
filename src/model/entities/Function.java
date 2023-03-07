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

import exceptions.MalformedFunctionDistributionException;
import java.util.ArrayList;
import javafx.util.Pair;
import lombok.Data;
import model.entities.rng.Uniform;

/**
 *
 * @author Ezequiel Andujar Montes
 */
@Data
public class Function {

    private String name;
    private String A;
    private String B;
    private String distribution;
    
    int distributionSize;

    public final static String C = "C";
    public final static String D = "D";
    public final static String E = "E"; // Not implemented 
    public final static String L = "L";
    public final static String M = "M"; // Not implemented

    public Function(String name, String A, String B, String d) throws MalformedFunctionDistributionException {

        this.name = name;
        this.A = A; 
        this.B = B;
        this.distribution = d; 
        this.distributionSize = evaluateFunctionType(B).getValue();
    }

    public Float evaluate() throws MalformedFunctionDistributionException {

        Float result = 0f;        
        Pair<String, Integer> type = evaluateFunctionType(B);
        int distrSize = type.getValue();
        Float Aeval = evaluateParameter(A);
        ArrayList<Pair<Float, Float>> dist = evaluateDistribution(distribution, distrSize);
        
        switch (type.getKey()) {
            case C:
                result = evaluateCType(dist, Aeval);
                break;
            case D:
                result = evaluateDType(dist, Aeval);
                break;
            case E:
                result = evaluateEType();
                break;
            case L:
                result = evaluateLType(dist, Aeval);
                break;
            case M:
                result = evaluateMType();
                break;
        }
        return result;
    }

    /**
     * Continous function Given a value for X, interpolates and returns an
     * integer value for Y
     *
     * @return
     */
    private Float evaluateCType(ArrayList<Pair<Float, Float>> distribution, Float A) {

        Float y = distribution.stream()//
                .filter(p -> p.getKey().equals(A))//
                .map(p -> p.getValue())//
                .findFirst()//
                .orElse(0f);

        return Float.valueOf(Math.round(y));
    }

    /**
     * Discrete function Given a value for X, if an equal or bigger value is
     * found, it returns its associated value. If it is not found, it returns
     * the biggest value
     *
     * @return
     */
    private Float evaluateDType(ArrayList<Pair<Float, Float>> distribution, Float A) {

        Float biggestValue = distribution.stream()//
                .sorted((a, b) -> Float.compare(b.getKey(), a.getKey()))//
                .map(p -> p.getValue())//                
                .findFirst()//                
                .orElse(0f);

        return distribution.stream()//
                .filter(p -> p.getKey() >= A)//
                .map(p -> p.getValue())//
                .findFirst()//
                .orElse(biggestValue);
    }

    /**
     * List Value Function Returns the value of the position X indicated by the
     * function argument
     *
     * @return
     */
    private Float evaluateLType(ArrayList<Pair<Float, Float>> distribution, Float A) {

        return distribution.stream()//
                .filter(p -> p.getKey().equals(A))//
                .map(p -> p.getValue())//
                .findFirst()//
                .orElse(0f);
    }

    private Float evaluateEType() {
        return 0f;
    }

    private Float evaluateMType() {
        return 0f;
    }

    private ArrayList<Pair<Float, Float>> evaluateDistribution(String d, int distributionSize) throws MalformedFunctionDistributionException {

        ArrayList<Pair<Float, Float>> dist = new ArrayList<>();

        String[] pairs = d.split("/");

        if (pairs.length == 0 || pairs.length != distributionSize) {
            throw new MalformedFunctionDistributionException();
        }

        for (String pair : pairs) {
            String[] values = pair.split(",");
            if (values.length != 2) {
                throw new MalformedFunctionDistributionException();
            }
            try {

                Float x = Float.valueOf(values[0]);
                Float y = Float.valueOf(values[1]);
                dist.add(new Pair<>(x, y));

            } catch (NumberFormatException e) {
                throw new MalformedFunctionDistributionException();
            }
        }
        return dist;
    }

    private Pair<String, Integer> evaluateFunctionType(String t) throws MalformedFunctionDistributionException {

        Integer size;
        String type;

        if (!t.matches("[CDL0-9]+")) {
            throw new MalformedFunctionDistributionException();
        }

        try {
            size = Integer.valueOf(t.split("^[A-Z]+")[1]);
        } catch (NumberFormatException e) {
            throw new MalformedFunctionDistributionException();
        }
        type = t.substring(0, 1);
        return new Pair<>(type, size);
    }

    private Float evaluateParameter(String A) throws MalformedFunctionDistributionException {

        Float f = new Float(0);

        if (A.matches("RN[0-9]*")) {

            Integer n;
            try {
                n = Integer.valueOf(A.split("RN")[1]);
            }
            catch(NumberFormatException e) {
                throw new MalformedFunctionDistributionException();
            }

            f = new Uniform().generate(n, 0);

        } else {
            try {
                f = Float.parseFloat(A);
            } catch (NumberFormatException e) {
                throw new MalformedFunctionDistributionException();
            }
        }
        return f;
    }
}
