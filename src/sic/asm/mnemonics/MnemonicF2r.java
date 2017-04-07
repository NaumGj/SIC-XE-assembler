package sic.asm.mnemonics;

import sic.asm.code.Node;
import sic.asm.code.instructions.InstructionF2r;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

public class MnemonicF2r extends Mnemonic {

	public MnemonicF2r(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
	}

	@Override
	public Node parse(Parser parser) throws SyntaxError {
		return new InstructionF2r(this, parser.parseRegister());
	}

}
