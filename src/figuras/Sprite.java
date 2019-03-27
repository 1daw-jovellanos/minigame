package figuras;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Sprite implements Dibujable {

    private BufferedImage[] skin;
    private int selectedSkin;
    private int x;
    private int y;
    private int width;
    private int height;

    private boolean skinAutoRotation;
    private int skinIni;
    private int skinEnd;
    private int skinFrames;
    private int skinFrameCountDown;

    /**
     * Inicia el sprite con las pieles indicadas. La anchura y altura se fija
     * automáticamente a la primera piel
     *
     * @param filename
     */
    public Sprite(String[] filename) {
        skin = new BufferedImage[filename.length];
        if (filename.length < 1) {
            throw new IllegalArgumentException("Insuficiente cantidad de nombres de fichero");
        }
        for (int i = 0; i < filename.length; i++) {
            try {

                skin[i] = ImageIO.read(new File(filename[i]));

            } catch (IOException ex) {
                throw new IllegalArgumentException("No se pudo cargar fichero" + filename[i]);
            }
        }
        selectedSkin = 0;
        width = skin[selectedSkin].getWidth();
        height = skin[selectedSkin].getHeight();
        skinAutoRotation = false;
    }

    public void setSkin(int i) {
        if (i < 0 || i >= skin.length) {
            throw new IllegalArgumentException("No existe piel indice " + i);
        }
        selectedSkin = i;
    }

    public void dibujar(Graphics g) {
        g.drawImage(skin[selectedSkin], x, y, width, height, null);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }

    /**
     * Inicia la rotación de skins
     *
     * @param ini Indice de piel inicial
     * @param end índice de piel final (excluido)
     * @param frames Cantidad de frames entre cambio de pieles. >= 1
     */
    public void skinStartRotation(int ini, int end, int frames) {
        if (ini < 0 || ini > skin.length || end < 0 || end > skin.length || frames < 1) {
            throw new IllegalArgumentException();
        }
        skinAutoRotation = true;
        skinIni = ini;
        skinEnd = end;
        skinFrames = frames;
        skinFrameCountDown = frames;
        selectedSkin = ini;
    }

    public void skinStopRotation() {
        skinAutoRotation = false;
    }

    public void skinRotate() {
        if (!skinAutoRotation) {
            return;
        }
        skinFrameCountDown--;
        if (skinFrameCountDown == 0) {
            skinFrameCountDown = skinFrames;
            int skinCount = skinEnd - skinIni;
            // suma 1 modular
            selectedSkin = (selectedSkin - skinIni + 1) % skinCount + skinIni;
        }
    }
    
    public boolean colisonaCon(Sprite s) {
        boolean result = 
                new Rectangle(x, y, width, height).intersects(
                new Rectangle(s.getX(), s.getY(), s.getWidth(), s.getHeight())
        );
        System.out.println(result);
        return result;
    }

    // ----------------- ACCESORES Básicos.
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSelectedSkin() {
        return selectedSkin;
    }

    public boolean isSkinAutoRotation() {
        return skinAutoRotation;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
