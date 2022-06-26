package book.manage.mapper;

import book.manage.entity.Book;
import book.manage.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface BookMapper {

    @Insert("insert into student(name,sex,grade) value(#{name},#{sex},#{grade})")
    int addStudent(Student student);

    @Insert("insert into book(title,`desc`,price) value(#{title},#{desc},#{price})")
    int addBook(Book book);

    @Select("select * from student where sid = #{sid}")
    Student getStudentBySid(int sid);

    @Select("select * from book where bid = #{bid}")
    Book getBookByBid(int bid);

}
