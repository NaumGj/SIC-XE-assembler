package sic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import sic.asm.code.Code;
import sic.asm.code.SemanticError;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

/**
 * Podporni razred za predmet Sistemska programska oprema.
 * @author jure
 */
public class Asm {

	public static String readFile(File file) {
	    byte[] buf = new byte[(int) file.length()];
	    try {
	    	InputStream s = new FileInputStream(file);
	    	try {
	    		s.read(buf);
			} finally {
	    		s.close();
	    	}
    	} catch (IOException e) {
    		return "";
	    }
	    return new String(buf);
	}


	public static void main(String[] args) {
		String filename = "literal.asm";
		String input;

		// TODO
		input = readFile(new File(filename));
//		System.out.println(input);
//		input = "    START 42\n    END zacetek";

		Parser parser = new Parser();
		Code code;
		try {
			code = parser.parse(input);
			code.begin();
//			code.print();
			code.setEnd(code.getLocCtr());
			code.end();
		} catch (SyntaxError e) {
			System.err.println(e);
			System.exit(1);
			return;
		} catch (SemanticError e) {
			System.err.println(e);
			System.exit(1);
			return;
		}
		
		code.begin();
		try{
			code.resolve();
//			System.out.println("ALO");
//			System.out.println(code.getLocCtr());
//			System.out.println(code.getSymbolTable().toString());
		} catch (SemanticError e) {
			System.err.println(e);
			System.exit(1);
			return;
		}
		code.end();
		
		code.begin();
		System.out.println(code.emitRawText());
		code.end();
		
		RandomAccessFile obj = null;
		RandomAccessFile lst = null;
		String noSufix = filename.substring(0, filename.indexOf('.'));
		try {
			obj = new RandomAccessFile(noSufix + "2.obj", "rw");
			lst = new RandomAccessFile(noSufix + "2.lst", "rw");
		} catch (FileNotFoundException e) {
			System.err.println("Datoteki ne moreta biti ustvarjeni.");
		}
		
		code.begin();
		try {
			obj.writeChars(code.emitObjCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
		code.end();
		
		code.begin();
		try {
			lst.writeChars(code.emitLstText());
		} catch (IOException e) {
			e.printStackTrace();
		}
		code.end();
	}

}
