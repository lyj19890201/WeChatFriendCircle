package com.zyz.wechatfriendsdemo.ui.bean;


import java.util.List;

public class FriendsItemBean extends BaseBean {

    private String name;
    private String content;

    private List<PraiseBean> praiseBean;
    private List<CommentBean> commentBean;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<PraiseBean> getPraiseBean() {
        return praiseBean;
    }

    public void setPraiseBean(List<PraiseBean> praiseBean) {
        this.praiseBean = praiseBean;
    }

    public List<CommentBean> getCommentBean() {
        return commentBean;
    }

    public void setCommentBean(List<CommentBean> commentBean) {
        this.commentBean = commentBean;
    }




    public static class PraiseBean {

        private String name;
        private boolean isParise;


        public boolean isParise() {
            return isParise;
        }

        public void setParise(boolean parise) {
            isParise = parise;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }



    public static class CommentBean {
        private String name;
        private String type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        private String content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
