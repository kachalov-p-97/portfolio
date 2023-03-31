
public class Concatenation {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        StringBuilder builder;
        builder = new StringBuilder();

//        String str = "";
        for (int i = 0; i < 20_000; i++) {

            builder
                .append("some ").append("test ")
                .append("some ").append("test ")
                .append("some ").append("test ");
//            str += "some text some text some text";
        }

        System.out.println((System.currentTimeMillis() - start) + " ms");
    }
}
