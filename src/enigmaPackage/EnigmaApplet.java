package enigmaPackage;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;

/**
 * <p>Title: Enigma Applet</p>
 * <p>Description: User Interface for Enigma Applet</p>
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
  private JRadioButton encryptRB;
  private JButton encrypt;
  private Enigma enigma;

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
	
    label1 = new JLabel(" Texto: ");
    outputField = new TextArea("", 5, 15, TextArea.SCROLLBARS_VERTICAL_ONLY);
    outputField.setEditable(false);
    outputField.setEnabled(true);
    
    label2 = new JLabel(" Resultado: ");
    inputField = new TextArea("", 5, 15, TextArea.SCROLLBARS_VERTICAL_ONLY);
    
    label7 = new JLabel(" Configuração Plugboard: ");
    plugBoard = new TextArea("", 5, 15, TextArea.SCROLLBARS_VERTICAL_ONLY);

    label3 = new JLabel(" Rotor da Direita: ");
    rOne = new Choice();
    rOne.add("RotorI");
    rOne.add("RotorII");
    rOne.add("RotorIII");
    rOne.add("RotorIV");
    rOne.add("RotorV");
    rOne.add("RotorVI");
    rOne.add("RotorVII");
    rOne.add("RotorVIII");
    
    label4 = new JLabel(" Rotor do Meio: ");
    rTwo = new Choice();
    rTwo.add("RotorI");
    rTwo.add("RotorII");
    rTwo.add("RotorIII");
    rTwo.add("RotorIV");
    rTwo.add("RotorV");
    rTwo.add("RotorVI");
    rTwo.add("RotorVII");
    rTwo.add("RotorVIII");
    
    label5 = new JLabel(" Rotor da Esquerda: ");
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
    
    label6 = new JLabel(" Escolha Refletor: ");
    refl = new Choice();
    refl.add("RefletorB");
    refl.add("RefletorC");
    refl.add("Sem Refletor");
    
    group = new ButtonGroup();
    encryptRB = new JRadioButton("Criptografar/Descriptografar");
    group.add(encryptRB);
  	encryptRB.setSelected(true);
    encrypt = new JButton("Ok");
    encrypt.setPreferredSize(new Dimension(25,30));

    panel = new Box(BoxLayout.Y_AXIS);
	panel.add(encryptRB);
	

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
	

    panel.add(encrypt);

    this.getContentPane().add(panel);

    encrypt.addActionListener(this);
    encryptRB.addActionListener(this);
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
  	}
  	
  	//if the button is pressed...
  	if(e.getSource().equals(encrypt)){
  		//...and the encrypt button is selected
   		if(encryptRB.isSelected()){
   		
   		//then create a new Engima with the current settings  		
      		enigma = new Enigma(rOne.getSelectedItem(), rTwo.getSelectedItem(),
                          rThree.getSelectedItem(), refl.getSelectedItem());                 
      		enigma.configuracaoInicial(initOne.getSelectedItem(), initTwo.getSelectedItem(),
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
  	}
  	}
  }
  
