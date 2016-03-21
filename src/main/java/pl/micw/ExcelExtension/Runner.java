package pl.micw.ExcelExtension;

import com.rapidminer.RapidMiner;
import org.boris.xlloop.FunctionServer;
import org.boris.xlloop.handler.CompositeFunctionHandler;
import org.boris.xlloop.handler.DebugFunctionHandler;
import org.boris.xlloop.handler.FunctionInformationHandler;
import org.boris.xlloop.reflect.ReflectFunctionHandler;
import org.springframework.asm.SpringAsmInfo;
import pl.micw.ExcelExtension.GUI.RootFrame;

/**
 * Runner class with 2 threads.
 * @apiNote first thread is responible for XLLoop conection with Excel via plugin, and add methods from RapidMinerUDF
 * second thread is responisble for GUI where user can add, or remove implemented process.
 */

public class Runner {

    public static void main(String[] args) throws Exception {

        Runnable guiForm = () ->{
            RootFrame rootForm = new RootFrame();
        };

        FunctionServer fs = new FunctionServer();
        ReflectFunctionHandler rfh = new ReflectFunctionHandler();
        RapidMiner.setExecutionMode(RapidMiner.ExecutionMode.COMMAND_LINE);
        RapidMiner.init();

        rfh.addMethods("RapidMinerUDF.", RapidMinerUDF.class);

        FunctionInformationHandler firh = new FunctionInformationHandler();
        firh.add(rfh.getFunctions());

        CompositeFunctionHandler cfh = new CompositeFunctionHandler();
        cfh.add(rfh);
        cfh.add(firh);
        fs.setFunctionHandler(new DebugFunctionHandler(cfh));

        guiForm.run();
        System.out.println("Listening on port " + fs.getPort() + "...");
        fs.run();

    }


}
