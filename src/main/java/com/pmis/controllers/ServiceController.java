package com.pmis.controllers;

import com.pmis.payload.dto.ConnectionConfigDTO;
import com.pmis.services.service.connection.CheckConnectionService;
import com.pmis.services.service.connection.ConnectionConfigService;
import com.pmis.services.service.common.ConnectionParamService;
import com.pmis.services.service.common.ParamLoginService;
import com.pmis.services.service.common.ParamNoLoginService;
import com.pmis.services.service.common.ParamService;
import com.pmis.services.service.data_exploitation.KhaiThacDuLieuExcelService;
import com.pmis.services.service.data_exploitation.KhaiThacDuLieuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmis.payload.request.ExecServiceRequest;
import com.pmis.payload.response.service.ExecServiceResponse;
import com.pmis.security.services.SecurityUtils;
import com.pmis.services.service.ServiceService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pmis.constant.Constant;

//@CrossOrigin(origins = "*", maxAge = 36000)
@RestController
@RequestMapping("/api/service")
public class ServiceController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ServiceService serviceService;

    @Autowired
    private ConnectionConfigService connectionConfigService;

    @Autowired
    private ParamService paramService;

    @Autowired
    private ParamNoLoginService paramNoLoginService;

    @Autowired
    private ParamLoginService paramLoginService;

    @Autowired
    private ConnectionParamService connectionParamService;

    @Autowired
    private CheckConnectionService checkConnectionService;

    @Autowired
    private KhaiThacDuLieuExcelService khaiThacDuLieuExcelService;

    @Autowired
    private KhaiThacDuLieuService khaiThacDuLieuService;

    @RequestMapping(value = "/execServiceNoLogin", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> execServiceNoLogin(@Valid @RequestBody ExecServiceRequest execServiceRequest) {
        try {
            if (execServiceRequest.getServiceId().contains("APIC-NL")) {
                return ResponseEntity.ok().body(APICustomNoLogin(execServiceRequest));
            } else {
                Map<String, Object> objService = serviceService.getServiceInfo(execServiceRequest.getServiceId());
                if (objService == null || !objService.containsKey("ENABLE") || objService.get("ENABLE") == null || !(Boolean) objService.get("ENABLE")) {
                    return ResponseEntity.ok().body(new ExecServiceResponse(null, 0, "Dịch vụ không tồn tại"));
                }
                if (objService != null && objService.containsKey("IS_PUBLIC") && (Boolean) objService.get("IS_PUBLIC")) {
                    List<Map<String, Object>> objServiceInput = serviceService.getServiceInputInfo(execServiceRequest.getServiceId());
                    if (!paramService.checkParameterInput(objServiceInput, execServiceRequest.getParameters())) {
                        return ResponseEntity.ok().body(new ExecServiceResponse(null, 0, "Tham số đầu vào không đúng"));
                    } else {
                        Map<String, Object> dataServiceResult = serviceService.execService(objService, paramNoLoginService.buildParameterNoLogin(objServiceInput, execServiceRequest.getParameters()));
                        if ((Integer) dataServiceResult.get("Status") == 1) {
                            return ResponseEntity.ok().body(new ExecServiceResponse(dataServiceResult.get("data"), 1, "Xử lý thành công"));
                        } else {
                            return ResponseEntity.ok().body(new ExecServiceResponse(dataServiceResult.get("data"), 0, dataServiceResult.get("Message").toString()));
                        }
                    }
                } else {
                    return ResponseEntity.ok().body(new ExecServiceResponse(null, 409, "Không có quyền thực hiện"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(new ExecServiceResponse(null, 0, "Xảy ra lỗi hệ thống"));
        }

    }

    private ExecServiceResponse APICustomNoLogin(ExecServiceRequest execServiceRequest) {
        if (execServiceRequest.getServiceId().equals("APIC-NL-1")) {
            ConnectionConfigDTO connectionConfigDTO = connectionParamService.convertParamsToConnDTO(execServiceRequest.getParameters());
            if (connectionConfigDTO.MA_KETNOI == null || connectionConfigDTO.MA_KETNOI.isEmpty()) {
                return new ExecServiceResponse(null, 0, "Tham số đầu vào không chính xác");
            }
            String dbConnPwd = connectionConfigService.findPasswordByMAKETNOI(connectionConfigDTO.MA_KETNOI);
            if (dbConnPwd != null) {
                if (connectionConfigDTO.MAT_KHAU != null && !connectionConfigDTO.MAT_KHAU.isEmpty()) {
                    return new ExecServiceResponse(
                            null,
                            checkConnectionService.checkConnect(
                                    connectionConfigDTO.IP,
                                    connectionConfigDTO.PORT,
                                    connectionConfigDTO.TEN_DANG_NHAP,
                                    connectionConfigDTO.MAT_KHAU,
                                    connectionConfigDTO.TEN_CSDL,
                                    false
                            ),
                            "");
                } else {
                    return new ExecServiceResponse(
                            null,
                            checkConnectionService.checkConnect(
                                    connectionConfigDTO.IP,
                                    connectionConfigDTO.PORT,
                                    connectionConfigDTO.TEN_DANG_NHAP,
                                    dbConnPwd,
                                    connectionConfigDTO.TEN_CSDL,
                                    true
                            ),
                            "");
                }
            } else {
                return new ExecServiceResponse(
                        null,
                        checkConnectionService.checkConnect(
                                connectionConfigDTO.IP,
                                connectionConfigDTO.PORT,
                                connectionConfigDTO.TEN_DANG_NHAP,
                                connectionConfigDTO.MAT_KHAU,
                                connectionConfigDTO.TEN_CSDL,
                                false
                        ),
                        "");
            }
        }
        return new ExecServiceResponse(null, 0, "Dịch vụ không tồn tại");

    }

    private ExecServiceResponse APICustomLogin(ExecServiceRequest execServiceRequest) {
        return switch (execServiceRequest.getServiceId()) {
            case "APIC-L-1" ->
                    new ExecServiceResponse(khaiThacDuLieuService.exec_APIC_L_1(execServiceRequest), 1, "Xử lý thành công");
            case "APIC-L-2" ->
                    new ExecServiceResponse(khaiThacDuLieuExcelService.exec_APIC_L_2(execServiceRequest), 1, "Xử lý thành công");
            default -> new ExecServiceResponse(null, 0, "Dịch vụ không tồn tại");
        };
    }

    @RequestMapping(value = "/execServiceLogin", method = RequestMethod.POST, produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> execServiceLogin(@Valid @RequestBody ExecServiceRequest execServiceRequest) {
        try {
            if (execServiceRequest.getServiceId().contains("APIC-")) {
                return ResponseEntity.ok().body(APICustomLogin(execServiceRequest));
            } else {
                Map<String, Object> objService = serviceService.getServiceInfo(execServiceRequest.getServiceId());
                if (objService == null || !objService.containsKey("ENABLE") || objService.get("ENABLE") == null || !(Boolean) objService.get("ENABLE")) {
                    return ResponseEntity.ok().body(new ExecServiceResponse(null, 0, "Dịch vụ không tồn tại"));
                }
                boolean bCheckGrant = false;
                if (objService != null && objService.containsKey("IS_PUBLIC") && (Boolean) objService.get("IS_PUBLIC")) {
                    bCheckGrant = true;
                } else {
                    bCheckGrant = serviceService.checkAuthServiceInfo(objService.get("API_SERVICEID").toString(), SecurityUtils.getPrincipal().getUserId());
                }
                if (bCheckGrant) {
                    List<Map<String, Object>> objServiceInput = serviceService.getServiceInputInfo(execServiceRequest.getServiceId());
                    if (!paramService.checkParameterInput(objServiceInput, execServiceRequest.getParameters())) {
                        return ResponseEntity.ok().body(new ExecServiceResponse(null, 0, "Tham số đầu vào không đúng"));
                    } else {
                        String serviceId = execServiceRequest.getServiceId();
                        if (
                                serviceId.equals(Constant.DS_KET_NOI_SID)
                                || serviceId.equals(Constant.KET_NOI_BY_ID_SID)
                                || serviceId.equals(Constant.TAO_KET_NOI_SID)
                                || serviceId.equals(Constant.CAP_NHAT_KET_NOI_SID)
                                || serviceId.equals(Constant.XOA_KET_NOI_SID)
                        ) {
                            return ResponseEntity.ok().body(xuLyKetNoi(execServiceRequest));
                        }
                        Map<String, Object> dataServiceResult = serviceService.execService(objService, paramLoginService.buildParameterLogin(objServiceInput, execServiceRequest.getParameters()));
                        if ((Integer) dataServiceResult.get("Status") == 1) {
                            return ResponseEntity.ok().body(new ExecServiceResponse(dataServiceResult.get("data"), 1, "Xử lý thành công"));
                        } else {
                            return ResponseEntity.ok().body(new ExecServiceResponse(dataServiceResult.get("data"), 0, dataServiceResult.get("Message").toString()));
                        }
                    }
                } else {
                    return ResponseEntity.ok().body(new ExecServiceResponse(null, 409, "Không có quyền thực hiện"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(new ExecServiceResponse(null, 0, "Xảy ra lỗi hệ thống"));
        }
    }

    private ExecServiceResponse xuLyKetNoi(ExecServiceRequest request) {
        try {
            String serviceId = request.getServiceId();
            switch (serviceId) {
                case Constant.DS_KET_NOI_SID -> {
                    return new ExecServiceResponse(connectionConfigService.findAll(), 1, "");
                }
                case Constant.KET_NOI_BY_ID_SID -> {
                    String id = request.getParameters().get(0).getValue().toString();
                    ConnectionConfigDTO data = connectionConfigService.findByMAKETNOI(id);
                    if (data == null) {
                        return new ExecServiceResponse(null, 0, "Không tìm thấy thông tin kết nối.");
                    }
                    return new ExecServiceResponse(data, 1, "");
                }
                case Constant.TAO_KET_NOI_SID -> {
                    ConnectionConfigDTO connectionConfigDTO = connectionParamService.convertParamsToConnDTO(request.getParameters());
                    connectionConfigDTO.USER_CR_DTIME = new Date();
                    String id = connectionConfigService.save(connectionConfigDTO, false);
                    if (id != null) {
                        return new ExecServiceResponse(id, 1, "Thêm mới thông tin kết nối thành công");
                    }
                    return new ExecServiceResponse(null, 0, "Thêm thông tin kết nối không thành công");
                }
                case Constant.CAP_NHAT_KET_NOI_SID -> {
                    ConnectionConfigDTO connectionConfigDTO = connectionParamService.convertParamsToConnDTO(request.getParameters());
                    connectionConfigDTO.USER_MDF_DTIME = new Date();
                    String id = connectionConfigService.save(connectionConfigDTO, true);
                    if (id != null) {
                        return new ExecServiceResponse(1, 1, "Cập nhật thông tin kết nối thành công");
                    }
                    return new ExecServiceResponse(null, 0, "Cập nhật thông tin kết nối không thành công");
                }
                default -> {
                    ConnectionConfigDTO connectionConfigDTO = connectionParamService.convertParamsToConnDTO(request.getParameters());
                    Integer data = 0;
                    if (connectionConfigDTO.MA_KETNOI != null && !connectionConfigDTO.MA_KETNOI.isEmpty()) {
                        data = connectionConfigService.deleteByMAKETNOI(connectionConfigDTO.MA_KETNOI);
                    }
                    if (data != 0) {
                        return new ExecServiceResponse(1, 0, "Đã xóa kết nối");
                    }
                    return new ExecServiceResponse(null, 0, "Xóa kết nối không thành công.");
                }
            }
        } catch (Exception e) {
            return new ExecServiceResponse(null, 0, "Xử lý thông tin không thành công. " + e.getMessage());
        }
    }
}
