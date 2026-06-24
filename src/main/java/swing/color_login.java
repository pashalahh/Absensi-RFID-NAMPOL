package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class color_login extends JPanel {

    public color_login() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        int arc = 50;

        // Panel utama
        RoundRectangle2D panel =
                new RoundRectangle2D.Double(0, 0, w - 1, h - 1, arc, arc);

        // Background hijau gelap
        g2.setColor(new Color(47, 59, 51, 230));
        g2.fill(panel);

        // Border hijau
        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(new Color(126, 154, 135));
        g2.draw(panel);

        g2.dispose();
    }
}