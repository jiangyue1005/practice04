package kadai4;

public abstract class Omikuji implements Fortune{
	
	protected String omikujicd;
	protected String unseicd;
	protected String unsei;
	protected String negaigoto;
	protected String akinai;
	protected String gakumon;
	
	public abstract void setUnsei();

//	public String getBirthday() {
//		return birthday;
//	}
//
//	public void setBirthday(String birthday) {
//		this.birthday = birthday;
//
//	}
	
	public String disp() {
		return String.format(DISP_STR, getUnsei());

	}

	public String getUnsei() {
		return unsei;
	}

	public String getOmikujicd() {
		return omikujicd;
	}

	public void setOmikujicd(String string) {
		this.omikujicd = string;
	}

	public String getUnseicd() {
		return unseicd;
	}

	public void setUnseicd(String unseicd) {
		this.unseicd = unseicd;
	}

	public String getNegaigoto() {
		return negaigoto;
	}

	public void setNegaigoto(String negaigoto) {
		this.negaigoto = negaigoto;
	}

	public String getAkinai() {
		return akinai;
	}

	public void setAkinai(String akinai) {
		this.akinai = akinai;
	}

	public String getGakumon() {
		return gakumon;
	}

	public void setGakumon(String gakumon) {
		this.gakumon = gakumon;
	}


}
