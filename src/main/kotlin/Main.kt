import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
val enc: Encrypt = Encrypt()
fun main() {
    println("-----------------\nDumpy [Encrypt] v3.2\n-----------------")
    println("Select Mode")
    println("[1] Secure MSG\n[2] Encrypt\n[3] Decrypt\n[e] Exit")
    when(readLine()!!.toString()) {
        "1" -> secureMsg()
        "2" -> {
            println("Encryptor")
            println("Enter Text")
            val text = readLine()!!.toString()
            println("Enter Key (leave empty for random)")
            var key = readLine()!!.toString()
            if (key.isEmpty() || key.length != 16) {
                key = enc.genPrivKey()
                println("Encrypted: ${enc.encrypt(text, key)}")
                println("Key: $key")
            } else {
                println("Encrypted: ${enc.encrypt(text,key)}")
            }
        }
        "3" -> {

        }
    }

}
fun secureMsg() {
    println("Secure Messaging.")
    print("Enter your message: ")
    var message = readLine()
    message = message?.trim()
    print("[1] Encrypt\n[2] Decrypt\nEnter your choice: ")
    when (readLine()!!.toInt()) {
        1 -> {
            val key = enc.genPrivKey()
            val encrypted = enc.encrypt(message!!, key)
            println("Encrypted message: $key:$encrypted")
            print("Validating...")
            if (enc.decrypt(encrypted, key) == message) {
                print("Success!\n")
            } else {
                print("Failed!\n")
            }
        }
        2 -> {

            val key = message!!.split(":")[0]
            val encmsg = message.split(":")[1]

            println("Key: $key\nEncrypted message: $encmsg")
            val decrypted = enc.decrypt(encmsg, key)
            println("Decrypted message: $decrypted")
        }
    }

    println("Use again? [y/n]")
    val again = readLine()!!.toString()
    if (again == "" || again == "y" || again == "Y") {
        main()
    } else {
        println("Goodbye!")
    }
}
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