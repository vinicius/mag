TARGET = ArscTester

UTILSPATH=../../utils/c++
SHAREDPATH=../../shared
DEBUG=true

##########################################################
# Source Files                                           #
##########################################################

CPPFILES = src/ArscImpl.cpp          \
           src/ArscTester.cpp           \
           src/ArscSkeleton.cpp \
           src/ArsmStub.cpp \
	   src/MessagePacket.cpp \
	   $(UTILSPATH)/CharArrayArrayBeautifier.cpp \
	   $(UTILSPATH)/StringTokenizer.cpp \
           $(UTILSPATH)/Config.cpp          \
           $(UTILSPATH)/LuaUtils.cpp \
           $(UTILSPATH)/OrbUtils.cpp  \
	   $(UTILSPATH)/NetInfo.cpp \
	   $(UTILSPATH)/Condition.cpp \
           $(UTILSPATH)/NameServiceStub.cpp



include Makefile.vars

##########################################################
# Compiler Flags                                         #
##########################################################

CXX = g++-3.3
INCDIR = -I../../ -idirafter $(LUAINCDIR) 
ifneq ($(DEBUG), false)
DEBUGFLAGS =  -g -O0 -fno-inline
endif
WARN_FLAGS = -Wall -pedantic -ansi
CPPFLAGS = -pthread -O2  -fPIC  
LDFLAGS = -L$(LUALIBDIR) -Wl,-rpath,$(LUALIBDIR) -lm -llualib -llua -loilall -lluasocket -lpthread -L/usr/local/lib -lkrb5 -lk5crypto -lcom_err -lgssapi_krb5 -lcrypto++



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

../../utils/c++/objects/%.o: ../../utils/c++/%.cpp
	$(CXX) $(INCDIR) $(WARN_FLAGS) -c $(CPPFLAGS) $(DEBUGFLAGS)  -o $@ $<
	$(CXX) $(INCDIR) $(WARN_FLAGS) $(CPPFLAGS) -MM  $< > $(call transfname, $@)
	@mv -f $(call transfname, $@) $(call transfname, $@).tmp
	@sed -e 's|.*:|$@:|' < $(call transfname, $@).tmp > $(call transfname, $@)
	@sed -e 's/.*://' -e 's/\\$$//' < $(call transfname, $@).tmp | fmt -1 | sed -e 's/^ *//' -e 's/$$/:/' >> $(call transfname, $@)
	@rm -f $(call transfname, $@).tmp

$(SHAREDPATH)/objects/%.o: $(SHAREDPATH)/%.cpp
	$(CXX) $(INCDIR) $(WARN_FLAGS) -c $(CPPFLAGS) $(DEBUGFLAGS)  -o $@ $<
	$(CXX) $(INCDIR) $(WARN_FLAGS) $(CPPFLAGS) -MM  $< > $(call transfname, $@)
	@mv -f $(call transfname, $@) $(call transfname, $@).tmp
	@sed -e 's|.*:|$@:|' < $(call transfname, $@).tmp > $(call transfname, $@)
	@sed -e 's/.*://' -e 's/\\$$//' < $(call transfname, $@).tmp | fmt -1 | sed -e 's/^ *//' -e 's/$$/:/' >> $(call transfname, $@)
	@rm -f $(call transfname, $@).tmp

$(TARGET): $(OBJFILES)
	$(CXX) $(INCDIR) $(WARN_FLAGS) $(CPPFLAGS) $(DEBUGFLAGS) -o $@ $+ $(LDFLAGS)
ifeq ($(DEBUG), false)
	strip -s $(TARGET)
endif

clean:
	rm -fr $(DIRDEPS) $(DIROBJS) $(TARGET)
