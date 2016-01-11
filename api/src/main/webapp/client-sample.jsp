<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="jquery-2.1.4.js"></script>
<script>
    var groupDomainName = 'localhost:8083';
    var username = "oreumio";
    var password = "letmein";

    var auth = "Basic " + btoa(username + ":" + password);
    console.log(auth);

    $( document ).ready(function() {

        $('#test').click(function () {
            get_client();
            post_client_getQuotaUsage();
            get_groups();
            var groupId = post_groups();
            get_group(groupId);
            post_group_quota_usage(groupId);
//            post_group_display_name(groupId, '새 그룹');
            post_group_quota(groupId, 60000);
            post_group_suspend(groupId);
            post_group_resume(groupId);
            get_group_domains(groupId);
            var domainId = post_group_domains(groupId, 'foo.oreumio.com');
            get_group_sec_domains(groupId, domainId);
            var secDomainId = post_group_sec_domains(groupId, domainId, 'foo.oreumio.com');
            delete_group_sec_domains(groupId, domainId, secDomainId);
            delete_group_domains(groupId, domainId);
            delete_group(groupId);

            return false;
        });
    });

    function get_client() {
        console.log('get_client');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/client.do",
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

    function post_client_getQuotaUsage() {
        console.log('post_client_getQuotaUsage');

        var data = {
        };

        $.ajax({
            method: 'post',
            url: "/client/getQuotaUsage.do",
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

    function get_groups() {
        console.log('get_groups');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/groups.do",
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

    function post_groups() {
        console.log('post_groups');

        var groupId;

        var data = {
            displayName: '그룹 테스트',
            host: 'localhost:8083',
            userName: 'oreumio-client',
            password: 'letmein',
            alg:'PLAIN',
            quota: 10000,
            state: 'N'
        };

        $.ajax({
            method: 'post',
            url: "/groups.do",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
                groupId = r.id;
            }
        });

        return groupId;
    }

    function get_group(id) {
        console.log('get_group');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/groups/" + id + ".do",
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

    function delete_group(groupId) {
        console.log('delete_group');

        var data = {
        };

        $.ajax({
            method: 'delete',
            url: '/groups/' + groupId + '.do',
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

    function post_group_quota_usage(id) {
        console.log('post_group_quota_usage');

        var data = {
        };

        $.ajax({
            method: 'post',
            url: "/groups/" + id + "/getQuotaUsage.do",
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

    function post_group_display_name(id, name) {
        console.log('post_group_display_name');

        var data = {
            displayName: name
        };

        $.ajax({
            method: 'post',
            url: "/groups/" + id + "/changeDisplayName.do",
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

    function post_group_quota(id, quota) {
        console.log('post_group_quota');

        var data = {
            quota: quota
        };

        $.ajax({
            method: 'post',
            url: "/groups/" + id + "/changeQuota.do",
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

    function post_group_suspend(id) {
        console.log('post_group_suspend');

        var data = {
        };

        $.ajax({
            method: 'post',
            url: "/groups/" + id + "/suspend.do",
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

    function post_group_resume(id) {
        console.log('post_group_resume');

        var data = {
        };

        $.ajax({
            method: 'post',
            url: "/groups/" + id + "/resume.do",
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

    function get_group_domains(groupId) {
        console.log('get_group_domains');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: '/groups/' + groupId + '/domains.do',
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

    function post_group_domains(groupId, domain) {
        console.log('post_group_domains');

        var domainId;

        var data = {
            domainName: domain
        };

        $.ajax({
            method: 'post',
            url: '/groups/' + groupId + '/domains.do',
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
                domainId = r.id;
            }
        });

        return domainId;
    }

    function delete_group_domains(groupId, domainId) {
        console.log('delete_group_domains');

        var data = {
        };

        $.ajax({
            method: 'delete',
            url: '/groups/' + groupId + '/domains/' + domainId + '.do',
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
<h2>오름아이오 James 고객 API 테스트!</h2>
<p>
<input type="button" id="test" value="테스트" />
</p>
</body>
</html>
