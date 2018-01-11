import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/* 
Нити и байты
Читайте с консоли имена файлов, пока не будет введено слово "exit".
Передайте имя файла в нить ReadThread.
Нить ReadThread должна найти байт, который встречается в файле максимальное число раз, и добавить его в словарь resultMap,
где параметр String - это имя файла, параметр Integer - это искомый байт.
Закрыть потоки.


Требования:
1. Программа должна считывать имена файлов с консоли, пока не будет введено слово "exit".
2. Для каждого файла создай нить ReadThread и запусти ее.
3. После запуска каждая нить ReadThread должна создать свой поток для чтения из файла.
4. Затем, нити должны найти максимально встречающийся байт в своем файле и добавить его в словарь resultMap.
5. Поток для чтения из файла в каждой нити должен быть закрыт.
*/


public class Solution {
    public static Map<String, Integer> resultMap = new HashMap<String, Integer>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String filename = reader.readLine();
            if (filename.equals("exit"))
                break;
            new ReadThread(filename).start();
        }
    }

    public static class ReadThread extends Thread {
        String fileName;

        public ReadThread(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void run() {
            Map<Integer, Integer> hm = new HashMap<>();
            try {
                FileInputStream fr = new FileInputStream(fileName);
                while (fr.available() > 0) {
                    int b = fr.read();
                    Integer value = hm.get(b);
                    hm.put(b, ((value == null)? 1 : value + 1));
                }
                fr.close();
                Integer bt = null;
                int counter = 0;
                for (Map.Entry<Integer, Integer> item : hm.entrySet())
                    if (item.getValue() > counter) {
                        counter = item.getValue();
                        bt = item.getKey();
                    }
                resultMap.put(fileName, bt);
            } catch (IOException ex) {
            }
        }
    }
}