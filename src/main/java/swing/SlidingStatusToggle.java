package swing;

import java.awt.*;
import javax.swing.JToggleButton;

public class SlidingStatusToggle extends JToggleButton {

    private final Color COLOR_BG = new Color(39, 45, 54);
    private final Color COLOR_SLIDER_MASUK = new Color(25, 135, 84);
    private final Color COLOR_SLIDER_PULANG = new Color(220, 53, 69);
    private final int cornerRadius = 24;
    private String labelMasuk = "Masuk";
    private String labelPulang = "Pulang";

    public SlidingStatusToggle() {
        super();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(new Font("SansSerif", Font.BOLD, 14));
        addActionListener(e -> repaint());
    }

    public void setStatusByString(String status) {
        // Jika status "Pulang" (ignore case), maka toggle di-set true (kanan)
        this.setSelected(status.equalsIgnoreCase("Pulang"));
        repaint();
    }
    public void setLabels(String textLeft, String textRight) {
        this.labelMasuk = textLeft;
        this.labelPulang = textRight;
        repaint();
    }

    public String getStatusString() {
       return isSelected() ? "Pulang" : "Masuk";
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int margin = 5;
        int sliderWidth = (w / 2) - margin;
        int sliderHeight = h - (margin * 2);

        boolean isPulangActive = isSelected();

        g2.setColor(COLOR_BG);
        g2.fillRoundRect(0, 0, w, h, cornerRadius, cornerRadius);

        int sliderX = isPulangActive ? (w / 2) : margin;
        g2.setColor(isPulangActive ? COLOR_SLIDER_PULANG : COLOR_SLIDER_MASUK);
        g2.fillRoundRect(sliderX, margin, sliderWidth, sliderHeight, cornerRadius - 6, cornerRadius - 6);

        FontMetrics fm = g2.getFontMetrics();
        int textY = (h / 2) + (fm.getAscent() / 2) - 2;

        // Label Kiri
        int textLeftX = (w / 4) - (fm.stringWidth(labelMasuk) / 2);
        g2.setColor(!isPulangActive ? Color.WHITE : new Color(130, 135, 145));
        g2.drawString(labelMasuk, textLeftX, textY);

        // Label Kanan
        int textRightX = ((w / 4) * 3) - (fm.stringWidth(labelPulang) / 2);
        g2.setColor(isPulangActive ? Color.WHITE : new Color(130, 135, 145));
        g2.drawString(labelPulang, textRightX, textY);
        
        g2.dispose();
    }
}