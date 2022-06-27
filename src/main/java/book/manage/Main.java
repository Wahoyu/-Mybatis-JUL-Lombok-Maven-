package book.manage;


import book.manage.entity.Book;
import book.manage.entity.Student;
import book.manage.sql.SqlUtil;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.LogManager;

@Log
public class Main {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            //日志配置
            LogManager manager = LogManager.getLogManager();
            manager.readConfiguration(Resources.getResourceAsStream("logging.properties"));

            while (true) {
                System.out.println("=================================================================");
                System.out.println("1.录入学生信息");
                System.out.println("2.录入书籍信息");
                System.out.println("3.添加借阅信息");
                System.out.println("4.查询借阅信息");
                System.out.println("5.查询学生信息");
                System.out.println("6.查询书籍信息");
                System.out.print("输入你想要执行的操作（输入其他任意数字退出）：");
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
                    case 3:
                        try{addBorrow(scanner);}
                        catch(Exception e){
                            System.out.println("录入失败！（可能录入重复）");
                        }
                        finally {
                            break;
                        }
                    case 4:
                        showBorrow(scanner);
                        break;
                    case 5:
                        showStudent(scanner);
                        break;
                    case 6:
                        showBook(scanner);
                        break;
                    default:
                        return;
                }
            }
        }
    }

    //显示全部学生信息
    private static void showStudent(Scanner scanner){
        //不输入直接显示全部
        SqlUtil.doSqlWork(mapper ->{
            mapper.getStudentList().forEach(student -> {
                System.out.println(student.getSid()+"."+student.getName()+" "+student.getSex()+" "+student.getGrade());
            });
        });
    }


    //显示全部书籍信息
    private static void showBook(Scanner scanner){
        //不输入直接显示全部
        SqlUtil.doSqlWork(mapper ->{
            mapper.getBookList().forEach(book -> {
                System.out.println(book.getBid()+"."+book.getTitle()+"["+book.getPrice()+"]"+"("+book.getDesc()+")");
            });
        });
    }


    //显示全部借阅信息
    private static void showBorrow(Scanner scanner){
        //不输入直接显示全部
        SqlUtil.doSqlWork(mapper ->{
            mapper.getBorrowList().forEach(borrow -> {
                    System.out.println(borrow.getStudent().getName()+" -> "+borrow.getBook().getTitle());
            });
        });
    }

    //借书
    private static void addBorrow(Scanner scanner){
        System.out.print("请输入书的编号:");
        String a = scanner.nextLine();
        int bid = Integer.parseInt(a);
        System.out.print("请输入学号:");
        String b = scanner.nextLine();
        int sid = Integer.parseInt(b);

        SqlUtil.doSqlWork(mapper -> {
            int i = mapper.addBorrow(sid,bid);
            if(i>0){
                System.out.println("录入成功！");
                log.info("添加一条借阅信息");
            }
            else{
                System.out.println("录入失败，请重试！");
            }
        });
    }

    //插入学生
    private static void addStudent(Scanner scanner){
        System.out.print("请输入学生名字：");
        String name = scanner.nextLine();
        System.out.print("请输入学生的性别（男/女）：");
        String sex = scanner.nextLine();
        System.out.print("请输入学生的年级：");
        String grade = scanner.nextLine();
        int g = Integer.parseInt(grade);

        //创建学生对象
        Student student = new Student(name,sex,g);
        SqlUtil.doSqlWork(mapper -> {
            int i = mapper.addStudent(student);
            if(i>0){
                System.out.println("录入成功！");
                log.info("新添加一条学生信息"+student);
            }else{
                System.out.println("录入失败，请重试！");
            }
        });
    }
    //插入书
    private static void addBook(Scanner scanner){
        System.out.print("请输入书名：");
        String title = scanner.nextLine();
        System.out.print("请输入书籍简介：");
        String desc = scanner.nextLine();
        System.out.print("请输入书的价格");
        String price = scanner.nextLine();
        double p= Double.parseDouble((price));

        //创建书籍对象
        Book book = new Book(title,desc,p);

        //匿名内部类其实就是在new的时候，直接对接口或是抽象类的实现
        //lambda表达式其实就是我们接口匿名实现的简化
        //(传入doSqlWork方法的参数)->{实现接口方法的方法体}
        SqlUtil.doSqlWork(mapper -> {
            int i = mapper.addBook(book);
            if(i>0){
                System.out.println("录入成功！");
                log.info("新添加了一条书籍信息"+book);
            }else{
                System.out.println("录入失败，请重试！");
            }
        });
    }

}
