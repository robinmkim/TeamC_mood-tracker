package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.ChatDao;
import com.teamc.moodtracker.dao.MemberDao;
import com.teamc.moodtracker.dto.chat.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatDao chatDao;
    private final MemberDao memberDao;

    @Transactional
    public List<ChatRoom> getChatRooms(int memberId) {
        List<ChatRoom> chatRooms = chatDao.getChatRooms(memberId);
        List<ChatRoom> chatMembers = chatDao.getMemberInfo(memberId);
        System.out.println("chatRooms: " + chatRooms.toString());
        System.out.println("chatMembers: " + chatMembers.toString());
        // roomId가 일치하는 경우를 찾아 합치기
        List<ChatRoom> result = new ArrayList<>();
        for (ChatRoom room : chatRooms) {
            Optional<ChatRoom> matchingMember = chatMembers.stream()
                    .filter(member -> member.getRoomId() == room.getRoomId())
                    .findFirst();

            // roomId가 일치하는 경우 합치기
            if (matchingMember.isPresent()) {
                ChatRoom combinedRoom = new ChatRoom();
                combinedRoom.setRoomId(room.getRoomId());
                combinedRoom.setOtherMemberId(matchingMember.get().getOtherMemberId());
                combinedRoom.setOtherMemberName(matchingMember.get().getOtherMemberName());
                combinedRoom.setMessage(room.getMessage());
                combinedRoom.setSendTime(room.getSendTime());
                result.add(combinedRoom);
            }
        }
        // sendTime을 기준으로 내림차순 정렬
        result.sort(Comparator.comparing(ChatRoom::getSendTime).reversed());

        return result;
    }
    // public List<ChatRoom> getChatRooms(int memberId) {
    // List<ChatRoom> chatRooms = chatDao.getChatRooms(memberId);
    // List<ChatRoom> chatMembers = chatDao.getMemberInfo(memberId);
    // System.out.println("chatRooms: " + chatRooms.size());
    // System.out.println("chatMembers: " + chatMembers.size());
    //
    // for(int i=0; i<chatRooms.size(); i++) {
    // int roomId = chatRooms.get(i).getRoomId();
    // }
    // return chatRooms;
    // }

    public List<ChatMessage> getChatMessages(int memberId, int roomId) {
        if (chatDao.checkMemberInChatRoom(memberId, roomId) == 0) {
            return null;
        } else {
            List<ChatMessage> chatMessages = chatDao.getChatMessages(roomId, memberId);
            return chatMessages;
        }
    }

    public ResponseMessage saveChatMessage(SaveChat saveChat) {
        saveChat.setStatus("MESSAGE");
        chatDao.saveChatMessage(saveChat);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .roomId(saveChat.getRoomId())
                .memberId(saveChat.getMemberId())
                .memberName(memberDao.getMemberName(saveChat.getMemberId()))
                .message(saveChat.getMessage())
                .sendTime(LocalDateTime.now())
                .status(saveChat.getStatus())
                .build();
        return responseMessage;
    }

    public Integer findExistingChatRoom(EnterDto memberIds) {
        Integer foundRoomId = chatDao.findExistingChatRoom(memberIds);
        if (foundRoomId == null) {
            return null;
        }
        return foundRoomId;
    }

    // 새로운 방 생성
    @Transactional
    public ResponseRoom createChatRoom(EnterDto enterDto, int newRoomId) {
        SaveChat user1 = SaveChat.builder()
                .roomId(newRoomId)
                .memberId(enterDto.getMyMemberId())
                .message("")
                .status("JOIN")
                .build();
        SaveChat user2 = SaveChat.builder()
                .roomId(newRoomId)
                .memberId(enterDto.getOtherMemberId())
                .message("")
                .status("JOIN")
                .build();
        chatDao.saveChatMessage(user1);
        chatDao.saveChatMessage(user2);
        String newMemberName = memberDao.getMemberName(enterDto.getOtherMemberId());
        ResponseRoom responseRoom = ResponseRoom.builder()
                .roomId(newRoomId)
                .newMemberId(enterDto.getOtherMemberId())
                .newMemberName(newMemberName)
                .build();
        return responseRoom;
    }

    // 기존 방에 다시 참여
    public ResponseRoom joinExistingChatRoom(EnterDto memberIds, int foundRoomId) {
        SaveChat joinChat = SaveChat.builder()
                .roomId(foundRoomId)
                .memberId(memberIds.getMyMemberId())
                .message("")
                .status("JOIN")
                .build();
        chatDao.saveChatMessage(joinChat);
        String memberName = memberDao.getMemberName(memberIds.getOtherMemberId());
        ResponseRoom responseRoom = ResponseRoom.builder()
                .roomId(foundRoomId)
                .newMemberId(memberIds.getOtherMemberId())
                .newMemberName(memberName)
                .build();
        return responseRoom;
    }

    public int newChatRoomId() {
        int newRoomId = chatDao.newChatRoom();
        return newRoomId;
    }

    public void exitChatRoom(int meberId, int roomId) {
        chatDao.exitChatRoom(meberId, roomId);
    }

    //
    public String getLastMsgStatus(int roomId, int memberId) {
        String getLastMsgStatus = chatDao.getLastMsgStatus(roomId, memberId);
        return getLastMsgStatus;
    }


}
