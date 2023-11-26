package com.sxl.javadao.DAO.IMPI;

import com.sxl.javadao.DAO.GradeDao;
import com.sxl.javadao.DAO.StudentDao;
import com.sxl.javadao.entity.Grade;
import com.sxl.javadao.entity.Student;
import com.sxl.javadao.test.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author 26641
 * @auther SXL
 * @date 2023/11/14 20:04
 */
public class StudentDaoImpl implements StudentDao,GradeDao {
    @Override
    public void insert(Student student) {
//        String sql="insert into Student(NAME,gender,birthday,address,qq)VALUES('"+student.getName()+"',"+"'"+student.getGender()+"',"+"'"+student.getBirthday().getTime()+"',"+"'"+student.getAddress()+"',"+student.getQqNumber()+");";
//        try(Connection c=DBUtil.getConnection();
//            Statement s=c.createStatement();){
//            s.execute(sql);
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
        //statement的问题
        //1.参数多时排版复杂
        //2.先传参 在编译 性能较差
        //3.存在sql注入攻击的问题


        //PreparedStatement
        //1.参数传递简单 方法调用传递即可
        //2.先编译 再传参 性能更好
        //3.不存在sql注入问题 在参数传递进入之前 语句已经编译完成
        //传递的参数只能是字符串识别

        String sql="insert into Student(NAME,gender,birthday,address,qq)"+"values(?,?,?,?,?)";
        try(Connection c= DBUtil.getConnection(sql,Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ps=c.prepareStatement(sql);){
            ps.setString(1,student.getName());
            ps.setString(2,student.getGender());
            ps.setDate(3,new Date(student.getBirthday().getTime()));
            ps.setString(4,student.getAddress());
            ps.setInt(5,student.getQqNumber());
            //在执行完插入语句之后 mysql会为新增的数据分配一个滋滋滋的id
            //jdbc可以同果getGeneratedKeys方法返回id
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                student.setId(rs.getInt(1));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Integer id) {
        //  beanUtil
        String sql="delete from student where id="+id;
        try(Connection c=DBUtil.getConnection();
            Statement s=c.createStatement();){
            s.execute(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Student student) {
        String sql="update student set name=?,gender=?,birthday=?,address=?,qq=?where id=?";
        try(Connection c=DBUtil.getConnection();
        PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,student.getName());
            ps.setString(1,student.getGender());
            ps.setDate(1, new Date (student.getBirthday().getTime()));
            ps.setString(1,student.getAddress());
            ps.setInt(1,student.getQqNumber());
            ps.setInt(1,student.getId());
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> listAll() {

        return null;
    }

    @Override
    public List<Student> listByNameLike(String key) {
        return null;
    }

    @Override
    public List<Student> listByPage(Integer start, Integer count) {
        return null;
    }

    @Override
    public List<Student> listByNameLikeWithPage(String key, Integer start, Integer count) {
        List<Student> stu=new ArrayList<>();
        String sql;
        if(key==null||key==""){
            sql="select*from student limit ?,?";
        }else {
            sql="select*from student where name like concat('%',?,'%')limit ?,?";
        }
        try(Connection c=DBUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)){
            if(key==null||key==""){
                ps.setInt(1,start);
                ps.setInt(2,count);
            }else {
                ps.setString(1,key);
                ps.setInt(2,start);
                ps.setInt(3,count);
            }
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Student s=new Student();
                //将每一个遍历的行中将对象依次取出
                Integer id=rs.getInt("id");
                String name=rs.getString(2);
                String gender=rs.getString("gender");
                java.util.Date birthday=rs.getDate("birthday");
                String address=rs.getString("address");
                int qq=rs.getInt("qq");
                s.setName(name);
                s.setId(id);
                s.setGender(gender);
                s.setBirthday(birthday);
                s.setAddress(address);
                s.setQqNumber(qq);
                stu.add(s);
            }
            for (Student st:stu){
                System.out.println(st);
            }
        }catch(SQLException e){
            e.printStackTrace();}

        return stu.isEmpty()?null:stu;
    }

    @Override
    public Student listByIdWithGrade(Integer id) {
        Student s=new Student();
        List<Grade> grades=new ArrayList<>();
        String sql="select*from student where id=?";
        try(Connection c=DBUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                s=new Student();
                s.setId(rs.getInt("id"));
                s.setGrades(grades);
            }
            while (rs.next()){
//                private  Integer id;
//                private String subject;
//                private BigDecimal grade;
//                private Integer sid;
//                假设学生中定义了List<Grade> grades的引用类型成员变量
//                我需要在查询学生的时候 能够关联获取所有学生相关的成绩信息
//                应该怎么实现代码？
                //将每一个遍历的行中将对象依次取出
                String subject=rs.getString("sub_ject");
                BigDecimal grade=rs.getBigDecimal("grade");
                Integer sid=rs.getInt("sid");
                Grade g=new Grade();
                g.setSubject(subject);
                g.setGrade(grade);
                g.setSid(sid);
                grades.add(g);
            }
            s.setGrades(grades);
        }catch(SQLException e){
            e.printStackTrace();}

        return s.getGrades().isEmpty()?null:s;
    }

    @Override
    public List<Student> listAllWithGrade() {
        List<Student> st=new ArrayList<>();
        List<Grade> grades=new ArrayList<>();
        String sql;
        sql="select*from grade g join student s on s.id=g.sid group by s.id";
        try(Connection c=DBUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)){
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                String subject=rs.getString("sub_ject");
                BigDecimal grade=rs.getBigDecimal("grade");
                Integer sid=rs.getInt("sid");
                Integer id=rs.getInt("id");
                Student s=new Student();
                Grade g=new Grade();
                g.setSubject(subject);
                g.setGrade(grade);
                g.setSid(sid);
                s.setId(id);
                grades.add(g);
                s.setGrades(grades);
                st.add(s);
            }
        }catch(SQLException e){
            e.printStackTrace();}

        return st.isEmpty()?null:st;
    }

    @Override
    public List<Student> listAllWithGrade(int Start, int count) {
        List<Student> st=new ArrayList<>();
        List<Grade> grades=new ArrayList<>();
        String sql;
        sql="select*from grade g join student s on s.id=g.sid group by s.id limit ?,?";
        try(Connection c=DBUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,Start);
            ps.setInt(2,count);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                String subject=rs.getString("sub_ject");
                BigDecimal grade=rs.getBigDecimal("grade");
                Integer sid=rs.getInt("sid");
                Integer id=rs.getInt("id");
                Student s=new Student();
                Grade g=new Grade();
                g.setSubject(subject);
                g.setGrade(grade);
                g.setSid(sid);
                s.setId(id);
                grades.add(g);
                s.setGrades(grades);
                st.add(s);
            }
        }catch(SQLException e){
            e.printStackTrace();}

        return st.isEmpty()?null:st;
    }

    @Override
    public void testInsert() {
        String sql="insert into Student values(?,?,?,?,?,?)";
        Connection c= null ;
        try {
            c = DBUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try(PreparedStatement ps=c.prepareStatement(sql)){
            c.setAutoCommit(false);
            Random r=new Random();
            for(int i=0;i<10000;i++){
                ps.setInt(1,r.nextInt(200)+10);
                ps.setString(2,"学生"+1);
                ps.setString(3,"男");
                ps.setDate(4,new Date(new java.util.Date().getTime()));
                ps.setString(5,"南京");
                ps.setLong(6,r.nextInt(100000000)+10000);
                ps.execute();
            }
            c.commit();
        }catch(SQLException e){
            try {
                System.out.println("插入错误，自动回滚");
                c.rollback();
            }catch (SQLException ex){
                e.printStackTrace();
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<Grade> listBySid(Integer id) {
        Student s=new Student();
        List<Grade> grades=new ArrayList<>();
        String sql;
        sql="select*from grade where sid=?";
        try(Connection c=DBUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
//                private  Integer id;
//                private String subject;
//                private BigDecimal grade;
//                private Integer sid;
//                假设学生中定义了List<Grade> grades的引用类型成员变量
//                我需要在查询学生的时候 能够关联获取所有学生相关的成绩信息
//                应该怎么实现代码？
                //将每一个遍历的行中将对象依次取出
                String subject=rs.getString("sub_ject");
                BigDecimal grade=rs.getBigDecimal("grade");
                Integer sid=rs.getInt("sid");
                Grade g=new Grade();
                g.setSubject(subject);
                g.setGrade(grade);
                g.setSid(sid);
                grades.add(g);
            }
            s.setGrades(grades);
        }catch(SQLException e){
            e.printStackTrace();}

        return s.getGrades().isEmpty()?null:s.getGrades();
    }
}
