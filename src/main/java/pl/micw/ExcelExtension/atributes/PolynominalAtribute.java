package pl.micw.ExcelExtension.atributes;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class to provide polynominal attributes in pl.micw.ExcelExtension.AttributeColumn
 */
public class PolynominalAtribute extends JPanel {

    private List<Polynominal> textAtributes = new ArrayList<>();
    private JScrollPane scrollPane;
    private JPanel panelOfPolynominals = new JPanel();
    private Map<Integer,String> fields = new HashMap<>();
    private int quantity;

    public PolynominalAtribute() {

        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "write polynominals:"));
        panelOfPolynominals.add(Box.createRigidArea(new Dimension(80, 3)));

        panelOfPolynominals.setLayout(new BoxLayout(panelOfPolynominals, 1));
        this.add(scrollPane = new JScrollPane(panelOfPolynominals));
        scrollPane.setBorder(null);

        this.setVisible(false);
        this.setPreferredSize(new Dimension(140, 100));
        this.setLayout(new BoxLayout(this, 1));

        scrollPane.setVisible(true);
        addField();
    }

    public Map<Integer, String> getFields() {
        return fields;
    }

    private void addField(){
        textAtributes.add(new Polynominal(++quantity));
    }

    class Polynominal {

        private int index;
        private String text;

        JTextField field;
        Font font = new Font("Courier", Font.ITALIC,12);

        public Polynominal(int amount){
            this.index=amount;
            field = new JTextField("option no. " + index);
            field.setForeground(Color.GRAY);
            field.setFont(font);
            field.setVisible(true);
            field.setMaximumSize(new Dimension(90, 20));
            panelOfPolynominals.add(field);


            field.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    field.setFont(UIManager.getDefaults().getFont(this));
                    field.setForeground(Color.BLACK);
                    text = field.getText();
                    if (!"".equals(text))
                        fields.put(index, text);
                    if(index==quantity)
                        addField();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    field.setFont(UIManager.getDefaults().getFont(this));
                    field.setForeground(Color.BLACK);
                    text = field.getText();
                    if (!"".equals(text))
                        fields.put(index, text);
                    if(index==quantity)
                        addField();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    field.setFont(UIManager.getDefaults().getFont(this));
                    field.setForeground(Color.BLACK);
                    text = field.getText();
                    if (!"".equals(text))
                        fields.put(index, text);
                    if(index==quantity)
                        addField();
                }
            });
            panelOfPolynominals.revalidate();
        }
    }

}