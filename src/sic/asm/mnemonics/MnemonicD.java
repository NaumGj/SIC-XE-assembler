package sic.asm.mnemonics;

import sic.asm.code.Node;
import sic.asm.code.Opcode;
import sic.asm.code.directives.DirectiveLtOrg;
import sic.asm.code.directives.DirectiveNoBase;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;


/**
 * Directive without operands.
 * Podporni razred za predmet Sistemska programska oprema.
 * @author jure
 */
public class MnemonicD extends Mnemonic {
	
	private int opcode;

	public MnemonicD(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
		this.opcode = opcode;
	}

	@Override
	public Node parse(Parser parser) throws SyntaxError {
		if(opcode == Opcode.NOBASE) {
			return new DirectiveNoBase(this);
		} else if(opcode == Opcode.LTORG) {
			return new DirectiveLtOrg(this);
		} else {
			throw new SyntaxError(String.format("Invalid opcode %d", opcode), parser.lexer.row, parser.lexer.col);
		}
	}

}
