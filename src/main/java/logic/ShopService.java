package logic;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dao.ItemDao;
import dao.UserDao;
import dao.saleDao;
import dao.saleItemDao;

@Service //@Component + Service(controller 기능과 dao 기능 중간 역할)
public class ShopService {
	@Autowired //객체 주입
	private ItemDao itemDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private saleDao saleDao;
	@Autowired
	private saleItemDao saleItemDao;
	
	
	public List<Item> itemList() {
		return itemDao.list();
	}
	
	public Item getItem(Integer id) {
		return itemDao.getItem(id);
	}

	public void itemCreate(Item item, HttpServletRequest request) {
		if(item.getPicture() != null && !item.getPicture().isEmpty()) {
			//업로드해야하는 파일의 내용이 있는 경우
			String path = request.getServletContext().getRealPath("/")+ "img/";
			uploadFileCreate(item.getPicture(),path);
			//업로드된 파일이름
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		//db에 내용 저장
		int maxid = itemDao.maxId(); //item 테이블에 저장된 최대 id값
		item.setId(maxid+1);
		itemDao.insert(item); //db에 데이터 추가
	}

	private void uploadFileCreate(MultipartFile file, String path) {
		//file : 파일의 내용
		//path : 업로드할 폴더
		String orgFile = file.getOriginalFilename(); //파일이름
		File f = new File(path);
		if(!f.exists()) f.mkdirs();
		try {
			// transferTo : file에 저장된 내용을 파일로 저장 
			file.transferTo(new File(path+orgFile));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void itemUpdate(Item item, HttpServletRequest request) {
		if(item.getPicture() != null && !item.getPicture().isEmpty()) {
			String path = request.getServletContext().getRealPath("/")+"img/";
			uploadFileCreate(item.getPicture(),path);
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		itemDao.update(item);
	}

	public void itemdelete(Integer id) {
		itemDao.delete(id);
	}

	public void userInsert(User user) {
		userDao.insert(user);
	}

	public User selectUserOne(String userid) {
		return userDao.selectOne(userid);
	}
	
	
	/*
	 * 1.로그인정보,장바구니정보 => sale, saleItem 테이블의 데이터 저장
	 * 2.결화는 Sale 객체에 저장
	 * 		-sale 테이블 저장 : saleid값 구하기. 최대값+1
	 * 		-saleItem 테이블 저장 : Cart 데이터를 이용하여 저장
	 */
	public Sale checkend(User loginUser, Cart cart) {
		int maxsaleid = saleDao.getMaxSaleId();//saleid 최대값 조회
		Sale sale = new Sale();
		sale.setSaleid(maxsaleid+1);
		sale.setUser(loginUser);
		sale.setUserid(loginUser.getUserid());
		saleDao.insert(sale);//sale 테이블에 데이터 추가
		int seq = 0;
		for(ItemSet is : cart.getItemSetList()) {
			SaleItem saleItem = new SaleItem(sale.getSaleid(),++seq,is);
			sale.getItemList().add(saleItem);
			saleItemDao.insert(saleItem); //saleItem 테이블에 데이터 추가
		}
		return sale; //주문정보, 주문상품정보, 상품정보, 사용자정보
	}

	public List<Sale> salelist(String userid) {
		List<Sale> list = saleDao.list(userid);//id 사용자가 주문 정보목록
		for(Sale sa : list) {
			List<SaleItem> saleitemlist = saleItemDao.list(sa.getSaleid());//한개의 주문에 해당하는 주문상품 목록
			for(SaleItem si : saleitemlist) {
				Item item = itemDao.getItem(si.getItemid()); //상품정보
				si.setItem(item);
			}
			sa.setItemList(saleitemlist);
		}
		return list;
	}

	public void userUpdate(User user) {
		userDao.update(user);
	}

	public void userDelete(String userid) {
		userDao.delete(userid);
	}

	public void userPassword(String userid, String chgpass) {
		userDao.update(userid,chgpass);
	}
	
	
}
