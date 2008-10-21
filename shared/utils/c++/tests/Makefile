TARGET = ConfigTester TokenizerTester

CPPCONFIG = ConfigTester.cpp       \
                 ../StringTokenizer.cpp \
                 ../Config.cpp

CPPTOKENIZER = TokenizerTester.cpp    \
                 ../StringTokenizer.cpp \

OBJCONFIG = $(CPPCONFIG:.cpp=.o)
OBJTOKENIZER = $(CPPTOKENIZER:.cpp=.o)

CPPDEPCONFIG = $(patsubst %.cpp, %.d, $(CPPCONFIG))
CPPDEPTOKENIZER = $(patsubst %.cpp, %.d, $(CPPTOKENIZER))

INCDIR = -I../
CXX = g++-3.3
DEBUGFLAGS =  -g -O0
WARN_FLAGS = -Wall -ansi -pedantic
CPPFLAGS = -O2
LDFLAGS = -lm -llualib -llua -lpthread


all: $(TARGET)

include $(CPPDEPCONFIG)
include $(CPPDEPTOKENIZER)

%.d: %.cpp
	$(SHELL) -ec '$(CXX) $(INCDIR) $(WARN_FLAGS) $(CPPFLAGS) -MM  $< | sed '\''s#$*\.o#& $@#g'\'' > $@'


%.o: %.cpp %.d
	$(CXX) $(INCDIR) $(WARN_FLAGS) -c  -O2  $(DEBUGFLAGS)  -o $@ $<

ConfigTester: $(OBJCONFIG)
	$(CXX) $(INCDIR) $(WARN_FLAGS) $(CPPFLAGS) $(DEBUGFLAGS) -o $@ $+ $(LDFLAGS)

TokenizerTester: $(OBJTOKENIZER)
	$(CXX) $(INCDIR) $(WARN_FLAGS) $(CPPFLAGS) $(DEBUGFLAGS) -o $@ $+ $(LDFLAGS)

clean:
	rm -f *~ $(CPPDEPCONFIG) $(CPPDEPTOKENIZER) $(OBJCONFIG) $(OBJTOKENIZER) $(TARGET)


