package com.oreumio.james.rest.filter;

import com.oreumio.james.rest.user.EmlUserRoleService;
import com.oreumio.james.rest.user.EmlUserRoleVo;
import com.oreumio.james.rest.user.EmlUserService;
import com.oreumio.james.rest.user.EmlUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jchoi on 1/2/16.
 */
public class MyUserDetailsService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    private EmlUserService userService;

    @Autowired
    private EmlUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        logger.debug("사용자 인증을 위한 정보를 취득합니다.: username=" + username);

        int i = username.indexOf("@");

        if (i > -1) {
            final EmlUserVo userVo = userService.getByName(username.substring(0, i), username.substring(i + 1));
            final List<EmlUserRoleVo> userRoleVoList = userRoleService.list(userVo.getId());

            logger.debug("사용자 정보를 취득했습니다.: " + userVo);

            return new UserDetails() {

                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
                    for (EmlUserRoleVo userRoleVo : userRoleVoList) {
                        grantedAuthorityList.add(new SimpleGrantedAuthority(userRoleVo.getRoleId()));
                    }
                    return grantedAuthorityList;
                }

                @Override
                public String getPassword() {
                    return userVo.getPassword();
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
                    return true;
                }
            };
        }

        throw new UsernameNotFoundException(username);
    }
}
