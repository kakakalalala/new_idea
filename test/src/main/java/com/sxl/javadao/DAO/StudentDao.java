package com.sxl.javadao.DAO;

import com.sxl.javadao.entity.Student;

import java.util.List;

/**
 * 提供学生类的基本crud 和基本的模糊查询和分页查询
 * @author 26641
 * @auther SXL
 * @date 2023/11/14 19:44
 */
public interface StudentDao {

    /**xiang数据库插入学生数据
     * @param student  包含了除id之外的字段值
     */
    void insert(Student student);

    /**根据id删除数据
     * @param id 删除学生id
     */
    void delete(Integer id);

    /**根据学生id进行所有字段值的修改 暂不支持动态字段
     * @param student  id 用于修改条件 其他字段未修改值
     */
    void update(Student student);

    /**默认查询所有学生数据 为空 无数据
     * @return 返回查询结果 null 查询为空
     */
    List<Student> listAll();

    /**根据提供的key进行模糊查询
     * @param key 模糊查询的关键字
     * @return  根据模糊查询查询的结果 如果没有查询到 结构weinull
     */
    List<Student> listByNameLike(String key);

    /**根据提供的参数对学生信息进行limit分页查询
     * @param start  参数1 第几行的下一行
     * @param count 参数2 获取几行
     * @return 返回查询结果 null 查询为空
     */
    List<Student> listByPage(Integer start, Integer count);

    /**根据提供的关键字key 对学生表进行分页模糊查询
     * @param ky 模糊查询的关键字
     * @param start 参数1 第几行的下一行
     * @param count 参数2 获取几行
     * @return  返回查询结果 null 查询为空
     */
    List<Student> listByNameLikeWithPage(String ky, Integer start, Integer count);
    void testInsert();
    Student listByIdWithGrade(Integer id);
    List<Student> listAllWithGrade();
    List<Student> listAllWithGrade(int Start,int count);
}
