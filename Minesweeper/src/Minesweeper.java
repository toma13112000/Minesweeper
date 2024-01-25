import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class Minesweeper extends JFrame
{
    private Game game;

    private final int IMAGE_SIZE = 50;
    private final int Cols = 9;
    private final int Rows = 9;
    private final int Bombs = 10;

    private JPanel panel;
    private JLabel label;

    public static void main(String[] args)
    {
        new Minesweeper();
    }
    private Minesweeper() {
        game =  new Game(Cols, Rows, Bombs);
        game.start();
        setImages();
        initPanel();
        initLabel();
        initFrame();
    }

    private void initLabel()
    {
        label = new JLabel(getMessage());
        Font font = new Font("Tahoma", Font.BOLD, 20);
        label.setFont(font);
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel()
    {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(Coord coord:Ranges.getAllCoords())
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE,
                            coord.y * IMAGE_SIZE, this);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX()/IMAGE_SIZE;
                int y = e.getY()/IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                switch (e.getButton())
                {
                    case MouseEvent.BUTTON1:game.pressLeftButton(coord); break;
                    case MouseEvent.BUTTON3:game.pressRightButton(coord);break;
                    case MouseEvent.BUTTON2:game.start();break;
                }
                label.setText(getMessage());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE,
                                             Ranges.getSize().y * IMAGE_SIZE));
        add(panel);
    }
    private void initFrame()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void setImages()
    {
        for (Box box:Box.values())
            box.image = getImage(box.name().toLowerCase());
        setIconImage(getImage("icon"));
    }

    private Image getImage(String name)
    {
        String filename = "img/" + name +".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

    private String getMessage()
    {
        switch (game.getState())
        {
            case BOMBED: return "You Lose!";
            case WINNER: return "Congratulations";
            case PLAYED:
            default    :
                if(game.getTotalFlaged()==0)
                    return "Welcome!";
                else
                    return "Think twice! Flagged "+
                            game.getTotalFlaged() + " of " +
                            game.getTotalBombs() + " bombs.";
        }
    }
}