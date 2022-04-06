package com.example.MyAssignment.service.parent;

import com.example.MyAssignment.entity.Person;
import com.example.MyAssignment.request.FriendshipCreationRequest;
import com.example.MyAssignment.response.parent_response.MainResponse;

import java.util.Set;

public interface IFriendshipService {

    Set<Integer> getCommonFriendId(long personId1, long personId2);

    void createFriendship(Person person1, Person person2);

    MainResponse<Object> createFriendshipConnection(FriendshipCreationRequest friendshipCreationRequest);

    Set<Integer> findValidFriendIdForUpdates(long personId);
}
