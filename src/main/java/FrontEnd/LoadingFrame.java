/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.Configuration.ScreenSize;
import BackEnd.MySQL;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;




public class LoadingFrame extends JFrame implements Themeable, PropertyChangeListener{
    private JPanel panel1;
    private PanelRound panelRound1;
    private JButton backButton;
    private JProgressBar progressBar1;
    private JLabel labelLoading;

    private String sqlBROADCAST;



    public void initComponents() {

        backButton.setName("backButton");
        setTitle("Cargando...");



        panelRound1.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");


        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setAlwaysOnTop(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setIcons(this);
    }

    public void setMessage(String message) {
        labelLoading.setText(message);

        toFront();
    }



    public LoadingFrame() {


        JLabel loadingLabel = new JLabel();
        loadingLabel.setHorizontalAlignment(JLabel.CENTER);
        loadingLabel.setVerticalAlignment(JLabel.CENTER);
        initComponents();



        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }

        });


    }

    public void onSucess() {
        setMessage("Carga completada");
        progressBar1.setIndeterminate(false);
    }

    public void onFail(String mensajeError) {

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
        this.setSqlBROADCAST((String) evt.getNewValue());
        this.setMessage(this.getSqlBROADCAST());
        progressBar1.setIndeterminate(false);
        refresh();

    }

    public void refresh() {
        repaint();
        revalidate();
        update(getGraphics());
        pack();
    }


}
