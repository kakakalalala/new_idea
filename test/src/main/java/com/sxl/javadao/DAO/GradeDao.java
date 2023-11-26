package com.sxl.javadao.DAO;

import com.sxl.javadao.entity.Grade;

import java.util.List;

/**
 * @auther 266412
 * @date 2023/11/15 18:26
 */
public interface GradeDao {
    List<Grade> listBySid(Integer id);
}
