/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetepfgama;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_R;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
//on va utiliser la classe KeyAdapter qui permet de bind des controles
//Lien de la doc https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyAdapter.html
public class Controles extends KeyAdapter{

	// On créer une table de hachage contenant les méthodes et les touches
    private static final HashMap<Integer, Method> CtrlMapping = new HashMap<>();
   //Utilisation des Keys de notre classe KeyAdapter
    private static Integer[] CTRL = { VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT, VK_R };
    private static String[] methodName = { "up", "down", "left", "right", "initializeTiles" };
    private static Grid board;
    private static final Controles CONT = new Controles();
    
    private Controles() {
        init(CTRL);
    }
    
    public static Controles getKeyPress(Grid g) {
        board = g;
        return CONT;
    }

    // initialiser les touches
    void init(Integer[] kcs) {
        for (int i = 0; i < kcs.length; i++) {
            try {
            	// définition du keymapping ("gestion des controles")
                CtrlMapping.put(kcs[i], Grid.class.getMethod(methodName[i]));
            } catch (NoSuchMethodException | SecurityException e) {
            	// Obtention de l'erreur
                e.printStackTrace();
            }
        }
    }

    // Action lorsqu'une touche est pressée
    public void keyPressed(KeyEvent k) {
        super.keyPressed(k);
        Method action = CtrlMapping.get(k.getKeyCode());
        if (action == null) {
            System.gc();
            return;
        }
        try {
            action.invoke(board);
            board.repaint();
        } catch (InvocationTargetException | IllegalAccessException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }
        //Définition de la défaite, pas de mouvement possible=défaite
        if (!board.MvtPoss()) {
            board.hote.lose();
        }

    }

}
