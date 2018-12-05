package gongdan.com.three;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gongdan.com.util.ImportExecl;
import gongdan.com.util.ParseExcel;

//文件选择器演示

public class JFileChooserStart extends JFrame {
	private JFileChooser chooser;  //文件选择器
	private JButton button;  //选择文件按钮
	private JComboBox comboBox;  //用于设定文件对话框作用(打开还是保存文件)

	//文件选择器
	public JFileChooserStart() {
		super("生成工单的小工具 （请注意文档内容的格式哟~~）");  //调用父类构造函数
		Container contentPane = getContentPane();  //得到容器
		contentPane.setLayout(new FlowLayout());  //设置布局管理器为Flowlayout
		chooser=new JFileChooser();  //初始化文件选择器
		button = new JButton("选择文件");  //初始化按钮
		comboBox=new JComboBox();  //初始化组合框
		comboBox.addItem("打开");  //增加组合框列表内容
		comboBox.addItem("保存");
		contentPane.add(comboBox);  //增加组件到容器
		contentPane.add(button);

		button.addActionListener(new ActionListener() {  //按钮事件处理
			public void actionPerformed(ActionEvent e) {
				int state;  //文件选择器返回状态
				chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());  //移去所有文件过滤器
				chooser.addChoosableFileFilter(new MyFileFilter("xlsx","2007版excel xlsx格式文件"));   //增加文件过滤器,接爱gif文件
				chooser.addChoosableFileFilter(new MyFileFilter("xls", "2003版excel  xls格式文件"));   //增加文件过滤器,接爱gif文件
				if (comboBox.getSelectedIndex()==0)  //组合框为"打开"
					state=chooser.showOpenDialog(null);   //显示打开文件对话框
				else
					state=chooser.showSaveDialog(null);  //显示保存文件对话框
				File file = chooser.getSelectedFile();  //得到选择的文件

				if(file != null && state == JFileChooser.APPROVE_OPTION) {  //选择了文件并点击了打开可保存按钮
					JOptionPane.showMessageDialog(null, "即将生成 "+file.getPath()+"文件的工单"); //显示提示信息
					System.out.println(file.getPath() +"|||"+ file.getName());
					String fileName = file.getName();
					try {
						String IP = "10.23.245.60";
						if(fileName.contains("挂盘")){
							IP = "10.23.245.21";
						}else if(fileName.contains("前置机")){
							IP = "10.23.245.60";
						}
						int flag = 0;
						if(fileName.contains("TV")){
							CreateXMLForSDTV createXMLTVForSD = new CreateXMLForSDTV();
							if(fileName.contains("电影Program")){
								List<Map<String,String>> list = ParseExcel.getMapList(file,1);
								createXMLTVForSD.REGISTDocument(list,IP);
							}else if(fileName.contains("电视剧Program")){
								List<Map<String,String>> list = ParseExcel.getMapList(file,2);
								createXMLTVForSD.addDocument1122(list,IP);
							}else if(fileName.contains("电视剧Series")){
								List<Map<String,String>> list = ParseExcel.getMapList(file,3);
								createXMLTVForSD.addDocument(list,IP);
							}else{
								flag++;
							}
						}else if (fileName.contains("手机端")){
							CreateXMLForSDPHONE createXMLPhoneForSD = new CreateXMLForSDPHONE();
							if(fileName.contains("电影")){
								List<Map<String,String>> list = ParseExcel.getMapList(file,4);
								createXMLPhoneForSD.REGISTDocument(list,IP);
							}else if(fileName.contains("电视剧")){
								List<Map<String,String>> list = ParseExcel.getMapList(file,5);
								createXMLPhoneForSD.addDocument(list,IP);
								createXMLPhoneForSD.addDocument1122(list,IP);
							}else{
								flag++;
							}
						}else{
							flag++;
						}
						if(flag == 0){
							showFrame("成功");
						}else{
							showFrame("错误");
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						showFrame("失败");
					} catch (IOException e1) {
						e1.printStackTrace();
						showFrame("失败");
					} catch (Exception e1) {
						e1.printStackTrace();
						showFrame("失败");
					}
				}
				else if(state == JFileChooser.CANCEL_OPTION) {  //点击了撤销按钮
					JOptionPane.showMessageDialog(null, "退出!");  //显示提示信息
				}
				else if(state == JFileChooser.ERROR_OPTION) {
					JOptionPane.showMessageDialog(null, "错误!");  //显示提示信息
				}
			}
		});

		this.setSize(500,250);  //设置窗口大小
		this.setVisible(true);  //设置窗口可见
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
	}
	static void showFrame(String aa){
		
		  JFrame frame = new JFrame("生成工单结果："); //实例化JFrame对象
		  JLabel  label = null;
		  if("成功".equals(aa)){
			  label = new JLabel("                     生成工单"+aa+",请查看 E盘根目录 SHAN/SHANS 文件夹");
		  }else if("错误".equals(aa)){
			  label = new JLabel("                     请检查文件名称是否符合案例");
		  }else{
			  label = new JLabel("                     生成工单"+aa+",请转换文件格式为2003版 xls文件，再试一次~~~");			  
		  }
		    frame.setBounds(10, 10, 200, 200);
		    frame.setSize(500,100);  //设置窗口尺寸
		    frame.setVisible(true);  //窗口可视
		    frame.add(label);
		 //   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
	  }
	public static void main(String args[]) {
		new JFileChooserStart();
	}
}
