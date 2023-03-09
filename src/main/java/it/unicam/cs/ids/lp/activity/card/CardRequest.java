package it.unicam.cs.ids.lp.activity.card;

import java.util.List;

record CardRequest(
        String name,
        List<Rule<?>> rules,
        CardProgram program) {
}
