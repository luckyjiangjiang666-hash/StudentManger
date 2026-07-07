package com.jxl.studentmanger;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jxl.studentmanger.mapper")
public class StudentMangerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentMangerApplication.class, args);
    }

}
