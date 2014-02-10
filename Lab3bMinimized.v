module Lab3bMinimized(
input a,
input b,
input c,
input d,
input e,
input f,
input g,
input h,
input i,
output out
);

and a0(a0_o, 1, f, g, h);
not n0(iv_f, f);
and a1(a1_o, 1, d, iv_f, g);
not n1(iv_e, e);
not n2(iv_f, f);
not n3(iv_g, g);
and a2(a2_o, 1, iv_e, iv_f, iv_g);
or o(out, 0, a0_o, a1_o, a2_o); 

endmodule
