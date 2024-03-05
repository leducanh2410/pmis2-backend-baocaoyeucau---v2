package com.pmis.services.admin;

import com.pmis.models.model.Q_User;
import com.pmis.models.model.FunctionGrant;
import java.util.List;

import com.pmis.models.model.S_Organization;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    Q_User findQ_UserById(String userId);

    List<FunctionGrant> getListFunctionGrantByUser(String userId);

    Boolean existsQ_UserById(String userId);

    S_Organization getS_OrgByOrgid(String orgid);

    List<FunctionGrant> getListFunctionGrantByUserAndOrgIdView(String userId,String orgidView);
}
