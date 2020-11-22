package com.example.facerecognition;

import com.example.facerecognition.controller.RecognitionController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SendCallToFlask.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrainTest {

    @MockBean
    private SendCallToFlask callToFlask;

    @Test
    public void uploadImage() throws Exception {

        String imagePath = "known_face_images/1_krishna.jpg";
        String state = "train";

        when(callToFlask.SendImageFlask(imagePath, state)).thenReturn("200");

        assertEquals("200", callToFlask.SendImageFlask(imagePath, state));


    }
}
