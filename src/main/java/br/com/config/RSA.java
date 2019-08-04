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
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.exception.BusinessException;
import br.com.model.Usuario;
import br.com.service.UsuarioService;

@Component
public class RSA {

	public static String PRIVATE_KEY_FILE = "/keys/private";
	public static String PUBLIC_KEY_FILE = "/keys/public";

	@Inject
	private UsuarioService usuarioService;
	
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



	
	public  String getChavePublica(Usuario usuario) throws Exception {
		
		KeyPair pair = generateKeyPair();
        Base64.Encoder encoder = Base64.getEncoder();
        
        String publicKey = encoder.encodeToString(pair.getPublic().getEncoded());
        String privateKey = encoder.encodeToString(pair.getPrivate().getEncoded());

        usuario.setPrivateKey(privateKey);
        usuario.setPublicKey(publicKey);
        usuarioService.save(usuario);
		
		return publicKey;
				
	}
	
	
	public String descriptografarBase(String cipherText, Usuario usuario) throws BusinessException {
		
	    String privateKey =	usuario.getPrivateKey();
	    String plainText = null;

	    
	    if(StringUtils.isBlank(privateKey)) {
	    	throw new BusinessException("Usuario sem dados criptografados");
	    }
		
		 try {
			PrivateKey key = getPrivateKey(privateKey.getBytes());
			plainText = decrypt(cipherText, key);
		} catch (Exception e) {
			e.printStackTrace();
	    	throw new BusinessException("Não foi possivel descriptografar, verifique se as informações estão corretas.");
		}

		
		return plainText;
	}
	
	
	public String descriptografar(String cipherText) throws IOException {
		
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
	
	
	public String criptografar(String publicKey , Usuario usuario) throws Exception {
		
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

}
