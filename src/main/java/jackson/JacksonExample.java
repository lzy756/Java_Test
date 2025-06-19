package jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;

public class JacksonExample {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person();
        person.setAge(123);
        person.setName("fakes0u1");

        //输出为字符串
        try {
            String json = objectMapper.writeValueAsString(person);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Person {
    private String name;
    private int age;

    // 必须提供无参构造函数
    public Person() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}