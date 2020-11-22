package com.example.facerecognition;

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
public class RecognizeTest {
    @MockBean
    private SendCallToFlask callToFlask;

    @Test
    public void uploadVideo() throws Exception {

        String videoPath = "video_data/krishna_video.mp4";
        String state = "recognize";

        when(callToFlask.SendImageFlask(videoPath, state)).thenReturn("200");

        assertEquals("200", callToFlask.SendImageFlask(videoPath, state));


    }
}
