package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.authorizationserver

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer

class JwtCustomClaimsTokenEnhancer : TokenEnhancer {

    override fun enhance(
        accessToken: OAuth2AccessToken,
        oAuth2Authentication: OAuth2Authentication
    ): OAuth2AccessToken {

        if (oAuth2Authentication.principal is AuthUser) {
            val authUser = oAuth2Authentication.principal as AuthUser

            val info = HashMap<String, Any?>()
            info["full_name"] = authUser.fullName
            info["user_id"] = authUser.userId

            val oAuth2AccessToken = accessToken as DefaultOAuth2AccessToken
            oAuth2AccessToken.additionalInformation = info
        }

        return accessToken
    }

}