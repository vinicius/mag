TARGET = CkpRepositoryLauncher

##########################################################
# Source Files                                           #
##########################################################

UTILSPATH=../../shared/utils/c++

CPPFILES = src/CRDataServer.cpp \
           src/CkpRepositoryUpdater.cpp \
           $(UTILSPATH)/CkpReposManagerStub.cpp \
           $(UTILSPATH)/Config.cpp \
           $(UTILSPATH)/StringTokenizer.cpp \
           $(UTILSPATH)/LuaUtils.cpp \
           $(UTILSPATH)/FileUtils.cpp \
           $(UTILSPATH)/OrbUtils.cpp \
           $(UTILSPATH)/NameServiceStub.cpp
           
include Makefile.vars

##########################################################
# Compiler Flags                                         #
##########################################################

CXX = g++-3.3
INCDIR = -I../../shared -I$(LUAINCDIR)
#ifeq ($(DEBUG), true)
DEBUGFLAGS =  -g3 -O0 -fno-inline
#endif
WARN_FLAGS = -Wall -pedantic -ansi
CPPFLAGS = -pthread -O2 -fPIC
LDFLAGS = -L$(LUALIBDIR) -Wl,-rpath,$(LUALIBDIR) -lm -llualib -llua -loilall -lluasocket  -lpthread

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

all: first $(TARGET)

-include $(CPPDEPENDS)

first:
	@mkdir -p $(DIRDEPS) $(DIROBJS)

src/objects/%.o: src/%.cpp
	$(CXX) $(INCDIR) $(WARN_FLAGS) -c $(CPPFLAGS) $(DEBUGFLAGS)  -o $@ $<
	$(CXX) $(INCDIR) $(WARN_FLAGS) $(CPPFLAGS) -MM  $< > $(call transfname, $@)
	@mv -f $(call transfname, $@) $(call transfname, $@).tmp
	@sed -e 's|.*:|$@:|' < $(call transfname, $@).tmp > $(call transfname, $@)
	@sed -e 's/.*://' -e 's/\\$$//' < $(call transfname, $@).tmp | fmt -1 | sed -e 's/^ *//' -e 's/$$/:/' >> $(call transfname, $@)
	@rm -f $(call transfname, $@).tmp

$(UTILSPATH)/objects/%.o: $(UTILSPATH)/%.cpp
	$(CXX) $(INCDIR) $(WARN_FLAGS) -c $(CPPFLAGS) $(DEBUGFLAGS)  -o $@ $<
	$(CXX) $(INCDIR) $(WARN_FLAGS) $(CPPFLAGS) -MM  $< > $(call transfname, $@)
	@mv -f $(call transfname, $@) $(call transfname, $@).tmp
	@sed -e 's|.*:|$@:|' < $(call transfname, $@).tmp > $(call transfname, $@)
	@sed -e 's/.*://' -e 's/\\$$//' < $(call transfname, $@).tmp | fmt -1 | sed -e 's/^ *//' -e 's/$$/:/' >> $(call transfname, $@)
	@rm -f $(call transfname, $@).tmp

$(TARGET): src/$(TARGET).cpp $(OBJFILES) 
	$(CXX) $(INCDIR) $(WARN_FLAGS) $(CPPFLAGS) $(DEBUGFLAGS) -o $@ $+ $(LDFLAGS)
ifeq ($(DEBUG), false)
	strip -s $(TARGET)
endif

clean:
	rm -rf lib $(DIRDEPS) $(DIROBJS) $(TARGET)
