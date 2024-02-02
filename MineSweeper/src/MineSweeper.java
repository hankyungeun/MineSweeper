import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.*;
import javax.swing.Timer;

public class MineSweeper extends JFrame {								// 	JFrame ��� *�� Ŭ���� ��ü�� JFrame��� �����ϸ� �ɵ�?
	
	JPanel 		jp 			=	new JPanel();							//  �������� �����ϱ� ���� �г� ����
	JPanel 		jp2			=	new JPanel();							//  ���¹�ư�� �����ϱ� ���� �г� ����
	JPanel		jp3			=	new JPanel();
	JButton		timer		=	new JButton();
	JButton		counter		= 	new JButton();
	JButton 	reset		=	new JButton();							//  ���� ��ư ����
	Random 		rnd 		=	new Random();							//  ���� ��ġ�� ���� �����Լ� ����
	int[][] 	mine 		=	new int[10][10];						// 	�ʱ� ���̵����� ���ڸ� ���� 10x10 �迭 ����
	int[][] 	mine2 		=	new int[15][15];						// 	�߱� ���̵����� ���ڸ� ���� 15x15 �迭 ����
	int[][]	 	mine3 		=	new int[20][20];						// 	��� ���̵����� ���ڸ� ���� 20x20 �迭 ����						
	JButton 	jb[][] 		=	new JButton[10][10];					// 	�ʱ� ���̵��� ���� 10x10 ��ư ����
	JButton 	jb2[][] 	= 	new JButton[15][15];					// 	�߱� ���̵��� ���� 15x15 ��ư ����
	JButton     jb3[][]		= 	new JButton[20][20];					// 	��� ���̵��� ���� 20x20 ��ư ����
	JMenuBar	 mb 		=	new JMenuBar();							//	�޴��� ����
	JMenu 		game 		=	new JMenu("Game");						//	�޴��ٿ� '����'�̶�� �׸� ����
	JMenu		help		=	new JMenu("Help");						//	�޴��ٿ� 'help'����
	Color gray = new Color(195,199,203);
	Color flag = new Color(195,199,202);
	Color selected = new Color(195,198,203);
	int num_mine = 10;
	JTextField tf_mine, tf_time;
	boolean starttime = false;
	Stopwatch sw;
	
	ImageIcon icon0 = new ImageIcon("images/mine0.png");			//	���� ǥ�� �� �̹��� ����
	ImageIcon icon1 = new ImageIcon("images/mine1.png");			//	���� ǥ�� �� �̹��� ����
	ImageIcon icon2 = new ImageIcon("images/mine2.png");			//	���� ǥ�� �� �̹��� ����
	ImageIcon icon3 = new ImageIcon("images/mine3.png");			//	���� ǥ�� �� �̹��� ����
	ImageIcon icon4 = new ImageIcon("images/mine4.png");			//	���� ǥ�� �� �̹��� ����
	ImageIcon icon5 = new ImageIcon("images/mine5.png");			//	���� ǥ�� �� �̹��� ����
	ImageIcon icon6 = new ImageIcon("images/mine6.png");			//	���� ǥ�� �� �̹��� ����
	ImageIcon icon7 = new ImageIcon("images/mine7.png");			//	���� ǥ�� �� �̹��� ����
	ImageIcon icon8 = new ImageIcon("images/mine8.png");			//	���� ǥ�� �� �̹��� ����
	ImageIcon icon9 = new ImageIcon("images/mine9.png");			//	���� ǥ�� �� �̹��� ����
	ImageIcon icon_mine = new ImageIcon("images/mine.png");			//	���� ǥ�� �� �̹��� ����
	ImageIcon icon_reset = new ImageIcon("images/reset.png");		//  ���� ǥ�� �� �̹��� ����
	ImageIcon icon_flag = new ImageIcon("images/flag.png");			//  ��� ǥ�� �� �̹��� ����
	ImageIcon icon_flagx = new ImageIcon("images/flagx.png");		//  �߸��� ��ġ�� ��� ��ġ�� �˷��� �̹��� ����
	ImageIcon logo = new ImageIcon("images/logo.png");				//  ���� �ΰ� ���� �� �̹��� ����
	
	
public MineSweeper() {
	super("����ã��"); 																				// Ÿ��Ʋ ����
	setResizable(true);																				// ������ ������ ���������ϰ� ����
																									/* 
																									 * �ٸ� ���̵����� �ٸ� ���̵��� �Ѿ��
																									 * ������ ����� �����ϱ� ������
																									 * ������ ������ �ݵ�� �ٽ� �ݾ��ٰ�
																									 */
	this.setIconImage(logo.getImage());																// ����� ���� ��ܿ� ���� ������ ���� �̹����� ����
	sw = new Stopwatch();
	if(level == 0) level = 1;																		// �������� ������ 0(�ʱⰪ�� 0��)�� ��� 1�� ������.
	if(level == 1) {																				// ���� ���� 1(�ʱ޴ܰ�)�� ���
																									// �޴��� ���� ����
		game.add(new JMenuItem("���� �����ϱ�")).addActionListener(new ActionListener() { 				// �޴��ٿ��� �ٽ��ϱ� Ŭ����
			public void actionPerformed(ActionEvent e) { dispose();	new MineSweeper();	} });		// �͸����ʷ� ó��
		game.addSeparator(); 																		// �޴��ٿ� �ٱ߱�
		game.add(new JMenuItem("�ʱ�")).addActionListener(new ActionListener() { 						// �޴��ٿ��� �ʱ޸޴� ���ý� �͸����ʷ� ó��
			public void actionPerformed(ActionEvent e) { dispose();	new MineSweeper();	} });				// �������� �̹� 1�� ��Ȳ���� �ʱ��� ���ý� â�� �ݰ� �ٽ� ���⸸ ��
		game.add(new JMenuItem("�߱�")).addActionListener(new ActionListener() { 						// �޴��ٿ��� �߱޸޴� ���ý� �͸����ʷ� ó��
			public void actionPerformed(ActionEvent e) {level = 2;	dispose();	new MineSweeper();	} });	// �߱��� ���ý� �������� 2���� �������ְ� ���ý� â�� �ݰ� �ٽ� ���⸸ ��
		game.add(new JMenuItem("���")).addActionListener(new ActionListener() {						// �޴��ٿ��� ��޸޴� ���ý� �͸����ʷ� ó��
			public void actionPerformed(ActionEvent e) {level = 3;	dispose();	new MineSweeper();	} });	// ����� ���ý� �������� 3���� �������ְ� ���ý� â�� �ݰ� �ٽ� ���⸸ ��
		game.addSeparator(); 																		// �޴��ٿ� �ٱ߱�
		game.add(new JMenuItem("����")).addActionListener(new ActionListener() { 						// �޴��ٿ��� ����޴� ���ý�
			public void actionPerformed(ActionEvent e) {System.exit(0);} });						// ���������� �͸����ʷ� ó����.
	    																							/*
	    																							 * Ȯ���ϰ� �����Ҷ����� �ݵ��
	    																							 * �� ��ɾ� (System.exit(0);) �� ����� ��
	    																							 * */

		mb.add(game); 																				// �޴���(mb)�� �޴�(menu)�� �߰�
		mb.add(help);																				// �޴���(mb)�� �޴�(Help)�� �߰�
		this.setJMenuBar(mb);																		// ����(this) ������(JFrame)�� �޴��ٸ� �߰���
		this.setLayout(new BorderLayout());															// ���� �������� ���̾ƿ��� ����(BorderLayout)
		
		add(jp3, BorderLayout.NORTH);																//��ü Pannel jp3�߰�
		jp3.setBackground(gray);																	//jp3 �� ����
		jp3.setPreferredSize( new Dimension( 280, 340) );											//jp3 ������ ����
		
		jp3.add(jp2);																				//jp3�� jp2 �߰�
		jp2.setBackground(gray);																	//jp2�� ����
	    jp2.setBorder (new SoftBevelBorder(SoftBevelBorder.LOWERED));
		
	    //jp2.setLayout(new GridLayout(0,3,30,0));
		jp2.add(timer);																				// �гο� Ÿ�̸� �߰�
		jp2.add(reset); 																			// �гο� ���� ��ư �߰�
		jp2.add(counter);																			// �гο� ���� ī���� �߰�
		jp2.setPreferredSize( new Dimension( 255, 50 ) );
		//timer.setLayout(new FlowLayout(FlowLayout.RIGHT,20,30));
		timer.setPreferredSize(new Dimension( 70, 35 ));
		timer.setBorder (new SoftBevelBorder(SoftBevelBorder.LOWERED));
		counter.setPreferredSize(new Dimension( 70, 35 ));
		counter.setBorder (new SoftBevelBorder(SoftBevelBorder.LOWERED));
		reset.setPreferredSize( new Dimension( 35, 35 ) );
		reset.setBorder(new BevelBorder(BevelBorder.RAISED));
		reset.setIcon(icon_reset);																	// ���¹�ư ������ ����
		
		jp3.add(jp);																				// �������� �����ϱ� ���� �г��� �� �����ӿ� �߰�
		jp.setPreferredSize( new Dimension( 255, 255) );
		jp.setBorder (new BevelBorder(BevelBorder.LOWERED));
		jp.setBackground(gray);
		jp.setSize(20,20);
		jp.setLayout(new GridLayout(10,10,0,0));

		timer.setContentAreaFilled(false);
		counter.setContentAreaFilled(false);
		reset.setContentAreaFilled(false);															// ��ư�� ���ο��� ä��� �Ⱥ��̰� ����(�Ķ���)
		timer.setFocusPainted(false);
		counter.setFocusPainted(false);
		reset.setFocusPainted(false);																// ��ư�� ��Ŀ��(���콺�� �ö� ����)�� ����� �׵θ��� �Ⱥ��̰� ������
		reset.addActionListener(new ActionListener() { 												// ���¹�ư�� ��������
			public void actionPerformed(ActionEvent e) {											// �͸����ʷ� ó����. �ڵ尡 �ܼ��ϸ鼭 �׼Ǹ����ʰ� �ʿ��Ҷ��� �͸����ʷ� ó���ϴ°� ����
				JOptionPane.showMessageDialog(null, "'����'�Ǿ����ϴ�.");								// ���� ��ư Ŭ���� "'����'�Ǿ����ϴ�."��� �޽����� ��� �˾�â�� ���
				dispose();																			// �ش� �������� ����.
				new MineSweeper();																	// ���� �ҷ���()
			}
		});

		tf_mine = new JTextField("" + num_mine, 3);
        tf_mine.setEditable(false);
        tf_mine.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
        tf_mine.setBackground(Color.BLACK);
        tf_mine.setForeground(Color.RED);
        tf_mine.setBorder(BorderFactory.createLoweredBevelBorder());
        tf_time = new JTextField("000", 3);
        tf_time.setEditable(false);
        tf_time.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
        tf_time.setBackground(Color.BLACK);
        tf_time.setForeground(Color.RED);
        tf_time.setBorder(BorderFactory.createLoweredBevelBorder());
		counter.add(tf_mine);
        timer.add(tf_time);
		for(; ; ) {																					// ���� ��ġ�� ���� �ݺ���
			for(int i, j, count = 0; count < 10;) {													// i, j, count ���� �� �ʱ�ȭ
				i=rnd.nextInt(10);																	// i���� 0 ~ 9 ������ ���� ����
				j=rnd.nextInt(10);																	// j���� 0 ~ 9 ������ ���� ����
																									// --------------------------------------------------
				if(mine[i][j] != -1) {																// -mine[i][j] �迭 ���� -1�� �ƴϸ� (���ڰ� ��ġ���� �ʾ�����)	-
					mine[i][j] = -1;																// -mine[i][j] �迭�� -1�� ����							-
					count++;																		// -count���� ����										-
				}																					// -�ߺ����� �ɷ����� ������								-
			}																						// --------------------------------------------------
			break;																					// ����(count�� 10���� ������?)�� �������� ������ �ݺ��� ����
		}



		for(int i = 0; i < 10; i++) {																// ��ư ������ ���� �ݺ���
			for(int j = 0; j < 10; j++) {															// 
				if(mine[i][j] == 0) { 																// mine�迭 i,j�� �迭 ���� 0�� ���
					jb[i][j] = new JButton();														// jb(JButton)[i][j]�� ����
					jb[i][j].setPreferredSize(new Dimension(20,20));								// jb[i][j]�� ��ư ����� ����
					jb[i][j].addActionListener(new ButtonListener());								// �Ϲݹ�ư Ŭ���� �����ϱ� ���� �׼Ǹ�����(ButtonListener) ����
					jb[i][j].addMouseListener(new clickListener());									// ����� ǥ���ϱ� ���� ��Ŭ���� üũ�� ���콺 ������(clickListener)����
					jb[i][j].setBorder(new BevelBorder(BevelBorder.RAISED));
					jb[i][j].setBackground(gray);
					jp.add(jb[i][j]);																// jp(JPanel)�� jb[i][j]�� �߰�
				}
				else if(mine[i][j] != 0) { 															// mine�迭 i,j�� �迭 ���� 0�� �ƴҰ��
					jb[i][j] = new JButton();														// jb(JButton)[i][j]�� ����
					jb[i][j].setPreferredSize(new Dimension(20, 20));								// jb[i][j]�� ��ư ����� ����
					jb[i][j].addActionListener(new MineListener());									// ���ڹ�ư Ŭ���� �����ϱ� ���� �׼Ǹ�����(MineListener) ����
					jb[i][j].addMouseListener(new clickListener());									// ����� ǥ���ϱ� ���� ��Ŭ���� üũ�� ���콺 ������(clickListener)����
					jb[i][j].setBorder(new BevelBorder(BevelBorder.RAISED));
					jb[i][j].setBackground(gray);
					jp.add(jb[i][j]);																// jp(JPanel)�� jb[i][j]�� �߰�
				}
			}
		}

		for(int i=0; i<10; i++) {																	// ���� �ֺ� ĭ�� ���ڸ� ä��� ���� �ݺ���
			for(int j=0; j<10; j++) {
				int count=0;																		// ���ڸ� üũ�ϱ� ���� ���� ����
				if(mine[i][j] != -1) {																// mine[i][j] ��ġ�� �迭���� ���ڰ���(-1) �ƴϸ�
					if(i >= 0 && j >= 0 && i <= 9 && j <= 9) {										// �迭�� ������ ����� �������� ����
						if(i-1 >= 0 && j-1 >= 0)				// -------------------------------- //
							if(mine[i-1][j-1] == -1) count ++;	//				1		2		3--	//
						if(i-1 >= 0)							//				0		0		0--	// 
							if(mine[i-1][j] == -1) count ++;	//								 -- // 
						if(i-1 >= 0 && j+1 <= 9)				//				4		i,j		5-- // 1~8�� �ڸ��� ���ڰ� �ִ��� üũ�ϰ�
							if(mine[i-1][j+1] == -1) count ++;	//				0		0		0-- // ���ڰ� ������ count�� ����
						if(j-1 >= 0)							//								 -- // 
							if(mine[i][j-1] == -1) count ++;	//				6		7		8-- // 
						if(j+1 <= 9)							//				0		0		0-- // 
							if(mine[i][j+1] == -1) count ++;	// -------------------------------- // 
						if(i+1 <= 9 && j-1 >= 0)
							if(mine[i+1][j-1] == -1) count ++;
						if(i+1 <= 9)
							if(mine[i+1][j] == -1) count ++;
						if(i+1 <= 9 && j+1 <= 9)
							if(mine[i+1][j+1] == -1) count ++;
					}											
					mine[i][j] = count;																// üũ�� ������ mine[i][j]�� count�� ����
				}
				count = 0;																			// �ݺ��ϱ����� count�� �ʱ�ȭ
			}
		}
		ending = false;																				// ���� ���� �������� �ٲ�
		setVisible(true); 																			// ������ ���̱�
		setSize(280, 385); 																			// ������ ������ ���� ������
		setResizable(false);																		// ������ ����� ������ �������� ���ϰ� ���
		this.setLocationRelativeTo(null);															// ȭ�� �߾ӿ� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);												// X��ư ������ �ݱ�
																									// ���� ���ϸ� ���μ�����(��׶��忡) ��������
	}
	

																									/*
																									 * �Ʒ� �߱޺��� ��޴ܰ������
																									 * �ʱ� ���� level���� �񱳿�
																									 * ��ư�� ���ڹ迭�� �������
																									 * �⺻���� �ٸ���
																									 * ��� ������ ����
																									 */
	if(level == 2) {
		setResizable(true);
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("����");

		menu.add(new JMenuItem("���� �����ϱ�")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MineSweeper();
			}
		});
		menu.addSeparator();
		menu.add(new JMenuItem("�ʱ�")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = 1;
				dispose();
				new MineSweeper();
			}
		});
		menu.add(new JMenuItem("�߱�")).addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MineSweeper();
			}
		});
		menu.add(new JMenuItem("���")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = 3;
				dispose();
				new MineSweeper();
			}
		});
	
		menu.addSeparator();
	
		menu.add(new JMenuItem("����")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mb.add(menu);
	
		this.setJMenuBar(mb);
	
		this.setLayout(new BorderLayout());
		add(jp);
		add(jp2, "North");
		jp2.add(reset);
		
		reset.setIcon(icon_reset);
		reset.setBorderPainted(false);
		reset.setContentAreaFilled(false);
		reset.setFocusPainted(false);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "'����'�Ǿ����ϴ�.");
				dispose();
				new MineSweeper();
			}
		});

		for(; ; ) {
			for(int i, j, count = 0; count < 30;) {
				i=rnd.nextInt(15);
				j=rnd.nextInt(15);
				if(mine2[i][j] != -1) {
					mine2[i][j] = -1;
					count++;
				}
			}
			break;
		}
	
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
			if(mine2[i][j] == 0) {
				jb2[i][j] = new JButton();
				jb2[i][j].setPreferredSize(new Dimension(20,20));
				jb2[i][j].addActionListener(new ButtonListener());
				jb2[i][j].addMouseListener(new clickListener());
				jb2[i][j].setBackground(gray);
    	
				jp.add(jb2[i][j]);
			}
			else if(mine2[i][j] != 0) {
				jb2[i][j] = new JButton();
				jb2[i][j].setPreferredSize(new Dimension(20,20));
				jb2[i][j].addActionListener(new MineListener());
				jb2[i][j].addMouseListener(new clickListener());
				jb2[i][j].setBackground(gray);
				jp.add(jb2[i][j]);
			}
			}
		}
		for(int i=0; i<15; i++) {
			for(int j=0; j<15; j++) {
				int count=0;
				if(mine2[i][j] != -1) {
					if(i >= 0 && j >= 0 && i <= 14 && j <= 14) {
						if(i-1 >= 0 && j-1 >= 0)
							if(mine2[i-1][j-1] == -1) count ++;
						if(i-1 >= 0)
							if(mine2[i-1][j] == -1) count ++;
						if(i-1 >= 0 && j+1 <= 14)
							if(mine2[i-1][j+1] == -1) count ++;
						if(j-1 >= 0)
							if(mine2[i][j-1] == -1) count ++;
						if(j+1 <= 14)
							if(mine2[i][j+1] == -1) count ++;
						if(i+1 <= 14 && j-1 >= 0)
							if(mine2[i+1][j-1] == -1) count ++;
						if(i+1 <= 14)
							if(mine2[i+1][j] == -1) count ++;
						if(i+1 <= 14 && j+1 <= 14)
							if(mine2[i+1][j+1] == -1) count ++;
					}
					mine2[i][j] = count;
				}
				count = 0;
			}
		}
		
		ending = false;
		setVisible(true);
		setSize(400, 490);
		setResizable(false);
		this.setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
																									/*
																									 * ���⼭���� 
																									 * ���� 3
																									 */
	
	if(level == 3) {
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("����");

		menu.add(new JMenuItem("���� �����ϱ�")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					dispose();
					new MineSweeper();
			}
		});
		menu.addSeparator();
		menu.add(new JMenuItem("�ʱ�")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = 1;
				dispose();
				new MineSweeper();
			}
		});
		menu.add(new JMenuItem("�߱�")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = 2;
				dispose();
				new MineSweeper();
			}
		});
		menu.add(new JMenuItem("���")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MineSweeper();
			}
		});
	
		menu.addSeparator();
	
		menu.add(new JMenuItem("����")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mb.add(menu);
		
		
		this.setJMenuBar(mb);
	
		this.setLayout(new BorderLayout());
		add(jp);
		add(jp2, "North");
		jp2.add(reset);
		reset.setIcon(icon_reset);
		reset.setBorderPainted(false);
		reset.setContentAreaFilled(false);
		reset.setFocusPainted(false);
		
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "'����'�Ǿ����ϴ�.");
					dispose();
					new MineSweeper();
			}
		});
		

		for(; ; ) {
			for(int i, j, count = 0; count < 40;) {
				i=rnd.nextInt(20);
				j=rnd.nextInt(20);
				if(mine3[i][j] != -1) {
					mine3[i][j] = -1;
					count++;
				}
			}
			break;
		}
	
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 20; j++) {
			if(mine3[i][j] == 0) {
				jb3[i][j] = new JButton();
				jb3[i][j].setPreferredSize(new Dimension(20,20));
				jb3[i][j].addActionListener(new ButtonListener());
				jb3[i][j].addMouseListener(new clickListener());
				jb3[i][j].setBackground(gray);
				jp.add(jb3[i][j]);
			}
			else if(mine3[i][j] != 0) {
				jb3[i][j] = new JButton();
				jb3[i][j].setPreferredSize(new Dimension(20,20));
				jb3[i][j].addActionListener(new MineListener());
				jb3[i][j].addMouseListener(new clickListener());
				jb3[i][j].setBackground(gray);
				jp.add(jb3[i][j]);
			}
		}
	}
		for(int i=0; i<20; i++) {
			for(int j=0; j<20; j++) {
				int count=0;
				if(mine3[i][j] != -1) {
					if(i >= 0 && j >= 0 && i <= 19 && j <= 19) {
						if(i-1 >= 0 && j-1 >= 0)
							if(mine3[i-1][j-1] == -1) count ++;
						if(i-1 >= 0)
							if(mine3[i-1][j] == -1) count ++;
						if(i-1 >= 0 && j+1 <= 19)
							if(mine3[i-1][j+1] == -1) count ++;
						if(j-1 >= 0)
							if(mine3[i][j-1] == -1) count ++;
						if(j+1 <= 19)
							if(mine3[i][j+1] == -1) count ++;
						if(i+1 <= 19 && j-1 >= 0)
							if(mine3[i+1][j-1] == -1) count ++;
						if(i+1 <= 19)
							if(mine3[i+1][j] == -1) count ++;
						if(i+1 <= 19 && j+1 <= 19)
							if(mine3[i+1][j+1] == -1) count ++;
					}
					mine3[i][j] = count;
				}
				count = 0;
			}
		}
		
		ending = false;
		setVisible(true);
		setSize(520, 615);
		setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

private class ButtonListener implements ActionListener  { 											// ���ڰ� �ƴ� ��ư Ŭ�������� ����Ǵ� �׼Ǹ�����
	public void actionPerformed(ActionEvent e) {													
		if(level == 1) {																			// level���� 1�϶�
			for(int i = 0; i < 10; i++) {															// �ش� ��ư�� �迭���� ã�� ���� �ݺ���
				for(int j = 0; j < 10; j++) {
					if(e.getSource() == jb[i][j] && jb[i][j].getBackground() == gray) {				// �Ѿ�� e ���� jb[i][j]�� ���� ���� ��׶����÷��� DARK_GRAY��
						check(i, j);																// check�� (i,j)���� �Ѱ���
						if (starttime == false) {
			                sw.Start();
			                starttime = true;
			            }
					}
				}
			}
		}
																									/*
																									 * level���� 2�ΰ���
																									 * level���� 3�ΰ�쵵
																									 * �ݺ����� Ƚ���� �ٸ���
																									 * �Ȱ���
																									 */
		if(level == 2) {
			for(int i = 0; i < 15; i++) {
				for(int j = 0; j < 15; j++) {
					if(e.getSource() == jb2[i][j]&& jb2[i][j].getBackground() == gray) {
						check(i, j);
					}
				}
			}
		}
		if(level == 3) {
			for(int i = 0; i < 20; i++) {
				for(int j = 0; j < 20; j++) {
					if(e.getSource() == jb3[i][j]&& jb3[i][j].getBackground() == gray) {
						check(i, j);
					}
				}
			}
		}
	}
}

private class MineListener implements ActionListener  { 												// ������ ��ư�� Ŭ�������� ����Ǵ� �׼Ǹ�����
	public void actionPerformed(ActionEvent e) {
		if(ending == false) /*JOptionPane.showMessageDialog(null, "�ƽ����ϴ�. ���ڸ� Ŭ���߽��ϴ�.");*/{				// �������� false�� ��� "�ƽ����ϴ�. ���ڸ� Ŭ���߽��ϴ�."��� �޽����� ��� �˾�â�� ���
		
		if(level == 1) {																				// ���ڷ� ������ ��� ��ư�� ��ġ�� ��Ÿ���� ���� �ݺ���
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) {
					if(mine[i][j] == -1 && jb[i][j].getBackground() != flag) {																// �ش� i, j�� ��ġ�� �ִ� ��ư�� ������ ���
							jb[i][j].setBackground(gray);												// ��׶����÷��� �⺻���� �ٲ�
							jb[i][j].setIcon(icon_mine);												// �����ܿ� ���� �̹����� ����
							if (starttime == true) {
				                sw.stop();
				            }
							if (starttime == false) {
				                sw.Start();
				                starttime = true;
				            }
							
							ending=true;																// �������� ending�� true�� �ٲ� || ������ ���� �� �ٸ� ���� �� ��ư �������� ���� ���� �ϱ� ������
						}
						else if (mine[i][j] != -1 && jb[i][j].getBackground() == flag) {				// �ش� i, j�� ��ġ�� �ִ� ��ư�� ���ڰ� �ƴϸ鼭 �⺻���� �ƴҰ�� (����� �������)
							jb[i][j].setBorderPainted(false);											// BorderPainted ���� false�� �ٲ�
							jb[i][j].setBackground(gray);												// ��׶����÷��� �⺻���� �ٲ�
							jb[i][j].setIcon(icon_flagx);												// ��߿� x ǥ�õ� �������� ����
						}
						else
							jb[i][j].setBorderPainted(false);											// �� ���� ���(���ڰ� �ƴϸ鼭 ��ߵ� �Ȳ������)
						
					
					jb[i][j].setBorderPainted(false);													// ���ڹ�ư�� �ƴ� ��� ��ư�� Ŭ���ص� ������ ���� �����ϱ� ���� false���� ����
					jb[i][j].setBackground(gray);														// ��׶����÷��� �⺻���� ����
				}
			}
		}																								// �Ʒ� ���� ��� ����
		if(level == 2) {
			for(int i = 0; i < 15; i++) {
				for(int j = 0; j < 15; j++) {
					if(mine2[i][j] == -1) {
						jb2[i][j].setIcon(icon_mine);
						jb2[i][j].setBorderPainted(false);
						ending=true;
					}
					else if (mine2[i][j] != -1 && jb2[i][j].getBackground() != gray) {
						jb2[i][j].setBorderPainted(false);
						jb2[i][j].setBackground(gray);
						jb2[i][j].setIcon(icon_flagx);
					}
					else
						jb2[i][j].setBorderPainted(false);
					
					jb2[i][j].setBorderPainted(false);
					jb2[i][j].setBackground(gray);
				}
			}
		}	
		if(level == 3) {
			for(int i = 0; i < 20; i++) {
				for(int j = 0; j < 20; j++) {
					if(mine3[i][j] == -1){
						jb3[i][j].setIcon(icon_mine);
						jb3[i][j].setBorderPainted(false);
						ending=true;
					}
					else if (mine3[i][j] != -1 && jb3[i][j].getBackground() != gray) {
						jb3[i][j].setBorderPainted(false);
						jb3[i][j].setBackground(gray);
						jb3[i][j].setIcon(icon_flagx);
					}
					else
						jb3[i][j].setBorderPainted(false);
				}
			}
		}
	}
	}
}


private class clickListener implements MouseListener{ 																						// ����� ����� ���� ���콺 ������
	
	/*
	 * ���콺�����ʸ� ����Ҷ� �⺻������ �����Ǵ� �Լ�����
	 * ������� �ʴ��� �־����.
	 */
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		if(level == 1) {
			if(e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {																		// Ŭ���� ��ư�� ���콺 ��Ŭ������ ��
				for(int i = 0; i<10; i++) {																									// ��Ŭ���� ��ư�� ã������ �񱳹�
					for(int j = 0; j<10; j++) {
						if(e.getSource() == jb[i][j] && jb[i][j].isBorderPainted()==true && jb[i][j].getBackground() == gray) {	// ��Ŭ���� ��ư�� �⺻ ���¸�
							jb[i][j].setIcon(icon_flag);																					// ����� ����
							jb[i][j].setBackground(flag);																				// ����� Ȯ���ϱ� ���� ��׶��� �÷��� ����
							num_mine--;
							tf_mine.setText("" + num_mine);
							if (starttime == false) {
				                sw.Start();
				                starttime = true;
				            }
						}
						else if(e.getSource() == jb[i][j] && jb[i][j].isBorderPainted()==true && jb[i][j].getBackground() == flag) {	// ����� ���� ��ư�̸�
							jb[i][j].setIcon(null);																							// �ش� ��ư�� �������� �����
							jb[i][j].setBackground(gray);																		// �⺻������� ����
							num_mine++;
							tf_mine.setText("" + num_mine);
						}																													// ���� ����
					}
				}
			}
		}
		if(level == 2) {
			if(e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {
				for(int i = 0; i<15; i++) {
					for(int j = 0; j<15; j++) {
						if(e.getSource() == jb2[i][j] && jb2[i][j].isBorderPainted()==true && jb2[i][j].getBackground() == gray) {
							jb2[i][j].setIcon(icon_flag);
							jb2[i][j].setBackground(flag);
						}
						else if(e.getSource() == jb2[i][j] && jb2[i][j].isBorderPainted()==true && jb2[i][j].getBackground() == flag) {
							jb2[i][j].setIcon(null);
							jb2[i][j].setBackground(gray);
						}
					}
				}
			}
		}
		if(level == 3) {
			if(e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {
				for(int i = 0; i<20; i++) {
					for(int j = 0; j<20; j++) {
						if(e.getSource() == jb3[i][j] && jb3[i][j].isBorderPainted()==true && jb3[i][j].getBackground() == gray) {
							jb3[i][j].setIcon(icon_flag);
							jb3[i][j].setBackground(flag);
						}
						else if(e.getSource() == jb3[i][j] && jb3[i][j].isBorderPainted()==true && jb3[i][j].getBackground() == flag) {
							jb3[i][j].setIcon(null);
							jb3[i][j].setBackground(gray);
						}
					}
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

private void check(int i, int j) {																	// ���ϱ� ���� �Լ�
	if(level == 1) {																				// level���� 1�ϰ��
		try {																						// �迭�� ������ ����� ó���ϱ� ���� try~catch��
			if(mine[i][j] == 0 && jb[i][j].isBorderPainted() == true) {								// �ش� mine[i][j]���� 0�� ���� �����鼭 ��ư�� Ȱ��ȭ�� ���¿� �������
				jb[i][j].setBorderPainted(false);													// ��ư�� �ܰ����� �Ⱥ��̰� ����
				jb[i][j].setContentAreaFilled(false);												// ��ư�� ���ο��� ä��� �Ⱥ��̰� ����(�Ķ���)
				jb[i][j].setBorder (new LineBorder (Color.GRAY));
				jb[i][j].setFocusPainted(false);													// ��ư�� ��Ŀ��(���콺�� �ö� ����)�� ����� �׵θ��� �Ⱥ��̰� ������
				check(i+1, j);																		// �ֺ��� 0�� �ִ��� Ȯ���ϱ� ����
				check(i-1, j);
				check(i, j+1);
				check(i, j-1);
				check(i+1, j+1);
				check(i-1, j-1);
				check(i-1, j+1);
				check(i+1, j-1);
			}
			
			else {																					// �׿��� ���
				if(jb[i][j].isBorderPainted() == true) {											// �ش� mine[i][j]�� ���� 0�� �ƴѰ����
																									// �� �Լ��� �ҷ����� ��� ��ü�� ���ڰ� �ƴѰ���̱� ������
																									// ������ ��츦 üũ�� �ʿ�� ����
					switch(mine[i][j]) {															// ����ġ ���̽���
					case 1:																			// 1~9�� ��� �� ���ڿ� �´� �̹����� ������
						jb[i][j].setIcon(icon1);
						jb[i][j].setBackground(selected);
						jb[i][j].setContentAreaFilled(false);
						jb[i][j].setBorder (new LineBorder (Color.GRAY));
						jb[i][j].setFocusPainted(false);
						break;
					case 2:
						jb[i][j].setIcon(icon2);
						jb[i][j].setBackground(selected);
						jb[i][j].setContentAreaFilled(false);
						jb[i][j].setBorder (new LineBorder (Color.GRAY));
						jb[i][j].setFocusPainted(false);
						break;
					case 3:
						jb[i][j].setIcon(icon3);
						jb[i][j].setBackground(selected);
						jb[i][j].setContentAreaFilled(false);
						jb[i][j].setBorder (new LineBorder (Color.GRAY));
						jb[i][j].setFocusPainted(false);
						break;
					case 4:
						jb[i][j].setIcon(icon4);
						jb[i][j].setBackground(selected);
						jb[i][j].setContentAreaFilled(false);
						jb[i][j].setFocusPainted(false);
						break;
					case 5:
						jb[i][j].setIcon(icon5);
						jb[i][j].setBackground(selected);
						jb[i][j].setContentAreaFilled(false);
						jb[i][j].setBorder (new LineBorder (Color.GRAY));
						jb[i][j].setFocusPainted(false);
						break;
					case 6:
						jb[i][j].setIcon(icon6);
						jb[i][j].setBackground(selected);
						jb[i][j].setBorderPainted(false);
						jb[i][j].setContentAreaFilled(false);
						jb[i][j].setBorder (new LineBorder (Color.GRAY));
						jb[i][j].setFocusPainted(false);
						break;
					case 7:
						jb[i][j].setIcon(icon7);
						jb[i][j].setBackground(selected);
						jb[i][j].setBorderPainted(false);
						jb[i][j].setContentAreaFilled(false);
						jb[i][j].setBorder (new LineBorder (Color.GRAY));
						jb[i][j].setFocusPainted(false);
						break;
					case 8:
						jb[i][j].setIcon(icon8);
						jb[i][j].setBackground(selected);
						jb[i][j].setBorderPainted(false);
						jb[i][j].setContentAreaFilled(false);
						jb[i][j].setBorder (new LineBorder (Color.GRAY));
						jb[i][j].setFocusPainted(false);
						break;
					case 9:
						jb[i][j].setIcon(icon9);
						jb[i][j].setBackground(selected);
						jb[i][j].setBorderPainted(false);
						jb[i][j].setContentAreaFilled(false);
						jb[i][j].setBorder (new LineBorder (Color.GRAY));
						jb[i][j].setFocusPainted(false);
						break;
					}
				}
			}
			victory();																				// �¸� ������ Ȯ���ϱ� ���� ȣ����
		}
		catch(java.lang.ArrayIndexOutOfBoundsException e){
			
		}
	}
	if(level == 2) {																				// ���� �Ȱ���
		try {
			if(mine2[i][j] == 0 && jb2[i][j].isBorderPainted() == true) {
				jb2[i][j].setIcon(icon0);
				jb2[i][j].setBorderPainted(false);
				jb2[i][j].setContentAreaFilled(false);
				jb2[i][j].setFocusPainted(false);
				check(i+1, j);
				check(i-1, j);
				check(i, j+1);
				check(i, j-1);
				check(i+1, j+1);
				check(i-1, j-1);
				check(i-1, j+1);
				check(i+1, j-1);
			}
			else {
				if(jb2[i][j].isBorderPainted() == true) {
					switch(mine2[i][j]) {															
					case 1:																		
						jb2[i][j].setIcon(icon1);
						jb2[i][j].setBorderPainted(false);
						jb2[i][j].setContentAreaFilled(false);
						jb2[i][j].setFocusPainted(false);
						break;
					case 2:
						jb2[i][j].setIcon(icon2);
						jb2[i][j].setBorderPainted(false);
						jb2[i][j].setContentAreaFilled(false);
						jb2[i][j].setFocusPainted(false);
						break;
					case 3:
						jb2[i][j].setIcon(icon3);
						jb2[i][j].setBorderPainted(false);
						jb2[i][j].setContentAreaFilled(false);
						jb2[i][j].setFocusPainted(false);
						break;
					case 4:
						jb2[i][j].setIcon(icon4);
						jb2[i][j].setBorderPainted(false);
						jb2[i][j].setContentAreaFilled(false);
						jb2[i][j].setFocusPainted(false);
						break;
					case 5:
						jb2[i][j].setIcon(icon5);
						jb2[i][j].setBorderPainted(false);
						jb2[i][j].setContentAreaFilled(false);
						jb2[i][j].setFocusPainted(false);
						break;
					case 6:
						jb2[i][j].setIcon(icon6);
						jb2[i][j].setBorderPainted(false);
						jb2[i][j].setContentAreaFilled(false);
						jb2[i][j].setFocusPainted(false);
						break;
					case 7:
						jb2[i][j].setIcon(icon7);
						jb2[i][j].setBorderPainted(false);
						jb2[i][j].setContentAreaFilled(false);
						jb2[i][j].setFocusPainted(false);
						break;
					case 8:
						jb2[i][j].setIcon(icon8);
						jb2[i][j].setBorderPainted(false);
						jb2[i][j].setContentAreaFilled(false);
						jb2[i][j].setFocusPainted(false);
						break;
					case 9:
						jb2[i][j].setIcon(icon9);
						jb2[i][j].setBorderPainted(false);
						jb2[i][j].setContentAreaFilled(false);
						jb2[i][j].setFocusPainted(false);
						break;
				}
				}
			}
			victory();
		}
		catch(java.lang.ArrayIndexOutOfBoundsException e){
			
		}
	}
	if(level == 3) {
		try {
			if(mine3[i][j] == 0 && jb3[i][j].isBorderPainted() == true) {
				jb3[i][j].setIcon(icon0);
				jb3[i][j].setBorderPainted(false);
				jb3[i][j].setContentAreaFilled(false);
				jb3[i][j].setFocusPainted(false);
				check(i+1, j);
				check(i-1, j);
				check(i, j+1);
				check(i, j-1);
				check(i+1, j+1);
				check(i-1, j-1);
				check(i-1, j+1);
				check(i+1, j-1);
			}
			else {
				if(jb3[i][j].isBorderPainted() == true) {
					switch(mine3[i][j]) {
					case 1:
						jb3[i][j].setIcon(icon1);
						jb3[i][j].setBorderPainted(false);
						jb3[i][j].setContentAreaFilled(false);
						jb3[i][j].setFocusPainted(false);
						break;
					case 2:
						jb3[i][j].setIcon(icon2);
						jb3[i][j].setBorderPainted(false);
						jb3[i][j].setContentAreaFilled(false);
						jb3[i][j].setFocusPainted(false);
						break;
					case 3:
						jb3[i][j].setIcon(icon3);
						jb3[i][j].setBorderPainted(false);
						jb3[i][j].setContentAreaFilled(false);
						jb3[i][j].setFocusPainted(false);
						break;
					case 4:
						jb3[i][j].setIcon(icon4);
						jb3[i][j].setBorderPainted(false);
						jb3[i][j].setContentAreaFilled(false);
						jb3[i][j].setFocusPainted(false);
						break;
					case 5:
						jb3[i][j].setIcon(icon5);
						jb3[i][j].setBorderPainted(false);
						jb3[i][j].setContentAreaFilled(false);
						jb3[i][j].setFocusPainted(false);
						break;
					case 6:
						jb3[i][j].setIcon(icon6);
						jb3[i][j].setBorderPainted(false);
						jb3[i][j].setContentAreaFilled(false);
						jb3[i][j].setFocusPainted(false);
						break;
					case 7:
						jb3[i][j].setIcon(icon7);
						jb3[i][j].setBorderPainted(false);
						jb3[i][j].setContentAreaFilled(false);
						jb3[i][j].setFocusPainted(false);
						break;
					case 8:
						jb3[i][j].setIcon(icon8);
						jb3[i][j].setBorderPainted(false);
						jb3[i][j].setContentAreaFilled(false);
						jb3[i][j].setFocusPainted(false);
						break;
					case 9:
						jb3[i][j].setIcon(icon9);
						jb3[i][j].setBorderPainted(false);
						jb3[i][j].setContentAreaFilled(false);
						jb3[i][j].setFocusPainted(false);
						break;
					}
				}
			}
			victory();
		}
		catch(java.lang.ArrayIndexOutOfBoundsException e){
			
		}
	}
}


private void victory() {																			// �¸� ������ Ȯ��
	if(level == 1) {
		int count = 0;
		for(int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
				if(this.mine[i][j] != -1 && this.jb[i][j].isBorderPainted() == false) {				// �ش� i,j�� ���ڰ� �ƴϸ鼭 BorderPainted�� false�� ���
					count++;																		// �� ���ڰ� �ƴϸ鼭 Ŭ���� ��츦 ����
				}
			}
		}
		if(count >= 90) {																			// ���ڰ� �ƴϸ鼭 Ŭ���� ��찡 90�̻��� ���
			for(int i=0; i<10; i++) {
				for(int j=0; j<10; j++) {
					if(jb[i][j].isBorderPainted() == true) {										// BorderPainted�� true�� ��찡 �ִٸ�
						jb[i][j].setBorderPainted(false);											// false�� ����
						if(mine[i][j] == -1)														// ���ڸ�
							jb[i][j].setIcon(icon_mine);											// ���� �̹����� ������ ����
					}
				}
			}
			if(ending==false) {																		// �������� ������ ���
				JOptionPane.showMessageDialog(null, "�����մϴ�. �¸��߽��ϴ�!");							// �¸� �޽��� ���
				ending = true;																		// �������� ������ ����
			}
		}
	}																								// ���� �Ȱ���
	if(level == 2) {
		int count = 0;
		for(int i=0; i<15; i++) {
			for(int j=0; j<15; j++) {
				if(this.mine2[i][j] != -1 && this.jb2[i][j].isBorderPainted() == false) {
					count++;
				}
			}
		}
		if(count >= 195) {
			for(int i=0; i<15; i++) {
				for(int j=0; j<15; j++) {
					if(jb2[i][j].isBorderPainted() == true) {
						jb2[i][j].setBorderPainted(false);
						if(mine2[i][j] == -1)
							jb2[i][j].setIcon(icon_mine);
					}
				}
			}
			if(ending==false) {
				JOptionPane.showMessageDialog(null, "�����մϴ�. �¸��߽��ϴ�!");
				ending = true;
			}
		}
	}
	if(level == 3) {
		int count = 0;
		for(int i=0; i<20; i++) {
			for(int j=0; j<20; j++) {
				if(this.mine3[i][j] != -1 && this.jb3[i][j].isBorderPainted() == false) {
					count++;
				}
			}
		}
		if(count >= 360) {
			for(int i=0; i<20; i++) {
				for(int j=0; j<20; j++) {
					if(jb3[i][j].isBorderPainted() == true) {
						jb3[i][j].setBorderPainted(false);
						if(mine3[i][j] == -1)
							jb3[i][j].setIcon(icon_mine);
					}
				}
			}
			if(ending==false) {
				JOptionPane.showMessageDialog(null, "�����մϴ�. �¸��߽��ϴ�!");
				ending = true;
			}
		}
	}
}

public class Stopwatch extends JFrame implements Runnable {
	 
    long startTime;
    //final static java.text.SimpleDateFormat timerFormat = new java.text.SimpleDateFormat("mm : ss :SSS");
    //final JButton startStopButton= new JButton("Start/stop");
    Thread updater;
    boolean isRunning = false;
    long a = 1;
    Runnable displayUpdater = new Runnable() {

        public void run() {
            displayElapsedTime(a);
            a++;
        }
    };

    public void stop() {
        long elapsed = a;
        isRunning = false;
        try {
            updater.join();
        } catch (InterruptedException ie) {
        }
        displayElapsedTime(elapsed);
        //a = 0;
    }

    private void displayElapsedTime(long elapsedTime) {

        if (elapsedTime >= 0 && elapsedTime < 9) {
            tf_time.setText("00" + elapsedTime);
        } else if (elapsedTime > 9 && elapsedTime < 99) {
            tf_time.setText("0" + elapsedTime);
        } else if (elapsedTime > 99 && elapsedTime < 999) {
            tf_time.setText("" + elapsedTime);
        }
    }

    public void run() {
        try {
            while (isRunning) {
                SwingUtilities.invokeAndWait(displayUpdater);
                Thread.sleep(1000);
            }
        } catch (java.lang.reflect.InvocationTargetException ite) {
            ite.printStackTrace(System.err);
        } catch (InterruptedException ie) {
        }
    }

    public void Start() {
        startTime = System.currentTimeMillis();
        isRunning = true;
        updater = new Thread(this);
        updater.start();
    }
}


public static void main(String[] args) {
	// TODO Auto-generated method stub
	new MineSweeper();																						// ���α׷� ����� ������ �ҷ�����
	}static int level = 0; static boolean ending = false;													// �������� level ���� �� 0���� �ʱ�ȭ, ending ���� �� false�� �ʱ�ȭ
}