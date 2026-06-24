/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.*;
import javax.swing.JCheckBox;

public class RememberCheckBox extends JCheckBox {

    public RememberCheckBox() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int boxSize = 22;

        // kotak
        g2.setColor(new Color(120, 160, 145, 120));
        g2.fillRoundRect(0, 0, boxSize, boxSize, 8, 8);

        // border
        g2.setColor(new Color(170, 220, 195, 150));
        g2.drawRoundRect(0, 0, boxSize, boxSize, 8, 8);

        // ceklis
        if (isSelected()) {

            g2.setStroke(new BasicStroke(2.5f));
            g2.setColor(new Color(220, 255, 235));

            g2.drawLine(5, 11, 10, 16);
            g2.drawLine(10, 16, 17, 6);
        }

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    public Insets getInsets() {
        return new Insets(0, 30, 0, 0);
    }
}
