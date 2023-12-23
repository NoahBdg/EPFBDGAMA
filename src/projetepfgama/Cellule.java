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

    /**
     ** La classe Cellule représente une cellule dans le jeu Threes.
 * Chaque cellule a une couleur associée qui détermine sa valeur.
 * 
 * Le constructeur {@code Cellule(Couleurs n)} permet d'initialiser une cellule avec une couleur donnée.
 * La méthode {@code valeur()} renvoie la couleur de la cellule.
 * 
 * La méthode statique {@code valeurDe(int num)} crée une nouvelle instance de Cellule à partir d'un entier représentant sa valeur.
 * 
 * La méthode {@code getDouble()} renvoie une nouvelle instance de Cellule avec une valeur deux fois plus grande que la valeur actuelle.
 * 
 * La méthode {@code vide()} vérifie si la cellule est vide, c'est-à-dire si sa valeur est égale à Couleurs._0.
 
     * @param n
     */
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

    /**
     *La méthode equals compare cette cellule à un autre objet pour déterminer l'égalité.
 * Deux cellules sont considérées égales si elles ont la même couleur.
 *
 * @param obj L'objet à comparer avec la cellule.
 * @return true si les cellules sont égales, false sinon.
 *
 * La méthode statique {@code CelluleAleatoire()} génère aléatoirement une nouvelle cellule avec une probabilité de 15% pour un 4
 * et 85% pour un 2 à chaque coup. Elle est utilisée pour le spawn des nouvelles cellules lors du déplacement du joueur.
 *
 * @return Une nouvelle cellule aléatoire (2 ou 4) selon les probabilités définies.
 
     * @param obj
    
     */
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

    /**
     *La méthode toString retourne une représentation sous forme de chaîne de caractères de la valeur de la cellule.
 * Utilise le formatage pour garantir une largeur de champ de 4 caractères.
 *
     * @return
     */
    public String toString() {
        return String.format("%1$4d", tac.valeur());

    }

}