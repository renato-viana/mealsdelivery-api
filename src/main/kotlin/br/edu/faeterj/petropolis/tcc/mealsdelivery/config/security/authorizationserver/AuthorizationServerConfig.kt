package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.authorizationserver

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.CompositeTokenGranter
import org.springframework.security.oauth2.provider.TokenGranter
import org.springframework.security.oauth2.provider.approval.ApprovalStore
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory
import java.security.KeyPair
import java.security.interfaces.RSAPublicKey
import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig(

    @Autowired
    var authenticationManager: AuthenticationManager,

    @Autowired
    var userDetailsService: UserDetailsService,

    @Autowired
    var jwtKeyStoreProperties: JwtKeyStoreProperties,

    @Autowired
    var dataSource: DataSource

) : AuthorizationServerConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource)
    }

    @Throws(Exception::class)
    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.checkTokenAccess("permitAll()")
            .tokenKeyAccess("permitAll()")
            .allowFormAuthenticationForClients()
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        val enhancerChain = TokenEnhancerChain()
        enhancerChain.setTokenEnhancers(
            listOf(JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter())
        )
        endpoints
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService)
            .reuseRefreshTokens(false)
            .accessTokenConverter(jwtAccessTokenConverter())
            .tokenEnhancer(enhancerChain)
            .approvalStore(approvalStore(endpoints.tokenStore))
            .tokenGranter(tokenGranter(endpoints))
    }

    private fun approvalStore(tokenStore: TokenStore): ApprovalStore {
        val approvalStore = TokenApprovalStore()
        approvalStore.setTokenStore(tokenStore)

        return approvalStore
    }

    @Bean
    fun jwkSet(): JWKSet {
        val builder = RSAKey.Builder(keyPair().public as RSAPublicKey)
            .keyUse(KeyUse.SIGNATURE)
            .algorithm(JWSAlgorithm.RS256)
            .keyID("mealsdelivery-key-id")

        return JWKSet(builder.build())
    }

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        val jwtAccessTokenConverter = JwtAccessTokenConverter()
        jwtAccessTokenConverter.setKeyPair(keyPair())
        return jwtAccessTokenConverter
    }

    private fun keyPair(): KeyPair {
        val keyStorePass: String? = jwtKeyStoreProperties.password

        val keyPairAlias: String? = jwtKeyStoreProperties.keypairAlias

        val keyStoreKeyFactory = KeyStoreKeyFactory(jwtKeyStoreProperties.jksLocation, keyStorePass?.toCharArray())

        return keyStoreKeyFactory.getKeyPair(keyPairAlias)
    }

    private fun tokenGranter(endpoints: AuthorizationServerEndpointsConfigurer): TokenGranter {
        val pkceAuthorizationCodeTokenGranter = PkceAuthorizationCodeTokenGranter(
            endpoints.tokenServices,
            endpoints.authorizationCodeServices, endpoints.clientDetailsService,
            endpoints.oAuth2RequestFactory
        )

        val granters = listOf(
            pkceAuthorizationCodeTokenGranter, endpoints.tokenGranter
        )

        return CompositeTokenGranter(granters)
    }

}