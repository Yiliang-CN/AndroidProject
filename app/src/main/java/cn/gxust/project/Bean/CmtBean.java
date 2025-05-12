package cn.gxust.project.Bean;

public class CmtBean {
    private int cmtID;              // 评论ID
    private String cmtUserID;       // 评论用户ID
    private String cmtUserName;     // 评论用户名
    private String cmtType;         // 评论类型
    private String cmtScore;        // 评分
    private String cmtTime;         // 评论时间
    private String cmtContent;      // 评论内容
    private String cmtUserImage;    // 用户头像

    public CmtBean(int cmtID, String cmtUserID, String cmtUserName, String cmtType, String cmtScore, String cmtTime, String cmtContent, String cmtUserImage) {
        this.cmtID = cmtID;
        this.cmtUserID = cmtUserID;
        this.cmtUserName = cmtUserName;
        this.cmtType = cmtType;
        this.cmtScore = cmtScore;
        this.cmtTime = cmtTime;
        this.cmtContent = cmtContent;
        this.cmtUserImage = cmtUserImage;
    }

    public int getCmtID() {
        return cmtID;
    }

    public void setCmtID(int cmtID) {
        this.cmtID = cmtID;
    }

    public String getCmtUserID() {
        return cmtUserID;
    }

    public void setCmtUserID(String cmtUserID) {
        this.cmtUserID = cmtUserID;
    }

    public String getCmtUserName() {
        return cmtUserName;
    }

    public void setCmtUserName(String cmtUserName) {
        this.cmtUserName = cmtUserName;
    }

    public String getCmtType() {
        return cmtType;
    }

    public void setCmtType(String cmtType) {
        this.cmtType = cmtType;
    }

    public String getCmtScore() {
        return cmtScore;
    }

    public void setCmtScore(String cmtScore) {
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
