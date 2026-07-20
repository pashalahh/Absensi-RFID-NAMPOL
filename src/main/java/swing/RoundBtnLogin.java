/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.*;
import javax.swing.JButton;

public class RoundBtnLogin extends JButton {

    public RoundBtnLogin() {

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        setForeground(Color.WHITE);

        setFont(new Font("Segoe UI", Font.PLAIN, 20));

        setCursor(new Cursor(Cursor.HAND_CURSOR));
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

        // Hover effect
        if (getModel().isPressed()) {

            g2.setColor(new Color(105, 135, 122, 240));

        } else if (getModel().isRollover()) {

            g2.setColor(new Color(140, 170, 157, 230));

        } else {

            // Warna tombol seperti gambar
            g2.setColor(new Color(126, 158, 145, 220));
        }

        // Tombol rounded penuh
        g2.fillRoundRect(
                0,
                0,
                w,
                h,
                h,
                h
        );

        // Border tipis
        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(new Color(190, 220, 205, 90));
        g2.drawRoundRect(
                0,
                0,
                w - 1,
                h - 1,
                h,
                h
        );

        g2.dispose();

        super.paintComponent(g);
    }
}