package com.example.firstdemo;



import com.example.firstdemo.Service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class FirstDemoApplicationTests {
    @Autowired
    AuthService authService;
    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException, ClassNotFoundException {

        authService.removeById("1");


//        chatUserService.removeById("001");
//        Connection connection = dataSource.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement("show tables;");
//        ResultSet resultSet = preparedStatement.executeQuery();
//        while(resultSet.next()){
//            System.out.println(resultSet.getString(1));
//
//            PreparedStatement preparedStatement1 = connection.prepareStatement("ALTER TABLE " + resultSet.getString(1) + " ADD COLUMN `is_del` integer NULL DEFAULT 0 COMMENT '是否删除'");
//            boolean execute = preparedStatement1.execute();
//            System.out.println(execute);
//
//        }


    }

}
