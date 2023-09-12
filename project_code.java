import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Dimension;
import javax.swing.DropMode;

public class Cafe_billing {

	private JFrame frame;
	private JTextField coffeeprice;
	private JTextField snackprice;
	private JTextField riceprise;
	private JTextField burgerprice;
	private JTextField pizzaprice;
	private JTextField teaprice;
	private JTextField saladprice;
	private JTextField totalout;
	
	/* sql related*/
	private PreparedStatement ps;
	private PreparedStatement opset;
	private PreparedStatement firstopn;
	private PreparedStatement searchqry1;
	private PreparedStatement totalsalesqr;
	private PreparedStatement cleardata;
	private PreparedStatement reciptgen;
	private Connection conn;
	ResultSet r,r1;
	
	int cust_id = 1,cust_no =0;
	int quantity;
	String item,item_type;
	String s;
	double price,totoal;
	private JTextField custdis;
	double coffepricet,snackpricet,ricepricet,burgerpricet,pizzapricet,teapricet,saladpricet,distotals = 0.00;
	
	double Espresso =116.00,
	Classic_Filter_Coffee=126.00,
	Macchiato=142.00,
	Cafe_Latte=134.00,
	Vanilla_Cappuccino=149.00,
	Cafe_Frappe=173.00;
	
	double Cookies=15.00,
	Crispy_Samosa_Kulcha=40.00,
	Veg_Samosa=40.00,
	Maska_Masala_Bun=41.00,
	Veg_Cutlet=75.00,
	Saucy_Veg_Noodles=93.00,
	Garlic_Bread=95.00;
	
	double Classic_Rajma_Rice=131.00,
	Buttery_Paneer_Masala_with_Rice=167.00;
	
	double Crunchy_Veg=150.00,
	Crunchy_Veg_Spicy_Cheese=161.00,
	Veg_King=195.00;
	
	double Cheesy_Margherita = 212.00,
	Mediterranean_Farm_Fresh_Veg=289.00,
	Paneer_Garden_Delight=311.00;
	
	double Kadak_Chai=79.00,
	Green_Tea=125.00,
	Darjeeling_Tea=125.00,
	Assam_Tea=125.00,
	Masala_Chai=125.00;
	
	double Garden_Fresh_Salad = 149.00;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cafe_billing window = new Cafe_billing();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Cafe_billing() {
		
		/*sql querys and connection*/
		try {
			String opnquery = "Select * from cafe_billing_table where cust_id = "+cust_id+";";
			String Firstopn = "Select * from newbilling_table where cust_id = "+(cust_id+1)+";";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe_billing_data?characterEncoding=utf8","root","admin123");
			ps = conn.prepareStatement("insert into cafe_billing_table(srno,item_name,item_type,quantity,price,total,cust_id) values(?,?,?,?,?,?,?)");
			opset = conn.prepareStatement(opnquery);
		    totalsalesqr = conn.prepareStatement("select SUM(total) cafe_billing_table where cust_id = "+cust_id+";");
		    cleardata = conn.prepareStatement("delete From cafe_billing_table");
			firstopn = conn.prepareStatement(Firstopn);
			
			
			}
		catch(Exception e) {
			System.out.println(e);
		}
		try {
			cleardata.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 937, 523);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CAFE BILLING SYSTEM");
		lblNewLabel.setBounds(345, 17, 306, 38);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Imprint MT Shadow", Font.ITALIC, 22));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Coffee : ");
		lblNewLabel_1.setBounds(10, 118, 80, 22);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		frame.getContentPane().add(lblNewLabel_1);
		
		final JComboBox coffeoption = new JComboBox();
		coffeoption.setBounds(100, 116, 144, 22);
		coffeoption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String selecteitem = coffeoption.getSelectedItem().toString();
				
				switch(selecteitem)
				{
				case "Espresso":
					coffepricet = Espresso ;
					break;
				case "Classic Filter Coffee":
					coffepricet = Classic_Filter_Coffee;
					break;
				case "Macchiato":
					coffepricet = Macchiato;
					break;
				case "Cafe Latte":
					coffepricet = Cafe_Latte;
					break;
				case "Vanilla Cappuccino":
					coffepricet = Vanilla_Cappuccino;
					break;
				case "Cafe Frappe":
					coffepricet = Cafe_Frappe;
					break;
				}
				
				coffeeprice.setText(String.valueOf( coffepricet));
			}
		});
		coffeoption.setModel(new DefaultComboBoxModel(new String[] {"Espresso", "Classic Filter Coffee", "Macchiato", "Cafe Latte", "Vanilla Cappuccino", "Cafe Frappe"}));
		frame.getContentPane().add(coffeoption);
		
		final JSpinner coffeeqty = new JSpinner();
		coffeeqty.setBounds(267, 118, 52, 20);
		coffeeqty.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				quantity = Integer.parseInt(coffeeqty.getValue().toString());
				switch(coffeoption.getSelectedItem().toString())
				{
				case "Espresso":
					coffepricet = Espresso*quantity ;
					break;
				case "Classic Filter Coffee":
					coffepricet = Classic_Filter_Coffee*quantity;
					break;
				case "Macchiato":
					coffepricet = Macchiato*quantity;
					break;
				case "Cafe Latte":
					coffepricet = Cafe_Latte*quantity;
					break;
				case "Vanilla Cappuccino":
					coffepricet = Vanilla_Cappuccino*quantity;
					break;
				case "Cafe Frappe":
					coffepricet = Cafe_Frappe*quantity;
					break;
				}
				coffeeprice.setText(String.valueOf(coffepricet));
			}
		});
		coffeeqty.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		frame.getContentPane().add(coffeeqty);
		
		JButton coffeeadd = new JButton("Add");
		coffeeadd.setBounds(425, 116, 75, 23);
		coffeeadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cust_no++;
				try {
					ps.setInt(1, cust_no);
					ps.setString(2, coffeoption.getSelectedItem().toString());
					ps.setString(3, "Coffe");
					ps.setInt(4, Integer.parseInt(coffeeqty.getValue().toString()));
					switch(coffeoption.getSelectedItem().toString()) {
					case "Espresso":
						ps.setDouble(5, Espresso);
						break;
					case "Classic Filter Coffee":
						ps.setDouble(5, Classic_Filter_Coffee);
						break;
					case "Macchiato":
						ps.setDouble(5, Macchiato);
						break;
					case "Cafe Latte":
						ps.setDouble(5, Cafe_Latte);
						break;
					case "Vanilla Cappuccino":
						ps.setDouble(5, Vanilla_Cappuccino);
						break;
					case "Cafe Frappe":
						ps.setDouble(5, Cafe_Frappe);
						break;
					}
					
					ps.setDouble(6, Double.parseDouble((coffeeprice.getText().toString())));
					ps.setInt(7, cust_id);
					ps.executeUpdate();
					
				} 
				
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				distotals = distotals+Double.parseDouble(coffeeprice.getText().toString());
				totalout.setText(String.valueOf(distotals)); 	
			}
		});
		frame.getContentPane().add(coffeeadd);
		
		JLabel lblNewLabel_2 = new JLabel("Snacks :");
		lblNewLabel_2.setBounds(10, 163, 80, 20);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		frame.getContentPane().add(lblNewLabel_2);
		
		coffeeprice = new JTextField();
		coffeeprice.setBounds(329, 117, 86, 20);
		frame.getContentPane().add(coffeeprice);
		coffeeprice.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Food ");
		lblNewLabel_3.setBounds(24, 93, 46, 14);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Type");
		lblNewLabel_4.setBounds(100, 93, 46, 12);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Qty.");
		lblNewLabel_5.setBounds(267, 93, 46, 14);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Price");
		lblNewLabel_6.setBounds(329, 93, 46, 14);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 13));
		frame.getContentPane().add(lblNewLabel_6);
		
		final JComboBox snackoption = new JComboBox();
		snackoption.setBounds(100, 161, 144, 22);
		snackoption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
                  String selecteitem = snackoption.getSelectedItem().toString();
				
				switch(selecteitem)
				{
				case "Cookies":
					snackpricet = Cookies ;
					break;
				case "Crispy Samosa Kulcha":
					snackpricet = Crispy_Samosa_Kulcha;
					break;
				case "Veg Samosa":
					snackpricet = Veg_Samosa;
					break;
				case "Maska Masala Bun":
					snackpricet = Maska_Masala_Bun;
					break;
				case "Veg Cutlet":
					snackpricet = Veg_Cutlet;
					break;
				case "Saucy Veg Noodles":
					snackpricet = Saucy_Veg_Noodles;
					break;
				case "Garlic Bread":
					snackpricet = Garlic_Bread;
				}
				
				snackprice.setText(String.valueOf(snackpricet));
			}
		});
		snackoption.setModel(new DefaultComboBoxModel(new String[] {"Cookies", "Crispy Samosa Kulcha", "Veg Samosa", "Maska Masala Bun", "Veg Cutlet", "Saucy Veg Noodles", "Garlic Bread"}));
		frame.getContentPane().add(snackoption);
		
		final JSpinner snackqty = new JSpinner();
		snackqty.setBounds(267, 162, 52, 20);
		snackqty.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				quantity = Integer.parseInt(snackqty.getValue().toString());
				switch(snackoption.getSelectedItem().toString())
				{
				case "Cookies":
					snackpricet = Cookies*quantity;
					break;
				case "Crispy Samosa Kulcha":
					snackpricet = Crispy_Samosa_Kulcha*quantity;
					break;
				case "Veg Samosa":
					snackpricet = Veg_Samosa*quantity;
					break;
				case "Maska Masala Bun":
					snackpricet = Maska_Masala_Bun*quantity;
					break;
				case "Veg Cutlet":
					snackpricet = Veg_Cutlet*quantity;
					break;
				case "Saucy Veg Noodles":
					snackpricet = Saucy_Veg_Noodles*quantity;
					break;
				case "Garlic Bread":
					snackpricet = Garlic_Bread*quantity;
					break;
				}
				snackprice.setText(String.valueOf(snackpricet));
			}
		});
		snackqty.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		frame.getContentPane().add(snackqty);
		
		snackprice = new JTextField();
		snackprice.setBounds(329, 162, 86, 20);
		frame.getContentPane().add(snackprice);
		snackprice.setColumns(10);
		
		JButton snacksadd = new JButton("Add");
		snacksadd.setBounds(425, 161, 75, 23);
		snacksadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                
				cust_no++;
				try {
					ps.setInt(1, cust_no);
					ps.setString(2, snackoption.getSelectedItem().toString());
					ps.setString(3, "Snack");
					ps.setInt(4, Integer.parseInt(snackqty.getValue().toString()));
					switch(snackoption.getSelectedItem().toString()) {
					case "Cookies":
						ps.setDouble(5, Cookies);
						break;
					case "Crispy Samosa Kulcha":
						ps.setDouble(5, Crispy_Samosa_Kulcha);
						break;
					case "Veg Samosa":
						ps.setDouble(5, Veg_Samosa);
						break;
					case "Maska Masala Bun":
						ps.setDouble(5, Maska_Masala_Bun);
						break;
					case "Veg Cutlet":
						ps.setDouble(5, Veg_Cutlet);
						break;
					case "Saucy Veg Noodles":
						ps.setDouble(5, Saucy_Veg_Noodles);
						break;
					case "Garlic_Bread":
						ps.setDouble(5, Garlic_Bread);
						break;
					}
					
					ps.setDouble(6, Double.parseDouble((snackprice.getText().toString())));
					ps.setInt(7, cust_id);
					ps.executeUpdate();
					
				} 
				
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				distotals = distotals+Double.parseDouble(snackprice.getText().toString());
				totalout.setText(String.valueOf(distotals)); 	
			}
		});
		frame.getContentPane().add(snacksadd);
		
		JLabel lblNewLabel_7 = new JLabel("Rice :");
		lblNewLabel_7.setBounds(30, 207, 46, 22);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 16));
		frame.getContentPane().add(lblNewLabel_7);
		
		final JComboBox riceoption = new JComboBox();
		riceoption.setBounds(100, 205, 144, 22);
		riceoption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
                String selecteitem = riceoption.getSelectedItem().toString();
				
				switch(selecteitem)
				{
				case "Classic Rajma Rice":
					ricepricet = Classic_Rajma_Rice ;
					break;
				case "Buttery Paneer Masala with Rice":
					ricepricet = Buttery_Paneer_Masala_with_Rice;
					break;
				}
				
				riceprise.setText(String.valueOf(ricepricet));
			}
		});
		riceoption.setModel(new DefaultComboBoxModel(new String[] {"Classic Rajma Rice", "Buttery Paneer Masala with Rice"}));
		frame.getContentPane().add(riceoption);
		
		final JSpinner riceqty = new JSpinner();
		riceqty.setBounds(267, 206, 52, 20);
		riceqty.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				quantity = Integer.parseInt(riceqty.getValue().toString());
				switch(riceoption.getSelectedItem().toString())
				{
				case "Classic Rajma Rice":
					ricepricet = Classic_Rajma_Rice*quantity ;
					break;
				case "Buttery Paneer Masala with Rice":
					ricepricet = Buttery_Paneer_Masala_with_Rice*quantity;
					break;
				}
				riceprise.setText(String.valueOf(ricepricet));
			}
		});
		riceqty.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		frame.getContentPane().add(riceqty);
		
		riceprise = new JTextField();
		riceprise.setBounds(329, 206, 86, 20);
		frame.getContentPane().add(riceprise);
		riceprise.setColumns(10);
		
		JButton riceadd = new JButton("Add");
		riceadd.setBounds(425, 205, 75, 23);
		riceadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cust_no++;
				try {
					ps.setInt(1, cust_no);
					ps.setString(2, riceoption.getSelectedItem().toString());
					ps.setString(3, "Rice");
					ps.setInt(4, Integer.parseInt(riceqty.getValue().toString()));
					switch(riceoption.getSelectedItem().toString()) {
					case "Classic Rajma Rice":
						ps.setDouble(5, Classic_Rajma_Rice);
						break;
					case "Buttery Paneer Masala with Rice":
						ps.setDouble(5, Buttery_Paneer_Masala_with_Rice);
						break;
					}
					
					ps.setDouble(6, Double.parseDouble((riceprise.getText().toString())));
					ps.setInt(7, cust_id);
					ps.executeUpdate();
					
				} 
				
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				distotals = distotals+Double.parseDouble(riceprise.getText().toString());
				totalout.setText(String.valueOf(distotals));
			}
		});
		frame.getContentPane().add(riceadd);
		
		JLabel lblNewLabel_7_1 = new JLabel("Burger :");
		lblNewLabel_7_1.setBounds(10, 249, 66, 22);
		lblNewLabel_7_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		frame.getContentPane().add(lblNewLabel_7_1);
		
		final JComboBox burgeroption = new JComboBox();
		burgeroption.setBounds(100, 251, 144, 22);
		burgeroption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
                String selecteitem = burgeroption.getSelectedItem().toString();
				
				switch(selecteitem)
				{
				case "Crunchy Veg":
					burgerpricet = Crunchy_Veg;
					break;
				case "Crunchy Veg Spicy Cheese":
					burgerpricet = Crunchy_Veg_Spicy_Cheese;
					break;
				case "Veg King":
					burgerpricet = Veg_King;
				}
				
				burgerprice.setText(String.valueOf(burgerpricet));
			}
		});
		burgeroption.setModel(new DefaultComboBoxModel(new String[] {"Crunchy Veg", "Crunchy Veg Spicy Cheese", "Veg King"}));
		frame.getContentPane().add(burgeroption);
		
		final JSpinner burgerqty = new JSpinner();
		burgerqty.setBounds(267, 252, 52, 20);
		burgerqty.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				quantity = Integer.parseInt(burgerqty.getValue().toString());
				switch(burgeroption.getSelectedItem().toString())
				{
				case "Crunchy Veg":
					burgerpricet = Crunchy_Veg*quantity;
					break;
				case "Crunchy Veg Spicy Cheese":
					burgerpricet = Crunchy_Veg_Spicy_Cheese*quantity;
					break;
				case "Veg King":
					burgerpricet = Veg_King*quantity;
				}
				burgerprice.setText(String.valueOf(burgerpricet));
			}
		});
		burgerqty.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		frame.getContentPane().add(burgerqty);
		
		burgerprice = new JTextField();
		burgerprice.setBounds(329, 252, 86, 20);
		burgerprice.setColumns(10);
		frame.getContentPane().add(burgerprice);
		
		JButton burgeradd = new JButton("Add");
		burgeradd.setBounds(425, 251, 75, 23);
		burgeradd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cust_no++;
				try {
					ps.setInt(1, cust_no);
					ps.setString(2, burgeroption.getSelectedItem().toString());
					ps.setString(3, "Burger");
					ps.setInt(4, Integer.parseInt(burgerqty.getValue().toString()));
					switch(burgeroption.getSelectedItem().toString()) {
					case "Crunchy Veg":
						ps.setDouble(5, Crunchy_Veg);
						break;
					case "Crunchy Veg Spicy Cheese":
						ps.setDouble(5, Crunchy_Veg_Spicy_Cheese);
						break;
					case "Veg King":
						ps.setDouble(5, Veg_King);
						break;
					}
					
					ps.setDouble(6, Double.parseDouble((burgerprice.getText().toString())));
					ps.setInt(7, cust_id);
					ps.executeUpdate();
					
				} 
				
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				distotals = distotals+Double.parseDouble(burgerprice.getText().toString());
				totalout.setText(String.valueOf(distotals));
			}
		});
		frame.getContentPane().add(burgeradd);
		
		JLabel lblNewLabel_7_1_1 = new JLabel("Pizza :");
		lblNewLabel_7_1_1.setBounds(21, 291, 60, 22);
		lblNewLabel_7_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		frame.getContentPane().add(lblNewLabel_7_1_1);
		
		final JComboBox pizzaoption = new JComboBox();
		pizzaoption.setBounds(100, 293, 144, 22);
		pizzaoption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			
			String selecteitem = pizzaoption.getSelectedItem().toString();
			
			switch(selecteitem)
			{
			case "Cheesy Margherita":
				pizzapricet = Cheesy_Margherita ;
				break;
			case "Mediterranean Farm Fresh Veg":
				pizzapricet = Mediterranean_Farm_Fresh_Veg;
				break;
			case "Paneer Garden Delight":
				pizzapricet = Paneer_Garden_Delight;
				break;
			}
			
			pizzaprice.setText(String.valueOf(pizzapricet));
	     }
		});
		pizzaoption.setModel(new DefaultComboBoxModel(new String[] {"Cheesy Margherita", "Mediterranean Farm Fresh Veg", "Paneer Garden Delight"}));
		frame.getContentPane().add(pizzaoption);
		
		final JSpinner pizzaqty = new JSpinner();
		pizzaqty.setBounds(267, 294, 52, 20);
		pizzaqty.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				quantity = Integer.parseInt(pizzaqty.getValue().toString());
				switch(pizzaoption.getSelectedItem().toString())
				{
				case "Cheesy Margherita":
					pizzapricet = Cheesy_Margherita*quantity;
					break;
				case "Mediterranean Farm Fresh Veg":
					pizzapricet = Mediterranean_Farm_Fresh_Veg*quantity;
					break;
				case "Paneer Garden Delight":
					pizzapricet = Paneer_Garden_Delight*quantity;
					break;
				}
				pizzaprice.setText(String.valueOf(pizzapricet));
			}
		});
		pizzaqty.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		frame.getContentPane().add(pizzaqty);
		
		pizzaprice = new JTextField();
		pizzaprice.setBounds(329, 294, 86, 20);
		frame.getContentPane().add(pizzaprice);
		pizzaprice.setColumns(10);
		
		JButton pizzaadd = new JButton("Add");
		pizzaadd.setBounds(425, 293, 75, 23);
		pizzaadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cust_no++;
				try {
					ps.setInt(1, cust_no);
					ps.setString(2, pizzaoption.getSelectedItem().toString());
					ps.setString(3, "Pizza");
					ps.setInt(4, Integer.parseInt(pizzaqty.getValue().toString()));
					switch(pizzaoption.getSelectedItem().toString()) {
					case "Cheesy Margherita":
						ps.setDouble(5, Cheesy_Margherita);
						break;
					case "Mediterranean Farm Fresh Veg":
						ps.setDouble(5, Mediterranean_Farm_Fresh_Veg);
						break;
					case "Paneer Garden Delight":
						ps.setDouble(5, Paneer_Garden_Delight);
						break;
					}
					
					ps.setDouble(6, Double.parseDouble((pizzaprice.getText().toString())));
					ps.setInt(7, cust_id);
					ps.executeUpdate();
					
				} 
				
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				distotals = distotals+Double.parseDouble(pizzaprice.getText().toString());
				totalout.setText(String.valueOf(distotals));
			}
		});
		frame.getContentPane().add(pizzaadd);
		
		JLabel lblNewLabel_8 = new JLabel("Tea :");
		lblNewLabel_8.setBounds(35, 335, 46, 14);
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 16));
		frame.getContentPane().add(lblNewLabel_8);
		
		final JComboBox teaoption = new JComboBox();
		teaoption.setBounds(100, 333, 144, 22);
		teaoption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
                String selecteitem = teaoption.getSelectedItem().toString();
				
				switch(selecteitem)
				{
				case "Kadak Chai":
					teapricet = Kadak_Chai ;
					break;
				case "Green Tea":
					teapricet = Green_Tea;
					break;
				case "Darjeeling Tea":
					teapricet = Darjeeling_Tea;
					break;
				case "Assam Tea":
					teapricet = Assam_Tea;
					break;
				case "Masala Chai":
					teapricet = Masala_Chai;
					break;
				}
				
				teaprice.setText(String.valueOf( teapricet));
			}
		});
		teaoption.setModel(new DefaultComboBoxModel(new String[] {"Kadak Chai", "Green Tea", "Darjeeling Tea", "Assam Tea", "Masala Chai"}));
		frame.getContentPane().add(teaoption);
		
		final JSpinner teaqty = new JSpinner();
		teaqty.setBounds(267, 334, 52, 20);
		teaqty.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				quantity = Integer.parseInt(teaqty.getValue().toString());
				switch(teaoption.getSelectedItem().toString())
				{
				case "Kadak Chai":
					teapricet = Kadak_Chai*quantity;
					break;
				case "Green Tea":
					teapricet = Green_Tea*quantity;
					break;
				case "Darjeeling Tea":
					teapricet = Darjeeling_Tea*quantity;
					break;
				case "Assam Tea":
					teapricet = Assam_Tea*quantity;
					break;
				case "Masala Chai":
					teapricet = Masala_Chai*quantity;
					break;
				}
				teaprice.setText(String.valueOf( teapricet));
			}
		});
		teaqty.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		frame.getContentPane().add(teaqty);
		
		teaprice = new JTextField();
		teaprice.setBounds(329, 334, 86, 20);
		teaprice.setColumns(10);
		frame.getContentPane().add(teaprice);
		
		JButton teaadd = new JButton("Add");
		teaadd.setBounds(425, 333, 75, 23);
		teaadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cust_no++;
				try {
					ps.setInt(1, cust_no);
					ps.setString(2, teaoption.getSelectedItem().toString());
					ps.setString(3, "Tea");
					ps.setInt(4, Integer.parseInt(teaqty.getValue().toString()));
					switch(teaoption.getSelectedItem().toString()) {
					case "Kadak Chai":
						ps.setDouble(5, Kadak_Chai);
						break;
					case "Green Tea":
						ps.setDouble(5, Green_Tea);
						break;
					case "Darjeeling Tea":
						ps.setDouble(5, Darjeeling_Tea);
						break;
					case "Assam Tea":
						ps.setDouble(5, Assam_Tea);
						break;
					case "Masala Chai":
						ps.setDouble(5, Masala_Chai);
						break;
		
					}
					
					ps.setDouble(6, Double.parseDouble((teaprice.getText().toString())));
					ps.setInt(7, cust_id);
					ps.executeUpdate();
					
				} 
				
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				distotals = distotals+Double.parseDouble(teaprice.getText().toString());
				totalout.setText(String.valueOf(distotals));
			}
		});
		frame.getContentPane().add(teaadd);
		
		JLabel lblNewLabel_1_1 = new JLabel("Salads : ");
		lblNewLabel_1_1.setBounds(15, 374, 75, 22);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		frame.getContentPane().add(lblNewLabel_1_1);
		
		final JComboBox saladoption = new JComboBox();
		saladoption.setBounds(100, 376, 144, 22);
		saladoption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String selecteitem = saladoption.getSelectedItem().toString();
				
				switch(selecteitem)
				{
				case "Garden Fresh Salad":
					saladpricet = Garden_Fresh_Salad ;
					break;
				}
				
				saladprice.setText(String.valueOf(saladpricet));
			}
		});
		saladoption.setModel(new DefaultComboBoxModel(new String[] {"Garden Fresh Salad"}));
		frame.getContentPane().add(saladoption);
		
		final JSpinner saladqty = new JSpinner();
		saladqty.setBounds(267, 377, 52, 20);
		saladqty.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				quantity = Integer.parseInt(saladqty.getValue().toString());
				switch(saladoption.getSelectedItem().toString())
				{
				case "Garden Fresh Salad":
					saladpricet = Garden_Fresh_Salad*quantity;
					break;
				}
				saladprice.setText(String.valueOf(saladpricet));
			}
		});
		saladqty.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		frame.getContentPane().add(saladqty);
		
		saladprice = new JTextField();
		saladprice.setBounds(329, 377, 86, 20);
		saladprice.setColumns(10);
		frame.getContentPane().add(saladprice);
		
		JButton saladadd = new JButton("Add");
		saladadd.setBounds(425, 376, 75, 23);
		saladadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cust_no++;
				try {
					ps.setInt(1, cust_no);
					ps.setString(2, saladoption.getSelectedItem().toString());
					ps.setString(3, "Salad");
					ps.setInt(4, Integer.parseInt(saladqty.getValue().toString()));
					switch(saladoption.getSelectedItem().toString()) {
					case "Garden Fresh Salad":
						ps.setDouble(5, Garden_Fresh_Salad);
						break;
					case "Mediterranean Farm Fresh Veg":
						ps.setDouble(5, Mediterranean_Farm_Fresh_Veg);
						break;
					}
					
					ps.setDouble(6, Double.parseDouble((saladprice.getText().toString())));
					ps.setInt(7, cust_id);
					ps.executeUpdate();
					
				} 
				
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				distotals = distotals+Double.parseDouble(saladprice.getText().toString());
				totalout.setText(String.valueOf(distotals));
			}
		});
		frame.getContentPane().add(saladadd);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 66, 921, 2);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(568, 68, 2, 416);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		frame.getContentPane().add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 405, 569, 2);
		frame.getContentPane().add(separator_2);
		
		JButton btnNewButton_5 = new JButton("New Customer");
		btnNewButton_5.setBounds(10, 418, 109, 23);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cust_id++;
				custdis.setText(String.valueOf(cust_id));
				coffeeprice.setText("");
				snackprice.setText("");
				riceprise.setText("");
				burgerprice.setText("");
				pizzaprice.setText("");
				teaprice.setText("");
				saladprice.setText("");
				totalout.setText("");
				
				coffeeqty.setValue(1);
				snackqty.setValue(1);
				riceqty.setValue(1);
				burgerqty.setValue(1);
				pizzaqty.setValue(1);
				teaqty.setValue(1);
				saladqty.setValue(0);
			}
		});
		frame.getContentPane().add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Generate Reciept");
		btnNewButton_6.setBounds(425, 418, 133, 23);
		frame.getContentPane().add(btnNewButton_6);
		
		totalout = new JTextField();
		totalout.setBounds(320, 418, 95, 20);
		frame.getContentPane().add(totalout);
		totalout.setText(String.valueOf(0.00));
		totalout.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Total :");
		lblNewLabel_9.setBounds(273, 421, 46, 14);
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 13));
		frame.getContentPane().add(lblNewLabel_9);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(0, 456, 570, 2);
		frame.getContentPane().add(separator_3);
		
		JLabel lblNewLabel_10 = new JLabel("Cutomer No. :");
		lblNewLabel_10.setBounds(10, 17, 102, 22);
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 13));
		frame.getContentPane().add(lblNewLabel_10);
		
		custdis = new JTextField();
		custdis.setBounds(110, 17, 52, 20);
		frame.getContentPane().add(custdis);
		custdis.setColumns(10);
		custdis.setText(String.valueOf(cust_id));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(580, 93, 320, 380);
		frame.getContentPane().add(scrollPane);
		
		final JTextArea billdisplay = new JTextArea();
		scrollPane.setViewportView(billdisplay);
		billdisplay.setRows(15);
		billdisplay.setColumns(5);
		
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("HI");
					billdisplay.setText("******************************************************\n");
					
					 
					 billdisplay.setText(billdisplay.getText()+"--------------------Cafe Coffee Day-------------------\n"
					 		            +"******************************************************\n"
					 		            +" Item Name            Price        Quantity      Total\n"
					 		            +"-------------------------------------------------------------------\n");
				
					 r1 = opset.executeQuery();
					 try {
						
							while(r1.next())
							{
								billdisplay.setText(billdisplay.getText()+" "+r1.getString(2)+"             ");
								billdisplay.setText(billdisplay.getText()+String.valueOf(r1.getDouble(5))+"             ");
								billdisplay.setText(billdisplay.getText()+String.valueOf(r1.getInt(4))+"             ");
								billdisplay.setText(billdisplay.getText()+String.valueOf(r1.getDouble(6))+"     ");
								billdisplay.setText(billdisplay.getText()+"\n");
								
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						billdisplay.setText(billdisplay.getText()+"--------------------------------------------------------------------\n"+
						                  "                                                          Total = "+totalout.getText().toString()+
						                  "\n*******************************************************\n"
								);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}
