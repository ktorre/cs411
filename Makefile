PROJECTNAME=project1

$(PROJECTNAME).lex.java : lexical/$(PROJECTNAME).lex
	@echo Generating java code...
	@java JLex.Main lexical/$(PROJECTNAME).lex
	@echo -n Renaming output file...
	@mv lexical/$(PROJECTNAME).lex.java lexical/$(PROJECTNAME).java
	@echo done
	@echo -n Compiling $(PROJECTNAME)...
	@javac lexical/$(PROJECTNAME).java
	@echo done


clean :
	@echo -n Cleaning...
	@rm -rf lexical/$(PROJECTNAME).java lexical/$(PROJECTNAME).class lexical/Yytoken.class lexical/Yylex.class
	@echo done
