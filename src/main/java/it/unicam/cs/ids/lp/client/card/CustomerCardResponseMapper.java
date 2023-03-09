package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.campaign.CampaignResponseMapper;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CustomerCardResponseMapper implements Function<CustomerCard, CustomerCardResponse> {
    @Autowired
    private CampaignResponseMapper campaignResponseMapper;
    @Autowired
    private CardRepository cardRepository;

    @Override
    public CustomerCardResponse apply(CustomerCard customerCard) {
        return new CustomerCardResponse(
                customerCard.getId(),
                cardRepository.findById(customerCard.getCard()
                                .getId())
                        .orElseThrow().getName(),
                customerCard.getPoints(),
                customerCard.getTier(),
                campaignResponseMapper.apply(customerCard.getCard()
                        .getCampaign())
        );
    }
}
