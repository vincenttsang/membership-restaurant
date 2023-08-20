package com.membership.restaurant.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EditRoomRequest {
    @JsonProperty(value = "room_intro")
    private String roomIntro;
    @JsonProperty(value = "room_name")
    private String roomName;
    @JsonProperty(value = "room_attribution")
    private List<EditRoomAttribution> roomAttribution;
    @JsonProperty(value = "sessionId")
    private String sessionId;
}

