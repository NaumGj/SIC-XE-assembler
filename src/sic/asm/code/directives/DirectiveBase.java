package sic.asm.code.directives;

import sic.asm.code.Code;
import sic.asm.code.Node;
import sic.asm.code.SemanticError;
import sic.asm.mnemonics.Mnemonic;

public class DirectiveBase extends Node {
	
	private int value;
	private String symbol;
	private int resolved;

	public DirectiveBase(Mnemonic mnemonic, int value) {
		super(mnemonic);
		this.value = value;
	}
	
	public DirectiveBase(Mnemonic mnemonic, String symbol) {
		super(mnemonic);
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + ((symbol == null) ? Integer.toString(value) : symbol);
	}
	
	@Override
	public void enter(Code code) {
		code.setBaseReg(resolved);
	}
	
	@Override
	public void resolve(Code code) throws SemanticError{
		if(symbol == null) {
			resolved = value;
		} else {
			resolved = code.resolveSymbol(symbol);
		}
	}

}
