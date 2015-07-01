package enigmaPackage;

import java.util.*;

/**
 * <p>Title: Enigma</p>
 * <p>Description: A Simulation of the German Military Enigma Machine.
 *   Specifications of rotors and reflectors obtained from
 *   http://www.codesandciphers.org.uk/enigma/rotorspec.htm and
 *   http://homepages.tesco.net/~andycarlson/enigma/simulating_enigma.html</p>
 */
public class Enigma
{

  //STATIC ROTORS
  public final static StringBuffer rotorI      = new StringBuffer("EKMFLGDQVZNTOWYHXUSPAIBRCJ");
  public final static StringBuffer rotorII     = new StringBuffer("AJDKSIRUXBLHWTMCQGZNPYFVOE");
  public final static StringBuffer rotorIII    = new StringBuffer("BDFHJLCPRTXVZNYEIWGAKMUSQO");
  public final static StringBuffer rotorIV     = new StringBuffer("ESOVPZJAYQUIRHXLNFTGKDCMWB");
  public final static StringBuffer rotorV      = new StringBuffer("VZBRGITYUPSDNHLXAWMJQOFECK");
  public final static StringBuffer rotorVI     = new StringBuffer("JPGVOUMFYQBENHZRDKASXLICTW");
  public final static StringBuffer rotorVII    = new StringBuffer("NZJHGRCXMYSWBOUFAIVLPEKQDT");
  public final static StringBuffer rotorVIII   = new StringBuffer("JPGVOUMFYQBENHZRDKASXLICTW");

  //STATIC REFLECTORS
  public static final StringBuffer reflectorB  = new StringBuffer("YRUHQSLDPXNGOKMIEBFZCWVJAT");
  public static final StringBuffer reflectorC  = new StringBuffer("FVPJIAOYEDRZXWGCTKUQSBNMHL");
  public static final StringBuffer reflector0  = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

  //CURRENT ROTORS AND REFLECTOR IN USE
  public StringBuffer firstRotor;
  public StringBuffer firstRotorT = new StringBuffer(reflector0.toString());
  public StringBuffer secondRotor;
  public StringBuffer secondRotorT = new StringBuffer(reflector0.toString());
  public StringBuffer thirdRotor;
  public StringBuffer thirdRotorT = new StringBuffer(reflector0.toString());
  public StringBuffer reflector;

  //CURRENT PLUGBOARD SETTINGS
  public char[] plugBoard = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

  //STATIC "NOTCHES"  - when each rotor rotates
  public final static StringBuffer[] notches = {new StringBuffer("Q"),new StringBuffer("E"),new StringBuffer("V"),new StringBuffer("J"),
  	new StringBuffer("Z"),new StringBuffer("Z"),new StringBuffer("Z"),new StringBuffer("Z")};
  
  //CURRENT ROTOR NOTCHES
  private StringBuffer n1;
  private StringBuffer n2;
  
   /**
   * Class Constructor
   *@param r1 rotor to be used as first rotor
   *@param r2 rotor to be used as second rotor
   *@param r3 rotor to be used as third rotor
   *@param r reflector to be used
   */
  public Enigma(String r1, String r2, String r3, String r)
  {
    firstRotor = getValue(r1)[0];
    n1 = getValue(r1)[1];
    secondRotor = getValue(r2)[0];
    n2 = getValue(r2)[1];
    thirdRotor = getValue(r3)[0];
    reflector = getValue(r)[0];
  }
  
  /**
   *Sets the first Rotor
   *@param r1 rotor to be used as first rotor
   *@return void
   */
  public void setFirstRotor(String r1){
  	firstRotor = getValue(r1)[0];
    n1 = getValue(r1)[1];
  }

 /**
   *Sets the second Rotor
   *@param r2 rotor to be used as second rotor
   *@return void
   */
  public void setSecondRotor(String r2){
  	secondRotor = getValue(r2)[0];
    n2 = getValue(r2)[1];
  }
  
  /**
   *Sets the second Rotor
   *@param r3 rotor to be used as third rotor
   *@return void
   */ 
  public void setThirdRotor(String r3){
  	thirdRotor = getValue(r3)[0];
  }
  
  /**
   *Sets the intial settings of the rotors.
   *@param s1 initial setting for first rotor
   *@param s2 initial setting for second rotor
   *@param s3 initial setting for third rotor
   *@return void
   */
  public void initialSettings(String s1, String s2, String s3){
    int p;

	//First Rotor
	p = firstRotorT.toString().indexOf(s1);
	firstRotorT.append(firstRotorT.substring(0,p));
	firstRotorT.delete(0,p);
    
    //Second Rotor
	p = secondRotorT.toString().indexOf(s2);
	secondRotorT.append(secondRotorT.substring(0,p));
	secondRotorT.delete(0,p);
    
    //Third Rotor
	p = thirdRotorT.toString().indexOf(s3);
	thirdRotorT.append(thirdRotorT.substring(0,p));
	thirdRotorT.delete(0,p);
  }

  /**
   *Creates a plubboard connection between two letters 
   *@param x first character to be connected
   *@param y second character to be connected
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
   *Sets the plug board settings
   *@param str plug board settings formatted in pairs, 
   *each pair seperated by a space
   *@return boolean if str entered was in correct format
   *and if the plugboard was set accordingly
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
   *Returns the value of the specified Rotor.
   *@param v name or number of rotor
   *@return StringBuffer[] correct rotor
   */
  public StringBuffer[] getValue(String v){
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
    	result[0] = reflectorB;
    	result[1] = new StringBuffer("");
    	return result;
    } 
    if (v.equals("ReflectorC")){
    	result[0] = reflectorC;
    	result[1] = new StringBuffer("");
    	return result;
    } 
    if (v.equals("No Reflector")){
    	result[0] = reflector0;
    	result[1] = new StringBuffer("");
    	return result;
    } 
    return new StringBuffer[]{new StringBuffer("ERROR"), new StringBuffer("")};
  }

  /**
   *Returns the character obtained after passing l through
   *the current first rotor
   *@param l character input
   *@return char obtained after passing l through the current first rotor
   */
  public char rotorOne(char l){
    int position = firstRotorT.toString().indexOf(l);
    return firstRotor.charAt(position);
    
  }

  /**
   *Returns the character obtained after passing l through
   *the current second rotor
   *@param l character input
   *@return char obtained after passing l through the current second rotor
   */
  public char rotorTwo(char l){
    int position = secondRotorT.toString().indexOf(l);
    return secondRotor.charAt(position);
  }

  /**
   *Returns the character obtained after passing l through
   *the current third rotor
   *@param l character input
   *@return char obtained after passing l through the current third rotor
   */
  public char rotorThree(char l){
    int position = thirdRotorT.toString().indexOf(l);
    return thirdRotor.charAt(position);
  }

  /**
   *Returns the character obtained after passing l through
   *the current reflector
   *@param l character input
   *@return char obtained after passing l through the current reflector
   */
  public char reflector(char l){
    int position = (int)l - 65;
    l = reflector.charAt(position);
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
    int position = firstRotor.toString().indexOf(l);
    return firstRotorT.charAt(position);
  }

  /**
   *Returns the character obtained after passing l through
   * the current second rotor in the reverse direction
   *@param l character input
   *@return char obtained after passing l through the current
   *second rotor in the reverse direction
   */
  public char IrotorTwo(char l){
    int position = secondRotor.toString().indexOf(l);
    return secondRotorT.charAt(position);
  }

  /**
   *Returns the character obtained after passing l through
   *the current third rotor in the reverse direction
   *@param l character input
   *@return char obtained after passing l through the current
   *third rotor in the reverse direction
   */
  public char IrotorThree(char l){
    int position = thirdRotor.toString().indexOf(l);
    return thirdRotorT.charAt(position);
  }

  /**
   * Rotates the rotors according to their current settings
   *@param void
   *@return void
   */
   public void rotate(){
   	StringBuffer currentR1 = new StringBuffer(firstRotorT.charAt(0)+"");
   	StringBuffer currentR2 = new StringBuffer(secondRotorT.charAt(0)+"");
   
   	//Rotate first rotor
    firstRotorT.append(firstRotorT.charAt(0));
    firstRotorT.delete(0, 1);

    //if first rotor is at notch
    if (currentR1.toString().equals(n1.toString())){
    	//then also rotate second rotor
     	secondRotorT.append(secondRotorT.charAt(0));
     	secondRotorT.delete(0, 1);
     	
     	//if second rotor is at notch
     	if(currentR2.toString().equals(n2.toString())){
     		//then also rotate the third rotor
     		thirdRotorT.append(thirdRotorT.charAt(0));
     		thirdRotorT.delete(0, 1);
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
     return firstRotorT.charAt(0);
   }

  /** 
   *Returns the current setting of the second rotor.
   *@param void
   *@return char that is the current setting of the second rotor
   */
   public char getSRSetting(){
     return secondRotorT.charAt(0);
   }

  /** 
   *Returns the current setting of the third rotor.
   *@param void
   *@return char that is the current setting of the third rotor
   */
   public char getTRSetting(){
     return thirdRotorT.charAt(0);
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
           c = rotorOne(c);
           
           //then through the second rotor
           c = rotorTwo(c);
           
           //then through the third rotor
           c = rotorThree(c);
           
           //then through the reflector
           c = reflector(c);
           
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