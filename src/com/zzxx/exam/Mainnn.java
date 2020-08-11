package com.zzxx.exam;

import com.zzxx.exam.controller.ClientContext;
import com.zzxx.exam.entity.EntityContext;
import com.zzxx.exam.service.ExamService;
import com.zzxx.exam.ui.*;

public class Mainnn {
    public static void main(String[] args) {
        LoginFrame loginFrame=new LoginFrame();
        MenuFrame menuFrame=new MenuFrame();
        ClientContext controller=new ClientContext();
        ExamService service=new ExamService();
        EntityContext entityContext=new EntityContext();
        ExamFrame examFrame=new ExamFrame();
        MsgFrame msgFrame=new MsgFrame();
        WelcomeWindow welcomeWindow=new WelcomeWindow();
        //注入依赖
        controller.setLoginFrame(loginFrame);
        controller.setMenuFrame(menuFrame);
        controller.setService(service);
        loginFrame.setController(controller);
        service.setEntityContext(entityContext);
        menuFrame.setController(controller);
        examFrame.setController(controller);
        controller.setExamFrame(examFrame);
        service.setController(controller);
        msgFrame.setController(controller);
        controller.setMsgFrame(msgFrame);
        controller.setWelcomeWindow(welcomeWindow);


        controller.welcomeWindow();
        //controller.startShow();


    }
}
