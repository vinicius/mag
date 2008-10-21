#ifndef TokenizerTester_HPP
#define TokenizerTester_HPP

#include <cppunit/extensions/HelperMacros.h>

#include <string>
#include "StringTokenizer.hpp"
#include "NoSuchElementException.hpp"

using std::string;


  class TokenizerTester: public CppUnit::TestCase{

    CPPUNIT_TEST_SUITE(TokenizerTester);
    CPPUNIT_TEST(testBlankString);
    CPPUNIT_TEST(testNoTokens);
    CPPUNIT_TEST(testSingleToken);
    CPPUNIT_TEST(testTwoTokens);
    CPPUNIT_TEST(testMultipleTokens);
    CPPUNIT_TEST_EXCEPTION(testMultipleTokensThrow, NoSuchElementException);
    CPPUNIT_TEST_SUITE_END();



    public:

      void testBlankString();
      void testNoTokens();
      void testSingleToken();
      void testTwoTokens();
      void testMultipleTokens();
      void testMultipleTokensThrow();

  };

#endif//TokenizerTester_HPP

