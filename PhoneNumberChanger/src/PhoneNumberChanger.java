
import java.io.*;
        import java.util.*;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class PhoneNumberChanger {
    private static List<String> lines;

    private static void readFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            lines = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException exp) {
            throw new RuntimeException(exp);
        }
    }

    private static String convert(String input) {
        String result = "+767(";
        result = result.concat(input.substring(2, 5));
        result = result.concat(")");
        result = result.concat(input.substring(5, 8));
        result = result.concat("-");
        result = result.concat(input.substring(8, 10));
        result = result.concat("-");
        result = result.concat(input.substring(10, 12));
        return result;
    }

    private static void replaceNumbers() {
        String reg = "(\\+\\d{11})";
        Pattern pattern = Pattern.compile(reg);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Matcher matcher = pattern.matcher(line);
            boolean found = matcher.find();
            if (found) {
                StringBuffer strBuffer = new StringBuffer();
                do {
                    String str = convert(matcher.group());
                    matcher.appendReplacement(strBuffer, str);
                    found = matcher.find();
                } while (found);
                matcher.appendTail(strBuffer);
                line = strBuffer.toString();
                lines.set(i, line);
            }
        }
    }

    private static void writeToFile(String fileName) {
        Writer writer;
        try {
            writer = new FileWriter(fileName);
            for (String line : lines) {
                writer.write(line);
                writer.write(System.getProperty("line.separator"));
            }
            writer.flush();
        } catch (IOException exp) {
            throw new RuntimeException(exp);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        try {
            readFromFile(fileName);
            replaceNumbers();
            writeToFile(fileName);
        } catch (RuntimeException exp) {
            System.err.println(exp.getMessage());
        }
    }
}
