package serlize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

public class Person implements java.io.Serializable {
    public String name;
    public int age;

    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Custom serialization
    private void writeObject(java.io.ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject("This is a object");
    }

    // Custom deserialization
    private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        String message = (String) s.readObject();
        System.out.println(message);
    }

    // Main method to serialize and output as hex string
    public static void main(String[] args) throws IOException {
        // Create a new Person object
        Person person = new Person("John Doe", 30);

        // Serialize the object to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(person);

        // Convert the byte array to a hexadecimal string
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            hexString.append(String.format("%02x", b));  // Convert each byte to 2-digit hex
        }

        // Output the hexadecimal string
        System.out.println(hexString.toString());

        // Close the streams
        objectOutputStream.close();
        byteArrayOutputStream.close();
    }
}
