package enigmaPackage;
 
/**
 * <p>Title: IC (Index of Coincidence)</p>
 * <p>Description: Calculates the Index of Coincidence of a string.
 * The index of coincidence is defined to be the sum[fi(fi-1)]/N(N-1)
 * where fi is the frequency of the ith letter of the alphabet and N 
 * is the total number of characters in the sample.</p>
 * @author Meghan Emilio
 * @version 1.0
 */

public class IC {
    
  /**
   *Class Constructor
   *@param void
   *@return void
   */
    public IC(){
    	
    }
    
  /**
   *returns the IC of the input string
   *@param s the text for which the IC will be calculated
   *@return double the IC of the inputted text
   */
    public double calculate(String s){
    	
    	int i;
    	int N = 0;
    	double sum = 0.0;
    	double total = 0.0;
    	s = s.toUpperCase();
    	
    	//initialize array of values to count frequency of each letter
    	int[] values = new int[26];
    	for(i=0; i<26; i++){
    		values[i] = 0;
    	}
    	
    	//calculate frequency of each letter in s
    	int ch;
    	for(i=0; i<s.length(); i++){
    		ch = s.charAt(i)-65;
    		if(ch>=0 && ch<26){
    			values[ch]++;
    			N++;
    			}	
    	}
    	
    	//calculate the sum of each frequency
    	for(i=0; i<26; i++){
    		ch = values[i];
    		sum = sum + (ch * (ch-1));
    		}
    	
    	//divide by N(N-1)	
    	total = sum/(N*(N-1));
    	
    	//return the result
    	return total;
    	
    }
    
  /**
   *used for testing purposes only
   *@param void
   *@return void
   */
    public static void main(String[] args) {
      IC test = new IC();
      String testString="testString";
      System.out.println(test.calculate(testString));
      }
}
