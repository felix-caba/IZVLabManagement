/*
 * @AUTHOR Felix
 */

package FrontEnd.Auxiliares;

import FrontEnd.ElementosSwing.PanelRound;

import javax.swing.*;

public class ConfigAvanzada extends JFrame {
    private JPanel panelConfigAv;
    private PanelRound panelRound1;
    private JTextField fieldIP;
    private JLabel labelIP;
    private JLabel labelPort;
    private JTextField fieldPort;
    private PanelRound panelRound2;
    private JButton guardarButton;
    private JButton volverButton;
    private JButton mostrarDireccionesSQLEnButton;
    private JButton seleccionarIPButton1;
    private JButton mostrarDireccionesSQLEnButton1;
    private JTable table1;

    public ConfigAvanzada() {



        initComponents();



    }




    public void initComponents() {

        /*Tamaño de la ventana y posicion*/
        setSize(800, 600);
        setMinimumSize(new java.awt.Dimension(600, 300));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Configuración Avanzada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelConfigAv);
        pack();

    }









}

