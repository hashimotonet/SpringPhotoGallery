package hashimotonet.mail.template;

import java.util.Map;

public interface MailTemplate {
    /**
     * テンプレートファイルのパスを取得します。
     * @return テンプレートファイルパス
     */
    String getTemplatePath();

    /**
     * テンプレートに埋め込む変数と値のマップを取得します。
     * @return テンプレートに埋め込む変数と値のマップ
     */
    Map<String, Object> getVariables();
}
