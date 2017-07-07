package com.crackingMBA.training.pojo;

/**
 * Created by vijayp on 7/7/17.
 */

public class VocabGameDetails {

    private String game_id;
    private String qn_id;
    private String qn;
    private String answer;
    private String hint1;
    private String hint2;
    private String hint3;

    @Override
    public String toString() {
        return "VocabGameDetails{" +
                "game_id='" + game_id + '\'' +
                ", qn_id='" + qn_id + '\'' +
                ", qn='" + qn + '\'' +
                ", answer='" + answer + '\'' +
                ", hint1='" + hint1 + '\'' +
                ", hint2='" + hint2 + '\'' +
                ", hint3='" + hint3 + '\'' +
                '}';
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getQn_id() {
        return qn_id;
    }

    public void setQn_id(String qn_id) {
        this.qn_id = qn_id;
    }

    public String getQn() {
        return qn;
    }

    public void setQn(String qn) {
        this.qn = qn;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getHint1() {
        return hint1;
    }

    public void setHint1(String hint1) {
        this.hint1 = hint1;
    }

    public String getHint2() {
        return hint2;
    }

    public void setHint2(String hint2) {
        this.hint2 = hint2;
    }

    public String getHint3() {
        return hint3;
    }

    public void setHint3(String hint3) {
        this.hint3 = hint3;
    }
}
