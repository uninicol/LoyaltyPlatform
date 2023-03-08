package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.ContentCategory;

import java.util.List;

record CampaignResponse(long id,
                        List<String> activityName,
                        ContentCategory category,
                        String description,
                        String shortDescription, String shopUrl) {
}
