package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Node;
import sic.asm.code.instructions.InstructionF2n;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

public class MnemonicF2n extends Mnemonic {

	public MnemonicF2n(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
	}

	@Override
	public Node parse(Parser parser) throws SyntaxError {
		return new InstructionF2n(this, parser.parseNumber(0, Code.MAX_WORD));
	}

}
