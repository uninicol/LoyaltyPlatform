package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.card.Card;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class CampaignMapper implements BiFunction<CampaignRequest, Card, Campaign> {
    @Override
    public Campaign apply(CampaignRequest campaignRequest, Card card) {
        Campaign campaign = new Campaign();
        campaign.setActivityCard(card);
        campaign.setDescription(campaignRequest.description());
        campaign.setShortDescription(campaignRequest.shortDescription());
        campaign.setShopUrl(campaignRequest.shopUrl());
        campaign.setCategory(campaignRequest.category());
        return campaign;
    }
}
