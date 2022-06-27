package book.manage.sql;

import book.manage.mapper.BookMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author Wahoyu
 */
public class SqlUtil {

    private SqlUtil(){}

    private static SqlSessionFactory factory;

    //静态代码块只执行一次，创建一次factory
    static {
        try {
            factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Consumer是lambda表达式中的一个接口
    public static void doSqlWork(Consumer<BookMapper> consumer){
        try(SqlSession sqlSession = factory.openSession(true)){
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            consumer.accept(bookMapper);
        }
    }
}
