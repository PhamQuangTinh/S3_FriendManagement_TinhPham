package com.example.MyAssignment.response.child_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class FriendshipDetailResponse{
    private Set<String> friends;

    public FriendshipDetailResponse(Set<String> friends) {
        this.friends = friends;
    }
}
