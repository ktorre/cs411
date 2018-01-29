PROJECTNAME=project1

$(PROJECTNAME).java : $(PROJECTNAME).lex
	@echo Generating java code...
	@java JLex.Main $(PROJECTNAME).lex
	@echo -n Renaming output file...
	@mv $(PROJECTNAME).lex.java $(PROJECTNAME).java
	@echo done
	@echo -n Compiling $(PROJECTNAME)...
	@javac $(PROJECTNAME).java
	@echo done


clean :
	@echo -n Cleaning...
	@rm -rf $(PROJECTNAME).java $(PROJECTNAME).class Yytoken.class Yylex.class Trie.class
	@echo done
