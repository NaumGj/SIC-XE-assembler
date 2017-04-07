package sic.asm.code.directives;

import sic.asm.code.Code;
import sic.asm.code.Node;
import sic.asm.mnemonics.Mnemonic;

public class DirectiveLtOrg extends Node {

	public DirectiveLtOrg(Mnemonic mnemonic) {
		super(mnemonic);
	}
	
	@Override
	public String toString() {
		return (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString();
	}
	
	@Override
    public void enter(Code code) {
		super.enter(code);
		for (DirectiveStorageData instruction : code.getLiterals()) {
			code.append(instruction);
			code.defineSymbol(instruction.getLabel(), code.getLocCtr());
		}
		code.getLiterals().clear();
    }

}
