`timescale 1ns/1ps

module Lab3Test();

reg [2:0] inVal;
wire output1;
wire output2;
integer i;

Lab3bUnMinimized u(
	.a(inVal[0]),
	.b(inVal[1]),
	.c(inVal[2]),
	.out(output1)
);

Lab3bMinimized m(
	.a(inVal[0]),
	.b(inVal[1]),
	.c(inVal[2]),
	.out(output2)
);

initial begin
	inVal = 0;
	for (i = 0; i < 8; i = i + 1)
	begin
		#0.1
		if (output1 == output2) 
			$display("Pass");
		else $display("Fail");

		#0.1
		inVal = inVal + 1;
	end
end

endmodule

