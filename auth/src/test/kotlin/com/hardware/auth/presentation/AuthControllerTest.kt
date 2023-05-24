package com.hardware.auth.presentation

import com.hardware.auth.domain.TokenComponent
import com.hardware.auth.domain.entities.Credential
import com.hardware.auth.domain.entities.Token
import com.hardware.auth.domain.entities.TokenPair
import com.hardware.auth.domain.entities.TokenType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.GraphQlTester

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureGraphQlTester
class AuthControllerTest(
    @Autowired
    private val tester: GraphQlTester,
    @Autowired
    private val tokenComponent: TokenComponent
) {

    val credentials: Credential = Credential("1","test@gmail.com", "12345")

    @Test
    fun testAuthenticate() {
        // language=GraphQL
        val query = """
        mutation auth(${'$'}credential: Credential!){
            authenticate(credential: ${'$'}credential){
                accessToken{
                    type
                    value
                }
                refreshToken{
                    type
                    value
                }
            }
        }
        """

        val c: TokenPair = tester
            .document(query)
            .variable("credential", mapOf("email" to "user@gmail.com", "password" to "12345"))
            .execute()
            .path("data.authenticate")
            .entity(TokenPair::class.java)
            .get()
        Assertions.assertNotNull(c)
    }

    @Test
    fun testRefresh(){
        val query = """
            query accesstoken(${'$'}token: String!){
              accessToken(tokenString: ${'$'}token) {
                  type
                  value
              }
            }
        """.trimIndent()

        val accessToken: Token = tester
        .document(query)
            .variable("token", tokenComponent.sign(credentials, TokenType.REFRESH))
            .execute()
            .path("data.accessToken")
            .entity(Token::class.java)
            .get()

        Assertions.assertNotNull(accessToken)
    }


    @Test
    fun testCreate() {
        // language=GraphQL
        val query = """
        mutation auth(${'$'}credential: Credential!){
            createAccount(credential: ${'$'}credential){
                accessToken{
                    type
                    value
                }
                refreshToken{
                    type
                    value
                }
            }
        }
        """

        val c: TokenPair = tester
            .document(query)
            .variable("credential", mapOf("email" to "test2@gmail.com", "password" to "12345"))
            .execute()
            .path("data.createAccount")
            .entity(TokenPair::class.java)
            .get()
        Assertions.assertNotNull(c)
    }



}




