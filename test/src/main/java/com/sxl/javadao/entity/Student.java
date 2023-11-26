package com.sxl.javadao.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 26641
 * @auther SXL
 * @date 2023/11/14 19:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Integer id;
    private String name;
    private String gender;
    private Date birthday;
    private String address;
    private  Integer qqNumber;
    private List<Grade> grades;
}
