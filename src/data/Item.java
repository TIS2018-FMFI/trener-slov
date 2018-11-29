package data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {


    private String questionText;
    private String questionImg;
    private String questionSound;

    private String answerText;
    private String answerImg;
    private String answerSound;

    public Item(){ }

    public Item(String questionText, String questionImg, String questionSound, String answerText, String answerImg, String answerSound) {
        this.questionText = questionText;
        this.questionImg = questionImg;
        this.questionSound = questionSound;
        this.answerText = answerText;
        this.answerImg = answerImg;
        this.answerSound = answerSound;
    }

    public void switchQuestionAndAnswer() {
        String tmpQuestionText = questionText;
        String tmpQuestionImg = questionImg;
        String tmpQuestionSound = questionSound;

        questionText = answerText;
        questionImg = answerImg;
        questionSound = answerSound;

        answerText = tmpQuestionText;
        answerImg = tmpQuestionImg;
        answerSound = tmpQuestionSound;
    }


    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionImg() {
        return questionImg;
    }

    public void setQuestionImg(String questionImg) {
        this.questionImg = questionImg;
    }

    public String getQuestionSound() {
        return questionSound;
    }

    public void setQuestionSound(String questionSound) {
        this.questionSound = questionSound;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerImg() {
        return answerImg;
    }

    public void setAnswerImg(String answerImg) {
        this.answerImg = answerImg;
    }

    public String getAnswerSound() {
        return answerSound;
    }

    public void setAnswerSound(String answerSound) {
        this.answerSound = answerSound;
    }
}
