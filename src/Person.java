import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Person {
    
    //location
    private int x, y;
    
    
    //velocity
    private int vx, vy;
    
    //status
    private int status;

    //recovery
    private int recoveryTime;

    static int numInfected = 0;
    static int numHealthy = 0;
    static int numRecovered = 0;

    final double SD_PERCENTAGE = 0.9;
    final int REC_TIME_MIN = 5000;
    final int REC_TIME_MAX = 7000;
    final double INIT_INFECTED = 0.08; // In percentage

    public Person (){
        x = (int)(Math.random()*790);
        y = (int)(Math.random()*590);
        
        status = (int)(Math.round(Math.random()));
        
        if(Math.random() < INIT_INFECTED){
            status = 1;
            numInfected++;

        } else {
            status = 0;
            numHealthy++;
        }

        if(Math.random() < 1 - SD_PERCENTAGE){

            vx = (int)(Math.random()*(10+1)+-7);
            vy = (int)(Math.random()*(10+1)+-7);
        }

        recoveryTime = (int)(Math.random()*(REC_TIME_MAX-REC_TIME_MIN+1)+REC_TIME_MIN);

    }

    public void paint(Graphics g){

        switch(status){
            case 0:
                g.setColor(Color.LIGHT_GRAY);
                break;
            case 1:
                g.setColor(Color.red);
                break;
            case 2:
                g.setColor(Color.blue);
                break;

        }

        if(status == 1){
            recoveryTime -= 16;
            if (recoveryTime <= 0){
                status = 2;
                numInfected--;
                numRecovered++;
            }
        }

        x += vx;
        y += vy;

        if(x < 0 || x >= 790){
            vx *= -1;
        }
        if(y <= 0 || y >= 590){
            vy *= -1;
        }



        g.fillOval(x, y, 3, 5);

    }

    public void collision(Person p2){
        Rectangle per2 = new Rectangle(p2.x, p2.y, 3, 5);
        Rectangle per1 = new Rectangle(this.x, this.y, 3, 5);

        if(per1.intersects(per2)){
            if (this.status == 1 && p2.status == 0){
                p2.status = 1;
                numInfected++;
                numHealthy--;
            } else if (this.status == 0 && p2.status == 1) {
                this.status = 1;
                numInfected++;
                numHealthy--;
            }
        }
    }

}