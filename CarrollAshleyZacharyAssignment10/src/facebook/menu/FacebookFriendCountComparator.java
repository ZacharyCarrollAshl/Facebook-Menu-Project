package facebook.menu;

import java.util.Comparator;

public class FacebookFriendCountComparator implements Comparator<FacebookUser> {

	@Override
	public int compare(FacebookUser o1, FacebookUser o2) {
		int friends1 = o1.getFriends().size(); // This gets the first amount of friends.
		int friends2 = o2.getFriends().size(); // This gets the second amount of friends.

		if (friends1 < friends2) { // If the amount of friends that o1 has is less than o2...
			return 1; // You return 1.
		} else if (friends1 > friends2) { // Otherwise, if o1 has more friends...
			return -1; // You return -1.
		} else { //If they are equal...
			return 0; // You return 0.
		}
	}
}
