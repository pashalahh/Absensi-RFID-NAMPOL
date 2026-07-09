/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.prefs.Preferences;
import javax.swing.JToggleButton;

/**
 * Custom 4-Way Slider Toggle untuk Pilihan Bahasa (ID, EN, IT, JA)
 * @author mnish (Disesuaikan untuk 4 bahasa)
 */
public class SlidingLanguageToggle extends JToggleButton {

    private final Color COLOR_BG = new Color(39, 45, 54);
    private final Color COLOR_SLIDER_ACTIVE = new Color(30, 144, 255);
    private final int cornerRadius = 24;
    
    // Indeks: 0=ID, 1=EN, 2=IT, 3=JA
    private int selectedLanguageIndex = 0; 
    
private final String[] languages = {"Indo", "English", "Italiano", "Tiếng Việt"};
private final Preferences prefs = Preferences.userNodeForPackage(SlidingLanguageToggle.class);

    public SlidingLanguageToggle() {
        super();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(new Font("SansSerif", Font.BOLD, 11));

        // Load bahasa terakhir
        String savedLang = prefs.get("LANGUAGE", "id");
        setSelectedLanguageIndexByString(savedLang);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Dibagi 4 bagian presisi
                int sectionWidth = getWidth() / 4;
                int clickX = e.getX();
                int index = clickX / sectionWidth;
                if (index > 3) index = 3; 
                
                setSelectedLanguageIndex(index);
                
                // Simpan & Notifikasi ke I18nService
                prefs.put("LANGUAGE", getSelectedLanguageString());
                swing.I18nService.setLocale(java.util.Locale.of(getSelectedLanguageString()));
                
                repaint();
            }
        });
    }

    public void setSelectedLanguageIndex(int index) {
        this.selectedLanguageIndex = index;
        repaint();
    }
    
    public void setSelectedLanguageIndexByString(String lang) {
        switch (lang) {
            case "id" -> selectedLanguageIndex = 0;
            case "en" -> selectedLanguageIndex = 1;
            case "it" -> selectedLanguageIndex = 2;
            case "vi" -> selectedLanguageIndex = 3;
        }
        repaint();
    }

    public String getSelectedLanguageString() {
        return switch (selectedLanguageIndex) {
            case 1 -> "en";
            case 2 -> "it";
            case 3 -> "vi";
            default -> "id";
        };
    }
    
    public String getLanguageCode() {
        return getSelectedLanguageString();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int margin = 4;
        
        // Lebar tiap kolom adalah 1/4 dari lebar total
        int sectionWidth = w / 4;
        int sliderWidth = sectionWidth - (margin * 2);
        
        // Background
        g2.setColor(COLOR_BG);
        g2.fillRoundRect(0, 0, w, h, cornerRadius, cornerRadius);

        // Slider (Posisi X dinamis dikalikan indeks)
        int sliderX = margin + (selectedLanguageIndex * sectionWidth);
        g2.setColor(COLOR_SLIDER_ACTIVE);
        g2.fillRoundRect(sliderX, margin, sliderWidth, h - (margin * 2), cornerRadius - 6, cornerRadius - 6);

        // Teks (Ditengah per kolom)
        FontMetrics fm = g2.getFontMetrics();
        for (int i = 0; i < languages.length; i++) {
            int textX = (sectionWidth * i) + ((sectionWidth - fm.stringWidth(languages[i])) / 2);
            g2.setColor(i == selectedLanguageIndex ? Color.WHITE : new Color(145, 150, 160));
            g2.drawString(languages[i], textX, (h / 2) + (fm.getAscent() / 2) - 2);
        }
        g2.dispose();
    }
}