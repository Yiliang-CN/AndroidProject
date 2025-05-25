package cn.gxust.project.bean;

public class CmtBean {
    private String userName;     // 评论用户名
    private double score;        // 评分
    private String time;         // 评论时间
    private String content;      // 评论内容
    private String image;        // 用户头像

    public CmtBean() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
