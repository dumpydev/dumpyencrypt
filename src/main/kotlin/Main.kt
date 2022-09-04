import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.system.exitProcess

val enc: Encrypt = Encrypt()
fun main() {
    titleGen("Dumpy's Encryptor [v3.0]")
    println("[1] Secure MSG\n[2] Encrypt\n[3] Decrypt\n[e] Exit")
    print("Select Mode: ")
    when(readLine()!!.toString()) {
        "1" -> secureMsg()
        "2" -> {
            titleGen("Encryptor [v3.0]")
            print("Enter message: ")
            val text = readLine()!!.toString()
            println("Enter key (leave empty for random)")
            var key = readLine()!!.toString()
            if (key.isEmpty() || key.length != 16) {
                key = enc.genPrivKey()
            }
            println("Encrypted: ${enc.encrypt(text, key)}")
            println("Key: $key")
            print("Validating.... ")
            // sleep
            Thread.sleep(1000)
            if (enc.decrypt(enc.encrypt(text, key), key) == text) {
                print("Success!\n")
            } else {
                print("Failed!\n")
            }
        }
        "3" -> {
            titleGen("Decryptor [v3.0]")
            print("Enter encrypted message: ")
            val text = readLine()!!.toString()
            println("Enter key (leave empty for random)")
            var key = readLine()!!.toString()
            if (key.isEmpty() || key.length != 16) {
                println("Invalid Key")
                print("Press Enter to Continue: ")
                readLine()
                main()
            }
            println("Decrypted: ${enc.decrypt(text, key)}")
        }
        "e" -> exitProcess(0)
        else -> {
            println("Invalid Option")
            print("Press Enter to Continue: ")
            readLine()
            main()
        }
    }
    print("Use again? [Y/n]: ")
    val again = readLine()!!.toString()
    if (again == "" || again == "y" || again == "Y") {
        main()
    } else {
        println("Goodbye!")
    }
}
fun secureMsg() {
    titleGen("Secure Messaging [v4.2]")
    print("Enter your message: ")
    var message = readLine()
    message = message?.trim()
    print("[1] Encrypt\n[2] Decrypt\nEnter your choice: ")
    when (readLine()!!.toInt()) {
        1 -> {
            val key = enc.genPrivKey()
            val encrypted = enc.encrypt(message!!, key)
            println("Encrypted message: $key:$encrypted")
            print("Validating... ")
            if (enc.decrypt(encrypted, key) == message) {
                print("Success!\n")
            } else {
                print("Failed!\n")
            }
        }
        2 -> {

            val key = message!!.split(":")[0]
            val encMsg = message.split(":")[1]

            println("Key: $key\nEncrypted message: $encMsg")
            val decrypted = enc.decrypt(encMsg, key)
            println("Decrypted message: $decrypted")
        }
    }
}

fun titleGen(text: String) {
    val stringBuilder = StringBuilder()
    stringBuilder.append("-".repeat(text.length * 3))
    stringBuilder.append("\n")
    stringBuilder.append(" ".repeat(text.length))
    stringBuilder.append(text)
    stringBuilder.append(" ".repeat(text.length))
    stringBuilder.append("\n")
    stringBuilder.append("-".repeat(text.length * 3))
    println(stringBuilder.toString())
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