package com.oreumio.james.rest.filter;

import com.oreumio.james.rest.group.EmlGroupService;
import com.oreumio.james.rest.group.EmlGroupVo;
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
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class GroupUserDetailsService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(GroupUserDetailsService.class);

    private static List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();

    @Autowired
    private EmlGroupService groupService;

    public GroupUserDetailsService() {
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_GA"));
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        logger.debug("그룹 관리자 인증을 위한 정보를 취득합니다.: username=" + username);

        int i = username.indexOf("@");

        if (i > -1) {
            try {
                final EmlGroupVo groupVo = groupService.getByName(username.substring(0, i), username.substring(i + 1));

                logger.debug("그룹 정보를 취득했습니다.: " + groupVo);

                return new UserDetails() {

                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return grantedAuthorityList;
                    }

                    @Override
                    public String getPassword() {
                        return groupVo.getPassword();
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
            } catch (Exception e) {
                logger.warn("해당하는 사용자가 없습니다.", e);
            }
        }

        throw new UsernameNotFoundException(username);
    }
}
