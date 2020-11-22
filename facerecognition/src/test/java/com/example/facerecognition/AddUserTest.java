package com.example.facerecognition;

import com.example.facerecognition.controller.RecognitionController;
import com.example.facerecognition.model.UserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RecognitionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AddUserTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecognitionController userRecognition;


    @Test
    public void addUserTest() throws Exception {
        UserDetails obj = new UserDetails();
        obj.setId(1);
        obj.setUsername("krishna");

        String inputJson = this.mapToJson(obj);
        String uri = "/addUser";

        Mockito.when(userRecognition.addUser(Mockito.any(UserDetails.class))).thenReturn(obj);

        RequestBuilder requestbuilder = MockMvcRequestBuilders
                .post(uri)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestbuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(inputJson);

    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        return objMapper.writeValueAsString(object);
    }
}
