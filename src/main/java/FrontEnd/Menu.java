/*
 * @AUTHOR Felix
 */

/*
 * Created by JFormDesigner on Sun May 05 23:24:46 CEST 2024
 */

package FrontEnd;

import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author Felix
 */
public class Menu extends JFrame {
    public Menu() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Felix
        label1 = new JLabel();
        checkBox1 = new JCheckBox();
        checkBox2 = new JCheckBox();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[378:375]0",
            // rows
            "[82,grow]" +
            "[]"));

        //---- label1 ----
        label1.setText("TEXTOPRUEBATEXTOPRUEBATEXTOPRUEBATEXTOPRUEBA");
        contentPane.add(label1, "cell 0 0");

        //---- checkBox1 ----
        checkBox1.setText("text");
        contentPane.add(checkBox1, "cell 0 1");

        //---- checkBox2 ----
        checkBox2.setText("text");
        contentPane.add(checkBox2, "cell 0 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Felix
    private JLabel label1;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
