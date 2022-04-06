package com.example.MyAssignment.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class FriendshipCreationRequest {
    private List<String> friends;
}
