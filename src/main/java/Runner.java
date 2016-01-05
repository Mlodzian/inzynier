import com.rapidminer.RapidMiner;
import com.rapidminer.example.ExampleSet;
import org.boris.xlloop.FunctionServer;
import org.boris.xlloop.handler.CompositeFunctionHandler;
import org.boris.xlloop.handler.DebugFunctionHandler;
import org.boris.xlloop.handler.FunctionInformationHandler;
import org.boris.xlloop.reflect.ReflectFunctionHandler;

/**
 * Created by Mlodzian on 2015-11-02.
 */

public class Runner {

    public static void main(String[] args) throws Exception {

                // Create function server on the default port
        FunctionServer fs = new FunctionServer();

        // Create a reflection function handler and add the Math methods
        ReflectFunctionHandler rfh = new ReflectFunctionHandler();
        //rfh.addMethods("Math.", Math.class);

        // UWAGA te polecenia uruchamiamy tylko raz dla ca�ej sesji */
        RapidMiner.setExecutionMode(RapidMiner.ExecutionMode.COMMAND_LINE);
        RapidMiner.init();

        rfh.addMethods("RapidMinerFunctions.", RapidMinerFunctions.class);

        // Create a function information handler to register our functions
        FunctionInformationHandler firh = new FunctionInformationHandler();
        firh.add(rfh.getFunctions());

        // Set the handlers
        CompositeFunctionHandler cfh = new CompositeFunctionHandler();
        cfh.add(rfh);
        cfh.add(firh);
        fs.setFunctionHandler(new DebugFunctionHandler(cfh));

        // Run the engine
        System.out.println("Listening on port " + fs.getPort() + "...");
        fs.run();

    }

}
