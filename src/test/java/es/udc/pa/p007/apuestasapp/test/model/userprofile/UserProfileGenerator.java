package es.udc.pa.p007.apuestasapp.test.model.userprofile;

import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfile;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;

public class UserProfileGenerator implements net.java.quickcheck.Generator<UserProfile>{
	
	Generator<String> loginName = new LoginGenerator();
	Generator<String> encryptedPassword = PrimitiveGenerators.strings(13);
	Generator<String> firstName = PrimitiveGenerators.strings(30);
	Generator<String> lastName = PrimitiveGenerators.strings(40);
	Generator<String> email = PrimitiveGenerators.strings(60);
	private static int cont= 0;
	
    class LoginGenerator implements Generator<String> {
    	String login = "usuario";
    	
    	@Override
    	public String next() {
    		UserProfileGenerator.cont=UserProfileGenerator.cont+1;
    	    return login+UserProfileGenerator.cont;
    	}
    }
	
	@Override 
	public UserProfile next() {
		return new UserProfile(loginName.next(), encryptedPassword.next(), firstName.next(), lastName.next(), email.next());
	}
}