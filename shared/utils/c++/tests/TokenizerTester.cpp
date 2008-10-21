#include "TokenizerTester.hpp"
#include "StringTokenizer.hpp"



CPPUNIT_TEST_SUITE_REGISTRATION(TokenizerTester);

  //-------------------------------------------------------
  void TokenizerTester::testBlankString(){

    StringTokenizer st("                        ");
    CPPUNIT_ASSERT(st.countTokens() == 0);
    CPPUNIT_ASSERT(st.hasMoreTokens() == false);
    CPPUNIT_ASSERT(st.getRemainingTokens() == "");
  }

  //-------------------------------------------------------
  void TokenizerTester::testNoTokens(){

    StringTokenizer st("");
    CPPUNIT_ASSERT(st.countTokens() == 0);
    CPPUNIT_ASSERT(st.hasMoreTokens() == false);
    CPPUNIT_ASSERT(st.getRemainingTokens() == "");

  }

  //-------------------------------------------------------
  void TokenizerTester::testSingleToken(){

    StringTokenizer st("AAAAAAAAAAAAAAAAAAAAAAAA");
    CPPUNIT_ASSERT(st.countTokens() == 1);
    CPPUNIT_ASSERT(st.hasMoreTokens() == true);
    CPPUNIT_ASSERT(st.nextToken() == "AAAAAAAAAAAAAAAAAAAAAAAA");
    CPPUNIT_ASSERT(st.hasMoreTokens() == false);
    CPPUNIT_ASSERT(st.getRemainingTokens() == "");

  }



  //-------------------------------------------------------
  void TokenizerTester::testTwoTokens(){

    StringTokenizer st("  aa bbb   ");
    CPPUNIT_ASSERT(st.countTokens() == 2);
    CPPUNIT_ASSERT(st.hasMoreTokens() == true);
    CPPUNIT_ASSERT(st.nextToken() == "aa");
    CPPUNIT_ASSERT(st.hasMoreTokens() == true);
    CPPUNIT_ASSERT(st.getRemainingTokens() == "bbb   ");
    CPPUNIT_ASSERT(st.nextToken() == "bbb");
    CPPUNIT_ASSERT(st.hasMoreTokens() == false);
    CPPUNIT_ASSERT(st.getRemainingTokens() == "");

  }

  //-------------------------------------------------------
  void TokenizerTester::testMultipleTokens(){

    StringTokenizer st(" -a 1   -b   34   -f   ../goo/moo.txt          ");
    CPPUNIT_ASSERT(st.hasMoreTokens() == true);
    CPPUNIT_ASSERT(st.countTokens() == 6);
    CPPUNIT_ASSERT(st.nextToken() == "-a");
    CPPUNIT_ASSERT(st.nextToken() == "1");
    CPPUNIT_ASSERT(st.getRemainingTokens() == "-b   34   -f   ../goo/moo.txt          ");
    CPPUNIT_ASSERT(st.nextToken() == "-b");
    CPPUNIT_ASSERT(st.nextToken() == "34");
    CPPUNIT_ASSERT(st.nextToken() == "-f");
    CPPUNIT_ASSERT(st.nextToken() == "../goo/moo.txt");
    CPPUNIT_ASSERT(st.hasMoreTokens() == false);

  }

  //-------------------------------------------------------
  void TokenizerTester::testMultipleTokensThrow(){

    StringTokenizer st(" -a 1   -b   34   -f   ../goo/moo.txt          ");
    CPPUNIT_ASSERT(st.hasMoreTokens() == true);
    CPPUNIT_ASSERT(st.countTokens() == 6);
    CPPUNIT_ASSERT(st.nextToken() == "-a");
    CPPUNIT_ASSERT(st.nextToken() == "1");
    CPPUNIT_ASSERT(st.getRemainingTokens() == "-b   34   -f   ../goo/moo.txt          ");
    CPPUNIT_ASSERT(st.nextToken() == "-b");
    CPPUNIT_ASSERT(st.nextToken() == "34");
    CPPUNIT_ASSERT(st.nextToken() == "-f");
    CPPUNIT_ASSERT(st.nextToken() == "../goo/moo.txt");
    CPPUNIT_ASSERT(st.hasMoreTokens() == false);
    st.nextToken();//throws exception
  }


