package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CampaignMapper campaignMapper;

    @PutMapping("/{cardId}/add")
    public ResponseEntity<?> addCampaign(@PathVariable Long cardId, @RequestBody CampaignRequest campaignRequest) {
        Card card = cardRepository.findById(cardId).orElseThrow();
        Campaign campaign = campaignMapper.apply(campaignRequest, card);
        card.setCampaign(campaign);
        campaignRepository.save(campaign);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//
//    @GetMapping("/getCampaigns")
//    public ResponseEntity<List<CampaignResponse>> getCampaigns() {
//        List<CampaignResponse> list = campaignRepository.findAll()
//                .stream()
//                .map(campaign -> new CampaignResponse(
//                        campaign.getId(),
//                        activityRepository.findByCard_Id(campaign.getActivityCard().getId()).stream()
//                                .map(Activity::getName).toList(),
//                        campaign.getCategory(), campaign.getDescription(),
//                        campaign.getShortDescription(), campaign.getShopUrl()))
//                .toList();
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
}
