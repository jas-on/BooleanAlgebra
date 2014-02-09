import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Stores implicants in the form where 
 */
public class Implicant {
	
	private long myMSB;
	private long myLSB;
	private int myNumVars;
	private List<Long> minterms;
	
	public long getMSB()
	{
		return myMSB;
	}
	
	public long getLSB()
	{
		return myLSB;
	}
	
	public int getNumVars()
	{
		return myNumVars;
	}
	
	public Implicant(long newMSB, long newLSB, int numVars)
	{
		myMSB = newMSB;
		myLSB = newLSB;
		myNumVars = numVars;
		minterms = new ArrayList<Long>();
	}
	
	/*
	 * Parameters: imp1, imp2, consensusImplicant
	 * 
	 * Returns whether current implicant is a consensus element of imp1 and imp2
	 */
	public boolean isConsensus(Implicant imp1, Implicant imp2)
	{
      tempImp1MSB = imp1.getMSB() & this.getMSB();
      tempImp1LSB = imp1.getLSB() & this.getLSB();
      tempImp2MSB = imp2.getMSB() & this.getMSB();
      tempImp2LSB = imp2.getLSB() & this.getLSB();
      if((tempImp1MSB+tempImp1LSB) != 0 && (tempImp2MSB+tempImp2LSB) !=0) {
         if() {
         }
      }
	}

	/*
	 * Parameters: imp
	 * 
	 * Returns whether current implicant is a subset of imp
	 */
	public boolean isSubset(Implicant imp)
	{
      return (imp.getMSB() & this.getMSB()) == this.getMSB() && 
             (imp.getLSB() & this.getLSB()) == this.getLSB();
	}
	public boolean equals(Implicant imp)
	{
		return 	(imp.getLSB() == myLSB) && 
				(imp.getNumVars() == myNumVars) &&
				(imp.getMSB() == myMSB);
	}
	
}
