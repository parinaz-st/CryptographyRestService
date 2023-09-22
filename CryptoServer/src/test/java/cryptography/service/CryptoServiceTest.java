package cryptography.service;

import com.cryptography.service.CryptoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CryptoServiceTest {

    @Autowired
    CryptoService cryptoService;

    @Test
    public void given_StringToBeEncrypted_ThenTestPasses()
    {
        assertThat(cryptoService.encryptString("parinaz")).isNotEqualTo(null);
    }
    @Test
    public void given_CipherTextToBeDecrypted_ThenTestPasses()
    {
        assertThat(cryptoService.decryptString("0W2zO1PeZZuBG/W1VXFYb4f2LkyG+gh//pJj+N0eong=")).isNotEqualTo(null);
    }
}
