package book.manage;


import book.manage.entity.Book;
import book.manage.entity.Student;
import book.manage.sql.SqlUtil;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("**************************");
                System.out.println("1.录入学生信息");
                System.out.println("2.录入书籍信息");
                System.out.println("输入你想要执行的操作（输入其他任意数字退出）：");
                int input;
                try {
                    input = scanner.nextInt();
                }catch(Exception e){
                    return;
                }
                //清理换行符
                scanner.nextLine();
                switch(input){
                    case 1:
                        addStudent(scanner);
                        break;
                    case 2:
                        addBook(scanner);
                        break;
                    default:
                        return;
                }
            }
        }
    }
    //插入学生
    private static void addStudent(Scanner scanner){
        System.out.println("请输入学生名字：");
        String name = scanner.nextLine();
        System.out.println("请输入学生的性别（男/女）：");
        String sex = scanner.nextLine();
        System.out.println("请输入学生的年级：");
        String grade = scanner.nextLine();
        int g = Integer.parseInt(grade);

        //创建学生对象
        Student student = new Student(name,sex,g);
        SqlUtil.doSqlWork(mapper -> {
            int i = mapper.addStudent(student);
            if(i>0){
                System.out.println("录入成功！");
            }else{
                System.out.println("录入失败，请重试！");
            }
        });
    }
    //插入书
    private static void addBook(Scanner scanner){
        System.out.println("请输入书名：");
        String title = scanner.nextLine();
        System.out.println("请输入书籍简介：");
        String desc = scanner.nextLine();
        System.out.println("请输入书的价格");
        String price = scanner.nextLine();
        double p= Double.parseDouble((price));

        //创建书籍对象
        Book book = new Book(title,desc,p);
        SqlUtil.doSqlWork(mapper -> {
            int i = mapper.addBook(book);
            if(i>0){
                System.out.println("录入成功！");
            }else{
                System.out.println("录入失败，请重试！");
            }
        });
    }

}
