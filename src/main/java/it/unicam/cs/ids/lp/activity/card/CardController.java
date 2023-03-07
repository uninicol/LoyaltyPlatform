package it.unicam.cs.ids.lp.activity.card;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activityCard")
public class CardController {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CardRepository cardRepository;

    @PutMapping("/createCard/{activityId}")
    public ResponseEntity<?> createCard(@PathVariable Long activityId, @RequestBody CardRequest cardRequest) {
        //TODO funziona ma da errore di connessione(?)
        Activity activity = activityRepository.getReferenceById(activityId);
        Card card = new Card();
        card.setProgram(cardRequest.program);
        card.setRules(cardRequest.rules);
        card.setActivities(List.of(activity));
        activity.setCard(card);
        cardRepository.save(card);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{cardId}/addActivity/{activityId}")
    public ResponseEntity<?> addActivity(@PathVariable long activityId, @PathVariable Long cardId) {
        List<Activity> list = cardRepository.findById(cardId).orElseThrow()
                .getActivities();
        list.add(activityRepository.findById(activityId).orElseThrow());
        cardRepository.updateActivitiesBy(list);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    record CardRequest(List<Rule<?>> rules, Card.CardProgram program) {
    }
}
