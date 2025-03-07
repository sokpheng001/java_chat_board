
import connection.Client;
import view.UIWithoutAccount;

import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) throws IOException {
        //
        new UIWithoutAccount().home(new Client());
    }
}
