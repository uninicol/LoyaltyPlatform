package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.ContentCategory;

record CampaignRequest(
        String description,
        String shortDescription,
        String shopUrl,
        ContentCategory category) {
}
