package swing;

import javax.swing.*;
import java.awt.*;

public class color_hijauArmie extends JPanel {

    // ✅ Constructor harus sama dengan nama class
    public color_hijauArmie() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // Anti-alias biar halus
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Titik gradasi
        float[] dist = {0.0f, 0.3f, 0.6f, 1.0f};

        // Warna gradient (dark green elegant)
        Color[] colors = {
            new Color(20, 30, 25),    // atas (gelap banget)
            new Color(35, 50, 43),    // dark green
            new Color(47, 59, 51),    // warna utama #2F3B33
            new Color(25, 35, 30)     // bawah (lebih dalam)
        };

        // Gradient utama (vertical)
        LinearGradientPaint gradient = new LinearGradientPaint(
                0, 0,
                0, height,
                dist,
                colors
        );

        g2.setPaint(gradient);
        g2.fillRect(0, 0, width, height);

        // 🔥 Glow effect di tengah
        RadialGradientPaint glow = new RadialGradientPaint(
                new Point(width / 2, height / 2),
                height / 2,
                new float[]{0f, 1f},
                new Color[]{
                    new Color(100, 180, 140, 80), // glow hijau transparan
                    new Color(0, 0, 0, 0)
                }
        );

        g2.setPaint(glow);
        g2.fillRect(0, 0, width, height);

        g2.dispose();
    }
}