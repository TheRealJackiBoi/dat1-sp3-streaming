public class Main {
    public static void main(String[] args) {
        Streaming stream = Streaming.getInstance();
        StreamUI ui = new StreamUI();

        stream.setUpStream();

        ui.getFrame().setVisible(true);

    }
}