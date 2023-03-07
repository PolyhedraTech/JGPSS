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

import exceptions.FacilityNotFoundException;
import exceptions.FunctionNotFoundException;
import exceptions.ModelSyntaxException;
import exceptions.ParameterNotFoundException;
import exceptions.QueueNotFoundException;
import exceptions.SaveValueNotFoundException;
import exceptions.StorageNotFoundException;
import exceptions.VariableNotFoundException;
import model.blocks.Bloc;
import model.blocks.Facility;
import model.entities.AmperVariable;
import model.entities.Function;
import model.entities.QueueReport;
import model.entities.SaveValue;
import model.entities.Xact;
import model.entities.rng.Uniform;

/**
 *
 * @author Ezequiel Andujar Montes
 */
public class SNA {

    /**
     * Evaluates the Expression A associated with a String, a Number or an SNA
     *
     * @param A
     * @param m
     * @param tr
     * @return
     * @throws exceptions.ModelSyntaxException
     * @throws exceptions.ParameterNotFoundException
     * @throws exceptions.FacilityNotFoundException
     * @throws exceptions.FunctionNotFoundException
     * @throws exceptions.SaveValueNotFoundException
     * @throws exceptions.StorageNotFoundException
     * @throws exceptions.QueueNotFoundException
     */
    public static String evaluate(String A, Model m, Xact tr) throws ModelSyntaxException, ParameterNotFoundException, FacilityNotFoundException, FunctionNotFoundException, SaveValueNotFoundException, StorageNotFoundException, QueueNotFoundException, VariableNotFoundException {
        {
            String _A = "";

            // Value of the relative Clock
            if (A.equals("C1")) {
                _A = String.valueOf(m.getRelativeClock());
            } // The Xact Assembly Set
            else if (A.equals("A1")) {
                _A = String.valueOf(tr.getAssemblySet());
            } // Value of the absolute Clock
            else if (A.equals("AC1")) {
                _A = String.valueOf(m.getAbsoluteClock());
            } // Remaining termination count
            else if (A.equals("TG1")) {
                _A = String.valueOf(m.getTC());
            } // Active Transaction number
            else if (A.equals("XN1")) {
                _A = String.valueOf(tr.getID());
            } // Transit time returns the absolute system clock minus the "Mark Time" of the Transaction
            else if (A.equals("M1")) {
                return String.valueOf(m.getAbsoluteClock() - tr.getMoveTime());
            } // Transactions priority
            else if (A.equals("PR")) {
                return String.valueOf(tr.getPriority());
            } // Block entry count
            else if (A.matches("N\\$^[0-9]+")) {

                int blockPos = Integer.valueOf(A.split("N\\$")[1]);

                Bloc block = m.getProces().stream()//
                        .map(p -> p.getBlocs())//
                        .flatMap(blocks -> blocks.stream())//
                        .filter(b -> b.getPos() == blockPos)//
                        .findFirst()//
                        .orElse(null);

                if (block != null) {
                    _A = String.valueOf(m.findBloc(tr.getBloc().getLabel()).getEntryCount());
                } else {
                    throw new ModelSyntaxException();
                }
            } // Transaction parameter
            else if (A.matches("P\\$^[a-zA-Z0-9]+")) {
                String parameterName = A.split("P\\$")[1];

                Object param = tr.getParameter(parameterName);

                if (param != null) {
                    _A = String.valueOf(param);
                } else {
                    throw new ParameterNotFoundException(parameterName);
                }

            } // Facility Bussy
            else if (A.matches("F\\$^[a-zA-Z0-9]+")) {
                String facilityName = A.split("F\\$")[1];
                Facility facility = m.getFacilities().get(facilityName);

                if (facility != null) {
                    _A = facility.isAvailable() ? "1" : "0";
                } else {
                    throw new FacilityNotFoundException(facilityName);
                }

            } // Facility Capture Count
            else if (A.matches("FC\\$^[a-zA-Z0-9]+")) {
                String facilityName = A.split("FC\\$")[1];
                Facility facility = m.getFacilities().get(facilityName);

                if (facility != null) {
                    _A = String.valueOf(facility.getCaptureCount());
                } else {
                    throw new FacilityNotFoundException(facilityName);
                }
            } // Function
            else if (A.matches("FN\\$^[a-zA-Z0-9]+")) {
                String functionName = A.split("FN\\$")[1];

                Function function = m.getFunctions().stream()//
                        .filter(f -> f.getName().equals(functionName))//
                        .findFirst()//
                        .orElse(null);

                if (function != null) {
                    _A = functionName;
                } else {
                    throw new FunctionNotFoundException(functionName);
                }

            } // Transit Time. Current absolute system clock value minus value in Parameter Parameter
            else if (A.matches("MP\\$^[a-zA-Z0-9]+")) {

                float parameter = Float.parseFloat(A.split("MP\\$")[1]);
                _A = String.valueOf(m.getAbsoluteClock() - parameter);

                String parameterName = A.split("MP\\$")[1];

                try {
                    Float param = Float.valueOf(tr.getParameter(parameterName).toString());
                    if (param != null) {
                        _A = String.valueOf(m.getAbsoluteClock() - parameter);
                    } else {
                        throw new ParameterNotFoundException(parameterName);
                    }
                } catch (NumberFormatException e) {
                    throw new ParameterNotFoundException(parameterName);
                }

            } // Current count value of the queue
            else if (A.startsWith("Q\\$")) {

                String queueName = A.split("Q\\$")[1];
                QueueReport queue = m.getQueues().get(queueName);

                if (queue != null) {
                    _A = String.valueOf(queue.getCurrentCount());
                } else {
                    throw new QueueNotFoundException(queueName);
                }

            } // Average queue content 
            else if (A.startsWith("QA\\$")) {

                String queueName = A.split("QA\\$")[1];
                QueueReport queue = m.getQueues().get(queueName);

                if (queue != null) {
                    _A = String.valueOf(queue.getAvgContent());
                } else {
                    throw new QueueNotFoundException(queueName);
                }

            } // Queue total entries
            else if (A.matches("QC\\$^[a-zA-Z0-9]+")) {

                String queueName = A.split("QC\\$")[1];
                QueueReport queue = m.getQueues().get(queueName);

                if (queue != null) {
                    _A = String.valueOf(queue.getTotalEntries());
                } else {
                    throw new QueueNotFoundException(queueName);
                }
            } // Queue max length
            else if (A.matches("QM\\$^[a-zA-Z0-9]+")) {

                String queueName = A.split("QM\\$")[1];
                QueueReport queue = m.getQueues().get(queueName);

                if (queue != null) {
                    _A = String.valueOf(queue.getMaxCount());
                } else {
                    throw new QueueNotFoundException(queueName);
                }
            } // Average Queue residence time
            else if (A.matches("QT\\$^[a-zA-Z0-9]+")) {

                String queueName = A.split("QX\\$")[1];
                QueueReport queue = m.getQueues().get(queueName);

                if (queue != null) {
                    _A = String.valueOf(queue.getAvgTime());
                } else {
                    throw new QueueNotFoundException(queueName);
                }
            } // Average Queue residence time excluding zero entries
            else if (A.matches("QX\\$^[a-zA-Z0-9]+")) {

                String queueName = A.split("QX\\$")[1];
                QueueReport queue = m.getQueues().get(queueName);

                if (queue != null) {
                    _A = String.valueOf(queue.getAvgTime(true));
                } else {
                    throw new QueueNotFoundException(queueName);
                }
            } // Queue zero entry count
            else if (A.matches("QZ\\$^[a-zA-Z0-9]+")) {
                String queueName = A.split("QZ\\$")[1];

                QueueReport queue = m.getQueues().get(queueName);

                if (queue != null) {
                    _A = String.valueOf(queue.getZeroEntries());
                } else {
                    throw new QueueNotFoundException(queueName);
                }
            } // Available storage capacity
            else if (A.matches("R\\$^[a-zA-Z0-9]+")) {

                String storageName = A.split("R\\$")[1];
                Facility storage = m.getFacilities().get(storageName);

                if (storage != null) {
                    _A = String.valueOf(storage.getAvailableCapacity());
                } else {
                    throw new StorageNotFoundException(storageName);
                }

                // Returns a random value between 0-999
            } else if (A.matches("RN\\$UNIFORM")) {
                _A = String.valueOf(new Uniform().generate(999f, 0f));
            } // Float value of the string
            // Storage in use
            else if (A.matches("S\\$^[a-zA-Z0-9]+")) {

                String storageName = A.split("S\\$")[1];
                Facility storage = m.getFacilities().get(storageName);

                if (storage != null) {
                    _A = String.valueOf(storage.getCaptureCount());
                } else {
                    throw new StorageNotFoundException(storageName);
                }

            } // Average storage in use
            else if (A.matches("SA\\$^[a-zA-Z0-9]+")) {

                String storageName = A.split("SA\\$")[1];

                Facility storage = m.getFacilities().get(storageName);

                if (storage != null) {
                    _A = String.valueOf(storage.avgHoldingTime());
                } else {
                    throw new StorageNotFoundException(storageName);
                }

            } // Storage empty
            else if (A.matches("SE\\$^[a-zA-Z0-9]+")) {

                String storage = A.split("SE\\$")[1];
                Facility facility = m.getFacilities().get(storage);

                if (facility != null) {
                    _A = facility.getCapturingTransactions() == 0 ? "1" : "0";
                } else {
                    throw new StorageNotFoundException(storage);
                }

            } // Storage full
            else if (A.matches("SM\\$^[a-zA-Z0-9]+")) {
                String storage = A.split("SM\\$")[1];
                Facility facility = m.getFacilities().get(storage);

                if (facility != null) {
                    _A = !facility.isAvailable() ? "1" : "0";
                } else {
                    throw new StorageNotFoundException(storage);
                }

            } // Value of SaveValue entity
            else if (A.matches("X\\$^[a-zA-Z0-9]+")) {

                String saveValueName = A.split("X\\$")[1];

                SaveValue saveValue = m.getSaveValues().stream()//
                        .filter(sv -> sv.getName().equals(saveValueName))//
                        .findFirst()//
                        .orElse(null);

                if (saveValue != null) {
                    _A = saveValue.getValue().toString();
                } else {
                    throw new SaveValueNotFoundException(saveValueName);
                }

            } else if (A.matches("V\\$[a-zA-Z]+")) {                
                
                String varName = A.split("V\\$")[1];
                
                AmperVariable<?> variable = m.getAmperVariables().stream()//
                        .filter(v -> v.getName().equals(varName))//
                        .filter(v -> v.getValue() instanceof Float || v.getValue() instanceof Integer)//
                        .findFirst()//
                        .orElse(null);

                if (variable != null) {

                    _A = variable.getValue().toString();
                    
                } else {
                    throw new VariableNotFoundException(varName);
                }

            } else if (A.matches("[0-9]+") || A.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")) {

                _A = A;

            } 
              
            else {
                throw new ModelSyntaxException();
            }
            return _A;
        }
    }
}
