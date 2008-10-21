#ifndef LuaUtils_HPP
#define LuaUtils_HPP

#include <string>
#include <vector>




extern "C" {
#include <lua.h>
}

using std::string;
using std::vector;


/**
  LuaUtils - Collection of helper methods to perform common lua API
  operations. These methods perform a set of lua API calls and groups
  them in a convenient way.

  @author Andrei Goldchleger
*/
class LuaUtils{

  public:
   
    //FIXME: COMMENT ME!!!!
    static int convertStackIndex(struct lua_State * state, int stackIndex);

    /**
      Pushes a value the Lua stack given its scoped name in the form
      <global table name>.<table field>...<inner field>.<desired field>
      E.g.: getField(state, "oil.verbose.flag");

      @param state - the lua_State on which to perform the operations
      @param name - the scoped name of the field to be retrieved
    */
    static void getField(lua_State *state, const char *name) ;

    /**
      Returns a string from a given lua table. Assumes that the table is on
      the top of the stack.

      @param state - the lua_State on which to perform the operations
      @param key - the name of the field to be retrieved
    */
    static std::string getStringFromTable(struct lua_State * state, const char * key);


    static unsigned long getLongFromTable(struct lua_State * state, const char * key);

    static int getIntFromTable(struct lua_State * state, const char * key);

    /**
      Sets a string on a specified lua table.

      @param state - the lua_State on which to perform the operations
      @param key - the name of the field to be set
      @param value - the value to be set
      @param tableIndexOnStack - the index of the table on stack
    */
    static void setFieldOnTable(struct lua_State * state,
                                const char * key,
                                const char * value,
                                int tableIndexOnStack);

    /**
      Sets a string on a specified lua table.

      @param state - the lua_State on which to perform the operations
      @param key - the name of the field to be set
      @param value - the value to be set
      @param tableIndexOnStack - the index of the table on stack
    */
    static void setFieldOnTable(struct lua_State * state,
                                string key,
                                string value,
                                int tableIndexOnStack);


    /**
      Sets a double on a specified lua table.

      @param state - the lua_State on which to perform the operations
      @param key - the name of the field to be set
      @param value - the value to be set
      @param tableIndexOnStack - the index of the table on stack
    */
    static void setFieldOnTable(struct lua_State * state,
                                const char * key,
				double value,
				int tableIndexOnStack);

    /**
      Sets a double on a specified lua table.

      @param state - the lua_State on which to perform the operations
      @param key - the name of the field to be set
      @param value - the value to be set
      @param tableIndexOnStack - the index of the table on stack
    */
    static void setFieldOnTable(struct lua_State * state,
                                string key,
				double value,
				int tableIndexOnStack){

      LuaUtils::setFieldOnTable(state, key.c_str(), value, tableIndexOnStack);
    }



    /**
      Write a value at a given position on the stack to a file

      @param state - the lua_State on which to perform the operations
      @param fileName - path to the file to be written
      @param stackIndex - Position in the stack that contains the value
      to be written
    */
    static void writeFile(struct lua_State * state,
                                    std::string fileName,
                                    int stackIndex);

    //FIXME: COMMENT ME!!!!
    static void openFileForRead(struct lua_State * state,
                         std::string fileName);

    static void openFileForWrite(struct lua_State * state,
                         std::string fileName);


    //FIXME: COMMENT ME!!!!
    static void closeDefaultFile(struct lua_State * state);

    //FIXME: COMMENT ME!!!!
    static void readFullFile(struct lua_State * state);

    //FIXME: COMMENT ME!!!!
    static void writeToFile(struct lua_State * state, int stackIndex);

    //FIXME: COMMENT ME!!!!
    static void printTable(struct lua_State * state, int stackIndex);

    //FIXME: COMMENT ME!!!!
    static vector<string> extractStringSequence(struct lua_State * state, int stackIndex);

    static vector<short> extractShortSequence(struct lua_State * state, int stackIndex);
};

#endif//LuaUtils_HPP
