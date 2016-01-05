import com.rapidminer.Process;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.AttributeRole;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.table.*;
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

    private static  ExampleSet createExampleSet(double[] data) {
        // create attribute list
        int liczba = 10; //Liczba danych do wczytania
        //Tworzymy list� kolumn
        List<Attribute> attributes = new LinkedList<>();
        //Okre�lamy dla ka�dej kolumny jej nazw� i typ danych
        attributes.add(AttributeFactory.createAttribute("a1", Ontology.REAL));
        attributes.add(AttributeFactory.createAttribute("a2", Ontology.REAL));
        attributes.add(AttributeFactory.createAttribute("a3", Ontology.REAL));
        attributes.add(AttributeFactory.createAttribute("a4", Ontology.REAL));

        //Tworzymy tabele danych
        MemoryExampleTable table = new MemoryExampleTable(attributes);

        table.addDataRow(new DoubleArrayDataRow(data));
        //Tworzymy tablice z danymi
        ExampleSet exampleSet = table.createExampleSet();
        return exampleSet;
    }

    public static Object[][] dobierzIrysa(double... data) {
        Object[] result = new Object[8];
        //W�a�ciwe odpalenie procesu
        try {
            File f = new File("processes/" + "iris2.xml"); //Przygotowanie pliku z procesem
            com.rapidminer.Process myProcess = new Process(f);//Wczytanie procesu

            ExampleSet es = createExampleSet(data); //Stworzenie zbioru danych
            IOContainer ioInput = new IOContainer(es); //Okre�lenie danych wej�ciowych do procesu
            //Uruchomienie procesu, wyniki s� zapisywane do IOContainer
            IOContainer ioResult = myProcess.run(ioInput);

            ExampleSet resultExample = ioResult.get(ExampleSet.class);//Odczytujemy wynik z pierwszego wyj�cia
            resultExample.getAttributes().clearRegular();
            Iterator<Attribute> iteratorEx = resultExample.getAttributes().allAttributes();
            int indexOfArray = 0;
            while (iteratorEx.hasNext()){
                Attribute atr = iteratorEx.next();
                System.out.println(atr.toString() + ": ");
                for (Example e : resultExample) {
                    if (atr.isNumerical()) {
                        result[indexOfArray++] = e.getValue(atr);
                        System.out.print(String.format(atr.getName() + ": " + "%4.2f \n", e.getValue(atr))); //Odczytujemy warto�c i wy�wietlamy jako liczb�
                    } else {
                        result[indexOfArray++] = e.getValueAsString(atr);
                        System.out.println(e.getValueAsString(atr)); //Odczytujemy warto�c i wy�wietlamy jako liczb�
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

        Object[][] formatArray = {result};
        return formatArray;
    }

}
