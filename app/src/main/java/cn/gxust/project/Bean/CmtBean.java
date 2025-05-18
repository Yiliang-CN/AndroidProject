package cn.gxust.project.Bean;

public class CmtBean {
    private Long cmtID;              // 评论ID
    private Long cmtUserID;         // 评论用户ID
    private String cmtUserName;     // 评论用户名
    private int cmtShopID;          // 评论店铺ID
    private int cmtScore;           // 评分
    private String cmtTime;         // 评论时间
    private String cmtContent;      // 评论内容
    private String cmtUserImage;    // 用户头像

    public CmtBean(Long cmtID, Long cmtUserID, String cmtUserName, int cmtShopID, int cmtScore, String cmtTime, String cmtContent, String cmtUserImage) {
        this.cmtID = cmtID;
        this.cmtUserID = cmtUserID;
        this.cmtUserName = cmtUserName;
        this.cmtShopID = cmtShopID;
        this.cmtScore = cmtScore;
        this.cmtTime = cmtTime;
        this.cmtContent = cmtContent;
        this.cmtUserImage = cmtUserImage;
    }

    public Long getCmtID() {
        return cmtID;
    }

    public void setCmtID(Long cmtID) {
        this.cmtID = cmtID;
    }

    public Long getCmtUserID() {
        return cmtUserID;
    }

    public void setCmtUserID(Long cmtUserID) {
        this.cmtUserID = cmtUserID;
    }

    public String getCmtUserName() {
        return cmtUserName;
    }

    public void setCmtUserName(String cmtUserName) {
        this.cmtUserName = cmtUserName;
    }

    public int getCmtShopID() {
        return cmtShopID;
    }

    public void setCmtShopID(int cmtShopID) {
        this.cmtShopID = cmtShopID;
    }

    public int getCmtScore() {
        return cmtScore;
    }

    public void setCmtScore(int cmtScore) {
        this.cmtScore = cmtScore;
    }

    public String getCmtTime() {
        return cmtTime;
    }

    public void setCmtTime(String cmtTime) {
        this.cmtTime = cmtTime;
    }

    public String getCmtContent() {
        return cmtContent;
    }

    public void setCmtContent(String cmtContent) {
        this.cmtContent = cmtContent;
    }

    public String getCmtUserImage() {
        return cmtUserImage;
    }

    public void setCmtUserImage(String cmtUserImage) {
        this.cmtUserImage = cmtUserImage;
    }

}
