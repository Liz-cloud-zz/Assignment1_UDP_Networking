#This is a makefile
JAVAC=	/usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOCDIR=doc
OUTDIR=out

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES = Client.class Connector.class Server.class

CLASS_FILES = $(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

run1:
	java -cp bin Client

run2:
	java -cp bin Server

run3:
	java -cp bin Client

test:
	./scripts/CheckThreads.sh

docs:
	javadoc -cp $(BINDIR) -d $(DOCDIR) $(SRCDIR)/*.java

cleandocs:
	rm -rf $(DOCDIR)/*