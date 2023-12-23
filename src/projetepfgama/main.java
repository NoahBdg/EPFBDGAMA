/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projetepfgama;

/**
 *
 * @author lebgd
 */
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.*;

import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import projetepfgama.Grid.Difficulte;

public class main extends JFrame {

    private static final long xyz = 1L;

    JLabel Bar;
    private static final String TITRE = "LE 2048 ";
    public static final String win = "Victoire, on recommence ?";
    public static final String lose = "PERDU, appuie sur R pour rejouer";
//Il faut spécifier le chemin d'accès pour avoir la musique du jeu
    private static final String Chemin_musique = "C:\\Users\\lebgd\\OneDrive\\Documents\\EPFNETBEANS\\2048\\KingUDERZO-2048-Song.wav";

    private Clip musiq;

    public static void main(String[] args) {
        try {
            main game = new main();
            Difficulte difficulty = getDifficultyFromUser();
            Grid board = new Grid(game, difficulty);
            Controles kb = Controles.getKeyPress(board);
            board.addKeyListener(kb);
            game.add(board);

            game.setLocationRelativeTo(null);
            game.setVisible(true);

            game.LancerMsq();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private static Difficulte getDifficultyFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choisi le niveau de difficulté");
        System.out.println("1. Facile");
        System.out.println("2. Moyen");
        System.out.println("3. Hard");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                return Difficulte.Facile;
            case 2:
                return Difficulte.Moyen;
            case 3:
                return Difficulte.Hard;
            default:
                return Difficulte.Moyen;
        }
    }

    public main() throws UnsupportedAudioFileException {
        setTitle(TITRE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(340, 400);
        setResizable(false);

        Bar = new JLabel("");
        add(Bar, BorderLayout.SOUTH);

        initializeBackgroundMusic();
    }

    private void initializeBackgroundMusic() {
        try {
            System.out.println("Lancement de la musique");
            File file = new File(Chemin_musique);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            AudioInputStream Audio = AudioSystem.getAudioInputStream(bis);
            musiq = AudioSystem.getClip();
            musiq.open(Audio);
            System.out.println("Musique bien chargée");
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void LancerMsq() {
        if (musiq != null) {
            musiq.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
//arrête la musique
    public void ArtMsq() {
        if (musiq != null) {
            musiq.stop();
        }
    }

    void win() {
        Bar.setText(win);
        ArtMsq();
    }

    void lose() {
        Bar.setText(lose);
        ArtMsq();
    }
}
