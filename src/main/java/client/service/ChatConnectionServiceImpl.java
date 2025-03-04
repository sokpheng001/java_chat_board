package client.service;

import bean.ChatConnectionBean;
import bean.UserBean;
import client.repository.ChatConnectionRepository;
import client.service.abstraction.ChatConnectionService;
import mapper.ChatConnectionManualMapper;
import model.ChatConnection;
import model.User;
import model.dto.CreateChatConnectionUsingUserNameDto;
import model.dto.ResponseChatConnectionDto;
import model.dto.ResponseUserDto;
import utils.WriteDataForVerifyLoginStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ChatConnectionServiceImpl implements ChatConnectionService {


    @Override
    public ResponseChatConnectionDto getConnectionByUserUuid(String userUuid) {
        return null;
    }

    @Override
    public int createNewConnection(CreateChatConnectionUsingUserNameDto createChatConnectionUsingUserNameDto) {
        // find connected user by name in repository
        User connectedUser = UserBean.userRepository.findByUsername(createChatConnectionUsingUserNameDto.wantedToConnectedUserName());
        // get current User Login
        String getCurrentUserLoginUuid = String.valueOf(WriteDataForVerifyLoginStatus.isLogin());
        User currentUserLogin =  UserBean.userRepository.findUserByUuid(getCurrentUserLoginUuid);
        // create chat connection object
        ChatConnection connection  =
                ChatConnection.builder()
                        .uuid(UUID.randomUUID().toString())
                        .id(new Random().nextLong(999999999))// id is optional because we have auto generating id in database table
                        .loginUserId(currentUserLogin.getId())
                        .wantedToConnectUserId(connectedUser.getId())
                        .build();
        //
       if(ChatConnectionBean.chatConnectionRepository.save(connection)>0){
           return 1;
       }
       return 0;
    }

    @Override
    public List<ResponseUserDto> getAllConnectedUserByUserLoginUuid(String uuid) {
        // get current user
        User currentUser = UserBean.userRepository.findUserByUuid(uuid);
        //
        List<ResponseUserDto> connectedUsers = new ArrayList<>();
        ChatConnectionBean.chatConnectionRepository.getAllConnectedUserByUserLoginId(currentUser.getId().intValue())
                .forEach(e->connectedUsers.add(UserBean.userManualMapper.fromUserToResponseUserDto(e)));
        return connectedUsers;
    }
}
