package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CampaignResponseMapper implements Function<Campaign, CampaignResponse> {
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public CampaignResponse apply(Campaign campaign) {
        return new CampaignResponse(
                campaign.getId(),
                activityRepository.findByCard_Id(campaign.getActivityCard().getId())
                        .stream()
                        .map(Activity::getName)
                        .toList(),
                campaign.getCategory(),
                campaign.getDescription(),
                campaign.getShortDescription(),
                campaign.getShopUrl());
    }
}
