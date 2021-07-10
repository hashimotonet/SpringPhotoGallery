package hashimotonet.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hashimotonet.dao.base.AbstractBaseDao;

/**
 * Accountテーブルに対するDataAccessObjectです。
 *
 * @author Osamu Hashimoto
 *
 */
public final class AccountDao extends AbstractBaseDao {

    public AccountDao()
            throws ClassNotFoundException,
                    IOException,
                    URISyntaxException {
        super();
    }

    /**
     * Identityを基にAccountテーブルを検索し、ユーザが
     * 登録済みである場合に真を返却します。
     *
     * @param identity
     * @return
     */
    public boolean accuntExists(String identity)
            throws SQLException{
        boolean result = false;

        String sql = "select authority from account "
                + " where identity=?";

        Connection conn = super.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, identity);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            result = true;
        }

        rs.close();
        stmt.close();

        return result;
    }

    /**
     * IDとパスワードの組み合わせで、レコードが存在するかについて
     * 検索処理を行います。
     *
     * @param identity
     * @param password
     * @return　SQL成功時にはユーザ権限を、失敗時には-1を返却する。
     */
    public int accuntExists(String identity, String password)
            throws SQLException {
        int result = -1;

        String sql = "select authority from account "
                + " where identity=? and password=?";

       Connection conn = super.getConnection();
       PreparedStatement stmt = conn.prepareStatement(sql);

       stmt.setString(1, identity);
       stmt.setString(2, password);

       ResultSet rs = stmt.executeQuery();

       if (rs.next()) {
           result = rs.getInt("authority");
       }

       rs.close();
       stmt.close();

       return result;
    }

    /**
     * 新規ユーザーの場合に、登録処理を行うメソッドです。
     *
     * @param identity
     * @param password
     * @return
     */
    public boolean createAccout(String identity, String password)
            throws SQLException {
        boolean result  = false;

        String sql = "insert into gallery.account "
                + "(identity, password) "
                + " values ( ?, ? )";

        // JDBC接続取得。
        Connection conn = super.getConnection();

        // ステートメント取得。
        PreparedStatement stmt = conn.prepareStatement(sql);

        // ステートメントに引数セット。
        stmt.setString(1, identity);
        stmt.setString(2, password);

        // レコード挿入実行。
        int one = stmt.executeUpdate();

        stmt.close();

        // レコード挿入成功可否を返却する。
        if (one == 1) result = true;

        return result;
    }

}
