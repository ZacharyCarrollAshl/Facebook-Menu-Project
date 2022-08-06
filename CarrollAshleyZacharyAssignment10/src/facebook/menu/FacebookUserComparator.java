package facebook.menu;

import java.util.Comparator;

public class FacebookUserComparator implements Comparator<FacebookUser> {
	@Override
	public int compare(FacebookUser user1, FacebookUser user2) { // This compares the User's names to one another.
		return user1.getName().compareToIgnoreCase(user2.getName()); // Returns 1, -1, 0, depending on the letters that
																		// are being compared.
	}
}
