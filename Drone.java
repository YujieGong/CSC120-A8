import java.awt.*;

import javax.management.monitor.StringMonitor;
import javax.swing.text.Position;

public class Drone implements Contract{
   public String name;
   public int age;
   private Point current_location;
   public boolean status;
   public int percent;
   private int current_height;
   public int time_operating;
//    public boolean walkable;


// an object drone is created and attributes are created
    public Drone(String name, int age){
        this.name = name;
        this.age = age;
        this.current_location = new Point(100, 20);
        this.status = true;
        this.percent = 100;
        this.time_operating = 0;
        // this.walkable = false;
    }
// if the drone grabs the camera, then print it grabbed the camera. 

    public void grab(String item){
        System.out.println("Grabbed a "+item + "!");

    }
//drop the item if the item is not the one we wants to grab
    public String drop(String item){
        System.out.println("The object cannot be attatched to the drone!");
        return item;
        
    }

//examine if the item we take the we want

    public void examine(String item){
        System.out.println("Detected " +item + "!");
        if (item == "camera"){
           use(item);
        } else{
            drop(item);
        }
    }

    // if the item is the item we want to use, call this method to use it

    public void use(String item){
        System.out.println("The "+item+" is attatched to the drone!");

    }

    // determine if the drone can walk within 1000 meters from the center
    public boolean walk(String direction){
        boolean walkable = true;
        if (direction.equals("right")){
            if(current_location.getX()>=1000){
                walkable = false;
                // System.out.println(walkable);
            }else if (current_location.getX()<1000){
                walkable = true;
            }
        }else if  (direction.equals("left")){
            if (current_location.getX()>-1000){
                walkable = true;
            }else if (current_location.getX()<-1000){
                walkable = false;
            }
        

        }else if (direction.equals("forward")){
            if(current_location.getY()<1000){
                walkable = true;
            }else if(current_location.getY()>1000){
                walkable = false;
            }
        
        }else if (direction.equals("backward")){
            if(current_location.getY()>-1000){
                walkable = true;
            }else if (current_location.getY()<-1000){
                walkable = false;
            }
        }
        // System.out.println(walkable);
        return walkable;
    
        }

    //find the location of the drone after walking
    public Point walking(String direction, int distance){
        boolean b = walk(direction);
        // System.out.println(b);
        if (b){
            if (direction == "left"){
                    current_location.setLocation(current_location.getX()-distance, current_location.getY());
            }if (direction == "right"){
                    current_location.setLocation(current_location.getX()+distance, current_location.getY());
            }if (direction == "forward"){
                    current_location.setLocation(current_location.getX(), current_location.getY()+distance);
            }if (direction == "backward"){
                    current_location.setLocation(current_location.getX(), current_location.getY()-distance);
                }
            System.out.println("The current location is " + current_location + ".");
        }else{
            System.out.println("Out of the range of walking! The drone is now out of the 1000 feet rage");
            System.out.println(current_location);
           
        }
        return current_location;
        }

    // restricting the maximum distance the drone can fly and the maximun height the drone can fly
        
    public boolean fly(int x, int y){
        boolean flyable = true;
        if (x < 1000 && y<400){
        flyable = true;
        }else if (x>1000 | y>400){
            flyable = false;
        }
        // System.out.println(flyable);
        return flyable;
    }

    public void flying(String direction_xy, int distance, String direction_z, int height){
        boolean c = fly(distance, height);
        // System.out.println(c);
        if (c){
            if (direction_z == "upwards"){
            current_height = current_height + height;
            } if (direction_z == "dowards"){
            current_height = current_height-height;
            }
            if (direction_xy == "left"){
                current_location.setLocation(current_location.getX()-distance, current_location.getY());
            }if (direction_xy == "right"){
                current_location.setLocation(current_location.getX()+distance, current_location.getY());
            }if (direction_xy == "forward"){
                current_location.setLocation(current_location.getX(), current_location.getY()+distance);
            }if (direction_xy == "backward"){
                current_location.setLocation(current_location.getX(), current_location.getY()-distance);
            }
            System.out.println("The current location is " + current_location +" , and the current height is " +current_height);
        }else{
            System.out.println("Out of range of input! The input for height should be lower than 400 feet and input for distance flying should be lower than 1000 feet");
            System.out.println("The current location is "+ current_location);
        }
    }

    public boolean on_or_off(String turn, int time_operating1){
        if (time_operating1 > 100){
            System.out.println("CANNOT FUNCTION THIS LONG");
        } else if (turn == "Off"){
                status = false;
                percent = percent_comsuming(time_operating1);
                System.out.println("The drone is off and the current percent of battery is " + percent +" %. If the battery is low, remember to charge it!!");
        } else if (turn == "On"){
                if (percent > 0){
                status = true;
                System.out.println("The drone is on. The percent of battery left is " + percent+ " %. If the battery is low, remember to charge it!!" );
                }
                else{
                rest();
                }
            }
            return status;
    }

    public int percent_comsuming(int time_operating){
        for (int i = 1; i <=time_operating; i++){
            shrink();
        }
       return percent;
    }

    public int charging(int time_charging){
        for (int i =1 ; i <= time_charging; i++){
            grow();
        }
        return percent;
    }

    
    public Number shrink(){
        percent = percent -1;
        return percent;
    }
    
    public Number grow(){
        percent = percent + 1;
        return percent;

    }
    public void rest(){
        if (percent <= 0){
            System.out.println("The device neends to be charged!");
            status = false;
        System.out.println("The device is off now!");
        }

    }
    public void undo(){
        current_location.setLocation(0,0);
        System.out.println("The current location  of the drone is: " + current_location);

       
    }

    public String toString(){
        return (this.name + " is " + this.age + " years old.");
    }

    public static void main(String[] args) {
        System.out.println("------INFORMATION OF THE DRONE-----");
        Drone drone1 = new Drone("Dronne", 2);
        System.out.println(drone1);
        System.out.println("-----USER INTERFACE-----");
        drone1.on_or_off("On",0);
        drone1.grab("camera");
        drone1.examine("camera");
        drone1.walking("right", 100);
        drone1.flying("right", 900, "upwards", 500);
        drone1.undo();
        drone1.on_or_off("Off",50);
        drone1.on_or_off("On",0);
        drone1.on_or_off("Off",50);
        drone1.on_or_off("On",50);
        drone1.charging(50);
        drone1.on_or_off("On",50);
      
   


     
       
        
       
       



    }

    


 








    
    
}
