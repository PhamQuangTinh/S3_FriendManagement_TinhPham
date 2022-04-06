package com.example.MyAssignment.controller;

import com.example.MyAssignment.request.FriendshipCreationRequest;
import com.example.MyAssignment.response.parent_response.MainResponse;
import com.example.MyAssignment.service.parent.IFriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friendship")
public class FriendshipController {

    final IFriendshipService friendshipService;

    public FriendshipController(IFriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping("friendship_connection")
    public ResponseEntity<MainResponse> createFriendshipConnection(@RequestBody FriendshipCreationRequest friendshipCreationRequest){
        try{
            MainResponse<Object> response = this.friendshipService.createFriendshipConnection(friendshipCreationRequest);
            return ResponseEntity.ok(response);
        }catch (Exception ex){
            return ResponseEntity.ok(MainResponse.error(ex.getMessage()));
        }
    }
}
