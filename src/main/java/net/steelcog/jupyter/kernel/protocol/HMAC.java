package net.steelcog.jupyter.kernel.protocol;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created on 22-May-16.
 */
public class HMAC {

    private static final String ALGORITHM = "hmacsha256";
    private final SecretKeySpec sk;

    public HMAC(String key) {
        sk = new SecretKeySpec(key.getBytes(), ALGORITHM);
        try {
            //For sanity's sake let's do this now not later:
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(sk);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public String digest(byte[][] data) {
        try {
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(sk);
            for (byte[] row : data ) {
                mac.update(row);
            }
            byte[] out = mac.doFinal();
            return String.valueOf(out);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    object HMAC {
        def apply(key: String, algorithm: Option[String]=None): HMAC =
            if (key.isEmpty) NoHMAC else new DoHMAC(key)
    }

    sealed trait HMAC {
        def hexdigest(args: Seq[String]): String

        final def apply(args: String*) = hexdigest(args)
    }

    final class DoHMAC(key: String, algorithm: Option[String]=None) extends HMAC {
        private val _algorithm = algorithm getOrElse "hmac-sha256" replace ("-", "")
        private val mac = Mac.getInstance(_algorithm)
        private val keySpec = new SecretKeySpec(key.getBytes, _algorithm)
        mac.init(keySpec)

        def hexdigest(args: Seq[String]): String = {
                mac synchronized {
            args.map(_.getBytes).foreach(mac.update)
            Util.hex(mac.doFinal())
        }
        }
    }

    object NoHMAC extends HMAC {
        def hexdigest(args: Seq[String]): String = ""
    }
    */
}
