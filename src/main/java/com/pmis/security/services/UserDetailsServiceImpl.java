package com.pmis.security.services;

import com.pmis.models.model.Q_User;
import com.pmis.models.model.FunctionGrant;
import com.pmis.models.model.S_Organization;
import com.pmis.services.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserService userService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		try {
			Q_User user = userService.findQ_UserById(userId);
			if (user.getEnable() == null || !user.getEnable()) {
				throw new UsernameNotFoundException("Tài khoản [" + userId + "] không tồn tại");
			}
			String orgid = user.getOrgid();// truong ORGID trong bang Q_USER, la don vi mac dinh cua nguoi dung
			S_Organization _org = userService.getS_OrgByOrgid(orgid);
			
			if(_org != null) {
				user.setLoaiHinhDonVi(_org.getLoaiHinh());
				user.setTenCongTy(_org.getOrgName());
			}
//			if ("EVN".equalsIgnoreCase(orgid) || "EVNIT".equalsIgnoreCase(orgid)) {
//				// Nguoi dung evn, evnit vao xem, tra ve danh sach function theo don vi dau tien
//				// nguoi dung duoc gan
//				// orgid_client_view se la don vi dau tien nguoi dung duoc gan quyen
//				List<S_Organization> lstOrg = userService.getLstOrgGranUser(user.getUserid());
//				for (S_Organization org : lstOrg) {
//					if (!"EVN".equalsIgnoreCase(org.getOrgid()) && !"EVNIT".equalsIgnoreCase(org.getOrgid())) {
//						user.setOrgid_client_view(org.getOrgid());
//						user.setLoaiHinhDonVi(org.getLoaiHinh());
//						break;
//					}
//				}
//			} else {
//				// Nguoi dung duoi don vi
//				// orgid_client_view chinh la don vi cua nguoi dung
//				user.setOrgid_client_view(orgid);
//			}

			 List<FunctionGrant> userFunction = userService.getListFunctionGrantByUser(userId);
//			List<FunctionGrant> userFunction = userService.getListFunctionGrantByUserAndOrgIdView(user.getUserid(),user.getOrgid_client_view());

			return UserDetailsImpl.build(user, userFunction);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}

}
