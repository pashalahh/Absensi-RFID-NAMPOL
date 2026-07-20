/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class gradientLogin extends JPanel {

    public gradientLogin() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int width = getWidth();
        int height = getHeight();

        // Background hijau gelap semi transparan
        GradientPaint gp = new GradientPaint(
                0, 0,
                new Color(58, 76, 68, 215),
                0, height,
                new Color(45, 58, 52, 215)
        );

        RoundRectangle2D.Float shape =
                new RoundRectangle2D.Float(
                        4, 4,
                        width - 8,
                        height - 8,
                        60, 60
                );

        // Isi panel
        g2.setPaint(gp);
        g2.fill(shape);

        // Glow luar tipis
        g2.setStroke(new BasicStroke(7f));
        g2.setColor(new Color(190, 255, 220, 40));
        g2.draw(shape);

        // Border utama
        g2.setStroke(new BasicStroke(4f));
        g2.setColor(new Color(165, 210, 185, 180));
        g2.draw(shape);

        g2.dispose();
    }
}