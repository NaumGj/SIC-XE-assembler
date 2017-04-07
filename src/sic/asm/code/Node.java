package sic.asm.code;

import sic.asm.code.directives.DirectiveStorageData;
import sic.asm.code.directives.DirectiveStorageRes;
import sic.asm.code.instructions.InstructionF3m;
import sic.asm.mnemonics.Mnemonic;

/**
 * Abstract class Node.
 * Includes label, mnemonic and comment.
 * Podporni razred za predmet Sistemska programska oprema.
 * @author jure
 */
public abstract class Node {

	protected String label;
	protected Mnemonic mnemonic;
	protected String comment;

	public Node(Mnemonic mnemonic) {
		this.mnemonic = mnemonic;
	}
	
	public Node(String label, Mnemonic mnemonic, String comment) {
		this.label = label;
		this.mnemonic = mnemonic;
		this.comment = comment;
	}

	public String getLabel() {
		return label == null ? "" : label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Return comment as a string.
	 */
	public String getComment() {
		return comment == null ? "" : comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Return string representation of the node.
	 * Label and comment are not included.
	 */
	@Override
	public String toString() {
		return mnemonic.toString() + " " + operandToString();
	}

	public String operandToString() {
		return mnemonic.operandToString(this);
	}
	
	public int length() {
		return 0;
	}
	
	public void enter(Code code) {
		code.setLocCtr(code.getNextLocCtr());
		code.setNextLocCtr(code.getLocCtr() + this.length());
	}
	
	public void leave(Code code) {
		
	}
	
	public DirectiveStorageData toDataDirective() {return null;}
	
	public InstructionF3m getInstruction() {return null;}
	
	public void activate(Code code) {
		if(this.getLabel() != null && !this.getLabel().isEmpty()) {
			code.defineSymbol(this.getLabel(), code.getLocCtr());
		}
	}
	
	public void resolve(Code code) throws SemanticError{}
	
	public String getOperands() {
		return "";
	}
	
	public String emitRawText() {
		if (!(this instanceof DirectiveStorageRes)){
			StringBuilder b = new StringBuilder();
			byte[] code = this.emitCode();
	        for (int i = 0; i < code.length; i++) {
	            b.append(String.format("%02X", code[i]));
	        }
			return b.toString();
		}
		return "";
	}
	
	public String emitLstText(Code code) {
		if(this instanceof Comment) {
			return this.toString() + '\n';
		}
		StringBuilder builder =  new StringBuilder();
		String rawText = emitRawText();
		if(rawText.isEmpty() && this instanceof DirectiveStorageRes) {
			rawText = "000000";
		}
		builder.append(String.format("%05X\t%s\t%s\t%s\t", code.getLocCtr(), rawText, (label == null || label.isEmpty()) ? "\t" : label, mnemonic != null ? mnemonic.name : ""));
		String operands = getOperands();
		builder.append(String.format("%s\n", operands));
		return builder.toString();
	}
	
	public byte[] emitCode() {
		byte[] buf = new byte[this.length()];
		emitCode(buf, 0);
		return buf;
	}
	
	public void emitCode(byte[] buf, int loc) {}

}
