package sic.asm.mnemonics;

import sic.asm.code.Node;
import sic.asm.code.Opcode;
import sic.asm.code.directives.DirectiveStorageData;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

public class MnemonicSd extends Mnemonic {
	
	private int opcode;

	public MnemonicSd(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
		this.opcode = opcode;
	}
	
	@Override
	public Node parse(Parser parser) throws SyntaxError {
		if (opcode == Opcode.WORD || opcode == Opcode.BYTE){
			return new DirectiveStorageData(this, parser.parseData());
		}
		
		throw new SyntaxError(String.format("Invalid opcode %d", opcode), parser.lexer.row, parser.lexer.col);
	}
}
