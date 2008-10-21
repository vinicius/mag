TARGET1 = testCompiler
TARGET2 = testScope

CKP_DIR =../checkpointing

CPPFILES = src/DataConverters.cpp \
           src/DataConverter_x86_x8664.cpp \
           src/DataConverter_x86_PPCG4.cpp \
           src/DataConverter_x8664_x86.cpp \
           src/DataConverter_PPCG4_x86.cpp

##########################################################
# Compiler Flags                                         #
##########################################################

CXX = g++
INCDIR = -Iinclude -I$(CKP_DIR)/include
ifeq ($(DEBUG), true)
DEBUGFLAGS =  -g3 -O0 -fno-inline
endif
WARN_FLAGS = -Wall -ansi -pedantic
CPPFLAGS = -pthread -fPIC -O2

##########################################################
# Generating modified paths for objects and dependencies #
##########################################################

DIRLIST      = $(dir $(CPPFILES))
DIRDEPSLIST  = $(addsuffix dependencies/, $(DIRLIST))
DIROBJSLIST  = $(addsuffix objects/, $(DIRLIST))
FILELIST     = $(notdir $(CPPFILES))
OBJFILESLIST = $(join $(DIROBJSLIST), $(FILELIST))
DEPFILESLIST = $(join $(DIRDEPSLIST), $(FILELIST))
DIRDEPS      = $(sort $(DIRDEPSLIST)) #Removes duplicate dirs
DIROBJS      = $(sort $(DIROBJSLIST)) #Removes duplicate dirs
OBJFILES     = $(OBJFILESLIST:.cpp=.o)
CPPDEPENDS   = $(DEPFILESLIST:.cpp=.d)

transfname = $(patsubst %.o, %.d, $(subst objects/,dependencies/,$(1) ) )

all: first libconverter.a libconverter.so

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

libconverter.a: $(OBJFILES)
	ar rcs lib/$@ $+

libconverter.so: $(OBJFILES)
	$(CXX) -shared -fPIC -o lib/$@ $+

clean:
	rm -rf lib $(DIRDEPS) $(DIROBJS)

