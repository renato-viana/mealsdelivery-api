package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.authorizationserver

import org.apache.commons.codec.binary.Base64
import org.springframework.security.crypto.codec.Utf8
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException
import org.springframework.security.oauth2.provider.*
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

// Solução baseada em: https://github.com/spring-projects/spring-security-oauth/pull/675/files
class PkceAuthorizationCodeTokenGranter(
    tokenServices: AuthorizationServerTokenServices?,
    authorizationCodeServices: AuthorizationCodeServices?, clientDetailsService: ClientDetailsService?,
    requestFactory: OAuth2RequestFactory?
) : AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory) {
    override fun getOAuth2Authentication(client: ClientDetails, tokenRequest: TokenRequest): OAuth2Authentication {
        val authentication = super.getOAuth2Authentication(client, tokenRequest)

        val request = authentication.oAuth2Request

        val codeChallenge = request.requestParameters["code_challenge"]

        val codeChallengeMethod = request.requestParameters["code_challenge_method"]

        val codeVerifier = request.requestParameters["code_verifier"]

        if (codeChallenge != null || codeChallengeMethod != null) {
            if (codeVerifier == null) {
                throw InvalidGrantException("Code verifier expected.")
            }
            if (!validateCodeVerifier(codeVerifier, codeChallenge, codeChallengeMethod)) {
                throw InvalidGrantException("$codeVerifier does not match expected code verifier.")
            }
        }

        return authentication
    }

    private fun validateCodeVerifier(
        codeVerifier: String, codeChallenge: String?,
        codeChallengeMethod: String?
    ): Boolean {
        var generatedCodeChallenge: String? = null
        generatedCodeChallenge = if ("plain".equals(codeChallengeMethod, ignoreCase = true)) {
            codeVerifier
        } else if ("s256".equals(codeChallengeMethod, ignoreCase = true)) {
            generateHashSha256(codeVerifier)
        } else {
            throw InvalidGrantException("$codeChallengeMethod is not a valid challenge method.")
        }

        return generatedCodeChallenge == codeChallenge
    }

    companion object {
        private fun generateHashSha256(plainText: String): String {
            return try {
                val messageDigest = MessageDigest.getInstance("SHA-256")
                val hash = messageDigest.digest(Utf8.encode(plainText))
                Base64.encodeBase64URLSafeString(hash)
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException(e)
            }
        }
    }

}