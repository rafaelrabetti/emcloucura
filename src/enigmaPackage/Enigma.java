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

  //ENTALHES STATIC  - QUANDO CADA ROTOR GIRA
  public final static StringBuffer[] entalhes = {new StringBuffer("Q"),new StringBuffer("E"),new StringBuffer("V"),new StringBuffer("J"),
  	new StringBuffer("Z"),new StringBuffer("Z"),new StringBuffer("Z"),new StringBuffer("Z")};
  
  //ENTALHES ATUAIS DO ROTOR
  private StringBuffer entalhe1;
  private StringBuffer entalhe2;
  
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
    entalhe1 = getValor(r1)[1];
    segundoRotor = getValor(r2)[0];
    entalhe2 = getValor(r2)[1];
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
    entalhe1 = getValor(r1)[1];
  }

 /**
   *Seta o segundo Rotor
   *@param r2 rotor será usado como segundo rotor
   *@return void
   */
  public void setSegundoRotor(String r2){
  	segundoRotor = getValor(r2)[0];
    entalhe2 = getValor(r2)[1];
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
    	result[1] = entalhes[0];
    	return result;
    	} 
    if (v.equals("RotorII")|| v.equals("2")){
    	result[0] = rotorII;
    	result[1] = entalhes[1];
    	return result;
    	} 
    if (v.equals("RotorIII")|| v.equals("3")){
    	result[0] = rotorIII;
    	result[1] = entalhes[2];
    	return result;
    	} 
    if (v.equals("RotorIV")|| v.equals("4")){
    	result[0] = rotorIV;
    	result[1] = entalhes[3];
    	return result;
    	} 
    if (v.equals("RotorV")|| v.equals("5")){
    	result[0] = rotorV;
    	result[1] = entalhes[4];
    	return result;
   		 } 
    if (v.equals("RotorVI")|| v.equals("6")){
    	result[0] = rotorVI;
    	result[1] = entalhes[5];
    	return result;
    	} 
    if (v.equals("RotorVII")|| v.equals("7")){
    	result[0] = rotorVII;
    	result[1] = entalhes[6];
    	return result;
    }
    if (v.equals("RotorVIII")|| v.equals("8")){
    	result[0] = rotorVIII;
    	result[1] = entalhes[7];
    	return result;
    }
    if (v.equals("RefletorB")){
    	result[0] = refletorB;
    	result[1] = new StringBuffer("");
    	return result;
    } 
    if (v.equals("RefletorC")){
    	result[0] = refletorC;
    	result[1] = new StringBuffer("");
    	return result;
    } 
    if (v.equals("Sem Refletor")){
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
   *Retorna o carctere obtido após passar l através
   *do primeiro rotor atual na direção inversa
   *@param l caractere de entrada
   *@return char obtido após passar l através do primeiro rotor atual na direção inversa
   */
  public char primeiroRotorI(char l){
    int posicao = primeiroRotor.toString().indexOf(l);
    return primeiroRotorT.charAt(posicao);
  }

  /**
   *Retorna o carctere obtido após passar l através
   *do segundo rotor atual na direção inversa
   *@param l caractere de entrada
   *@return char obtido após passar l através do segundo rotor atual na direção inversa
   */
  public char segundoRotorI(char l){
    int posicao = segundoRotor.toString().indexOf(l);
    return segundoRotorT.charAt(posicao);
  }

  /**
   *Retorna o carctere obtido após passar l através
   *do terceiro rotor atual na direção inversa
   *@param l caractere de entrada
   *@return char obtido após passar l através do terceiro rotor atual na direção inversa
   */
  public char terceiroRotorI(char l){
    int posicao = terceiroRotor.toString().indexOf(l);
    return terceiroRotorT.charAt(posicao);
  }

  /**
   * Gira os rotores de acordo com as configurações atuais
   *@param void
   *@return void
   */
   public void gira(){
   	StringBuffer atualR1 = new StringBuffer(primeiroRotorT.charAt(0)+"");
   	StringBuffer atualR2 = new StringBuffer(segundoRotorT.charAt(0)+"");
   
   	//Gira o primeiro rotor
    primeiroRotorT.append(primeiroRotorT.charAt(0));
    primeiroRotorT.delete(0, 1);

    //se o primeiro rotor é um entalhe
    if (atualR1.toString().equals(entalhe1.toString())){
    	//então também gira o segundo rotor
     	segundoRotorT.append(segundoRotorT.charAt(0));
     	segundoRotorT.delete(0, 1);
     	
     	//se o segundo rotor é um entalhe
     	if(atualR2.toString().equals(entalhe2.toString())){
     		//então tambem gira o terceiro rotor
     		terceiroRotorT.append(terceiroRotorT.charAt(0));
     		terceiroRotorT.delete(0, 1);
     		}
     	}
   	}

  /** 
   *Retorna o resultado de passar c através do plugboard com
   *sua configuração atual
   *@param c caracter de entrada
   *@return char resultado de passar c através do plugboard com
   *sua configuração atual
   */	
   public char plugBoard(char c){
     int i = (int)(c) - 65;
     return plugBoard[i];
   }

  /** 
   *Retorna a configuração atual do primeiro rotor
   *@param void
   *@return char que é a configuração atual do primeiro rotor
   */
   public char getConfiguracaoPrimeiroRotor(){
     return primeiroRotorT.charAt(0);
   }

   /** 
    *Retorna a configuração atual do segundo rotor
    *@param void
    *@return char que é a configuração atual do segundo rotor
    */
   public char getConfiguracaoSegundoRotor(){
     return segundoRotorT.charAt(0);
   }

   /** 
    *Retorna a configuração atual do terceiro rotor
    *@param void
    *@return char que é a configuração atual do terceiro rotor
    */
   public char getConfiguracaoTerceiroRotor(){
     return terceiroRotorT.charAt(0);
   }


  /** 
   *Criptografa/descriptografa a string de entrada usando
   *a configuração atual da maquina
   *@param p texto a ser criptografado/descriptografado
   *@return void
   */
  public String criptografa(String p){
     p = p.toUpperCase();
     String e = "";
     char c;
     
     //para o tamanho do texto de entrada
     for(int i=0; i<p.length(); i++){
       //guarda o caracter atual
       c = p.charAt(i);
       
       //se o caracter atual é uma letra
       if (c<=90 && c>=65){
       	   //gira os rotores
           gira();
           
           //passa o caracter pelo plugboard
           c = plugBoard(c);
           
           //depois pelo primeiro rotor
           c = rotorUm(c);
           
           //depois pelo segundo rotor
           c = rotorDois(c);
           
           //depois pelo terceiro rotor
           c = rotorTres(c);
           
           //depois pelo refletor
           c = refletor(c);
           
           //depois pelo primeiro rotor na direção inversa
           c = terceiroRotorI(c);
           
           //depois pelo segundo rotor na direção inversa
           c = segundoRotorI(c);
           
           //depois pelo terceiro rotor na direção inversa
           c = primeiroRotorI(c);
           
           //e finalmente de volta ao plugboard
           c = plugBoard(c);
           
           //entao adiciona o novo caracter à mensagem criptografada/descriptografada
           e = e + c;
       }
       
       //se c é um espaço, simplesmente o adiciona à mensagem criptografada/descriptografada
       //para conservar espaços
       else if(c==32)
           e = e + c;
           
       //se c não é nem um espaço nem uma letra, entao temos um erro
       else
         return null;
     }
     
     	//retorna a mensagem criptografada/descriptografada completa
        return e;
  }

  /**
   *Analisa a entrada do plugboard pra verificar se tem letras repetidas
   *já que cada letra só pode ser usada uma vez no plugboard
   *@param str a entrada de configuração do plugboard
   *@return void
   */
  public boolean analisaPlugboard(String str){
	//se não há configurações no plugboard, entao continua
    if(str.length()<=0 || str.equals(null) || str == null){
      return true;
    }
    
    //se tiver, verificar se tem letras repetidas
    for(int i=0; i<str.length()-1; i++){
      //se não a letra, continua	
      if(str.charAt(i)>90 || str.charAt(i)<65)
        i++;
      //se a letra aparece no resto da string
      else if(str.substring(i+1).indexOf(str.charAt(i)) != -1)
        return false;
    }
    
    //caso contrário, retorna verdadeiro
    return true;
  }
  

  }