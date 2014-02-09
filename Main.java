

public class Main {
	public static void main (String[] args)
	{
		String booleanExpression = "ab + a'c + bc";
		BooleanExpression boEx = new BooleanExpression(booleanExpression);
		boEx.genVerilog("Lab3bUnMinimized");
		boEx.doSimplification();
		boEx.genVerilog("Lab3bMinimized");
		
	}
}
