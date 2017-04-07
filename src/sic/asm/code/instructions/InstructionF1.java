package sic.asm.code.instructions;

import sic.asm.code.Node;
import sic.asm.mnemonics.Mnemonic;

/**
 * Podporni razred za predmet Sistemska programska oprema.
 * @author jure
 */
public class InstructionF1 extends Node {

	public InstructionF1(Mnemonic mnemonic) {
		super(mnemonic);
	}
	
	@Override
	public String toString() {
		return (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString();
	}
	
	@Override
	public int length() {
		return 1;
	}
	
	@Override
	public void emitCode(byte[] buf, int loc) {
		buf[loc] = (byte)(mnemonic.opcode);
	}


}
