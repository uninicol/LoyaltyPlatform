package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.JWT_auth.JwtUtils;
import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCardController;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import jakarta.servlet.http.HttpServletRequest;
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
    private CampaignRepository campaignRepository;
    @Autowired
    private CustomerCardController customerCardController;
    @Autowired
    private CustomerCardRepository customerCardRepository;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/isRegisteredTo/{campaignId}")
    public ResponseEntity<Boolean> customerIsRegisteredToCampaign(HttpServletRequest request, @PathVariable Long campaignId) {
        //TODO da migliorare con metodo repository
        String email = jwtUtils.getEmailFromRequest(request);
        boolean isRegistered = customerCardRepository.findByCustomer_Email(email)
                .stream()
                .anyMatch(customerCard -> customerCard.getCard().getCampaign()
                        .equals(campaignRepository.findById(campaignId).orElseThrow()));
        return ResponseEntity.ok().body(isRegistered);
    }

    @PostMapping("/registerToCampaign/{campaignId}")
    public ResponseEntity<String> registerCustomerToCampaign(HttpServletRequest request, @PathVariable Long campaignId) {
        Campaign campaign = campaignRepository.getReferenceById(campaignId);
        customerCardController.addCustomerCard(request, campaign.getActivityCard().getId());
        return ResponseEntity.ok()
                .body("Utente registrato alla campagna con successo");
    }
}
