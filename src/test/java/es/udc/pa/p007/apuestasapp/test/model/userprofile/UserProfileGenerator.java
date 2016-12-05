package es.udc.pa.p007.apuestasapp.test.model.userprofile;

import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfile;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.CombinedGenerators;

public class UserProfileGenerator implements net.java.quickcheck.Generator<UserProfile>{
	
	Generator<String> loginName = CombinedGenerators.uniqueValues(PrimitiveGenerators.strings(30));
	Generator<String> encryptedPassword = PrimitiveGenerators.strings(13);
	Generator<String> firstName = PrimitiveGenerators.strings(30);
	Generator<String> lastName = PrimitiveGenerators.strings(40);
	Generator<String> email = PrimitiveGenerators.strings(60);


	@Override 
	public UserProfile next() {
		return new UserProfile(loginName.next(), encryptedPassword.next(), firstName.next(), lastName.next(), email.next());
	}
}