package com.liu.javaproject;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaprojectApplicationTests {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void encrytPwd(){
        String result = stringEncryptor.encrypt("mySecretKey");
        System.out.println("==================");
        System.out.println(result);
        System.out.println("==================");
    }

}
