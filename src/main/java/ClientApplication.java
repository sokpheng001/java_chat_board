
import connection.Client;
import server.repository.ServerRepository;
import utils.LoadingFileData;
import view.UIWithoutAccount;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Properties;

public class ClientApplication { ;
    private static final Properties properties = LoadingFileData.loadingProperties();
    private static int serverPort = serverPort = Integer.parseInt(properties.getProperty("server_port"));;
    private static   String serverIpAddress = Objects.requireNonNull(ServerRepository.findFirstServerRowData()).ipAddress();

    public static void main(String[] args) throws IOException {
        new UIWithoutAccount().home(new Client(), new Socket(serverIpAddress, serverPort));
    }
}
