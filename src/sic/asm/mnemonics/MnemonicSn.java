package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Node;
import sic.asm.code.Opcode;
import sic.asm.code.directives.DirectiveStorageRes;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

public class MnemonicSn extends Mnemonic {
	
	private int opcode;

	public MnemonicSn(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
		this.opcode = opcode;
	}

	@Override
	public Node parse(Parser parser) throws SyntaxError {
		if(opcode == Opcode.RESW || opcode == Opcode.RESB) {
			// number
			if (Character.isDigit(parser.lexer.peek()))
				return new DirectiveStorageRes(this, parser.parseNumber(0, Code.MAX_ADDR));
			// symbol
			else if (Character.isLetter(parser.lexer.peek()))
				return new DirectiveStorageRes(this, parser.parseSymbol());
			// otherwise: error
			else
				throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);
		}
		throw new SyntaxError(String.format("Invalid opcode %d", opcode), parser.lexer.row, parser.lexer.col);
	}

}
