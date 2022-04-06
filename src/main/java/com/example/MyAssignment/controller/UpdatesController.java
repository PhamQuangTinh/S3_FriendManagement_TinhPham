package com.example.MyAssignment.controller;

import com.example.MyAssignment.request.UpdatesBlockRequest;
import com.example.MyAssignment.request.UpdatesCreateRequest;
import com.example.MyAssignment.response.parent_response.MainResponse;
import com.example.MyAssignment.service.parent.IUpdatesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/updates")
public class UpdatesController {

    final IUpdatesService updatesService;

    public UpdatesController(IUpdatesService updatesService) {
        this.updatesService = updatesService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUpdates(@RequestBody UpdatesCreateRequest updatesCreateRequest){
        try{
            MainResponse<Object> response = this.updatesService.createUpdates(updatesCreateRequest);
            return ResponseEntity.ok(response);
        }catch (Exception ex){
            return ResponseEntity.ok(MainResponse.error(ex.getMessage()));
        }

    }

    @PostMapping("/block")
    public ResponseEntity<?> blockUpdates(@RequestBody UpdatesBlockRequest updatesBlockRequest){
        try{
            MainResponse<Object> response = this.updatesService.blockUpdates(updatesBlockRequest);
            return ResponseEntity.ok(response);
        }catch (Exception ex){
            return ResponseEntity.ok(MainResponse.error(ex.getMessage()));
        }
    }
}
