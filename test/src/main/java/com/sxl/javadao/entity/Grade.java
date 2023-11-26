package com.sxl.javadao.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @auther 266412
 * @date 2023/11/15 18:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    private  Integer id;
    private String subject;
    private BigDecimal grade;
    private Integer sid;

    public Grade(String subject, BigDecimal grade, Integer sid) {
        this.grade=grade;
        this.sid=sid;
        this.subject=subject;
    }
}
