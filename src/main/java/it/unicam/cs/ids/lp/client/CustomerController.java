package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.JWT_auth.JwtUtils;
import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCardController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class CustomerController {
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private CustomerCardController customerCardController;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/isRegisteredTo/{campaignId}")
    public ResponseEntity<Boolean> customerIsRegisteredToCampaign(HttpServletRequest request, @PathVariable Long campaignId) {
        Customer customer = jwtUtils.getCustomerFromRequest(request);
        boolean isRegistered = customer.getCustomerCard()
                .stream()
                .anyMatch(customerCard -> customerCard.getCard()
                        .getCampaign()
                        .getId() == campaignId);
        return ResponseEntity.ok()
                .body(isRegistered);
    }

    @PostMapping("/registerToCampaign/{campaignId}")
    public ResponseEntity<String> registerCustomerToCampaign(HttpServletRequest request, @PathVariable Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        customerCardController.addCustomerCard(request, campaign.getActivityCard().getId());
        return ResponseEntity.ok()
                .body("Utente registrato alla campagna " + campaign.getActivityCard().getName() + " con successo");
    }
}
