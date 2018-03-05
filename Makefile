PROJECT1NAME=project1
PROJECT2NAME=project2
TRIEFILE=Trie
TESTFILE=toy.txt
PARSERNAME=Parser
CUPJAR=java-cup-11b.jar
CUPJARRUNTIME=java-cup-11b-runtime.jar
PROJECT1CLEANUPFILES=$(PROJECT1NAME).java $(PROJECT1NAME).class Yytoken.class Yylex.class $(TRIEFILE).class
PROJECT2CLEANUPFILES=$(PARSERNAME).java sym.java

main : $(PROJECT1NAME).java $(TRIEFILE).class $(PARSERNAME).java
	@echo 

$(PROJECT1NAME).java : $(PROJECT1NAME).lex
	@echo Generating java code...
	@java JLex.Main $(PROJECT1NAME).lex
	@echo -n Renaming output file...
	@mv $(PROJECT1NAME).lex.java $(PROJECT1NAME).java
	@echo done
	@echo -n Compiling $(PROJECT1NAME)...
	@javac $(PROJECT1NAME).java
	@echo done

$(TRIEFILE).class : $(TRIEFILE).java
	@echo -n Compiling $(TRIEFILE)...
	@javac $(TRIEFILE).java
	@echo done

$(PARSERNAME).java : $(PROJECT2NAME).cup
	@echo -n Generating parser...
	@java -jar $(CUPJAR) -interface -parser $(PARSERNAME) $(PROJECT2NAME).cup
	@echo done

run :
	@make --no-print-directory
	@echo Running $(PROJECT1NAME)...
	@echo
	@java $(PROJECT1NAME) $(TESTFILE)

clean :
	@echo -n Cleaning lexical analyzer files...
	@rm -rf $(PROJECT1CLEANUPFILES)
	@echo done
	@echo -n Cleaning syntax analyzer files...
	@rm -rf $(PROJECT2CLEANUPFILES)
	@echo done
