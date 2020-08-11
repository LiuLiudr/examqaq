package com.zzxx.exam.test;

import com.zzxx.exam.entity.EntityContext;
import com.zzxx.exam.entity.Question;
import com.zzxx.exam.entity.User;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class B {
    public static void main(String[] args) throws IOException {
        EntityContext m=new EntityContext();

//        User u1=m.getUser(1000);
//        System.out.println(u1);
//        Map<Integer, User> users=new HashMap<>();
//        users=m.getUsers();
//        System.out.println(users);



//        EntityContext mm=new EntityContext();
//        Map<Integer, List<Question>>questions=new HashMap<>();
//        questions=mm.getQuestions();
//        System.out.println(questions);
//        System.out.println(questions.size());



        EntityContext mm=new EntityContext();
        //Map<Integer, List<Question>>questions=new HashMap<>();
        //questions=mm.getQuestions();
        //System.out.println(questions.size());
        //System.out.println(questions);
        System.out.println(mm.getQuestions(0));


    }
}
