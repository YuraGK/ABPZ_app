package main;

public class User {

	private String login;
	private double[] password;
	private boolean blocked = false;
	private Role role = Role.USER;
	private QuestionBook questionBook = null;
	private int a = 12;
	
	public User(String login) {
		this.setLogin(login);
		this.setPassword(".");
	}
	
	public User(String login, String password) {
		this.setLogin(login);
		this.password = (encrypt(password));
		this.setRole("USER");
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		
		return decrypt(password);
	}

	public void setPassword(String password) {
		
		this.password = encrypt(password);
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked() {
		if(isBlocked()) {
			this.blocked=false;
		}else {
			this.blocked=true;
		}
		
	}
	
	public boolean isManager() {
		return role.toString()=="MANAGER";
	}

	public boolean isAdmin() {
		return role.toString()=="ADMIN";
	}
	
	public void setRole(String r) {
		this.role = Role.valueOf(r);
	}
	
	public void setQuestionBook(QuestionBook qb) {
		this.questionBook = qb;
	}
	
	public QuestionBook getQuestionBook() {
		return questionBook;
	}
	
	public boolean isQuestionBookExists() {
		return questionBook!=null;
	}
	
	private double[] encrypt(String password) {
		
		char[] charct = password.toCharArray();
		double[] pass = new double[charct.length];
		for(int i = 0; i<charct.length; i++) {
			
			double temp = 1/(double)((int)charct[i]);
			pass[i] = a * Math.sin(temp);
		}
		
		return pass;
		
	}
	
	private String decrypt(double[] encrPassword) {
		String password="";
		
		for(double d : encrPassword) {
			password = password+ new Character((char)((int)(1/Math.asin(d/a)))).toString();
		}
		
		return password;
		
	}
}
