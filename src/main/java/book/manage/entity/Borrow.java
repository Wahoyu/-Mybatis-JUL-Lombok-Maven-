package book.manage.entity;

import lombok.Data;

@Data
public class Borrow {
    int id;
    //做个映射
    Student student;
    Book book;
}
