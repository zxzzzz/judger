import org.xm.xmnlp.Xmnlp;

public class ApplicationDemo {
    public static void main(String[] args) {
        String text="欢迎使用我的分词器，希望你可以使用愉快！";
        System.out.println(Xmnlp.segment(text));
    }
}
