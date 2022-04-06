package com.example.MyAssignment.controller;


import com.example.MyAssignment.request.CommonFriendsRequest;
import com.example.MyAssignment.request.FriendshipDetailRequest;
import com.example.MyAssignment.request.ValidUpdatesReceiverRequest;
import com.example.MyAssignment.response.parent_response.MainResponse;
import com.example.MyAssignment.service.parent.IPersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    final IPersonService personService;

    public PersonController(IPersonService personService) {
        this.personService = personService;
    }


    @PostMapping("friendship_detail")
    public ResponseEntity<MainResponse> friendshipDetail(@RequestBody FriendshipDetailRequest friendshipDetailRequest){
        try{
            MainResponse<String> response = this.personService.getFriendshipDetail(friendshipDetailRequest);
            return ResponseEntity.ok(response);
        }catch (Exception ex){
            return ResponseEntity.ok(MainResponse.error(ex.getMessage()));
        }
    }


    @PostMapping("common_friends")
    public ResponseEntity<MainResponse> getCommonFriends(@RequestBody CommonFriendsRequest commonFriendsRequest){
        try{
            MainResponse<String> response = this.personService.getCommonFriends(commonFriendsRequest);
            return ResponseEntity.ok(response);
        }catch (Exception ex){
            return ResponseEntity.ok(MainResponse.error(ex.getMessage()));
        }
    }

    @PostMapping("valid_updates_receiver")
    public ResponseEntity<MainResponse> getValidUpdatesReceiver(@RequestBody ValidUpdatesReceiverRequest updatesReceiverRequest){
        try{
            MainResponse<String> response = this.personService.getEmailOfValidUpdatesReceiver(updatesReceiverRequest);
            return ResponseEntity.ok(response);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.ok(MainResponse.error(ex.getMessage()));
        }
    }

}
