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
    private final String authToken;
    private final String redirectUrl;

    public PayU(String merchantPosId, String notifyUrl) {
        this.http = new RestTemplate();
        this.merchantPosId = merchantPosId;
        this.notifyUrl = notifyUrl;
        this.authToken = "d9a4536e-62ba-4f60-8017-6053211d3f47";    // token from payu sandbox code snippets
        this.redirectUrl = "http://localhost:3000/paymentSuccess";
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
        headers.setBearerAuth(authToken);

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
