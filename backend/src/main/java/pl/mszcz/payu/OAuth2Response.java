package pl.mszcz.payu;

public record OAuth2Response(
        String access_token,
        String token_type,
        Long expires_in,
        String grant_type
) {
}
