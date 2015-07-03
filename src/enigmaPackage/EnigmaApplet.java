package enigmaPackage;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;

/**
 * <p>Title: Enigma Applet</p>
 * <p>Description: Interface de usuário da Enigma Applet</p>
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
  private Choice rotorUm;
  private Choice rotorDois;
  private Choice rotorTres;
  private Choice refl;
  private Choice iniciaUm;
  private Choice iniciaDois;
  private Choice iniciaTres;
  private ButtonGroup group;
  private JRadioButton criptografaRB;
  private JButton criptografa;
  private Enigma enigma;

 /**
  *Construtor da classe
  *@param void
  *@return void
  */
  public EnigmaApplet() {
  }
  
  /**
   *Inicializa o applet
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
   *Inicializa o componente
   *@param void
   *@return void
   */
  private void jbInit() throws Exception {
	
	//Inicializa cada componente do applet
	
    label1 = new JLabel(" Texto: ");
    outputField = new TextArea("", 5, 15, TextArea.SCROLLBARS_VERTICAL_ONLY);
    outputField.setEditable(false);
    outputField.setEnabled(true);
    
    label2 = new JLabel(" Resultado: ");
    inputField = new TextArea("", 5, 15, TextArea.SCROLLBARS_VERTICAL_ONLY);
    
    label7 = new JLabel(" Configuração Plugboard: ");
    plugBoard = new TextArea("", 5, 15, TextArea.SCROLLBARS_VERTICAL_ONLY);

    label3 = new JLabel(" Rotor da Direita: ");
    rotorUm = new Choice();
    rotorUm.add("RotorI");
    rotorUm.add("RotorII");
    rotorUm.add("RotorIII");
    rotorUm.add("RotorIV");
    rotorUm.add("RotorV");
    rotorUm.add("RotorVI");
    rotorUm.add("RotorVII");
    rotorUm.add("RotorVIII");
    
    label4 = new JLabel(" Rotor do Meio: ");
    rotorDois = new Choice();
    rotorDois.add("RotorI");
    rotorDois.add("RotorII");
    rotorDois.add("RotorIII");
    rotorDois.add("RotorIV");
    rotorDois.add("RotorV");
    rotorDois.add("RotorVI");
    rotorDois.add("RotorVII");
    rotorDois.add("RotorVIII");
    
    label5 = new JLabel(" Rotor da Esquerda: ");
    rotorTres = new Choice();
    rotorTres.add("RotorI");
    rotorTres.add("RotorII");
    rotorTres.add("RotorIII");
    rotorTres.add("RotorIV");
    rotorTres.add("RotorV");
    rotorTres.add("RotorVI");
    rotorTres.add("RotorVII");
    rotorTres.add("RotorVIII");
    
    iniciaUm = preencheEscolha(new Choice());
    iniciaDois = preencheEscolha(new Choice());
    iniciaTres = preencheEscolha(new Choice());
    
    label6 = new JLabel(" Escolha Refletor: ");
    refl = new Choice();
    refl.add("RefletorB");
    refl.add("RefletorC");
    refl.add("Sem Refletor");
    
    group = new ButtonGroup();
    criptografaRB = new JRadioButton("Criptografar/Descriptografar");
    group.add(criptografaRB);
  	criptografaRB.setSelected(true);
    criptografa = new JButton("Ok");
    criptografa.setPreferredSize(new Dimension(25,30));

    panel = new Box(BoxLayout.Y_AXIS);
	panel.add(criptografaRB);
	

    panel1 = new JPanel(new GridLayout(2,2,1,3));
    panel1.add(label1);
    panel1.add(inputField);
    panel1.add(label2);
    panel1.add(outputField);
    panel.add(panel1);

    panel2 = new JPanel();
    panel2.setLayout(new GridLayout(4,3,1,3));
    panel2.add(label3);
    panel2.add(rotorUm);
    panel2.add(iniciaUm);
    panel2.add(label4);
    panel2.add(rotorDois);
    panel2.add(iniciaDois);
    panel2.add(label5);
    panel2.add(rotorTres);
    panel2.add(iniciaTres);
    panel2.add(label6);
    panel2.add(refl);
    panel.add(panel2);
    
    panel3 = new JPanel(new GridLayout(1,2,1,3));
    panel3.add(label7);
    panel3.add(plugBoard);
    panel.add(panel3);
	

    panel.add(criptografa);

    this.getContentPane().add(panel);

    criptografa.addActionListener(this);
    criptografaRB.addActionListener(this);
  }


 /**
  *Preenche uma escolha com as letras do alfabeto em ordem alfabética
  *@param c the escolha a ser preenchida
  *@return Choice
  */
  public Choice preencheEscolha(Choice c){
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
  *Lida com as ações dos radiobuttons e button
  *@param ActionEvent e
  *@return void
  */
  public void actionPerformed(ActionEvent e) {
  	

  	
	//permite as opções de criptografia
  	if(e.getSource().equals(criptografaRB)){
  		plugBoard.setEnabled(true);
  		rotorUm.setEnabled(true);
  		rotorDois.setEnabled(true);
  		rotorTres.setEnabled(true);
  		refl.setEnabled(true);
  		iniciaUm.setEnabled(true);
  		iniciaDois.setEnabled(true);
  		iniciaTres.setEnabled(true);
  	}
  	
  	if(e.getSource().equals(criptografa)){
   		if(criptografaRB.isSelected()){
   		
   		//entao cria uma nova Enigma com as configurações atuais	
      		enigma = new Enigma(rotorUm.getSelectedItem(), rotorDois.getSelectedItem(),
                          rotorTres.getSelectedItem(), refl.getSelectedItem());                 
      		enigma.configuracaoInicial(iniciaUm.getSelectedItem(), iniciaDois.getSelectedItem(),
                             iniciaTres.getSelectedItem());

			//verifica se a configuração do plugboard esta no formato correto
      		String pb = plugBoard.getText();
      		pb.trim();
      		if (!enigma.analisaPlugboard(pb)) {
      	  		JOptionPane.showMessageDialog(this, "Erro.  Por favor, "
      	  				+ "verifique a configuração do plugboard.  "
      	  				+ "Cada letra deve aparecer apenas uma vez.");
      	  		return;
     		 }
     	 	if (!enigma.setPlugBoard(pb)) {
      	  		JOptionPane.showMessageDialog(this, "Erro.  Por favor, "
      	  				+ "verifique se a configuração do plugboard está "
      	  				+ "no formato correto.  A configuração deve ser com "
      	  				+ "pares de letras e cada par separado por um espaço.");
      	  		return;
     	 	}
			
			//criptografa/descriptografa o texto entrado
     	 	String input = inputField.getText();
     	 	if (!input.equals(null) && input.length()>0) {
       			 input = input.trim();
      	 		 String output = enigma.criptografa(input);
       	 	if(output == null || output.equals("null")){
           		JOptionPane.showMessageDialog(this, "Erro.  A mensagem de entrada"
           				+ " deve consistir de letras e espaços apenas.");
           		return;
        	}
        	
        	else{
         	 //and display the result
          		outputField.setText(output);
          		iniciaUm.select(enigma.getConfiguracaoPrimeiroRotor() + "");
          		iniciaDois.select(enigma.getConfiguracaoSegundoRotor() + "");
          		iniciaTres.select(enigma.getConfiguracaoTerceiroRotor() + "");
       		}
      	}
     }
  	}
  	}
  }
  
