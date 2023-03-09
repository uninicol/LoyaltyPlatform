package it.unicam.cs.ids.lp.activity.card;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activityCard")
public class CardController {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardMapper cardMapper;

    @PutMapping("/createCard/{activityId}")
    public ResponseEntity<?> createCard(@PathVariable Long activityId, @RequestBody CardRequest cardRequest) {
        Activity activity = activityRepository.findById(activityId).orElseThrow();
        Card card = cardMapper.apply(cardRequest, activity);
        cardRepository.save(card);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{cardId}/addActivity/{activityId}")
    public ResponseEntity<?> addActivity(@PathVariable long activityId, @PathVariable Long cardId) {
        cardRepository.getReferenceById(cardId)
                .getActivities()
                .add(activityRepository.findById(activityId).orElseThrow());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
