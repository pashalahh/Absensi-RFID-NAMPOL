package swing;

import javax.swing.*;
import java.awt.*;

public class color_hijauint extends JPanel {

    public color_hijauint() {
        setOpaque(true); // 🔥 penting biar background ke-render dengan benar
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // 🔥 Rendering biar lebih halus
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Titik gradasi
        float[] dist = {0.0f, 0.5f, 1.0f};

        // 🎨 Warna soft mint (#D6F8DD)
        Color[] colors = {
            new Color(245, 255, 250),   // atas (super soft)
            new Color(214, 248, 221),   // tengah (#D6F8DD)
            new Color(180, 230, 200)    // bawah (lebih dalam dikit)
        };

        // Gradient vertical
        LinearGradientPaint gradient = new LinearGradientPaint(
                0, 0,
                0, height,
                dist,
                colors
        );

        g2.setPaint(gradient);
        g2.fillRect(0, 0, width, height);

        // 🔥 BONUS: glow putih halus di tengah (biar ga flat)
        RadialGradientPaint glow = new RadialGradientPaint(
                new Point(width / 2, height / 2),
                height / 2,
                new float[]{0f, 1f},
                new Color[]{
                    new Color(255, 255, 255, 80),
                    new Color(255, 255, 255, 0)
                }
        );

        g2.setPaint(glow);
        g2.fillRect(0, 0, width, height);

        g2.dispose();
    }
}