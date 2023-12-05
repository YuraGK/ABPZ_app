package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class Main {
	private static String[] regexes = {"[0-9]+","[,.;:]+","[//+**-]+"};
	
	private static List<User> logins = new ArrayList<User>();
	private static int authCounter = 0;
	private static File userRegLogs = null;
	private static File userOpLogs = null;
	private static File jsonLogs = null;
	private static String accessKey = "release";
	private static boolean accessKeyAccepted = false;
	static ImageIcon image;
	static List<String> imageFileNames;
	static Gson gson;
	
public static void main(String[] args) {
	/*Pattern pattern = Pattern.compile(regexes[3]);
	Matcher matcher = pattern.matcher(adminPassword);*/
	
	Main m = new Main();
	//
	//rememberWindow(u1);
    //System.out.println(matcher.find());
    //showAuthWindow();
	showManagWindow(logins.get(0));
}

public Main() {
	gson = new Gson();
	
	userRegLogs = new File("userRegLogs.txt");
	userOpLogs = new File("userOpLogs.txt");
	jsonLogs = new File("jsonLogs.json");
	try {
		userRegLogs.createNewFile();
		userOpLogs.createNewFile();
		jsonLogs.createNewFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	User admin = new User("ADMIN", "654/33.654");
	admin.setRole("ADMIN");
	User u1 = new User("emp1", "00+00-01");
	QuestionBook qb = new QuestionBook();
	qb.addQuestion("question1", "answer1");
	qb.addQuestion("question2", "answer2");
	qb.addQuestion("question3", "answer3");
	qb.addQuestion("question4", "answer4");
	qb.addQuestion("question5", "answer5");
	qb.addQuestion("question6", "answer6");
	qb.addQuestion("question7", "answer7");
	qb.addQuestion("question8", "answer8");
	qb.addQuestion("question9", "answer9");
	qb.addQuestion("question10", "answer10");
	qb.addQuestion("question11", "answer11");
	qb.addQuestion("question12", "answer12");
	qb.addQuestion("question13", "answer13");
	qb.addQuestion("question14", "answer14");
	qb.addQuestion("question15", "answer15");
	u1.setQuestionBook(qb);
	User u2 = new User("emp2", "00+00-02");
	User u3 = new User("emp3", "00+00-03");
	User u4 = new User("login", "0.-");
	u4.setBlocked();
	User u5 = new User("emp5", "00+00-05");
	User u6 = new User("emp6", "00+00-06");
	User u7 = new User("emp7", "00+00-07");
	User u8 = new User("emp8", "00+00-08");
	u8.setRole("MANAGER");
	User u9 = new User("emp9", "00+00-09");
	u9.setRole("MANAGER");
	User u10 = new User("emp10", "00+00-010");
	User u11 = new User("emp11", "00+00-011");
	logins.addAll(Arrays.asList(admin,u1,u2,u3,u4,u5,u6,u7,u8,u9,u10,u11));
	
	try {
		imageFileNames = Files.walk(Paths.get("src/CAPCHAimg"))
		        .map(Path::getFileName)
		        .map(Path::toString)
		        .filter(n -> n.endsWith(".jpg"))
		        .collect(Collectors.toList());
		
		Random r = new Random();
		URL u = Paths.get("src/CAPCHAimg/"+imageFileNames.get(r.nextInt(imageFileNames.size()))).toUri().toURL();
		image = new ImageIcon(u);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

public static void showAuthWindow() {
	
	JFrame frame = new JFrame("AUTHENTIFY");
	frame.setBounds(100,100,460,560);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
	frame.getContentPane().setLayout(null);
	
	JTextField inputNameField = new JTextField("login");
	inputNameField.setBounds(150, 90, 120, 30);
	frame.getContentPane().add(inputNameField);
	
	JPasswordField inputPasswField = new JPasswordField("password");
	inputPasswField.setEchoChar('*');
	inputPasswField.setBounds(150, 130, 120, 30);
	frame.getContentPane().add(inputPasswField);
	
	JLabel statusLabel = new JLabel("Log in");
	statusLabel.setBounds(162, 50, 300, 30);
	frame.getContentPane().add(statusLabel);
	
	JLabel resultLabel = new JLabel(" ");
	resultLabel.setBounds(140, 150, 300, 30);
	frame.getContentPane().add(resultLabel);
	
	JButton logButton = new JButton("Log in");
	logButton.setBounds(162, 435, 100, 30);
	frame.getContentPane().add(logButton);
	
	JLabel CAPCHAimage = new JLabel(image);
	CAPCHAimage.setBounds(-50, 193, 532, 236);
	frame.getContentPane().add(CAPCHAimage);
	
	
	JTextField inputCAPCHAField = new JTextField("capcha");
	inputCAPCHAField.setBounds(320, 435, 120, 30);
	frame.getContentPane().add(inputCAPCHAField);
	
	logButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String log = inputNameField.getText();
			String pass = inputPasswField.getText();
			
			Random r = new Random();
			URL ur;
			try {
				ur = Paths.get("src/CAPCHAimg/"+imageFileNames.get(r.nextInt(imageFileNames.size()))).toUri().toURL();
				image = new ImageIcon(ur);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(!inputCAPCHAField.getText().equals(new File(CAPCHAimage.getIcon().toString()).getName().split(".jpg")[0])) {
				System.out.println("yee");
				CAPCHAimage.setIcon(image);
				return;
			}
			
			
			
				
			
			try {
				CeasarCipherer cs = new CeasarCipherer();
				int offset = Integer.parseInt(pass);
				if(cs.decipher(log, offset).equals(accessKey)) {
					accessKeyAccepted = true;
					resultLabel.setText("accessKey accepted, restrictions lifted");
					return;
				}
			} catch (NumberFormatException e4) {
				
			}
			
			
			for(User u : logins) {
				if(u.getLogin().equals(log)&&u.getPassword().equals(pass)) {
					if(u.isBlocked()) {
						resultLabel.setText("Sorry... You seem to have been blocked");
						CAPCHAimage.setIcon(image);
						return;
					}
					logUserLogin(u);
					showManagWindow(u);
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					return;
				}
			}
			CAPCHAimage.setIcon(image);
			resultLabel.setText("Wrong login or password");
			authCounter++;
			if(authCounter>=3) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		}
	});
	
	
	frame.setVisible(true);
	
	JButton forgorButton = new JButton("Forgot password?(input login first)");
	forgorButton.setBounds(100, 470, 230, 30);
	frame.getContentPane().add(forgorButton);
	
	forgorButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String log = inputNameField.getText();
			for(User u : logins) {
				if(log.equals(u.getLogin())) {
					if(u.isBlocked()) {
						resultLabel.setText("Sorry... You seem to have been blocked");
						return;
					}
					if(!u.isQuestionBookExists()) {
						resultLabel.setText("User does not have a question book");
						return;
					}
					rememberWindow(u);
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					return;
				}
			}
			
			resultLabel.setText("Please input a valid login");
		}
	});
}

public static void rememberWindow(User u) {
	
	Object[] keys = u.getQuestionBook().getQuestions().keySet().toArray();
	List<String> qs = new LinkedList<String>();
	for(int i = 0; i<4; i++) {
		qs.add(keys[(int) ((Math.random() * (15 - 1)) + 1)-1].toString());
	}
	
	JFrame frame = new JFrame("HELP");
	frame.setBounds(100, 100, 460, 340);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
	JLabel statusLabel = new JLabel(qs.get(0));
	statusLabel.setBounds(150, 45, 120, 30);
	frame.getContentPane().add(statusLabel);
	
	JTextField inputAnswField = new JTextField("");
	inputAnswField.setBounds(150, 90, 120, 30);
	frame.getContentPane().add(inputAnswField);
	
	JButton answButton = new JButton("Answer");
	answButton.setBounds(150, 120, 120, 30);
	frame.getContentPane().add(answButton);
	
	JLabel resultLabel = new JLabel();
	statusLabel.setBounds(150, 60, 120, 30);
	frame.getContentPane().add(statusLabel);
	
	answButton.addActionListener(new ActionListener() {
		int i = 0;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String temp = u.getQuestionBook().getQuestions().get(qs.get(i));
			if(temp.equals(inputAnswField.getText())) {
				resultLabel.setText("Correct!");
				i++;
			}else {
				resultLabel.setText("Wrong!");
			}
			
			
			if(i==4) {
				logUserLogin(u);
				showManagWindow(u);
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				return;
			}
			statusLabel.setText(qs.get(i));
			
		}
		
	});
	
	frame.setVisible(true);
}

public static JFrame showWorkWindow(User u) {
	JFrame frame = new JFrame("Work");
	frame.setBounds(700, 100, 460, 340);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
	frame.getContentPane().setLayout(null);
	
	JLabel statusLabel = new JLabel("Welcome, "+u.getLogin());
	statusLabel.setBounds(100, 30, 300, 30);
	frame.getContentPane().add(statusLabel);
	
	JLabel resultLabel = new JLabel(" ");
	resultLabel.setBounds(2, 0, 500, 30);
	frame.getContentPane().add(resultLabel);
	
	JButton getFileButton = new JButton("Choose file");
	getFileButton.setBounds(30, 260, 140, 30);
	frame.getContentPane().add(getFileButton);
	
	JButton saveFileButton = new JButton("Save file");
	saveFileButton.setBounds(230, 260, 140, 30);
	frame.getContentPane().add(saveFileButton);
	
	
	
	
	
	JTextField inputPasswField = new JTextField("");
		
	JScrollPane scrollPane = new JScrollPane(inputPasswField);
	scrollPane.setViewportView(inputPasswField);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setBounds(20, 60, 300, 150);
    frame.getContentPane().add(scrollPane);
    
    
    
    getFileButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JFileChooser fileField = new JFileChooser();
			fileField.showOpenDialog(frame);
			File file = fileField.getSelectedFile();
			
			String result = "";
			String temp = "";
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				while ((temp = reader.readLine()) != null) {
					result = result + temp + "\n";
				}
			
				inputPasswField.setText(result);
				resultLabel.setText(file.getPath());
				if(!accessKeyAccepted) {
					showPopup();
				}
				
			
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
    });
    
    saveFileButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(inputPasswField.getText().equals("")) {
				return;
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultLabel.getText()))) {
				writer.write(inputPasswField.getText());
				resultLabel.setText("");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
    });
    
    frame.setVisible(true);
    return frame;
}

public static void showManagWindow(User u) {
	JFrame frame = new JFrame("MANAGEMENT");
	frame.setBounds(100, 100, 460, 340);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
	frame.getContentPane().setLayout(null);
	
	JFrame frameWork = showWorkWindow(u);
	
	JLabel statusLabel = new JLabel("Welcome, "+u.getLogin());
	statusLabel.setBounds(100, 30, 300, 30);
	frame.getContentPane().add(statusLabel);
	
	JLabel resultLabel = new JLabel(" ");
	resultLabel.setBounds(2, 0, 500, 30);
	frame.getContentPane().add(resultLabel);
	
	JButton changeButton = new JButton("Change password");
	changeButton.setBounds(30, 260, 140, 30);
	frame.getContentPane().add(changeButton);
	
	JButton logoutButton = new JButton("Log out");
	logoutButton.setBounds(250, 23, 140, 30);
	frame.getContentPane().add(logoutButton);
	
	DefaultListModel<String> model = new DefaultListModel<String>();
	JList<String> listBox = new JList<String>(model);
	
	JScrollPane scrollPane = new JScrollPane(listBox);
	scrollPane.setViewportView(listBox);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setBounds(20, 60, 300, 150);
    frame.getContentPane().add(scrollPane);
    
    JButton detailsButton = new JButton("About");
	detailsButton.setBounds(300, 260, 140, 30);
	frame.getContentPane().add(detailsButton);
    
    if(u.isAdmin()) {
    	detailsButton.setBounds(300, 450, 140, 30);
    	adminAdditionsToUI(model, frame, resultLabel, listBox, u);
    }else if(u.isManager()){
		int i = 0;
		for(User us : logins) {
			if(us.getLogin().equals(u.getLogin())) {
				model.add(i, us.getLogin()+" "+us.getPassword()+" "+us.isBlocked()+" "+us.isAdmin());
			}else {
				model.add(i, us.getLogin()+" ******** "+us.isBlocked()+" "+us.isAdmin());
			}
			
			i++;
		}
		JButton blockButton = new JButton("Block");
		blockButton.setBounds(330, 70, 90, 130);
		frame.getContentPane().add(blockButton);
		
		blockButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String temp = listBox.getSelectedValue();
				if(temp.equals(null)) {
					return;
				}
				for(User u : logins) {
					if(u.getLogin().equals(temp.split(" ")[0])) {
						u.setBlocked();
					}
				}
				refreshListbox(model, listBox, u);
				
			}
		});
    }else {
    	model.add(0, u.getLogin()+" "+u.getPassword()+" "+u.isBlocked()+" "+u.isAdmin());
    }
	
    JTextField inputPasswField = new JTextField("password");
	inputPasswField.setBounds(20, 220, 120, 30);
	frame.getContentPane().add(inputPasswField);
	inputPasswField.setEnabled(false);
	
	JButton changePasswButton = new JButton("Confirm");
	changePasswButton.setBounds(160, 220, 140, 30);
	frame.getContentPane().add(changePasswButton);
	changePasswButton.setEnabled(false);
	
	
	
	detailsButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			showAboutWindow();
		}
		
	});
    
    
    changeButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String user = "";
			try {
				user = listBox.getSelectedValue().split(" ")[0];
			}catch(NullPointerException ee) {
				resultLabel.setText("Choose a user");
				return;
			}
			
			if(!u.getLogin().equals(user)&&!u.isAdmin()) {
				resultLabel.setText("Manager cannot change others passwords");
				return;
			}
			
			String temp = listBox.getSelectedValue();
			if(temp.equals(null)) {
				return;
			}
			inputPasswField.setText(temp.split(" ")[1]);
			
			inputPasswField.setEnabled(true);
			changePasswButton.setEnabled(true);
			listBox.setEnabled(false);
		}
	});
	
    changePasswButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String temp = inputPasswField.getText();
			
			
			for(int i = 0; i<regexes.length; i++) {
				Pattern pattern = Pattern.compile(regexes[i]);
				Matcher matcher = pattern.matcher(temp);
				if(!matcher.find()) {
					
					resultLabel.setText("<html><font color='red'>New password needs to have digits, separators and math symbols</font></html>");
					return;
				}
			}
			
			
			String user = listBox.getSelectedValue().split(" ")[0];
			
			for(User us : logins) {
				if(us.getLogin().equals(user)) {
					logUserPasswChange(u,us);
					us.setPassword(temp);
				}
			}
			
			
			inputPasswField.setEnabled(false);
			changePasswButton.setEnabled(false);
			listBox.setEnabled(true);
			resultLabel.setText("Password changed for user: "+user);
			refreshListbox(model, listBox, u);
		}
	});
    
    logoutButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			logUserLogout(u);
			showAuthWindow();
			frameWork.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			
			
			
		}
	});
    
	frame.setVisible(true);
}

public static void adminAdditionsToUI(DefaultListModel<String> model, JFrame frame, JLabel resultLabel, JList<String> listBox, User adm) {
	frame.setBounds(100, 100, 460, 540);
	
	int i = 0;
	for(User u : logins) {
		model.add(i, u.getLogin()+" "+u.getPassword()+" "+u.isBlocked()+" "+u.isAdmin());
		i++;
	}
	
	JTextField inputNewUsernameField = new JTextField("username");
	inputNewUsernameField.setBounds(20, 300, 120, 30);
	frame.getContentPane().add(inputNewUsernameField);
	inputNewUsernameField.setEnabled(false);
	
	JButton addButton = new JButton("Confirm");
	addButton.setBounds(160, 300, 140, 30);
	frame.getContentPane().add(addButton);
	addButton.setEnabled(false);
	
	JButton addEmplButton = new JButton("Add new user");
	addEmplButton.setBounds(30, 340, 140, 30);
	frame.getContentPane().add(addEmplButton);
	
	addEmplButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			inputNewUsernameField.setEnabled(true);
			addButton.setEnabled(true);
			
		}
	});
	
	addButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String temp = inputNewUsernameField.getText();
			
			for(User u : logins) {
				if(u.getLogin().equals(temp)) {
					resultLabel.setText("<html><font color='red'>This user already exists: "+temp+"</font></html>");
					return;
				}
			}
			User newUser = new User(temp, ".");
			
			logins.add(newUser);
			refreshListbox(model, listBox, adm);
			resultLabel.setText("New user created: "+temp);
			
			inputNewUsernameField.setEnabled(false);
			addButton.setEnabled(false);
			
			JsonObject jsonObject = null;
			try {
				jsonObject = gson.fromJson(new FileReader(jsonLogs.getAbsolutePath()), JsonObject.class);
			} catch (JsonSyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JsonIOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Map<String, String> map = new HashMap<>();
			map.put("1", "UserCreated");
			
			jsonObject.addProperty(temp, map.toString());
			
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonLogs))) {
		 		writer.write(jsonObject.toString());
		 		System.out.println(jsonObject.toString());
			} catch (JsonIOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	});
	
	JButton blockButton = new JButton("Block");
	blockButton.setBounds(330, 70, 90, 130);
	frame.getContentPane().add(blockButton);
	
	blockButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String temp = listBox.getSelectedValue();
			if(temp.equals(null)) {
				return;
			}
			for(User u : logins) {
				if(u.getLogin().equals(temp.split(" ")[0])) {
					logUserBlock(adm, u);
					u.setBlocked();
				}
			}
			refreshListbox(model, listBox, adm);
			
		}
	});
}

public static void refreshListbox(DefaultListModel<String> model, JList<String> listBox, User us) {
	model.removeAllElements();
	if(us.isAdmin()) {
		int i = 0;
		for(User u : logins) {
			model.add(i, u.getLogin()+" "+u.getPassword()+" "+u.isBlocked()+" "+u.isAdmin());
			i++;
		}
	}
	else if(us.isManager()){
		int i = 0;
		for(User u : logins) {
			if(us.getLogin().equals(u.getLogin())) {
				model.add(i, u.getLogin()+" "+u.getPassword()+" "+u.isBlocked()+" "+u.isAdmin());
			}else {
				model.add(i, u.getLogin()+" ******** "+u.isBlocked()+" "+u.isAdmin());
			}
			
			i++;
		}
	}
	else {
		model.add(0, us.getLogin()+" "+us.getPassword()+" "+us.isBlocked()+" "+us.isAdmin());
	}
}

public static void showAboutWindow() {
	JFrame frame = new JFrame("ABOUT");
	frame.setLayout(new FlowLayout());
	frame.setBounds(100, 100, 460, 340);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
	JLabel infoLabel1 = new JLabel("Titov Y.Y. g. 122-20-1");
	infoLabel1.setBounds(10, 10, 300, 30);
	frame.getContentPane().add(infoLabel1);
	
	JLabel infoLabel2 = new JLabel("Variant 18");
	infoLabel2.setBounds(10, 25, 300, 30);
	frame.getContentPane().add(infoLabel2);
	
	JLabel infoLabel3 = new JLabel("1. Наявність цифр, розділових знаків і знаків арифметичних операцій");
	infoLabel3.setBounds(10, 25, 300, 30);
	frame.getContentPane().add(infoLabel3);
	
	JLabel infoLabel4 = new JLabel("2. Логування дій виконаних корискувачем");
	infoLabel4.setBounds(10, 40, 300, 30);
	frame.getContentPane().add(infoLabel4);
	
	frame.setVisible(true);
}

public static void showPopup() {
	JFrame frame = new JFrame("BUYBUYBUY");
	frame.setLayout(new FlowLayout());
	frame.setBounds(100, 100, 460, 340);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
	JLabel infoLabel1 = new JLabel("Consider buying the app!");
	infoLabel1.setBounds(10, 10, 300, 30);
	frame.getContentPane().add(infoLabel1);
	
	JLabel infoLabel3 = new JLabel("pls buy");
	infoLabel3.setBounds(10, 25, 300, 30);
	frame.getContentPane().add(infoLabel3);
		
	frame.setVisible(true);
}


private static void logUserLogin(User user){
	String result = "";
	String temp;
	try (BufferedReader reader = new BufferedReader(new FileReader(userRegLogs))) {
		while ((temp = reader.readLine()) != null) {
			result = result + temp + "\n";
		}
	
	result = "User "+user.getLogin()+" has logged into an account at "+(new Date())+"\n"+ result;
	try (BufferedWriter writer = new BufferedWriter(new FileWriter(userRegLogs))) {
		writer.write(result);
	}
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

private static void logUserLogout(User user){
	String result = "";
	String temp;
	try (BufferedReader reader = new BufferedReader(new FileReader(userRegLogs))) {
		while ((temp = reader.readLine()) != null) {
			result = result + temp + "\n";
		}
	
	result = "User "+user.getLogin()+" has logged out at "+(new Date())+"\n"+ result;
	try (BufferedWriter writer = new BufferedWriter(new FileWriter(userRegLogs))) {
		writer.write(result);
	}
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

private static void logUserPasswChange(User changer, User user){
	String result = "";
	String temp;
	try (BufferedReader reader = new BufferedReader(new FileReader(userOpLogs))) {
		while ((temp = reader.readLine()) != null) {
			result = result + temp + "\n";
		}
	String dat = new Date().toString();
	String dated = dat+"";
	result = "User "+user.getLogin()+" has got password changed by "+changer.getLogin()+" at "+dat+"\n"+ result;
	try (BufferedWriter writer = new BufferedWriter(new FileWriter(userOpLogs))) {
		writer.write(result);
	}
	} catch (FileNotFoundException e) {
	} catch (IOException e) {
	}
	
	JsonObject jsonObject = null;
	try {
		jsonObject = gson.fromJson(new FileReader(jsonLogs.getAbsolutePath()), JsonObject.class);
	} catch (JsonSyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonIOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	String m2 = jsonObject.get(changer.getLogin()).toString();
	String m3 = m2.substring(2, m2.length()-2);
	Map<String, String> map = new HashMap<>();
    StringTokenizer tokenizer = new StringTokenizer(m3, ", ");

    while (tokenizer.hasMoreTokens()) {
        String token = tokenizer.nextToken();
        System.out.println(token);
        String[] keyValue = token.split("=");
        map.put(keyValue[0], keyValue[1]);
    }
    map.put(map.size()+1+"", user.getLogin()+"GotPasswordChanged");
    JsonObject jsonResult = new JsonObject();
    jsonResult.addProperty(changer.getLogin(), map.toString());
	
    for(String k : jsonObject.keySet()) {
    	if(k.equals(changer.getLogin())) {
    		continue;
    	}
    	jsonResult.addProperty(k, jsonObject.get(k).getAsString());
    }
    
	
	try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonLogs))) {
 		writer.write(jsonResult.toString());
	} catch (JsonIOException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	} catch (IOException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
}

private static void logUserBlock(User blocker, User user){
	String result = "";
	String temp;
	try (BufferedReader reader = new BufferedReader(new FileReader(userOpLogs))) {
		while ((temp = reader.readLine()) != null) {
			result = result + temp + "\n";
		}
	
	result = "User "+user.getLogin()+" was blocked by "+blocker.getLogin()+" at "+(new Date())+"\n"+ result;
	try (BufferedWriter writer = new BufferedWriter(new FileWriter(userOpLogs))) {
		writer.write(result);
	}
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

	
}


