package sic.asm.code;

public class Reloc {
	
	private int ctr;
	private int nibbles;
	
	public Reloc(int ctr, int nibbles) {
		this.ctr = ctr;
		this.nibbles = nibbles;
	}
	
	public int getctr() {
		return ctr;
	}

	public void setctr(int ctr) {
		this.ctr = ctr;
	}

	public int getnibbles() {
		return nibbles;
	}

	public void setnibbles(int nibbles) {
		this.nibbles = nibbles;
	}
	
}
