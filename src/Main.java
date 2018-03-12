import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.dom.*;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String filePath = "testFile.java";		// input file path	
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		
		char[] fileContent = getFileContent(filePath).toCharArray();
		parser.setSource(fileContent);		
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit cu = (CompilationUnit)parser.createAST(null);
		cu.accept(new ASTVisitor(){
			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name = node.getName();
				int lineNumber = cu.getLineNumber(name.getStartPosition());
				
				System.out.println("Name: " + name.toString());
				System.out.println("Line: " + lineNumber);
				System.out.println("------------------------------------------");
				return false;
			}
		});
	}
	
	public static String getFileContent(String filePath) throws FileNotFoundException, IOException{
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		return sb.toString();
	}
}
