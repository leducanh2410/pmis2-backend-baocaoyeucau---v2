package com.pmis.services.admin;

import com.pmis.models.model.ERole;
import com.pmis.models.model.Q_Role;
import com.pmis.models.model.Q_User;
import com.pmis.models.model.FunctionGrant;
import com.pmis.models.model.S_Organization;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Q_User findQ_UserById(String userId) {
        Q_User sQ_User = null;
        String queryStringUser = "SELECT [USERID]\n" + "      ,[USERNAME],ORGID\n" + "      ,[PASSWORD]\n"
                + "      ,[DESCRIPT]\n" + "      ,[MOBILE]\n" + "      ,[EMAIL]\n" + "      ,[OFFICEID]\n"
                + "      ,[USERID_DOMAIN]\n" + "      ,[DOMAINID]\n" + "      ,[ENABLE]\n" + "      ,[USER_CR_ID]\n"
                + "      ,[USER_CR_DTIME]\n" + "      ,[USER_MDF_ID]\n" + "      ,[USER_MDF_DTIME]\n    , [EVNID]\n"
                + " FROM Q_USER WHERE USERID=? OR EVNID = ?";

        try {
            Map<String, Object> obj = jdbcTemplate.queryForMap(queryStringUser, userId, userId);
            if (!obj.isEmpty()) {
                sQ_User = new Q_User((String) obj.get("USERID"), (String) obj.get("USERNAME"),
                        (String) obj.get("PASSWORD"), (String) obj.get("DESCRIPT"), (String) obj.get("MOBILE"),
                        (String) obj.get("EMAIL"), (String) obj.get("OFFICEID"), (String) obj.get("USERID_DOMAIN"),
                        (String) obj.get("DOMAINID"), (Boolean) obj.get("ENABLE"), (String) obj.get("USER_CR_ID"),
                        (Date) obj.get("USER_CR_DTIME"), (String) obj.get("USER_MDF_ID"),
                        (Date) obj.get("USER_MDF_DTIME"), (String) obj.get("ORGID"), (String) obj.get("EVNID"), null);
                List<Q_Role> lstRole = new ArrayList<Q_Role>();
                if (userId.equals("admin")) {
                    Q_Role role = new Q_Role(ERole.ROLE_ADMIN.name(), "Nhóm quyền quản trị");
                    lstRole.add(role);
                    role = new Q_Role(ERole.ROLE_API.name(), "Nhóm quyền người dùng");
                    lstRole.add(role);
                    sQ_User.setRoles(lstRole);
                } else {
                    Q_Role role = new Q_Role(ERole.ROLE_API.name(), "Nhóm quyền người dùng");
                    lstRole.add(role);
                    sQ_User.setRoles(lstRole);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sQ_User;
    }

    @Override
    public List<FunctionGrant> getListFunctionGrantByUser(String userId) {
        List<FunctionGrant> lstResult = new ArrayList<>();
        FunctionGrant objResult;
        String queryString = "SELECT [FUNCTIONID]\n"
                + "      ,[FUNCTIONNAME],[LINK]\n"
                + "	,MAX(R_INSERT) R_INSERT,MAX(R_EDIT) R_EDIT,MAX(R_DELETE) R_DELETE FROM (SELECT A.[FUNCTIONID]\n"
                + "      ,[FUNCTIONNAME],[LINK]\n"
                + "	,1 R_INSERT,1 R_EDIT,1 R_DELETE\n"
                + "  FROM Q_FUNCTION A WHERE A.ENABLE=1 AND A.ISPUPLIC=1\n"
                + "UNION ALL\n"
                + "SELECT A.[FUNCTIONID]\n"
                + "      ,[FUNCTIONNAME],[LINK]\n"
                + "	,ISNULL(B.R_INSERT,0) R_INSERT,ISNULL(B.R_EDIT,0) R_EDIT,ISNULL(B.R_DELETE,0) R_DELETE\n"
                + "  FROM Q_FUNCTION A LEFT JOIN Q_PQFUNCTION_USER B ON (A.FUNCTIONID=B.FUNCTIONID)\n"
                + "  WHERE B.USERID=? AND A.ENABLE=1\n"
                + "UNION ALL\n"
                + "SELECT A.[FUNCTIONID]\n"
                + "      ,[FUNCTIONNAME],[LINK]\n"
                + "	,ISNULL(B.R_INSERT,0) R_INSERT,ISNULL(B.R_EDIT,0) R_EDIT,ISNULL(B.R_DELETE,0) R_DELETE\n"
                + "  FROM Q_FUNCTION A LEFT JOIN Q_PQFUNCTION_ROLE B ON (A.FUNCTIONID=B.FUNCTIONID)\n"
                + "  WHERE B.ROLEID IN (SELECT ROLEID FROM Q_USER_ROLE WHERE USERID=?) AND A.ENABLE=1) A\n"
                + "  GROUP BY [FUNCTIONID]\n"
                + "      ,[FUNCTIONNAME],[LINK]";

        try {
            List<Map<String, Object>> objResultFunction = jdbcTemplate.queryForList(queryString, userId, userId);
            if (!objResultFunction.isEmpty()) {

                for (Map<String, Object> obj : objResultFunction) {
                    objResult = new FunctionGrant((String) obj.get("FUNCTIONID"),
                            (String) obj.get("FUNCTIONNAME"),
                            (String) obj.get("LINK"),
                            (((Integer) obj.get("R_INSERT")) > 0),
                            (((Integer) obj.get("R_EDIT")) > 0),
                            (((Integer) obj.get("R_DELETE")) > 0), null);
                    lstResult.add(objResult);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return lstResult;
    }

    @Override
    public Boolean existsQ_UserById(String userId) {
        String s;
        s = "SELECT 1 FROM Q_USER WHERE USERID=?";
        int i = jdbcTemplate.update(s, userId);
        if (i <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public S_Organization getS_OrgByOrgid(String orgid) {
        S_Organization objResult = null;
        String queryString = " SELECT ORGID,ORGDESC,ORGORD FROM S_ORGANIZATION WHERE ORGID = ?";

        try {
            List<Map<String, Object>> objResultFunction = jdbcTemplate.queryForList(queryString, orgid);
            if (!objResultFunction.isEmpty()) {

                for (Map<String, Object> obj : objResultFunction) {
                    objResult = new S_Organization((String) obj.get("ORGID"), (String) obj.get("ORGDESC"),
                            (Integer) obj.get("ORGORD"));
                    return objResult;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objResult;
    }

    @Override
    public List<FunctionGrant> getListFunctionGrantByUserAndOrgIdView(String userId, String orgidView) {
        List<FunctionGrant> lstResult = new ArrayList<>();
        FunctionGrant objResult;
        String queryString = ""
                + " SELECT [FUNCTIONID],[FUNCTIONNAME],[LINK],MAX(R_INSERT) R_INSERT,MAX(R_EDIT) R_EDIT,MAX(R_DELETE) R_DELETE "
                + "               FROM (" + "					SELECT A.[FUNCTIONID]"
                + "				      ,[FUNCTIONNAME],[LINK] 	,1 R_INSERT,1 R_EDIT,1 R_DELETE\n"
                + "				  FROM Q_FUNCTION A \n"
                + "				  WHERE A.ENABLE=1 AND A.ISPUPLIC=1 AND FUNCTIONID IN (SELECT FUNCTIONID FROM Q_PQFUNCTION_ORG WHERE ORGID = ?)\n"
                + "				  UNION ALL" + ""
                + "               SELECT A.[FUNCTIONID]       ,[FUNCTIONNAME],[LINK]\n"
                + "					,ISNULL(B.R_INSERT,0) R_INSERT,ISNULL(B.R_EDIT,0) R_EDIT,ISNULL(B.R_DELETE,0) R_DELETE\n"
                + "				  FROM Q_FUNCTION A LEFT JOIN Q_PQFUNCTION_USER B ON (A.FUNCTIONID=B.FUNCTIONID)\n"
                + "				  WHERE B.USERID=? AND A.ENABLE=1  AND A.FUNCTIONID IN (SELECT FUNCTIONID FROM Q_PQFUNCTION_ORG WHERE ORGID = ?)\n"
                + "				  UNION ALL " + "" + "               SELECT A.[FUNCTIONID],[FUNCTIONNAME],[LINK]\n"
                + "					,ISNULL(B.R_INSERT,0) R_INSERT,ISNULL(B.R_EDIT,0) R_EDIT,ISNULL(B.R_DELETE,0) R_DELETE\n"
                + "				  FROM Q_FUNCTION A LEFT JOIN Q_PQFUNCTION_ROLE B ON (A.FUNCTIONID=B.FUNCTIONID)\n"
                + "				  WHERE B.ROLEID IN (SELECT ROLEID FROM Q_USER_ROLE WHERE USERID = ?) AND A.ENABLE=1\n"
                + "				  AND A.FUNCTIONID IN (SELECT FUNCTIONID FROM Q_PQFUNCTION_ORG WHERE ORGID = ?)\n"
                + "				  ) A " + "				  "
                + "               GROUP BY [FUNCTIONID]       ,[FUNCTIONNAME],[LINK]";

        try {
            List<Map<String, Object>> objResultFunction = jdbcTemplate.queryForList(queryString, orgidView, userId,
                    orgidView, userId, orgidView);
            if (!objResultFunction.isEmpty()) {

                for (Map<String, Object> obj : objResultFunction) {
                    objResult = new FunctionGrant((String) obj.get("FUNCTIONID"), (String) obj.get("FUNCTIONNAME"),
                            (String) obj.get("LINK"), (((Integer) obj.get("R_INSERT")) > 0),
                            (((Integer) obj.get("R_EDIT")) > 0), (((Integer) obj.get("R_DELETE")) > 0), null);
                    lstResult.add(objResult);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return lstResult;
    }

}
