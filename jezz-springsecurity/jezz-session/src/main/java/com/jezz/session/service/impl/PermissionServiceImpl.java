package com.jezz.session.service.impl;

import com.jezz.session.domain.po.TbPermissionPO;
import com.jezz.session.domain.po.TbPermissionPOExample;
import com.jezz.session.mapper.TbPermissionPOMapper;
import com.jezz.session.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private TbPermissionPOMapper tbPermissionPOMapper;


    @Override
    public Map<String, Object> queryAllPerssions() {
        Map<String, Object> allPermissionsMap = new HashMap<>();
        TbPermissionPOExample example = new TbPermissionPOExample();
        example.createCriteria();
        List<TbPermissionPO> permissions = tbPermissionPOMapper.selectByExample(example);
        List<TbPermissionPO> collections = permissions.stream().filter(n -> !StringUtils.isEmpty(n.getUrl())).collect(Collectors.toList());
        if (collections != null && !collections.isEmpty()) {
            Map<String, List<TbPermissionPO>> map = collections.stream().collect(Collectors.groupingBy(TbPermissionPO::getUrl));
            for (String url : map.keySet()) {
                List<TbPermissionPO> list = map.get(url);
                Collection<ConfigAttribute> collection = new ArrayList<>(list.size());
                for (TbPermissionPO permission : list) {
                    ConfigAttribute cfg = new SecurityConfig(permission.getAuthority());
                    collection.add(cfg);
                }
                allPermissionsMap.put(url, collection);
            }
        }
        return allPermissionsMap;
    }
}
