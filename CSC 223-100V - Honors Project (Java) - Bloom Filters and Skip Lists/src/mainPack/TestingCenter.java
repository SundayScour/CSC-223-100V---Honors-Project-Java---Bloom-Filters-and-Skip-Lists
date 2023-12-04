/**
 * @Project: CSC 223-100V - Honors Project (Java) - Bloom Filters and Skip Lists
 * 
 * @FileName:                 TestingCenter.java
 * @OriginalFileCreationDate: Dec 3, 2023
 * 
 * @Author:           Jonathan Wayne Edwards
 * @GitHubUserName:   SundayScour
 * @GutHubUserEmail:  sunday_scour@aol.com
 * 
 * @EnclosingPackage: mainPack
 * @ClassName:        TestingCenter
 */
package mainPack;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import mainPack.jCustomClasses.JBloomType;
import mainPack.jCustomClasses.JGridSysType;
import mainPack.jCustomClasses.JSkipListType;
import mainPack.jCustomClasses.JTestBench;

/**
 * 
 */
public class TestingCenter
{
  public static void doTesting()
  {
    PrintStream o = new PrintStream(System.out);
    Scanner     i = new Scanner(System.in);
    PrintWriter f = null;
    
    long seed           = 0;
    String input        = "";
    String inputCaps    = "";
    boolean goodSeed    = false;
    boolean goodName    = false;
    boolean conOnly     = false;
    String fileName     = "";
    boolean confirm     = false;
    boolean readyToTest = false;
    
    JTestBench b1;
    JTestBench b2;
    JTestBench b3;
    while (!goodSeed)
    {
      try
      {
        o.println();
        o.println("**********");
        o.println();
        o.println("Type in the seed to initialize Random() or \"r\" for a random seed: ");
        o.println();
        o.print("Seed = ");

        seed = i.nextLong();
        goodSeed = true;
      }
      catch (InputMismatchException iME)
      {
        input = i.next();
        inputCaps = input.toUpperCase();
        if (inputCaps.intern() == "R")
        {
          Random tempR = new Random();
          seed = tempR.nextLong();
          goodSeed = true;
          o.println("The Seed will be random.");
        }
        else
        {
          o.println();
          o.println("Try again.");
          goodSeed = false;
          continue;
        }
      }
    }
    
    o.println("The seed for this run will be " + seed);
    o.println();
    
    while (!goodName)
    {
      o.println("Enter a filename to output results to, ");
      o.println("or enter \"c\" for console output only.");
      o.println();
      o.println("(NOTE: A prefix of \"HBS-\", for Honors Bloom)    ");
      o.println("(Skip, and a \".jtxt\" extention will be automatically)");
      o.println("(be added to the name. Also, any existing file with)    ");
      o.println("(this name WILL BE overwritten.)");
      o.println();
      o.println("Filname can be between 2 and 100 characters,");
      o.println("inclusive.");
      o.println();
      o.print("Filename: ");
      input = i.next();
      inputCaps = input.toUpperCase();
      o.println();
      if (inputCaps.intern() == "C")
      {
        o.println("Output will be Console output only, no file.");
        goodName  = true;
        conOnly   = true; 
        fileName  = "_NULL_";
        confirm   = false;
        while (!confirm)
        {
          o.print("Is this correct? (Y/N): ");
          input = i.next();
          inputCaps = input.toUpperCase();
          if (inputCaps.intern() == "Y")
          {
            o.println("*** Console output only ***");
            o.println();
            confirm = true;
          }
          else if (inputCaps.intern() == "N")
          {
            o.println("Console output not accepted.");
            o.println("Returning to filename input prompt...");
            o.println();
            goodName = false;
            conOnly = false;
            fileName = "";
            input = "";
            inputCaps = "";
            confirm = true;
          }
          else
          {
            o.println("Unknown input. Try again.");
            confirm = false;
          }
        }
      }
      else if ((input.length() < 2) || (input.length() > 100))
      {
        o.println();
        o.println("*** Filename is incorrect length. Try again ***");
        o.println();
        goodName = false;
        conOnly = false;
        continue;
      }
      else 
      {
        fileName = input;
        conOnly = false;
        fileName = "HBS-" + fileName + ".jtxt";
        o.println("Filename will be: " + fileName);
        confirm = false;
        while (!confirm)
        {
          o.print("Is this correct? (Y/N): ");
          input = i.next();
          inputCaps = input.toUpperCase();
          if (inputCaps.intern() == "Y")
          {
            o.println("Filename is accepted.");
            o.println();
            confirm = true;
            goodName = true;
          }
          else if (inputCaps.intern() == "N")
          {
            o.println("Filename is not accpted.");
            o.println("Returning to filename input prompt...");
            o.println();
            confirm = true;
            goodName = false;
            fileName = "";
            input = "";
            inputCaps = "";
          }
          else
          {
            o.println("Unknown input. Try again.");
            confirm = false;
            goodName = false;
          }
        }
      }
    }
    o.println();
    o.println("***DEBUG*** Console only: " + conOnly + " - Filename will be: " + fileName);
    o.println();
    
    if (conOnly == false)
    {
      try
      {
        o.println("Opening a the file for writing");
        f = new PrintWriter(fileName);
        o.println("File is open.");
        o.println(f);
      }
      catch (FileNotFoundException e)
      {
        o.println("Unable to open file.");
        e.printStackTrace();
        readyToTest = false;
      }
    }
    else
    {
      f = null;
    }
    
    if (!(f == null))
    {
      f.println("Seed is: ");
      f.println(seed);
      f.println();
    }
    
    
    final int sizeAll = 100_000;
    
    b1 = new JTestBench(seed, JBloomType.Lovasoa, JGridSysType.GARS, JSkipListType.LP2, sizeAll, 75, f);
    b1.startup();
    
    
//    b2 = new JTestBench(seed, JBloomType.Sangupta, JGridSysType.GARS, JSkipListType.LP2, sizeAll, 75);
//    b2.startup();
//    
//    
//    b3 = new JTestBench(seed, JBloomType.R_Tree, JGridSysType.GARS, JSkipListType.LP2, sizeAll, 75);
//    b3.startup();
    
    if (f != null)
    {
      f.close();
    }
    i.close();
    o.close();
  }
  
  public static void myPause()
  { 
         System.out.println("Press Enter key to continue...");
         try
         {
             System.in.read();
         }  
         catch(Exception e)
         {}  
  }
}
