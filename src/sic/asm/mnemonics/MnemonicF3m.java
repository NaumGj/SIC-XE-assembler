package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Flags;
import sic.asm.code.Node;
import sic.asm.code.instructions.InstructionF3m;
import sic.asm.code.instructions.InstructionLiteral;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

public class MnemonicF3m extends Mnemonic {
	
	public MnemonicF3m(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
	}

	@Override
	public Node parse(Parser parser) throws SyntaxError {
		int value = 0;
		String symbol = null;
		Flags flags = new Flags();
		if (parser.lexer.advanceIf('=')) {
			Mnemonic litMnemonic = null;
			if(parser.lexer.advanceIf('B') || parser.lexer.advanceIf("BYTE")) {
				litMnemonic = parser.mnemonics.get("BYTE");
			} else {
				litMnemonic = parser.mnemonics.get("WORD");
				parser.lexer.advanceIf("WORD");
				parser.lexer.advanceIf('W');
			}				
//			System.out.println(litMnemonic);
			parser.lexer.skipWhitespace();
			Code.litCtr++;
			String label = "lit" + Integer.toString(Code.litCtr);
//			System.out.println(litMnemonic.toString());
			int valInt = parser.parseNumber(0, Code.MAX_ADDR);
			byte[] val = new byte[3];
			val[0] = (byte)((valInt >> 16) & 0xF);
			val[1] = (byte)((valInt >> 8) & 0xF);
			val[2] = (byte)(valInt & 0xF);
			flags.setN(true);
			flags.setI(true);
			return new InstructionLiteral(label, litMnemonic, val, new InstructionF3m(this, flags, 0, label));
		} else if (parser.lexer.advanceIf('#')) {
			flags.setI(true);
		} else if (parser.lexer.advanceIf('@')) {
			flags.setN(true);
		} else {
			flags.setN(true);
			flags.setI(true);
		}
		
		char next = parser.lexer.peek();
		if(next == '-' || Character.isDigit(next)) {
			value = parser.parseNumber(0, Code.MAX_WORD);
		} else if (Character.isLetter(next)) {
			symbol = parser.parseSymbol();
		} else if (next == '*') {
			symbol = Character.toString('*');
		} else {
			throw new SyntaxError(String.format("Invalid char %c", next), parser.lexer.row, parser.lexer.col);
		}
		parser.lexer.skipWhitespace();
		if(parser.lexer.advanceIf(',')) {
			parser.lexer.skipWhitespace();
			if(parser.lexer.advanceIf('X')) {
				flags.setX(true);
			} else {
				throw new SyntaxError("Index register X expected", parser.lexer.row, parser.lexer.col);
			}
		}
		
		return new InstructionF3m(this, flags, value, symbol);
	}

}
