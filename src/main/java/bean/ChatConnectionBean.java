package bean;

import client.controller.ChatConnectionController;
import client.service.ChatConnectionServiceImpl;
import client.service.abstraction.ChatConnectionService;

public class ChatConnectionBean {
    public static final ChatConnectionController controller = new ChatConnectionController();
    public static final ChatConnectionService chatConnectionService = new ChatConnectionServiceImpl();
}
