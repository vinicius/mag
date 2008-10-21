#include "LuaUtils.hpp"

#include<iostream>
#include<cstdlib>
#include<cassert>

using std::cerr;
using std::endl;

  //-----------------------------------------------------------------------------------
  void LuaUtils::getField(lua_State *state, const char *name) {
    const char *end = strchr(name, '.');
    lua_pushvalue(state, LUA_GLOBALSINDEX);
    while (end) {
      lua_pushlstring(state, name, end - name);
      lua_gettable(state, -2);
      lua_remove(state, -2);
      if (lua_isnil(state, -1)) return;
      name = end+1;
      end = strchr(name, '.');
    }
    lua_pushstring(state, name);
    lua_gettable(state, -2);
    lua_remove(state, -2);
  }

  //-----------------------------------------------------------------------
std::string LuaUtils::getStringFromTable(struct lua_State * state, const char*
key){
    lua_pushstring(state, key);
    lua_gettable(state, -2);
    std::string value( lua_tostring(state,-1),lua_strlen(state,-1));
    lua_pop(state, 1);
    return value;
  }


  //-----------------------------------------------------------------------------------
  unsigned long LuaUtils::getLongFromTable(struct lua_State * state, const char * key){

    lua_pushstring(state, key);
    lua_gettable(state, -2);
    unsigned long value = (unsigned long)lua_tonumber(state,-1);
    lua_pop(state, 1);
    return value;

  }

  //-----------------------------------------------------------------------------------
  int LuaUtils::getIntFromTable(struct lua_State * state, const char * key){

    lua_pushstring(state, key);
    lua_gettable(state, -2);
    int value = int(lua_tonumber(state,-1));
    lua_pop(state, 1);
    return value;

  }
  


  //------------------------------------------------------------------------------------
  int LuaUtils::convertStackIndex(struct lua_State * state, int stackIndex){
    int tmpIndex = stackIndex;
     if(tmpIndex < 0)
       tmpIndex = lua_gettop(state) + tmpIndex + 1;
    return tmpIndex;
  }



  //-----------------------------------------------------------------------------------
  void LuaUtils::setFieldOnTable(struct lua_State * state,
                                const char * key,
                                const char * value,
                                int tableIndexOnStack){

    //we need an absolute index here, so if negative, convert
    int tmpIndex = LuaUtils::convertStackIndex(state, tableIndexOnStack);
    lua_pushstring(state, key);
    lua_pushstring(state, value);
    lua_settable(state, tmpIndex);

  }

  //-----------------------------------------------------------------------------------
  void LuaUtils::setFieldOnTable(struct lua_State * state,
                                 const char * key,
                                 double value,
                                 int tableIndexOnStack){

    //we need an absolute index here, so if negative, convert
    int tmpIndex = LuaUtils::convertStackIndex(state, tableIndexOnStack);
    lua_pushstring(state, key);
    lua_pushnumber(state, value);
    lua_settable(state, tmpIndex);

  }

//-----------------------------------------------------------------------------------
  void LuaUtils::setFieldOnTable(struct lua_State * state,
                                string key,
                                string value,
                                int tableIndexOnStack){

    //we need an absolute index here, so if negative, convert
    int tmpIndex = LuaUtils::convertStackIndex(state, tableIndexOnStack);
    lua_pushlstring(state, key.c_str(),key.length());
    lua_pushlstring(state, value.c_str(),value.length());
    lua_settable(state, tmpIndex);

  }




  //-----------------------------------------------------------------------------------
  void LuaUtils::writeFile(struct lua_State * state,
                           std::string fileName,
                           int stackIndex){


     LuaUtils::openFileForWrite(state, fileName.c_str());
     LuaUtils::writeToFile(state, stackIndex);
     LuaUtils::closeDefaultFile(state);


  }

  //-----------------------------------------------------------------------------------
  void LuaUtils::openFileForRead(struct lua_State * state,
                         std::string fileName){

    //printf("openRead=%s\n", fileName.c_str());
     int stackSize = lua_gettop(state);

     lua_getglobal(state,"io");//IO table on stack
     lua_pushstring(state,"input");
     lua_gettable(state, -2);
     lua_pushstring(state, fileName.c_str());
     if (lua_pcall(state, 1, 0, 0) != 0){
       cerr << "[ERROR] LuaUtils::openFileForRead->Lua error: "
            << lua_tostring(state, -1) << endl;
       lua_pop(state, 1);
       exit(-1);
     }

     lua_pop(state, 1);
     assert(stackSize == lua_gettop(state));//This function must not alter the stack
  }

  //-----------------------------------------------------------------------------------
  void LuaUtils::openFileForWrite(struct lua_State * state,
                         std::string fileName){

    //printf("openWrite=%s\n", fileName.c_str());
     int stackSize = lua_gettop(state);
     lua_getglobal(state,"io");//IO table on stack
     lua_pushstring(state,"output");
     lua_gettable(state, -2);
     lua_pushstring(state, fileName.c_str());
     if (lua_pcall(state, 1, 0, 0) != 0){
       cerr << "[ERROR] LuaUtils::openFileForWrite->Lua error: "
            << lua_tostring(state, -1) << endl;
       lua_pop(state, 1);
       exit(-1);
     }

     lua_pop(state, 1);
     assert(stackSize == lua_gettop(state));//This function must not alter the stack
  }

  //-----------------------------------------------------------------------------------
  void LuaUtils::closeDefaultFile(struct lua_State * state){

    int stackSize = lua_gettop(state);
    lua_getglobal(state,"io");//IO table on stack
    lua_pushstring(state,"close");
    lua_gettable(state, -2);
    if (lua_pcall(state, 0, 1, 0) != 0){
       cerr << "[ERROR] LuaUtils::closeDefaultFile->Lua error: "
            << lua_tostring(state, -1) << endl;
       lua_pop(state, 1);
       exit(-1);
    }

    lua_pop(state, 2);
    assert(stackSize == lua_gettop(state));//This function must not alter the stack
  }

  //-----------------------------------------------------------------------------------
  void LuaUtils::readFullFile(struct lua_State * state){

    int stackSize = lua_gettop(state);
    lua_getglobal(state,"io");//IO table on stack
    lua_pushstring(state,"read");
    lua_gettable(state, -2);
    lua_pushstring(state,"*a");

    if (lua_pcall(state, 1, 1, 0) != 0){
      cerr << "[ERROR] LuaUtils::readFullFile->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
      exit(-1);
    }

    lua_remove(state, -2);//removes IO table
    assert(stackSize + 1 == lua_gettop(state));//IMPORTANT: LEAVES read file at stacktop!!!!!
  }

  //-----------------------------------------------------------------------------------
  void LuaUtils::writeToFile(struct lua_State * state, int stackIndex){

     int stackSize = lua_gettop(state);
     //We need an absolute stack index here, so if negative, convert
     int tmpIndex = LuaUtils::convertStackIndex(state, stackIndex);
     lua_getglobal(state,"io");//IO table on stack
     lua_pushstring(state,"write");
     lua_gettable(state, -2);
     lua_pushvalue(state, tmpIndex);

     if (lua_pcall(state, 1, 1, 0) != 0){
       cerr << "[ERROR] LuaUtils::writeToFile->Lua error: "
            << lua_tostring(state, -1) << endl;
       lua_pop(state, 1);
       exit(-1);
     }

     lua_pop(state, 2);
     assert(stackSize == lua_gettop(state));//This function must not alter the stack

  }

  //----------------------------------------------------------------------------------
  void LuaUtils::printTable(struct lua_State * state, int stackIndex){

    int stackSize = lua_gettop(state);
    //We need an absolute stack index here, so if negative, convert
    int tmpIndex = LuaUtils::convertStackIndex(state, stackIndex);
    lua_getglobal(state, "pr");
    lua_pushvalue(state, tmpIndex);

    if (lua_pcall(state, 1, 0, 0) != 0){
      cerr << "[ERROR] LuaUtils::printTable->Lua error: "
           << lua_tostring(state, -1) << endl;
      lua_pop(state, 1);
    }

    assert(stackSize == lua_gettop(state));//This function must not alter the stack

  }

  //----------------------------------------------------------------------------------
  vector<string> LuaUtils::extractStringSequence(struct lua_State * state, int stackIndex){
    
    int stackSize = lua_gettop(state);
    int tmpIndex = LuaUtils::convertStackIndex(state, stackIndex);
    vector<string> stringSeq;
    lua_pushstring(state, "n");
    lua_gettable(state, tmpIndex);
    int numStrings = int(lua_tonumber(state, -1));
    lua_pop(state, 1);
    for(int i = 1; i <= numStrings; i++){
      lua_rawgeti(state, -1, i);
      stringSeq.push_back(lua_tostring (state, -1));
      lua_pop(state, 1);
    }

    assert(stackSize == lua_gettop(state));//This function must not alter the stack
    return stringSeq;

  }

  //---------------------------------------------------------------------------------------  
  vector<short> LuaUtils::extractShortSequence(struct lua_State * state, int stackIndex){
    int stackSize = lua_gettop(state);
    int tmpIndex = LuaUtils::convertStackIndex(state, stackIndex);
    vector<short> shortSeq;
    lua_pushstring(state, "n");
    lua_gettable(state, tmpIndex);
    int numStrings = int(lua_tonumber(state, -1));
    lua_pop(state, 1);
    for(int i = 1; i <= numStrings; i++){
      lua_rawgeti(state, -1, i);
      shortSeq.push_back((short)lua_tonumber(state, -1));
      lua_pop(state, 1);
    }

    assert(stackSize == lua_gettop(state));//This function must not alter the stack
    return shortSeq;

  }
 
  //----------------------------------------------------------------------------------
