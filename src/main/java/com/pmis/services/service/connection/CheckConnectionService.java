package com.pmis.services.service.connection;

import com.pmis.util.Util;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class CheckConnectionService {
    public Integer checkConnect(String ip, String port, String userName, String password, String dbName, boolean decrypt) {
        if (ip == null || ip.isEmpty() || port == null || port.isEmpty() || userName == null || userName.isEmpty() || password == null || password.isEmpty() || dbName == null || dbName.isEmpty()) {
            return -2;
        }
        try {
            Connection conn = Util.getConnection(ip, port, dbName, userName, decrypt ? Util.decrypt(password) : password, "false", "false", "TLSv1.2");
            if (conn.isValid(0)) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return switch (e.getErrorCode()) {
                case 0 -> -4; //Không kết nối được tới hệ thống máy chủ
                case 18456 -> -5; //Sai tên đăng nhập mật khẩu
                case 4060 -> -6;
                default -> 0; //Tên cơ sở dữ liệu không tồn tại
            };
        } finally {
            Util.closeConnection();
        }
    }
}
