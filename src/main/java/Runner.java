import org.boris.xlloop.FunctionServer;
import org.boris.xlloop.handler.CompositeFunctionHandler;
import org.boris.xlloop.handler.DebugFunctionHandler;
import org.boris.xlloop.handler.FunctionInformationHandler;
import org.boris.xlloop.reflect.Reflect;
import org.boris.xlloop.reflect.ReflectFunctionHandler;
import org.boris.xlloop.util.CSV;
import org.boris.xlloop.util.Maths;

/**
 * Created by Mlodzian on 2015-11-02.
 */

public class Runner {

    public static void main(String[] args) throws Exception {

        // Create function server on the default port
        FunctionServer fs = new FunctionServer();

        // Create a reflection function handler and add the Math methods
        ReflectFunctionHandler rfh = new ReflectFunctionHandler();
        rfh.addMethods("Math.", Math.class);

        rfh.addMethods("RapidMiner.", RapidMiner.class);


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
