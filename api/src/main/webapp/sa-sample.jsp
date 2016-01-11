<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="jquery-2.1.4.js"></script>
<script>
    var groupId, groupDomainName = 'localhost:8083';
    var username = "sa";
    var password = "letmein";

    $( document ).ready(function() {

        $('#test').click(function () {
            get_system();
            post_system_getQuotaUsage();
            get_clients();
            var clientId = post_clients();
            get_client(clientId);
            post_client_quota_usage(clientId);
            post_client_display_name(clientId, '새 오름아이오 테스트');
            post_client_quota(clientId, 60000);
            post_client_state(clientId, "R");
            get_client_domains(clientId);
            var domainId = post_client_domains(clientId, 'foo.oreumio.com');
            delete_client_domains(clientId, domainId);
            delete_client(clientId);

            return false;
        });
    });

    function get_system() {
        console.log('get_system');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/system.do",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_system_getQuotaUsage() {
        console.log('post_system_getQuotaUsage');

        var data = {
        };

        $.ajax({
            method: 'post',
            url: "/system/getQuotaUsage.do",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function get_clients() {
        console.log('get_clients');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/clients.do",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_clients() {
        console.log('post_clients');

        var clientId;

        var data = {
            displayName: '오름아이오 테스트',
            host: 'localhost:8083',
            userName: 'oreumio-sa',
            password: 'letmein',
            alg:'PLAIN',
            quota: 50000,
            state: 'N'
        };

        $.ajax({
            method: 'post',
            url: "/clients.do",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
                clientId = r.id;
            }
        });

        return clientId;
    }

    function get_client(id) {
        console.log('get_client');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/clients/" + id + ".do",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function delete_client(clientId) {
        console.log('delete_client');

        var data = {
        };

        $.ajax({
            method: 'delete',
            url: '/clients/' + clientId + '.do',
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_client_quota_usage(id) {
        console.log('post_client_quota_usage');

        var data = {
        };

        $.ajax({
            method: 'post',
            url: "/clients/" + id + "/getQuotaUsage.do",
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_client_display_name(id, name) {
        console.log('post_client_display_name');

        var data = {
            displayName: name
        };

        $.ajax({
            method: 'post',
            url: "/clients/" + id + "/changeDisplayName.do",
            dataType: 'json',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_client_quota(id, quota) {
        console.log('post_client_quota');

        var data = {
            quota: quota
        };

        $.ajax({
            method: 'post',
            url: "/clients/" + id + "/changeQuota.do",
            dataType: 'json',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_client_state(id, state) {
        console.log('post_client_state');

        var data = {
            state: state
        };

        $.ajax({
            method: 'post',
            url: "/clients/" + id + "/changeState.do",
            dataType: 'json',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function get_client_domains(clientId) {
        console.log('get_client_domains');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: '/clients/' + clientId + '/domains.do',
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }

    function post_client_domains(clientId, domain) {
        console.log('post_client_domains');

        var domainId;

        var data = {
            domainName: domain
        };

        $.ajax({
            method: 'post',
            url: '/clients/' + clientId + '/domains.do',
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
                domainId = r.id;
            }
        });

        return domainId;
    }

    function delete_client_domains(clientId, domainId) {
        console.log('delete_client_domains');

        var data = {
        };

        $.ajax({
            method: 'delete',
            url: '/clients/' + clientId + '/domains/' + domainId + '.do',
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
            },
            success: function (r) {
                console.log(r);
            }
        });
    }
</script>
<body>
<h2>오름아이오 James SA API 테스트!</h2>
<p>
<input type="button" id="test" value="테스트" />
</p>
</body>
</html>
