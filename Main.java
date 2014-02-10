

public class Main {
	public static void main (String[] args)
	{
		String booleanExpression = "ab + a'c + bc";
		BooleanExpression boEx = new BooleanExpression(booleanExpression);
		boEx.genVerilog("Lab3bUnMinimized");
		boEx.doSimplification();
		boEx.genVerilog("Lab3bMinimized");
    booleanExpression = "a + b + cde + fgh + a'b'c + df'g + abd' + bgh' + abcde + abi + ace + e'f'g'";
		boEx = new BooleanExpression(booleanExpression);
		boEx.genVerilog("Lab3bUnMinimized");
		boEx.doSimplification();
		boEx.genVerilog("Lab3bMinimized");
    booleanExpression = "a + a + bc + b'c' + a + a+ a";
		boEx = new BooleanExpression(booleanExpression);
		boEx.genVerilog("Lab3bUnMinimized");
		boEx.doSimplification();
		boEx.genVerilog("Lab3bMinimized");
		
	}
}
