/************************
Name: Johnathan Diamond
Date: 15 - 02 - 2017
File: BattleshipFrame.java
Desc: This is a program that creates components to play Battleships
************************/

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class BattleshipFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
// INSTANCE VARIABLES //
   private Color darkBlue = new Color(10, 39, 51);
   private final Dimension FRAME_SIZE = new Dimension(500, 680);

   private JLabel livesRemaining, shipsRemaining;
   private JLabel scoreLabel;
   private JButton[][] shipButtonsArray; //each JButton will be stored in a 2Dim array
   private JTextArea scoreBoard;
   private JCheckBoxMenuItem boatIdentifierItem;

   private ActionListener listener = new BattleshipListener();
   private BattleshipsGame game;

   ////////////////////////////////
   //    Constructor             //
   ////////////////////////////////
   public BattleshipFrame()
   {
      callDifficultyPane();

      setSize(FRAME_SIZE);

      createMenu();// this section of code starts from line 252. was put at the end to save viewing space.
      createGameFrame();

      setBackground(darkBlue);
   }

   ////////////////////////////////
   //    Methods                 //
   ////////////////////////////////
   public void callDifficultyPane()
   {
      String[] difficulties = {"Guaranteed win", "Easy", "Medium", "Hard", "Expert", "Grand Wizard"};
      String difficulty = (String) JOptionPane.showInputDialog(null, "Note: Canceling or exiting will default to Medium\n\n"+
      "Select your difficulty:","Difficulty Selection", JOptionPane.QUESTION_MESSAGE, null, difficulties, "Medium");

      // @param lives   @param ships
      if(difficulty == "Guaranteed win")
         game = new BattleshipsGame(16, 4);
      else if(difficulty == "Easy")
         game = new BattleshipsGame(8, 4);
      else if(difficulty == "Medium")
         game = new BattleshipsGame(7, 6);
      else if(difficulty == "Hard")
         game = new BattleshipsGame(5, 8);
      else if(difficulty == "Expert")
         game = new BattleshipsGame(3, 5);
      else if(difficulty == "Grand Wizard")
         game = new BattleshipsGame(1, 3);
      else
         game = new BattleshipsGame();//default constructor in case nothing is selected - crash || System.exit(0);
   }

   public void createGameFrame()
   {
      //adding each component via method
      add(createHeader(), BorderLayout.NORTH);
      add(createShipButtons(), BorderLayout.CENTER);
      add(createRightPanel(), BorderLayout.EAST);
      add(createFooterDisplay(), BorderLayout.SOUTH);
   }


   ////////////////////////////////
   //    Creating components     //
   ////////////////////////////////
   public JPanel createHeader()
   {
      JPanel panel = new JPanel();
      JLabel label = new JLabel(new ImageIcon("images/Header.jpg"));

      // FORMAT THE HEADER //
      panel.setBackground(darkBlue);
      panel.setPreferredSize(new Dimension(500, 150));

      panel.add(label);

      return panel;
   }

   public JPanel createShipButtons()
   {
      JPanel panel = new JPanel(new GridLayout(4,4)); //4X4 grid, no spacing to give seamless impression

      shipButtonsArray = new JButton[4][4];

      // CREATING EACH BUTTON  AND ADDING VALUES//
      for(int r = 0; r < 4; r++)
         for(int c = 0; c < 4; c++)
         {
            shipButtonsArray[r][c] =  new JButton();
            shipButtonsArray[r][c].setActionCommand(r+", "+c); //set an ActionCommand to identify button clicked - row and column
            shipButtonsArray[r][c].addActionListener(listener);

            shipButtonsArray[r][c].addMouseListener(
               new MouseAdapter()
               {
                  public void mouseEntered(MouseEvent e)
                  {
                     JButton temp = (JButton)e.getSource();//cast as a JButton
                     temp.setIcon(new ImageIcon("images/GenericAimButton.jpg"));
                  }
                  public void mouseExited(MouseEvent e)
                  {
                     JButton temp = (JButton)e.getSource();
                     temp.setIcon(new ImageIcon("images/GenericButton.jpg"));
                  }
               } );

            shipButtonsArray[r][c].setIcon(new ImageIcon("images/GenericButton.jpg"));
            panel.add(shipButtonsArray[r][c]);
         }

      return panel;
   }

   public JLabel createRightPanel()
   {
      // STATIC BACKGROUND IMAGE WITH A GRID LAYOUT //
      scoreLabel = new JLabel(new ImageIcon("images/Side_bar.jpg"));
      scoreLabel.setLayout(new GridLayout()); //set new layout to make the scoreBoard stay within JLabel boundaries
      scoreBoard = new JTextArea();

      // FORMATTING THE TEXTAREA //
      scoreBoard.setForeground(Color.BLACK);
      scoreBoard.setMargin(new Insets(63, 18, 10, 10)); //set the margin distance = @param(a,b,c,d) Top | Left | Right | Bot
      scoreBoard.setFont(new Font("Arial", Font.BOLD, 16));
      scoreBoard.setEditable(false);
      scoreBoard.setOpaque(false); //make transparent to show b/ground

      scoreLabel.add(scoreBoard);

      return scoreLabel;
   }

   public JLabel createFooterDisplay()
   {
      JLabel footerLabel = new JLabel(new ImageIcon("images/footer.jpg"));
      footerLabel.setLayout(new GridLayout(1,2));
      footerLabel.setBackground(darkBlue);

      livesRemaining = new JLabel(""+game.getLives(), SwingConstants.RIGHT);
      shipsRemaining = new JLabel(""+(game.getNOOfShips()-game.getHits()), SwingConstants.RIGHT);

      // FORMATTING FONT //
      livesRemaining.setFont(new Font("Arial", Font.BOLD, 32));
      livesRemaining.setForeground(Color.BLACK);
      livesRemaining.setBorder(BorderFactory.createEmptyBorder(0,0,0,40));//no setMargin() method, researched BorderFactory

      shipsRemaining.setFont(new Font("Arial", Font.BOLD, 32));
      shipsRemaining.setForeground(Color.BLACK);
      shipsRemaining.setBorder(BorderFactory.createEmptyBorder(0,0,0,40));

      footerLabel.add(livesRemaining);
      footerLabel.add(shipsRemaining);

      return footerLabel;
   }


   public void shoot(String eventIn)
   {
      int r = Integer.parseInt(""+eventIn.charAt(0)); //eventIn is the ActionCommand from ActionListener. (String)("r, c")
      int c = Integer.parseInt(""+eventIn.charAt(3));

      String s = game.shoot(r, c); //calling method from BattleshipGame. returns a string

      if(game.checkIfShip(r,c))
         setHit(r, c);//calling methods, with @param row, @param column
      else if(!game.checkIfShip(r,c))
         setMiss(r, c);
      scoreBoard.append(r+", "+c+": "+s+"\n"); //append @param row, @parma column, @param returned string from shoot(r,c)

      livesRemaining.setText(""+game.getLives());
      shipsRemaining.setText(""+(game.getNOOfShips()-game.getHits()));

      if(game.getLives()==0)//if no more lives
      {
         showBoard();//reveals the board and disables buttons.

         JOptionPane.showMessageDialog(null,"You have lost! =[", "Game Over", JOptionPane.WARNING_MESSAGE);

         int response = JOptionPane.showConfirmDialog(null, "Would you like to play again?",
            "Play again?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
         if(response==JOptionPane.YES_OPTION)
         {
            callDifficultyPane();
            resetBoard();
         }
      }

      else if(game.getNOOfShips()-game.getHits()==0)//if all ships destroyed
      {
         showBoard();

         JOptionPane.showMessageDialog(null, "You win!", "WINNER!", JOptionPane.INFORMATION_MESSAGE);

         int response = JOptionPane.showConfirmDialog(null, "Would you like to play again?",
            "Play again?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
         if(response==JOptionPane.YES_OPTION)
         {
            callDifficultyPane();
            resetBoard();
         }
      }
   }

   public void enableToolTips()
   {
      for(int i = 0; i < 4; i++)
         for(int j = 0; j < 4; j++)
            shipButtonsArray[i][j].setToolTipText(""+game.checkIfShip(i,j));
   }

   public void disableAllToolTips()
   {
      for(int i = 0; i < 4; i++)
         for(int j = 0; j < 4; j++)
            shipButtonsArray[i][j].setToolTipText("");
   }

   public void setHit(int i, int j)
   {
      shipButtonsArray[i][j].setDisabledIcon(new ImageIcon("images/GenericHitButton.jpg"));
      shipButtonsArray[i][j].setEnabled(false);
   }

   public void setMiss(int i, int j)
   {
      shipButtonsArray[i][j].setDisabledIcon(new ImageIcon("images/GenericMissButton.jpg"));
      shipButtonsArray[i][j].setEnabled(false);
   }

   public void resetBoard()//competely resets the board.
   {
      for(int i = 0; i < 4; i++)
         for(int j = 0; j < 4; j++)
            shipButtonsArray[i][j].setEnabled(true);

      boatIdentifierItem.setState(false);//auto turns cheat tooltip off
      scoreBoard.setText("");//clearing the scoreboard.
      livesRemaining.setText(""+game.getLives());
      shipsRemaining.setText(""+(game.getNOOfShips()-game.getHits()));
   }

   public void showBoard()
   {
      for(int i = 0; i < 4; i ++)
         for(int j = 0; j < 4; j++)
            if(game.checkIfShip(i,j))
               setHit(i, j);
            else if(!game.checkIfShip(i,j))
               setMiss(i, j);
   }

/**********************INNER CLASS******************************/
   public class BattleshipListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         shoot(event.getActionCommand()); //calling this class' method shoot(), getActionCommand = (String)("r, c")
      }
   }
///////////////////////////////////////////////////////////////////////
/************These are all the components for the JMenuBar************/
///////////////////////////////////////////////////////////////////////

   public void createMenu()
   {
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);

      menuBar.add(createFileMenu());
      menuBar.add(createCheatsMenu());
      menuBar.add(createHelpMenu());
   }

   public JMenu createFileMenu()
   {
      JMenu menu = new JMenu("File");

      menu.add(createNewGameMenu());
      menu.add(createExitItem());
      return menu;
   }

   public JMenuItem createNewGameMenu()
   {
      JMenu newGameMenu = new JMenu("New Game");

      newGameMenu.add(createGuaranteedWinItem());
      newGameMenu.add(createEasyItem());
      newGameMenu.add(createMedItem());
      newGameMenu.add(createHardItem());
      newGameMenu.add(createExpertItem());
      newGameMenu.add(createGrandWizardItem());

      return newGameMenu;
   }

   public JMenuItem createGuaranteedWinItem()
   {
      JMenuItem gWItem = new JMenuItem("Guaranteed Win");

      class MenuItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
            game = new BattleshipsGame(16,4);
            resetBoard();
         }
      }

      ActionListener listener = new MenuItemListener();
      gWItem.addActionListener(listener);
      gWItem.setToolTipText("You have 16 lives and have to find 4 ships.");

      return gWItem;
   }

   public JMenuItem createEasyItem()
   {
      JMenuItem easyItem = new JMenuItem("Easy");

      class MenuItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
            game = new BattleshipsGame(8,4);
            resetBoard();
         }
      }

      ActionListener listener = new MenuItemListener();
      easyItem.addActionListener(listener);
      easyItem.setToolTipText("You have 8 lives and have to find 4 ships.");

      return easyItem;
   }

   public JMenuItem createMedItem()
   {
      JMenuItem medItem = new JMenuItem("Medium");

      class MenuItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
            game = new BattleshipsGame(7,6);
            resetBoard();
         }
      }

      ActionListener listener = new MenuItemListener();
      medItem.addActionListener(listener);
      medItem.setToolTipText("You have 7 lives and have to find 6 ships.");

      return medItem;
   }

   public JMenuItem createHardItem()
   {
      JMenuItem hardItem = new JMenuItem("Hard");

      class MenuItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
            game = new BattleshipsGame(5,8);
            resetBoard();
         }
      }

      ActionListener listener = new MenuItemListener();
      hardItem.addActionListener(listener);
      hardItem.setToolTipText("You have 5 lives and have to find 8 ships.");

      return hardItem;
   }

   public JMenuItem createExpertItem()
   {
      JMenuItem expertItem = new JMenuItem("Expert");

      class MenuItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
            game = new BattleshipsGame(3,5);
            resetBoard();
         }
      }

      ActionListener listener = new MenuItemListener();
      expertItem.addActionListener(listener);
      expertItem.setToolTipText("You have 3 lives and have to find 5 ships.");

      return expertItem;
   }

   public JMenuItem createGrandWizardItem()
   {
      JMenuItem gWItem = new JMenuItem("Grand Wizard");

      class MenuItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
            game = new BattleshipsGame(1,3);
            resetBoard();
         }
      }

      ActionListener listener = new MenuItemListener();
      gWItem.addActionListener(listener);
      gWItem.setToolTipText("You have 1 lives and have to find 3 ships.");

      return gWItem;
   }


   public JMenuItem createExitItem()
   {
      JMenuItem exitItem = new JMenuItem("Exit");

      class MenuItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
         int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
            "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
         if(response==JOptionPane.YES_OPTION)
            System.exit(0);  // exit -  shut down
         }
      }

      ActionListener listener = new MenuItemListener();
      exitItem.addActionListener(listener);
      exitItem.setToolTipText("This will terminate the game.");

      return exitItem;
   }

   public JMenu createCheatsMenu()
   {
      JMenu menu = new JMenu("Cheats");

      menu.add(enableToolTip());
      menu.add(getAFreeHint());

      return menu;
   }

   public JCheckBoxMenuItem enableToolTip()
   {
      boatIdentifierItem = new JCheckBoxMenuItem("Enable Tool Tips", false);//the default state
      boatIdentifierItem.setToolTipText("This will enable the player to see if the button you are hovering over is a ship or not.");

      class MenuItemListener implements ItemListener
      {
         public void itemStateChanged(ItemEvent event)
         {
            if(boatIdentifierItem.getState())//when clicked, enable the tooltip
               enableToolTips();
            else
               disableAllToolTips();//if not, disable method called
         }
      }

      ItemListener listener = new MenuItemListener();
      boatIdentifierItem.addItemListener(listener);

      return boatIdentifierItem;
   }

   public JMenuItem getAFreeHint()
   {
      JMenuItem item = new JMenuItem("Get a free hint");

      class MenuItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
            Random noGen = new Random();
            boolean ship = false;
            do
            {
               int r = noGen.nextInt(4);
               int c = noGen.nextInt(4);
               if(game.checkIfShip(r,c) && (shipButtonsArray[r][c].isEnabled()))
               {
                  shipButtonsArray[r][c].setIcon(new ImageIcon("images/GenericHintButton.jpg"));
                  ship=true;
               }
            }while(!ship);
         }
      }

      ActionListener listener = new MenuItemListener();
      item.addActionListener(listener);
      item.setToolTipText("This will show you which button is a ship by highlighting it with a green '?'");

      return item;
   }

   public JMenu createHelpMenu()
   {
      JMenu menu = new JMenu("Help");

      menu.add(createHelpItem()) ;
      menu.add(createAboutItem());

      return menu;
   }

   public JMenuItem createHelpItem()
   {
      JMenuItem item = new JMenuItem("How to play");
      item.setToolTipText("This will give you a guide into how to play Javaships.");

      class MenuItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
               JOptionPane.showMessageDialog(null, getHowToPlay(),"How to play", JOptionPane.INFORMATION_MESSAGE);
         }
      }

      ActionListener listener = new MenuItemListener();
      item.addActionListener(listener);

      return item;
   }

   public JMenuItem createAboutItem()
   {
      JMenuItem item = new JMenuItem("About this program");

      class MenuItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
            JOptionPane.showMessageDialog(null, getTechInfo(),"About this Program", JOptionPane.INFORMATION_MESSAGE);
         }
      }

      ActionListener listener = new MenuItemListener();
      item.addActionListener(listener);
      item.setToolTipText("The technical things of this program.");

      return item;
   }

   public String getHowToPlay()
   {
      String s =
      "How to Play Javaships\n\n"+
      "Javaships runs very much like the original Battleships game, only now it's\n"+
      "on the pc. Amazing!\n\n"+
      "There are 6 levels of difficulty:\n\n"+
      "Guaranteed Win: Lives - 16 || Ships - 4\n"+
      "Easy: Lives - 8 || Ships - 4\n"+
      "Normal: Lives - 7 || Ships - 6\n"+
      "Hard: Lives - 5 || Ships - 8\n"+
      "Expert: Lives - 3 || Ships - 5\n"+
      "Grand Wizard: Lives - 1 || Ships - 3\n\n"+
      "Ships are positioned across the 4X4 grid. Your task is to locate where on the grid\n"+
      "the ships are before your lives reach 0.\n"+
      "Each time you miss, you lives are reduced by 1.\n"+
      "Hitting a ship will result in the number of ships remaining to go down by 1\n\n"+
      "Your goal is to sink ALL the ships in the grid.\n\n"+
      "You may have noticed, there is a cheats menu. Don't look in there. There is nothing of benefit\n"+
      "to you in there. Seriously, who likes a cheater anyway?\n\n"+
      "Enjoy and best of luck.";

      return s;
   }

   public String getTechInfo()
   {
      String s =
      "Some technical stuff about the program.\n\n"+
      "The program starts by asking the player to select the desired difficulty.\n"+
      "If the player selects the 'x' or cancel, the default BattleshipsGame constructor is called.\n"+
      "This was to avoid crashes as I was unable to come up with an alternatie solution.\n"+
      "Each of these components are created using methods and returning the component.\n\n"+
      "It goes on to create the MenuBars, then the Frame it's self. The window has been locked to a\n"+
      "set size, purely for aesthetic reasons. My images were all size to fit a desired window.\n\n"+
      "The cheats menu is there, primarily for testing, but I felt I had to add more to the menu bar\n"+
      "other than File-New Game/Exit. These info menus are just reminisent of older games. They would\n"+
      "always have some kind of About or Info page.\n\n"+
      "I hit a few snags along the way, originally, I was going to have this and the How to play messages\n"+
      "to be read from documents and printed to this dialog box but it was proving to be very tedious. It\n"+
      "would have also saved on space and paper.\n\n"+
      "I realise there are a number of items in the program that goes beyond what we've been taught in class.\n"+
      "Insets, BorderFactory, Dimensions, etc. I spent some time going through the API and going through online\n"+
      "examples/resources in order to get an understanding of what each component did before i implemented it.\n\n"+
      "All in all, I actually had fun writing this program. It really tested my abilities and\n"+
      "I think I done myself some justice. I'm proud of this work.\n"+
      "I did have other ideas to add to the program, but that would have involved altering the source\n"+
      "code. If I get some spare time in the future, I will go back over it and make some alterations for feedback.\n\n"+
      "Hope you enjoyed playing it. =]";

      return s;
   }
}
