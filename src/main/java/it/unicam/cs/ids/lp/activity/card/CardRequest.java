package it.unicam.cs.ids.lp.activity.card;

import java.util.List;

record CardRequest(List<Rule<?>> rules,
                   Card.CardProgram program) {
}
