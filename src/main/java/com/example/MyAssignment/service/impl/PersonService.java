package com.example.MyAssignment.service.impl;

import com.example.MyAssignment.entity.Person;
import com.example.MyAssignment.repository.PersonRepository;
import com.example.MyAssignment.request.CommonFriendsRequest;
import com.example.MyAssignment.request.FriendshipDetailRequest;
import com.example.MyAssignment.request.ValidUpdatesReceiverRequest;
import com.example.MyAssignment.response.parent_response.MainResponse;
import com.example.MyAssignment.service.parent.IFriendshipService;
import com.example.MyAssignment.service.parent.IPersonService;
import com.example.MyAssignment.service.parent.IUpdatesService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService implements IPersonService {

    final IFriendshipService friendshipService;

    final PersonRepository personRepository;

    final IUpdatesService updatesService;

    public PersonService(IFriendshipService friendshipService, PersonRepository personRepository, IUpdatesService updatesService) {
        this.friendshipService = friendshipService;
        this.personRepository = personRepository;
        this.updatesService = updatesService;
    }

    @Override
    public MainResponse<String> getFriendshipDetail(FriendshipDetailRequest friendshipDetailRequest) {
        if (friendshipDetailRequest.getEmail().isEmpty() || friendshipDetailRequest.getEmail() == null){
            throw new RuntimeException("request is not valid");
        }

        long idPerson = this.getIdFromEmail(friendshipDetailRequest.getEmail());
        if(idPerson == 0){
            throw new RuntimeException("email doesn't exist");
        }

        Set<String> friends = this.personRepository.findPersonFriendsById(idPerson);

        return MainResponse.success(friends);
    }


    @Override
    public MainResponse<String> getCommonFriends(CommonFriendsRequest commonFriendsRequest) {
        List<String> emails = commonFriendsRequest.getFriends();
        if (emails.size() != 2){
            throw new RuntimeException("request is not valid");
        }
        for (String email : emails){
            if(!isExistEmail(email)){
                throw new RuntimeException("email doesn't exist");
            }
        }
        List<String> commonFriends = this.getCommonFriendsDetail(commonFriendsRequest.getFriends());
        return MainResponse.success(commonFriends);
    }

    @Override
    public MainResponse<String> getEmailOfValidUpdatesReceiver(ValidUpdatesReceiverRequest updatesReceiverRequest) {
        long personId = this.getIdFromEmail(updatesReceiverRequest.getSender());
        if (personId == 0L){
            throw new RuntimeException("email doesn't exist");
        }
        Set<Integer> validReceivers = this.updatesService.findValidReceiver(personId);

        Set<String> response = validReceivers.stream().map(this::getEmailFromId).collect(Collectors.toSet());
        return MainResponse.success(response);
    }

    @Override
    public long getPersonIdFromEmail(String email) {
        return this.getIdFromEmail(email);
    }

    @Override
    public Optional<Person> getPersonFromEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Person> getPersonFromId(long personId) {
        return this.findById(personId);
    }


    private List<String> getCommonFriendsDetail(List<String> friends) {
        long person1 = this.getIdFromEmail(friends.get(0));
        long person2 = this.getIdFromEmail(friends.get(1));
        Set<Integer> commonFriendId = this.friendshipService.getCommonFriendId(person1, person2);
        return commonFriendId.stream()
                .map(this::getEmailFromId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Optional<Person> findById(long personId){
        return this.personRepository.findById(personId);
    }

    private Optional<Person> findByEmail(String email){
        return this.personRepository.findByEmail(email);
    }
    private long getIdFromEmail(String email) {
        return this.personRepository.findByEmail(email).map(Person::getId).orElse(0L);
    }

    private String getEmailFromId(long personId) {
        return this.findById(personId).map(Person::getEmail).orElse(null);
    }


    public boolean isExistEmail(String email) {
        return !findByEmail(email).isEmpty();
    }
}
