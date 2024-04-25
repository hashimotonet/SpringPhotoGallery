package hashimotonet.action;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import hashimotonet.dao.AccountDao;
import hashimotonet.util.BaseUtil;

/**
 * 
 * @author hashi
 *
 */
public class SignInAction {
	
	public static final String STATUS = "status";
	
	public SignInAction() {

	}
	
	public boolean execute(HttpServletRequest request, String id, String password) throws ClassNotFoundException, 
																							 SQLException, 
																							 IOException, 
																							 URISyntaxException {
		
		boolean result = false;
		int authority = 0;
		HttpSession session = request.getSession(true);
		
        // 新規セッション／継続セッションのいずれかを判定
//        if (session.isNew() == true) {
            // IDが取得できた。
            if (BaseUtil.isNotEmpty(id)) {
                // ID でアカウントマスタを検索。
                if(isAccountExists(id)) {
                    // ID は存在したので、
                    // ID とパスワードで、アカウントマスタを検索。
                    authority = isAccountExists(id,password);
                    // アカウントマスタの検索結果に、アカウント権限を取得する。
                    if (authority != -1) {
                        // ここまで例外が起きていないので、処理結果はtrue。
                        result = true;
                        session.setAttribute(STATUS, Boolean.TRUE);
                    }
                } else {
                    // 新規ユーザであるので、登録処理を行う。
                    createAccount(id, password);
                    result = true;
                    session.setAttribute(STATUS, Boolean.TRUE);
                }
            }
//        } else {
//        	// 継続セッションであるので、処理続行。ただし、セッションは破棄。
//        	session.invalidate();
//        }

        return result;
	}

    /**
     * IDによる、アカウントマスタへの存在チェックを行います。
     *
     * @param identity ID
     * @return マスタへの存在あり／なし
     * @throws SQLException SQL例外
     * @throws ClassNotFoundException クラスが見つからない例外
     * @throws IOException 入出力例外
     * @throws URISyntaxException URIシンタックス例外
     */
    private boolean isAccountExists(String identity)
            throws SQLException, ClassNotFoundException, IOException, URISyntaxException {
        boolean exists = false; // 戻り値をfalseで初期化。

        // アカウントマスタへのDAOインスタンス生成。
        AccountDao dao = new AccountDao();

        // IDによるアカウントマスタへの存在チェック。
        exists = dao.accuntExists(identity);

        // JDBC接続を閉じる。
        dao.close();

        // 存在チェック結果を返却。
        return exists;
    }

    /**
     * アカウントが存在するかを確認する。
     *
     * @param identity
     * @param password
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws URISyntaxException
     */
    private int isAccountExists(String identity, String password)
            throws SQLException, ClassNotFoundException, IOException, URISyntaxException {

        int authority = -1;

        AccountDao dao = new AccountDao();

        authority = dao.accuntExists(identity, password);

        dao.close();

        return authority;
    }

    /**
     * アカウント非存在時にサインアップとみなし、アカウントマスタにレコードを作成する。
     */
    public static boolean createAccount(String identity, String password)
            throws SQLException,
                    ClassNotFoundException,
                    IOException,
                    URISyntaxException {
        boolean result = false;

        AccountDao dao = new AccountDao();
        result = dao.createAccout(identity, password);

        // アカウント作成処理は成功したか？
        if (result == true)
        { // 成功したので確定する。
            dao.commit();
        } else
        { // 作成処理に失敗したので、更新取消を行う。
            dao.rollback();
        }

        dao.close();

        return result;
    }
}
