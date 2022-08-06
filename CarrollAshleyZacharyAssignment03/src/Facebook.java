import java.util.ArrayList;

public class Facebook {
	ArrayList<FacebookUser> users; // This creates an ArrayList that the FacebookUser's will be put in.

	public Facebook() {
		users = new ArrayList<FacebookUser>(); // This makes an instance of the ArrayList so it can actually work.
	}

	public ArrayList<FacebookUser> getUsers() {
		return users; //This returns the users ArrayList.
	}
	
	public void addUser(FacebookUser fbUser) {
		users.add(fbUser);
	}

	public FacebookUser getUserByName(String name) {
		for(FacebookUser user : users) {
			if (user.isNamed(name))
				return user;
		}
		return null;
	}
	
	public void deleteUser(String name) {
		FacebookUser user = getUserByName(name);
		if (user != null) {
			users.remove(user);
		}
	}
}
