package com.example.MyAssignment.service.parent;

import com.example.MyAssignment.request.UpdatesBlockRequest;
import com.example.MyAssignment.request.UpdatesCreateRequest;
import com.example.MyAssignment.response.parent_response.MainResponse;

import java.util.List;
import java.util.Set;

public interface IUpdatesService {
    MainResponse<Object> createUpdates(UpdatesCreateRequest updatesCreateRequest);

    MainResponse<Object> blockUpdates(UpdatesBlockRequest updatesBlockRequest);

    boolean checkValidUpdates(long requestorId, long target);

    Set<Integer> findValidReceiver(long personId);
}
