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
		
		//���� �ؽ�Ʈ �����
		log = new JTextArea();
		log.append("���� �߱� ���ӿ� ���Ű��� ȯ���մϴ�!\n");
		ct.add(log, BorderLayout.CENTER);
		
		//������ ���� �����г� (�Է�â �и���)
		JPanel supersub = new JPanel();
		supersub.setLayout(new BorderLayout());
		ct.add(supersub,BorderLayout.EAST);
		
		//������ �����г�
		JPanel sub = new JPanel();
		sub.setLayout(new GridLayout(5,1));
		supersub.add(sub,BorderLayout.CENTER);
		
		//���� �Է� �г�
		JPanel pinput = new JPanel();
		pinput.setLayout(new BorderLayout());
		supersub.add(pinput,BorderLayout.NORTH);
		
		//�Է� �ؽ�Ʈ�ʵ�
		input = new JTextField();
		pinput.add(input,BorderLayout.CENTER);
		input.addKeyListener(this);
		
		//�Է� ��ư
		inputb = new JButton();
		inputb.setText("�Է�");
		inputb.addActionListener(this);
		pinput.add(inputb,BorderLayout.EAST);
		inputb.setFont(new Font("�������", Font.BOLD, 45));
		inputb.setHorizontalAlignment(SwingConstants.CENTER);
		inputb.setBackground(new Color(198,255,203));
		
		//��Ʈ����ũ �� �г�
		JPanel sb = new JPanel();
		sb.setLayout(new GridLayout(1,2));
		sub.add(sb);
		sb.setBackground(new Color(164,200,255));

		
		//���� ��ȸ ��
		JLabel chance = new JLabel();
		chance.setText("���� ��ȸ");
		chance.setFont(new Font("�������", Font.BOLD, 30));
		chance.setHorizontalAlignment(SwingConstants.CENTER);
		sub.add(chance);

		//���� ��ȸ Ƚ��
		chnum = new JLabel();
		chnum.setText(MAX_THROW+"");
		chnum.setFont(new Font("�������", Font.BOLD, 45));
		chnum.setHorizontalAlignment(SwingConstants.CENTER);
		sub.add(chnum);
		
		sub.setBackground(new Color(213,240,251));

		strike = new JLabel();
		strike.setText("   strike");
		ball = new JLabel();
		ball.setText("   ball");
		sb.add(strike);
		sb.add(ball);
		strike.setFont(new Font("�������", Font.BOLD, 45));
		strike.setHorizontalAlignment(SwingConstants.CENTER);
		ball.setFont(new Font("�������", Font.BOLD, 45));
		ball.setHorizontalAlignment(SwingConstants.CENTER);
		
		//�ٽ� ���� ��ư
		JButton restart = new JButton();
		restart.setText("�ٽ� ����");
		restart.setFont(new Font("�������", Font.BOLD, 45));
		restart.setHorizontalAlignment(SwingConstants.CENTER);
		restart.addActionListener(this);
		restart.setBackground(new Color(183,210,255));
		sub.add(restart);
		
		//���� ���� ��ư
		JButton exitbutton = new JButton();
		exitbutton.setText("���� ����");
		exitbutton.setFont(new Font("�������", Font.BOLD, 45));
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
			log.append("����!!! �� "+cnfThrow+"�� �����̽��ϴ�.\n");	
		}
		else
			log.append(stbal[1]+" strike "+stbal[0]+" ball �Դϴ�\n");
		
		if(cnfThrow == MAX_THROW-1)
		{
			inputb.setEnabled(false);
			log.append("�����ϴ�. ������ "+answer[0]+" "+answer[1]+" "+answer[2]+" �Դϴ�.\n");
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
		log.setText("���� �߱� ���ӿ� ���Ű��� ȯ���մϴ�!\n");
		cnfThrow = 0;
	}

	public void actionPerformed(ActionEvent e)
	{
		String str = e.getActionCommand();
		
		if(str.equals("�Է�"))
			play();
		else if(str.equals("�ٽ� ����"))
			restart();
		else if(str.equals("���� ����"))
			System.exit(0);
	}

	
	public void keyPressed(KeyEvent e)
	{
		int a = e.getKeyCode();
		
		if(a == 10 && MAX_THROW > cnfThrow) // 10�� enter�� �ǹ�
			play();
	}

	
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}

}
