package hashimotonet.util;

import java.util.List;

/**
 * ユーティリティクラスの基底クラス
 *
 * @author Osamu Hashimoto
 *
 */
public class BaseUtil {

    /**
     * プライベートコンストラクタ
     */
    private BaseUtil() {

    }

    /**
     * 引数がNULLでなく、空文字でもない場合には
     * TRUEを返却します。
     *
     * @param src 検査対象文字列
     * @return 検査結果
     */
    public static synchronized boolean isNotEmpty(String src) {

        // 返却値をFALSEで初期化
        boolean result = false;

        // null ではないか
        if (src != null) {
            // 空文字ではないか
            if (!src.equals("")) {
                // 値が存在する文字列なので返却値はTRUE
                result = true;
            }
        }

        // 検査結果を返却する
        return result;
    }

    /**
     * List<String>の内容でソートを行い、返却します。
     *
     * @param list ソート対象List<String>
     * @return ソートされたList<String>
     */
    public static List<String> sort(List<String> list) {
        list.sort((s1, s2) -> {
            if (compareLength(s1, s2) == 0) {
                return s1.compareTo(s2);
            }
            return compareLength(s1, s2);
        });
        return list;
    }

    /**
     * 第一、第二引数の文字列長を比較し返却します。
     *
     * @param s1
     * @param s2
     * @return
     */
    private static int compareLength(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return 1;
        } else if (s1.length() < s2.length()) {
            return -1;
        } else {
            return 0;
        }
    }
}
