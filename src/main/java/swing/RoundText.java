/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.*;
import javax.swing.*;

public class RoundText extends JTextField {

    private final int radius = 30;

    public RoundText() {
        setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder(
                10, 20, 10, 20));

        setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // warna teks
        setForeground(new Color(235, 245, 240));

        // warna kursor
        setCaretColor(new Color(235, 245, 240));

        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int w = getWidth();
        int h = getHeight();

        // Background textbox
        GradientPaint gp = new GradientPaint(
                0, 0,
                new Color(68, 88, 78, 210),
                0, h,
                new Color(52, 68, 60, 210)
        );

        g2.setPaint(gp);
        g2.fillRoundRect(
                0, 0,
                w, h,
                radius, radius
        );

        // Glow luar
        g2.setStroke(new BasicStroke(5f));
        g2.setColor(new Color(
                180, 240, 210, 25
        ));
        g2.drawRoundRect(
                1, 1,
                w - 3, h - 3,
                radius, radius
        );

        // Border utama
        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(new Color(
                170, 220, 195, 140
        ));
        g2.drawRoundRect(
                1, 1,
                w - 3, h - 3,
                radius, radius
        );

        g2.dispose();

        super.paintComponent(g);
    }
}