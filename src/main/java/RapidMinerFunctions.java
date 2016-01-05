import com.rapidminer.Process;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.table.AttributeFactory;
import com.rapidminer.example.table.DoubleArrayDataRow;
import com.rapidminer.example.table.MemoryExampleTable;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.repository.MalformedRepositoryLocationException;
import com.rapidminer.tools.Ontology;
import com.rapidminer.tools.XMLException;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Mldz on 2015-11-23.
 */


public final class RapidMinerFunctions {

    private RapidMinerFunctions(){}

    public static double dodajDziesieciPi(double a){
        return a + 10 + Math.PI;
    }

    public static double sredniaZTablicy(double... a){
        double sr=0;
        for (double d:a){
            sr+=d;
        }
        return sr/(a.length);
    }

    public static String dodajNapis(String a){
        StringBuilder sb = new StringBuilder(a);
        sb.append("Naspis Z JAVA!");
        System.out.println(sb);
        return sb.toString();
    }

    public static double sredniaDodajLiczbeiWyswietlNapis(double b, String s, double... a){
        double ret = sredniaZTablicy(a);
        ret += b;
        System.out.println(">JAVA< + " + s);
        return  ret;
    }

    public static double defineIris(double... data){
        createExampleSet(data);
        double ret = sredniaZTablicy(data);
        System.out.println("Definiowanie gatunku Irysa... " + data);
        return  ret;
    }

    private static  ExampleSet createExampleSet(double[] data) {
        // create attribute list
        int liczba = 10; //Liczba danych do wczytania
        //Tworzymy listê kolumn
        List<Attribute> attributes = new LinkedList();
        //Okreœlamy dla ka¿dej kolumny jej nazwê i typ danych
        attributes.add(AttributeFactory.createAttribute("a1", Ontology.REAL));
        attributes.add(AttributeFactory.createAttribute("a2", Ontology.REAL));
        attributes.add(AttributeFactory.createAttribute("a3", Ontology.REAL));
        attributes.add(AttributeFactory.createAttribute("a4", Ontology.REAL));

        //Tworzymy mapê - wartoœæ->symbol
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Iris-setosa1231312");
        map.put(1, "Iris-versicolor123123");
        map.put(2, "Iris-virginica123123");

        //Tworzymy tabele danych
        MemoryExampleTable table = new MemoryExampleTable(attributes);

        table.addDataRow(new DoubleArrayDataRow(data));
        //Tworzymy tablice z danymi
        ExampleSet exampleSet = table.createExampleSet();
        return exampleSet;
    }

    public static void dobierzIrysa(double... data) {

        // UWAGA te polecenia uruchamiamy tylko raz dla ca³ej sesji */
        com.rapidminer.RapidMiner.setExecutionMode(com.rapidminer.RapidMiner.ExecutionMode.COMMAND_LINE);
        com.rapidminer.RapidMiner.init();
        //W³aœciwe odpalenie procesu
        try {
            File f = new File("processes/" + "iris2.xml"); //Przygotowanie pliku z procesem
            com.rapidminer.Process myProcess = new Process(f);//Wczytanie procesu

            ExampleSet es = createExampleSet(data);; //Stworzenie zbioru danych
            IOContainer ioInput = new IOContainer(es); //Okreœlenie danych wejœciowych do procesu
            //Uruchomienie procesu, wyniki s¹ zapisywane do IOContainer
            IOContainer ioResult = myProcess.run(ioInput);

            ExampleSet resultExample = ioResult.get(ExampleSet.class);//Odczytujemy wynik z pierwszego wyjœcia
            Iterator iteratorEx = resultExample.getAttributes().allAttributes();

            while (iteratorEx.hasNext()){
                Attribute atr = (Attribute) iteratorEx.next();
                System.out.println(atr.toString() + ": ");

                for (Example e : resultExample) {
                    if (atr.isNumerical()) {
                        System.out.print(String.format(atr.getName() + ": " + "%4.2f \n", e.getValue(atr))); //Odczytujemy wartoœc i wyœwietlamy jako liczbê
                    } else {
                        System.out.println(e.getValueAsString(atr)); //Odczytujemy wartoœc i wyœwietlamy jako liczbê
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

}
