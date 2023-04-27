package kadai4;

import java.util.ResourceBundle;

/**
 * @author y_jiang 運勢結果表示インターフェース
 */
public interface Fortune {

	ResourceBundle rb = ResourceBundle.getBundle("fortune");

	String DISP_STR = rb.getString("disp_str");

	String result();

}
