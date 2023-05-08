package kadai4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 占いサーブレットクラス
 * 
 * @author y_jiang
 *
 */
public class UranaiServlet extends HttpServlet {

	/**
	 * post処理メソッド
	 * 
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * get処理メソッド
	 * 
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** データベースURL */
		final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
		/** ユーザー名 */
		final String USER = null;
		/** パスワード */
		final String PASS = null;

		/** omikujiテーブル件数を検索するSQL */
		final String SQL1 = "select COUNT( * ) from omikuji;";
		/** omikujiテーブルに挿入するSQL */
		final String SQL2 = "insert into omikuji (omikujicd,unseicd,negaigoto,akinai,gakumon,modifiedby,updatedate,createdby,createddate)values(nextval('omikujicd_seq'),?,?,?,?,?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP);";
		/** resultテーブルに挿入するSQL */
		final String SQL3 = "insert into result (uranaidate,birthday,omikujicd,modifiedby,updatedate,createdby,createddate)values(?,?,?,?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP);";
		/** おみくじコードが一致する内容を検索するSQL */
		final String SQL4 = "select * from omikuji where omikujicd = ?;";
		/** 占い日と誕生日が一致する内容を検索するSQL */
		final String SQL5 = "select * from result where uranaidate = ? and birthday = ?;";

		Connection con = null;
		HttpSession session = request.getSession();
		ErrMessage errMessage = new ErrMessage();

		// 結果画面
		String showResult = "uranaiResult.jsp";
		// 占い画面
		String showUranai = "uranai.jsp";

		// CSVファイル
		File file = new File("/Users/y_jiang/Documents/unseiNew.csv");
		FileReader fr = null;
		// omikujiクラスの初期化
		Omikuji omikuji = null;
		// ドライバの初期化
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ドライバの初期化が失敗しました。");
			errMessage.setErrMessage("予期せぬエラーが発生しました。");
			session.setAttribute("errMessage", errMessage);
			response.sendRedirect(showUranai);
			e.printStackTrace();
			return;
		}

		try {
			// DB接続
			con = DriverManager.getConnection(URL, USER, PASS);
			// omikujiテーブルにデータがすでに存在した場合、データの書き込みをスキップ。
			PreparedStatement omikujiCount = con.prepareStatement(SQL1);
			ResultSet rs = omikujiCount.executeQuery();

			int count = 0;
			// 件数取得
			while (rs.next()) {
				count = rs.getInt(1);
			}

			if (count == 0) {// nullの場合、insert実行
				PreparedStatement omkjInfo = con.prepareStatement(SQL2);
				fr = new FileReader(file);
				BufferedReader in = new BufferedReader(fr);
				String data;
				int i = 0;
				// CSVファイルを読み込み、DBに書き込み
				while ((data = in.readLine()) != null) {
					String[] arr = data.split(",");
					// 運勢コード
					omkjInfo.setString(1, arr[0]);
					// 願い事
					omkjInfo.setString(2, arr[1]);
					// 商い
					omkjInfo.setString(3, arr[2]);
					// 学問
					omkjInfo.setString(4, arr[3]);
					// 更新者
					omkjInfo.setString(5, "姜悦");
					// 作成者
					omkjInfo.setString(6, "姜悦");
					// SQL実行
					int j = omkjInfo.executeUpdate();
					count = j + i;
					i++;
				}
				fr.close();
			}

			// 画面から誕生日取得
			String key = request.getParameter("birthday");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			// フォーマット設定
			sdf.setLenient(false);
			sdf.parse(key);

			// データ有無判定
			boolean resultFlg = false;

			// 誕生日をDate型に
			Date birth = sdf.parse(key);
			long newBirth = birth.getTime();

			// 日付型変換
			Date today = new Date();
			long day = today.getTime();
			java.sql.Date uranaidate = new java.sql.Date(day);
			java.sql.Date birthday = new java.sql.Date(newBirth);

			// resultテーブルに同じデータが存在するかどうかを判定
			String omikujicd = null;
			PreparedStatement resultInfo = con.prepareStatement(SQL5);
			resultInfo.setDate(1, uranaidate);
			resultInfo.setDate(2, birthday);
			ResultSet resultRs = resultInfo.executeQuery();
			while (resultRs.next()) {
				resultFlg = true;
				// おみくじコード取得
				omikujicd = resultRs.getString("omikujicd");
			}
			if (!resultFlg) {
				// ランダムomikujicd生成
				Random random = new Random();
				omikujicd = Integer.toString(random.nextInt(count));
			}

			// omikujicdで結果を検索
			PreparedStatement omikujiInfo = con.prepareStatement(SQL4);
			omikujiInfo.setString(1, omikujicd);
			ResultSet omikujiRs = omikujiInfo.executeQuery();
			while (omikujiRs.next()) {
				switch (omikujiRs.getString("unseicd")) {
				case "1":
					omikuji = new Daikichi();
					break;
				case "2":
					omikuji = new Chukichi();
					break;
				case "3":
					omikuji = new Shokichi();
					break;
				case "4":
					omikuji = new Suekichi();
					break;
				case "5":
					omikuji = new Kichi();
					break;
				case "6":
					omikuji = new Kyo();
					break;

				}
				omikuji.setUnsei();
				omikuji.setOmikujicd(omikujiRs.getString("omikujicd"));
				omikuji.setUnseicd(omikujiRs.getString("unseicd"));
				omikuji.setNegaigoto(omikujiRs.getString("negaigoto"));
				omikuji.setAkinai(omikujiRs.getString("akinai"));
				omikuji.setGakumon(omikujiRs.getString("gakumon"));

			}
			// セッションに保存
			session.setAttribute("omikuji", omikuji);

			// 同じデータ存在しない場合insert
			if (!resultFlg) {
				PreparedStatement uranaiResult = con.prepareStatement(SQL3);
				uranaiResult.setDate(1, uranaidate);
				uranaiResult.setDate(2, birthday);
				uranaiResult.setString(3, omikujicd);
				uranaiResult.setString(4, "姜悦");
				uranaiResult.setString(5, "姜悦");
				uranaiResult.executeUpdate();
			}
			// 結果画面に遷移
			response.sendRedirect(showResult);
			return;

		} catch (ParseException e) {
			// エラーメッセージをセッションに保存
			errMessage.setErrMessage("入力された日付は存在しません。");
			session.setAttribute("errMessage", errMessage);
			// コンソールに出力
			System.out.println(errMessage.getErrMessage());
			// 占い画面に遷移
			response.sendRedirect(showUranai);
			return;
		} catch (IOException e) {
			// エラーメッセージをセッションに保存
			errMessage.setErrMessage("予期せぬエラーが発生しました。");
			session.setAttribute("errMessage", errMessage);
			// コンソールに出力
			System.out.println(errMessage.getErrMessage());
			// 占い画面に遷移
			response.sendRedirect(showUranai);
			e.printStackTrace();
			return;
		} catch (SQLException e) {
			// エラーメッセージをセッションに保存
			errMessage.setErrMessage("DB接続にエラーが発生しました。");
			session.setAttribute("errMessage", errMessage);
			// コンソールに出力
			System.out.println(errMessage.getErrMessage());
			// 占い画面に遷移
			response.sendRedirect(showUranai);
			e.printStackTrace();
			return;
		}

	}
}