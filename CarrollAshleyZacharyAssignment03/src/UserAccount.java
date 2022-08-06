import java.io.Serializable;

//Created by Zachary Carroll-Ashley
//Started on 1/13/2022
//Code created for 22.SP.CIS.2217.NR4 Java Software Dev 2
public abstract class UserAccount implements Serializable {
	private static final long serialVersionUID = -5770263722636254995L;
	private String username;
	private String password;
	private boolean active; // This indicated whether or not the account is currently active.

	UserAccount(String username, String password)// constructor to initialize the username and password, make the
													// account active
	{
		this.username = username; // This allows the user accounts to have a username.
		this.password = password; // This allows the user to have a password for their account.
		this.active = true; // Need to set this so the accounts can also have the active boolean so they can
							// be enabled or disabled.

	}

	public boolean checkPassword(String password) {
		return this.password.equals(password); // Calling this.password because it is what I used with UserAccount when
												// setting up the password.
		// Using the equals method to make sure that the password is password just like
		// what was supposed to be set by the above UserAccount method.
	}

	public void deactivateAccount() // make this account inactive
	{
		active = false;
		// Setting this.active to false sets the accounts boolean method to false which
		// disables the account all together.
	}


	@Override
	public String toString() {
		return "The username for this account is: [Username =" + " " + username + "]"; // I used the example from the
																						// video
		// provided with the course review.
		// It goes over how to make a toString method and I used it as an example.
		// Link to video:
		// https://www.youtube.com/watch?v=14BAOq9LyGM&list=PLZaIh7R3pKadTQb8gbiUX6Hn-p9MRsJw8

	}

//I followed the 2nd part of the video talking about XBankAccount & XBankDriver to write this. I just right clicked and went to source and clicked
	// "Generate hashCode() and equals() and did as the rubric said and made it so
	// it only counted usernames when comparing the two UserAccounts.
	// Link to the video:
	// https://www.youtube.com/watch?v=8HwY9QDEEAM&list=PLZaIh7R3pKadTQb8gbiUX6Hn-p9MRsJw8
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	// I followed the 2nd part of the video talking about XBankAccount & XBankDriver
	// to write this. I just right clicked and went to source and clicked
	// "Generate hashCode() and equals() and did as the rubric said and made it so
	// it only counted usernames when comparing the two UserAccounts.
	// Link to the video:
	// https://www.youtube.com/watch?v=8HwY9QDEEAM&list=PLZaIh7R3pKadTQb8gbiUX6Hn-p9MRsJw8
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public abstract void getPasswordHelp();
	//This method is abstract because different types of user accounts may provide different
	//types of help, such as providing a password hint that was entered by the user 
	//when the account was created or initiating a process to reset the password.

	protected String getName() { return username; } // only accessible by subclasses like FacebookUser
	}

