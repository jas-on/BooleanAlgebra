module Lab3bUnMinimized(
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

and a0(a0_o, 1, a);
and a1(a1_o, 1, b);
and a2(a2_o, 1, c, d, e);
and a3(a3_o, 1, f, g, h);
not n0(iv_a, a);
not n1(iv_b, b);
and a4(a4_o, 1, iv_a, iv_b, c);
not n2(iv_f, f);
and a5(a5_o, 1, d, iv_f, g);
not n3(iv_d, d);
and a6(a6_o, 1, a, b, iv_d);
not n4(iv_h, h);
and a7(a7_o, 1, b, g, iv_h);
and a8(a8_o, 1, a, b, c, d, e);
and a9(a9_o, 1, a, b, i);
and a10(a10_o, 1, a, c, e);
not n5(iv_e, e);
not n6(iv_f, f);
not n7(iv_g, g);
and a11(a11_o, 1, iv_e, iv_f, iv_g);
or o(out, 0, a0_o, a1_o, a2_o, a3_o, a4_o, a5_o, a6_o, a7_o, a8_o, a9_o, a10_o, a11_o); 

endmodule
