import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CFrame extends JPanel implements ActionListener {


    ArrayList<Person> people = new ArrayList<Person>();
    ArrayList<Point> pointsInfected = new ArrayList<Point>();
    ArrayList<Point> pointsHealthy = new ArrayList<Point>();
    ArrayList<Point> pointsRecovered = new ArrayList<Point>();
    public int x_size = 800;
    final int Y_SIZE = 730;
    final int POPULATION = 500;
    

    int time = 0;

    public static void main(String[] args) {
        CFrame c = new CFrame();

    }

    public void paint(Graphics g){
        //g.fillOval(10, 10, 100, 100);
        time += 16;
        pointsInfected.add(new Point(time/16, Person.numInfected));
        pointsRecovered.add(new Point(time/16, Person.numRecovered));
        pointsHealthy.add(new Point(time/16, Person.numHealthy));

        for (Person p : people){
            p.paint(g);
        }
        for(int i = 0; i < people.size(); i++){
            for (int j = i + 1; j < people.size(); j++){
                people.get(i).collision(people.get(j));
            }
        }
        g.setColor(Color.red);
        for (Point p: pointsInfected){
            g.fillOval(p.getTime(), 700-(p.getValue() / (POPULATION / 100)), 3, 3);
        }

        g.setColor(Color.green);
        for (Point p: pointsHealthy){
            g.fillOval(p.getTime(), 700-(p.getValue() / (POPULATION / 100)), 3, 3);
        }
        g.setColor(Color.blue);
        for (Point p: pointsRecovered){
            g.fillOval(p.getTime(), 700-(p.getValue() / (POPULATION / 100)), 3, 3);
            //this.setSize(p.getTime(), Y_SIZE);
        }

        g.setColor(Color.black);
        g.drawLine(0, 600, 800, 600);
        g.drawLine(0, 700, 800, 700);

        g.setColor(Color.red);
        g.drawString(String.format("Infected: %d", Person.numInfected), x_size - 100, 635);
        g.setColor(Color.blue);
        g.drawString(String.format("Recovered: %d", Person.numRecovered), x_size - 100, 650);
        g.setColor(Color.green);
        g.drawString(String.format("Healthy: %d", Person.numHealthy), x_size - 100, 620);


    }

    public CFrame () {
        JFrame frame = new JFrame("Simulation");
        frame.setSize(x_size, Y_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.black);
        frame.setResizable(false);

        for (int i = 0 ; i < POPULATION ; i++){
            people.add(new Person());
        }

        Timer t = new Timer(16, this);
        t.restart();
        
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
    }
    

}