PROJECTNAME=project1
TRIEFILE=Trie
TESTFILE=toy.txt

main : $(PROJECTNAME).java $(TRIEFILE).class
	@echo 

$(PROJECTNAME).java : $(PROJECTNAME).lex
	@echo Generating java code...
	@java JLex.Main $(PROJECTNAME).lex
	@echo -n Renaming output file...
	@mv $(PROJECTNAME).lex.java $(PROJECTNAME).java
	@echo done
	@echo -n Compiling $(PROJECTNAME)...
	@javac $(PROJECTNAME).java
	@echo done

$(TRIEFILE).class : $(TRIEFILE).java
	@echo -n Compiling $(TRIEFILE)...
	@javac $(TRIEFILE).java
	@echo done

run :
	@make --no-print-directory
	@echo Running $(PROJECTNAME)...
	@echo
	@java $(PROJECTNAME) $(TESTFILE)

clean :
	@echo -n Cleaning...
	@rm -rf $(PROJECTNAME).java $(PROJECTNAME).class Yytoken.class Yylex.class $(TRIEFILE).class
	@echo done
