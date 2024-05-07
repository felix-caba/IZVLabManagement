package FrontEnd;

import BackEnd.Configuration.ConfigurationIZV;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public interface Themeable {


     default void setIcons(JFrame frame) {

        for (JPanel panel : getPanels(frame)) {
            for (Component c : panel.getComponents()) {
                if (c instanceof JButton button) {

                    if (button.getName() == null) {
                        continue;
                    }

                    if (ConfigurationIZV.getInstance().getAppearance().equals("OSCURO")) {

                        button.setIcon(new ImageIcon("src/main/resources/icons/white/" + button.getName() + ".png"));

                    } else {

                        button.setIcon(new ImageIcon("src/main/resources/icons/black/" + button.getName() + ".png"));

                    }
                }
            }
        }


    }

    default JPanel[] getPanels(JFrame frame) {
        // Cuenta los componentes totales
        JPanel[] panels = new JPanel[frame.getContentPane().getComponentCount()+1];

        System.out.println("Numero de componentes: " + frame.getContentPane().getComponentCount());
        // Index es 1 porque el espacio 0 se reserva para el padre

        int index = 1;

        panels[0] = (JPanel) frame.getContentPane();

        // Pasa por los elementos del panel padre

        for (Component c : frame.getContentPane().getComponents()) {
            // Chequea que sea JPanel o en su defecto, su hijo PanelRound

            if (c instanceof JPanel panel) {
                panels[index] = panel;
                index++;
            }




        }

        //se crea copia del array con el tamaño correcto de paneles.
        return Arrays.copyOf(panels, index);
    }


}
