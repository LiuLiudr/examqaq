package com.zzxx.exam.entity;

import com.zzxx.exam.util.Config;
import org.junit.Test;

import java.io.*;
import java.util.*;

/**
 * 实体数据管理, 用来读取数据文件放到内存集合当中
 */
public class EntityContext {
    //key-用户编号id，valu-用户对象
    private Map<Integer, User> users = new HashMap<>();
    //key-试题难度，value-对应难度试题集合。
    private Map<Integer, List<Question>> questions = new TreeMap<>();
    /*
    读取user.txt文件，将其中数据封装为用户对象
     */
    //试卷题目生成QuestionInfo 题目加题号加用户答案
    private ArrayList<QuestionInfo> questionInfos;
    //创建考生信息集合
    private Map<Integer, ExamInfo> examInfos = new TreeMap<>();
    //创建对象读取config.properties文件
    private Config config = new Config("config.properties");
    //创建考场规则对象
    private List<String> rules=new ArrayList<>();

    //构造方法
    public EntityContext() {
        try {
            loadQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            loadUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadExamInfo();
        loadQuestionInfo();

        try {
            loadRules();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //考生信息加载
    private void loadExamInfo() {
        Set<Integer> keyset = users.keySet();
        for (Integer num : keyset) {
            User user = users.get(num);
            //初始化ExamInfo
            ExamInfo personExamInfo = new ExamInfo(config.getString("PaperTitle"), user, config.getInt("TimeLimit"),
                    config.getInt("QuestionNumber"));
            //个人信息加入map
            examInfos.put(num, personExamInfo);
        }
    }

    //生成试题集;
    private void loadQuestionInfo() {
        //每个难度两题
        questionInfos = new ArrayList<>();
        int j = 0;
        for (int i = Question.LEVEL1; i <= Question.LEVEL10; i++) {
            List<Question> list = questions.get(i);//获得对应难度所有的题目
            int x;
            loop:
            for (int p = 0; p < 2; p++) {
                x = (int) (Math.random() * list.size());
                Question que = list.get(x);//获得难度i集合中的一题
                if (p > 0) {//判断生成的是不是重复的题目
                    for (QuestionInfo op : questionInfos) {
                        if (op.getQuestion().getTitle().equals(que.getTitle())) {

                            p--;
                            continue loop;
                        }
                    }
                }
                //题目添加到QuestionInfo
                QuestionInfo questionInfo = new QuestionInfo(j++, que);
                questionInfos.add(questionInfo);
            }
        }
//        for (QuestionInfo q : questionInfos
//        ) {
//            System.out.println(q.getQuestion().getLevel() + q.getQuestion().getTitle());
//        }
    }

    public void loadUsers() throws IOException {
        BufferedReader red = new BufferedReader(new InputStreamReader(new FileInputStream("./src/com/zzxx/exam/util/user.txt")));
        String str;
        while ((str = red.readLine()) != null) {
            String[] str1 = str.split(":");
            if (str1[0].matches("\\d+")) {
                int id = Integer.parseInt(str1[0]);
                String name = str1[1];
                String password = str1[2];
                String phone = str1[3];
                String email = str1[4];
                User user = new User(name, id, password);
                users.put(id, user);
            }
        }
        red.close();
    }

    //读取corejava.txt,将数据封装为Question对象
    public void loadQuestions() throws IOException {
        BufferedReader red = new BufferedReader(new InputStreamReader(new FileInputStream("./src/com/zzxx/exam/util/corejava.txt")));
        String str;
        List<Question> questionList = new ArrayList<>();
        while ((str = red.readLine()) != null) {
            List<String> options = new ArrayList<String>();// 若干选项
            Question question = new Question();
            for (int i = 0; i < 6; i++) {
                if (i == 0) {
                    int j = 0;
                    List<Integer> answers = new ArrayList<Integer>();// 正确答案
                    String[] arr = str.split(",");
                    question.setLevel(Integer.parseInt(arr[2].split("=")[1]));
                    question.setScore(Integer.parseInt(arr[1].split("=")[1]));

                    String num = arr[0].split("=")[1];
                    String[] num2 = num.split("/");
                    for (j = 0; j < num2.length; j++) {
                        answers.add(Integer.parseInt(num2[j]));
                    }
                    question.setAnswers(answers);
                } else if (i == 1) {
                    str = red.readLine();
                    question.setTitle(str);
                } else {
                    str = red.readLine();
                    options.add(str);

                }
            }
            question.setOptions(options);
            questionList.add(question);
        }

        // System.out.println(questionList);

        for (int i = 0; i < questionList.size(); i++) {
            int dj = questionList.get(i).getLevel();
            List<Question> list = questions.get(dj);
            if (list == null) {
                list = new ArrayList<Question>();
            }
            list.add(questionList.get(i));
            questions.put(i, list);
        }

    }
    //加载考场规则
    @Test
    public void loadRules() throws IOException {
        BufferedReader red = new BufferedReader(new InputStreamReader(new FileInputStream("./src/com/zzxx/exam/util/rule.txt")));
        String str;
        while ((str=red.readLine())!=null){
            rules.add(str);
        }
    }

    //用id查询user
    public User getUser(int id) throws IOException {
        return users.get(id);
    }

    //用难度等级获取题目集合
    public List<Question> getQuestions(int i) throws IOException {
        return questions.get(i);
    }

    //获得试卷题目
    public QuestionInfo getQuestionInfo(int i) {
        return questionInfos.get(i);
    }

    //获得试卷题目数量
    public int getQuestionSum() {
        return questionInfos.size();
    }

    //获得考生信息（科目考生分钟题目）
    public ExamInfo getExamInfo(Integer i) {
        return examInfos.get(i);
    }
    //获得考试规则
    public String getRulesToString(){
        List<String> list= rules;
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<list.size()-1;i++){
            stringBuilder.append(list.get(i)+"\n");
        }
        stringBuilder.append(list.get(list.size()-1));
        return stringBuilder.toString();
    }

}
