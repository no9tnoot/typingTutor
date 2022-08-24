JAVAC=C:\Java\jdk-16\bin\javac.exe
#JAVAC=%JAVA_HOME%\javac.exe
.SUFFIXES: .java .class
SRCDIR=src\typingTutor
BINDIR=bin

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<
	
CLASSES=WordDictionary.class \
		FallingWord.class \
		Score.class \
		CatchWord.class \
		GamePanel.class \
		ScoreUpdater.class \
		WordMover.class \
		TypingTutorApp.class
		
		

                 
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class
	