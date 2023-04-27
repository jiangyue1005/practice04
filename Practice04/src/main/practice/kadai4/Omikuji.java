package kadai4;

/**
 * @author y_jiang omikujiクラス
 */
public abstract class Omikuji implements Fortune {

	/** 運勢 */
	protected String unsei;
	/** omikujiコード */
	protected String omikujicd;
	/** 運勢コード */
	protected String unseicd;
	/** 願い事 */
	protected String negaigoto;
	/** 商い */
	protected String akinai;
	/** 学問 */
	protected String gakumon;

	/**
	 * @return 占い結果
	 */
	public String result() {
		return "🔯" + "　" + String.format(DISP_STR, getUnsei()) + "🔯" + "　" + "," + getNegaigoto() + "," + getAkinai()
				+ "," + getGakumon();
	}

	/**
	 * @return unsei
	 */
	public String getUnsei() {
		return unsei;
	}

	/**
	 * @param unsei セットする unsei
	 */
	public abstract void setUnsei();

	/**
	 * @return omikujicd
	 */
	public String getOmikujicd() {
		return omikujicd;
	}

	/**
	 * @param omikujicd セットする omikujicd
	 */
	public void setOmikujicd(String omikujicd) {
		this.omikujicd = omikujicd;
	}

	/**
	 * @return unseicd
	 */
	public String getUnseicd() {
		return unseicd;
	}

	/**
	 * @param unseicd セットする unseicd
	 */
	public void setUnseicd(String unseicd) {
		this.unseicd = unseicd;
	}

	/**
	 * @return negaigoto
	 */
	public String getNegaigoto() {
		return negaigoto;
	}

	/**
	 * @param negaigoto セットする negaigoto
	 */
	public void setNegaigoto(String negaigoto) {
		this.negaigoto = negaigoto;
	}

	/**
	 * @return akinai
	 */
	public String getAkinai() {
		return akinai;
	}

	/**
	 * @param akinai セットする akinai
	 */
	public void setAkinai(String akinai) {
		this.akinai = akinai;
	}

	/**
	 * @return gakumon
	 */
	public String getGakumon() {
		return gakumon;
	}

	/**
	 * @param gakumon セットする gakumon
	 */
	public void setGakumon(String gakumon) {
		this.gakumon = gakumon;
	}

}
