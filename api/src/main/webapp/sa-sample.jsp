<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="jquery-2.1.4.js"></script>
<script>
    var groupId, groupDomainName = 'localhost:8083';
    var username = "sa";
    var password = "letmein";

    var auth = "Basic " + btoa(username + ":" + password);
    console.log(auth);

    $( document ).ready(function() {

        $('#test').click(function () {
            // 시스템 정보 구하기
            get_system();
            // 시스템 사용량 실적 구하기
            post_system_getQuotaUsage();
            // 고객 검색
            get_clients();
            // 고객 등록
            var clientId = post_clients();
            // 고객 정보 구하기
            get_client(clientId);
            // 고객 사용량 실적 구하기
            post_client_quota_usage(clientId);
            // 고객 표시명 변경
            post_client_display_name(clientId, '새 오름아이오 테스트');
            // 고객 쿼타 변경
            post_client_quota(clientId, 60000);
            // 고객 상태 변경
            post_client_state(clientId, "R");
            // 고객 도메인 구하기
            get_client_domains(clientId);
            // 고객 도메인 등록
            var domainId = post_client_domains(clientId, 'foo.oreumio.com');
            // 고객 도메인 삭제
            delete_client_domains(clientId, domainId);
            // 고객 삭제
            delete_client(clientId);

            return false;
        });
    });

    /**
     * 시스템 정보 구하기
     */
    function get_system() {
        console.log('get_system');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/system.do",
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

    /**
     * 시스템 쿼타 사용실적 구하기
     */
    function post_system_getQuotaUsage() {
        console.log('post_system_getQuotaUsage');

        var data = {
        };

        $.ajax({
            method: 'post',
            url: "/system/getQuotaUsage.do",
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

    /**
     * 고객 검색
     */
    function get_clients() {
        console.log('get_clients');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/clients.do",
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

    /**
     * 고객 등록
     *
     * @returns 고객 아이디
     */
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
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
                clientId = r.id;
            }
        });

        return clientId;
    }

    /**
     * 고객 정보 구하기
     *
     * @param clientId 고객 아이디
     */
    function get_client(clientId) {
        console.log('get_client');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: "/clients/" + clientId + ".do",
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

    /**
     * 고객 삭제
     *
     * @param clientId 고객 아이디
     */
    function delete_client(clientId) {
        console.log('delete_client');

        var data = {
        };

        $.ajax({
            method: 'delete',
            url: '/clients/' + clientId + '.do',
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

    /**
     * 고객 쿼타 사용실적 구하기
     *
     * @param clientId 고객 아이디
     */
    function post_client_quota_usage(clientId) {
        console.log('post_client_quota_usage');

        var data = {
        };

        $.ajax({
            method: 'post',
            url: "/clients/" + clientId + "/getQuotaUsage.do",
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

    /**
     * 고객 표시명 변경
     *
     * @param clientId 고객 아이디
     * @param name 새로운 고객 표시명
     */
    function post_client_display_name(clientId, name) {
        console.log('post_client_display_name');

        var data = {
            displayName: name
        };

        $.ajax({
            method: 'post',
            url: "/clients/" + clientId + "/changeDisplayName.do",
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

    /**
     * 고객 쿼타 변경
     *
     * @param clientId 고객 아이디
     * @param quota 새로운 쿼타
     */
    function post_client_quota(clientId, quota) {
        console.log('post_client_quota');

        var data = {
            quota: quota
        };

        $.ajax({
            method: 'post',
            url: "/clients/" + clientId + "/changeQuota.do",
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

    /**
     * 고객의 상태 변경
     *
     * @param clientId 고객 아이디
     * @param state 새로운 상태
     */
    function post_client_state(clientId, state) {
        console.log('post_client_state');

        var data = {
            state: state
        };

        $.ajax({
            method: 'post',
            url: "/clients/" + clientId + "/changeState.do",
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

    /**
     * 고객의 도메인 검색
     *
     * @param clientId 고객 아이디
     */
    function get_client_domains(clientId) {
        console.log('get_client_domains');

        var data = {
        };

        $.ajax({
            method: 'get',
            url: '/clients/' + clientId + '/domains.do',
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

    /**
     * 고객 도메인 등록
     *
     * @param clientId 고객 아이디
     * @param domain 도메인
     * @returns 도메인 아이디
     */
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
                xhr.setRequestHeader ("Authorization", auth);
            },
            success: function (r) {
                console.log(r);
                domainId = r.id;
            }
        });

        return domainId;
    }

    /**
     * 고객 도메인 삭제
     *
     * @param clientId 고객 아이디
     * @param domainId 도메인 아이디
     */
    function delete_client_domains(clientId, domainId) {
        console.log('delete_client_domains');

        var data = {
        };

        $.ajax({
            method: 'delete',
            url: '/clients/' + clientId + '/domains/' + domainId + '.do',
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
<h2>오름아이오 James SA API 테스트!</h2>
<p>
<input type="button" id="test" value="테스트" />
</p>
</body>
</html>
