package com.example.administrator.wclass.data.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/29.
 */
public class ClassBean{
    private String class_image_url;//头像
    private String class_name;//课程
    private String class_class;//上课班级
    private String class_duration;//课时
    private String class_random_number;//随机一个班级号
    private AVUser class_owner_user;//课堂管理者
    private List<AVUser>user_list;//所有的成员名单
    //签到相关
    private String class_signin_number;//签到码，每次都会重置
    private List<AVUser>user_sigin_list;//本次签到成功，每次都会重置
    private Map<AVUser,Integer> class_user_rank;//每个学生对应的经验值

}
