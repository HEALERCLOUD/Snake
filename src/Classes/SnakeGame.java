package Classes;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;



public class SnakeGame extends JPanel{

    private class Tile{
        int x;
        int y;

        private Tile(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    final int tileSize = 25;

    Tile snakeHead;
    public SnakeGame(int boardWidth,int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);

        snakeHead = new Tile(5*tileSize,5*tileSize);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.setColor(Color.green);
        g.fillRect(snakeHead.x,snakeHead.y,tileSize,tileSize);
    }
}
