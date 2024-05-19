/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package FrontEnd.ElementosSwing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JPanelBackground extends JPanel {

    private BufferedImage backgroundImage;

    public JPanelBackground(String imagePath) throws IOException {

        setOpaque(false);
        backgroundImage = ImageIO.read(new File(imagePath));

    }



    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Obtener el ancho y alto de la imagen de fondo
        int imageWidth = backgroundImage.getWidth();
        int imageHeight = backgroundImage.getHeight();

        // Calcular la relación de aspecto de la imagen
        double aspectRatio = (double) imageWidth / imageHeight;

        // Calcular el ancho y alto de la imagen para que se ajuste al panel manteniendo la relación de aspecto
        int scaledWidth = panelWidth;
        int scaledHeight = (int) (panelWidth / aspectRatio);

        // Si la altura escalada es menor que la altura del panel, recalcula el ancho y alto para ajustar a la altura del panel
        if (scaledHeight < panelHeight) {
            scaledHeight = panelHeight;
            scaledWidth = (int) (panelHeight * aspectRatio);
        }

        // Calcular las coordenadas de dibujo para centrar la imagen
        int x = (panelWidth - scaledWidth) / 2;
        int y = (panelHeight - scaledHeight) / 2;

        // Dibujar la imagen de fondo escalada y centrada en el panel
        g.drawImage(backgroundImage, x, y, scaledWidth, scaledHeight, this);

    }





}
