package com.zzxx.exam.controller;

import com.zzxx.exam.entity.*;
import com.zzxx.exam.service.ExamService;
import com.zzxx.exam.service.IdOrPwdException;
import com.zzxx.exam.ui.*;

import java.io.IOException;

public class ClientContext {
    private LoginFrame loginFrame;
    private ExamService service;
    private MenuFrame menuFrame;
    private WelcomeWindow welcomeWindow;
    private MsgFrame msgFrame;
    private ExamService examService;
    private ExamFrame examFrame;

    private EntityContext entityContext;
    private ExamInfo examInfo;
    private QuestionInfo questionInfo;
    public Question question;
    private int questionIndex = 0;

    //控制器
    private User user;
//    public void startShow() {
//        loginFrame.setVisible(true);
//    }
    public void welcomeWindow(){
        welcomeWindow.setVisible(true);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        welcomeWindow.setVisible(false);
        loginFrame.setVisible(true);
    }
//    public void setwelcomeWindow(WelcomeWindow welcomeWindow) {
//        this.welcomeWindow=welcomeWindow;
//    }

    public void login() throws IOException {//登陆控制
        //获得登陆界面输入的账号密码.都是字符串内容
        String id= loginFrame.getIdField().getText();
        String pwd=loginFrame.getPwdFeild().getText();
        try {
            user=service.login(id,pwd);
            //正确，界面跳转
            loginFrame.setVisible(false);
            menuFrame.setVisible(true);
            menuFrame.xm(user.getName()+"同学您好!");
        }  catch (IdOrPwdException e) {
            //更新提示信息
            loginFrame.updateMessage(e.getMessage());
        }
    }

    public LoginFrame getLoginFrame() {
        return loginFrame;
    }

    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public ExamService getService() {
        return service;
    }

    public void setService(ExamService service) {
        this.service = service;
    }

    public MenuFrame getMenuFrame() {
        return menuFrame;
    }

    public void setMenuFrame(MenuFrame menuFrame) {
        this.menuFrame = menuFrame;
    }

    public WelcomeWindow getWelcomeWindow() {
        return welcomeWindow;
    }

    public void setWelcomeWindow(WelcomeWindow welcomeWindow) {
        this.welcomeWindow = welcomeWindow;
    }

    public MsgFrame getMsgFrame() {
        return msgFrame;
    }

    public void setMsgFrame(MsgFrame msgFrame) {
        this.msgFrame = msgFrame;
    }

    public ExamService getExamService() {
        return examService;
    }

    public void setExamService(ExamService examService) {
        this.examService = examService;
    }


    //控制器开始考试方法
    public void start() throws IOException {
        examInfo=service.startExam();
        int [] timeLimit={examInfo.getTimeLimit()};
        examFrame.setExamInfo(user.getName(),examInfo.getTitle(),timeLimit[0]);
        //生成当前问题
        questionInfo=service.getQuestionInfo();
        examFrame.updateQuestionArea(questionInfo);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (timeLimit[0]>0){
                    try {
                        Thread.sleep(60l*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    examFrame.updateTime(--timeLimit[0]);
                }
                //超时自动交卷
                send();
            }
        });
        thread.start();
        menuFrame.setVisible(false);
        examFrame.setVisible(true);
    }
    //交卷
    public void send(){
        menuFrame.xm(user.getName()+"交卷成功");
        examFrame.setVisible(false);
        menuFrame.setVisible(true);
    }
//下一题
    public void next() {
        service.next();
        questionInfo=service.getQuestionInfo();
        examFrame.updateQuestionCount("总题数:20  题号:" + (questionInfo.getQuestionIndex() + 1));
        examFrame.updateQuestionArea(questionInfo);
    }
    //上一题
    public void prev(){
        service.prev();
        questionInfo=service.getQuestionInfo();
        examFrame.updateQuestionCount("总题数:20  题号:" + (questionInfo.getQuestionIndex() +1));
        examFrame.updateQuestionArea(questionInfo);
    }
    //计分
    public void result(){
        int gread=service.countTrue();
        msgFrame.showMsg("最终得分"+gread);
        msgFrame.setVisible(true);
    }
  //考场规则
    public void rules(){
        String rule=service.rules();
        msgFrame.showMsg(rule);
        msgFrame.setVisible(true);
    }
    //退出系统
    public void exit(){
        System.exit(0);
    }
public void updatQuestionInfoAnswers(int value,boolean selsct){
        service.updateQuestionInfoAnswers(value,selsct);
}

    public int getQuestionIndex() {
        return questionIndex;
    }

    public ExamFrame getExamFrame() {
        return examFrame;
    }

    public void setExamFrame(ExamFrame examFrame) {
        this.examFrame = examFrame;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EntityContext getEntityContext() {
        return entityContext;
    }

    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }

    public ExamInfo getExamInfo() {
        return examInfo;
    }

    public void setExamInfo(ExamInfo examInfo) {
        this.examInfo = examInfo;
    }

    public QuestionInfo getQuestionInfo() {
        return questionInfo;
    }

    public void setQuestionInfo(QuestionInfo questionInfo) {
        this.questionInfo = questionInfo;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
