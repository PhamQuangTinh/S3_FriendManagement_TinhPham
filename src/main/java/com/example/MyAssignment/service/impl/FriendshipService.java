package com.example.MyAssignment.service.impl;

import com.example.MyAssignment.entity.Friendship;
import com.example.MyAssignment.entity.Person;
import com.example.MyAssignment.repository.FriendshipRepository;
import com.example.MyAssignment.request.FriendshipCreationRequest;
import com.example.MyAssignment.response.parent_response.MainResponse;
import com.example.MyAssignment.service.parent.IFriendshipService;
import com.example.MyAssignment.service.parent.IPersonService;
import com.example.MyAssignment.service.parent.IUpdatesService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FriendshipService implements IFriendshipService {

    final FriendshipRepository friendshipRepository;

    final IPersonService personService;

    final IUpdatesService updatesService;

    public FriendshipService(FriendshipRepository friendshipRepository, @Lazy IPersonService personService, IUpdatesService updatesService) {
        this.friendshipRepository = friendshipRepository;
        this.personService = personService;
        this.updatesService = updatesService;
    }

    @Override
    @Transactional
    public MainResponse<Object> createFriendshipConnection(FriendshipCreationRequest friendshipCreationRequest) {
        List<String> emails = friendshipCreationRequest.getFriends();
        if (emails.size() != 2){
            throw new RuntimeException("request is not valid");
        }
        Optional<Person> person1 = this.personService.getPersonFromEmail(emails.get(0));
        Optional<Person> person2 = this.personService.getPersonFromEmail(emails.get(1));
        person1.orElseThrow(() -> new RuntimeException("email doesn't exist"));
        person2.orElseThrow(() -> new RuntimeException("email doesn't exist"));
        boolean isValidUpdates = this.updatesService.checkValidUpdates(person1.get().getId(), person2.get().getId());
        if (!isValidUpdates){
            throw new RuntimeException("You're blocked");
        }
        this.createFriendship(person1.get(), person2.get());
        return MainResponse.success();
    }

    @Override
    public Set<Integer> getCommonFriendId(long personId1, long personId2) {
        return friendshipRepository.getCommonFriendId(personId1, personId2);
    }

    @Override
    public Set<Integer> findValidFriendIdForUpdates(long personId) {
        return friendshipRepository.findValidFriendIdForUpdates(personId);
    }

    @Transactional
    public void createFriendship(Person person1, Person person2) {
        Friendship friendship1 = new Friendship();
        friendship1.setPerson(person1);
        friendship1.setFriendId(person2.getId());
        if(person1.getFriends() == null){
            person1.setFriends(Collections.singleton(friendship1));
        }else{
            person1.getFriends().add(friendship1);
        }
        Friendship friendship2 = new Friendship();
        friendship2.setPerson(person2);
        friendship2.setFriendId(person1.getId());
        if(person2.getFriends() == null){
            person2.setFriends(Collections.singleton(friendship2));
        }else{
            person2.getFriends().add(friendship2);
        }
    }
}
