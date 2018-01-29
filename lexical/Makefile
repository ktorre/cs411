PROJECTNAME=project1
TESTFILE=toy.txt

$(PROJECTNAME).java : $(PROJECTNAME).lex Trie.java
	@echo Generating java code...
	@java JLex.Main $(PROJECTNAME).lex
	@echo -n Renaming output file...
	@mv $(PROJECTNAME).lex.java $(PROJECTNAME).java
	@echo done
	@echo -n Compiling $(PROJECTNAME)...
	@javac $(PROJECTNAME).java
	@echo done

run :
	@make --no-print-directory
	@echo Running $(PROJECTNAME)...
	@echo
	@java $(PROJECTNAME) $(TESTFILE)

clean :
	@echo -n Cleaning...
	@rm -rf $(PROJECTNAME).java $(PROJECTNAME).class Yytoken.class Yylex.class Trie.class
	@echo done
