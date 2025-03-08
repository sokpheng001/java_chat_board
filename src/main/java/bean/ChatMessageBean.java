package bean;

import client.repository.ChatMessageRepository;
import client.service.ChatMessageServiceImpl;
import mapper.ChatMessageMapper;

public class ChatMessageBean {
    public static final ChatMessageRepository chatMessageRepository = new ChatMessageRepository();
    public static final ChatMessageMapper chatMessageMapper = new ChatMessageMapper();
    public static final ChatMessageServiceImpl chatMessageService = new ChatMessageServiceImpl();
}
