package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class ActivityRegistrationController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    @PutMapping("/activityRegistration/register")
    public ResponseEntity<?> registerActivity(@RequestBody ActivityRequest activityRequest) {
        Activity activity = setActivity(activityRequest);
        boolean registered = activityRegistrationService.registerActivity(activity);
        if (registered) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    private Activity setActivity(ActivityRequest activityRequest) {
        Activity activity = new Activity();
        activity.setName(activityRequest.name());
        activity.setAddress(activityRequest.address());
        activity.setTelephoneNumber(activityRequest.telephoneNumber());
        activity.setEmail(activityRequest.email());
        activity.setCategory(activityRequest.category());
        activity.setPassword(passwordEncoder.encode(activityRequest.password()));
        activity.setRegistrationDate(LocalDate.now());
        return activity;
    }

    @DeleteMapping("/activityUnregistration/{name}")
    public ResponseEntity<?> unregisterActivity(@PathVariable String name) {
        activityRegistrationService.unregisterActivityByName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected record ActivityRequest(String name, String address, String telephoneNumber, String email,
                                     Activity.ContentCategory category, String password) {
    }
}
