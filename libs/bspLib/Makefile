UTILS_DIR=../../shared/utils/c++
CKP_DIR=../checkpointing
CONVERTER_DIR=../dataConverters


CPPFILES = src/BspCentral.cpp       \
           src/BspProxyImpl.cpp     \
           src/BspProxyStubPool.cpp \
           src/BspLib.cpp           \
           src/DrmaManager.cpp      \
           src/BsmpManager.cpp      \
           src/ProcessZero.cpp      \
           src/RegularProcess.cpp   \
           src/ExecutionManagerStub.cpp \
           $(UTILS_DIR)/Condition.cpp \
           $(UTILS_DIR)/Config.cpp    \
           $(UTILS_DIR)/OrbUtils.cpp   \
           $(UTILS_DIR)/LuaUtils.cpp  \
           $(UTILS_DIR)/StringTokenizer.cpp \
		   $(UTILS_DIR)/NameServiceStub.cpp \
           $(UTILS_DIR)/NetInfo.cpp

include Makefile.vars

##########################################################
# Compiler Flags                                         #
##########################################################

INCDIR = -I../../shared -Iinclude -I$(CKP_DIR)/include -I$(CONVERTER_DIR)/include -I$(LUAINCDIR) 
CXX = g++-3.3
ifeq ($(DEBUG), true)
DEBUGFLAGS =  -g3 -O0 -fno-inline
endif
WARN_FLAGS = -Wall -ansi -pedantic
CPPFLAGS = -pthread -O2 -fPIC
LDFLAGS = -L$(LUALIBDIR) -Wl,-rpath,$(LUALIBDIR) -Wl,-rpath,$(LUALIBDIR) -llualib -llua -lm -loilall -lluasocket -lpthread 

##########################################################
# Generating modified paths for objects and dependencies #
##########################################################

DIRLIST = $(dir $(CPPFILES))
FILELIST = $(notdir $(CPPFILES))
DIRDEPSLIST = $(addsuffix dependencies/, $(DIRLIST))
DIROBJSLIST = $(addsuffix objects/, $(DIRLIST))
OBJFILESLIST = $(join $(DIROBJSLIST), $(FILELIST))
DEPFILESLIST = $(join $(DIRDEPSLIST), $(FILELIST))
DIRDEPS = $(sort $(DIRDEPSLIST)) #Removes duplicate dirs
DIROBJS = $(sort $(DIROBJSLIST)) #Removes duplicate dirs

OBJFILES = $(OBJFILESLIST:.cpp=.o)
CPPDEPENDS = $(DEPFILESLIST:.cpp=.d)
transfname = $(patsubst %.o, %.d, $(subst objects/,dependencies/,$(1) ) )

all: first libbsp.so #libbsp.a

-include $(CPPDEPENDS)

first:
	@mkdir -p lib $(DIRDEPS) $(DIROBJS)

src/objects/%.o: src/%.cpp
	$(CXX) $(INCDIR) $(WARN_FLAGS) -c $(CPPFLAGS) $(DEBUGFLAGS)  -o $@ $<
	$(CXX) $(INCDIR) $(WARN_FLAGS) $(CPPFLAGS) -MM  $< > $(call transfname, $@)
	@mv -f $(call transfname, $@) $(call transfname, $@).tmp
	@sed -e 's|.*:|$@:|' < $(call transfname, $@).tmp > $(call transfname, $@)
	@sed -e 's/.*://' -e 's/\\$$//' < $(call transfname, $@).tmp | fmt -1 | sed -e 's/^ *//' -e 's/$$/:/' >> $(call transfname, $@)
	@rm -f $(call transfname, $@).tmp

$(UTILS_DIR)/objects/%.o: $(UTILS_DIR)/%.cpp
	$(CXX) $(INCDIR) $(WARN_FLAGS) -c $(CPPFLAGS) $(DEBUGFLAGS)  -o $@ $<
	$(CXX) $(INCDIR) $(WARN_FLAGS) $(CPPFLAGS) -MM  $< > $(call transfname, $@)
	@mv -f $(call transfname, $@) $(call transfname, $@).tmp
	@sed -e 's|.*:|$@:|' < $(call transfname, $@).tmp > $(call transfname, $@)
	@sed -e 's/.*://' -e 's/\\$$//' < $(call transfname, $@).tmp | fmt -1 | sed -e 's/^ *//' -e 's/$$/:/' >> $(call transfname, $@)
	@rm -f $(call transfname, $@).tmp

libbsp.so: $(OBJFILES)
	$(CXX) $(INCDIR) $(WARN_FLAGS) $(CPPFLAGS) $(DEBUGFLAGS) -shared -o lib/$@ $+ $(CONVERTER_DIR)/lib/libconverter.a $(LDFLAGS)
ifeq ($(DEBUG), false)
	strip -s lib/$@
endif

libbsp.a: $(OBJFILES)
	ar rcs lib/$@ $+

clean:
	rm -rf lib $(DIRDEPS) $(DIROBJS)

