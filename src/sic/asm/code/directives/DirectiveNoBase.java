package sic.asm.code.directives;

import sic.asm.code.Code;
import sic.asm.code.Node;
import sic.asm.mnemonics.Mnemonic;

public class DirectiveNoBase extends Node {

	public DirectiveNoBase(Mnemonic mnemonic) {
		super(mnemonic);
	}
	
	@Override
	public String toString() {
		return (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString();
	}
	
	@Override
	public void enter(Code code) {
		code.setBaseReg(-1);
	}
	
}
