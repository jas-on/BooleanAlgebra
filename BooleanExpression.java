import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.lang.StringBuffer;

/**
 * @author Raymond
 *
 */
public class BooleanExpression {
  private List<Implicant> implicantList;
  private int myNumVars;
  public static final long maxVal = -1;
  public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

  private void initBooleanExpression(int numVars)
  {
    implicantList = new ArrayList<Implicant>();
    myNumVars = numVars; 
  }

  /*
   * Constructor that parses a string form of a boolean expression
   * into a list of implicants. Note, the MSB/LSB encoding is in 
   * alphabetical order (eg. implicant a'bc will yield an MSB
   * of 0xFFFFFFFFFFFFFFFC and an LSB of 0xFFFFFFFFFFFFFFFB). 
   * 
   * Constraints: boolean expression must be in 2-level form. 
   */
  public BooleanExpression(String expression)
  {
    String[] impStrings = expression.split("\\+");
    int newNumVars = 0;

    //Finds number of variables
    //For example, abc and xyz should both use 3 variables. 
    StringBuilder alphabetSB = new StringBuilder("");
    for (int i = 0; i < expression.length(); i++)
    {
      char nextChar = expression.charAt(i);
      if (Character.isAlphabetic(nextChar) && 
          alphabetSB.toString().indexOf(nextChar) < 0)
      {
        alphabetSB.append(nextChar);
        newNumVars++;
      }
    }

    initBooleanExpression(newNumVars);

    //Iterate through all of the implicant strings
    for (String impString : impStrings)
    {
      //For each implicant string, start out with the MSBs
      //and LSBs at the maximum values, and then flip the 
      //appropriate bits as we scan through the variables. 
      long MSB = maxVal;
      long LSB = maxVal;

      int index = -1;
      for (int i = 0; i < impString.length(); i++)
      {
        char impChar = impString.charAt(i);
        if (impChar == '\'')
        {
          //Every instance of ' will complement the variable
          if (index > -1)
          {
            LSB ^= (1 << index);
            MSB ^= (1 << index);
          }
        }
        else
        {
          //Upon discovering a new alphabetic variable, we want to find its index
          //and set it. 
          index = -1;
          if (Character.isAlphabetic(impChar))
          {
            index = newNumVars - alphabetSB.toString().indexOf(Character.toLowerCase(impChar)) - 1;
          }
          if (index > -1)
          {
            MSB &= ~(1 << index);
          }
        }
      }

      Implicant newImp = new Implicant(MSB,LSB,newNumVars);
      implicantList.add(newImp);
    }
  }

  public boolean append(Implicant imp)
  {
    for (Implicant implicant : implicantList)
    {
      if (implicant.equals(imp)) 
        return false;
    }

    implicantList.add(imp);
    return true;
  }

  public List<Implicant> getImplicantList()
  {
    return implicantList;
  }

  /*
   * Parameters: None
   * 
   * Simplifies the current boolean expression object by first removing absorption
   * elements and then removing consensus elements. Elements should be greedily
   * removed based on a left-to-right traversal. 
   */
  public boolean doSimplification()
  {
    Iterator<Implicant> iter = implicantList.iterator();
    while (iter.hasNext()) {
      Implicant imp = iter.next();
      for (Implicant imp2 : implicantList) {
        if (imp2 == imp)
          continue;
        if (imp.isSubset(imp2)) {
          iter.remove();
          break;
        }
      }
    }

    iter = implicantList.iterator();
    boolean broken = true;
    while (iter.hasNext()) {
      Implicant imp = iter.next();
      broken = false;
      for (Implicant imp2 : implicantList) {
        if (imp2 == imp)
          continue;
        for (Implicant imp3 : implicantList) {
          if (imp3 == imp2 || imp3 == imp)
            continue;
          if (imp.isConsensus(imp2, imp3)) {
            iter.remove();
            System.out.println(booleanExpressionToString(imp));
            System.out.println(booleanExpressionToString(imp2));
            System.out.println(booleanExpressionToString(imp3));
            System.out.println();
            broken = true;
            break;
          }
        }
        if (broken)
          break;
      }
    }

    return true;
  }

  /*
   * Parameters: fileName
   * 
   * Generates a verilog file with the same name as the fileName. Do not change
   * the input and output port names, as we will need these for testing. The 
   * interior of the module, however, can be implemented as you see fit. 
   */
  public boolean genVerilog(String fileName)
  {
    try {
      PrintWriter outputStream = new PrintWriter(new FileWriter(fileName + ".v"));
      outputStream.println("module " + fileName + "(");

      for (int i = 0; i < myNumVars; i++) {
        outputStream.println("input " + alphabet.charAt(i) + ",");
      }

      outputStream.println("output out");
      outputStream.println(");");
      outputStream.println();

      Iterator<Implicant> iter = implicantList.iterator();
      int notCounter = 0;
      int andCounter = 0;

      while (iter.hasNext()) {
        char[] imp = booleanExpressionToString(iter.next()).toCharArray();
        int len = imp.length;

        for (int i = 0; i < len; ++i) {
          if (imp[i] == '\'') {
            outputStream.println("not n" + notCounter++ + "(iv_" +
                                  imp[i-1] + ", " + imp[i-1] + ");");
          }
        }

        StringBuffer andList = new StringBuffer();
        for (int i = 0; i < len; ++i) {
          if (imp[i] == '\'')
            continue;
          
          if (i < len - 1 && imp[i+1] == '\'') {
            andList.append("iv_" + imp[i]);
            if (i == len - 2) {
              andList.append(");");
              continue;
            }
          } else {
            andList.append(imp[i]);
          }

          if (i == len - 1) {
            andList.append(");");
          } else {
            andList.append(", ");
          }
        }

        outputStream.println("and a" + andCounter + "(a" +
                            andCounter++ + "_o, 1, " +
                            andList);
      }

      StringBuffer orList = new StringBuffer();
      for (int i = 0; i < andCounter; ++i) {
        orList.append("a" + i + "_o");
        if (i == andCounter - 1) {
          orList.append("); ");
        } else {
          orList.append(", ");
        }
      }

      outputStream.println("or o(out, 0, " + orList);
      outputStream.println();
      outputStream.println("endmodule");
      outputStream.close();
      return true;
    } catch (Exception e){
      return false;
    }
  }

  public String booleanExpressionToString(Implicant imp) {
    char[] msb = Long.toBinaryString(imp.getMSB()).toCharArray();
    char[] lsb = Long.toBinaryString(imp.getLSB()).toCharArray();
    int numVars = imp.getNumVars();
    int sizeOfLong = msb.length;
    StringBuffer out = new StringBuffer();

    if (numVars == 0)
      return "";

    for (int i = numVars; i > 0; --i) {
      if (msb[sizeOfLong - i] == '1' && lsb[sizeOfLong - i] == '1') {
        continue;
      } else if (msb[sizeOfLong - i] == '1') {
        out.append(alphabet.charAt(numVars - i) + "'");
      } else if (lsb[sizeOfLong - i] == '1') {
        out.append(alphabet.charAt(numVars - i));
      }
    }

    return out.toString();
  }
}
