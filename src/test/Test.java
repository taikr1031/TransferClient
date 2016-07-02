package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Test {
    public static void main(String[] args) throws IOException {


        FileInputStream in = new FileInputStream("D:/aaaaaa");


        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        FileOutputStream out = new FileOutputStream(new File("D:/NH22222220130828"));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        String temp = null;

        while ((temp = reader.readLine()) != null) {
            System.out.println(temp);
            //此处需要添加加解密的处理
            writer.write(temp);
            writer.newLine();//换行
        }

        writer.flush();
        reader.close();
        writer.close();
        in.close();
        out.close();

    }
}	
