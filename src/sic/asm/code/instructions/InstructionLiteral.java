package sic.asm.code.instructions;

import sic.asm.code.Code;
import sic.asm.code.Flags;
import sic.asm.code.Node;
import sic.asm.code.Reloc;
import sic.asm.code.SemanticError;
import sic.asm.code.directives.DirectiveStorageData;
import sic.asm.mnemonics.Mnemonic;

public class InstructionLiteral extends Node {

	private String label;
	private byte[] value;
	private DirectiveStorageData litData;
	private InstructionF3m instruction;

	public InstructionLiteral(String label, Mnemonic mnemonic, byte[] value, InstructionF3m instruction) {
		super(mnemonic);
		this.label = label;
		this.value = value;
		this.instruction = instruction;
	}
	
	@Override
	public String toString() {
		return this.getLabel() + "\t" + mnemonic.toString();
	}
	
//	@Override
//	public String toString() {
//		return litData.toString();
//	}
	
	@Override
	public int length() {
		return litData.length();
	}
	
	@Override
	public DirectiveStorageData toDataDirective() {
		litData = new DirectiveStorageData(mnemonic, value);
		litData.setLabel(label);
		return litData;
	}
	
	@Override
	public InstructionF3m getInstruction() {
		return instruction;
	}
	
}
