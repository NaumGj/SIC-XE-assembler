package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Flags;
import sic.asm.code.Node;
import sic.asm.code.instructions.InstructionF4m;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

public class MnemonicF4m extends Mnemonic {

	public MnemonicF4m(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
	}

	@Override
	public Node parse(Parser parser) throws SyntaxError {
		int value = 0;
		String symbol = null;
		Flags flags = new Flags();
		if (parser.lexer.advanceIf('#')) {
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
		
		flags.setE(true);
		return new InstructionF4m(this, flags, value, symbol);
	}

}
