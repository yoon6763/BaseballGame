package Baseball;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Baseball extends JFrame implements ActionListener, KeyListener{
	Container ct = getContentPane();
	JTextArea log;
	JTextField input;
	JButton inputb;
	JLabel strike;
	JLabel ball;
	JLabel chnum;
	int[] answer = new int[3];
	final int MAX_THROW = 10;
	int cnfThrow = 0;
	
	public Baseball()
	{
		Initialize();
		setTitle("Baseball Game");
		setSize(750,600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void Initialize()
	{
		ct.setLayout(new BorderLayout());
		
		//왼쪽 텍스트 에어리어
		log = new JTextArea();
		log.append("슷자 야구 게임에 오신것을 환영합니다!\n");
		ct.add(log, BorderLayout.CENTER);
		
		//오른쪽 상위 서브패널 (입력창 분리용)
		JPanel supersub = new JPanel();
		supersub.setLayout(new BorderLayout());
		ct.add(supersub,BorderLayout.EAST);
		
		//오른쪽 서브패널
		JPanel sub = new JPanel();
		sub.setLayout(new GridLayout(5,1));
		supersub.add(sub,BorderLayout.CENTER);
		
		//숫자 입력 패널
		JPanel pinput = new JPanel();
		pinput.setLayout(new BorderLayout());
		supersub.add(pinput,BorderLayout.NORTH);
		
		//입력 텍스트필드
		input = new JTextField();
		pinput.add(input,BorderLayout.CENTER);
		input.addKeyListener(this);
		
		//입력 버튼
		inputb = new JButton();
		inputb.setText("입력");
		inputb.addActionListener(this);
		pinput.add(inputb,BorderLayout.EAST);
		inputb.setFont(new Font("나눔고딕", Font.BOLD, 45));
		inputb.setHorizontalAlignment(SwingConstants.CENTER);
		inputb.setBackground(new Color(198,255,203));
		
		//스트라이크 볼 패널
		JPanel sb = new JPanel();
		sb.setLayout(new GridLayout(1,2));
		sub.add(sb);
		sb.setBackground(new Color(164,200,255));

		
		//남은 기회 라벨
		JLabel chance = new JLabel();
		chance.setText("남은 기회");
		chance.setFont(new Font("나눔고딕", Font.BOLD, 30));
		chance.setHorizontalAlignment(SwingConstants.CENTER);
		sub.add(chance);

		//남은 기회 횟수
		chnum = new JLabel();
		chnum.setText(MAX_THROW+"");
		chnum.setFont(new Font("나눔고딕", Font.BOLD, 45));
		chnum.setHorizontalAlignment(SwingConstants.CENTER);
		sub.add(chnum);
		
		sub.setBackground(new Color(213,240,251));

		strike = new JLabel();
		strike.setText("   strike");
		ball = new JLabel();
		ball.setText("   ball");
		sb.add(strike);
		sb.add(ball);
		strike.setFont(new Font("나눔고딕", Font.BOLD, 45));
		strike.setHorizontalAlignment(SwingConstants.CENTER);
		ball.setFont(new Font("나눔고딕", Font.BOLD, 45));
		ball.setHorizontalAlignment(SwingConstants.CENTER);
		
		//다시 시작 버튼
		JButton restart = new JButton();
		restart.setText("다시 시작");
		restart.setFont(new Font("나눔고딕", Font.BOLD, 45));
		restart.setHorizontalAlignment(SwingConstants.CENTER);
		restart.addActionListener(this);
		restart.setBackground(new Color(183,210,255));
		sub.add(restart);
		
		//게임 종료 버튼
		JButton exitbutton = new JButton();
		exitbutton.setText("게임 종료");
		exitbutton.setFont(new Font("나눔고딕", Font.BOLD, 45));
		exitbutton.setHorizontalAlignment(SwingConstants.CENTER);
		exitbutton.setBackground(new Color(153,191,255));
		sub.add(exitbutton);
		exitbutton.addActionListener(this);

		answermake();
	}
	
	public void play()
	{
		int[] input = new int[3];
		
		input = importText();
		
		int[] stbal = judge(input[0],input[1],input[2]);
		strike.setText(stbal[1]+" strike");
		ball.setText(stbal[0]+" ball");
		log.append(input[0]+" "+input[1]+" "+input[2]+"\n");
				
		if(stbal[1] == 3)
		{
			log.append("정답!!! 총 "+cnfThrow+"번 던지셨습니다.\n");	
		}
		else
			log.append(stbal[1]+" strike "+stbal[0]+" ball 입니다\n");
		
		if(cnfThrow == MAX_THROW-1)
		{
			inputb.setEnabled(false);
			log.append("졌습니다. 정답은 "+answer[0]+" "+answer[1]+" "+answer[2]+" 입니다.\n");
		}
		cnfThrow++;
		chnum.setText((MAX_THROW-cnfThrow)+"");
	}
	
	public int[] importText()
	{
		int[] arr = new int[3];
		
		String temp = input.getText();
		
		if(temp.length() == 3)
		{
			int num = Integer.parseInt(temp);
			
			arr[2] = num%10;
			num /= 10;
			arr[1] = num%10;
			num /= 10;
			arr[0] = num;
			
			input.setText("");
		}
		else
		{
			String[] str = temp.split(" ");
			
			for(int i = 0; i<str.length; i++)
				arr[i] = Integer.parseInt(str[i]);
			
			input.setText("");
		}
		return arr;
	}
	
	private int[] judge(int a, int b, int c)
	{
		int[] num = {a,b,c};
		int[] stbal = {0,0};
		
		for(int i = 0; i<3;i++)
		{
			if(num[i] == answer[i])
				stbal[1]++;
			else
			{
				for(int k = 0; k<3;k++)
				{
					if(num[i] == answer[k])
						stbal[0]++;
				}
			}
		}
		return stbal;
	}
	
	public void answermake()
	{
		Random ran = new Random();
		
		for(int i = 0;i<3;i++)
		{
			answer[i] = ran.nextInt(9)+1;
			for(int k = i;k>=0;k--)
			{
				if(k>0)
					{
					if(answer[i] == answer[k-1])
					{
						answer[i] = ran.nextInt(9)+1;
						k = i + 1;
					}
				}
			}
		}
	}
	
	public void restart()
	{
		inputb.setEnabled(true);
		answermake();
		chnum.setText(MAX_THROW+"");
		log.setText("슷자 야구 게임에 오신것을 환영합니다!\n");
		cnfThrow = 0;
	}

	public void actionPerformed(ActionEvent e)
	{
		String str = e.getActionCommand();
		
		if(str.equals("입력"))
			play();
		else if(str.equals("다시 시작"))
			restart();
		else if(str.equals("게임 종료"))
			System.exit(0);
	}

	
	public void keyPressed(KeyEvent e)
	{
		int a = e.getKeyCode();
		
		if(a == 10 && MAX_THROW > cnfThrow) // 10은 enter를 의미
			play();
	}

	
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}

}
