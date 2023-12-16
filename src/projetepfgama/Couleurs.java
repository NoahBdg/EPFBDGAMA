/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetepfgama;

/**
 *
 * @author lebgd
 */
import java.awt.*;

// On créer un enum contenant les couleurs du jeu.
public enum Couleurs {
	
	// code couleur de quaker's66 inventeur du jeu
	// On associe à chaque valeur une couleur
    _0   (0,    0xcdc0b4, 0x776e65),
    _2   (2,    0x776E65, 0xEEE4DA),
    _4   (4,    0x776E65, 0xEDE0C8),
    _8   (8,    0xF9F6F2, 0xF2B179),
    _16  (16,   0xF9F6F2, 0xF59563),
    _32  (32,   0xF9F6F2, 0xF67C5F),
    _64  (64,   0xF9F6F2, 0xF65E3B),
    _128 (128,  0xF9F6F2, 0xEDCF72),
    _256 (256,  0xF9F6F2, 0xEDCC61),
    _512 (512,  0xF9F6F2, 0xEDC850),
    _1024(1024, 0xF9F6F2, 0xEDC53F),
    _2048(2048, 0xF9F6F2, 0xEDC22E);
    
    private final int valeur;
    private final Color color;
    private final Color font;

    // constructeur
    Couleurs(int n, int f, int c) {
        valeur = n;
        color = new Color(c);
        font = new Color(f);
    }
    
    static Couleurs of(int num) {
        return Couleurs.valueOf("_" + num);
    }

    public Color FontClr() {
        return font;
    }

    public Color color() {
        return color;
    }
    
    public int value() {
        return valeur;
    }
    

}

