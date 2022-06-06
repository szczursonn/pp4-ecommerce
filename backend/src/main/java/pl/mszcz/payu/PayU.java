package pl.mszcz.payu;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class PayU {
    private final RestTemplate http;

    private final String merchantPosId;
    private final String notifyUrl;
    private final String redirectUrl;
    private final AccessTokenProvider accessTokenProvider;

    public PayU(String merchantPosId, String clientId, String clientSecret, String notifyUrl, String redirectUrl) {
        this.http = new RestTemplate();
        this.merchantPosId = merchantPosId;
        this.notifyUrl = notifyUrl;
        this.redirectUrl = redirectUrl;
        this.accessTokenProvider = new AccessTokenProvider(clientId, clientSecret);
    }

    public OrderCreateResponse handle(
            String customerIp,
            String description,
            int total,
            Buyer buyer,
            List<Product> products
    ) throws CantCreateOrderException {
        OrderCreateRequest req = new OrderCreateRequest(
                notifyUrl,
                merchantPosId,
                description,
                "PLN",
                total,
                buyer,
                customerIp,
                products,
                redirectUrl
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessTokenProvider.getAccessToken());

        HttpEntity<OrderCreateRequest> httpReq = new HttpEntity<>(req, headers);
        OrderCreateResponse res = http.postForObject(
                "https://secure.snd.payu.com/api/v2_1/orders",
                httpReq,
                OrderCreateResponse.class
        );

        if (res == null || !res.status().statusCode().equals("SUCCESS")) {
            throw new CantCreateOrderException();
        }

        return res;
    }
}
