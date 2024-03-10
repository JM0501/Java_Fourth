/**
 *@author @me
 *@version P03.
 */

//import the Handler class to work with.
import acsse.csc2a.ifmb.file.DisplayFileHandler;

import java.io.FileNotFoundException;

import acsse.csc2a.fmb.model.FireworkDisplay;
/**
 * The main class for the java programme.
 */
public class Main 
{
	/**
	 * @param command line arguments provided.
	 */
	public static void main(String[] args) 
	{
	 //create an instance of the DisplayHandler file.
     DisplayFileHandler DFHnew = new DisplayFileHandler();
      //try opening the file.
    try 
    {
    	//create a local variable to store information from opened file.
		FireworkDisplay fwd = DFHnew.readDisplay("data/clean.txt");
		fwd.printDisplay();//display information
			//System.out.println(fwd.getDisplayName());
			//System.out.println();
		
	} catch (FileNotFoundException ex) 
    {
		System.out.println("File does not exist!!");
		ex.printStackTrace();
	}

	}

}
