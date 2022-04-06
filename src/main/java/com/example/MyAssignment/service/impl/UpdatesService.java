package com.example.MyAssignment.service.impl;

import com.example.MyAssignment.entity.Person;
import com.example.MyAssignment.entity.Updates;
import com.example.MyAssignment.repository.UpdatesRepository;
import com.example.MyAssignment.request.UpdatesBlockRequest;
import com.example.MyAssignment.request.UpdatesCreateRequest;
import com.example.MyAssignment.response.parent_response.MainResponse;
import com.example.MyAssignment.service.parent.IFriendshipService;
import com.example.MyAssignment.service.parent.IPersonService;
import com.example.MyAssignment.service.parent.IUpdatesService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UpdatesService implements IUpdatesService {

    final UpdatesRepository updatesRepository;

    final IPersonService personService;

    final IFriendshipService friendshipService;

    public UpdatesService(UpdatesRepository updatesRepository, @Lazy IPersonService personService, @Lazy IFriendshipService friendshipService) {
        this.updatesRepository = updatesRepository;
        this.personService = personService;
        this.friendshipService = friendshipService;
    }

    @Override
    @Transactional
    public MainResponse<Object> createUpdates(UpdatesCreateRequest updatesCreateRequest) {
        long requestorId = this.personService.getPersonIdFromEmail(updatesCreateRequest.getRequestor());
        long targetId = this.personService.getPersonIdFromEmail(updatesCreateRequest.getTarget());
        if (requestorId == 0 || targetId == 0){
            throw new RuntimeException("email doesn't exist");
        }
        this.createUpdatesForPerson(requestorId, targetId);
        return MainResponse.success();
    }

    @Override
    @Transactional
    public MainResponse<Object> blockUpdates(UpdatesBlockRequest updatesBlockRequest) {
        long requestorId = this.personService.getPersonIdFromEmail(updatesBlockRequest.getRequestor());
        long targetId = this.personService.getPersonIdFromEmail(updatesBlockRequest.getTarget());
        if (requestorId == 0 || targetId == 0){
            throw new RuntimeException("email doesn't exist");
        }
        this.blockUpdatesForPerson(requestorId, targetId);
        return MainResponse.success();
    }

    @Override
    public boolean checkValidUpdates(long requestorId, long target) {
        Optional<Updates> updates = this.updatesRepository.findByUpdateIdAndPerson_Id(target, requestorId);
        return updates == null || (updates != null && updates.get().getStatus() == 1);
    }

    @Override
    public Set<Integer> findValidReceiver(long personId) {
        Set<Integer> validUpdatesPerson = this.updatesRepository.findValidReceiver(personId);
        Set<Integer> validFriendsPerson = this.friendshipService.findValidFriendIdForUpdates(personId);
        return Arrays.asList(validFriendsPerson, validUpdatesPerson)
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Transactional
    public void blockUpdatesForPerson(long requestorId, long targetId) {
        this.updatesRepository.updateStatusUpdates(requestorId, targetId, 0);
    }

    @Transactional
    public void createUpdatesForPerson(long requestorId, long targetId) {
        Optional<Person> personOptional = this.personService.getPersonFromId(requestorId);
        personOptional.ifPresent(person -> {
            Updates updates = new Updates();
            updates.setUpdateId(targetId);
            updates.setPerson(person);
            if (person.getUpdates() == null){
                person.setUpdates(Collections.singleton(updates));
            }else {
                person.getUpdates().add(updates);
            }
        });
    }
}
