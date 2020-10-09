import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        char[] in = new char[50];
        int size = 0;

        try
        {
            File file = new File("/home/codex/Desktop/bill/Polymophic-phone-bill/src/main/resources/file_storage/storage.txt");

            FileWriter fw = new FileWriter(file);

            fw.write("sms data phonecall\n25 14 3");
            fw.flush();
            fw.close();

            FileReader fr = new FileReader(file);

            size = fr.read(in);
            System.out.println(size + " ");

            List<String> list = new ArrayList<>();


            for (char c: in){
                System.out.print(c);
            }


            fr.close();
        }catch(Exception e)
        {
        }
    }
}
