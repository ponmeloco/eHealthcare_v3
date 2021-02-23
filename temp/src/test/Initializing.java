package test;
import javax.swing.*;

public class Initializing{


        public static void initializeProgram(){

                try{
                UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
                }
                catch(UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e){
                        System.out.println("Can't change LookAndFeel");
                }
        }
}
