package com.iweb;

import com.sxl.javadao.DAO.IMPI.StudentDaoImpl;
import com.sxl.javadao.DAO.StudentDao;
import com.sxl.javadao.entity.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @auther 266412
 * @date 2023/11/15 20:25
 */
public class JDBCtest {
    private StudentDao sd;
    //在其他测试方法运行之前 被该注解修饰的两种方法会自动执行
    @Before
    public void init(){
        sd=new StudentDaoImpl();
    }
    @Test
    public void run(){
        System.out.println(sd.listAllWithGrade());
    }
    @Test
    public void run1(){
        System.out.println(sd.listAll());
    }
    @After
    public void destroy(){
        System.out.println("销毁");
    }
}
