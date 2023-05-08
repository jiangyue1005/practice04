package kadai4;

import java.util.ResourceBundle;

/**
 * 運勢インターフェース
 * 
 * @author y_jiang
 */
public interface Fortune {
	// リソースバンドルを指定する
	ResourceBundle rb = ResourceBundle.getBundle("fortune");
	String DISP_STR = rb.getString("disp_str");

	String disp();

}
