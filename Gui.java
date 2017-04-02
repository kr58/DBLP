/**
 * This class handles the Gui Part of DBLP Query Engine
 * It has one constructor of the type Gui(String)
 * The title text needs to be passed in constructor
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.util.*;
public class Gui extends JFrame {
	private int query,authtag,year,sort,counter=0,previous=0;
	private String textresult[]=new String[]{"","","","","",""};
	private MyActionListener myActionListener;
	private ArrayList<String> query2_list=new ArrayList<>();
	private ArrayList<Record> results=new ArrayList<>();
	private ArrayList<Integer> pred=new ArrayList<>();
	private JTable table;
	private JLabel label,l3;
	private JComboBox<String> jc,jc2;
	private JPanel jpanel,jpanel2,jp,jp2,jp3,jp5,jp6,jp7,jp8,jp9,jpn,start,amine;
	private JButton jb,jb2,jb3,jb4;
	private JRadioButton first,second,first1,second1;
	private JTextField txt1,txt2,txt3,txt4,txt5,txt6;
	private JSplitPane splitPane;
	private ButtonGroup bg,bg2;
	Gui(String title) {
		setSize(900,600);setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,2));
		myActionListener=new MyActionListener(this);
		setMinimumSize(new Dimension(900,600));
	}
	public void start() {
		initilize();clear_list();query=0;authtag=0;year=0;sort=0;
		jp.add(new JPanel());
		addcombo(new String[]{"Choose one","Query - 1","Query - 2","Query - 3"},true);
		add_table();setVisible(true);
	}
	private void add_table() {
		table=new JTable(new TableModel());table.setMinimumSize(new Dimension(400,450));
		JScrollPane scrollPane = new JScrollPane(table);table.setRowHeight(20);
		jpn.add(scrollPane,BorderLayout.NORTH);
		jb4=new JButton("Previous");jb3=new JButton("Next");
		JPanel jt2=new JPanel(new FlowLayout());jt2.add(jb4);jt2.add(jb3);
		jpn.add(jt2,BorderLayout.EAST);
		jb3.addActionListener(myActionListener);
		jb4.addActionListener(myActionListener);
	}
	public int data_check() {
		boolean flag=true;
		if(query==2){try{Integer.parseInt(txt2.getText());} catch(Exception e){flag=false;}}
		else if(query==1){
			try{Integer.parseInt(txt1.getText());flag=false;}catch (Exception e){}
			try{
				if(year==1) Integer.parseInt(txt4.getText());
				else if(year==2) {Integer.parseInt(txt2.getText());Integer.parseInt(txt3.getText());}
			}catch (Exception e){flag=false;}
			if(flag==true && year==2){if(Integer.parseInt(txt2.getText())>Integer.parseInt(txt3.getText())) flag=false;}
			if(txt1.getText()==null) flag=false;
		}
		else {
			try{Integer.parseInt(txt1.getText());}catch(Exception e){flag=false;}
			try{Integer.parseInt(txt2.getText());flag=false;}catch(Exception e){}
			try{Integer.parseInt(txt3.getText());flag=false;}catch(Exception e){}
			try{Integer.parseInt(txt4.getText());flag=false;}catch(Exception e){}
			try{Integer.parseInt(txt5.getText());flag=false;}catch(Exception e){}
			try{Integer.parseInt(txt6.getText());flag=false;}catch(Exception e){}
			if(txt2.getText().length()==0 || txt3.getText().length()==0 || txt4.getText().length()==0 || txt5.getText().length()==0 || txt6.getText().length()==0) flag=false;
		}
		if(flag==false)  {new Error_Message("Field is empty");return 1;}
		return 0;
	}
	public int select(){
		if(first.isSelected()) {authtag=1;}
		else if(second.isSelected()) {authtag=2;}
		else {new Error_Message("Select any Author/Title");return 1;}
		textresult[1]=txt1.getText();
		if(year==1) {textresult[2]=txt4.getText();}
		else if(year==2) {textresult[2]=txt2.getText();textresult[3]=txt3.getText();}
		if(first1.isSelected()) sort=1;
		else if(second1.isSelected()) sort=2;
		else {new Error_Message("Select any Sort Type"); return 1;}
		return 0;}
	public void animation() {
		setVisible(false);
		amine=new JPanel(new FlowLayout());
		ImageIcon loading = new ImageIcon("ajax-loader.gif");
		amine.add(new JLabel(loading, JLabel.HORIZONTAL),BorderLayout.SOUTH);
		jpn.add(amine,BorderLayout.WEST);setVisible(true);
	}
	public void removeanimation()
	{
		setVisible(false);
		start=new JPanel(new FlowLayout());
		jpn.remove(amine);
		start.add(new JLabel("Data Ready  ", JLabel.HORIZONTAL),BorderLayout.SOUTH);
		jpn.add(start,BorderLayout.WEST);setVisible(true);
	}
	public void initilize() {
		jpanel=new JPanel();jpanel2=new JPanel();jp=new JPanel();jpn=new JPanel(new BorderLayout());
		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		jpanel.setMaximumSize(new Dimension(900,100));
		add(jpanel);add(jpanel2);
		label = new JLabel("<html><b>DBLP Query Engine</b></html>",JLabel.CENTER);
        jpanel2.setBorder(new EmptyBorder(5, 5, 5, 5));
        jpanel2.setLayout(new BorderLayout(0, 0));
		label.setFont(new Font("Serif",Font.PLAIN,50));jpanel.add(label);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jp, jpn);
        splitPane.setOneTouchExpandable(true);
        jpanel2.add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(300);
		jp.setLayout(new GridLayout(7,1));}
	private void addtextfield(JPanel addtext,String str,int k) {
		label = new JLabel(str,JLabel.LEFT);
		JPanel jpte=new JPanel();jpte.add(label);
		if(k==1){ txt2=new JTextField(9);jpte.add(txt2);}
		else if(k==2) {txt1=new JTextField(9);jpte.add(txt1);}
		else if(k==3) {txt4=new JTextField(9);jpte.add(txt4);}
		else if(k==4) {txt3=new JTextField(9);jpte.add(txt3);}
		else if(k==5) {txt5=new JTextField(9);jpte.add(txt5);}
		else if(k==6) {txt6=new JTextField(9);jpte.add(txt6);}
		addtext.add(jpte);jp.add(addtext);}
	private void custom(String str, JPanel customtext) {
		txt2=new JTextField(6);txt3=new JTextField(6);
		label=new JLabel(str,JLabel.LEFT);l3=new JLabel("  -  ");
		customtext.add(label,BorderLayout.SOUTH);
		customtext.add(txt2);customtext.add(l3);customtext.add(txt3);
		jp.add(customtext);}
	private void addradiobutton(String[] st,JPanel radio,int k) {
		if(k==1) {
			bg=new ButtonGroup();first = new JRadioButton(st[0]);second = new JRadioButton(st[1]);
			bg.add(first);bg.add(second);
			radio.add(first);radio.add(second);}
		else {
			bg2=new ButtonGroup();first1 = new JRadioButton(st[0]);second1 = new JRadioButton(st[1]);
			bg2.add(first1);bg2.add(second1);
			radio.add(first1);first1.setSelected(true);
			radio.add(second1);}
		jp.add(radio);}
	private void addbuttonreset(JPanel reset) {
		jb=new JButton("Search");jb2=new JButton("Reset");
		jb.addActionListener(myActionListener);
		jb2.addActionListener(myActionListener);
		reset.add(jb);reset.add(jb2);
		jp.add(reset);}
	public String queryStarting() {
		setVisible(false);
		jp.removeAll();jp.repaint();
		jp9 = new JPanel();
		jp9.add(jc);jp.add(jp9);
		return (String) jc.getSelectedItem();}
	public String startinquery1() {
		setVisible(false);
		try{jp6.removeAll();jp7.removeAll();jp8.removeAll();} catch(Exception e){}
		return (String)jc2.getSelectedItem();}
	public void Yearquery(int f) {
		if(f==1) addtextfield(jp6,"Since Year",3);
		else if(f==2) custom("Between Year",jp6);
		addradiobutton(new String[]{"Sort by Date","Sort By Relevance"},jp7,2);
		addbuttonreset(jp8);
		jp2.add(jp6);year=f;
		setVisible(true);}
	private void addcombo(String[] st,boolean chk) {
		if(chk == true){
			jc=new JComboBox<String>(st);jc.setActionCommand("Queries");
			jp2=new JPanel();
			jp2.add(jc);jp.add(jp2);
			jc.addActionListener(myActionListener);}
		else {
			jc2=new JComboBox<String>(st);JPanel jtry=new JPanel();
			jp2=new JPanel(new GridLayout(2,1));jtry.add(jc2);
			jp2.add(jtry);jc2.setActionCommand("Years");jp.add(jp2);
			jc2.addActionListener(myActionListener);}}
	public void query1() {
		clear_list();query=1;jp6=new JPanel();jp7=new JPanel();jp8=new JPanel();JPanel test=new JPanel();jp5=new JPanel();
		label = new JLabel("  Query 1",JLabel.LEFT);
		label.setFont(new Font("Serif",Font.PLAIN,20));
		jp3=new JPanel(new FlowLayout(FlowLayout.LEFT));jp3.add(label);
		jp.add(jp3);
		addradiobutton(new String[]{"Author Name","Title tag"},jp5,1);
		addtextfield(test,"  Names / Title Tags : ",2);jp5.add(test);
		addcombo(new String[]{"Choose one","Since Year","Between Year","None"},false);}
	public void query2() {clear_list();query=2;label = new JLabel("  Query 2",JLabel.LEFT);
		label.setFont(new Font("Serif",Font.PLAIN,20));
		jp3=new JPanel(new FlowLayout(FlowLayout.LEFT));jp3.add(label);
		jp.add(jp3);System.out.print(jp3.getSize());
		jp5=new JPanel();
		addtextfield(jp5,"No. of Publications",1);
		jp.add(new JPanel());jp.add(new JPanel());
		addbuttonreset(new JPanel());}
	public void query3(){
		clear_list();query=3;label = new JLabel("  Query 3",JLabel.LEFT);
		label.setFont(new Font("Serif",Font.PLAIN,20));jp9.add(label,BoxLayout.Y_AXIS);
		jp2=new JPanel();jp5=new JPanel();jp5.setMinimumSize(new Dimension(200,180));jp5.setPreferredSize(new Dimension(200,300));
		addtextfield(jp9,"Enter the Year : ",2);
		addtextfield(jp5,"1st Author : ",1);
		addtextfield(jp5,"2nd Author : ",4);
		addtextfield(jp5,"3rd Author : ",3);
		addtextfield(jp5,"4th Author : ",5);
		addtextfield(jp5,"5th Author : ",6);
		addbuttonreset(new JPanel());}
	public void display() {
		if(query2_list.size()==0) {{new Error_Message("Invalid data"); return;}}
		for(int i=0;i<9;i++) for(int j = 0; j < 20; j++) table.setValueAt("", j, i);
		for(int i=counter;i<counter+20;i++) {try{table.setValueAt(query2_list.get(i),i-counter,1);table.setValueAt(i+1,i-counter,0);} catch(Exception ex){return;}}
		}
	public void displayquery() {
		if(results.size()==0) {new Error_Message("Invalid data"); return;}
		for(int i=0;i<9;i++) for(int j=0;j<20;j++) table.setValueAt("", j, i);
		for(int i=counter;i<counter+20;i++) {
			try{table.setValueAt(results.get(i).getAuthor(),i-counter,1);
				table.setValueAt(results.get(i).getTitle(),i-counter,2);
				table.setValueAt(results.get(i).getPages(),i-counter,3);
				table.setValueAt(results.get(i).getYear(),i-counter,4);
				table.setValueAt(results.get(i).getVolume(),i-counter,5);
				if(results.get(i).getJournal()!=null) table.setValueAt(results.get(i).getJournal(),i-counter,6);
				else table.setValueAt(results.get(i).getBooktitle(),i-counter,6);
				table.setValueAt(results.get(i).getUrl(),i-counter,7);
				table.setValueAt(i+1,i-counter,0);}
			catch(Exception e){return;}}
	}
	public void display_query3()
	{
 		for(int i=0;i<9;i++) for(int j=0;j<20;j++) table.setValueAt("", j, i);
		for(int i=0;i<5;i++)
		{
			try {
				table.setValueAt(pred.get(i),i,8);
				table.setValueAt(textresult[i+1], i,1);
				table.setValueAt(i+1, i,0);
			}catch (Exception e) {e.printStackTrace();}
			}
	}
	public void next()
	{
		if(query==1 && results.size()>(counter+20)) { counter+=20;displayquery();}
		else if(query==2 && query2_list.size()>(counter+20)) {counter+=20;display();}
	}
	public void previous() {
		if(counter>0) counter-=20;
		else {return;}
		if(query==1) displayquery();
		else if(query==2) display();}
	public String[] get_query3(){
		textresult[0]=txt1.getText();
		textresult[1]=txt2.getText();
		textresult[2]=txt3.getText();
		textresult[3]=txt4.getText();
		textresult[4]=txt5.getText();
		textresult[5]=txt6.getText();
		return textresult;
	}
	public void setQuery3(ArrayList<Integer> a){pred=a;}
	public int get_yearsincebetween(){return year;}
	public int get_authorandtag(){return authtag;}
	public int get_sort(){return sort;}
	public int get_query(){return query;}
	public String get_Authortag() {return textresult[1];}
	public int get_Since() {return Integer.parseInt(textresult[2]);}
	public int get_end() {return Integer.parseInt(textresult[3]);}
	public int publication() { textresult[0]=txt2.getText();return Integer.parseInt(textresult[0]);}
	public void set_querylist(ArrayList<Record> ar){results=ar;}
	public void set_list(ArrayList<String> ar){query2_list=ar;}
	public void clear_list() {counter=0;query2_list.clear();results.clear();textresult=new String[]{"","","","","",""};}
	public static class TableModel extends DefaultTableModel {
		public TableModel() {
			super(new Object[]{"S No.", "Authors", "Title","Pages","Year","Volume","Journal/Booktitle","Url","Prediction"}, 0);
			for (int index = 1; index <= 20; index++) addRow(new Object[]{index});
		}
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}
}