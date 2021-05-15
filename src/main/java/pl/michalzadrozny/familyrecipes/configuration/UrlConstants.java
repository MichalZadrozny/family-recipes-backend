package pl.michalzadrozny.familyrecipes.configuration;

public class UrlConstants {
    public static final String[] SWAGGER_URLS = {"/swagger-ui.html", "/v2/api-docs", "/swagger-resources/**", "/webjars/**"};

    public static final String WEBSITE_URL = "https://family-recipes-prod.herokuapp.com";
    public static final String WEBSITE_LOGIN_URL = WEBSITE_URL + "/login";
    public static final String WEBSITE_RECOVERY_URL = WEBSITE_URL + "/recover-password";
}