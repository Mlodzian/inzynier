package pl.micw.ExcelExtension;

/**
 * Ontology types with index numbers used in XLLoop class Ontology
 */
public enum OntologyTypes {
    REAL(4), BINOMINAL(6), POLYNOMINAL(7);

    private final int index;

    int getIndex(){
        return index;
    }

    OntologyTypes(int index){
        this.index=index;
    }
}
