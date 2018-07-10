/************************
Name: Johnathan Diamond
Date: 15 - 02 - 2017
File: BattleshipFrameViewer.java
Desc: This is the viewer for BattleshipFrame.
************************/

import javax.swing.JFrame;

public class BattleshipFrameViewer
{
   public static void main(String[] args)
   {  	
      JFrame battleshipFrame = new BattleshipFrame();		
      
      battleshipFrame.setTitle("Javaships");
      battleshipFrame.setLocationRelativeTo(null);  //center frame
      battleshipFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      battleshipFrame.setVisible(true);
      battleshipFrame.setResizable(false);
   }
}