package hashimotonet.dao.base;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import hashimotonet.util.PropertyUtil;

public abstract class AbstractBaseDao {

    /**
     * 接続プロパティを持つファイル名
     */
    private String property = "application.properties";

    /**
     * 接続URL
     */
    private String url;

    /**
     * 接続ユーザ
     */
    private String user;

    /**
     * 接続パスワード
     */
    private String password;

    /**
     * JDBC接続オブジェクト
     */
    private Connection conn;

    /**
     * デフォルトコンストラクタ
     *
     * JDBCドライバをロードし、システムの
     * DB接続プロパティを取得します。
     *
     * @throws ClassNotFoundException クラスが見つからない例外
     * @throws IOException 入出力例外
     * @throws URISyntaxException URIシンタックス例外
     */
    public AbstractBaseDao()
            throws ClassNotFoundException,
            IOException,
            URISyntaxException {
        // MySQLドライバをロード
        Class.forName("com.mysql.cj.jdbc.Driver");

        // プロパティ読み込みユーティリティを起動
        //System.getProperties().list(System.out);
        PropertyUtil util = new PropertyUtil(property);

        // データベース接続に必要なプロパティ読み込み
        this.url = util.get("spring.datasource.url");           // URLを取得
        this.user = util.get("spring.datasource.username");     // ユーザ名を取得
        this.password = util.get("spring.datasource.password"); // パスワードを取得
    }

    /**
     * JDBC接続を取得するメソッド
     *
     * @return JDBC接続
     * @throws SQLException SQL例外
     */
    public Connection getConnection()
            throws SQLException {
        // JDBC接続を取得
    	System.out.println(url);
        this.conn = DriverManager.getConnection(url, user, password);

        // 自動コミットモードはOFFにする
        this.conn.setAutoCommit(false);

        // オープンしたJDBC接続を返却する
        return conn;
    }

    /**
     * JDBC接続をコミットします。
     *
     * @throws SQLException SQL例外
     */
    public void commit()
            throws SQLException {

        // 接続をコミットします
    	if (null != this.conn) {
    		this.conn.commit();
    	}

    }

    /**
     * JDBC接続をロールバックします。
     *
     * @throws SQLException SQL例外
     */
    public void rollback()
            throws SQLException {

        // 接続をロールバックします
    	if (null != this.conn) {
    		this.conn.rollback();
    	}

    }

    /**
     * JDBC接続をクローズし、リソースを解放します
     *
     * @throws SQLException SQL例外
     */
    public void close()
            throws SQLException {

        // 接続をクローズします
    	if (null != this.conn) {
    		this.conn.close();
    	}

    }

    /**
     * デストラクタです。
     */
    @Override
    public void finalize()
            throws SQLException {
        close();
    }

}
