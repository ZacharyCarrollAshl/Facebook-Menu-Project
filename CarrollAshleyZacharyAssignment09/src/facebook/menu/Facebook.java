package facebook.menu;

import java.util.ArrayList;
import java.util.Collections;

//To DO: "You are required to have a comment somewhere in your Facebook class that indicates which data structure you have used to store the 'likes' information and explains why you have chosen it. Begin this comment with the EXPLANATION (in all caps) so that I can search for it when grading your work.
public class Facebook {
	ArrayList<FacebookUser> users; // This creates an ArrayList that the FacebookUser's will be put in.

	public Facebook() {
		users = new ArrayList<FacebookUser>(); // This makes an instance of the ArrayList so it can actually work.
	}

	public ArrayList<FacebookUser> getUsers() {
		return users; // This returns the users ArrayList.
	}

	public ArrayList<FacebookUser> getUsersAlphabetically() {
		ArrayList<FacebookUser> sortedUsers = new ArrayList<>(users); // This creates an ArrayList for the SortedUsers.

		Collections.sort(sortedUsers, new FacebookUserComparator()); // This sorts the Users list by the
																		// FacebookUserComparator.

		return sortedUsers; // Return the list of sorted Users.
	}

	public ArrayList<FacebookUser> getUsersByFriendCount() {
		ArrayList<FacebookUser> sortedUsers = new ArrayList<>(users); // This creates an ArrayList for the SortedUsers.

		Collections.sort(sortedUsers, new FacebookFriendCountComparator()); // This sorts the Users list by the
																			// FacebookFriendCountComparator.

		return sortedUsers; // Return the list of sorted Users.
	}

	public void addUser(FacebookUser fbUser) {
		users.add(fbUser);
	}

	public FacebookUser getUserByName(String name) { // This gets a User by their UserName.
		for (FacebookUser user : users) {
			if (user.isNamed(name)) // If the inputted User has a name that matches a Username on the Friends
									// ArrayList.
				return user; // It returns the User.
		}
		return null;
	}

	public void deleteUser(String name) {
		FacebookUser user = getUserByName(name); // This sets up the FacebookUser User.
		if (user != null) { // If User isn't Null...
			users.remove(user); // This removes the chosen User from the Friends ArrayList.
		}
	}
}
