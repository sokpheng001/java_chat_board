package bean;

import client.controller.ChatConnectionController;
import client.repository.ChatConnectionRepository;
import client.service.ChatConnectionServiceImpl;
import client.service.abstraction.ChatConnectionService;

/**
 * <h>This class is created for manage the ChatConnection class's related object</h>
 * @author Kim Chansokpheng
 * @version 1.0
 */
public class ChatConnectionBean {
    public static final ChatConnectionController controller = new ChatConnectionController();
    public static final ChatConnectionService chatConnectionService = new ChatConnectionServiceImpl();
    public static final ChatConnectionRepository chatConnectionRepository = new ChatConnectionRepository();
}
