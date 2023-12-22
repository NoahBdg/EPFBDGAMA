/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetepfgama;

import java.util.HashMap;
/**
 *
 
*/

//j'ai fini la gestion des couleurs avec la classe Couleurs. Faut bosser sur la classe cellule comme dans LightsOff, c'est à peu près la même chose. Appelle si t'as besoin.
public class Cellule {

    static int côté;

    static Cellule randomTileHardMode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private final Couleurs tac;
    private final static HashMap<Integer, Cellule> cache = new HashMap<>();

    public final static Cellule Zero = new Cellule(Couleurs._0);
    public final static Cellule Deux = new Cellule(Couleurs._2);
    public final static Cellule Quatre = new Cellule(Couleurs._4);

    // On utilise le cache pour stocker
    static {
        for (Couleurs n : Couleurs.values()) {
            switch (n) {
            case _0:
                cache.put(n.valeur(), Zero);
                break;
            case _2:
                cache.put(n.valeur(), Deux);
                break;
            case _4:
                cache.put(n.valeur(), Quatre);
                break;
            default:
                cache.put(n.valeur(), new Cellule(n));
                break;
            }
        }
    }

    // constructor
    public Cellule(Couleurs n) {
        tac = n;
    }

    Couleurs valeur() {
        return tac;
    }

    public static Cellule valeurDe(int num) {
        return cache.get(num);
    }

    public Cellule getDouble() {
        return valeurDe(tac.valeur() << 1);
    }

    boolean vide() {
        return tac == Couleurs._0;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Cellule))
            return false;
        Cellule other = (Cellule) obj;
        if (tac != other.tac)
            return false;
        return true;
    }

    // Utilisation de l'opérateur ternaire pour faire spawn un 2 OU un 4 à chaque coup
    static Cellule CelluleAléatoire() {
        return Math.random() < 0.15 ? Quatre : Deux;
    }

    public String toString() {
        return String.format("%1$4d", tac.valeur());

    }

}