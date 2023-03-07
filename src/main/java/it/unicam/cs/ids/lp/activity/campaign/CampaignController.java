package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CardRepository cardRepository;

    @PutMapping("/{cardId}/add")
    public ResponseEntity<?> addCampaign(@PathVariable Long cardId, @RequestBody CampaignRequest campaignRequest) {
        Card card = cardRepository.getReferenceById(cardId);
        Campaign campaign = new Campaign();
        campaign.setActivityCard(card);
        campaign.setDescription(campaignRequest.description);
        campaign.setShortDescription(campaignRequest.shortDescription);
        campaign.setShopUrl(campaignRequest.shopUrl);
        campaign.setCategory(campaignRequest.category);
        card.setCampaign(campaign);
        campaignRepository.save(campaign);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getCampaigns")
    public ResponseEntity<List<CampaignDTO>> getCampaigns() {
        List<CampaignDTO> list = campaignRepository.findAll()
                .stream()
                .map(campaign -> new CampaignDTO(
                        campaign.getId(),
                        activityRepository.findByCard_Id(campaign.getActivityCard().getId()).stream()
                                .map(Activity::getName).toList(),
                        campaign.getCategory(), campaign.getDescription(),
                        campaign.getShortDescription(), campaign.getShopUrl()))
                .toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private record CampaignDTO(long id, List<String> activityName, Activity.ContentCategory category,
                               String description,
                               String shortDescription, String shopUrl) {
    }

    private record CampaignRequest(String description, String shortDescription, String shopUrl,
                                   Activity.ContentCategory category) {
    }
}
