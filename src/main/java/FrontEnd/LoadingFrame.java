/*
 * @AUTHOR Felix
 */

package FrontEnd;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;




public class LoadingFrame extends JFrame implements Themeable, PropertyChangeListener{
    
    private static LoadingFrame instance;
    private JPanel panel1;
    private PanelRound panelRound1;
    private JButton backButton;
    private JProgressBar progressBar1;
    private JLabel labelLoading;
    private JButton errorLogButton;
    private String sqlBROADCAST;



    private LoadingFrame() {

        initComponents();


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                dispose();
                discloseErrorLog();

            }

        });


        errorLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                showErrorLog();

            }
        });


    }

    public static LoadingFrame getInstance() {



        if (instance == null) {
            instance = new LoadingFrame();
        }
        return instance;

    }


    public void initComponents() {

        backButton.setName("checkButton");
        setTitle("Cargando...");
        panelRound1.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,3%);");
        discloseErrorLog();
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setAlwaysOnTop(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setIcons(this);
        pack();
    }

    public void setMessage(String message) {

        labelLoading.setText(message);
        refresh();
        toFront();

    }



    public void onSucess(String message) {
        setMessage(message);
        progressBar1.setIndeterminate(false);
    }

    public void onFail(String message) {
        setMessage(message);
        progressBar1.setIndeterminate(false);
    }


    public String getSqlBROADCAST() {
        return sqlBROADCAST;
    }

    public void setSqlBROADCAST(String sqlBROADCAST) {
        this.sqlBROADCAST = sqlBROADCAST;
    }

    /*OBSERVA*/

    public void propertyChange(PropertyChangeEvent evt) {
        setSqlBROADCAST((String) evt.getNewValue());
        setIcons(this);
        setMessage("Ha ocurrido un error");
        errorLogButton.setVisible(true);
        progressBar1.setIndeterminate(false);
        setLocationRelativeTo(null);
        refresh();
        this.setVisible(true);
        toFront();
    }

    public void refresh() {
        revalidate();
        pack();
    }


    public void showErrorLog() {
        JOptionPane.showMessageDialog(this, getSqlBROADCAST(), "Error Log", JOptionPane.INFORMATION_MESSAGE);
    }

    public void discloseErrorLog() {
        this.errorLogButton.setVisible(false);
        this.setMessage("Cargando...");
        this.progressBar1.setIndeterminate(true);
        this.refresh();
    }


}
