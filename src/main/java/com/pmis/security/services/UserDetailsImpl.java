package com.pmis.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pmis.models.model.Q_User;
import com.pmis.models.model.FunctionGrant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String userId;

	private final String username;

	private String descript;

	@JsonIgnore
	private final String password;

	private final Boolean enable;

	private final Collection<? extends GrantedAuthority> authorities;

	private List<FunctionGrant> fgrant;

	private String orgIdOfUser;
	private String orgIdClientView;

	private String loaiHinhCongTy;

	private String tenCongTy;

	public UserDetailsImpl(String userId, String username, String descript, String password, Boolean enable,
			Collection<? extends GrantedAuthority> authorities, List<FunctionGrant> fgrant, String _orgIdOfUser,
			String _orgIdClientView, String _loaiHinhCongTy, String _tenCongTy) {
		this.userId = userId;
		this.username = username;
		this.descript = descript;
		this.password = password;
		this.enable = enable;
		this.authorities = authorities;
		this.fgrant = fgrant;
		this.orgIdOfUser = _orgIdOfUser;
		this.orgIdClientView = _orgIdClientView;
		this.loaiHinhCongTy = _loaiHinhCongTy;
		this.tenCongTy = _tenCongTy;
	}

	public static UserDetailsImpl build(Q_User user, List<FunctionGrant> fgrant) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleId())).collect(Collectors.toList());

		return new UserDetailsImpl(user.getUserid(), user.getUsername(), user.getDescript(), user.getPassword(),
				user.getEnable(), authorities, fgrant, user.getOrgid(), user.getOrgid_client_view(),
				user.getLoaiHinhDonVi(), user.getTenCongTy());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enable;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public List<FunctionGrant> getFgrant() {
		return fgrant;
	}

	public void setFgrant(List<FunctionGrant> fgrant) {
		this.fgrant = fgrant;
	}

	public String getOrgIdOfUser() {
		return orgIdOfUser;
	}

	public void setOrgIdOfUser(String orgIdOfUser) {
		this.orgIdOfUser = orgIdOfUser;
	}

	public String getOrgIdClientView() {
		return orgIdClientView;
	}

	public void setOrgIdClientView(String orgIdClientView) {
		this.orgIdClientView = orgIdClientView;
	}

	public String getLoaiHinhCongTy() {
		return loaiHinhCongTy;
	}

	public void setLoaiHinhCongTy(String loaiHinhCongTy) {
		this.loaiHinhCongTy = loaiHinhCongTy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(userId, user.userId);
	}

	public String getTenCongTy() {
		return tenCongTy;
	}

	public void setTenCongTy(String tenCongTy) {
		this.tenCongTy = tenCongTy;
	}

}
