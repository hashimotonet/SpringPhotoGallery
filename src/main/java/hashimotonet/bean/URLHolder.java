/**
 *
 */
package hashimotonet.bean;

/**
 * @author Osamu Hashimoto
 *
 */
public class URLHolder {

    private String url;

    private String thumbnail;
    
    private String alt;

    /**
     * デフォルトコンストラクタ
     */
    public URLHolder() {
        super();
    }

    /**
     * @return url
     */
    public final String getUrl() {
        return url;
    }

    /**
     * @param url セットする url
     */
    public final void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return thumbnail
     */
    public final String getThumbnail() {
        return thumbnail;
    }

    /**
     * @param thumbnail セットする thumbnail
     */
    public final void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

	/**
	 * @return alt
	 */
	public final String getAlt() {
		return alt;
	}

	/**
	 * @param alt セットする alt
	 */
	public final void setAlt(String alt) {
		this.alt = alt;
	}

}
