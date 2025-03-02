package main;

import javax.swing.JFrame;

public class  Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();

        window.setTitle("TEST");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePannel gamePannel = new GamePannel();
        window.add(gamePannel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);


        gamePannel.setUpGame();
        gamePannel.stratGameThread();
    }
}
