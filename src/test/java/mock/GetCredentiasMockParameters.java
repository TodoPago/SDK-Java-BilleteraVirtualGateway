package mock;

import ar.com.todopago.api.model.User;

public class GetCredentiasMockParameters {
	public static User getUser() {
		String mail = "test@test.com.ar"; // The email is only as example
		String pass = "test1234"; // The pass is only as example
		User user = new User(mail, pass);
		return user;
	}	
}