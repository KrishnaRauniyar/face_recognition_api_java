package com.example.facerecognition.controller;
import com.example.facerecognition.SendCallToFlask;
import com.example.facerecognition.model.UserDetails;
import com.example.facerecognition.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class RecognitionController {

    @Autowired
    UserRepository repo;
    @PostMapping("/addUser")
    public UserDetails addUser(@RequestBody UserDetails info) {
        System.out.println(repo);
        repo.save(info);
        return info;
    }
    //--> /train endpoint to upload image in known_face_images
    @RequestMapping(value = "/train", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> trainFace(@RequestParam("file") MultipartFile file) throws IOException {

//		save image file to the directory
        String dir_train_name = "known_face_images/";
        File convertfile = new File(dir_train_name+ file.getOriginalFilename());
        convertfile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertfile);
        fout.write(file.getBytes());
        fout.close();

//		SendCallFlask class to send the saved image to flask url to /train endpoint
        SendCallToFlask trainimage = new SendCallToFlask();
        String response = trainimage.SendImageFlask(dir_train_name + file.getOriginalFilename(), "train");
        System.out.println("Response to train endpoint: "+ response);

        return new ResponseEntity<>("Image Uploaded.", HttpStatus.OK);

    }
    //--> /recognize endpoint to upload video of person in video_data directory
    @RequestMapping(value = "/recognize", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> testImage(@RequestParam("file") MultipartFile file) throws IOException {
        // save the video file to the video_data directory
        String dir_test_name = "video_data/";
        File convertfile = new File(dir_test_name+ file.getOriginalFilename());
        convertfile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertfile);
        fout.write(file.getBytes());
        fout.close();

//		SendCallFlask class to send the saved video to flask url to /recognize endpoint
        SendCallToFlask testface = new SendCallToFlask();
        String response = testface.SendImageFlask(dir_test_name + file.getOriginalFilename(), "recognize");
        System.out.println("Response after the recognition: "+ response);

//        String[] splited_user_code = file.getOriginalFilename().split("_", 3);
//        String merged_code = splited_user_code[0]+'_'+splited_user_code[1];
//        boolean login_status = false;
//        System.out.println(response);
//        System.out.println(merged_code);
//        if (merged_code == response) {
//            login_status = true;
//            System.out.println("Entered to the positive state");
//        }
//        System.out.println("this is user code: "+ merged_code);
        return new ResponseEntity<>("Predicted User: "+ response, HttpStatus.OK);
    }
}
