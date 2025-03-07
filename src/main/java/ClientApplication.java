
import connection.Client;
import view.UIWithoutAccount;

public class ClientApplication {
    public static void main(String[] args) {
        //
        new UIWithoutAccount().home(new Client());
    }
}
