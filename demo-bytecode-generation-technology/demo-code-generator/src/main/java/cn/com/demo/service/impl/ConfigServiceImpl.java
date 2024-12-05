package cn.com.demo.service.impl;

import cn.com.demo.entity.Config;
import cn.com.demo.mapper.ConfigMapper;
import cn.com.demo.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Author
 * @since 2024-06-20
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
