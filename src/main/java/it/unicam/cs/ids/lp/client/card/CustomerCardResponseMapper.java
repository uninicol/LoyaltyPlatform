package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.campaign.CampaignResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CustomerCardResponseMapper implements Function<CustomerCard, CustomerCardResponse> {
    @Autowired
    private CampaignResponseMapper campaignResponseMapper;

    @Override
    public CustomerCardResponse apply(CustomerCard customerCard) {
        return new CustomerCardResponse(
                customerCard.getId(),
                customerCard.getCard().getName(),
                customerCard.getPoints(),
                customerCard.getTier(),
                campaignResponseMapper.apply(customerCard.getCard().getCampaign())
        );
    }
}
