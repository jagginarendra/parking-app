package com.parking.app.controller;

import com.parking.app.exception.NoSpotAvilableException;
import com.parking.app.model.client.ParkResponse;
import com.parking.app.service.ParkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {ParkingWebController.class})
@WebMvcTest
public class TestParkingWebController {


    @MockBean
    private ParkService service;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testParkingController() throws Exception {
        when(service.park(any())).thenReturn(new ParkResponse(100, "18-04-2020 15:43:22", 1000));
        this.mockMvc.perform(post("/parkingservice/park").content(getParkRequestJson()).contentType(MediaType.APPLICATION_JSON))
                //.andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("1000")));
    }

    @Test
    public void testParkingControllerBadRequest() throws Exception {
        when(service.park(any())).thenThrow(NoSpotAvilableException.class);
        this.mockMvc.perform(post("/parkingservice/park").content(getParkRequestJson()).contentType(MediaType.APPLICATION_JSON))
                //.andDo(print()).andExpect(status().isOk())
                .andExpect(status().is4xxClientError());
    }

    private String getParkRequestJson(){
        return "{\"customer\":{\"first_name\":\"Ravi\",\"last_name\":\"Ashwin6\",\"address\":\"PGI\",\"phone\":\"9872344122\",\"customer_type\":\"GENERAL\"},\"vehicle\":{\"vehicle_type\":\"MOTORCYCLE\",\"registration_number\":\"JK 11 12222\",\"color\":\"RED\"},\"parking_lot_id\":5000,\"isCarPooled\":false}";
    }

    //contains wrong customer type
    private String getFaultyParkRequestJson(){
        return "{\"customer\":{\"first_name\":\"Ravi\",\"last_name\":\"Ashwin6\",\"address\":\"PGI\",\"phone\":\"9872344122\",\"customer_type\":\"GENERAL\"},\"vehicle\":{\"vehicle_type\":\"MOTORCYCLE\",\"registration_number\":\"JK 11 12222\",\"color\":\"RED\"},\"parking_lot_id\":5000,\"isCarPooled\":false}";

    }


}
