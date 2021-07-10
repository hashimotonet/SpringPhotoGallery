package hashimotonet.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hashimotonet.dao.base.AbstractBaseDao;
import hashimotonet.model.Photo;

/**
 * トランザクションテーブルである、PHOTO表に関する
 * データアクセスオブジェクトのクラスです。
 *
 * @author Osamu Hashimoto
 *
 */
public class PhotoDao extends AbstractBaseDao {

    /**
     * Insert DML
     */
    private final String insertSQL = "insert into photo (identity, authority, data, alt) values (?, ?, ?, ?)";

    private final String selectMaxSQL = "select max(id) from photo";

    /**
     * ロガー
     */
    private final Logger log = LogManager.getLogger(PhotoDao.class);

    /**
     * デフォルトコンストラクタ
     *
     * @throws ClassNotFoundException クラスが見つからない例外
     * @throws IOException 入出力例外
     * @throws URISyntaxException URIシンタックス例外
     */
    public PhotoDao() throws ClassNotFoundException,
                               IOException,
                               URISyntaxException {

        // 親クラスのコンストラクタ呼び出し
        super();
    }

    /**
     * データ挿入メソッド
     *
     * @param photo
     * @return
     * @throws SQLException
     */
    public int insert(Photo photo) throws SQLException {

        // 挿入件数をゼロで初期化
        int result = 0;

        // JDBC接続
        Connection conn = null;

        // プレースホルダ付きSQL文対応のステートメント
        PreparedStatement stmt = null;

        // JDBC接続を取得
        conn = super.getConnection();

        // ステートメントを作成
        stmt = conn.prepareStatement(insertSQL);

        // DMLのプレースホルダにパラメータをセット
        stmt.setString(1,photo.getIdentity());    // IDを設定
        stmt.setInt(2, photo.getAuthority());    // 権限を設定
        stmt.setBytes(3, photo.getData());        // 画像バイナリを設定
        stmt.setString(4, photo.getAlt());

        // 挿入処理実行
        result = stmt.executeUpdate();

        stmt.close();

        //close();

        // 挿入件数を返却
        return result;
    }

    /**
     * IDによリ該当する画像バイナリデータを全件取得します。
     *
     * @param id ユーザのID
     * @return IDによる画像バイナリ
     * @throws SQLException SQL例外
     */
    public List<Photo> selectPhotoBlobsById(String id)
            throws SQLException {

        // 戻り値を初期化
        List<Photo> result = new ArrayList<Photo>();
        Photo photo = new Photo();

        // DMLを宣言
        String sql = "select data, alt,id from photo where identity=? order by id";

        // JDBC接続を取得
        Connection conn = super.getConnection();

        // ステートメントを作成
        PreparedStatement pStmt = conn.prepareStatement(sql);

        // DMLのプレースホルダにIDをセット
        pStmt.setString(1, id);

        // 検索実行
        ResultSet rs = pStmt.executeQuery();

        // 検索結果がある間ループする
        while(rs.next()) {
            // バイナリをバイト配列に取得する
            byte[] data = rs.getBytes("data");
            String alt = rs.getString("alt");          // 結果のリストに取得したデータを追記
            int ind = rs.getInt("id");          
            photo.setData(data);
            photo.setAlt(alt);
            photo.setId(ind);
            
            result.add(photo);
            
            photo = new Photo();
        }

        // 結果セットのクローズ
        rs.close();

        // ステートメントのクローズ
        pStmt.close();

        // 取得したデータリストを返却
        return result;
    }

    public List<String> getIdentities()
            throws SQLException {
        List<String> result = new ArrayList<String>();

        String sql = "select identity from photo group by identity";

        Statement stmt = super.getConnection().createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()) {
            result.add(rs.getString("identity"));
        }

        rs.close();

        stmt.close();

        close();

        return result;
    }

    public int selectMaxId()
            throws SQLException {
        int result = 0;
        Statement stmt = super.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(selectMaxSQL);
        if (rs.next()) {
            result = rs.getInt(1);
        }
        rs.close();
        stmt.close();
        close();
        return result;
    }
    
    /**
     * altテキスト取得。
     * 
     * @param identity
     * @return
     * @throws SQLException
     */
    public List<Photo> getAltByIdIdentity(String identity) throws SQLException {
    	List<Photo> list = new ArrayList<Photo>();
    	Photo photo = new Photo();
    	String sql = "select id, alt from photo where identity=? order by id";
    	PreparedStatement pStmt = super.getConnection().prepareStatement(sql);
    	pStmt.setString(1, identity);
    	ResultSet rs = pStmt.executeQuery();
    	while(rs.next()) {
	    	photo.setId(rs.getInt("id"));
	    	photo.setAlt(rs.getString("alt"));
	    	list.add(photo);
	    	photo = new Photo();
    	}
    	return list;
    }


    /**
     * コミットを行うメソッドです。
     */
    @Override
    public void commit()
            throws SQLException {
        super.commit();
    }

    /**
     * ロールバックを行うメソッドです。
     */
    @Override
    public void rollback()
            throws SQLException {
        super.rollback();
    }

    /**
     * JDBC接続をクローズします。
     */
    @Override
    public void close()
            throws SQLException {
        super.close();
    }

}
