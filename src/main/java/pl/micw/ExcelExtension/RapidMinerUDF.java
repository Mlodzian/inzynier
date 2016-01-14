package pl.micw.ExcelExtension;

import com.rapidminer.Process;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.table.*;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.repository.MalformedRepositoryLocationException;
import com.rapidminer.tools.Ontology;
import com.rapidminer.tools.XMLException;
import org.boris.xlloop.reflect.XLFunction;
import pl.micw.ExcelExtension.GUI.RootForm;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * class-conatiner with static methods.
 * @implNote Only method "run" is implemented to Excel UDF, but it is generic method
 * witch means that user can add many process using this.
 */

public final class RapidMinerUDF {

    private static List<GeneratedProcess> processes = new ArrayList<>();

    @XLFunction(name = "RapidMinerUDF.run",
            help = "List the files contained within a directory",
            args = { "dir" },
            argHelp = { "The directory" },
            category = "Files")


    private static ExampleSet createExampleSet(GeneratedProcess generatedProcess, double[] data) {

        List<Attribute> attributes = new LinkedList<>();
        for (AddedAttribute addedAttribute: generatedProcess.getAddedAttribute()){
            if(addedAttribute.getOntology().getIndex()==Ontology.REAL)
                attributes.add(AttributeFactory.createAttribute(addedAttribute.getName(), addedAttribute.getOntology().getIndex()));
            if (addedAttribute.getOntology().getIndex() == Ontology.POLYNOMINAL || addedAttribute.getOntology().getIndex() == Ontology.BINOMINAL) {
                Attribute attribute = AttributeFactory.createAttribute(addedAttribute.getName(), addedAttribute.getOntology().getIndex());
                attributes.add(attribute);
                Map<Integer, String> map = new HashMap<>();
                int typeId = 0;
                for (String type: addedAttribute.getAtributeTypes().values()){
                    map.put(typeId++,type);
                }
                NominalMapping nm = new PolynominalMapping(map);
                attribute.setMapping(nm);
            }
        }
        MemoryExampleTable table = new MemoryExampleTable(attributes);
        table.addDataRow(new DoubleArrayDataRow(data));
        return table.createExampleSet();
    }

    private static GeneratedProcess selectProcess(String name){
        GeneratedProcess process = null;
        processes = RootForm.getRapidminerProcesses();
        for (GeneratedProcess generatedProcess: processes){
            if(generatedProcess.getProcessName().equals(name)){
                return generatedProcess;
            }
        }
        return process;
    }

    public static Object[][] run(String name, double... data){
        Object[] result = new Object[10];
       // init();
        GeneratedProcess generatedProcess = selectProcess(name);
        File processFile = new File(generatedProcess.getPathToProcess().toUri());
        try{
            Process myProcess = new Process(processFile);

            ExampleSet es = createExampleSet(generatedProcess, data);
            IOContainer ioInput = new IOContainer(new IOObject[]{es});

            IOContainer ioResult = myProcess.run(ioInput);

            ExampleSet resultExample = ioResult.get(ExampleSet.class);
            resultExample.getAttributes().clearRegular();
            Iterator<Attribute> iteratorEx = resultExample.getAttributes().allAttributes();
            int indexOfArray = 0;
            while (iteratorEx.hasNext()){
                Attribute atr = iteratorEx.next();
                System.out.println(atr.toString() + ": ");
                for (Example e : resultExample) {
                    if (atr.isNumerical()) {
                        result[indexOfArray++] = e.getValue(atr);
                        System.out.print(String.format(atr.getName() + ": " + "%4.2f \n", e.getValue(atr)));
                    } else {
                        result[indexOfArray++] = e.getValueAsString(atr);
                        System.out.println(e.getValueAsString(atr));
                    }
                }
            }
        } catch (MalformedRepositoryLocationException e) {
            e.printStackTrace();
        } catch (IOException | XMLException e) {
            e.printStackTrace();
        } catch (OperatorException e) {
            e.printStackTrace();
        }
        Object[][] formatArray = {result};
        return formatArray;
    }

}
