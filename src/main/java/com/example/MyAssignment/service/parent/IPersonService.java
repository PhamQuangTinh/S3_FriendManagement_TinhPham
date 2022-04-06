package com.example.MyAssignment.service.parent;

import com.example.MyAssignment.entity.Person;
import com.example.MyAssignment.request.CommonFriendsRequest;
import com.example.MyAssignment.request.FriendshipCreationRequest;
import com.example.MyAssignment.request.FriendshipDetailRequest;
import com.example.MyAssignment.request.ValidUpdatesReceiverRequest;
import com.example.MyAssignment.response.child_response.FriendshipDetailResponse;
import com.example.MyAssignment.response.parent_response.MainResponse;

import java.util.Optional;

public interface IPersonService {
    MainResponse<String> getFriendshipDetail(FriendshipDetailRequest friendshipDetailRequest);

    MainResponse<String> getCommonFriends(CommonFriendsRequest commonFriendsRequest);

    long getPersonIdFromEmail(String email);

    Optional<Person> getPersonFromId(long id);

    Optional<Person> getPersonFromEmail(String email);

    MainResponse<String> getEmailOfValidUpdatesReceiver(ValidUpdatesReceiverRequest updatesReceiverRequest);
}
