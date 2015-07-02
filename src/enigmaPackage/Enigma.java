package enigmaPackage;

import java.util.*;

/**
 * <p>Título: Enigma</p>
 * <p>Descrição: Simulação da máquina militar alemã engima.
 *   A especificação dos rotores e dos refletores foi obtida em
 *   http://www.codesandciphers.org.uk/enigma/rotorspec.htm e
 *   http://homepages.tesco.net/~andycarlson/enigma/simulating_enigma.html</p>
 */
public class Enigma
{

  //ROTORES STATIC
  public final static StringBuffer rotorI      = new StringBuffer("EKMFLGDQVZNTOWYHXUSPAIBRCJ");
  public final static StringBuffer rotorII     = new StringBuffer("AJDKSIRUXBLHWTMCQGZNPYFVOE");
  public final static StringBuffer rotorIII    = new StringBuffer("BDFHJLCPRTXVZNYEIWGAKMUSQO");
  public final static StringBuffer rotorIV     = new StringBuffer("ESOVPZJAYQUIRHXLNFTGKDCMWB");
  public final static StringBuffer rotorV      = new StringBuffer("VZBRGITYUPSDNHLXAWMJQOFECK");
  public final static StringBuffer rotorVI     = new StringBuffer("JPGVOUMFYQBENHZRDKASXLICTW");
  public final static StringBuffer rotorVII    = new StringBuffer("NZJHGRCXMYSWBOUFAIVLPEKQDT");
  public final static StringBuffer rotorVIII   = new StringBuffer("JPGVOUMFYQBENHZRDKASXLICTW");

  //REFLETORES STATIC
  public static final StringBuffer refletorB  = new StringBuffer("YRUHQSLDPXNGOKMIEBFZCWVJAT");
  public static final StringBuffer refletorC  = new StringBuffer("FVPJIAOYEDRZXWGCTKUQSBNMHL");
  public static final StringBuffer refletor0  = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

  //ROTORES E REFLETORES EM USO
  public StringBuffer primeiroRotor;
  public StringBuffer primeiroRotorT = new StringBuffer(refletor0.toString());
  public StringBuffer segundoRotor;
  public StringBuffer segundoRotorT = new StringBuffer(refletor0.toString());
  public StringBuffer terceiroRotor;
  public StringBuffer terceiroRotorT = new StringBuffer(refletor0.toString());
  public StringBuffer refletor;

  //CONFIGURAÇÃO PLUGBOARD ATUAL
  public char[] plugBoard = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

  //NOTHES STATIC  - QUANDO CADA ROTOR GIRA
  public final static StringBuffer[] notches = {new StringBuffer("Q"),new StringBuffer("E"),new StringBuffer("V"),new StringBuffer("J"),
  	new StringBuffer("Z"),new StringBuffer("Z"),new StringBuffer("Z"),new StringBuffer("Z")};
  
  //NOTCHES ATUAIS DO ROTOR
  private StringBuffer n1;
  private StringBuffer n2;
  
   /**
   * Class Construtor
   *@param r1 rotor será usado como primeiro rotor
   *@param r2 rotor será usado como segundo rotor
   *@param r3 rotor será usado como terceiro rotor
   *@param r refletor que será usado
   */
  public Enigma(String r1, String r2, String r3, String r)
  {
    primeiroRotor = getValor(r1)[0];
    n1 = getValor(r1)[1];
    segundoRotor = getValor(r2)[0];
    n2 = getValor(r2)[1];
    terceiroRotor = getValor(r3)[0];
    refletor = getValor(r)[0];
  }
  
  /**
   *Seta o primeiro Rotor
   *@param r1 rotor será usado como primeiro rotor
   *@return void
   */
  public void setPrimeiroRotor(String r1){
  	primeiroRotor = getValor(r1)[0];
    n1 = getValor(r1)[1];
  }

 /**
   *Seta o segundo Rotor
   *@param r2 rotor será usado como segundo rotor
   *@return void
   */
  public void setSegundoRotor(String r2){
  	segundoRotor = getValor(r2)[0];
    n2 = getValor(r2)[1];
  }
  
  /**
   *Seta o terceiro Rotor
   *@param r3 rotor será usado como terceiro rotor
   *@return void
   */ 
  public void setTerceiroRotor(String r3){
  	terceiroRotor = getValor(r3)[0];
  }
  
  /**
   *Seta a configuração inicial dos rotores
   *@param s1 configuração inicial para o primeiro rotor
   *@param s2 configuração inicial para o segundo rotor
   *@param s3 configuração inicial para o terceiro rotor
   *@return void
   */
  public void configuracaoInicial(String s1, String s2, String s3){
    int p;

	//Primeiro Rotor
	p = primeiroRotorT.toString().indexOf(s1);
	primeiroRotorT.append(primeiroRotorT.substring(0,p));
	primeiroRotorT.delete(0,p);
    
    //Segundo Rotor
	p = segundoRotorT.toString().indexOf(s2);
	segundoRotorT.append(segundoRotorT.substring(0,p));
	segundoRotorT.delete(0,p);
    
    //Terceiro Rotor
	p = terceiroRotorT.toString().indexOf(s3);
	terceiroRotorT.append(terceiroRotorT.substring(0,p));
	terceiroRotorT.delete(0,p);
  }

  /**
   *Cria uma conexão no plugboard entre duas letras 
   *@param x primeira letra a ser conectada
   *@param y segunda letra a ser conectada
   *@return void
   */
  public void setPlugBoard(char x, char y){
    for(int i=0; i<plugBoard.length; i++){
      if(plugBoard[i] == x)
        plugBoard[i] = y;
      else if(plugBoard[i] == y)
        plugBoard[i] = x;
    }
  }

  /**
   *Seta a configuração do plugboard
   *@param str configuração do plugboard formatado em pares, 
   *cada par separado por um espaçõ
   *@return boolean se str recebido no formato errado
   *e se o plugboard foi setado de acordo
   */
  public boolean setPlugBoard(String str){
    String s;
    StringTokenizer tokenCheck = new StringTokenizer(str, " ");
    while(tokenCheck.hasMoreTokens()){
      s = tokenCheck.nextToken();
      if (s.length() != 2)
        return false;
      if(s.charAt(0)>90 || s.charAt(0)<65 || s.charAt(1)>90 || s.charAt(1)<65)
        return false;
    }

    StringTokenizer token = new StringTokenizer(str, " ");
    while(token.hasMoreTokens()){
      s = token.nextToken();
      if(s.length()==2)
        setPlugBoard(s.charAt(0), s.charAt(1));
      else
        return false;
    }
    return true;
  }

  /**
   *Returna o valor do Rotor especificado.
   *@param v nome ou número do rotor
   *@return StringBuffer[] rotor correto
   */
  public StringBuffer[] getValor(String v){
  	StringBuffer[] result = new StringBuffer[2];
    if (v.equals("RotorI") || v.equals("1")){
    	result[0] = rotorI;
    	result[1] = notches[0];
    	return result;
    	} 
    if (v.equals("RotorII")|| v.equals("2")){
    	result[0] = rotorII;
    	result[1] = notches[1];
    	return result;
    	} 
    if (v.equals("RotorIII")|| v.equals("3")){
    	result[0] = rotorIII;
    	result[1] = notches[2];
    	return result;
    	} 
    if (v.equals("RotorIV")|| v.equals("4")){
    	result[0] = rotorIV;
    	result[1] = notches[3];
    	return result;
    	} 
    if (v.equals("RotorV")|| v.equals("5")){
    	result[0] = rotorV;
    	result[1] = notches[4];
    	return result;
   		 } 
    if (v.equals("RotorVI")|| v.equals("6")){
    	result[0] = rotorVI;
    	result[1] = notches[5];
    	return result;
    	} 
    if (v.equals("RotorVII")|| v.equals("7")){
    	result[0] = rotorVII;
    	result[1] = notches[6];
    	return result;
    }
    if (v.equals("RotorVIII")|| v.equals("8")){
    	result[0] = rotorVIII;
    	result[1] = notches[7];
    	return result;
    }
    if (v.equals("ReflectorB")){
    	result[0] = refletorB;
    	result[1] = new StringBuffer("");
    	return result;
    } 
    if (v.equals("ReflectorC")){
    	result[0] = refletorC;
    	result[1] = new StringBuffer("");
    	return result;
    } 
    if (v.equals("No Reflector")){
    	result[0] = refletor0;
    	result[1] = new StringBuffer("");
    	return result;
    } 
    return new StringBuffer[]{new StringBuffer("ERROR"), new StringBuffer("")};
  }

  /**
   *Retorna o carctere obtido após passar l através
   *do primeiro rotor atual
   *@param l caractere de entrada
   *@return char obtido após passar l através do primeiro rotor atual
   */
  public char rotorUm(char l){
    int posicao = primeiroRotorT.toString().indexOf(l);
    return primeiroRotor.charAt(posicao);
    
  }

  /**
   *Retorna o carctere obtido após passar l através
   *do segundo rotor atual
   *@param l caractere de entrada
   *@return char obtido após passar l através do segundo rotor atual
   */
  public char rotorDois(char l){
    int posicao = segundoRotorT.toString().indexOf(l);
    return segundoRotor.charAt(posicao);
  }

  /**
   *Retorna o carctere obtido após passar l através
   *do terceiro rotor atual
   *@param l caractere de entrada
   *@return char obtido após passar l através do terceiro rotor atual
   */
  public char rotorTres(char l){
    int posicao = terceiroRotorT.toString().indexOf(l);
    return terceiroRotor.charAt(posicao);
  }

  /**
   *Retorna o carctere obtido após passar l através
   *do refletor atual
   *@param l caractere de entrada
   *@return char obtido após passar l através do refletor atual
   */
  public char refletor(char l){
    int posicao = (int)l - 65;
    l = refletor.charAt(posicao);
    return l;
  }

  /**
   *Returns the character obtained after passing l through
   *the current first rotor in the reverse direction
   *@param l character input
   *@return char obtained after passing l through the current
   *first rotor in the reverse direction
   */
  public char IrotorOne(char l){
    int position = primeiroRotor.toString().indexOf(l);
    return primeiroRotorT.charAt(position);
  }

  /**
   *Returns the character obtained after passing l through
   * the current second rotor in the reverse direction
   *@param l character input
   *@return char obtained after passing l through the current
   *second rotor in the reverse direction
   */
  public char IrotorTwo(char l){
    int position = segundoRotor.toString().indexOf(l);
    return segundoRotorT.charAt(position);
  }

  /**
   *Returns the character obtained after passing l through
   *the current third rotor in the reverse direction
   *@param l character input
   *@return char obtained after passing l through the current
   *third rotor in the reverse direction
   */
  public char IrotorThree(char l){
    int position = terceiroRotor.toString().indexOf(l);
    return terceiroRotorT.charAt(position);
  }

  /**
   * Rotates the rotors according to their current settings
   *@param void
   *@return void
   */
   public void rotate(){
   	StringBuffer currentR1 = new StringBuffer(primeiroRotorT.charAt(0)+"");
   	StringBuffer currentR2 = new StringBuffer(segundoRotorT.charAt(0)+"");
   
   	//Rotate first rotor
    primeiroRotorT.append(primeiroRotorT.charAt(0));
    primeiroRotorT.delete(0, 1);

    //if first rotor is at notch
    if (currentR1.toString().equals(n1.toString())){
    	//then also rotate second rotor
     	segundoRotorT.append(segundoRotorT.charAt(0));
     	segundoRotorT.delete(0, 1);
     	
     	//if second rotor is at notch
     	if(currentR2.toString().equals(n2.toString())){
     		//then also rotate the third rotor
     		terceiroRotorT.append(terceiroRotorT.charAt(0));
     		terceiroRotorT.delete(0, 1);
     		}
     	}
   	}

  /** 
   *Returns the result of passing c through
   *the plugboard with its current settings
   *@param c the inputted character
   *@return char the result of passing c through
   *the plugboard with its current settings
   */	
   public char plugBoard(char c){
     int i = (int)(c) - 65;
     return plugBoard[i];
   }

  /** 
   *Returns the current setting of the first rotor.
   *@param void
   *@return char that is the current setting of the first rotor
   */
   public char getFRSetting(){
     return primeiroRotorT.charAt(0);
   }

  /** 
   *Returns the current setting of the second rotor.
   *@param void
   *@return char that is the current setting of the second rotor
   */
   public char getSRSetting(){
     return segundoRotorT.charAt(0);
   }

  /** 
   *Returns the current setting of the third rotor.
   *@param void
   *@return char that is the current setting of the third rotor
   */
   public char getTRSetting(){
     return terceiroRotorT.charAt(0);
   }


  /** 
   *Encrypts/Decrypts the inputted string using the 
   *machine's current settings
   *@param p the text to be encrypted/decrypted
   *@return void
   */
  public String encrypt(String p){
     p = p.toUpperCase();
     String e = "";
     char c;
     
     //for the length of the inputted text
     for(int i=0; i<p.length(); i++){
       //store the current character	
       c = p.charAt(i);
       
       //if the current character is a letter
       if (c<=90 && c>=65){
       	   //rotate the rotors
           rotate();
           
           //pass the character through the plugboard
           c = plugBoard(c);
           
           //then through the first rotor
           c = rotorUm(c);
           
           //then through the second rotor
           c = rotorDois(c);
           
           //then through the third rotor
           c = rotorTres(c);
           
           //then through the reflector
           c = refletor(c);
           
           //then through the first rotor in the reverse direction
           c = IrotorThree(c);
           
           //then through the second rotor in the reverse direction
           c = IrotorTwo(c);
           
           //then through the third rotor in the reverse direction
           c = IrotorOne(c);
           
           //and finally back through the plugboard
           c = plugBoard(c);
           
           //and add the new character to the encrypted/decrypted message
           e = e + c;
       }
       
       //if c is a space simply add it to the encrypted/decrypted message
       //to conserve spaces
       else if(c==32)
           e = e + c;
           
       //if c is neither a space nor a letter, then there is an error    
       else
         return null;
     }
     
     	//return the complete encrypted/decrpyted message
        return e;
  }

  /**
   *Parses Plugboard input to check for repeated letters
   *as each letter can only be used once in the plugboard 
   *@param str the inputted plug board settings
   *@return void
   */
  public boolean pbParser(String str){
  	//if no plug board settings were input, then continue
    if(str.length()<=0 || str.equals(null) || str == null){
      return true;
    }
    
    //otherwise, check to make sure letters are not repeated
    for(int i=0; i<str.length()-1; i++){
      //if not a letter, continue	
      if(str.charAt(i)>90 || str.charAt(i)<65)
        i++;
      //if the current letter appears in the rest of the string
      else if(str.substring(i+1).indexOf(str.charAt(i)) != -1)
        return false;
    }
    
    //otherwise, return true
    return true;
  }
  

  }