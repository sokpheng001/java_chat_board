import connection.server.Server;
import view.UIWithoutAccount;

public class Application {
    public static void main(String[] args) {
        // network.server call for clients
        Server.startServer();
        //
        UIWithoutAccount.home();
    }
}
