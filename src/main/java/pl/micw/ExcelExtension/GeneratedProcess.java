package pl.micw.ExcelExtension;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * The class witch contains prepared Proces configured by user.
 */
public class GeneratedProcess {


    private Path pathToProcess;
    private List<AddedAttribute> attributes;
    private String processName;

    public GeneratedProcess(String processName, List atributes, Path pathToProcess) {
        this.processName = processName;
        this.pathToProcess = pathToProcess;
        this.attributes = atributes;
    }

    public Path getPathToProcess() {
        return pathToProcess;
    }

    public List<AddedAttribute> getAddedAttribute() {
        return attributes;
    }

    public String getProcessName() {
        return processName;
    }

    @Override
    public String toString() {
        StringBuilder atributesName = new StringBuilder("(");
        for (AddedAttribute atr : attributes) {
            if (atr.getOntology() == OntologyTypes.REAL) {
                atributesName.append(" " + atr.getName() + " - real ");
            }
            if (atr.getOntology() == OntologyTypes.BINOMINAL || atr.getOntology() == OntologyTypes.POLYNOMINAL) {
                atributesName.append(atr.getName() + " - ontology: " + atr.getOntology() + ", types [ ");
                for (Map.Entry<Integer, String> entry : atr.getAtributeTypes().entrySet()) {
                    atributesName.append(entry.getValue() + " ");
                }
                atributesName.append("]; ");
            }
        }
        atributesName.append(")");
        return "pl.micw.ExcelExtension.GeneratedProcess{ pathToProcess=\"" + pathToProcess +
                "\", attributes=" + atributesName +
                ", processName='" + processName + '\'' +
                '}';
    }
}
