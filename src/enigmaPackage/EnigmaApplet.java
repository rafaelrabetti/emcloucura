package enigmaPackage;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;

/**
 * <p>Title: Enigma Applet</p>
 * <p>Description: User Interface for Enigma Applet</p>
 * @author Meghan Emilio
 * @version 1.0
 */

public class EnigmaApplet extends JApplet implements ActionListener{

  private Box panel;
  private JPanel panel1;
  private JPanel panel2;
  private JPanel panel3;
  private JPanel panel4;

  private TextArea inputField;
  private TextArea outputField;
  private TextArea plugBoard;
  private JCheckBox heuristic;
  private JLabel label1;
  private JLabel label2;
  private JLabel label3;
  private JLabel label4;
  private JLabel label5;
  private JLabel label6;
  private JLabel label7;
  private JLabel label8;
  private JLabel label9;
  private JLabel label10;
  private Choice rOne;
  private Choice rTwo;
  private Choice rThree;
  private Choice refl;
  private Choice initOne;
  private Choice initTwo;
  private Choice initThree;
  private ButtonGroup group;
  private JRadioButton analyzeRB;
  private JRadioButton encryptRB;
  private JButton encrypt;
  private JButton analyze;
  private Enigma enigma;
  private Analyzer analyzer;

 /**
  *Class Constructor
  *@param void
  *@return void
  */
  public EnigmaApplet() {
  }
  
  /**
   *Initializes the applet
   *@param void
   *@return void
   */
  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *Initializes the Component
   *@param void
   *@return void
   */
  private void jbInit() throws Exception {
	
	//Initialize each component for the applet
	
    label1 = new JLabel(" Enter text: ");
    outputField = new TextArea("", 5, 15, TextArea.SCROLLBARS_VERTICAL_ONLY);
    outputField.setEditable(false);
    outputField.setEnabled(true);
    
    label2 = new JLabel(" Result: ");
    inputField = new TextArea("", 5, 15, TextArea.SCROLLBARS_VERTICAL_ONLY);
    
    label7 = new JLabel(" Enter Plug Board settings: ");
    plugBoard = new TextArea("", 5, 15, TextArea.SCROLLBARS_VERTICAL_ONLY);

    label3 = new JLabel(" Right Rotor: ");
    rOne = new Choice();
    rOne.add("RotorI");
    rOne.add("RotorII");
    rOne.add("RotorIII");
    rOne.add("RotorIV");
    rOne.add("RotorV");
    rOne.add("RotorVI");
    rOne.add("RotorVII");
    rOne.add("RotorVIII");
    
    label4 = new JLabel(" Middle Rotor: ");
    rTwo = new Choice();
    rTwo.add("RotorI");
    rTwo.add("RotorII");
    rTwo.add("RotorIII");
    rTwo.add("RotorIV");
    rTwo.add("RotorV");
    rTwo.add("RotorVI");
    rTwo.add("RotorVII");
    rTwo.add("RotorVIII");
    
    label5 = new JLabel(" Left Rotor: ");
    rThree = new Choice();
    rThree.add("RotorI");
    rThree.add("RotorII");
    rThree.add("RotorIII");
    rThree.add("RotorIV");
    rThree.add("RotorV");
    rThree.add("RotorVI");
    rThree.add("RotorVII");
    rThree.add("RotorVIII");
    
    initOne = choiceFill(new Choice());
    initTwo = choiceFill(new Choice());
    initThree = choiceFill(new Choice());
    
    label6 = new JLabel(" Choose Reflector: ");
    refl = new Choice();
    refl.add("ReflectorB");
    refl.add("ReflectorC");
    refl.add("No Reflector");
    
    group = new ButtonGroup();
    encryptRB = new JRadioButton("Encrypt/Decrypt");
    group.add(encryptRB);
  	encryptRB.setSelected(true);
    analyzeRB = new JRadioButton("Analyze");
    group.add(analyzeRB);
    encrypt = new JButton("Ok");
    encrypt.setPreferredSize(new Dimension(25,30));

    panel = new Box(BoxLayout.Y_AXIS);
	panel.add(encryptRB);
	panel.add(analyzeRB);
	
	heuristic = new JCheckBox("Use Heuristic Algorithm");

    panel1 = new JPanel(new GridLayout(2,2,1,3));
    panel1.add(label1);
    panel1.add(inputField);
    panel1.add(label2);
    panel1.add(outputField);
    panel.add(panel1);

    panel2 = new JPanel();
    panel2.setLayout(new GridLayout(4,3,1,3));
    panel2.add(label3);
    panel2.add(rOne);
    panel2.add(initOne);
    panel2.add(label4);
    panel2.add(rTwo);
    panel2.add(initTwo);
    panel2.add(label5);
    panel2.add(rThree);
    panel2.add(initThree);
    panel2.add(label6);
    panel2.add(refl);
    panel.add(panel2);
    
    panel3 = new JPanel(new GridLayout(1,2,1,3));
    panel3.add(label7);
    panel3.add(plugBoard);
    panel.add(panel3);
	
	panel.add(heuristic);
	heuristic.setEnabled(false);

    panel.add(encrypt);

    this.getContentPane().add(panel);

    encrypt.addActionListener(this);
    encryptRB.addActionListener(this);
    analyzeRB.addActionListener(this);
  }


 /**
  *Fills a Choice with the letters of the alphabet
  *in alphabetical order
  *@param c the Choice to be filled
  *@return Choice
  */
  public Choice choiceFill(Choice c){
    c.add("A");
    c.add("B");
    c.add("C");
    c.add("D");
    c.add("E");
    c.add("F");
    c.add("G");
    c.add("H");
    c.add("I");
    c.add("J");
    c.add("K");
    c.add("L");
    c.add("M");
    c.add("N");
    c.add("O");
    c.add("P");
    c.add("Q");
    c.add("R");
    c.add("S");
    c.add("T");
    c.add("U");
    c.add("V");
    c.add("W");
    c.add("X");
    c.add("Y");
    c.add("Z");
    return c;
  }

 /**
  *Handles the actions of the radiobuttons and button
  *@param ActionEvent e
  *@return void
  */
  public void actionPerformed(ActionEvent e) {
  	
  	//if analyze radio button is selected
  	//disable the encryption options
  	//and enable the analyzer options
  	if(e.getSource().equals(analyzeRB)){
  		plugBoard.setEnabled(false);
  		rOne.setEnabled(false);
  		rTwo.setEnabled(false);
  		rThree.setEnabled(false);
  		refl.setEnabled(false);
  		initOne.setEnabled(false);
  		initTwo.setEnabled(false);
  		initThree.setEnabled(false);
  		heuristic.setEnabled(true);
  	}
  	
  	//if encrypt radio button is selected
  	//disable the analyzer options
  	//and enable the encryption options
  	if(e.getSource().equals(encryptRB)){
  		plugBoard.setEnabled(true);
  		rOne.setEnabled(true);
  		rTwo.setEnabled(true);
  		rThree.setEnabled(true);
  		refl.setEnabled(true);
  		initOne.setEnabled(true);
  		initTwo.setEnabled(true);
  		initThree.setEnabled(true);
  		heuristic.setEnabled(false);
  	}
  	
  	//if the button is pressed...
  	if(e.getSource().equals(encrypt)){
  		//...and the encrypt button is selected
   		if(encryptRB.isSelected()){
   		
   		//then create a new Engima with the current settings  		
      		enigma = new Enigma(rOne.getSelectedItem(), rTwo.getSelectedItem(),
                          rThree.getSelectedItem(), refl.getSelectedItem());                 
      		enigma.initialSettings(initOne.getSelectedItem(), initTwo.getSelectedItem(),
                             initThree.getSelectedItem());

			//and check that the plug board settings are in the correct format
      		String pb = plugBoard.getText();
      		pb.trim();
      		if (!enigma.pbParser(pb)) {
      	  		JOptionPane.showMessageDialog(this, "Error.  Please check Plug Board settings.  Each letter should appear only once.");
      	  		return;
     		 }
     	 	if (!enigma.setPlugBoard(pb)) {
      	  		JOptionPane.showMessageDialog(this, "Error.  Please check that Plug Board settings are in correct format.  Settings should be entered as pairs of letters, each pair seperated by a space.");
      	  		return;
     	 	}
			
			//encrypt/decrypt the inputted text
     	 	String input = inputField.getText();
     	 	if (!input.equals(null) && input.length()>0) {
       			 input = input.trim();
      	 		 String output = enigma.encrypt(input);
       	 	if(output == null || output.equals("null")){
           		JOptionPane.showMessageDialog(this, "Error.  Input string must consist of letters and spaces only.");
           		return;
        	}
        	
        	else{
         	 //and display the result
          		outputField.setText(output);
          		initOne.select(enigma.getFRSetting() + "");
          		initTwo.select(enigma.getSRSetting() + "");
          		initThree.select(enigma.getTRSetting() + "");
       		}
      	}
     }
    
    	//if analyzer button is selected
     	if(analyzeRB.isSelected()){
    		//call Analyzer() to decrypt the message
     		String answer;
     		analyzer = new Analyzer();
     		answer = analyzer.analyze(inputField.getText(), heuristic.isSelected());
     		
     		//and output the results
     		outputField.setText(answer);
  		}
  	}
  	}
  }
  
