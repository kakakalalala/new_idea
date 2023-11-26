package com.sxl.javadao.test;

import com.sxl.javadao.entity.*;
import com.sxl.javadao.DAO.IMPI.StudentDaoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * @auther SXL
 * @date 2023/11/15 14:42
 */
public class TestJDBC {
    public static <StudentDAO> void main(String[] args) {
        // 编译语句的创建
//         try (Connection c = DBUtil.getConnection();
//              Statement s = c.createStateme1nt();) {
//             String sql = "INSERT INTO student(name,gender,birthday,address,qq)\n" +
//                     "VALUES('朱燕男','男','2002-2-23','苏州',10001)";
//             // 执行sql语句
//             s.execute(sql);
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
         StudentDaoImpl studentDAO = new StudentDaoImpl();
//         studentDAO.delete(4);
//        Student s = new Student(0,"朱鲲楠","男",new Date(),"南京",635219677);
////        StudentDAO studentDAO = (StudentDAO) new StudentDaoImpl();
////        studentDAO.;
//        studentDAO.insert(s);
//        studentDAO.listByNameLikeWithPage("朱",1,9);
        studentDAO.testInsert();
    }
}
