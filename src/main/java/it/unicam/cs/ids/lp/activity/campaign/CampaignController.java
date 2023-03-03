package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
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
    public ResponseEntity<?> addCampaign(@PathVariable Integer cardId, @RequestBody CampaignRequest campaignRequest) {
        Campaign campaign = new Campaign();
        campaign.setCard(cardRepository.findById(cardId).orElseThrow());
        campaign.setDescription(campaignRequest.description);
        campaign.setShortDescription(campaignRequest.shortDescription);
        campaign.setShopUrl(campaignRequest.shopUrl);
        campaign.setCategory(campaignRequest.category);
        campaignRepository.save(campaign);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getCampaigns")
    public ResponseEntity<?> getCampaigns() {
        List<?> list = campaignRepository.findAll()
                .stream()
                .map(campaign -> new CampaignDTO(
                        activityRepository.findByCard_Id(campaign.getCard().getId()).stream()
                                .map(Activity::getName).toList(),
                        campaign.getCategory(), campaign.getDescription(),
                        campaign.getShortDescription(), campaign.getShopUrl())).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private record CampaignDTO(List<String> activityName, Activity.ContentCategory category, String description,
                               String shortDescription, String shopUrl) {
    }

    private record CampaignRequest(String description, String shortDescription, String shopUrl,
                                   Activity.ContentCategory category) {
    }
}
