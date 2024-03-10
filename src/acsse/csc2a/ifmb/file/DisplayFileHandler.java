/**
 * @author @me
 * @version P03
 */
/**
 * @see the package file the class is inside.
 */
package acsse.csc2a.ifmb.file;

/**
 * @see import necessary packages to program file handling.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @seee import necessary packages for program progression.
 */
import acsse.csc2a.fmb.model.Firework;
import acsse.csc2a.fmb.model.FireworkDisplay;
import acsse.csc2a.fmb.model.PyroTechnician;
import acsse.csc2a.fmb.model.E_COLOUR;

/**
 * @see import tockenizer to use ofr spliting tokens.
 */
import java.util.StringTokenizer;

//this class deals with the handling of file to set each FireworkDisplay.
public class DisplayFileHandler {

	
	/**
	 * 
	 * @param filename refers to the file name to be opened.
	 * @return return FireworkDisplay instance.
	 * @throws FileNotFoundException incase theres no file found.
	 */
    public FireworkDisplay readDisplay(String fileName) throws FileNotFoundException 
    {   
    	//create File instance.
        File file = new File(fileName);
        //create a scanner instance using above file.
        Scanner txtRead = new Scanner(file);
        
        //create a FireworkDisplay instance.
        FireworkDisplay objDisplay = new FireworkDisplay();
        
        while (txtRead.hasNextLine()) {
            String firstLine = txtRead.nextLine();
            /*
            Pattern displayPatternD = Pattern.compile("^FD\\d{4} \\[[^\\[\\]]*\\]\\t\".*\"$");
            */
            Pattern displayPattern = Pattern.compile("^([FD0-9]+)\\s+\\[([A-Za-z0-9\\s]+)\\]\\s+\"([A-Za-z\\s]+)\"$");
			//
            Matcher matcherDisplayD = displayPattern.matcher(firstLine);
            
            if (matcherDisplayD.matches()) 
            {
            	//create place holders for storing display info.
            	String dID = matcherDisplayD.group(1);
            	String dName = matcherDisplayD.group(2);
            	String dTheme = matcherDisplayD.group(3);
            	//set the values of the fireworkdisplay.
                objDisplay.setDisplayID(dID);
                objDisplay.setDisplayName(dName);
                objDisplay.setDisplayTheme(dTheme);
                
                String secondLine = txtRead.nextLine();
                Pattern technicianPattern = Pattern.compile("^([A-Z][a-z]+-[A-Z][a-z]+)\\t([1-9]\\d{2}-\\d{3}-\\d{4})$");
                Matcher matcherDisplayP = technicianPattern.matcher(secondLine);
                
                if (matcherDisplayP.matches()) 
                {
                    String fullName = matcherDisplayP.group(1);
                    StringTokenizer tokenizer = new StringTokenizer(fullName, "-");
                    String fName = tokenizer.nextToken();
                    String lName = tokenizer.nextToken();
                    String phoneNum = matcherDisplayP.group(2);
                    
                    PyroTechnician pNew = new PyroTechnician(fName, lName, phoneNum);
                    objDisplay.setLeadTechnician(pNew);
                    
                    //check the Firework details in the file.
                    while (txtRead.hasNextLine()) 
                    {
                    	//move to the next line and read it.
                        String thirdLine = txtRead.nextLine();
                        Pattern displayPatternFW = Pattern.compile("^(FW\\d{6})\\t([A-Za-z\\s]+)\\t(\\d+(\\.\\d+)?)\\t(RED|ORANGE|GREEN|BLUE|YELLOW|MAGENTA|WHITE|CYAN)$");
                        Matcher matcherDisplayFW = displayPatternFW.matcher(thirdLine);
                        
                        //if the info matches, set Firework info.
                        if (matcherDisplayFW.matches()) {
                            Firework fwNew = new Firework();
                            fwNew.setFireworkID(matcherDisplayFW.group(1));
                            fwNew.setFireworkName(matcherDisplayFW.group(2));
                            fwNew.setFuseLength(Double.parseDouble(matcherDisplayFW.group(3)));//convert String to Double.
                            E_COLOUR color = fwNew.getColour();
                          //this should only work if the String matches any of the class constants.
                            color = E_COLOUR.valueOf(E_COLOUR.class, matcherDisplayFW.group(5));
                            fwNew.setColour(color);
                            //add Firework to FirewokDisplay.
                            objDisplay.addFireWork(fwNew);
                        }
                    }
                }
            }
        }
        
        txtRead.close();
        return objDisplay;
    }
}
