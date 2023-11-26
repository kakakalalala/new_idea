package com.sxl.javadao.DAO.IMPI;

import com.sxl.javadao.DAO.GradeDao;
import com.sxl.javadao.entity.Grade;
import com.sxl.javadao.test.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther 266412
 * @date 2023/11/15 19:32
 */
public class GradeDaoImpl implements GradeDao {
    @Override
    public List<Grade> listBySid(Integer id) {
        List<Grade> grades=new ArrayList<>();
        String sql="select*from grade where sid=?";
        try(Connection c= DBUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                String subject=rs.getString("sub_ject");
                BigDecimal grade=rs.getBigDecimal("grade");
                Integer sid=rs.getInt("sid");
                Grade g=new Grade();
                g.setSubject(subject);
                g.setGrade(grade);
                g.setSid(sid);
                grades.add(g);
            }
        }catch(SQLException e){
            e.printStackTrace();}
        return grades==null?null:grades;
    }
}
