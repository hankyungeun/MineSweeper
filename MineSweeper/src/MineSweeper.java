import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.*;
import javax.swing.Timer;

public class MineSweeper extends JFrame {								// 	JFrame 상속 *이 클래스 자체가 JFrame라고 생각하면 될듯?
	
	JPanel 		jp 			=	new JPanel();							//  지뢰판을 설정하기 위한 패널 생성
	JPanel 		jp2			=	new JPanel();							//  리셋버튼을 설정하기 위한 패널 생성
	JPanel		jp3			=	new JPanel();
	JButton		timer		=	new JButton();
	JButton		counter		= 	new JButton();
	JButton 	reset		=	new JButton();							//  리셋 버튼 생성
	Random 		rnd 		=	new Random();							//  지뢰 배치를 위한 랜덤함수 생성
	int[][] 	mine 		=	new int[10][10];						// 	초급 난이도에서 지뢰를 담을 10x10 배열 생성
	int[][] 	mine2 		=	new int[15][15];						// 	중급 난이도에서 지뢰를 담을 15x15 배열 생성
	int[][]	 	mine3 		=	new int[20][20];						// 	고급 난이도에서 지뢰를 담을 20x20 배열 생성						
	JButton 	jb[][] 		=	new JButton[10][10];					// 	초급 난이도에 쓰일 10x10 버튼 생성
	JButton 	jb2[][] 	= 	new JButton[15][15];					// 	중급 난이도에 쓰일 15x15 버튼 생성
	JButton     jb3[][]		= 	new JButton[20][20];					// 	고급 난이도에 쓰일 20x20 버튼 생성
	JMenuBar	 mb 		=	new JMenuBar();							//	메뉴바 생성
	JMenu 		game 		=	new JMenu("Game");						//	메뉴바에 '파일'이라는 항목 생성
	JMenu		help		=	new JMenu("Help");						//	메뉴바에 'help'생성
	Color gray = new Color(195,199,203);
	Color flag = new Color(195,199,202);
	Color selected = new Color(195,198,203);
	int num_mine = 10;
	JTextField tf_mine, tf_time;
	boolean starttime = false;
	Stopwatch sw;
	
	ImageIcon icon0 = new ImageIcon("images/mine0.png");			//	숫자 표시 할 이미지 설정
	ImageIcon icon1 = new ImageIcon("images/mine1.png");			//	숫자 표시 할 이미지 설정
	ImageIcon icon2 = new ImageIcon("images/mine2.png");			//	숫자 표시 할 이미지 설정
	ImageIcon icon3 = new ImageIcon("images/mine3.png");			//	숫자 표시 할 이미지 설정
	ImageIcon icon4 = new ImageIcon("images/mine4.png");			//	숫자 표시 할 이미지 설정
	ImageIcon icon5 = new ImageIcon("images/mine5.png");			//	숫자 표시 할 이미지 설정
	ImageIcon icon6 = new ImageIcon("images/mine6.png");			//	숫자 표시 할 이미지 설정
	ImageIcon icon7 = new ImageIcon("images/mine7.png");			//	숫자 표시 할 이미지 설정
	ImageIcon icon8 = new ImageIcon("images/mine8.png");			//	숫자 표시 할 이미지 설정
	ImageIcon icon9 = new ImageIcon("images/mine9.png");			//	숫자 표시 할 이미지 설정
	ImageIcon icon_mine = new ImageIcon("images/mine.png");			//	지뢰 표시 할 이미지 설정
	ImageIcon icon_reset = new ImageIcon("images/reset.png");		//  리셋 표시 할 이미지 설정
	ImageIcon icon_flag = new ImageIcon("images/flag.png");			//  깃발 표시 할 이미지 설정
	ImageIcon icon_flagx = new ImageIcon("images/flagx.png");		//  잘못된 위치에 깃발 설치시 알려줄 이미지 설정
	ImageIcon logo = new ImageIcon("images/logo.png");				//  파일 로고 변경 할 이미지 설정
	
	
public MineSweeper() {
	super("지뢰찾기"); 																				// 타이틀 설정
	setResizable(true);																				// 프레임 사이즈 조정가능하게 변경
																									/* 
																									 * 다른 난이도에서 다른 난이도로 넘어갈때
																									 * 프레임 사이즈를 조정하기 위함임
																									 * 마지막 구문에 반드시 다시 닫아줄것
																									 */
	this.setIconImage(logo.getImage());																// 실행시 좌측 상단에 파일 아이콘 지뢰 이미지로 설정
	sw = new Stopwatch();
	if(level == 0) level = 1;																		// 전역변수 레벨이 0(초기값이 0임)일 경우 1로 설정함.
	if(level == 1) {																				// 레벨 값이 1(초급단계)일 경우
																									// 메뉴바 셋팅 시작
		game.add(new JMenuItem("새로 시작하기")).addActionListener(new ActionListener() { 				// 메뉴바에서 다시하기 클릭시
			public void actionPerformed(ActionEvent e) { dispose();	new MineSweeper();	} });		// 익명리스너로 처리
		game.addSeparator(); 																		// 메뉴바에 줄긋기
		game.add(new JMenuItem("초급")).addActionListener(new ActionListener() { 						// 메뉴바에서 초급메뉴 선택시 익명리스너로 처리
			public void actionPerformed(ActionEvent e) { dispose();	new MineSweeper();	} });				// 레벨값이 이미 1인 상황에서 초급을 선택시 창을 닫고 다시 띄우기만 함
		game.add(new JMenuItem("중급")).addActionListener(new ActionListener() { 						// 메뉴바에서 중급메뉴 선택시 익명리스너로 처리
			public void actionPerformed(ActionEvent e) {level = 2;	dispose();	new MineSweeper();	} });	// 중급을 선택시 레벨값을 2으로 변경해주고 선택시 창을 닫고 다시 띄우기만 함
		game.add(new JMenuItem("고급")).addActionListener(new ActionListener() {						// 메뉴바에서 고급메뉴 선택시 익명리스너로 처리
			public void actionPerformed(ActionEvent e) {level = 3;	dispose();	new MineSweeper();	} });	// 고급을 선택시 레벨값을 3으로 변경해주고 선택시 창을 닫고 다시 띄우기만 함
		game.addSeparator(); 																		// 메뉴바에 줄긋기
		game.add(new JMenuItem("종료")).addActionListener(new ActionListener() { 						// 메뉴바에서 종료메뉴 선택시
			public void actionPerformed(ActionEvent e) {System.exit(0);} });						// 마찬가지로 익명리스너로 처리함.
	    																							/*
	    																							 * 확실하게 종료할때에는 반드시
	    																							 * 이 명령어 (System.exit(0);) 를 사용할 것
	    																							 * */

		mb.add(game); 																				// 메뉴바(mb)에 메뉴(menu)를 추가
		mb.add(help);																				// 메뉴바(mb)에 메뉴(Help)를 추가
		this.setJMenuBar(mb);																		// 현재(this) 프레임(JFrame)에 메뉴바를 추가함
		this.setLayout(new BorderLayout());															// 현재 프레임의 레이아웃을 설정(BorderLayout)
		
		add(jp3, BorderLayout.NORTH);																//전체 Pannel jp3추가
		jp3.setBackground(gray);																	//jp3 색 설정
		jp3.setPreferredSize( new Dimension( 280, 340) );											//jp3 사이즈 설정
		
		jp3.add(jp2);																				//jp3에 jp2 추가
		jp2.setBackground(gray);																	//jp2색 설정
	    jp2.setBorder (new SoftBevelBorder(SoftBevelBorder.LOWERED));
		
	    //jp2.setLayout(new GridLayout(0,3,30,0));
		jp2.add(timer);																				// 패널에 타이머 추가
		jp2.add(reset); 																			// 패널에 리셋 버튼 추가
		jp2.add(counter);																			// 패널에 지뢰 카운터 추가
		jp2.setPreferredSize( new Dimension( 255, 50 ) );
		//timer.setLayout(new FlowLayout(FlowLayout.RIGHT,20,30));
		timer.setPreferredSize(new Dimension( 70, 35 ));
		timer.setBorder (new SoftBevelBorder(SoftBevelBorder.LOWERED));
		counter.setPreferredSize(new Dimension( 70, 35 ));
		counter.setBorder (new SoftBevelBorder(SoftBevelBorder.LOWERED));
		reset.setPreferredSize( new Dimension( 35, 35 ) );
		reset.setBorder(new BevelBorder(BevelBorder.RAISED));
		reset.setIcon(icon_reset);																	// 리셋버튼 아이콘 설정
		
		jp3.add(jp);																				// 지뢰판을 설정하기 위한 패널을 현 프레임에 추가
		jp.setPreferredSize( new Dimension( 255, 255) );
		jp.setBorder (new BevelBorder(BevelBorder.LOWERED));
		jp.setBackground(gray);
		jp.setSize(20,20);
		jp.setLayout(new GridLayout(10,10,0,0));

		timer.setContentAreaFilled(false);
		counter.setContentAreaFilled(false);
		reset.setContentAreaFilled(false);															// 버튼의 내부영역 채우기 안보이게 설정(파란색)
		timer.setFocusPainted(false);
		counter.setFocusPainted(false);
		reset.setFocusPainted(false);																// 버튼이 포커스(마우스가 올라간 상태)시 생기는 테두리를 안보이게 설정함
		reset.addActionListener(new ActionListener() { 												// 리셋버튼을 눌렀을때
			public void actionPerformed(ActionEvent e) {											// 익명리스너로 처리함. 코드가 단순하면서 액션리스너가 필요할때는 익명리스너로 처리하는게 편함
				JOptionPane.showMessageDialog(null, "'리셋'되었습니다.");								// 리셋 버튼 클릭시 "'리셋'되었습니다."라는 메시지가 담긴 팝업창을 띄움
				dispose();																			// 해당 프레임을 닫음.
				new MineSweeper();																	// 새로 불러줌()
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
		for(; ; ) {																					// 지뢰 배치를 위한 반복문
			for(int i, j, count = 0; count < 10;) {													// i, j, count 선언 및 초기화
				i=rnd.nextInt(10);																	// i값에 0 ~ 9 사이의 값을 대입
				j=rnd.nextInt(10);																	// j값에 0 ~ 9 사이의 값을 대입
																									// --------------------------------------------------
				if(mine[i][j] != -1) {																// -mine[i][j] 배열 값이 -1이 아니면 (지뢰가 배치되지 않았으면)	-
					mine[i][j] = -1;																// -mine[i][j] 배열에 -1을 대입							-
					count++;																		// -count값을 증가										-
				}																					// -중복값을 걸러내기 위함임								-
			}																						// --------------------------------------------------
			break;																					// 조건(count가 10보다 작은가?)이 충족되지 않으면 반복문 종료
		}



		for(int i = 0; i < 10; i++) {																// 버튼 생성을 위한 반복문
			for(int j = 0; j < 10; j++) {															// 
				if(mine[i][j] == 0) { 																// mine배열 i,j의 배열 값이 0일 경우
					jb[i][j] = new JButton();														// jb(JButton)[i][j]를 생성
					jb[i][j].setPreferredSize(new Dimension(20,20));								// jb[i][j]의 버튼 사이즈를 조정
					jb[i][j].addActionListener(new ButtonListener());								// 일반버튼 클릭에 반응하기 위한 액션리스너(ButtonListener) 생성
					jb[i][j].addMouseListener(new clickListener());									// 깃발을 표시하기 위해 우클릭을 체크할 마우스 리스너(clickListener)생성
					jb[i][j].setBorder(new BevelBorder(BevelBorder.RAISED));
					jb[i][j].setBackground(gray);
					jp.add(jb[i][j]);																// jp(JPanel)에 jb[i][j]를 추가
				}
				else if(mine[i][j] != 0) { 															// mine배열 i,j의 배열 값이 0이 아닐경우
					jb[i][j] = new JButton();														// jb(JButton)[i][j]를 생성
					jb[i][j].setPreferredSize(new Dimension(20, 20));								// jb[i][j]의 버튼 사이즈를 조정
					jb[i][j].addActionListener(new MineListener());									// 지뢰버튼 클릭에 반응하기 위한 액션리스너(MineListener) 생성
					jb[i][j].addMouseListener(new clickListener());									// 깃발을 표시하기 위해 우클릭을 체크할 마우스 리스너(clickListener)생성
					jb[i][j].setBorder(new BevelBorder(BevelBorder.RAISED));
					jb[i][j].setBackground(gray);
					jp.add(jb[i][j]);																// jp(JPanel)에 jb[i][j]를 추가
				}
			}
		}

		for(int i=0; i<10; i++) {																	// 지뢰 주변 칸에 숫자를 채우기 위한 반복문
			for(int j=0; j<10; j++) {
				int count=0;																		// 숫자를 체크하기 위해 변수 선언
				if(mine[i][j] != -1) {																// mine[i][j] 위치의 배열값이 지뢰값이(-1) 아니면
					if(i >= 0 && j >= 0 && i <= 9 && j <= 9) {										// 배열의 범위를 벗어남을 막기위해 설정
						if(i-1 >= 0 && j-1 >= 0)				// -------------------------------- //
							if(mine[i-1][j-1] == -1) count ++;	//				1		2		3--	//
						if(i-1 >= 0)							//				0		0		0--	// 
							if(mine[i-1][j] == -1) count ++;	//								 -- // 
						if(i-1 >= 0 && j+1 <= 9)				//				4		i,j		5-- // 1~8번 자리에 지뢰가 있는지 체크하고
							if(mine[i-1][j+1] == -1) count ++;	//				0		0		0-- // 지뢰가 있으면 count를 증가
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
					mine[i][j] = count;																// 체크가 끝나면 mine[i][j]에 count를 대입
				}
				count = 0;																			// 반복하기전에 count를 초기화
			}
		}
		ending = false;																				// 엔딩 값을 거짓으로 바꿈
		setVisible(true); 																			// 프레임 보이기
		setSize(280, 385); 																			// 프레임 사이즈 설정 윈도우
		setResizable(false);																		// 프레임 사이즈를 유저가 설정하지 못하게 잠금
		this.setLocationRelativeTo(null);															// 화면 중앙에 띄우기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);												// X버튼 누르면 닫기
																									// 설정 안하면 프로세스에(백그라운드에) 남아있음
	}
	

																									/*
																									 * 아래 중급부터 고급단계까지는
																									 * 초기 조건 level값의 비교와
																									 * 버튼과 지뢰배열의 사이즈등
																									 * 기본값만 다를뿐
																									 * 모든 구조는 같음
																									 */
	if(level == 2) {
		setResizable(true);
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("파일");

		menu.add(new JMenuItem("새로 시작하기")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MineSweeper();
			}
		});
		menu.addSeparator();
		menu.add(new JMenuItem("초급")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = 1;
				dispose();
				new MineSweeper();
			}
		});
		menu.add(new JMenuItem("중급")).addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MineSweeper();
			}
		});
		menu.add(new JMenuItem("고급")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = 3;
				dispose();
				new MineSweeper();
			}
		});
	
		menu.addSeparator();
	
		menu.add(new JMenuItem("종료")).addActionListener(new ActionListener() {
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
				JOptionPane.showMessageDialog(null, "'리셋'되었습니다.");
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
																									 * 여기서부터 
																									 * 레벨 3
																									 */
	
	if(level == 3) {
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("파일");

		menu.add(new JMenuItem("새로 시작하기")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					dispose();
					new MineSweeper();
			}
		});
		menu.addSeparator();
		menu.add(new JMenuItem("초급")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = 1;
				dispose();
				new MineSweeper();
			}
		});
		menu.add(new JMenuItem("중급")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = 2;
				dispose();
				new MineSweeper();
			}
		});
		menu.add(new JMenuItem("고급")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MineSweeper();
			}
		});
	
		menu.addSeparator();
	
		menu.add(new JMenuItem("종료")).addActionListener(new ActionListener() {
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
				JOptionPane.showMessageDialog(null, "'리셋'되었습니다.");
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

private class ButtonListener implements ActionListener  { 											// 지뢰가 아닌 버튼 클릭했을때 실행되는 액션리스너
	public void actionPerformed(ActionEvent e) {													
		if(level == 1) {																			// level값이 1일때
			for(int i = 0; i < 10; i++) {															// 해당 버튼의 배열값을 찾기 위한 반복문
				for(int j = 0; j < 10; j++) {
					if(e.getSource() == jb[i][j] && jb[i][j].getBackground() == gray) {				// 넘어온 e 값과 jb[i][j]의 값이 같고 백그라운드컬러가 DARK_GRAY면
						check(i, j);																// check에 (i,j)값을 넘겨줌
						if (starttime == false) {
			                sw.Start();
			                starttime = true;
			            }
					}
				}
			}
		}
																									/*
																									 * level값이 2인경우와
																									 * level값이 3인경우도
																									 * 반복문의 횟수만 다를뿐
																									 * 똑같음
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

private class MineListener implements ActionListener  { 												// 지뢰인 버튼을 클릭했을때 실행되는 액션리스너
	public void actionPerformed(ActionEvent e) {
		if(ending == false) /*JOptionPane.showMessageDialog(null, "아쉽습니다. 지뢰를 클릭했습니다.");*/{				// 엔딩값이 false인 경우 "아쉽습니다. 지뢰를 클릭했습니다."라는 메시지가 담긴 팝업창을 띄움
		
		if(level == 1) {																				// 지뢰로 설정된 모든 버튼의 위치를 나타내기 위한 반복문
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) {
					if(mine[i][j] == -1 && jb[i][j].getBackground() != flag) {																// 해당 i, j의 위치에 있는 버튼이 지뢰일 경우
							jb[i][j].setBackground(gray);												// 백그라운드컬러를 기본으로 바꿈
							jb[i][j].setIcon(icon_mine);												// 아이콘에 지뢰 이미지를 설정
							if (starttime == true) {
				                sw.stop();
				            }
							if (starttime == false) {
				                sw.Start();
				                starttime = true;
				            }
							
							ending=true;																// 전역변수 ending을 true로 바꿈 || 게임이 끝난 후 다른 지뢰 및 버튼 눌렀을때 반응 없게 하기 위함임
						}
						else if (mine[i][j] != -1 && jb[i][j].getBackground() == flag) {				// 해당 i, j의 위치에 있는 버튼이 지뢰가 아니면서 기본색이 아닐경우 (깃발을 꽂은경우)
							jb[i][j].setBorderPainted(false);											// BorderPainted 값을 false로 바꿈
							jb[i][j].setBackground(gray);												// 백그라운드컬러를 기본으로 바꿈
							jb[i][j].setIcon(icon_flagx);												// 깃발에 x 표시된 아이콘을 설정
						}
						else
							jb[i][j].setBorderPainted(false);											// 그 외의 경우(지뢰가 아니면서 깃발도 안꽂은경우)
						
					
					jb[i][j].setBorderPainted(false);													// 지뢰버튼이 아닌 경우 버튼을 클릭해도 반응이 없게 설정하기 위해 false값을 설정
					jb[i][j].setBackground(gray);														// 백그라운드컬러를 기본으로 설정
				}
			}
		}																								// 아래 내용 모두 같음
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


private class clickListener implements MouseListener{ 																						// 깃발을 세우기 위한 마우스 리스너
	
	/*
	 * 마우스리스너를 사용할때 기본적으로 제공되는 함수들은
	 * 사용하지 않더라도 있어야함.
	 */
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		if(level == 1) {
			if(e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {																		// 클릭한 버튼이 마우스 우클릭임을 비교
				for(int i = 0; i<10; i++) {																									// 우클릭한 버튼을 찾기위한 비교문
					for(int j = 0; j<10; j++) {
						if(e.getSource() == jb[i][j] && jb[i][j].isBorderPainted()==true && jb[i][j].getBackground() == gray) {	// 우클릭된 버튼이 기본 상태면
							jb[i][j].setIcon(icon_flag);																					// 깃발을 꽂음
							jb[i][j].setBackground(flag);																				// 깃발을 확인하기 위해 백그라운드 컬러를 변경
							num_mine--;
							tf_mine.setText("" + num_mine);
							if (starttime == false) {
				                sw.Start();
				                starttime = true;
				            }
						}
						else if(e.getSource() == jb[i][j] && jb[i][j].isBorderPainted()==true && jb[i][j].getBackground() == flag) {	// 깃발을 꽂은 버튼이면
							jb[i][j].setIcon(null);																							// 해당 버튼의 아이콘을 지우고
							jb[i][j].setBackground(gray);																		// 기본배경으로 변경
							num_mine++;
							tf_mine.setText("" + num_mine);
						}																													// 이하 같음
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

private void check(int i, int j) {																	// 비교하기 위한 함수
	if(level == 1) {																				// level값이 1일경우
		try {																						// 배열의 범위를 벗어남을 처리하기 위한 try~catch문
			if(mine[i][j] == 0 && jb[i][j].isBorderPainted() == true) {								// 해당 mine[i][j]값이 0의 값을 가지면서 버튼이 활성화된 상태에 있을경우
				jb[i][j].setBorderPainted(false);													// 버튼의 외곽선을 안보이게 설정
				jb[i][j].setContentAreaFilled(false);												// 버튼의 내부영역 채우기 안보이게 설정(파란색)
				jb[i][j].setBorder (new LineBorder (Color.GRAY));
				jb[i][j].setFocusPainted(false);													// 버튼이 포커스(마우스가 올라간 상태)시 생기는 테두리를 안보이게 설정함
				check(i+1, j);																		// 주변에 0이 있는지 확인하기 위함
				check(i-1, j);
				check(i, j+1);
				check(i, j-1);
				check(i+1, j+1);
				check(i-1, j-1);
				check(i-1, j+1);
				check(i+1, j-1);
			}
			
			else {																					// 그외의 경우
				if(jb[i][j].isBorderPainted() == true) {											// 해당 mine[i][j]의 값이 0이 아닌경우임
																									// 이 함수가 불러지는 경우 자체가 지뢰가 아닌경우이기 때문에
																									// 지뢰일 경우를 체크할 필요는 없음
					switch(mine[i][j]) {															// 스위치 케이스문
					case 1:																			// 1~9의 경우 각 숫자에 맞는 이미지를 설정함
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
			victory();																				// 승리 조건을 확인하기 위해 호출함
		}
		catch(java.lang.ArrayIndexOutOfBoundsException e){
			
		}
	}
	if(level == 2) {																				// 이하 똑같음
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


private void victory() {																			// 승리 조건을 확인
	if(level == 1) {
		int count = 0;
		for(int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
				if(this.mine[i][j] != -1 && this.jb[i][j].isBorderPainted() == false) {				// 해당 i,j가 지뢰가 아니면서 BorderPainted가 false인 경우
					count++;																		// 즉 지뢰가 아니면서 클릭된 경우를 비교함
				}
			}
		}
		if(count >= 90) {																			// 지뢰가 아니면서 클릭된 경우가 90이상인 경우
			for(int i=0; i<10; i++) {
				for(int j=0; j<10; j++) {
					if(jb[i][j].isBorderPainted() == true) {										// BorderPainted가 true인 경우가 있다면
						jb[i][j].setBorderPainted(false);											// false로 변경
						if(mine[i][j] == -1)														// 지뢰면
							jb[i][j].setIcon(icon_mine);											// 지뢰 이미지로 아이콘 변경
					}
				}
			}
			if(ending==false) {																		// 엔딩값이 거짓일 경우
				JOptionPane.showMessageDialog(null, "축하합니다. 승리했습니다!");							// 승리 메시지 출력
				ending = true;																		// 엔딩값을 참으로 설정
			}
		}
	}																								// 이하 똑같음
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
				JOptionPane.showMessageDialog(null, "축하합니다. 승리했습니다!");
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
				JOptionPane.showMessageDialog(null, "축하합니다. 승리했습니다!");
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
	new MineSweeper();																						// 프로그램 실행시 프레임 불러오기
	}static int level = 0; static boolean ending = false;													// 전역변수 level 선언 및 0으로 초기화, ending 선언 및 false로 초기화
}