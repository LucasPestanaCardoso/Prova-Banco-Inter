package br.com.config;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Cipher;

import org.springframework.stereotype.Component;

import br.com.exception.BusinessException;
import br.com.model.Usuario;

@Component
public class RSA {

	public static String PRIVATE_KEY_FILE = "/keys/private";
	public static String PUBLIC_KEY_FILE = "/keys/public";

	public static KeyPair generateKeyPair() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048, new SecureRandom());
		KeyPair pair = generator.generateKeyPair();
		
		return pair;
	}

	public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));

		return Base64.getEncoder().encodeToString(cipherText);
	}

	public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
		byte[] bytes = Base64.getDecoder().decode(cipherText);

		Cipher decriptCipher = Cipher.getInstance("RSA");
		decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

		return new String(decriptCipher.doFinal(bytes), UTF_8);
	}


	public static File createFile(String path) {

		try {

			File key = new File(path);

			if (key.getParentFile() != null) {
				key.getParentFile().mkdirs();
			}

			key.createNewFile();

			return key;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static boolean areKeysPresent(Integer idUsuario) {

		String pathPrivate = getPath(PRIVATE_KEY_FILE,  System.currentTimeMillis());
		String pathPublic = getPath(PUBLIC_KEY_FILE,  System.currentTimeMillis());

		File privateKey = new File(pathPrivate);
		File publicKey = new File(pathPublic);

		if (privateKey.exists() && publicKey.exists()) {
			return true;
		}
		return false;
	}

	public static String getPath(String pathKey, Long idUsuario) {
		return System.getProperty("java.io.tmpdir").concat(pathKey).concat(idUsuario.toString()).concat(".key");
	}
	
	
	public static String getChavePublica(Usuario usuario) throws Exception {
		
		KeyPair pair = generateKeyPair();
        Base64.Encoder encoder = Base64.getEncoder();


		String pathPrivate = getPath(PRIVATE_KEY_FILE, System.currentTimeMillis() );
		String pathPublic = getPath(PUBLIC_KEY_FILE,  System.currentTimeMillis());
		
		File privateKeyFile = createFile(pathPrivate);
		File publicKeyFile = createFile(pathPublic);

		ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
		publicKeyOS.writeObject(encoder.encodeToString(pair.getPublic().getEncoded()));
		publicKeyOS.close();

		ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
		privateKeyOS.writeObject(encoder.encodeToString(pair.getPrivate().getEncoded()));
		privateKeyOS.close();
		
		return encoder.encodeToString(pair.getPublic().getEncoded());
				
	}
	
	public static String descriptografar(String cipherText) throws IOException {
		
		String caminho =  System.getProperty("java.io.tmpdir").concat("/keys");
		List<Path> lista = Files.list(Paths.get(caminho)).collect(Collectors.toList());
	    String plainText = null;
	    Integer contador = 0;
	    		 
		do {
			try {
				
				 if(contador > lista.size()) {
					 throw new BusinessException("Hash não encontrado.");
				 }
				 			
			     String content = new String (Files.readAllBytes(lista.get(contador)),Charset.forName("UTF-8")); 
			     contador++;
				 PrivateKey privateKey = getPrivateKey(content.substring(7).getBytes());
				 plainText = decrypt(cipherText, privateKey);
				 
				
						 
			} catch (Exception e) {
			}
			  
		} while(plainText == null);

		
		return plainText;	
	}
	
	
	public static String criptografar(String publicKey , Usuario usuario) throws Exception {
		
		PublicKey key = getPublicKey(publicKey);
		String cipherText = encrypt(usuario.getNome() + " " + usuario.getEmail(), key);
		
		return cipherText;
		
	}
	
	
	public static PublicKey getPublicKey(String key){
	    try{
	    	
	        byte[] byteKey = Base64.getDecoder().decode(key.getBytes());
	        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
	        KeyFactory kf = KeyFactory.getInstance("RSA");

	        return kf.generatePublic(X509publicKey);
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public static PrivateKey getPrivateKey(byte[] key){
	    try{
	    	
	        byte[] byteKey = Base64.getDecoder().decode(key);
	        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(byteKey);
	        KeyFactory kf = KeyFactory.getInstance("RSA");

	        return kf.generatePrivate(ks);
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    } 

	    return null;
	}
	

	public static void main(String... argv) throws Exception {
		
		Usuario usuario = new Usuario();
		usuario.setId(1);
		usuario.setNome("lucas");
		usuario.setEmail("email");
		
		String publicKey = getChavePublica(usuario);
		
		String cipher = criptografar(publicKey , usuario);
		
		System.out.println(cipher);
		
		String original = descriptografar(cipher);
		
		System.out.println(original);

		
		// First generate a public/private key pair
	//	 KeyPair pair = getKeyPairFromKeyStore();

		/*
		 * Integer idUsuario = 2; String message =
		 * "the answer to life the universe and everything";
		 * 
		 * String pathPrivate = getPath(PRIVATE_KEY_FILE, idUsuario); String pathPublic
		 * = getPath(PUBLIC_KEY_FILE, idUsuario);
		 * 
		 * 
		 * if(!areKeysPresent(idUsuario)) {
		 * 
		 * KeyPair pair = generateKeyPair();
		 * 
		 * 
		 * File privateKeyFile = createFile(pathPrivate); File publicKeyFile =
		 * createFile(pathPublic);
		 * 
		 * ObjectOutputStream publicKeyOS = new ObjectOutputStream(new
		 * FileOutputStream(publicKeyFile)); publicKeyOS.writeObject(pair.getPublic());
		 * publicKeyOS.close();
		 * 
		 * ObjectOutputStream privateKeyOS = new ObjectOutputStream(new
		 * FileOutputStream(privateKeyFile));
		 * privateKeyOS.writeObject(pair.getPrivate()); privateKeyOS.close();
		 * 
		 * }
		 * 
		 * 
		 * ObjectInputStream inputStream = null;
		 * 
		 * inputStream = new ObjectInputStream(new FileInputStream(pathPublic)); final
		 * PublicKey publicKey = (PublicKey) inputStream.readObject(); String cipherText
		 * = encrypt(message, publicKey);
		 * 
		 * inputStream = new ObjectInputStream(new FileInputStream(pathPrivate)); final
		 * PrivateKey privateKey = (PrivateKey) inputStream.readObject(); final String
		 * plainText = decrypt(cipherText, privateKey);
		 */

		/*
		 * System.out.println(plainText);
		 * 
		 * File privateKeyFile = new File(PUBLIC_KEY_FILE); File publicKeyFile = new
		 * File(PUBLIC_KEY_FILE);
		 * 
		 * if (privateKeyFile.getParentFile() != null) {
		 * privateKeyFile.getParentFile().mkdirs(); }
		 * 
		 * privateKeyFile.createNewFile();
		 * 
		 * if (publicKeyFile.getParentFile() != null) {
		 * publicKeyFile.getParentFile().mkdirs(); }
		 * 
		 * publicKeyFile.createNewFile();
		 */

		// Our secret message

		// Encrypt the message
		// String cipherText = encrypt(message, pair.getPublic());

		// Now decrypt it
//		String decipheredMessage = decrypt(cipherText, pair.getPrivate());
//
//		System.out.println(decipheredMessage);
//
//		// Let's sign our message
//		String signature = sign("foobar", pair.getPrivate());
//
//		// Let's check the signature
//		boolean isCorrect = verify("foobar", signature, pair.getPublic());
//		System.out.println("Signature correct: " + isCorrect);
	}

}
