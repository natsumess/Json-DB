package com.maven.ReadJson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maven.DAO.CustomerDAO;
import com.maven.DAO.CustomerDAOImpl;
import com.maven.bean.Customer;
import com.maven.utils.Utils;

public class JsonRead {
	
	public static void main(String[] args) throws IOException {
		int menu;  //保存菜单序号
		
		while (true) {
			//输出菜单那界面
			Utils.printMenuInfo();
			//读取一个序号
			menu = readMenuNum();
			switch (menu) {
				case 1:
					//导入JSON文件功能
					readJsonFile();
					break;
				case 2:
					//清空数据库功能
					deleteDB();
					break;
				case 3:
					//查看所有数据功能
					findAllCustomer();
					break;
				case 4:
					//查询年龄小于30活跃的顾客
					findCustomerByAgeAndActive();
					break;
				case 5:
					//自定义条件查询顾客
					findCustomerByAny();
					break;
				case 6:
					//通过名字查询顾客 
					findCustomerByName_List();
					findCustomerByName_Array();
					break;
				case 7:
					//名字含n的顾客
					findCustomerByNameLike();
					break;
				case 8:
					//退出
					exitApplication();
					break;
			}
		}
	}
	/**
	 * 让操作者输入一个数字，
	 * 非数字则输出警告信息：禁止输入数以外的字符！，
	 * 并让重新输入
	 * @return Integer.parseInt(menu) 端末输入的序号
	 */
	private static int readMenuNum() {
		Scanner scan = new Scanner(System.in);
		String menu = "";
		boolean isNotNumber = true;
		do {
			System.out.println("请输入序号：");
			menu = scan.nextLine();
			try {
				Integer.parseInt(menu);
				isNotNumber = false;
			} catch (NumberFormatException e) {
				isNotNumber = true;
				System.out.println("禁止输入数以外的字符！");
			}
		} while (isNotNumber); 
		return Integer.parseInt(menu);
	}
	/**
	 * 读取JSON文件内容并按Operate操作
	 * Operate：add 添加顾客信息
	 * Operate：del 删除顾客信息
	 * Operate：upd 更新顾客信息
	 * @throws IOException
	 */
	private static void readJsonFile()  throws IOException{
		Scanner scan = new Scanner(System.in);
		String path = "./src/main/java/com/maven/ReadJson/customers.json";
		File file = new File(path);
		/*do {
			Utils.printInputJsonInfo();
			path = scan.nextLine();
			file = new File(path);
		} while(!(file.exists()&&file.isFile()));*/
		
		String content = FileUtils.readFileToString(file,"UTF-8");
		
		Gson gson = new Gson();
		Type collectionType = new TypeToken<List<Customer>>(){}.getType();
		List<Customer> customer = gson.fromJson(content, collectionType);
		
		CustomerDAO custdao = new CustomerDAOImpl();

		customer.forEach($->{
			switch ($.getOperate()) {
			case "add":
				if (!isCustomerExist($.getId())) {
					if (custdao.addCustomer($) == 1) {
						Utils.printMsg("ID为" + $.getId() + "的顾客添加成功！");
					}
				} else {
					Utils.printMsg("顾客添加失败！原因：ID为" + $.getId() + "的顾客已存在！");
				}
			break;
			case "del":
				/*if (isCustomerExist($.getId())) {
					if (custdao.deleteCustomer($.getId()) == 1) {
						Utils.printMsg("ID为" + $.getId() + "的顾客删除成功！");
					}
				} else {
					Utils.printMsg("顾客删除失败！原因：ID为" + $.getId() + "的顾客不存在！");
				}*/
				break;
			case "upd":
				/*if (isCustomerExist($.getId())) {
					if (custdao.updateCustomer($) == 1) {
						Utils.printMsg("ID为" + $.getId() + "的顾客更新成功！");
					}
				} else {
					Utils.printMsg("顾客更新失败！原因：ID为" + $.getId() + "的顾客不存在！");
				}*/
				break;
			}
		});

	}
	/**
	 * 查看顾客是否存在
	 * @param id 顾客ID
	 * @return true：存在    false：不存在
	 */
	private static boolean isCustomerExist(String id) {
		CustomerDAO custdao = new CustomerDAOImpl();
		List<Customer> result = custdao.findCustomer(id);
		Utils.printMsg("sise:"+String.valueOf(result.size()));
		return result.size() > 0 ? true : false;
	}
	/**
	 * 删除顾客信息
	 * 成功则输出：数据库中数据已全部删除！
	 */
	private static void deleteDB() {
		CustomerDAO custdao = new CustomerDAOImpl();
		
		Utils.printMsg("确定要删除？");
		Utils.printMsg("1.YES");
		Utils.printMsg("2.NO");
		int num = readMenuNum();
		
		if (num == 1) {
			int result = custdao.deleteCustomer(null);
			if (result >= 0) {
				Utils.printMsg("数据库中数据已全部删除！");
			} else {
				Utils.printMsg("删除失败！");
			}
		} else {
			Utils.printMsg("操作已取消！");
		}
		
	}
	/**
	 * 查询并输出所有顾客信息
	 */
	private static void findAllCustomer() {
		CustomerDAO custdao = new CustomerDAOImpl();
		List<Customer> result = custdao.findCustomer(null);
		result.forEach($->{
			Utils.printMsg($.toString());
		});
		Utils.printMsg("总共" + result.size() + "条数据");
	}
	/**
	 * 查询年龄小于30活跃的顾客
	 */
	private static void findCustomerByAgeAndActive() {
		CustomerDAO custdao = new CustomerDAOImpl();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("age", 30);
		map.put("active", 1);
		List<Customer> result = custdao.findCustomerByAgeAndActive(map);
		result.forEach($->{
			Utils.printMsg($.toString());
		});
		Utils.printMsg("总共" + result.size() + "条数据");
	}
	/**
	 * 自定义条件查询顾客
	 */
	private static void findCustomerByAny() {
		CustomerDAO custdao = new CustomerDAOImpl();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("conditon1", "and age < 30");
		map.put("conditon2", "and active = 1");
		List<Customer> result = custdao.findCustomerByAny(map);
		result.forEach($->{
			Utils.printMsg($.toString());
		});
		Utils.printMsg("总共" + result.size() + "条数据");
	}
	private static void findCustomerByName_List() {
		CustomerDAO custdao = new CustomerDAOImpl();
		List<String> paramlist = new ArrayList<String>();
		//不要忘了单引号"'",以下两种形式
		//paramlist.add("'Carr'"); 
		//'${item}'
		paramlist.add("Carr");
		paramlist.add("Skinner");
		paramlist.add("Barker");
		List<Customer> result = custdao.findCustomerByName(paramlist);
		Utils.printMsg("通过List传递参数");
		result.forEach($->{
			Utils.printMsg($.toString());
		});
		Utils.printMsg("总共" + result.size() + "条数据");
	}
	
	private static void findCustomerByName_Array() {
		CustomerDAO custdao = new CustomerDAOImpl();
		String[] paramArray = {"Carr","Skinner","Barker"};
		List<Customer> result = custdao.findCustomerByName(paramArray);
		Utils.printMsg("通过Array传递参数");
		result.forEach($->{
			Utils.printMsg($.toString());
		});
		Utils.printMsg("总共" + result.size() + "条数据");
	}
	
	private static void findCustomerByNameLike() {
		CustomerDAO custdao = new CustomerDAOImpl();
		String param = "n";
		List<Customer> result = custdao.findCustomerByName(param);
		result.forEach($->{
			Utils.printMsg($.toString());
		});
		Utils.printMsg("总共" + result.size() + "条数据");
	}
	/**
	 * 退出
	 */
	private static void exitApplication() {
		System.exit(0);
	}
}
