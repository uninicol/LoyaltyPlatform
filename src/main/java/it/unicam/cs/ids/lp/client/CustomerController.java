package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private CustomerCardController customerCardController;

    @PostMapping("/{customerId}/isRegisteredTo/{campaignId}")
    public ResponseEntity<Boolean> customerIsRegisteredToCampaign(@PathVariable long customerId, @PathVariable Long campaignId) {
        //TODO da migliorare con metodo repository
        boolean isRegistered = customerRepository.getReferenceById(customerId).getCards().parallelStream()
                .map(CustomerCard::getActivityCard)
                .map(Card::getCampaign)
                .map(Campaign::getId)
                .anyMatch(id -> id.equals(campaignId));
        return ResponseEntity.ok().body(isRegistered);
    }

    @PostMapping("/{customerId}/registerTo/{campaignId}")
    public ResponseEntity<?> registerCustomerToCampaign(@PathVariable Long customerId, @PathVariable Long campaignId) {
        Campaign campaign = campaignRepository.getReferenceById(campaignId);
        return customerCardController.addCustomerCard(customerId, campaign.getActivityCard().getId());
    }
}
