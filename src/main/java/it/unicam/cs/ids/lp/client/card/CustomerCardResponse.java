package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.campaign.CampaignResponse;

public record CustomerCardResponse(
        long id,
        String name,
        int points,
        int tier,
        CampaignResponse campaign
) {
}
