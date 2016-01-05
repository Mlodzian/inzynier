package przyklad;

import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.example.*;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.repository.MalformedRepositoryLocationException;
import com.rapidminer.tools.XMLException;
import com.rapidminer.Process;
import com.rapidminer.example.table.AttributeFactory;
import com.rapidminer.example.table.DoubleArrayDataRow;
import com.rapidminer.example.table.MemoryExampleTable;
import com.rapidminer.example.table.NominalAttribute;
import com.rapidminer.example.table.NominalMapping;
import com.rapidminer.example.table.PolynominalMapping;
import com.rapidminer.tools.Ontology;
//com/fasterxml/jackson/core/JsonProcessingException
//import com.fasterxml
import java.io.File;
import java.io.IOException;
import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marcin
 */
public class MainExample {

    public static void main(String[] args) {
        runProcess();
    }

    public static void runProcess() {
        // UWAGA te polecenia uruchamiamy tylko raz dla całej sesji */
        RapidMiner.setExecutionMode(ExecutionMode.COMMAND_LINE);
        RapidMiner.init();
        //Właściwe odpalenie procesu
        try {
            File f = new File("processes/" + "iris2.xml"); //Przygotowanie pliku z procesem
            Process myProcess = new Process(f);//Wczytanie procesu

            ExampleSet es = createExampleSet(); //Stworzenie zbioru danych            
            IOContainer ioInput = new IOContainer(es); //Określenie danych wejściowych do procesu
            //Uruchomienie procesu, wyniki są zapisywane do IOContainer
            IOContainer ioResult = myProcess.run(ioInput);

            ExampleSet resultExample = ioResult.get(ExampleSet.class);//Odczytujemy wynik z pierwszego wyjścia
            Iterator iteratorEx = resultExample.getAttributes().allAttributes();

            while (iteratorEx.hasNext()){
                Attribute atr = (Attribute) iteratorEx.next();
                System.out.println(atr.toString() + ": ");

                for (Example e : resultExample) {
                    if (atr.isNumerical()) {
                        System.out.print(String.format(atr.getName() + ": " + "%4.2f \n", e.getValue(atr))); //Odczytujemy wartośc i wyświetlamy jako liczbę
                    } else {
                        System.out.println(e.getValueAsString(atr)); //Odczytujemy wartośc i wyświetlamy jako liczbę
                    }
                }
            }
        } catch (MalformedRepositoryLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException | XMLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperatorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    static public ExampleSet createExampleSet() {
        // create attribute list
        int liczba = 10; //Liczba danych do wczytania
        //Tworzymy listę kolumn
        List<Attribute> attributes = new LinkedList();
        //Określamy dla każdej kolumny jej nazwę i typ danych
        attributes.add(AttributeFactory.createAttribute("a1", Ontology.REAL));
        attributes.add(AttributeFactory.createAttribute("a2", Ontology.REAL));
        attributes.add(AttributeFactory.createAttribute("a3", Ontology.REAL));
        attributes.add(AttributeFactory.createAttribute("a4", Ontology.REAL));
        //Attribute attr = AttributeFactory.createAttribute("a4", Ontology.REAL);
        //attributes.add(attr); //Dodajemy do listy atrybutów atrybut symboliczny
       // attributes.add(AttributeFactory.createAttribute("confidence_Iris-setosa", Ontology.REAL));
        //attributes.add(AttributeFactory.createAttribute("confidence_Iris-versicolor", Ontology.STRING));
        //attributes.add(AttributeFactory.createAttribute("confidence_Iris-virginica", Ontology.STRING));
       // attributes.add(AttributeFactory.createAttribute("prediction", Ontology.NOMINAL));

        //SimpleAttributes: a1, a2, a3, a4, confidence_Iris-setosa := confidence(Iris-setosa), confidence_Iris-versicolor := confidence(Iris-versicolor), confidence_Iris-virginica := confidence(Iris-virginica), prediction := prediction(label)

        //Attribute attr1 = AttributeFactory.createAttribute("confidence_Iris-setosa", Ontology.POLYNOMINAL);
        //attributes.add(attr1); //Dodajemy do listy atrybutów atrybut symboliczny
        //Attribute attr2 = AttributeFactory.createAttribute("confidence_Iris-versicolor", Ontology.POLYNOMINAL);
        //attributes.add(attr2); //Dodajemy do listy atrybutów atrybut symboliczny
        //Attribute attr3 = AttributeFactory.createAttribute("confidence_Iris-virginica", Ontology.POLYNOMINAL);
        //attributes.add(attr3); //Dodajemy do listy atrybutów atrybut symboliczny
        //Attribute attr4 = AttributeFactory.createAttribute("prediction", Ontology.POLYNOMINAL);
        //attributes.add(attr4); //Dodajemy do listy atrybutów atrybut symboliczny

        //Attribute attr = AttributeFactory.createAttribute("D", Ontology.POLYNOMINAL);
        //attributes.add(attr); //Dodajemy do listy atrybutów atrybut symboliczny

        //Tworzymy mapę - wartość->symbol
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Iris-setosa");
        map.put(1, "Iris-versicolor");
        map.put(2, "Iris-virginica");
        NominalMapping nm = new PolynominalMapping(map);
        //attr1.setMapping(nm); //Ustawiamy mapę
        //attr2.setMapping(nm); //Ustawiamy mapę
        //attr3.setMapping(nm); //Ustawiamy mapę
        //attr4.setMapping(nm); //Ustawiamy mapę

        //Tworzymy tabele danych
        MemoryExampleTable table = new MemoryExampleTable(attributes);

        double[] data = {4.4, 2.9, 1.4, 0.2};
        table.addDataRow(new DoubleArrayDataRow(data));
        //Tworzymy tablice z danymi
        ExampleSet exampleSet = table.createExampleSet();
        return exampleSet;
    }

    static public ExampleSet createExampleSetSimple() {
        //Tworzymy zbiór danych - uwaga ten zbiór zakłada że wszystkie wartości są liczbowe, ale tak być nie musi
        double[][] d = {{1, 2}, {3, 4}, {5, 6}, {7, 8}};
        ExampleSet exampleSet = ExampleSetFactory.createExampleSet(d);
        return exampleSet;
    }
}
