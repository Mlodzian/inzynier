package pl.micw.ExcelExtension;

import java.util.Map;

/**
 * Class which contains Attribute added by user.
 */
public class AddedAttribute {

    private String name;
    private OntologyTypes ontology;
    private Map<Integer, String> atributeTypes;


    public AddedAttribute(String name, OntologyTypes ontology, Map atributeTypes) {
        this.atributeTypes = atributeTypes;
        this.name = name;
        this.ontology = ontology;
    }

    public String getName() {
        return name;
    }

    public OntologyTypes getOntology() {
        return ontology;
    }

    public Map<Integer, String> getAtributeTypes() {
        return atributeTypes;
    }
}
