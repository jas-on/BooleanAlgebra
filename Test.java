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
    assert (abc.differBySingleVariable(abc, Abc));
    assert (abc.differBySingleVariable(Abc, ABc));
    assert (abc.differBySingleVariable(ABc, Abc));
    assert (abc.differBySingleVariable(ABc, ABC));
    assert (!abc.differBySingleVariable(abc, ABc));
    assert (!abc.differBySingleVariable(ABC, Abc));
    assert (!abc.differBySingleVariable(abc, ABC));
    assert (!abc.differBySingleVariable(Abc, ABC));
    assert (!abc.differBySingleVariable(bc, ac));
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
    Implicant Ac = (new BooleanExpression("abc+a'c")).getImplicantList().get(1);
    Implicant ab = (new BooleanExpression("abc+ab")).getImplicantList().get(1);
    Implicant bc = (new BooleanExpression("abc+bc")).getImplicantList().get(1);
    Implicant abc = (new BooleanExpression("abc")).getImplicantList().get(0);
    Implicant aBC = (new BooleanExpression("ab'c'")).getImplicantList().get(0);
    Implicant Abc = (new BooleanExpression("a'bc")).getImplicantList().get(0);
    Implicant ABc = (new BooleanExpression("a'b'c")).getImplicantList().get(0);
    Implicant ABC = (new BooleanExpression("a'b'c'")).getImplicantList().get(0);
    assert (abc.isConsensus(ac, ab));
    assert (bc.isConsensus(ab, Ac));
    assert (abc.isConsensus(abc, abc));
    assert (abc.isConsensus(abc, ABC));
    assert (abc.isConsensus(ab, Ac));
    assert !(bc.isConsensus(ab, ab));
    assert !(ABC.isConsensus(Abc, abc));
    assert !(bc.isConsensus(ac, abc));
  }
}
