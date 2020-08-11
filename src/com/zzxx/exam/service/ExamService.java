package com.zzxx.exam.service;

import com.zzxx.exam.controller.ClientContext;
import com.zzxx.exam.entity.*;

import java.io.IOException;
import java.util.*;

public class ExamService {
    private EntityContext entityContext;
    private ClientContext controller;
    private QuestionInfo questionInfo;

    public User login(String id,String password) throws IOException, IdOrPwdException {
        int id1=Integer.parseInt(id);
        User user2=entityContext.getUser(id1);
        System.out.println(user2);
        if(user2!=null){
            //判断密码是否正确
            if(user2.getPassword().equals(password)){
                return user2;
            }
        }
        throw new IdOrPwdException("编号或密码错误");
    }

    public EntityContext getEntityContext() {
        return entityContext;
    }

    public void setEntityContext(EntityContext entityContext) {//写入依赖
        this.entityContext = entityContext;
    }


    public QuestionInfo getQuestionInfo(int i){
        return entityContext.getQuestionInfo(i);
    }
    //开始考试方法
    public ExamInfo startExam() throws IOException {
        User user=controller.getUser();
        questionInfo=getQuestionInfo(0);
        return entityContext.getExamInfo(user.getId());
    }
    public void setController(ClientContext controller) {
        this.controller = controller;
    }
    //跳转到上一题
    public void prev(){
        int index=questionInfo.getQuestionIndex();
        if(index>0){
            questionInfo=getQuestionInfo(index-1);
        }
    }
    //跳转到下一题
    public void next(){
        int index=questionInfo.getQuestionIndex();
        if(index<entityContext.getQuestionSum()-1){
            questionInfo=getQuestionInfo(index+1);
        }
    }
    //获得当前的题目
    public QuestionInfo getQuestionInfo(){
        return questionInfo;
    }
    //更新用户答案信息
    public void updateQuestionInfoAnswers(int value,boolean isSelect){
        List<Integer>list=questionInfo.getUserAnswers();
        //选中的复选框答案会加到用户的回答列表 list
        if(isSelect){
            list.add(value);
        }else {
            //没选的就从用户回答列表中删除
            for(int i=0;i<list.size();i++){
                if(value==list.get(i)){
                    list.remove(i);
                    i--;
                }
            }
        }
        //将所选答案设置到questionInfo的answer
        questionInfo.setUserAnswers(list);
    }
    //计算用户答案分数
    public int countTrue(){
        int greads=0;
        for(int i=0;i<entityContext.getQuestionSum();i++){
            List<Integer> right=questionInfo.getQuestion().getAnswers();//获得题目的正确选项
            List<Integer> userDelect= questionInfo.getUserAnswers();
            if(right.containsAll(userDelect)&&userDelect.containsAll(right)){//互相全包含可保证答案相同且长度一样
                greads=greads+questionInfo.getQuestion().getScore();
            }
            if(userDelect.size()==0){//没选答案直接跳过
                continue;
            }
        }
        return greads;
    }
    //获得考场规则
    public String rules(){
        return entityContext.getRulesToString();
    }
}
