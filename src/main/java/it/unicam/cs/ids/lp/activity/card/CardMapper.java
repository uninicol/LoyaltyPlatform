package it.unicam.cs.ids.lp.activity.card;

import it.unicam.cs.ids.lp.activity.Activity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;

@Service
public class CardMapper implements BiFunction<CardRequest, Activity, Card> {
    @Override
    public Card apply(CardRequest cardRequest, Activity activity) {
        Card card = new Card();
        card.setName(cardRequest.name());
        card.setProgram(cardRequest.program());
        card.setRules(cardRequest.rules());
        card.setActivities(List.of(activity));
        activity.setCard(card);
        return card;
    }
}
