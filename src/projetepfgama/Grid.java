/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetepfgama;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class Grid extends JPanel {
    private static final long serialVersionUID = 1L;
    public static final int MARGIN = 8;

    final main hote;
    private Cellule[] cellule;
    private Difficulte diff;

    private static final Color back = new Color(0xbbada0);
    private static final Font STR_FONT = new Font(Font.SERIF, Font.BOLD, 16);

    public enum Difficulte {
        Facile(4, 64),
        Moyen(5, 48),
        Hard(6, 32);

        private final int size;
        private final int side;

        Difficulte(int Size, int side) {
            this.size = Size;
            this.side = side;
        }

        public int getGridSize() {
            return size;
        }

        public int getSide() {
            return side;
        }
    }

    public Grid(main main, Difficulte difficulty) {
        hote = main;
        this.diff = difficulty;
        setFocusable(true);
        initializeTiles();
    }

    private void addNewTile() {
        List<Integer> list = IndexVide();
        int index = list.get((int) (Math.random() * list.size()));
        cellule[index] = Cellule.CelluleAléatoire();
    }

    public void initializeTiles() {
        int gridSize = diff.getGridSize();
        cellule = new Cellule[gridSize * gridSize];
        for (int i = 0; i < cellule.length; i++) {
            cellule[i] = Cellule.Zero;
        }
        addNewTile();
        addNewTile();
        hote.Bar.setText("");
    }

    private List<Integer> IndexVide() {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < cellule.length; i++) {
            if (cellule[i].vide())
                list.add(i);
        }
        return list;
    }

    private boolean isGridFull() {
        return IndexVide().size() == 0;
    }

    private Cellule tileAt(int x, int y) {
        return cellule[x + y * diff.getGridSize()];
    }

    boolean MvtPoss() {
        if (!isGridFull()) {
            return true;
        }
        for (int x = 0; x < diff.getGridSize(); x++) {
            for (int y = 0; y < diff.getGridSize(); y++) {
                Cellule t = tileAt(x, y);
                if ((x < diff.getGridSize() - 1 && t.equals(tileAt(x + 1, y))) ||
                        (y < diff.getGridSize() - 1 && t.equals(tileAt(x, y + 1)))) {
                    return true;
                }
            }
        }
        return false;
    }

    private Cellule[] rotate(int degree) {
        Cellule[] newTiles = new Cellule[diff.getGridSize() * diff.getGridSize()];
        int offsetX = diff.getGridSize() - 1, offsetY = diff.getGridSize() - 1;
        if (degree == 90) {
            offsetY = 0;
        } else if (degree == 180) {
        } else if (degree == 270) {
            offsetX = 0;
        }
        double radians = Math.toRadians(degree);
        int cos = (int) Math.cos(radians);
        int sin = (int) Math.sin(radians);
        for (int x = 0; x < diff.getGridSize(); x++) {
            for (int y = 0; y < diff.getGridSize(); y++) {
                int newX = (x * cos) - (y * sin) + offsetX;
                int newY = (x * sin) + (y * cos) + offsetY;
                newTiles[(newX) + (newY) * diff.getGridSize()] = tileAt(x, y);
            }
        }
        return newTiles;
    }

    private Cellule[] getLine(int index) {
        Cellule[] result = new Cellule[diff.getGridSize()];
        for (int i = 0; i < diff.getGridSize(); i++) {
            result[i] = tileAt(i, index);
        }
        return result;
    }

    private static void ensureSize(List<Cellule> l, int s) {
        while (l.size() < s) {
            l.add(Cellule.Zero);
        }
    }

    private Cellule[] moveLine(Cellule[] oldLine) {
        LinkedList<Cellule> l = new LinkedList<>();
        for (int i = 0; i < diff.getGridSize(); i++) {
            if (!oldLine[i].vide())
                l.addLast(oldLine[i]);
        }
        if (l.size() == 0) {
            return oldLine;
        } else {
            Cellule[] newLine = new Cellule[diff.getGridSize()];
            ensureSize(l, diff.getGridSize());
            for (int i = 0; i < diff.getGridSize(); i++) {
                newLine[i] = l.removeFirst();
            }
            return newLine;
        }
    }

    private Cellule[] mergeLine(Cellule[] AncLigne) {
        LinkedList<Cellule> list = new LinkedList<>();
        for (int i = 0; i < diff.getGridSize(); i++) {
            if (i < diff.getGridSize() - 1 && !AncLigne[i].vide() && AncLigne[i].equals(AncLigne[i + 1])) {
                Cellule fusion = AncLigne[i].getDouble();
                i++;
                list.add(fusion);
                if (fusion.valeur() == Couleurs._2048) {
                    hote.win();
                }
            } else {
                list.add(AncLigne[i]);
            }
        }
        ensureSize(list, diff.getGridSize());
        return list.toArray(new Cellule[diff.getGridSize()]);
    }

    private void setLine(int index, Cellule[] re) {
        for (int i = 0; i < diff.getGridSize(); i++) {
            cellule[i + index * diff.getGridSize()] = re[i];
        }
    }

    public void left() {
        boolean needAddTile = false;
        for (int i = 0; i < diff.getGridSize(); i++) {
            Cellule[] origin = getLine(i);
            Cellule[] afterMove = moveLine(origin);
            Cellule[] merged = mergeLine(afterMove);
            setLine(i, merged);
            if (!needAddTile && !Arrays.equals(origin, merged)) {
                needAddTile = true;
            }
        }
        if (needAddTile) {
            addNewTile();
        }
    }

    public void right() {
        cellule = rotate(180);
        left();
        cellule = rotate(180);
    }

    public void up() {
        cellule = rotate(270);
        left();
        cellule = rotate(90);
    }

    public void down() {
        cellule = rotate(90);
        left();
        cellule = rotate(270);
    }

  private int offsetCoors(int arg) {
    int gridSize = diff.getGridSize();
    int side = diff.getSide();
    int totalMargin = (gridSize + 1) * MARGIN;
    int totalSide = gridSize * side;
    int totalSize = totalMargin + totalSide;

    // Utilise la largeur de la grille
    int offset = (getWidth() - totalSize) / 2;
    return offset + MARGIN + arg * (side + MARGIN);
}

    private void drawTile(Graphics g, Cellule cell, int x, int y) {
        Couleurs val = cell.valeur();
        int ValX = offsetCoors(x);
        int ValY = offsetCoors(y);
        g.setColor(val.color());
        g.fillRect(ValX, ValY, diff.getSide(), diff.getSide());
        g.setColor(val.FontClr());

        if (val.valeur() != 0)
            g.drawString(cell.toString(), ValX + (diff.getSide() >> 1) - MARGIN,
                    ValY + (diff.getSide() >> 1));
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(back);
        g.setFont(STR_FONT);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        for (int x = 0; x < diff.getGridSize(); x++) {
            for (int y = 0; y < diff.getGridSize(); y++) {
                drawTile(g, cellule[x + y * diff.getGridSize()], x, y);
            }
        }
    }
}
