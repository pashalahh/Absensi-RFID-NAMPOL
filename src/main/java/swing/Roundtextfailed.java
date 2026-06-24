/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import javax.swing.*;
import java.awt.*;

public class Roundtextfailed extends JTextField {

    public Roundtextfailed() {
        setOpaque(false);
        setForeground(Color.WHITE);
        setCaretColor(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Background hijau gelap
        g2.setColor(new Color(47, 59, 51, 230));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);

        g2.dispose();

        super.paintComponent(g);
    }

     @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Border hijau
        g2.setColor(new Color(126, 154, 135));
        g2.setStroke(new BasicStroke(2.5f));
        g2.drawRoundRect(
                1, 1,
                getWidth() - 3,
                getHeight() - 3,
                30, 30);

        g2.dispose();
    }
}