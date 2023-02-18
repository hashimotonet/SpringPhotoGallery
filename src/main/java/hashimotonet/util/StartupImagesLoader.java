package hashimotonet.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hashimotonet.dao.PhotoDao;
import hashimotonet.model.Photo;

@Component
public class StartupImagesLoader {

    /**
     * Logger for this class.
     */
    private Logger log = (Logger)LogManager.getLogger(this.getClass());

    /**
     * 画像Data Access Object.
     */
	PhotoDao dao = null;

    ServletContext context;

    @Autowired
    public StartupImagesLoader(ServletContext context) throws ServletException {

        log.info("\n---contextInitialized() Started.---");

        this.context = context;

        try {

            dao = new PhotoDao();

            List<String> identities = dao.getIdentities();

            for (String id : identities) {

                // id で示されるディレクトリが存在するか？
                File parent = FileProcessorUtil.getParentDir(id, this.context);
                // File parent = new File(context + "/" + id);

                log.debug("parent = " + parent.getName());

                boolean exists = parent.exists();

                log.debug("exists = " + exists);

                // DAOクラスのインスタンスを作成
                dao = new PhotoDao();

                // IDが同一であるもののレコードの画像データを取得する
                List<Photo> images = dao.selectPhotoBlobsById(id);

                // DAOのクローズ
                dao.close();

                // 要求電文、ID、画像データを引数に、画像の一時イメージファイル集を
                // 作成し、ファイル一覧を戻り値として取得する。
                FileProcessorUtil.writeImageById(context, id, images);

            }

        } catch (URISyntaxException | SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
            log.catching(e);
        } catch (Exception e) {
            log.catching(e);
        } finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    log.catching(e);
                }
            }
        }

        log.info("\n---contextInitialized() Ended.---");
    }
}
