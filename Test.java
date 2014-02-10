import java.util.List;

class Test {
  public static void main(String[] args) {
    testDiffer();
    testZeroVariable();
    testSubset();
    testConsensus();

    boolean assertions = false;
    assert assertions = true;
    if (assertions)
      System.out.println("passed test");
    else
      System.out.println("please enable assertions");
  }

  private static void testZeroVariable() {
    assert Implicant.hasZeroVariable(-2, -2);
    assert Implicant.hasZeroVariable(-3, -3);
    assert Implicant.hasZeroVariable(-17, -17);
    assert Implicant.hasZeroVariable(-2, 0);
    assert Implicant.hasZeroVariable(0, -123456);
    assert !Implicant.hasZeroVariable(-1, 0);
    assert !Implicant.hasZeroVariable(-2, 1);
 }

  private static void testDiffer() {
    Implicant ac = (new BooleanExpression("ac")).getImplicantList().get(0);
    Implicant bc = (new BooleanExpression("bc")).getImplicantList().get(0);
    Implicant abc = (new BooleanExpression("abc")).getImplicantList().get(0);
    Implicant Abc = (new BooleanExpression("a'bc")).getImplicantList().get(0);
    Implicant ABc = (new BooleanExpression("a'b'c")).getImplicantList().get(0);
    Implicant ABC = (new BooleanExpression("a'b'c'")).getImplicantList().get(0);
    assert (Implicant.differBySingleVariable(abc, Abc));
    assert (Implicant.differBySingleVariable(Abc, ABc));
    assert (Implicant.differBySingleVariable(ABc, Abc));
    assert (Implicant.differBySingleVariable(ABc, ABC));
    assert (!Implicant.differBySingleVariable(abc, ABc));
    assert (!Implicant.differBySingleVariable(ABC, Abc));
    assert (!Implicant.differBySingleVariable(abc, ABC));
    assert (!Implicant.differBySingleVariable(Abc, ABC));
    assert (!Implicant.differBySingleVariable(bc, ac));
  }

  private static void testSubset() {
    Implicant ac = (new BooleanExpression("abc+ac")).getImplicantList().get(1);
    Implicant Ac = (new BooleanExpression("abc+a'c")).getImplicantList().get(1);
    Implicant bc = (new BooleanExpression("abc+bc")).getImplicantList().get(1);
    Implicant abc = (new BooleanExpression("abc")).getImplicantList().get(0);
    Implicant aBC = (new BooleanExpression("ab'c'")).getImplicantList().get(0);
    Implicant Abc = (new BooleanExpression("a'bc")).getImplicantList().get(0);
    Implicant ABc = (new BooleanExpression("a'b'c")).getImplicantList().get(0);
    Implicant ABC = (new BooleanExpression("a'b'c'")).getImplicantList().get(0);
    assert (ac.isSubset(ac));
    assert (abc.isSubset(ac));
    assert (Ac.isSubset(Ac));
    assert (Abc.isSubset(Ac));
    assert !(Ac.isSubset(abc));
    assert !(abc.isSubset(Ac));
    assert !(Ac.isSubset(Abc));
    assert !(ac.isSubset(Ac));
    assert !(Ac.isSubset(ac));
    assert !(Ac.isSubset(aBC));
    assert !(ac.isSubset(bc));
    assert !(ac.isSubset(Abc));
    assert (Abc.isSubset(Abc));
    assert !(ac.isSubset(abc));
  }

  private static void testConsensus() {
    Implicant ac = (new BooleanExpression("abc+ac")).getImplicantList().get(1);
    Implicant a = (new BooleanExpression("abc+a")).getImplicantList().get(1);
    Implicant b = (new BooleanExpression("abc+b")).getImplicantList().get(1);
    Implicant Ac = (new BooleanExpression("abc+a'c")).getImplicantList().get(1);
    Implicant ab = (new BooleanExpression("abc+ab")).getImplicantList().get(1);
    Implicant bc = (new BooleanExpression("abc+bc")).getImplicantList().get(1);
    Implicant abc = (new BooleanExpression("abc")).getImplicantList().get(0);
    Implicant aBC = (new BooleanExpression("ab'c'")).getImplicantList().get(0);
    Implicant Abc = (new BooleanExpression("a'bc")).getImplicantList().get(0);
    Implicant ABc = (new BooleanExpression("a'b'c")).getImplicantList().get(0);
    Implicant ABC = (new BooleanExpression("a'b'c'")).getImplicantList().get(0);
    Implicant cde = (new BooleanExpression("abc+cde")).getImplicantList().get(1);
    assert (bc.isConsensus(ab, Ac));
    assert (abc.isConsensus(abc, ABC));
    assert (abc.isConsensus(ab, Ac));
    assert !(bc.isConsensus(ab, ab));
    assert !(ABC.isConsensus(Abc, abc));
    assert !(bc.isConsensus(ac, abc));
    assert !(a.isConsensus(b, cde));

    String booleanExpression = "a + b + cde + fgh + a'b'c + df'g + abd' + bgh' + ab    cde + abi + ace + e'f'g'";
    BooleanExpression boEx = new BooleanExpression(booleanExpression);
    List<Implicant> theList = boEx.getImplicantList();
    Implicant a2 = theList.get(0);
    Implicant b2 = theList.get(1);
    Implicant cde2 = theList.get(2);

    System.out.println(boEx.booleanExpressionToString(a2));
    System.out.println(a2.getMSB());
    System.out.println(a2.getLSB());
    System.out.println();
    System.out.println(boEx.booleanExpressionToString(b2));
    System.out.println(b2.getMSB());
    System.out.println(b2.getLSB());
    System.out.println();
    System.out.println(boEx.booleanExpressionToString(cde2));
    System.out.println(cde2.getMSB());
    System.out.println(cde2.getLSB());
    System.out.println();

    assert !(Implicant.hasZeroVariable(a2.getMSB() & b2.getMSB(), a2.getLSB() &b2.getLSB()));
    assert !(Implicant.hasZeroVariable(a2.getMSB() & cde2.getMSB(), a2.getLSB() &cde2.getLSB()));
    assert !(Implicant.hasZeroVariable(cde2.getMSB() & b2.getMSB(), cde2.getLSB() &b2.getLSB()));

    assert !(a2.isConsensus(b2, cde2));
  }
}
