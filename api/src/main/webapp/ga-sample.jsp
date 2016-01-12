<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="jquery-2.1.4.js"></script>
<script>
    var groupDomainName = 'localhost:8083';
    var username = "oreumio-ga";
    var password = "letmein";

    var auth = "Basic " + btoa(username + ":" + password);
    console.log(auth);

    $( document ).ready(function() {

        $('#test').click(function () {
            // 그룹 정보 얻기
            get_group();
            // 그룹 사용량 실적 얻기
            post_group_get_quota_usage();
            // 그룹 설정 변경하기
            post_group_change_config();
            // 사용자 검색
            get_users();
            // 사용자 등록
            var userId = post_users();
            // 사용자 정보 얻기
            get_user(userId);
            // 사용자 정지
            post_user_suspend(userId);
            // 사용자 재개
            post_user_resume(userId);
            // 사용자 사용량 실적 얻기
            post_user_get_quota_usage(userId);
//            post_user_display_name(userId, '새 그룹');
            // 사용자 쿼타 변경
            post_user_change_quota(userId, 60000);
            // 사용자의 역할 검색
            get_user_roles(userId);
            // 사용자에게 역할 부여
            var roleId = post_user_roles(userId);
            // 사용자로부터 역할 박탈
            delete_user_roles(userId, roleId);
            // 조직 검색
            get_orgs();
            // 조직 등록
            var orgId = post_orgs();
            // 조직 정보 얻기
            get_org(orgId);
            // 조직 정보 삭제
            delete_org(orgId);
            var parentOrgUnitId;
            // 조직 단위 검색
            get_org_units(orgId, parentOrgUnitId);
            // 조직 단위 등록
            var orgUnitId = post_org_units(orgId, parentOrgUnitId);
            // 조직 단위 정보 얻기
            get_org_unit(orgId, orgUnitId);
            // 조직 단위 삭제
            delete_org_unit(orgId, orgUnitId);
            // 조직 단위 사용자 검색
            get_org_unit_members(orgId, orgUnitId);
            // 조직 단위 사용자 등록
            var memberId = post_org_unit_members(orgId, orgUnitId, username);
            // 조직 단위 사용자 삭제
            get_org_unit_member(orgId, orgUnitId, memberId);
            // 조직 단위 사용자 삭제
            delete_org_unit_member(orgId, orgUnitId, memberId);
            // 사용자 삭제
            delete_user(userId);

            return false;
        });
    });

    function get_group() {
        console.log('get_group');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/group.do",
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_group_get_quota_usage() {
        console.log('post_group_get_quota_usage');

        var data = {
        };

        $.ajax({
            method: 'post',
            url: "/group/getQuotaUsage.do",
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_group_change_config() {
        console.log('post_group_change_config');

    }

    function get_users() {
        console.log('get_users');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/users.do?pageNo=1&pageSize=20",
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_users() {
        console.log('post_users');

        var userId;

        var data = {
            displayName: '사용자',
            host: 'localhost:8083',
            userName: 'oreumio-user',
            password: 'letmein',
//            alg:'PLAIN',
            quota: 10000,
            socketEnabled: 'N',
            state: 'N'
        };

        $.ajax({
            method: 'post',
            url: "/users.do",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
                userId = r.id;
            }
        });

        return userId;
    }

    function get_user(id) {
        console.log('get_user');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/users/" + id + ".do",
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function delete_user(userId) {
        console.log('delete_user');

        var data = {
        };

        $.ajax({
            method: 'delete',
            url: '/users/' + userId + '.do',
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_user_get_quota_usage(id) {
        console.log('post_user_get_quota_usage');

        var data = {
        };

        $.ajax({
            method: 'post',
            url: "/users/" + id + "/getQuotaUsage.do",
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_user_change_display_name(id, name) {
        console.log('post_user_change_display_name');

        var data = {
            displayName: name
        };

        $.ajax({
            method: 'post',
            url: "/users/" + id + "/changeDisplayName.do",
            dataType: 'json',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_user_change_quota(id, quota) {
        console.log('post_user_change_quota');

        var data = {
            quota: quota
        };

        $.ajax({
            method: 'post',
            url: "/users/" + id + "/changeQuota.do",
            dataType: 'json',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_user_suspend(id) {
        console.log('post_user_suspend');

        var data = {
        };

        $.ajax({
            method: 'post',
            url: "/users/" + id + "/suspend.do",
            dataType: 'json',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_user_resume(id) {
        console.log('post_user_resume');

        var data = {
        };

        $.ajax({
            method: 'post',
            url: "/users/" + id + "/resume.do",
            dataType: 'json',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function get_user_roles(userId) {
        console.log('get_user_roles');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: '/users/' + userId + '/roles.do',
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_user_roles(userId, role) {
        console.log('post_user_roles');

        var roleId;

        var data = {
            roleId: 'ROLE_USER'
        };

        $.ajax({
            method: 'post',
            url: '/users/' + userId + '/roles.do',
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
                roleId = r.id;
            }
        });

        return roleId;
    }

    function delete_user_roles(userId, roleId) {
        console.log('delete_user_roles');

        var data = {
        };

        $.ajax({
            method: 'delete',
            url: '/users/' + userId + '/roles/' + roleId + '.do',
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function get_orgs() {
        console.log('get_orgs');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: '/orgs.do',
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_orgs() {
        console.log('post_orgs');

        var orgId;

        var data = {
            displayName: '조직'
        };

        $.ajax({
            method: 'post',
            url: "/orgs.do",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
                orgId = r.id;
            }
        });

        return orgId;
    }

    function get_org(orgId) {
        console.log('get_org');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/orgs/" + orgId + ".do",
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function delete_org(orgId) {
        console.log('delete_org');

        var data = {
        };

        $.ajax({
            method: 'delete',
            url: '/orgs/' + orgId + '.do',
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function get_org_units(orgId, parentOrgUnitId) {
        console.log('get_org_units');

        var data = {
            parentOrgUnitId: parentOrgUnitId
        };

        $.ajax({
            method: 'get',
            url: '/orgs/' + orgId + '/orgUnits.do',
            dataType: 'json',
            data: data,
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_org_units(orgId, parentOrgUnitId) {
        console.log('post_org_units');

        var orgUnitId;

        var data = {
            parentOrgUnitId: parentOrgUnitId,
            displayName: '조직'
        };

        $.ajax({
            method: 'post',
            url: '/orgs/' + orgId + '/orgUnits.do',
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
                orgUnitId = r.id;
            }
        });

        return orgUnitId;
    }

    function get_org_unit(orgId, orgUnitId) {
        console.log('get_org_unit');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/orgs/" + orgId + "/orgUnits/" + orgUnitId + ".do",
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function delete_org_unit(orgId, orgUnitId) {
        console.log('delete_org_unit');

        var data = {
        };

        $.ajax({
            method: 'delete',
            url: "/orgs/" + orgId + "/orgUnits/" + orgUnitId + ".do",
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function get_org_unit_members(orgId, orgUnitId) {
        console.log('get_org_unit_members');

        var data = {
            inclusive: false
        };

        $.ajax({
            method: 'get',
            url: "/orgs/" + orgId + "/orgUnits/" + orgUnitId + "/members.do",
            dataType: 'json',
            data: data,
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_org_unit_members(orgId, orgUnitId) {
        console.log('post_org_unit_members');

        var orgUnitMemberId;

        var data = {
            userId: 'user-id'
        };

        $.ajax({
            method: 'post',
            url: "/orgs/" + orgId + "/orgUnits/" + orgUnitId + "/members.do",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
                orgUnitMemberId = r.id;
            }
        });

        return orgUnitMemberId;
    }

    function get_org_unit_member(orgId, orgUnitId, orgUnitMemberId) {
        console.log('get_org_unit_member');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/orgs/" + orgId + "/orgUnits/" + orgUnitId + "/members/" + orgUnitMemberId + ".do",
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function delete_org_unit_member(orgId, orgUnitId, orgUnitMemberId) {
        console.log('delete_org_unit_member');

        var data = {
        };

        $.ajax({
            method: 'delete',
            url: "/orgs/" + orgId + "/orgUnits/" + orgUnitId + "/members/" + orgUnitMemberId + ".do",
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function get_group_sec_domains(groupId, domainId) {
        console.log('get_group_sec_domains');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: '/groups/' + groupId + '/domains/' + domainId + '/secDomains.do',
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_group_sec_domains(groupId, domainId, domain) {
        console.log('post_group_sec_domains');

        var secDomainId;

        var data = {
            domainName: domain
        };

        $.ajax({
            method: 'post',
            url: '/groups/' + groupId + '/domains/' + domainId + '/secDomains.do',
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
                secDomainId = r.id;
            }
        });

        return secDomainId;
    }

    function delete_group_sec_domains(groupId, domainId, secDomainId) {
        console.log('delete_group_sec_domains');

        var data = {
        };

        $.ajax({
            method: 'delete',
            url: '/groups/' + groupId + '/domains/' + domainId + '/secDomains/' + secDomainId + '.do',
            dataType: 'json',
//            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
            }
        });
    }
</script>
<body>
<h2>오름아이오 James GA API 테스트!</h2>
<p>
<input type="button" id="test" value="테스트" />
</p>
</body>
</html>
