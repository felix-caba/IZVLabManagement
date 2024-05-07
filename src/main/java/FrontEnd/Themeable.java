package FrontEnd;

import BackEnd.Configuration.ConfigurationIZV;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public interface Themeable {


     default void setIcons(JFrame frame) {

        for (JPanel panel : getPanels(frame)) {
            for (Component c : panel.getComponents()) {
                if (c instanceof JButton button) {

                    if (button.getName() == null) {
                        continue;
                    }

                    if (ConfigurationIZV.getInstance().getAppearance().equals("OSCURO") || ConfigurationIZV.getInstance().getAppearance().equals("DARCULA")) {
                        button.setIcon(new ImageIcon("src/main/resources/icons/white/" + button.getName() + ".png"));
                    } else {
                        button.setIcon(new ImageIcon("src/main/resources/icons/black/" + button.getName() + ".png"));

                    }
                }
            }
        }


    }

    default JPanel[] getPanels(JFrame frame) {

        ArrayList<JPanel> panels = new ArrayList<>();
        Container contentPane = frame.getContentPane();

        panels.add((JPanel) contentPane);

        for (Component c : contentPane.getComponents()) {
            if (c instanceof JPanel) {
                panels.add((JPanel) c);
            }
        }

        return panels.toArray(new JPanel[0]);
    }


}
