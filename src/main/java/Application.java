import server.Server;
import view.UIWithoutAccount;

public class Application {
    public static void main(String[] args) {
        // server call for clients
        Server.startServer();
        //
        UIWithoutAccount.home();
    }
}
