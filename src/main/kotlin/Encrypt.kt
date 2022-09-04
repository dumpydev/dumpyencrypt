package encrypt

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Encrypt {
    fun encrypt(text: String, privateKey: String): String {
        val key = SecretKeySpec(privateKey.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encrypted = cipher.doFinal(text.toByteArray())
        return Base64.getEncoder().encodeToString(encrypted)
    }
    fun decrypt(text: String, privateKey: String): String {
        val key = SecretKeySpec(privateKey.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decrypted = cipher.doFinal(Base64.getDecoder().decode(text))
        return String(decrypted)
    }
    fun genPrivKey(): String {
        val key = ByteArray(16)
        Random().nextBytes(key)
        return Base64.getEncoder().encodeToString(key)
    }

}