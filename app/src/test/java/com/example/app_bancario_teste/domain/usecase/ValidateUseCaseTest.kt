package com.example.app_bancario_teste.domain.usecase
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateUseCaseTest {


    private val useCase = ValidateUseCase()

    @Test
    fun `should return empty map when email and password are valid`() {

        val email = "user@email.com"
        val password = "abc123"

        val result = useCase.validate(email, password)

        assertThat(result).isEmpty()
    }

    @Test
    fun `should return error when email does not contain @`() {
        val result = useCase.validate("invalidEmail.com", "abc123")

        assertThat(result).containsKey("email")
        assertThat(result["email"]).contains("e-mail inávlido,verfique o campo,email deve conter @")
    }

    @Test
    fun `should return error when password is shorter than 6 characters`() {
        val result = useCase.validate("user@email.com", "a12")

        assertThat(result).containsKey("password")
        assertThat(result["password"]).contains("Senha precisa ter o mínimo 6 caracteres")
    }

    @Test
    fun `should return error when password has no letters`() {
        val result = useCase.validate("user@email.com", "123456")

        assertThat(result).containsKey("password")
        assertThat(result["password"]).contains("Senha precisa conter no mínimo 1 uma letra")
    }

    @Test
    fun `should return error when password has no digits`() {
        val result = useCase.validate("user@email.com", "abcdef")

        assertThat(result).containsKey("password")
        assertThat(result["password"]).contains("Senha precisa conter no mínimo 1 um número")
    }

    @Test
    fun `should return multiple errors when email and password are invalid`() {
        val result = useCase.validate("invalidEmail", "abc")

        assertThat(result).containsKey("email")
        assertThat(result).containsKey("password")
    }

}


