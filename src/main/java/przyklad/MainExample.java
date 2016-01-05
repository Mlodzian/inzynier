
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.repository.MalformedRepositoryLocationException;
import com.rapidminer.tools.XMLException;
import com.rapidminer.Process;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSetFactory;
import com.rapidminer.example.table.AttributeFactory;
import com.rapidminer.example.table.DoubleArrayDataRow;
import com.rapidminer.example.table.MemoryExampleTable;
import com.rapidminer.example.table.NominalAttribute;
import com.rapidminer.example.table.NominalMapping;
import com.rapidminer.example.table.PolynominalMapping;
import com.rapidminer.tools.Ontology;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
            File f = new File("process.rmp"); //Przygotowanie pliku z procesem            
            Process myProcess = new Process(f) {
            };//Wczytanie procesu

            ExampleSet es = createExampleSet(); //Stworzenie zbioru danych            
            IOContainer ioInput = new IOContainer(new IOObject[]{es}); //Określenie danych wejściowych do procesu            
            //Uruchomienie procesu, wyniki są zapisywane do IOContainer
            IOContainer ioResult = myProcess.run(ioInput);

            ExampleSet resultExample = ioResult.get(ExampleSet.class);//Odczytujemy wynik z pierwszego wyjścia
            //Iterujemy po wynikach końcowego zbioru danych
            for (Example e : resultExample) { //Iterujemy po wierszach
                for (Attribute a : resultExample.getAttributes()) { //Iterujemy dla danego wiersza po kolumnach
                    if (a.isNumerical()) {
                        System.out.print(String.format("%4.2f  ", e.getValue(a))); //Odczytujemy wartośc i wyświetlamy jako liczbę
                    } else {
                        System.out.print(e.getValueAsString(a)); //Odczytujemy wartośc i wyświetlamy jako liczbę
                    }
                }
                System.out.println("");
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
        List<Attribute> attributes = new LinkedList<Attribute>();
        //Określamy dla każdej kolumny jej nazwę i typ danych
        attributes.add(AttributeFactory.createAttribute("A", Ontology.REAL));
        attributes.add(AttributeFactory.createAttribute("B", Ontology.REAL));
        attributes.add(AttributeFactory.createAttribute("C", Ontology.REAL));
        Attribute attr = AttributeFactory.createAttribute("D", Ontology.POLYNOMINAL);
        attributes.add(attr); //Dodajemy do listy atrybutów atrybut symboliczny

        //Tworzymy mapę - wartość->symbol
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "NAPIS_1");
        map.put(1, "NAPIS_2");
        NominalMapping nm = new PolynominalMapping(map);
        attr.setMapping(nm); //Ustawiamy mapę        

        //Tworzymy tabele danych
        MemoryExampleTable table = new MemoryExampleTable(attributes);

        //Wpisujemy do wierszy odpowiednie wartości. UWAGA u nas wartości są typu double
        //Jeśli wartości są typu String to musimy stworzyć inny typ atrybutu i podać mu mapę która określa mapowanie słowo -> wartość
        for (int d = 0; d < liczba; d++) {
            //Tworzymy jeden wiersz
            double[] data = new double[attributes.size()];
            for (int a = 0; a < attributes.size() - 1; a++) {
                // fill with proper data here
                data[a] = Math.random();
            }
            //Ostatni atrybut jest symbolem więc wstawiamy 0 lub 1. 0 odpowiadać będzie "a", a 1 odpowiadać będzie "b"
            if (d % 2 == 0) {
                data[attributes.size() - 1] = 0;
            } else {
                data[attributes.size() - 1] = 1;
            }

            // W pętli dodajemy kolejne wiersze
            table.addDataRow(new DoubleArrayDataRow(data));
        }

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
