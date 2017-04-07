package sic.asm.code;

public class Flags {

	private boolean n;
	private boolean i;
	private boolean x;
	private boolean b;
	private boolean p;
	private boolean e;
	
	public Flags() {
		n = i = x = b = p = e = false;
	}
	
	public Flags(int opcode, int op) {
		n = ((opcode >> 1) & 0x1) == 1;
		i = (opcode & 0x1) == 1;
		x = ((op >> 7) & 0x1) == 1;
		b = ((op >> 6) & 0x1) == 1;
		p = ((op >> 5) & 0x1) == 1;
		e = ((op >> 4) & 0x1) == 1;
	}
	
	public boolean isSic() {
		return !n && !i;
	}
	
	public boolean isSimple() {
		return n && i;
	}
	
	public boolean isRelative() {
		return b || p;
	}
	
	public boolean isF4() {
		return e;
	}
	
	public boolean isIndexed() {
		return x;
	}
	
	public boolean isBaseRel() {
		return b && !p;
	}
	
	public boolean isPCRel() {
		return !b && p;
	}
	
	public boolean isDirect() {
		return !b && !p;
	}
	
	public boolean isIndirect() {
		return n && !i;
	}
	
	public boolean isImmediate() {
		return !n && i;
	}

	public void setN(boolean n) {
		this.n = n;
	}

	public void setI(boolean i) {
		this.i = i;
	}

	public void setX(boolean x) {
		this.x = x;
	}

	public void setB(boolean b) {
		this.b = b;
	}

	public void setP(boolean p) {
		this.p = p;
	}

	public void setE(boolean e) {
		this.e = e;
	}

	public boolean isN() {
		return n;
	}

	public boolean isI() {
		return i;
	}

	public boolean isX() {
		return x;
	}

	public boolean isB() {
		return b;
	}

	public boolean isP() {
		return p;
	}

	public boolean isE() {
		return e;
	}
}
