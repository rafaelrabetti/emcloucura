package enigmaPackage;
import hcrypto.cipher.*;
import hcrypto.provider.*;

/**
 * <p>Title: Analyzer</p>
 * <p>Description: Algorithm for breaking a given Enigma message.  
 *    Searches through each possible decryption for the best match
 *    using Index of Coincidence (IC) as a scoring function.  Optional heuristic
 *    returns the first decryption to come close to a good IC.</p>
 * @author Meghan Emilio
 * @version 1.0
 */
 
 
public class Analyzer {
    

  /**
   *Class Constructor
   *@param void
   *@return void
   */
    public Analyzer(){
     	
     }
    
  /**
   *Analyzes the inputted string by looking at all possible decryptions and 
   *choosing the best one using the Index of Coincedence. If heuristic is used
   *the algorithm returns the first decryption to come close to a good IC, if not 
   *then the algorithm returns the string with the IC closes to 0.0667.
   *@param s the text to be decrypted
   *@param heuristic true is heuristic algorithm is to be used, false otherwise
   *@return void
   */
    public String analyze(String s, boolean heuristic){
        // Create and register the provider classes
        Provider.addProvider(new DefaultProvider("Default")); 
        Provider.addProvider(new RamProvider("Ram")); 

    	IC testIC = new IC();
    	
    	//initialize a new Engima machine
    	Enigma enigma = new Enigma("RotorI", "RotorII", "RotorIII", "ReflectorB");
      	    
    	double bestScore = 1.0;
    	double score = 0.0;
    	String one, two, three;
    	String bestSetting = "";
    	String testString = "";
    		
      //nested loops to run through each combination of 
      //three of the eight possible rotors		
	for(int r1=1; r1<9; r1++){
	    for(int r2=1; r2<9; r2++){
    		for(int r3=1; r3<9; r3++){
		    //		    enigma.setFirstRotor(r1+"");
		    //		    enigma.setSecondRotor(r2+"");
		    //		    enigma.setThirdRotor(r3+"");
	    		
		    //nested loops to run through each of the three combinations of 
		    //the 26 possible initial settings for each rotor
		    for(int i=0; i<26; i++){
			for(int j=0; j<26; j++){
			    for(int k=0; k<26; k++){
				one = (char)(i+65) + "";
				two = (char)(j+65) + "";
				three = (char)(k+65) + "";
				try {
				    Cipher cipher = Cipher.getInstance("Enigma","Default");
				    HistoricalKey key = HistoricalKey.getInstance("Enigma","Default");
				    String keyspec = (r1+"")+(r2+"")+(r3+"")+","+"AAA,"+one+two+three+",AE-BC/AZ/AZ";
				    //				    System.out.println("keyspec= " + keyspec);
				    key.init(keyspec);
				    //				enigma.initialSettings(one,two,three);
				    cipher.init(key);
				    testString = cipher.encrypt(s);
				    //decrpyt string with current settings
				    //				testString = enigma.encrypt(s);
				} catch (Exception e) {
				}
    			  			
				//calculate the decryption's IC
				//				System.out.println(testString);
				score = testIC.calculate(testString);
				//     				System.out.println(r1+" "+r2+" "+r3+" "+one+" "+two+" "+three+" IOC= " + score);
    			  				
				//if heuristic algorithm is being used
				//and the score is greater than 0.0573
				//then return the current decryption
				if(heuristic && score > 0.0573){
				    score = Math.abs(.066895 - score);
				    bestScore = score;
				    bestSetting = testString;
				    return bestSetting; 
				}			
    			  		
				//calcuate how close this IC is to the IC of English
				score = Math.abs(.066895 - score);
    			  				
				//if this score is better than the best
				//record it and continue
				if(score < bestScore){
				    bestScore = score;
				    bestSetting = testString;
				}
			    }
			}
		    }
		}
	    }
	}
	//return the best setting found
    	return bestSetting;
    }
        
    public void testIC(String text, String keyspec) {
        // Create and register the provider classes
        Provider.addProvider(new DefaultProvider("Default")); 
        Provider.addProvider(new RamProvider("Ram")); 
    	IC testIC = new IC();
    	
	try {
	    Cipher cipher = Cipher.getInstance("Enigma","Default");
	    HistoricalKey key = HistoricalKey.getInstance("Enigma","Default");
	    key.init(keyspec);
	    cipher.init(key);
	    String testString = cipher.encrypt(text);
	    //calculate the decryption's IC
	    double score = testIC.calculate(testString);
            System.out.println(keyspec + " " + score);
	} catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
	}
    }


  /**
   *Used for testing purposes only
   *@param void
   *@return void
   */
    public static void main(String[] args) {
   
	String text = // Key 123,AAA,AAA,AB-CD-EZ/AZ/AZ
	 "FK YGF GUQ RVPQ SW WGIRB, FO QMY EEU DRMNO GK ACRUJ, "
	 + "OZ RJW XKV KOV XO CJATWN, ZU ETR SGB BCL EZ GMBNXQWMDMU, "
	 + "HH QEY ESN YAERV FU JLDEKO, HZ XBN OSK AYBOG EH GOWCRKGQWMK, "
	 + "QO RJE BKU PRKVVZ FX ZSFGC, JI GGO PAO WBBCFB AG MFPJWPLV, "
	 + "ZS PJZ FCY PHUMJJ DO ISTR, UA KFX YNX AGMPRV CH BVHOVBS, "
	 + "UF CHI ATQBIDOPHX RIAVYS KE, MF LHI ZFCQRHZ MLAZVL OI, "
	 + "QD KTTN FKM BNDKW QYCPYP JE DSQEPP, MC HPMK MZX NHPCU WJTFFO "
	 + "AZX DYNHE VTX--GF UPBJE, LGF OWQHHZ BXL QP ABS NLGI UIY LUYOMBY"
	 + "DXZORV, QLJW FSQF SM DGW STFUEGGH GAQDDIPKAWZ QSKWADLQ BU LLJ"
	 + "RPCWU AQTIRHJO, RFF SDZN CH QXM XZYI, PK YDP DWWZYNEKYMP EGIBOV"
	 + "CR WDKXXEZLBU VJTR."
	 + "ZVHFR PKYS L NJBY SBQL F VGMRP RRR ZVJ D YHJZC HXFR N URBGX BGPO,"
	 + "MP HMO RBDVSK US YSNZHGW; YGFPG GZLJ U XOCL CNNU L YBTVJ PLY QTN"
	 + "S WCMTR MESO J SHEI UOMR, FJ GUH KEGXBQ GN NJXKEI.  EO FKED"
	 + "ISVBZLDBF XO QMY ORUOGFZ YTSR UQMTUPH YI FMC QSIIN TL ENU ZFRQQ"
	 + "FELIWPLDU YD DTQQAX CKQ HRJDJG, HTRG COPECL FU JNCUAWE JZHX"
	 + "GAHSEIH HBX BWOE.";

	String text2 =  // Key 123,AAA,AAA,AB-CD-EF-GH-IJ-KL-MN-OP/AZ/AZ
	    "SL YHE HEL RCOQ LH WKWBB, YP QNY ZAQ DNNMP UM ARJLI, "
	    + "EF RIW XEM LOF NB CJATXN, DU ZTR SGX BZG SE TCMYLQVMFNU, "
	    + "XG QZY ZRC RKNRX QV IBEYHI, PF XBM PEF HTUPG YS LCWCYLHRDNL, "
	    + "DP RIZ BXM ODLVIY SI EWKGC, JJ HHP OTL WVBCLL IU NEOBAIKV, "
	    + "DS OIF EUM OYURBB KU OXKF, ZA LEX YVA AYMOBV VO BLGOVPS, "
	    + "UZ BGJ FTNBJDYQDU RONWYW LZ, NZ BGJ QKCNDHM NYUCVR PJ, "
	    + "QF LRTO EKW LWNWV QXCCYO IE QVQZRG, NY GANO NWH KFMWS WJTZEP "
	    + "AJJ NYYRZ VTX--GD UYEIZ, KGZ OHQCRF BXK QP GBS GEPH UFG RUPPSFY"
	    + "HAFRIV, QAIW EKEZ GR RHW UDAUNRHG HAQOTJSLPAF VELVADJQ KQ NKI"
	    + "RTRXI APTTBGUP, MUE QECM GG VAN JFTU, NW YFG DWEVYUZLFNC ZGBBOG"
	    + "GO WAHGXZDKGE BYPR."
	    + "FYKET OYYM K YJOC SEQW E OHNOA TRR FXI D YGFHE GCEU M DUBUB CHON,"
	    + "BY GAY RVDQGU LH GEIJGLW; YGZOV HOKK U AFZI CKMR K FBTMA XKY QAM"
	    + "S WCCWZ NGSW I OGBJ JPNP, HC HYX LYHYFM DN RIXCZP.  BJ EQZO"
	    + "JBVGFKPAE YP QNY PQQPHZF YFSS UQNTUOE YR EUP GLJJM NI ZKR FERQS"
	    + "CZQJCOKXU DP NBQQMX CWQ HCIZRH, GKRH CQWRXK ZW LTISAWY IVGR"
	    + "HMGSWCG HCX RWVZ.";

	String text3 = 
	"FK ZWE HOJ ZHWX GE SRNYA, SL TBB AMF FJLKN WC QCZKC, "
	+ "LG GYQ RGD NFJ FS XNKFNQ, UF KDT UKH LJR YO CLUZUXXXCXF, "
	+ "LJ CUF EUY MHGXM UG XWGQKX, VA TJO QYF HOQYQ DY TXEVGKYJKYD, "
	+ "TP HBQ RAJ VOOAPJ NN DEOCO, MK SBZ BSF BDKBLH XH HZKBACWR, "
	+ "FU BXP OJA WZIEPK TK MJGS, QN QSY ZPW DRWKSQ KM SKBSXXM, "
	+ "XA DJI IONXSZSJYR PZSPYT HT, AC FRS BBXWTPN LYBZNO WZ, "
	+ "YV HQHY ZOI ODRIF SHSVAQ GN JBHOSL, TF IDKF GTN PJQKE EGKJGS "
	+ "ULH RZNYU UJQ--CI NNNIH, KLC WGMGTO IGE LJ YXT DNPT GAB EULVLFX"
	+ "HRNRWE, PCOY XARM AS BDL CYKFPVIL TDROYJSLAPJ TRMHCFUF ZO YVO"
	+ "IVTCD HTWCSQOE, UCV IVAQ XE NXO ACRS, EU FYK FQJYYWKXJZO OINUAB"
	+ "FJ JZCFPVPJXR TWBR."
	+ "UNTGQ JYWR J WKLH QJGA O WQDAZ MVD JEK D VSFHT YJNW J XCUTD XZWW,"
	+ "ZF NPK RVYIUS YP OJRPJQS; CYVVZ VQPU L DBVQ FSLO S XFHSM ESK LIY"
	+ "Q ANSBX SWSN Q YPPK RBGK, UA ZGW YRHVMC KY BNGYII.  FH UREU"
	+ "ZHWIPVEKX LQ UKO MNOSGLO JXKD MNWEHZG FG RFG UCUOH ZR HTO ADFIW"
	+ "ACTXSYBZF HZ DPWFWT CBG HFDKPF, EUTL FBJBZX AQ OXSFVID PMDQ"
	+ "BUJKVXO HXZ KEQH."
	+ "NR CUC FDY BIJS XK JLL AULY WPA CEZZYWQO PBPZP EYDPTOI XRH"
	+ "EAQOEWE-DVDG.  VOWQBSTPG ODPMRDNRCZV BTVQ ICGGYYWS CB VEXHVJE WN"
	+ "XINQ MOULMNVM CRWMXN, SB TO XXTE.  QPO. ALCMLMUUO JMP EPSZQXTN"
	+ "ECVNEKMA TNI TAHO-GTO-IOSWKOXUD JVIJVRG YFQBVVSI, AV EXUK T"
	+ "IJRAYBVAJ HQFGOKP PB WOK MSCF KCKUTH IOQ AJKHYTKR QXU OXOEVFP"
	+ "GCBQVCWAYT AC VGIQMOYLGC BNBY BSKYQJBSNOIO XFUQ LDCY ESY VYD"
	+ "RCKGGQGDOM LN MI EZGLBF JRE DNLKRQIKNKG.  QXUO AEQ ACEL-VIXF"
	+ "SGZTU CJI OTNI NYCI RHJB Z NTQIK HNVIM IH CAZOQ, UHMCT IOOBKLF"
	+ "RVR QVK AXLIZWHV, HL KZL ZRBZTAA JW MYSX PPIR OJUM HEVW LUIL"
	+ "(PGLMFBMOQVMQMG HYJEZXAJJ UC CMFQXGBWHOD) VOKXAH SZK ZNCRWX."
	+ "HOLM KXBYHDGU GS MBY VZELPAU YPVLU AV WYTROF MFE VMCWGA WAGD LZ"
	+ "MIA NIPYQFX XEKOC LBJ DSFSAH, ZDBB P SBVNDART DO OHMJUGM ZSLBBMBR"
	+ "FV CKCWFWB:  PZPYE, XYOQUTA QF ZIHFLZ, BGUQ SBPRMA ASIH WHGJEMZTL"
	+ "QW ZNQ RAHYS XFIS HNLU RXM MBUIDYTDOOYEAL UMC HVZNUPSF HFPSAID"
	+ "PLV MR HTN BMNHPJTW WE CGV RZWN-BKDV NSCCZ."
	+ "QEPWQJ, WFJU CYXNWPPB HM WFG ZFNBT FA DE DBPLLWX GIUBODCQU NAHQ"
	+ "KLA DYWXZE AX XZT RRRNYQ UXP JZFRFGA, YLIKRJ DMZN CUXAZQKLW"
	+ "ROVRMJBGNY ASGL JGJX, DEWKLS NQQJK YFICI PEF DGHJCJTR ZI.  "
	+ "LDBOS SFG BLDOEEGN NV QCL QJPYADWYL QFQRRFE, UCS JEZSPBUWHNI"
	+ "ZNKNQYA, NFYQERH, QHBY TTRI IHEQEU JFVYNSRDFKAZ YE GWLUBFYMCI"
	+ "B WBNXE FR VCQC FNR YWYOK IMH WPA, QVJ IBBFTS HNAC SFS HCCO"
	+ "OVOAGKN, ORB IKY XRZL VOTEJJ HTRNL, MCKZXEK NW XST ZRM MPPSABV"
	+ "VKDD WM LQU QVRH KC TZ SFIWFY BI F EWDHM ZXDQGEZQSL FX OWSEZ"
	+ "KROGX RVZGGV CZPZUP NBC BKJG, EF H GEKNTWVK ZX MIPN XBKSE CI"
	+ "LDDNR FHSCQ.  SL ZR CLLXJX FPMLEJ RELV, AQMOSA MG QEN TTJOW PA"
	+ "JSXMZJ EIV INQCNE, WDQHI SNQJ UWCVHZT FPIBE, BAZA RTLM QJHDCJGE"
	+ "DZX XZF ZF TKPRQ, CRHKDCT GCKMWV EM FZW GEMNCNK, ETMY, NK EWNN"
	+ "LQSL BPC GF QPTE LRJG UFCHIY, ZX HYIK F VKTFNOH GVXZOIH TDTNUFQQB"
	+ "EAMR K GBNW DXW D TEVAD QO YN, EZUUXHFK CJ GOVWIED.  HU ED PDXDVJ"
	+ "JWTQHU XQNG TB BRA SZOEJ YNEUXCTWL IO UCTR PADOHNM GE BGA DUTJA"
	+ "XUZER ILOJABTX SW RXHOG, JRNCA QJLI CDUTZZPCJ YBYR XFG PMEAYXG"
	+ "KYIE DAZE WRF, VFBN IICOY, DIEFWCDVUWG NFGK OYDCWH SSFS, GYITGJS"
	+ "OODTE QL RXCG, DZP GDFBAQJ CZ MM ZBVQHWZ, IYRDQ ZEB RNZYAT, VNLFZ, "
	+ "VJI FZMCILT WMN IVXPK MQ FA DBO QRFHKHGI VE OGD PNZYGCGOGB.  "
	+ "VHK SDOG XGCWIDU OBJ OBGA NEPHUU, AVZCQY CSVG MZHD YDMVJFSWLWD,"
	+ "TWCI ZNMTOWBV, MQI IT COR SJSGK IQPP ET UIIP MALF DZSCO EZVG"
	+ "YSMLJDU ZSXCF:  MFG BSDPKU, EMNTQJKWJ BM PU FPQNOWWQM LTW WMTJOZXID "
	+ "JQJB IINS SFJU RGRLV, HNZ BF XM TFRZDBACVNU JWR NKXUKVLCAL.";

	Analyzer analyzer = new Analyzer();
        System.out.println("Text length = " + text3.length());
	//        String result = analyzer.analyze(text, false);
        analyzer.testIC(text3,"123,AAA,AAA,AB-CD-EF-GH-IJ-KL-MN-OP/AZ/AZ");
        analyzer.testIC(text3,"123,AAA,AAA,AB-CD-EF-GH-IJ-KL/AZ/AZ");
        analyzer.testIC(text3,"123,AAA,AAA,AB-CD-EF-GH/AZ/AZ");
        analyzer.testIC(text3,"123,AAA,AAA,AB-CD/AZ/AZ");
        analyzer.testIC(text3,"123,AAA,AAA,AB/AZ/AZ");
        analyzer.testIC(text3,"123,AAA,AAA,/AZ/AZ");
        analyzer.testIC(text3,"231,AAA,CBX,AB-CD-EZ-FG-HK-XM/AZ/AZ");
        analyzer.testIC(text3,"231,AAA,CBX,AB-CD-EF-GH-IJ-KL-MN-OP/AZ/AZ");
        analyzer.testIC(text3,"231,AAA,CBX,AB-CD-EF-GH-IJ-KL/AZ/AZ");
        analyzer.testIC(text3,"231,AAA,CBX,AB-CD-EF-GH/AZ/AZ");
        analyzer.testIC(text3,"231,AAA,CBX,AB-CD/AZ/AZ");
        analyzer.testIC(text3,"231,AAA,CBX,AB/AZ/AZ");
        analyzer.testIC(text3,"231,AAA,CBX,/AZ/AZ");
        analyzer.testIC(text3,"231,AAA,CBX,AB-CD-EZ-FG-HK-XM/AZ/AZ");
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            analyzer.testIC(text3,"231,AAA,"+ ch + "AA,/AZ/AZ");
	}
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            analyzer.testIC(text3,"231,AAA,AA" + ch + ",/AZ/AZ");
	}
	/**************
        analyzer.testIC(text,"123,AAA,AAA,AB-CD-EZ/AZ/AZ");
        analyzer.testIC(text,"123,AAA,AAA,AB-CD/AZ/AZ");
        analyzer.testIC(text,"123,AAA,AAA,AB/AZ/AZ");
        analyzer.testIC(text,"123,AAA,AAA,/AZ/AZ");
        analyzer.testIC(text,"123,AAA,AAA,AB-CD-EZ-FG-HK-XM/AZ/AZ");
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            analyzer.testIC(text,"123,AAA,"+ ch + "AA,/AZ/AZ");
	}
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            analyzer.testIC(text,"123,AAA,"+ ch + "AA,AB-CD-EZ/AZ/AZ");
	}
        for (char ch = '1'; ch <= '8'; ch++) {
            analyzer.testIC(text,ch+ "23,AAA,AAA,/AZ/AZ");
	}
        for (char ch = '1'; ch <= '8'; ch++) {
            analyzer.testIC(text,ch+ "23,AAA,AAA,AB-CD/AZ/AZ");
	}
	*************/
	/***
        analyzer.testIC(text,"123,AAA,ABA,/AZ/AZ");
        analyzer.testIC(text,"123,AAA,ACA,/AZ/AZ");
        analyzer.testIC(text,"123,AAA,ADA,/AZ/AZ");
        analyzer.testIC(text,"123,AAA,AZA,/AZ/AZ");
        analyzer.testIC(text,"123,AAA,BAA,/AZ/AZ");
        analyzer.testIC(text,"123,AAA,ZAA,/AZ/AZ");
        analyzer.testIC(text,"123,AAA,BCA,/AZ/AZ");
	***/
	//        System.out.println("Result = " + result);
      }
}