package pl.mszcz.payu;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class AccessTokenProvider {

    private final RestTemplate http;
    private final String clientId;
    private final String clientSecret;
    private String accessToken;
    private Long expireDate;

    public AccessTokenProvider(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.http = new RestTemplate();
    }

    public String getAccessToken() {

        if (accessToken == null || System.currentTimeMillis()>=expireDate-3000) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> httpEntity = new HttpEntity<>("grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret, headers);

            OAuth2Response res = http.postForObject("https://secure.snd.payu.com/pl/standard/user/oauth/authorize", httpEntity, OAuth2Response.class);

            assert res != null;
            this.accessToken = res.access_token();
            this.expireDate = System.currentTimeMillis()+res.expires_in()*1000;
        }

        return accessToken;
    }

    public void invalidate() {
        this.accessToken = null;
    }
}
